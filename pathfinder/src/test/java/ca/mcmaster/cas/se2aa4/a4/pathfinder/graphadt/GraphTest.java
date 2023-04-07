package ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class GraphTest {
    Graph graph = new Graph();

    node node_1 = new node("toronto", 22,1);
    node node_2 = new node("mississauga", 22,2);
    node node_3 = new node("oakville", 22,3);
    node node_4 = new node("hamilton", 22,4);
    node node_5 = new node("milton",33,5);




    @Test
    public void test_addNode() {
        node test_node = new node("test_city", 22,22);
        graph.addNode(test_node);
        assertTrue(graph.get_nodes_list().contains(test_node));
    }

    @Test
    public void test_addEdge() {
        graph.addEdge(node_1, node_2, 18);
        assertEquals(node_1.getCity_name(),graph.get_edges_list().get(0).getSource_node().getCity_name());
        assertEquals(node_2.getCity_name(),graph.get_edges_list().get(1).getSource_node().getCity_name());
    }







}