package net.es.test.pathfinding.graph;

import net.es.test.pathfinding.graph.GraphEdge;
import net.es.test.pathfinding.topology.LinkType;

/**
 *
 * @author hacksaw
 */
public class NetworkEdge extends GraphEdge {
    private LinkType linkType;
    private long bandwidth;

    protected NetworkEdge(Builder builder) {
        super(builder);
        this.linkType = builder.linkType;
        this.bandwidth = builder.bandwidth;
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

    public static class Builder extends GraphEdge.Builder<Builder> {
        private LinkType linkType = LinkType.ETHERNET;
        private long bandwidth = 0;

        public Builder(String id) {
            super(id);
        }

        /**
         * @param linkType the linkType to set
         * @return
         */
        public Builder withLinktype(LinkType linkType) {
            this.linkType = linkType;
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
         *
         * @return
         */
        @Override
        public NetworkEdge build() {
            return new NetworkEdge(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("NetworkEdge: ");
        sb.append(this.getId());
        sb.append(", sourceVertex=");
        sb.append(this.getSourceVertex().getId());
        sb.append(", destinationVertex=");
        sb.append(this.getDestinationVertex().getId());
        sb.append(", cost=");
        sb.append(this.getCost());
        sb.append(", bandwidth=");
        sb.append(this.bandwidth);
        sb.append(", peerEdge=");
        sb.append(this.getPeerEdge());
        return sb.toString();
    }
}
