package net.es.test.pathfinding.transformers;

import com.google.common.base.Function;
import net.es.test.pathfinding.graph.GraphEdge;
import net.es.test.pathfinding.graph.NetworkEdge;

/**
 *
 * @author hacksaw
 */
public final class UnidirectionalEdgeEvaluator implements Function<GraphEdge, Number> {
    public final static long INVALID = 999999999;
    private final long bandwidth;

    public UnidirectionalEdgeEvaluator(long bandwidth) {
        this.bandwidth = bandwidth;
    }

    @Override
    public Number apply(GraphEdge edge) {
        // NetworkEdge is the only type of edge with bandwidth restrictions.
        if (edge instanceof NetworkEdge) {
            NetworkEdge ne = (NetworkEdge) edge;
            if (bandwidth > ne.getBandwidth()) {
                return INVALID;
            }
        }
        return edge.getCost();
    }

}
