package ca.mcmaster.cas.se2aa4.a3.island.graph;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.Properties.Properties;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.edges;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.node;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.shortPath;

import java.util.ArrayList;
import java.util.HashSet;

public class StarNetwork {

    private Structs.Mesh aMesh;
    private Graph graph;

    public StarNetwork(Structs.Mesh iMesh, Graph new_graph) {
        this.aMesh = iMesh;
        this.graph =new_graph;
    }
    public node get_nodeid(Graph new_graph, int idx){
        node source =null;
        for(int i=0;i<new_graph.get_nodes_list().size();i++) {
            if(new_graph.get_nodes_list().get(i).getNode_num()==idx){
                source = new_graph.get_nodes_list().get(i);
            }
        }
        return source;
    }
    public ArrayList<node> get_nodename(Graph new_graph, String cit){
        ArrayList<node> list_of_node = new ArrayList<>();
        node source =null;
        for(int i=0;i<new_graph.get_nodes_list().size();i++) {
            if(new_graph.get_nodes_list().get(i).getCity_name().equals(cit)){
                source = new_graph.get_nodes_list().get(i);
                list_of_node.add(source);
            }
        }
        return list_of_node;
    }

    public Structs.Mesh enrichGraph(Graph new_graph) {
        Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder();
        iMesh.addAllVertices(aMesh.getVerticesList());
        iMesh.addAllSegments(aMesh.getSegmentsList());
        for (Structs.Polygon poly : aMesh.getPolygonsList()) {
            Structs.Polygon.Builder x = Structs.Polygon.newBuilder(poly);
            iMesh.addPolygons(x);
        }

        int count=0;
        ArrayList<Structs.Polygon> landpolygons_list = new ArrayList<>();

        //creates list of land polygons only
        for (Structs.Polygon poly : aMesh.getPolygonsList()) {
            Structs.Polygon.Builder x = Structs.Polygon.newBuilder(poly);
            int vertex = x.getCentroidIdx();
            Structs.Vertex.Builder p = Structs.Vertex.newBuilder(iMesh.getVertices(vertex));
            if (poly.getProperties(0).getValue() != Properties.oceanColors &&
                    poly.getProperties(0).getValue() != Properties.lagoonColors) {
                landpolygons_list.add(poly);
                count++;
            }
        }

        //Loops through each polygon and its neigbor to add edges between them
        for (Structs.Polygon curr_poly:landpolygons_list){
            node source = get_nodeid(new_graph,curr_poly.getCentroidIdx());//gets current polygon's node by matching centroid

            for (int idx: curr_poly.getNeighborIdxsList()){
                Structs.Polygon curr_neigbhor = iMesh.getPolygons(idx);
                node target = get_nodeid(new_graph,curr_neigbhor.getCentroidIdx());//gets neighbor polygon's node by matching centroid

                new_graph.addEdge(source,target,1);
            }
        }

        //creates list of hamlet, villages and cities node
        node dest = get_nodename(new_graph,"capital").get(0);
        ArrayList<node> hamlets= get_nodename(new_graph,"hamlet");
        ArrayList<node> villages= get_nodename(new_graph,"village");
        ArrayList<node> cities= get_nodename(new_graph,"city");

        shortPath new_path = new shortPath();
        ArrayList<node> list_of_nodes = new ArrayList<>();

        //given list of city type, finds shortest path between the capital and that city type (for starnetwork)
        for(node ham_node:hamlets){
            ArrayList<node> path_list = new_path.find_shortest_path(new_graph, dest, ham_node);
            list_of_nodes.addAll(path_list);//keeps adding the nodes in shortest path into a single list
        }
        for(node village_node:villages){
            ArrayList<node> path_list = new_path.find_shortest_path(new_graph, dest, village_node);
            list_of_nodes.addAll(path_list);
        }
        for(node city_node:cities){
            ArrayList<node> path_list = new_path.find_shortest_path(new_graph, dest, city_node);
            list_of_nodes.addAll(path_list);
        }

        //given list of all nodes which are part of shortest path, find their centroid id (helps to visualize)
        ArrayList<Integer> centroidid = new ArrayList<>();
        for(int k =0;k<list_of_nodes.size();k++){
            centroidid.add(list_of_nodes.get(k).getNode_num()); //adds all shortest path node's centroid id's to list
        }

        //loop through all polygons and adds propety of road to be 1 if that polygon centroid is part of shortest path
        Structs.Property road = Properties.get_nodewithroad();
        Structs.Property no_road = Properties.get_nodewithoutroad();
        for (int i = 0; i < aMesh.getPolygonsCount(); i++) {
            Structs.Polygon.Builder p = Structs.Polygon.newBuilder(iMesh.getPolygons(i));

            if (centroidid.contains(p.getCentroidIdx())) {
                p.addProperties(road);
            }
            else {
                p.addProperties(no_road);
            }

            iMesh.setPolygons(i, p);
        }



        return iMesh.build();
    }
}
