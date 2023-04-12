package ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt;


import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class GraphTest {
    @Before
    public Graph create_Graph(){
        Graph test_graph = new Graph();
        node node_tor = new node("toronto", 22,1);
        node node_miss = new node("mississauga", 22,2);
        node node_oak = new node("oakville", 22,3);
        node node_ham = new node("hamilton", 22,4);
        node node_mil = new node("milton",33,5);
        node node_ajax = new node("ajax",22,6);

        test_graph.addNode(node_tor);//0
        test_graph.addNode(node_miss);//1
        test_graph.addNode(node_oak);//2
        test_graph.addNode(node_ham);//3
        test_graph.addNode(node_mil);//4
        test_graph.addNode(node_ajax);

        test_graph.addEdge(node_tor, node_miss, 15);
        test_graph.addEdge(node_tor, node_oak, 20);
        test_graph.addEdge(node_oak, node_ham, 1);
        test_graph.addEdge(node_miss, node_ham, 2);
        test_graph.addEdge(node_ham, node_mil,12);
        test_graph.addEdge(node_ham,node_ajax, 2);

        return test_graph;
    }

    @Test
    public void test_node_attributes(){
        node test_node = new node("Test_city", 22,197);
        assertEquals(197,test_node.getNode_num());
        assertEquals("Test_city",test_node.getCity_name());
    }
    @Test
    public void test_edge_attributes(){
        node test_node_1 = new node("Test_city", 22,1);
        node test_node_2 = new node("Test_city2", 22,2);
        edges test_edge= new edges(test_node_1,test_node_2,1);
        assertEquals(test_node_1,test_edge.getSource_node());
        assertEquals(test_node_2,test_edge.getTarget_node());
        assertEquals(1,test_edge.get_weight());
    }
    @Test
    public void test_addNode() {
        Graph new_graph = new Graph();
        node test_node = new node("test_city", 22,22);
        new_graph.addNode(test_node);
        assertTrue(new_graph.get_nodes_list().contains(test_node));
    }

    @Test
    public void test_addEdge() {
        Graph new_graph = new Graph();
        node test_node_1 = new node("test_city", 22,1);
        node test_node_2 = new node("test_city2", 22,2);
        new_graph.addEdge(test_node_1, test_node_2, 18);
        assertEquals(test_node_1,new_graph.get_edges_list().get(0).getSource_node());
        assertEquals(test_node_2,new_graph.get_edges_list().get(1).getSource_node());
    }

    @Test
    public void test_finding_node_edges(){
        shortPath test_path = new shortPath();
        Graph test_graph = create_Graph();
        int num_nodes = test_path.get_node_edge(test_graph,test_graph.get_nodes_list().get(0)).size();
        assertEquals(2,num_nodes);
    }

    @Test
    public void test_shortPathNode_itself(){
        shortPath test_path = new shortPath();
        Graph test_graph = create_Graph();
        int num_nodes =test_path.find_shortest_path(test_graph,test_graph.get_nodes_list().get(0),test_graph.get_nodes_list().get(0)).size();
        assertEquals(0,num_nodes);
    }

    @Test
    public void test_shortPath(){
        shortPath test_path = new shortPath();
        Graph test_graph = create_Graph();
        ArrayList<node> path =test_path.find_shortest_path(test_graph,test_graph.get_nodes_list().get(0),test_graph.get_nodes_list().get(5));
        ArrayList<node> expected_path = new ArrayList<>();
        expected_path.add(test_graph.get_nodes_list().get(0));
        expected_path.add(test_graph.get_nodes_list().get(1));
        expected_path.add(test_graph.get_nodes_list().get(3));
        expected_path.add(test_graph.get_nodes_list().get(5));

        assertEquals(expected_path,path);
    }

    @Test
    public void test_shortPath2(){
        shortPath test_path = new shortPath();
        Graph test_graph = create_Graph();
        ArrayList<node> path =test_path.find_shortest_path(test_graph,test_graph.get_nodes_list().get(4),test_graph.get_nodes_list().get(0));

        assertEquals("mississauga",path.get(2).getCity_name());
    }

    @Test
    public void test_shortPath_straight_order(){
        shortPath test_path = new shortPath();
        Graph test_graph = create_Graph();
        ArrayList<node> path =test_path.find_shortest_path(test_graph,test_graph.get_nodes_list().get(0),test_graph.get_nodes_list().get(3));
        int size= path.size();

        assertEquals(3,size);
    }




}