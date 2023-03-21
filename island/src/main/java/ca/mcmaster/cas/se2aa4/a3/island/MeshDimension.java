package ca.mcmaster.cas.se2aa4.a3.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class MeshDimension {
    private Structs.Mesh aMesh;
    public double maxX;
    public double maxY;

    public MeshDimension(Structs.Mesh mesh) {
        this.aMesh = mesh;
        centeredMeshDimensions();
    }

    public void centeredMeshDimensions() {
        double width = aMesh.getVertices(0).getX();
        double height = aMesh.getVertices(0).getY();

        for (int i = 0; i < aMesh.getVerticesCount(); i++) {
            if (aMesh.getVertices(i).getX() > width)
                width = aMesh.getVertices(i).getX();
            if (aMesh.getVertices(i).getY() > height)
                height = aMesh.getVertices(i).getY();
        }

        maxX = width;
        maxY = height;
    }
}
