/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.es.test.pathfinding.topology;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author hacksaw
 */
public class TopologyFactory {
    private final static Map<String, Topology> topologies = new ConcurrentHashMap<>();

    private TopologyFactory() {
        topologies.put(topology1.getId(), topology1);
    }

    private static class Holder {
            private static final TopologyFactory INSTANCE = new TopologyFactory();
    }

    public static TopologyFactory getInstance() {
            return Holder.INSTANCE;
    }

    public Topology get(String id) {
        return topologies.get(id);
    }

    private final static Topology topology1 = new Topology.Builder("Test")
            .withNodes(
                new ArrayList<>(
                    Arrays.asList(
                        new Node.Builder("SwA")
                            .withNodeType(NodeType.SWITCH)
                            .build(),
                        new Node.Builder("SwH")
                            .withNodeType(NodeType.SWITCH)
                            .build(),
                        new Node.Builder("SwF")
                            .withNodeType(NodeType.SWITCH)
                            .build(),
                        new Node.Builder("SwZ")
                            .withNodeType(NodeType.SWITCH)
                            .build(),

                        new Node.Builder("SwJ")
                            .withNodeType(NodeType.SWITCH)
                            .build(),
                        new Node.Builder("SwK")
                            .withNodeType(NodeType.SWITCH)
                            .build(),
                        new Node.Builder("SwL")
                            .withNodeType(NodeType.SWITCH)
                            .build(),
                        new Node.Builder("SwN")
                            .withNodeType(NodeType.SWITCH)
                            .build(),
                        new Node.Builder("SwP")
                            .withNodeType(NodeType.SWITCH)
                            .build(),

                        new Node.Builder("RtJ")
                            .withNodeType(NodeType.ROUTER)
                            .build(),
                        new Node.Builder("RtK")
                            .withNodeType(NodeType.ROUTER)
                            .build(),
                        new Node.Builder("RtL")
                            .withNodeType(NodeType.ROUTER)
                            .build(),
                        new Node.Builder("RtN")
                            .withNodeType(NodeType.ROUTER)
                            .build(),
                        new Node.Builder("RtP")
                            .withNodeType(NodeType.ROUTER)
                            .build()
                    )
                )
            )
            .withLinks(
                new ArrayList<>(
                    Arrays.asList(
                        // Ethenet links.
                    new Link.Builder("SwA:1-to-SwH:1")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwA")
                        .withSourcePort("1-out")
                        .withDestinationNode("SwH")
                        .withDestinationPort("1-in")
                        .withCost(10)
                        .withBandwidth(100)
                        .withPeerLink("SwH:1-to-SwA:1")
                        .build(),
                    new Link.Builder("SwH:1-to-SwA:1")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwH")
                        .withSourcePort("1-out")
                        .withDestinationNode("SwA")
                        .withDestinationPort("1-in")
                        .withCost(10)
                        .withBandwidth(100)
                        .withPeerLink("SwA:1-to-SwH:1")
                        .build(),

                    new Link.Builder("SwA:2-to-SwF:1")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwA")
                        .withSourcePort("2-out")
                        .withDestinationNode("SwF")
                        .withDestinationPort("1-in")
                        .withCost(10)
                        .withBandwidth(100)
                        .withPeerLink("SwF:1-to-SwA:2")
                        .build(),
                    new Link.Builder("SwF:1-to-SwA:2")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwF")
                        .withSourcePort("1-out")
                        .withDestinationNode("SwA")
                        .withDestinationPort("2-in")
                        .withCost(10)
                        .withBandwidth(100)
                        .withPeerLink("SwA:2-to-SwF:1")
                        .build(),

                    new Link.Builder("SwF:2-to-SwH:2")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwF")
                        .withSourcePort("2-out")
                        .withDestinationNode("SwH")
                        .withDestinationPort("2-in")
                        .withCost(10)
                        .withBandwidth(10)
                        .withPeerLink("SwH:2-to-SwF:2")
                        .build(),
                    new Link.Builder("SwH:2-to-SwF:2")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwH")
                        .withSourcePort("2-out")
                        .withDestinationNode("SwF")
                        .withDestinationPort("2-in")
                        .withCost(10)
                        .withBandwidth(10)
                        .withPeerLink("SwF:2-to-SwH:2")
                        .build(),

                    new Link.Builder("SwF:3-to-SwZ:2")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwF")
                        .withSourcePort("3-out")
                        .withDestinationNode("SwZ")
                        .withDestinationPort("2-in")
                        .withCost(10)
                        .withBandwidth(10)
                        .withPeerLink("SwZ:2-to-SwF:3")
                        .build(),
                    new Link.Builder("SwZ:2-to-SwF:3")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwH")
                        .withSourcePort("2-out")
                        .withDestinationNode("SwF")
                        .withDestinationPort("2-in")
                        .withCost(10)
                        .withBandwidth(10)
                        .withPeerLink("SwF:3-to-SwZ:2")
                        .build(),

                    new Link.Builder("SwH:3-to-SwZ:3")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwH")
                        .withSourcePort("3-out")
                        .withDestinationNode("SwZ")
                        .withDestinationPort("3-in")
                        .withCost(1000)
                        .withBandwidth(1000)
                        .withPeerLink("SwZ:3-to-SwH:3")
                        .build(),
                    new Link.Builder("SwZ:3-to-SwH:3")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwZ")
                        .withSourcePort("3-out")
                        .withDestinationNode("SwH")
                        .withDestinationPort("3-in")
                        .withCost(10)
                        .withBandwidth(1000)
                        .withPeerLink("SwH:3-to-SwZ:3")
                        .build(),

                    new Link.Builder("SwA:3-to-SwJ:1")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwA")
                        .withSourcePort("3-out")
                        .withDestinationNode("SwJ")
                        .withDestinationPort("1-in")
                        .withCost(10)
                        .withBandwidth(100)
                        .withPeerLink("SwJ:1-to-SwA:3")
                        .build(),
                    new Link.Builder("SwJ:1-to-SwA:3")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwJ")
                        .withSourcePort("1-out")
                        .withDestinationNode("SwA")
                        .withDestinationPort("3-in")
                        .withCost(10)
                        .withBandwidth(100)
                        .withPeerLink("SwA:3-to-SwJ:1")
                        .build(),

                    new Link.Builder("SwA:4-to-SwK:1")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwA")
                        .withSourcePort("4-out")
                        .withDestinationNode("SwK")
                        .withDestinationPort("1-in")
                        .withCost(100)
                        .withBandwidth(100)
                        .withPeerLink("SwK:1-to-SwA:4")
                        .build(),
                    new Link.Builder("SwK:1-to-SwA:4")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwK")
                        .withSourcePort("1-out")
                        .withDestinationNode("SwA")
                        .withDestinationPort("4-in")
                        .withCost(100)
                        .withBandwidth(100)
                        .withPeerLink("SwA:4-to-SwK:1")
                        .build(),

                    new Link.Builder("SwA:5-to-SwL:1")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwA")
                        .withSourcePort("5-out")
                        .withDestinationNode("SwL")
                        .withDestinationPort("1-in")
                        .withCost(10)
                        .withBandwidth(10)
                        .withPeerLink("SwL:1-to-SwA:5")
                        .build(),
                    new Link.Builder("SwL:1-to-SwA:5")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwL")
                        .withSourcePort("1-out")
                        .withDestinationNode("SwA")
                        .withDestinationPort("5-in")
                        .withCost(10)
                        .withBandwidth(10)
                        .withPeerLink("SwA:5-to-SwL:1")
                        .build(),

                    new Link.Builder("SwZ:4-to-SwP:1")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwZ")
                        .withSourcePort("4-out")
                        .withDestinationNode("SwP")
                        .withDestinationPort("1-in")
                        .withCost(100)
                        .withBandwidth(100)
                        .withPeerLink("SwP:1-to-SwZ:4")
                        .build(),
                    new Link.Builder("SwP:1-to-SwZ:4")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwP")
                        .withSourcePort("1-out")
                        .withDestinationNode("SwZ")
                        .withDestinationPort("4-in")
                        .withCost(100)
                        .withBandwidth(100)
                        .withPeerLink("SwZ:4-to-SwP:1")
                        .build(),

                    new Link.Builder("SwZ:1-to-SwN:1")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwA")
                        .withSourcePort("1-out")
                        .withDestinationNode("SwN")
                        .withDestinationPort("1-in")
                        .withCost(100)
                        .withBandwidth(0)
                        .withPeerLink("SwN:1-to-SwZ:1")
                        .build(),
                    new Link.Builder("SwN:1-to-SwZ:1")
                        .withLinkType(LinkType.ETHERNET)
                        .withSourceNode("SwN")
                        .withSourcePort("1-out")
                        .withDestinationNode("SwZ")
                        .withDestinationPort("1-in")
                        .withCost(100)
                        .withBandwidth(100)
                        .withPeerLink("SwZ:1-to-SwN:1")
                        .build(),

                    // Adaptation links.
                    new Link.Builder("SwJ-to-RtJ")
                        .withLinkType(LinkType.ADAPTATION)
                        .withSourceNode("SwJ")
                        .withDestinationNode("RtJ")
                        .withCost(0)
                        .withPeerLink("RtJ-to-SwJ")
                        .build(),
                    new Link.Builder("RtJ-to-SwJ")
                        .withLinkType(LinkType.ADAPTATION)
                        .withSourceNode("RtJ")
                        .withDestinationNode("SwJ")
                        .withCost(0)
                        .withPeerLink("SwJ-to-RtJ")
                        .build(),

                    new Link.Builder("SwK-to-RtK")
                        .withLinkType(LinkType.ADAPTATION)
                        .withSourceNode("SwK")
                        .withDestinationNode("RtK")
                        .withCost(0)
                        .withPeerLink("RtK-to-SwK")
                        .build(),
                    new Link.Builder("RtK-to-SwK")
                        .withLinkType(LinkType.ADAPTATION)
                        .withSourceNode("RtK")
                        .withDestinationNode("SwK")
                        .withCost(0)
                        .withPeerLink("SwK-to-RtK")
                        .build(),

                    new Link.Builder("SwL-to-RtL")
                        .withLinkType(LinkType.ADAPTATION)
                        .withSourceNode("SwL")
                        .withDestinationNode("RtL")
                        .withCost(0)
                        .withPeerLink("RtL-to-SwL")
                        .build(),
                    new Link.Builder("RtL-to-SwL")
                        .withLinkType(LinkType.ADAPTATION)
                        .withSourceNode("RtL")
                        .withDestinationNode("SwL")
                        .withCost(0)
                        .withPeerLink("SwL-to-RtL")
                        .build(),

                    new Link.Builder("SwP-to-RtP")
                        .withLinkType(LinkType.ADAPTATION)
                        .withSourceNode("SwP")
                        .withDestinationNode("RtP")
                        .withCost(0)
                        .withPeerLink("RtP-to-SwP")
                        .build(),
                    new Link.Builder("RtP-to-SwP")
                        .withLinkType(LinkType.ADAPTATION)
                        .withSourceNode("RtP")
                        .withDestinationNode("SwP")
                        .withCost(0)
                        .withPeerLink("SwP-to-RtP")
                        .build(),

                    new Link.Builder("SwN-to-RtN")
                        .withLinkType(LinkType.ADAPTATION)
                        .withSourceNode("SwN")
                        .withDestinationNode("RtN")
                        .withCost(0)
                        .withPeerLink("RtN-to-SwN")
                        .build(),
                    new Link.Builder("RtN-to-SwN")
                        .withLinkType(LinkType.ADAPTATION)
                        .withSourceNode("RtN")
                        .withDestinationNode("SwN")
                        .withCost(0)
                        .withPeerLink("SwN-to-RtN")
                        .build(),

                    // MPLS links.
                    new Link.Builder("RtJ:2-to-RtN:2")
                        .withLinkType(LinkType.MPLS)
                        .withSourceNode("RtJ")
                        .withSourcePort("2-out")
                        .withDestinationNode("RtN")
                        .withDestinationPort("2-in")
                        .withCost(10)
                        .withBandwidth(100)
                        .withPeerLink("RtN:2-to-RtJ:2")
                        .build(),
                    new Link.Builder("RtN:2-to-RtJ:2")
                        .withLinkType(LinkType.MPLS)
                        .withSourceNode("RtN")
                        .withSourcePort("2-out")
                        .withDestinationNode("RtJ")
                        .withDestinationPort("2-in")
                        .withCost(10)
                        .withBandwidth(0)
                        .withPeerLink("RtJ:2-to-RtN:2")
                        .build(),

                    new Link.Builder("RtK:2-to-RtN:3")
                        .withLinkType(LinkType.MPLS)
                        .withSourceNode("RtK")
                        .withSourcePort("2-out")
                        .withDestinationNode("RtN")
                        .withDestinationPort("3-in")
                        .withCost(100)
                        .withBandwidth(100)
                        .withPeerLink("RtN:3-to-RtK:2")
                        .build(),
                    new Link.Builder("RtN:3-to-RtK:2")
                        .withLinkType(LinkType.MPLS)
                        .withSourceNode("RtN")
                        .withSourcePort("3-out")
                        .withDestinationNode("RtK")
                        .withDestinationPort("2-in")
                        .withCost(100)
                        .withBandwidth(0)
                        .withPeerLink("RtK:2-to-RtN:3")
                        .build(),

                    new Link.Builder("RtK:3-to-RtL:2")
                        .withLinkType(LinkType.MPLS)
                        .withSourceNode("RtK")
                        .withSourcePort("3-out")
                        .withDestinationNode("RtL")
                        .withDestinationPort("2-in")
                        .withCost(100)
                        .withBandwidth(100)
                        .withPeerLink("RtL:2-to-RtK:3")
                        .build(),
                    new Link.Builder("RtL:2-to-RtK:3")
                        .withLinkType(LinkType.MPLS)
                        .withSourceNode("RtL")
                        .withSourcePort("2-out")
                        .withDestinationNode("RtK")
                        .withDestinationPort("3-in")
                        .withCost(100)
                        .withBandwidth(100)
                        .withPeerLink("RtK:3-to-RtL:2")
                        .build(),

                    new Link.Builder("RtN:4-to-RtP:2")
                        .withLinkType(LinkType.MPLS)
                        .withSourceNode("RtN")
                        .withSourcePort("4-out")
                        .withDestinationNode("RtP")
                        .withDestinationPort("2-in")
                        .withCost(100)
                        .withBandwidth(100)
                        .withPeerLink("RtP:2-to-RtN:4")
                        .build(),
                    new Link.Builder("RtP:2-to-RtN:4")
                        .withLinkType(LinkType.MPLS)
                        .withSourceNode("RtP")
                        .withSourcePort("2-out")
                        .withDestinationNode("RtN")
                        .withDestinationPort("4-in")
                        .withCost(100)
                        .withBandwidth(100)
                        .withPeerLink("RtN:4-to-RtP:2")
                        .build(),

                    new Link.Builder("RtL:3-to-RtP:3")
                        .withLinkType(LinkType.MPLS)
                        .withSourceNode("RtL")
                        .withSourcePort("3-out")
                        .withDestinationNode("RtP")
                        .withDestinationPort("3-in")
                        .withCost(10)
                        .withBandwidth(100)
                        .withPeerLink("RtP:3-to-RtL:3")
                        .build(),
                    new Link.Builder("RtP:3-to-RtL:3")
                        .withLinkType(LinkType.MPLS)
                        .withSourceNode("RtP")
                        .withSourcePort("3-out")
                        .withDestinationNode("RtL")
                        .withDestinationPort("3-in")
                        .withCost(10)
                        .withBandwidth(10)
                        .withPeerLink("RtL:3-to-RtP:3")
                        .build(),

                    new Link.Builder("RtK:4-to-RtP:4")
                        .withLinkType(LinkType.MPLS)
                        .withSourceNode("RtK")
                        .withSourcePort("4-out")
                        .withDestinationNode("RtP")
                        .withDestinationPort("4-in")
                        .withCost(10)
                        .withBandwidth(10)
                        .withPeerLink("RtP:4-to-RtK:4")
                        .build(),
                    new Link.Builder("RtP:4-to-RtK:4")
                        .withLinkType(LinkType.MPLS)
                        .withSourceNode("RtP")
                        .withSourcePort("4-out")
                        .withDestinationNode("RtK")
                        .withDestinationPort("4-in")
                        .withCost(10)
                        .withBandwidth(10)
                        .withPeerLink("RtK:4-to-RtP:4")
                        .build()
                )
            )
        ).build();
}
