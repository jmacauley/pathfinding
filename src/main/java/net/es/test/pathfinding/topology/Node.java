package net.es.test.pathfinding.topology;

/**
 *
 * @author hacksaw
 */
public class Node extends NetworkObject {
    private NodeType nodeType;

    public Node(Builder builder) {
        super(builder.id);
        this.nodeType = builder.nodeType;
    }

    /**
     * @return the nodeType
     */
    public NodeType getNodeType() {
        return nodeType;
    }

    /**
     * @param nodeType the nodeType to set
     */
    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public static class Builder {
        private final String id;
        private NodeType nodeType;

        public Builder(String id) {
            this.id = id;
        }

        /**
         * @param nodeType the nodeType to set
         * @return
         */
        public Builder withNodeType(NodeType nodeType) {
            this.nodeType = nodeType;
            return this;
        }

        public Node build() {
            return new Node(this);
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Node: ");
        sb.append(this.getId());
        sb.append(", nodeType=");
        sb.append(this.nodeType);
        return sb.toString();
    }
}
