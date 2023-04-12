package ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private ArrayList <node> node_list;
    private ArrayList <edges> stored_edges;

    public Graph(){
        this.node_list = new ArrayList<>();
        this.stored_edges =  new ArrayList<edges>();
    }

    public void addNode (node new_node){
        node_list.add(new_node);
    }

    public void addEdge (node source, node target, int weight){
        edges new_edge = new edges(source,target,weight);
        edges opposite_edge = new edges(target, source, weight);
        stored_edges.add(new_edge); //adds this new edge "new_edge", to the stored edges list
        stored_edges.add(opposite_edge);
    }

    public ArrayList<edges> get_edges_list(){
        return stored_edges;
    }
    public ArrayList<node> get_nodes_list(){
        return node_list;
    }


}
