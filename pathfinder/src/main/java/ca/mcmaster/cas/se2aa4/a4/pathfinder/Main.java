package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.node;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.edges;


public class Main {

    public static void main(String[] args) {

        Graph new_graph = new Graph();
        node node_1 = new node("toronto", 22);
        node node_2 = new node("mississauga", 22);
        node node_3 = new node("oakville", 22);
        node node_4 = new node("hamilton", 22);

        new_graph.addNode(node_1);
        new_graph.addNode(node_2);
        new_graph.addNode(node_3);
        new_graph.addNode(node_4);

        new_graph.addEdge(node_1, node_2, 4);
        new_graph.addEdge(node_1, node_3, 1);
        new_graph.addEdge(node_3, node_4, 2);
        new_graph.addEdge(node_2, node_4, 2);

        System.out.print("Current nodes city names: ");

        for(int i =0; i< new_graph.get_nodes_list().size(); i++){
            System.out.print(new_graph.get_nodes_list().get(i).getCity_name()+ " ");
        }





    }
}
