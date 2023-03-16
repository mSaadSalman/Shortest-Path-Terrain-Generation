package ca.mcmaster.cas.se2aa4.a3.island;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.Green;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class islandGenerator {
    private Structs.Mesh aMesh;
    private CenterMesh centerMesh;

    public islandGenerator() throws IOException {
        aMesh = new MeshFactory().read("img/irregular.mesh");
        centerMesh = new CenterMesh();
    }

    public Structs.Mesh LandPoly() {
        double width = centerMesh.meshDimensions()[0];
        double height = centerMesh.meshDimensions()[1];

        Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder();
        iMesh.addAllVertices(aMesh.getVerticesList());
        iMesh.addAllSegments(aMesh.getSegmentsList());

        Structs.Property land = Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue("124,252,0")
                .build();

        Structs.Property ocean = Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue("0,0,55")
                .build();

        for (Structs.Polygon poly : aMesh.getPolygonsList()) {
            Structs.Vertex centroid = aMesh.getVertices(poly.getCentroidIdx());
            Structs.Polygon.Builder p = Structs.Polygon.newBuilder(poly);
            if((centroid.getX()<(width *0.75) && centroid.getX()>(width*0.25)) && (centroid.getY()<(height*0.75) && centroid.getY()>(height*0.25))){
                p.addProperties(land);
            } 
            else p.addProperties(ocean);
            iMesh.addPolygons(p);
         }
        
        return iMesh.build();
    }

}
