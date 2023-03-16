package ca.mcmaster.cas.se2aa4.a3.island;



import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class CenterMesh {
    private Structs.Mesh aMesh;
    public double minX;
    public double maxX;
    public double minY;
    public double maxY;

    public CenterMesh(Structs.Mesh mesh) {
        this.aMesh = mesh;
        centeredMeshDimensions();
    }

    public void centeredMeshDimensions() {
        // Initialize max with first element in the array
        double width = aMesh.getVertices(0).getX();
        double height = aMesh.getVertices(0).getY();

        for(int i=0; i < aMesh.getVerticesCount(); i++){
            if (aMesh.getVertices(i).getX() > width) width = aMesh.getVertices(i).getX();
            if (aMesh.getVertices(i).getY() > height) height = aMesh.getVertices(i).getY();
        }

        minX = width*0.20;
        maxX = width*0.80;
        minY = height*0.20;
        maxY = height*0.80;
    }
}
