package net.es.test.pathfinding.graph;

import net.es.test.pathfinding.graph.GraphVertex;

/**
 *
 * @author hacksaw
 */
public class PortVertex extends GraphVertex {
    public PortVertex(String id) {
        super(id);
    }

    @Override
    public String toString() {
        return "PortVertex=" + this.getId();
    }
}
