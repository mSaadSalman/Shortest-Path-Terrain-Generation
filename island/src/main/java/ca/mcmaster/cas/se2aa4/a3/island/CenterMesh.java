package ca.mcmaster.cas.se2aa4.a3.island;


import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class CenterMesh {
    private Structs.Mesh aMesh;
    
    public CenterMesh() throws IOException {
        aMesh = new MeshFactory().read("img/irregular.mesh");
    }

    public double[] meshDimensions() {
        // Initialize max with first element in the array
        double max_X = aMesh.getVertices(0).getX();
        double max_Y = aMesh.getVertices(0).getY();

        for(int i=0; i < aMesh.getVerticesCount(); i++){
            if (aMesh.getVertices(i).getX() > max_X) max_X = aMesh.getVertices(i).getX();
            if (aMesh.getVertices(i).getY() > max_Y) max_Y = aMesh.getVertices(i).getY();
        }

        double[] Dimensions = {max_X, max_Y};
        return Dimensions;
    }
}
