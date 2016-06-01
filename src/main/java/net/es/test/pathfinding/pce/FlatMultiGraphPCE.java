package net.es.test.pathfinding.pce;

import com.google.common.collect.Lists;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SortedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.es.test.pathfinding.graph.AdaptationEdge;
import net.es.test.pathfinding.graph.EthernetEdge;
import net.es.test.pathfinding.graph.GraphEdge;
import net.es.test.pathfinding.graph.GraphVertex;
import net.es.test.pathfinding.graph.InternalEdge;
import net.es.test.pathfinding.graph.MplsEdge;
import net.es.test.pathfinding.graph.NetworkEdge;
import net.es.test.pathfinding.graph.PortVertex;
import net.es.test.pathfinding.graph.RouterVertex;
import net.es.test.pathfinding.graph.SwitchVertex;
import net.es.test.pathfinding.topology.Link;
import net.es.test.pathfinding.topology.Node;
import net.es.test.pathfinding.topology.NodeType;
import net.es.test.pathfinding.topology.Topology;
import net.es.test.pathfinding.transformers.BidirectionalEdgeEvaluator;
import net.es.test.pathfinding.transformers.UnidirectionalEdgeEvaluator;

/**
 * This class provides path finding capabilities on a directed graph with all
 * link types present on a single graph.
 *
 * @author hacksaw
 */
public class FlatMultiGraphPCE implements Ipce {
    private final Map<String, GraphVertex> vertexMap = new ConcurrentHashMap<>();
    private final Map<String, GraphEdge> edgeMap = new ConcurrentHashMap<>();
    private final Graph<GraphVertex, GraphEdge> flatMultigraph;

    /**
     * Constructor takes the network topology to build the graph on
     * instantiation.
     *
     * @param topology
     */
    public FlatMultiGraphPCE(Topology topology) {
        flatMultigraph = getFlatMultigraph(topology.getNodes(), topology.getLinks());
    }

    /**
     * Computes the shortest unidirectional path between the source and
     * destination nodes of the requested bandwidth.
     *
     * @param src
     * @param dst
     * @param bandwidth
     * @return
     */
    @Override
    public UnidirectionalResult computeUnidirectionalPath(String src, String dst, long bandwidth) {
        // Get our vertices corresponding to node names.
        GraphVertex srcVertex = getVertexOrFail(src);
        GraphVertex dstVertex = getVertexOrFail(dst);

        // We use a Dijkstra's shortest path algorithm with weighted edges.
        UnidirectionalEdgeEvaluator eval = new UnidirectionalEdgeEvaluator(bandwidth);
        DijkstraShortestPath<GraphVertex, GraphEdge> alg = new DijkstraShortestPath<>(flatMultigraph, eval, true);

        // We want to set the maximum acceptable path cost to less than an
        // value used to identify an invalid edge length.
        alg.setMaxDistance(UnidirectionalEdgeEvaluator.INVALID - 1);

        // Find a viable path.
        List<GraphEdge> path = alg.getPath(srcVertex, dstVertex);

        // Reduce to list of link identifiers and path cost.
        List<String> links = new ArrayList<>();
        long cost = path.stream().filter(edge -> !(edge instanceof InternalEdge)).map(edge -> {
            links.add(edge.getId());
            return edge;
        }).map(GraphEdge::getCost).reduce(0L, Long::sum);

        return new UnidirectionalResult.Builder().withCost(cost).withPath(links).build();
    }

    /**
     * Computes the shortest bidirectional path between the source and
     * destination nodes of the requested (possibly asymmetric) bandwidth.
     *
     * @param src
     * @param dst
     * @param outbound
     * @param inbound
     * @return
     */
    @Override
    public BidirectionalResult computeBidirectionalPath(String src, String dst, long outbound, long inbound) {
        // Get our vertices corresponding to node names.
        GraphVertex srcVertex = getVertexOrFail(src);
        GraphVertex dstVertex = getVertexOrFail(dst);

        // We will use a Dijkstra's shortest path algorithm with weighted edges
        // and an average cost of both path directions.
        BidirectionalEdgeEvaluator eval = new BidirectionalEdgeEvaluator(outbound, inbound, edgeMap);
        DijkstraShortestPath<GraphVertex, GraphEdge> alg = new DijkstraShortestPath<>(flatMultigraph, eval, true);

        // We want to set the maximum acceptable path cost to less than an
        // value used to identify an invalid edge length.
        alg.setMaxDistance(BidirectionalEdgeEvaluator.INVALID - 1);

        // Find a viable path.
        List<GraphEdge> path = alg.getPath(srcVertex, dstVertex);

        // Reduce to a list of link identifiers for component unidirectional
        // paths and their costs.
        long inboundCost = 0;
        long outboundCost = 0;
        List<String> inboundPath = new ArrayList<>();
        List<String> outboundPath = new ArrayList<>();
        for (GraphEdge edge : path) {
            if (!(edge instanceof InternalEdge)) {
                GraphEdge peerEdge = edgeMap.get(edge.getPeerEdge());
                inboundCost += peerEdge.getCost();
                inboundPath.add(peerEdge.getId());
                outboundCost += edge.getCost();
                outboundPath.add(edge.getId());
            }
        }

        // Don't forget to reverse the order of the inbound path elements.
        return new BidirectionalResult.Builder()
                .withOutboundPath(outboundPath)
                .withInboundPath(Lists.reverse(inboundPath))
                .withOutboundCost(outboundCost)
                .withInboundCost(inboundCost)
                .withCost((inboundCost + outboundCost)/2)
                .build();
    }

    /**
     * Lookup the graph vertex matching the node identifier or throw an
     * exception.
     *
     * @param id
     * @return
     * @throws IllegalArgumentException
     */
    private GraphVertex getVertexOrFail(String id) throws IllegalArgumentException {
        return Optional.ofNullable(vertexMap.get(id)).orElseThrow(new IllegalArgumentExceptionSupplier("invalid vertex: " + id));
    }

    /**
     * Build the directed multigraph from provided network topology.
     *
     * @param nodes
     * @param links
     * @return
     * @throws IllegalArgumentException
     */
    private  Graph<GraphVertex, GraphEdge> getFlatMultigraph(Collection<Node> nodes, Collection<Link> links) throws IllegalArgumentException {

        // Add network nodes as vertices to the graph.
        Graph<GraphVertex, GraphEdge> graph = new SortedSparseMultigraph<>();
        for (Node node : nodes) {
            GraphVertex vertex;
            if (node.getNodeType() == NodeType.SWITCH) {
                vertex = new SwitchVertex(node.getId()).withNode(node);
            }
            else if (node.getNodeType() == NodeType.ROUTER) {
                vertex = new RouterVertex(node.getId()).withNode(node);
            }
            else {
                throw new IllegalArgumentException("invalid node type: nodeId=" + node.getId() + ", type="+ node.getNodeType());
            }

            graph.addVertex(vertex);
            vertexMap.put(node.getId(), vertex);
        }

        // Add unidirectional edges to graph linked with source and destination ports.
        for (Link link : links) {
            // We need a reference to the source and destination node already added to graph.
            GraphVertex sourceNode = Optional.ofNullable(vertexMap.get(link.getSourceNode())).orElseThrow(new IllegalArgumentExceptionSupplier("invalid source vertex: " + link.getSourceNode()));
            GraphVertex destinationNode = Optional.ofNullable(vertexMap.get(link.getDestinationNode())).orElseThrow(new IllegalArgumentExceptionSupplier("invalid destination vertex: " + link.getDestinationNode()));

            switch (link.getLinkType()) {
                case ETHERNET:
                    addNetworkEdge(graph, sourceNode, destinationNode, link, new EthernetEdge.Builder(link.getId()));
                    break;
                case MPLS:
                    addNetworkEdge(graph, sourceNode, destinationNode, link, new MplsEdge.Builder(link.getId()));
                    break;
                case ADAPTATION:
                    addAdaptationEdge(graph, sourceNode, destinationNode, link);
                    break;
                default:
                    throw new IllegalArgumentException("invalid link type vertex: " + link.getId() + ", linkType=" + link.getLinkType());
            }
        }

        return graph;
    }

    /**
     * Add a network edge to the directed multigraph.  We treat this type of edge
     * with special handling, expanding it into the following:
     *      - A port vertex on each end of the network edge allowing for
     *        specification in ERO (not yet supported).
     *      - An internal edge from node vertex to a port vertex allowing for
     *        pathfinding traversal.
     *      - A network edge between ports modeling the physical attributes of
     *        the topological link.
     *
     * @param graph
     * @param src
     * @param dst
     * @param link
     * @param builder
     */
    private void addNetworkEdge(Graph<GraphVertex, GraphEdge> graph, GraphVertex src, GraphVertex dst, Link link, NetworkEdge.Builder builder) {
        // For each edge we add the associated in and out ports as vertices to the graph.
        PortVertex outPort = new PortVertex(link.getSourceNode() + ":" + link.getSourcePort());
        graph.addVertex(outPort);
        vertexMap.put(outPort.getId(), outPort);

        PortVertex inPort = new PortVertex(link.getDestinationNode() + ":" + link.getDestinationPort());
        graph.addVertex(inPort);
        vertexMap.put(inPort.getId(), inPort);

        // We now add an edge between the source node and the outbound port.
        GraphEdge outEdge = new InternalEdge.Builder(outPort.getId() + "-edge")
                .withSourceVertex(src)
                .withDestinationVertex(outPort)
                .build();

        graph.addEdge(outEdge,
                outEdge.getSourceVertex(),
                outEdge.getDestinationVertex(),
                EdgeType.DIRECTED);

        edgeMap.put(outEdge.getId(), outEdge);

        // We now add an edge between the inbound port and the destination node.
        GraphEdge inEdge = new InternalEdge.Builder(inPort.getId() + "-edge")
                .withSourceVertex(inPort)
                .withDestinationVertex(dst)
                .build();

        graph.addEdge(inEdge,
                inEdge.getSourceVertex(),
                inEdge.getDestinationVertex(),
                EdgeType.DIRECTED);

        edgeMap.put(inEdge.getId(), inEdge);

        // Finally we add an edge between the out and in ports between the nodes.
        GraphEdge linkEdge = builder
                    .withSourceVertex(outPort)
                    .withDestinationVertex(inPort)
                    .withBandwidth(link.getBandwidth())
                    .withCost(link.getCost())
                    .withPeerEdge(link.getPeerLink())
                    .build();

        graph.addEdge(linkEdge,
                linkEdge.getSourceVertex(),
                linkEdge.getDestinationVertex(),
                EdgeType.DIRECTED);

        edgeMap.put(linkEdge.getId(), linkEdge);
    }

    /**
     * Add a adaptation edge to the multigraph.  This edge models the adaptation
     * between networking technologies such as Ethernet and MPLS that may
     * require special handling.
     *
     * @param graph
     * @param src
     * @param dst
     * @param link
     */
    private void addAdaptationEdge(Graph<GraphVertex, GraphEdge> graph, GraphVertex src, GraphVertex dst, Link link) {
        GraphEdge linkEdge = new AdaptationEdge.Builder(link.getId())
                    .withSourceVertex(src)
                    .withDestinationVertex(dst)
                    .withCost(link.getCost())
                    .withPeerEdge(link.getPeerLink())
                    .build();

        graph.addEdge(linkEdge,
                linkEdge.getSourceVertex(),
                linkEdge.getDestinationVertex(),
                EdgeType.DIRECTED);

        edgeMap.put(linkEdge.getId(), linkEdge);
    }
}