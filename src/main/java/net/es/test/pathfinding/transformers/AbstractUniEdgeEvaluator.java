package net.es.test.pathfinding.transformers;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import net.es.test.pathfinding.graph.AbstractEdge;
import net.es.test.pathfinding.graph.GraphEdge;
import net.es.test.pathfinding.graph.NetworkEdge;
import net.es.test.pathfinding.pce.FlatMultiGraphPCE;
import net.es.test.pathfinding.pce.UnidirectionalResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hacksaw
 */
public final class AbstractUniEdgeEvaluator implements Function<GraphEdge, Number> {
    public final static long INVALID = 999999999;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final long bandwidth;
    private final FlatMultiGraphPCE mplsGraph;

    public AbstractUniEdgeEvaluator(long bandwidth, FlatMultiGraphPCE mplsGraph) {
        this.bandwidth = bandwidth;
        this.mplsGraph = mplsGraph;
    }

    @Override
    public Number apply(GraphEdge edge) {
        // NetworkEdge is the only edge type with bandwidth.
        if (edge instanceof NetworkEdge) {
            NetworkEdge ne = (NetworkEdge) edge;
            if (bandwidth > ne.getBandwidth() ||
                    Strings.isNullOrEmpty(ne.getPeerEdge())) {
                return INVALID;
            }
        }
        else if (edge instanceof AbstractEdge) {
            AbstractEdge ab = (AbstractEdge) edge;

            // This is an abstract link requiring us to perform pathfinding
            // on the MPLS graph.
            UnidirectionalResult bandwidthResult = mplsGraph.computeUnidirectionalPath(ab.getSourceVertex().getId(), ab.getDestinationVertex().getId(), bandwidth);

            ab.setCost(bandwidthResult.getCost());
            ab.setPath(bandwidthResult.getPath());

            if (bandwidthResult.getPath().isEmpty()) {
                return INVALID;
            }

            return bandwidthResult.getCost();
        }

        return edge.getCost();
    }

}
