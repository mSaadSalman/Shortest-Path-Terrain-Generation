package ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt;

import java.util.ArrayList;


public interface pathfinder {

    ArrayList<node> find_shortest_path(Graph test_graph, node source_node, node dest_node);


}

