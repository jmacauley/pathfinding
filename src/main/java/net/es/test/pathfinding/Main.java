package net.es.test.pathfinding;

import java.util.List;
import net.es.test.pathfinding.pce.AbstractMultiGraphPCE;
import net.es.test.pathfinding.pce.BidirectionalResult;
import net.es.test.pathfinding.pce.FlatMultiGraphPCE;
import net.es.test.pathfinding.pce.Ipce;
import net.es.test.pathfinding.pce.UnidirectionalResult;
import net.es.test.pathfinding.topology.Link;
import net.es.test.pathfinding.topology.Topology;
import net.es.test.pathfinding.topology.TopologyFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hacksaw
 */
public class Main {
    private static org.slf4j.Logger log;

    public static void main(String [ ] args)
    {
        // Load and watch the log4j configuration file for changes.
        DOMConfigurator.configureAndWatch("log4j.xml", 45 * 1000);
        log = LoggerFactory.getLogger(Main.class);

        String src = "SwA";
        String dst = "SwZ";
        long outbound = 100;
        long inbound = 10;

        Topology topology = TopologyFactory.getInstance().get("Test");

        // Perform pathfinding using a flat undirectional multigraph.
        log.debug("FlatMultiGraphPCE...");
        FlatMultiGraphPCE fPCE = new FlatMultiGraphPCE(topology);
        getUnidirectionalPath(topology, fPCE, src, dst, outbound);
        getUnidirectionalPath(topology, fPCE, dst, src, inbound);
        getBidirectionalPath(topology, fPCE, src, dst, outbound, inbound);

        // Perform the same request using the abstract unidirectional multigraph.
        log.debug("AbstractMultiGraphPCE...");
        AbstractMultiGraphPCE aPCE = new AbstractMultiGraphPCE(topology);
        getUnidirectionalPath(topology, aPCE, src, dst, outbound);
        getUnidirectionalPath(topology, aPCE, dst, src, inbound);
        getBidirectionalPath(topology, aPCE, src, dst, outbound, inbound);
    }

    private static void getUnidirectionalPath(Topology topology, Ipce pce, String src, String dst, long bandwidth) {
        // Perform pathfinding using a flat undirectional multigraph.
        UnidirectionalResult result = pce.computeUnidirectionalPath(src, dst, bandwidth);

        log.debug("Unidirectional path: source=" + src +
                ", destination=" + dst +
                ", bandwidth=" + bandwidth);
        log.debug("Path:");

        List<String> path = result.getPath();
        long cost = path.stream().map(edge -> {
            log.debug("  " + edge);
            return topology.getLink(edge);
        }).map(Link::getCost).reduce(0L, Long::sum);

        log.debug("Path length=" + path.size() + ", cost=" + cost);
    }

    private static void getBidirectionalPath(Topology topology, Ipce pce, String src, String dst, long outbound, long inbound) {
        // Perform pathfinding using a flat undirectional multigraph.
        BidirectionalResult result = pce.computeBidirectionalPath(src, dst, outbound, inbound);

        log.debug("Bidirectional path: source=" + src +
                ", destination=" + dst +
                ", outbound=" + outbound +
                ", inbound=" + inbound);
        log.debug("Outbound path:");

        List<String> outboundPath = result.getOutboundPath();
        List<String> inboundPath = result.getInboundPath();
        long outboundCost = outboundPath.stream().map(edge -> {
            log.debug("  " + edge);
            return topology.getLink(edge);
        }).map(Link::getCost).reduce(0L, Long::sum);

        log.debug("Inbound path:");
        long inboundCost = inboundPath.stream().map(edge -> {
            log.debug("  " + edge);
            return topology.getLink(edge);
        }).map(Link::getCost).reduce(0L, Long::sum);

        log.debug("Outbound path length=" + outboundPath.size() + ", cost=" + outboundCost);
        log.debug("Inbound path length=" + inboundPath.size() + ", cost=" + inboundCost);
        log.debug("Average cost=" + (outboundCost + inboundCost) / 2);
    }
}
