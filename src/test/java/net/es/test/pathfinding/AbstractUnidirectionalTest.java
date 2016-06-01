package net.es.test.pathfinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import net.es.test.pathfinding.pce.FlatMultiGraphPCE;
import net.es.test.pathfinding.pce.UnidirectionalResult;
import net.es.test.pathfinding.topology.Link;
import net.es.test.pathfinding.topology.Topology;
import net.es.test.pathfinding.topology.TopologyFactory;
import org.junit.Test;

/**
 *
 * @author hacksaw
 */
public class AbstractUnidirectionalTest {

    @Test
    public void outboundTest() {
        List<String> path = new ArrayList<>(Arrays.asList(
            "SwA:3-to-SwJ:1",
            "SwJ-to-RtJ",
            "RtJ:2-to-RtN:2",
            "RtN-to-SwN",
            "SwN:1-to-SwZ:1"
        ));
        System.out.println("==================================================");
        System.out.println("AbstractUnidirectionalTest: outboundTest");
        System.out.println("==================================================");
        unidirectionalTest("Test", "SwA", "SwZ", 100, 5, 120, path);
    }

    @Test
    public void inboundTest() {
        List<String> path = new ArrayList<>(Arrays.asList(
            "SwZ:3-to-SwH:3",
            "SwH:1-to-SwA:1"
        ));
        System.out.println("==================================================");
        System.out.println("AbstractUnidirectionalTest: inboundTest");
        System.out.println("==================================================");
        unidirectionalTest("Test", "SwZ", "SwA", 10, 2, 20, path);
    }

    @Test
    public void failedTest() {
        System.out.println("==================================================");
        System.out.println("AbstractUnidirectionalTest: failedTest");
        System.out.println("==================================================");
        unidirectionalTest("Test", "SwF", "SwA", 10000, 0, 0, new ArrayList<>());
    }

    private void unidirectionalTest(String top, String src, String dst, long bandwidth,
                    int expectedSize, long expectedCost, List<String> expectedPath) {
        Topology topology = TopologyFactory.getInstance().get(top);
        FlatMultiGraphPCE dijkstraPCE = new FlatMultiGraphPCE(topology);
        UnidirectionalResult result = dijkstraPCE.computeUnidirectionalPath(src, dst, bandwidth);
        System.out.println("Unidirectional path: source=" + src + ", destination=" + dst + ", bandwidth=" + bandwidth);
        System.out.println("Forward path:");
        long cost = result.getPath().stream().map(edge -> {
            System.out.println("  " + edge);
            return topology.getLink(edge);
        }).map(Link::getCost).reduce(0L, Long::sum);
        System.out.println("Forward path length=" + result.getPath().size()
                + ", returned cost=" + result.getCost() + ", total cost=" + cost);

        assertEquals(expectedSize, result.getPath().size());
        assertEquals(expectedCost, result.getCost());
        assertEquals(expectedPath, result.getPath());
    }
}
