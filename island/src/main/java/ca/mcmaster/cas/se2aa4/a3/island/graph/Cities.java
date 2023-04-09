package ca.mcmaster.cas.se2aa4.a3.island.graph;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.Properties.Properties;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt.node;

import java.util.ArrayList;
import java.util.Random;

public class Cities {


    private Structs.Mesh aMesh;

    public Cities(Structs.Mesh iMesh) {
        this.aMesh = iMesh;
    }


    public Structs.Mesh enrichNodes() {
        Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder();
        iMesh.addAllVertices(aMesh.getVerticesList());
        iMesh.addAllSegments(aMesh.getSegmentsList());
        Structs.Property population_zero = Properties.get_no_Population();
        Structs.Property population_hamlet = Properties.get_hamlet_Population();
        Structs.Property population_village = Properties.get_village_Population();
        Structs.Property population_city = Properties.get_city_Population();
        Structs.Property population_capital = Properties.get_capital_Population();

        for (Structs.Polygon poly : aMesh.getPolygonsList()) {
            Structs.Polygon.Builder x = Structs.Polygon.newBuilder(poly);
            iMesh.addPolygons(x);
        }
        for (int i = 0; i < iMesh.getVerticesCount(); i++) {
            Structs.Vertex.Builder p = Structs.Vertex.newBuilder(iMesh.getVertices(i));
            p.addProperties(population_zero);
            iMesh.setVertices(i, p.build());
        }

        Graph new_graph = new Graph();
        int num_totalnodes= 10;
        int num_nodes = num_totalnodes-1;
        Random rand = new Random();



        int poly_size = aMesh.getPolygonsCount();

        int quot = num_nodes/3;
        int rem = num_nodes % 3;
        int num_hamlet = quot;
        int num_village = quot;
        int num_city= quot + rem;
        int capital =1;

        int num_hamlet_counter =0;
        int num_village_counter = 0;
        int num_city_counter =0;
        int capital_counter=0;



        for (int i=0; i<aMesh.getPolygonsCount(); i++) {
            int y = rand.nextInt(poly_size);
            Structs.Polygon temp = aMesh.getPolygons(y);
            Structs.Polygon.Builder x = Structs.Polygon.newBuilder(temp);
            int vert = x.getCentroidIdx();
            Structs.Vertex.Builder p = Structs.Vertex.newBuilder(iMesh.getVertices(vert));

            if (num_hamlet_counter <num_hamlet &&(temp.getProperties(0).getValue() != Properties.oceanColors &&
                    temp.getProperties(0).getValue() != Properties.lagoonColors &&
                    temp.getProperties(0).getValue() != Properties.lakeColors&&
                    temp.getProperties(0).getValue() != Properties.volcanoTier1Colors&&
                    temp.getProperties(0).getValue() != Properties.volcanoTier2Colors)) {

                p.setProperties(0, population_hamlet);
                node new_node =new node("hamlet",2,vert);
                new_graph.addNode(new_node);
                num_hamlet_counter++;

            }


            else if (num_village_counter <num_village &&(temp.getProperties(0).getValue() != Properties.oceanColors &&
                    temp.getProperties(0).getValue() != Properties.lagoonColors &&
                    temp.getProperties(0).getValue() != Properties.lakeColors&&
                    temp.getProperties(0).getValue() != Properties.volcanoTier1Colors&&
                    temp.getProperties(0).getValue() != Properties.volcanoTier2Colors) &&
            p.getProperties(0) !=population_hamlet) {
                p.setProperties(0, population_village);
                node new_node =new node("village",2,vert);
                new_graph.addNode(new_node);
                num_village_counter++;
            }

            else if (num_city_counter <num_city &&(temp.getProperties(0).getValue() != Properties.oceanColors &&
                    temp.getProperties(0).getValue() != Properties.lagoonColors &&
                    temp.getProperties(0).getValue() != Properties.lakeColors&&
                    temp.getProperties(0).getValue() != Properties.volcanoTier1Colors&&
                    temp.getProperties(0).getValue() != Properties.volcanoTier2Colors) &&
                    p.getProperties(0) !=population_hamlet && p.getProperties(0)!=population_village) {
                p.setProperties(0, population_city);
                node new_node =new node("city",2,vert);
                new_graph.addNode(new_node);
                num_city_counter++;
            }

            else if (capital_counter <capital &&(temp.getProperties(0).getValue() != Properties.oceanColors &&
                    temp.getProperties(0).getValue() != Properties.lagoonColors &&
                    temp.getProperties(0).getValue() != Properties.lakeColors&&
                    temp.getProperties(0).getValue() != Properties.volcanoTier1Colors&&
                    temp.getProperties(0).getValue() != Properties.volcanoTier2Colors) &&
                    p.getProperties(0) !=population_hamlet && p.getProperties(0)!=population_village
                    && p.getProperties(0)!=population_capital) {
                p.setProperties(0, population_capital);
                node new_node =new node("capital",2,vert);
                new_graph.addNode(new_node);
                capital_counter++;
            }
            else{
                p.setProperties(0, population_zero);
            }

            iMesh.addVertices(p);
        }

        return iMesh.build();
    }

}