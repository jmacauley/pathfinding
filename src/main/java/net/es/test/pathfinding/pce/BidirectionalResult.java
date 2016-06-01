package net.es.test.pathfinding.pce;

import java.util.List;

/**
 *
 * @author hacksaw
 */
public class BidirectionalResult {
    private long cost;
    private long outboundCost;
    private long inboundCost;
    private List<String> outboundPath;
    private List<String> inboundPath;

    protected BidirectionalResult(Builder builder) {
        this.cost = builder.cost;
        this.outboundCost = builder.outboundCost;
        this.inboundCost = builder.inboundCost;
        this.outboundPath = builder.outboundPath;
        this.inboundPath = builder.inboundPath;
    }

    /**
     * @return the cost
     */
    public long getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(long cost) {
        this.cost = cost;
    }

    /**
     * @return the outboundCost
     */
    public long getOutboundCost() {
        return outboundCost;
    }

    /**
     * @param outboundCost the outboundCost to set
     */
    public void setOutboundCost(long outboundCost) {
        this.outboundCost = outboundCost;
    }

    /**
     * @return the inboundCost
     */
    public long getInboundCost() {
        return inboundCost;
    }

    /**
     * @param inboundCost the inboundCost to set
     */
    public void setInboundCost(long inboundCost) {
        this.inboundCost = inboundCost;
    }

    /**
     * @return the outboundPath
     */
    public List<String> getOutboundPath() {
        return outboundPath;
    }

    /**
     * @param outboundPath the outboundPath to set
     */
    public void setOutboundPath(List<String> outboundPath) {
        this.outboundPath = outboundPath;
    }

    /**
     * @return the inboundPath
     */
    public List<String> getInboundPath() {
        return inboundPath;
    }

    /**
     * @param inboundPath the inboundPath to set
     */
    public void setInboundPath(List<String> inboundPath) {
        this.inboundPath = inboundPath;
    }


    public static class Builder {
        private long cost;
        private long outboundCost;
        private long inboundCost;
        private List<String> outboundPath;
        private List<String> inboundPath;

        public Builder() {
        }

        Builder withCost(long cost) {
            this.cost = cost;
            return this;
        }

        Builder withOutboundCost(long outboundCost) {
            this.outboundCost = outboundCost;
            return this;
        }

        Builder withInboundCost(long inboundCost) {
            this.inboundCost = inboundCost;
            return this;
        }

        Builder withOutboundPath(List<String> outboundPath) {
            this.outboundPath = outboundPath;
            return this;
        }

        Builder withInboundPath(List<String> inboundPath) {
            this.inboundPath = inboundPath;
            return this;
        }

        public BidirectionalResult build() { return new BidirectionalResult(this); }
    }
}
