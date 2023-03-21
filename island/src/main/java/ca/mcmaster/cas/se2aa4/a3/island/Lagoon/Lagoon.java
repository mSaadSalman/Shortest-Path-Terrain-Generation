package ca.mcmaster.cas.se2aa4.a3.island.lagoon;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.MeshDimension;

public class Lagoon {
    private Structs.Mesh aMesh;
    private double centerX;
    private double centerY;
    private double radius;

    public Lagoon(Structs.Mesh iMesh) {
        this.aMesh = iMesh;
        MeshDimension dim = new MeshDimension(iMesh);
        this.centerX = dim.maxX / 2;
        this.centerY = dim.maxY / 2;
        this.radius = (dim.maxX > dim.maxY) ? dim.maxY * 0.2 : dim.maxX * 0.2;
    }

    public Structs.Mesh build() {
        Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder();
        iMesh.addAllVertices(aMesh.getVerticesList());
        iMesh.addAllSegments(aMesh.getSegmentsList());

        Structs.Property lagoon = Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue("0,0,100")
                .build();

        for (Structs.Polygon poly : aMesh.getPolygonsList()) {
            Structs.Vertex centroid = aMesh.getVertices(poly.getCentroidIdx());
            Structs.Polygon.Builder p = Structs.Polygon.newBuilder(poly);
            if (Math.sqrt(Math.pow(centroid.getX() - centerX, 2) + Math.pow(centroid.getY() - centerY, 2)) <= radius)
                p.setProperties(0, lagoon);
            iMesh.addPolygons(p);
        }
        return iMesh.build();
    }
}
