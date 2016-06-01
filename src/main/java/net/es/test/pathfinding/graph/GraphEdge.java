package net.es.test.pathfinding.graph;

/**
 *
 * @author hacksaw
 */
public class GraphEdge extends SortedGraphObject {
    private GraphVertex sourceVertex;
    private GraphVertex destinationVertex;
    private long cost;
    private String peerEdge;

    protected GraphEdge(Builder builder) {
        super(builder.id);
        this.sourceVertex = builder.sourceVertex;
        this.destinationVertex = builder.destinationVertex;
        this.cost = builder.cost;
        this.peerEdge = builder.peerEdge;
    }

    /**
     * @return the sourceVertex
     */
    public GraphVertex getSourceVertex() {
        return sourceVertex;
    }

    /**
     * @param sourceVertex the sourceVertex to set
     */
    public void setSourceVertex(GraphVertex sourceVertex) {
        this.sourceVertex = sourceVertex;
    }

    /**
     * @return the destinationVertex
     */
    public GraphVertex getDestinationVertex() {
        return destinationVertex;
    }

    /**
     * @param destinationVertex the destinationVertex to set
     */
    public void setDestinationVertex(GraphVertex destinationVertex) {
        this.destinationVertex = destinationVertex;
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
     * @return the peerEdge
     */
    public String getPeerEdge() {
        return peerEdge;
    }

    /**
     * @param peerEdge the peerEdge to set
     */
    public void setPeerEdge(String peerEdge) {
        this.peerEdge = peerEdge;
    }

    /**
     *
     * @param <T>
     */
    public static class Builder <T extends Builder> {
        private final String id;
        private GraphVertex sourceVertex;
        private GraphVertex destinationVertex;
        private long cost = 0;
        private String peerEdge;

        public Builder(String id) {
            this.id = id;
        }

        /**
         * @param sourceVertex the sourceVertex to set
         * @return
         */
        public T withSourceVertex(GraphVertex sourceVertex) {
            this.sourceVertex = sourceVertex;
            return (T) this;
        }

        /**
         * @param destinationVertex the destinationVertex to set
         * @return
         */
        public T withDestinationVertex(GraphVertex destinationVertex) {
            this.destinationVertex = destinationVertex;
            return (T) this;
        }

        /**
         * @param cost the cost to set
         * @return
         */
        public T withCost(long cost) {
            this.cost = cost;
            return (T) this;
        }

        public Builder withPeerEdge(String peerEdge) {
            this.peerEdge = peerEdge;
            return this;
        }

        public GraphEdge build() {
            return new GraphEdge(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GraphEdge: id=");
        sb.append(this.getId());
        sb.append(", sourceVertex=");
        sb.append(this.getSourceVertex().getId());
        sb.append(", destinationVertex=");
        sb.append(this.getDestinationVertex().getId());
        sb.append(", cost=");
        sb.append(this.cost);
        sb.append(", peerEdge=");
        sb.append(this.peerEdge);
        return sb.toString();
    }
}
