package net.es.test.pathfinding.graph;

/**
 *
 * @author hacksaw
 */
public class AdaptationEdge extends GraphEdge {

    protected AdaptationEdge(Builder builder) {
        super(builder);
    }

    public static class Builder extends GraphEdge.Builder<Builder> {
        public Builder(String id) {
            super(id);
        }
        
        @Override
        public AdaptationEdge build() { return new AdaptationEdge(this); }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AdaptationEdge: ");
        sb.append(this.getId());
        sb.append(", sourceVertex=");
        sb.append(this.getSourceVertex().getId());
        sb.append(", destinationVertex=");
        sb.append(this.getDestinationVertex().getId());
        sb.append(", cost=");
        sb.append(this.getCost());
        sb.append(", peerEdge=");
        sb.append(this.getPeerEdge());
        return sb.toString();
    }
}
