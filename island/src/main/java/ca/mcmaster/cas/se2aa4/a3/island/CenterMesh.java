package ca.mcmaster.cas.se2aa4.a3.island;


import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class CenterMesh {
    private Structs.Mesh aMesh;

    public CenterMesh() throws IOException {
        aMesh = new MeshFactory().read("img/irregular.mesh");
    }

    public double[] centeredMeshDimensions() {
        // Initialize max with first element in the array
        double width = aMesh.getVertices(0).getX();
        double height = aMesh.getVertices(0).getY();

        for(int i=0; i < aMesh.getVerticesCount(); i++){
            if (aMesh.getVertices(i).getX() > width) width = aMesh.getVertices(i).getX();
            if (aMesh.getVertices(i).getY() > height) height = aMesh.getVertices(i).getY();
        }

        double minX = width*0.25;
        double maxX = width*0.75;
        double minY = height*0.25;
        double maxY = height*0.75;
        

        double[] centeredDimensions = {minX, maxX, minY, maxY};
        
        return centeredDimensions;
    }
}
