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
    public node get_nodename(Graph new_graph, String cit){
        node source =null;
        for(int i=0;i<new_graph.get_nodes_list().size();i++) {
            if(new_graph.get_nodes_list().get(i).getCity_name().equals(cit)){
                source = new_graph.get_nodes_list().get(i);
            }
        }
        return source;
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

        for (Structs.Polygon curr_poly:landpolygons_list){
            node source = get_nodeid(new_graph,curr_poly.getCentroidIdx());

            for (int idx: curr_poly.getNeighborIdxsList()){
                Structs.Polygon curr_neigbhor = iMesh.getPolygons(idx);
                node target = get_nodeid(new_graph,curr_neigbhor.getCentroidIdx());

                new_graph.addEdge(source,target,1);
            }
        }

        node dest = get_nodename(new_graph,"city");
        node dest2 = get_nodename(new_graph,"hamlet");

        shortPath new_path = new shortPath();
        ArrayList<node> path_list = new_path.find_shortest_path(new_graph, dest, dest2);
        System.out.println("-------------------");
        System.out.println(path_list.size());

        ArrayList<Integer> centroidid = new ArrayList<>();

        for(int k =0;k<path_list.size();k++){
            System.out.println(path_list.get(k).getCity_name());
            centroidid.add(path_list.get(k).getNode_num());
        }
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
