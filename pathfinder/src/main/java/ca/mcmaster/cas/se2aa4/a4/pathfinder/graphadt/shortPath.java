package ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt;

import java.util.*;

public class shortPath implements pathfinder {

    public ArrayList<node> find_shortest_path(Graph test_graph, node source_node, node dest_node) {
        Map<node, Integer> dist = new HashMap<>();
        Map<node, node> previous_node = new HashMap<>();
        PriorityQueue<node> non_vistednodes = new PriorityQueue<>(Comparator.comparingInt(dist::get));
        ArrayList<node> nodes_vist = new ArrayList<>();


        for(int i=0; i<test_graph.get_nodes_list().size();i++){
            node new_node = test_graph.get_nodes_list().get(i);

            if (new_node.equals(source_node)) {
                dist.put(new_node, 0);
            }
            else {dist.put(new_node, 1000000);}

            non_vistednodes.add(new_node);
        }

        ArrayList<node> no_path = new ArrayList<>();// returns list of zero nodes for not existing or node path-itself
        ArrayList<node> final_path = new ArrayList<>();

        while (non_vistednodes.size()!=0) {
            node current = non_vistednodes.poll();

            if (current == dest_node) {
                node temp_node = dest_node;

                while (temp_node != null) {
                    final_path.add(0, temp_node);
                    temp_node = previous_node.get(temp_node);
                }
                if(final_path.size()==1){
                    return no_path;
                }
                return final_path;
            }

            else {
                ArrayList<edges> nodes_edges= get_node_edge(test_graph, current);

                for (edges edge : nodes_edges) {
                    node node2 = edge.getTarget_node();
                    int new_dist = dist.get(current) + edge.get_weight();

                    if (!nodes_vist.contains(node2) && (new_dist < dist.get(node2))) {
                        previous_node.put(node2, current);
                        dist.put(node2, new_dist);
                        non_vistednodes.remove(node2);
                        non_vistednodes.add(node2);
                    }
                }
                nodes_vist.add(current);
            }
        }

        return no_path;

    }

    public ArrayList<edges> get_node_edge(Graph test_graph, node node_temp) {
        ArrayList<edges> source_edges = new ArrayList<>();
        for (edges curr_edge : test_graph.get_edges_list()) {
            if ((curr_edge.getSource_node().getCity_name()).equals(node_temp.getCity_name())) {
                source_edges.add(curr_edge);
            }
//            if ((curr_edge.getTarget_node().getCity_name()).equals(node_temp.getCity_name())) {
//                source_edges.add(curr_edge);
//            }
        }
        return source_edges;
    }

}
