package net.es.test.pathfinding.graph;

import net.es.test.pathfinding.graph.GraphVertex;
import net.es.test.pathfinding.topology.Node;

/**
 *
 * @author hacksaw
 */
public class SwitchVertex extends GraphVertex {
    private Node node;

    public SwitchVertex(String id) {
        super(id);
    }

    /**
     * @return the node
     */
    public Node getNode() {
        return node;
    }

    /**
     * @param node the node to set
     */
    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * @param node the node to set
     * @return the node
     */
    public SwitchVertex withNode(Node node) {
        this.node = node;
        return this;
    }

    @Override
    public String toString() {
        return "SwitchVertex=" + this.getId();
    }
}
