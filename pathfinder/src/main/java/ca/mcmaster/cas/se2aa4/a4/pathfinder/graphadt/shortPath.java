package ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;

public class shortPath implements pathfinder {
    public ArrayList<edges> get_node_edge(Graph test_graph, node node_temp) {
        ArrayList<edges> source_edges = new ArrayList<>();
        for (edges curr_edge : test_graph.get_edges_list()) {
            if ((curr_edge.getSource_node().getCity_name()).equals(node_temp.getCity_name())) {
                source_edges.add(curr_edge);
            }
            if ((curr_edge.getTarget_node().getCity_name()).equals(node_temp.getCity_name())) {
                source_edges.add(curr_edge);
            }
        }
        return source_edges;
    }

    public ArrayList<node> find_shortest_path(Graph test_graph, node source_node, node dest_node) {
        HashMap<node, Integer> dist = new HashMap<>();
        HashMap<node, edges> previousEdges = new HashMap<>();
        Deque<node> non_vistednodes = new ArrayDeque<>();
        ArrayList<edges> path = new ArrayList<>();
        ArrayList<edges> newpath = new ArrayList<>();
        ArrayList<node> finalpath = new ArrayList<>();

        non_vistednodes.addLast(source_node);
        dist.put(source_node, 0);
        while (non_vistednodes.size() != 0) {
            node new_node = non_vistednodes.removeFirst();
            ArrayList<edges> edges_ofnode = get_node_edge(test_graph, new_node);

            if (new_node.equals(dest_node)) {
                break;
            }
            //System.out.println(test.size());


            for (edges edge : edges_ofnode) {
                node edge_nodes = edge.getTarget_node();
                int new_dist;
                if (dist.containsKey(new_node)) {
                    new_dist = dist.get(new_node) + edge.get_weight();
                }
                else {
                    new_dist = 1000000;
                }
                if (dist.containsKey(edge_nodes)) {
                    int old_dist = dist.get(edge_nodes);
                    if (new_dist < old_dist) {
                        dist.put(edge_nodes, new_dist);
                        previousEdges.put(edge_nodes, edge);
                        non_vistednodes.addLast(edge_nodes);
                    }
                }
                else {
                    dist.put(edge_nodes, new_dist);
                    previousEdges.put(edge_nodes, edge);
                    non_vistednodes.addLast(edge_nodes);
                }
            }
        }

        edges old_edge = previousEdges.get(dest_node);
        while (old_edge != null) {
            path.add(old_edge);
            old_edge = previousEdges.get(old_edge.getSource_node());
        }
        for (int val = path.size() - 1; val >= 0; val--) {
            newpath.add(path.get(val));
        }
        int val2 = newpath.size() - 1;

        for (int i = 0; i < newpath.size(); i++) {
            if (i == val2) {
                finalpath.add(newpath.get(i).getSource_node());
                finalpath.add(newpath.get(i).getTarget_node());
                break;
            }
            else {
                finalpath.add(newpath.get(i).getSource_node());
            }
        }


        return finalpath;
    }

}
