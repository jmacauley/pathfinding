package net.es.test.pathfinding.pce;

/**
 * Interface specification for a PCE module.
 *
 * @author hacksaw
 */
public interface Ipce {
    public UnidirectionalResult computeUnidirectionalPath(String src, String dst, long bandwidth);
    public BidirectionalResult computeBidirectionalPath(String src, String dst, long outbound, long inbound);
}
