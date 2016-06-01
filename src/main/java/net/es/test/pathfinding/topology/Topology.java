/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.es.test.pathfinding.topology;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author hacksaw
 */
public class Topology {
    private String id;
    private Map<String, Node> nodes = new ConcurrentHashMap<>();
    private Map<String, Link> links = new ConcurrentHashMap<>();

    protected Topology(Builder builder) {
        this.id = builder.id;
        builder.nodes.stream().forEach(node -> {
            this.nodes.put(node.getId(), node);
        });
        builder.links.stream().forEach(link -> {
            this.links.put(link.getId(), link);
        });
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the nodes
     */
    public Collection<Node> getNodes() {
        return nodes.values();
    }

    /**
     * @param nodes the nodes to set
     */
    public void setNodes(List<Node> nodes) {
        this.nodes.clear();
        nodes.stream().forEach(node -> {
            this.nodes.put(node.getId(), node);
        });
    }

    /**
     * @return the links
     */
    public Collection<Link> getLinks() {
        return links.values();
    }

    /**
     * @param links the links to set
     */
    public void setLinks(List<Link> links) {
        this.links.clear();
        links.stream().forEach(link -> {
            this.links.put(link.getId(), link);
        });
    }

    /**
     * @return the node
     */
    public Node getNode(String id) {
        return nodes.get(id);
    }

    /**
     * @return the link
     */
    public Link getLink(String id) {
        return links.get(id);
    }

    public static class Builder {
        private final String id;
        private List<Node> nodes;
        private List<Link> links;

        public Builder(String id) {
            this.id = id;
        }

        /**
         * @param nodes the nodes to set
         * @return
         */
        public Builder withNodes(List<Node> nodes) {
            this.nodes = nodes;
            return this;
        }

        /**
         * @param links the links to set
         * @return
         */
        public Builder withLinks(List<Link> links) {
            this.links = links;
            return this;
        }

        public Topology build() {
            return new Topology(this);
        }
    }
}
