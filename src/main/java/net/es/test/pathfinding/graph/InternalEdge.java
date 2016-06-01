package net.es.test.pathfinding.graph;

/**
 *
 * @author hacksaw
 */
public class InternalEdge extends GraphEdge {

    protected InternalEdge(Builder builder) {
        super(builder);
    }

    public static class Builder extends GraphEdge.Builder<Builder> {
        public Builder(String id) {
            super(id);
        }

        @Override
        public InternalEdge build() { return new InternalEdge(this); }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("InternalEdge: ");
        sb.append(this.getId());
        sb.append(", sourceVertex=");
        sb.append(this.getSourceVertex().getId());
        sb.append(", destinationVertex=");
        sb.append(this.getDestinationVertex().getId());
        sb.append(", cost=");
        sb.append(this.getCost());
        return sb.toString();
    }
}
