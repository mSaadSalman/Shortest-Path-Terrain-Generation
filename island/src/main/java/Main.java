import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.protobuf.Struct;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
// import ca.mcmaster.cas.se2aa4.a3.island.islandGenerator;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;

public class Main {
    public static void main(String[] args) throws IOException {
        // Configuration config = new Configuration(args);
        Structs.Mesh aMesh = new MeshFactory().read("img/irregular.mesh");

        ArrayList<Polygon> LandTiles = new ArrayList<>();
        ArrayList<Polygon> OceanTiles = new ArrayList<>();

        for (Structs.Polygon p : aMesh.getPolygonsList()) {
            Structs.Vertex centroid = aMesh.getVertices(p.getCentroidIdx());
            System.out.println(centroid.getX());
            System.out.println(centroid.getY());

            if (centroid.getX() > 500 || centroid.getY() > 500) {
                OceanTiles.add(p);
            } else {
                LandTiles.add(p);
            }
        }

        System.out.println("LANDTILES: " + LandTiles.toString());
        System.out.println("OCEANTILES: " + OceanTiles.toString());
    }
}
