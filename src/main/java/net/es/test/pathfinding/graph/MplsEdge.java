package net.es.test.pathfinding.graph;

/**
 *
 * @author hacksaw
 */
public class MplsEdge extends NetworkEdge {

    protected MplsEdge(Builder builder) {
        super(builder);
    }

    public static class Builder extends NetworkEdge.Builder {

        public Builder(String id) {
            super(id);
        }

        @Override
        public MplsEdge build() { return new MplsEdge(this); }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MplsEdge: ");
        sb.append(this.getId());
        sb.append(", sourceVertex=");
        sb.append(this.getSourceVertex().getId());
        sb.append(", destinationVertex=");
        sb.append(this.getDestinationVertex().getId());
        sb.append(", cost=");
        sb.append(this.getCost());
        sb.append(", bandwidth=");
        sb.append(this.getBandwidth());
        sb.append(", peerEdge=");
        sb.append(this.getPeerEdge());
        return sb.toString();
    }
}
