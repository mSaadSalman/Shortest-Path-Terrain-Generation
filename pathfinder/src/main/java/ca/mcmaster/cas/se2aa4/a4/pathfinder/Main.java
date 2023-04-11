package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.edges;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.node;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.shortPath;

import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {

        Graph new_graph = new Graph();
        node node_1 = new node("toronto", 22,1);
        node node_2 = new node("mississauga", 22,2);
        node node_3 = new node("oakville", 22,3);
        node node_4 = new node("hamilton", 22,4);
        node node_5 = new node("milton",33,5);
        node node_6 = new node("ajax",22,6);
        node node_7 = new node("whiby",22,6);

        new_graph.addNode(node_1);
        new_graph.addNode(node_2);
        new_graph.addNode(node_3);
        new_graph.addNode(node_4);
        new_graph.addNode(node_5);
        new_graph.addNode(node_6);
        new_graph.addNode(node_7);

        new_graph.addEdge(node_1, node_2, 18);
        new_graph.addEdge(node_1, node_3, 20);
        new_graph.addEdge(node_3, node_4, 1);
        new_graph.addEdge(node_2, node_4, 2);
        new_graph.addEdge(node_4, node_5,12);
        new_graph.addEdge(node_4,node_6, 2);


        System.out.print("Current nodes city names: ");

        for(int i =0; i< new_graph.get_nodes_list().size(); i++){
            System.out.print(new_graph.get_nodes_list().get(i).getCity_name()+ " ");
        }

        System.out.println();
        System.out.println("--------------------------------------------------------");



        shortPath new_path = new shortPath();
        ArrayList<node> path_list = new_path.find_shortest_path(new_graph, node_1, node_4);

        for(int i =0;i<path_list.size();i++){
            System.out.println(path_list.get(i).getCity_name());
        }



    }
}
