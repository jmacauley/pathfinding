package net.es.test.pathfinding.transformers;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import java.util.Map;
import net.es.test.pathfinding.graph.AdaptationEdge;
import net.es.test.pathfinding.graph.GraphEdge;
import net.es.test.pathfinding.graph.NetworkEdge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hacksaw
 */
public final class BidirectionalEdgeEvaluator implements Function<GraphEdge, Number> {
    public final static long INVALID = 999999999;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final long outbound;
    private final long inbound;
    private final Map<String, GraphEdge> edgeMap;

    public BidirectionalEdgeEvaluator(long outbound, long inbound, Map<String, GraphEdge> edgeMap) {
        this.outbound = outbound;
        this.inbound = inbound;
        this.edgeMap = edgeMap;
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

        return edge.getCost();
    }

}
