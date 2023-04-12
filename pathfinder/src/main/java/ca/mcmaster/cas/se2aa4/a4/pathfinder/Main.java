package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.edges;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.node;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.shortPath;

import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        Graph test_graph = new Graph();
        node node_tor = new node("toronto", 22,1);
        node node_miss = new node("mississauga", 22,2);
        node node_oak = new node("oakville", 22,3);
        node node_ham = new node("hamilton", 22,4);
        node node_mil = new node("milton",33,5);
        node node_ajax = new node("ajax",22,6);
        node node_whit = new node("whitby",22,6);

        test_graph.addNode(node_tor);
        test_graph.addNode(node_miss);
        test_graph.addNode(node_oak);
        test_graph.addNode(node_ham);
        test_graph.addNode(node_mil);
        test_graph.addNode(node_ajax);

        test_graph.addEdge(node_tor, node_miss, 2);
        test_graph.addEdge(node_tor, node_oak, 1);
        test_graph.addEdge(node_oak, node_ham, 1);
        test_graph.addEdge(node_miss, node_ham, 1);
        test_graph.addEdge(node_ham, node_mil,1);
        test_graph.addEdge(node_ham,node_ajax, 1);

        shortPath test_path = new shortPath();
        ArrayList<node> path =test_path.find_shortest_path(test_graph,node_ham,node_oak);

        for(int i=0;i<path.size();i++){
            System.out.println(path.get(i).getCity_name());
        }


    }
}
