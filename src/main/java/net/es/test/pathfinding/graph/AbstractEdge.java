package net.es.test.pathfinding.graph;

import java.util.List;

/**
 *
 * @author hacksaw
 */
public class AbstractEdge extends GraphEdge {
    private List<String> path;

    protected AbstractEdge(Builder builder) {
        super(builder);
        this.path = builder.path;
    }

    /**
     * @return the path
     */
    public List<String> getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(List<String> path) {
        this.path = path;
    }

    public static class Builder extends GraphEdge.Builder<Builder> {
        private List<String> path;

        public Builder(String id) {
            super(id);
        }

        public Builder withPath(List<String> path) {
            this.path = path;
            return this;
        }

        @Override
        public AbstractEdge build() { return new AbstractEdge(this); }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AbstractEdge: ");
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
