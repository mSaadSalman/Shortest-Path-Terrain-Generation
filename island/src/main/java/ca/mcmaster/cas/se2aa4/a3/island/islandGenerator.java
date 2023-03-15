package ca.mcmaster.cas.se2aa4.a3.island;

import java.io.IOException;
import java.util.ArrayList;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class islandGenerator {

    private ArrayList<Polygon> landPoly;
    private ArrayList<Polygon> oceanPoly;
    private ArrayList<ArrayList<Polygon>> polyList;
    private Structs.Mesh aMesh;

    public islandGenerator() throws IOException {
        aMesh = new MeshFactory().read("img/irregular.mesh");
        this.landPoly = new ArrayList<>();
        this.oceanPoly = new ArrayList<>();
        this.polyList = new ArrayList<>();
    }

    public ArrayList<ArrayList<Polygon>> LandPoly() {

        for (Structs.Polygon p : aMesh.getPolygonsList()) {
            Structs.Vertex centroid = aMesh.getVertices(p.getCentroidIdx());
            // System.out.println(centroid.getX());
            // System.out.println(centroid.getY());

            if (centroid.getX() > 500 || centroid.getY() > 500) {
                oceanPoly.add(p);
            } else {
                landPoly.add(p);
            }
        }
        polyList.add(landPoly);
        polyList.add(oceanPoly);

        

        return polyList;
    }

}
