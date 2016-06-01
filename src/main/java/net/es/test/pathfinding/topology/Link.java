package net.es.test.pathfinding.topology;

/**
 *
 * @author hacksaw
 */
public class Link extends NetworkObject {
    private LinkType linkType;
    private String sourceNode;
    private String sourcePort;
    private String destinationNode;
    private String destinationPort;
    private long cost = 1;
    private long bandwidth = 0;
    private String peerLink;

    public Link(Builder builder) {
        super(builder.id);
        this.linkType = builder.linkType;
        this.sourceNode = builder.sourceNode;
        this.sourcePort = builder.sourcePort;
        this.destinationNode = builder.destinationNode;
        this.destinationPort = builder.destinationPort;
        this.cost = builder.cost;
        this.bandwidth = builder.bandwidth;
        this.peerLink = builder.peerLink;
    }

    /**
     * @return the sourceNode
     */
    public String getSourceNode() {
        return sourceNode;
    }

    /**
     * @param sourceNode the sourceNode to set
     */
    public void setSourceNode(String sourceNode) {
        this.sourceNode = sourceNode;
    }

    /**
     * @return the sourcePort
     */
    public String getSourcePort() {
        return sourcePort;
    }

    /**
     * @param sourcePort the sourcePort to set
     */
    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }

    /**
     * @return the destinationNode
     */
    public String getDestinationNode() {
        return destinationNode;
    }

    /**
     * @param destinationNode the destinationNode to set
     */
    public void setDestinationNode(String destinationNode) {
        this.destinationNode = destinationNode;
    }

    /**
     * @return the destinationPort
     */
    public String getDestinationPort() {
        return destinationPort;
    }

    /**
     * @param destinationPort the destinationPort to set
     */
    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
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
     * @return the bandwidth
     */
    public long getBandwidth() {
        return bandwidth;
    }

    /**
     * @param bandwidth the bandwidth to set
     */
    public void setBandwidth(long bandwidth) {
        this.bandwidth = bandwidth;
    }

    /**
     * @return the peerLink
     */
    public String getPeerLink() {
        return peerLink;
    }

    /**
     * @param peerLink the peerLink to set
     */
    public void setPeerLink(String peerLink) {
        this.peerLink = peerLink;
    }

    /**
     * @return the linkType
     */
    public LinkType getLinkType() {
        return linkType;
    }

    /**
     * @param linkType the linkType to set
     */
    public void setLinkType(LinkType linkType) {
        this.linkType = linkType;
    }

    public static class Builder {
        private final String id;
        private LinkType linkType;
        private String sourceNode;
        private String sourcePort;
        private String destinationNode;
        private String destinationPort;
        private long cost = 1;
        private long bandwidth = 0;
        private String peerLink;

        public Builder(String id) {
            this.id = id;
        }

        /**
         * @param linkType the linkType to set
         * @return
         */
        public Builder withLinkType(LinkType linkType) {
            this.linkType = linkType;
            return this;
        }

        /**
         * @param sourceNode the sourceNode to set
         * @return
         */
        public Builder withSourceNode(String sourceNode) {
            this.sourceNode = sourceNode;
            return this;
        }

        /**
         * @param sourcePort the sourcePort to set
         * @return
         */
        public Builder withSourcePort(String sourcePort) {
            this.sourcePort = sourcePort;
            return this;
        }

        /**
         * @param destinationNode the destinationNode to set
         * @return
         */
        public Builder withDestinationNode(String destinationNode) {
            this.destinationNode = destinationNode;
            return this;
        }

        /**
         * @param destinationPort the destinationPort to set
         * @return
         */
        public Builder withDestinationPort(String destinationPort) {
            this.destinationPort = destinationPort;
            return this;
        }

        /**
         * @param cost the cost to set
         * @return
         */
        public Builder withCost(long cost) {
            this.cost = cost;
            return this;
        }

        /**
         * @param bandwidth the bandwidth to set
         * @return
         */
        public Builder withBandwidth(long bandwidth) {
            this.bandwidth = bandwidth;
            return this;
        }

        /**
         * @param peerLink the peerLink to set
         * @return
         */
        public Builder withPeerLink(String peerLink) {
            this.peerLink = peerLink;
            return this;
        }

        public Link build() {
            return new Link(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Link: ");
        sb.append(this.getId());
        sb.append(", linkType=");
        sb.append(this.linkType);
        sb.append(", sourceNode=");
        sb.append(this.sourceNode);
        sb.append(", sourcePort=");
        sb.append(this.sourcePort);
        sb.append(", destinationNode=");
        sb.append(this.destinationNode);
        sb.append(", destinationPort=");
        sb.append(this.destinationPort);
        sb.append(", cost=");
        sb.append(this.cost);
        sb.append(", bandwidth=");
        sb.append(this.bandwidth);
        sb.append(", peerLink=");
        sb.append(this.peerLink);
        return sb.toString();
    }
}
