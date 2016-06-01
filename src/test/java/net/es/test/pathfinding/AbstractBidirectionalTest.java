package net.es.test.pathfinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import net.es.test.pathfinding.pce.AbstractMultiGraphPCE;
import net.es.test.pathfinding.pce.BidirectionalResult;
import net.es.test.pathfinding.topology.Link;
import net.es.test.pathfinding.topology.Topology;
import net.es.test.pathfinding.topology.TopologyFactory;
import org.junit.Test;

/**
 *Outbound path:
  SwA:4-to-SwK:1
  SwK-to-RtK
  RtK:3-to-RtL:2
  RtL:3-to-RtP:3
  RtP-to-SwP
  SwP:1-to-SwZ:4
Inbound path:
  SwZ:4-to-SwP:1
  SwP-to-RtP
  RtP:4-to-RtK:4
  RtK-to-SwK
  SwK:1-to-SwA:4
Outbound path length=6, cost=310
Inbound path length=5, cost=210
Average cost=260
 * @author hacksaw
 */
public class AbstractBidirectionalTest {

    @Test
    public void asymmetricBandwidthTest() {
        List<String> outbound = new ArrayList<>(Arrays.asList(
            "SwA:4-to-SwK:1",
            "SwK-to-RtK",
            "RtK:3-to-RtL:2",
            "RtL:3-to-RtP:3",
            "RtP-to-SwP",
            "SwP:1-to-SwZ:4"
        ));

        List<String> inbound = new ArrayList<>(Arrays.asList(
            "SwZ:4-to-SwP:1",
            "SwP-to-RtP",
            "RtP:4-to-RtK:4",
            "RtK-to-SwK",
            "SwK:1-to-SwA:4"
        ));

        System.out.println("==================================================");
        System.out.println("AbstractBidirectionalTest: asymmetricBandwidthTest");
        System.out.println("==================================================");
        bidirectionalTest("Test", "SwA", "SwZ", 100, 10, 6, 5, 310, 210, outbound, inbound);
    }

    @Test
    public void symmetricBandwidthTest() {
        List<String> outbound = new ArrayList<>(Arrays.asList(
            "SwA:1-to-SwH:1",
            "SwH:3-to-SwZ:3"
        ));

        List<String> inbound = new ArrayList<>(Arrays.asList(
            "SwZ:3-to-SwH:3",
            "SwH:1-to-SwA:1"
        ));

        System.out.println("==================================================");
        System.out.println("AbstractBidirectionalTest: symmetricBandwidthTest");
        System.out.println("==================================================");
        bidirectionalTest("Test", "SwA", "SwZ", 100, 100, 2, 2, 1010, 20, outbound, inbound);
    }

    @Test
    public void failTest() {
        List<String> outbound = new ArrayList<>();

        List<String> inbound = new ArrayList<>();

        System.out.println("==================================================");
        System.out.println("AbstractBidirectionalTest: failTest");
        System.out.println("==================================================");
        bidirectionalTest("Test", "SwA", "SwZ", 1000, 100, 0, 0, 0, 0, outbound, inbound);
    }

    private void bidirectionalTest(
                    String top,
                    String src, String dst,
                    long outbound, long inbound,
                    int outboundExpectedSize, int inboundExpectedSize,
                    long outExpectedCost, long inExpectedCost,
                    List<String> outboundExpectedPath, List<String> inboundExpectedPath) {

        Topology topology = TopologyFactory.getInstance().get(top);
        AbstractMultiGraphPCE dijkstraPCE = new AbstractMultiGraphPCE(topology);
        BidirectionalResult result = dijkstraPCE.computeBidirectionalPath(src, dst, outbound, inbound);

        System.out.println("Bidirectional path: source=" + src +
                ", destination=" + dst +
                ", outbound=" + outbound +
                ", inbound=" + inbound);
        System.out.println("Outbound path:");

        List<String> outboundPath = result.getOutboundPath();
        List<String> inboundPath = result.getInboundPath();
        long outboundCost = outboundPath.stream().map(edge -> {
            System.out.println("  " + edge);
            return topology.getLink(edge);
        }).map(Link::getCost).reduce(0L, Long::sum);

        System.out.println("Inbound path:");
        long inboundCost = inboundPath.stream().map(edge -> {
            System.out.println("  " + edge);
            return topology.getLink(edge);
        }).map(Link::getCost).reduce(0L, Long::sum);

        System.out.println("Outbound path length=" + outboundPath.size() + ", cost=" + outboundCost);
        System.out.println("Inbound path length=" + inboundPath.size() + ", cost=" + inboundCost);
        System.out.println("Average cost=" + (outboundCost + inboundCost) / 2);

        assertEquals(outboundExpectedSize, outboundPath.size());
        assertEquals(inboundExpectedSize, inboundPath.size());

        assertEquals(outExpectedCost, outboundCost);
        assertEquals(inExpectedCost, inboundCost);

        assertEquals(outboundExpectedPath, outboundPath);
        assertEquals(inboundExpectedPath, inboundPath);
    }
}
