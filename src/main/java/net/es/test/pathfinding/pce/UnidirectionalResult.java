package net.es.test.pathfinding.pce;

import java.util.List;

/**
 *
 * @author hacksaw
 */
public class UnidirectionalResult {
    private long cost;
    private List<String> path;

    protected UnidirectionalResult(Builder builder) {
        this.cost = builder.cost;
        this.path = builder.path;
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

    public static class Builder {
        private long cost;
        private List<String> path;

        public Builder() {
        }

        Builder withCost(long cost) {
            this.cost = cost;
            return this;
        }

        Builder withPath(List<String> path) {
            this.path = path;
            return this;
        }

        public UnidirectionalResult build() { return new UnidirectionalResult(this); }
    }
}
