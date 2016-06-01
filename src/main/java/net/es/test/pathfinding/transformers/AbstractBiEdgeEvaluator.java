package net.es.test.pathfinding.transformers;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import java.util.Map;
import net.es.test.pathfinding.graph.AbstractEdge;
import net.es.test.pathfinding.graph.AdaptationEdge;
import net.es.test.pathfinding.graph.GraphEdge;
import net.es.test.pathfinding.graph.NetworkEdge;
import net.es.test.pathfinding.pce.FlatMultiGraphPCE;
import net.es.test.pathfinding.pce.UnidirectionalResult;

/**
 *
 * @author hacksaw
 */
public final class AbstractBiEdgeEvaluator implements Function<GraphEdge, Number> {
    public final static long INVALID = 999999999;
    private final long outbound;
    private final long inbound;
    private final Map<String, GraphEdge> edgeMap;
    private final FlatMultiGraphPCE mplsGraph;

    public AbstractBiEdgeEvaluator(long outbound, long inbound, Map<String, GraphEdge> edgeMap, FlatMultiGraphPCE mplsGraph) {
        this.outbound = outbound;
        this.inbound = inbound;
        this.edgeMap = edgeMap;
        this.mplsGraph = mplsGraph;
    }

    @Override
    public Number apply(GraphEdge edge) {
        // NetworkEdge is the only edge type with bandwidth.
        if (edge instanceof NetworkEdge) {
            NetworkEdge ne = (NetworkEdge) edge;
            if (outbound > ne.getBandwidth() ||
                    Strings.isNullOrEmpty(ne.getPeerEdge())) {
                return INVALID;
            }

            // For bidirectional connections we need to verify
            // the required bandwidth is on the return path.
            GraphEdge peerEdge = edgeMap.get(ne.getPeerEdge());
            if (peerEdge == null) {
                return INVALID;
            }

            if (peerEdge instanceof NetworkEdge) {
                NetworkEdge pne = (NetworkEdge) peerEdge;
                if (inbound > pne.getBandwidth()) {
                    return INVALID;
                }

                return ne.getCost() + pne.getCost();
            }

            return INVALID;
        }
        // AdaptationEdge has no bandwidth but contributes to cost.
        else if (edge instanceof AdaptationEdge) {
            AdaptationEdge ad = (AdaptationEdge) edge;
            if (Strings.isNullOrEmpty(ad.getPeerEdge())) {
                return INVALID;
            }

            GraphEdge peerEdge = edgeMap.get(ad.getPeerEdge());
            if (peerEdge == null) {
                return INVALID;
            }

            if (peerEdge instanceof AdaptationEdge) {
                AdaptationEdge pad = (AdaptationEdge) peerEdge;
                return ad.getCost() + pad.getCost();
            }

            return INVALID;
        }
        else if (edge instanceof AbstractEdge) {
            AbstractEdge ab = (AbstractEdge) edge;
            GraphEdge peerEdge = edgeMap.get(ab.getPeerEdge());
            if (peerEdge == null || !(peerEdge instanceof AbstractEdge)) {
                return INVALID;
            }

            // This is an abstract link requiring us to perform pathfinding
            // on the MPLS graph.
            UnidirectionalResult outboundResult = mplsGraph.computeUnidirectionalPath(ab.getSourceVertex().getId(), ab.getDestinationVertex().getId(), outbound);
            UnidirectionalResult inboundResult = mplsGraph.computeUnidirectionalPath(ab.getDestinationVertex().getId(), ab.getSourceVertex().getId(), inbound);

            ab.setPath(outboundResult.getPath());
            ab.setCost(outboundResult.getCost());
            ((AbstractEdge) peerEdge).setPath(inboundResult.getPath());
            peerEdge.setCost(inboundResult.getCost());


            // If either of the unidirectional paths were not possible then this
            // is not a feasible path.
            if (outboundResult.getPath().isEmpty() || inboundResult.getPath().isEmpty()) {
                return INVALID;
            }

            return inboundResult.getCost() + outboundResult.getCost();
        }

        return edge.getCost();
    }

}
