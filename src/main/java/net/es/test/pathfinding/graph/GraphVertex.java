package net.es.test.pathfinding.graph;

/**
 *
 * @author hacksaw
 */
public class GraphVertex extends SortedGraphObject {
    public GraphVertex(String id) {
        super(id);
    }

    @Override
    public String toString() {
        return "GraphVertex=" + this.getId();
    }
}
