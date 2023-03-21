package ca.mcmaster.cas.se2aa4.a3.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class Beaches {
    private Structs.Mesh aMesh;

    public Beaches(Structs.Mesh iMesh) {
        this.aMesh = iMesh;
    }

    public Structs.Mesh enrichBeaches() {
        Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder();
        iMesh.addAllVertices(aMesh.getVerticesList());
        iMesh.addAllSegments(aMesh.getSegmentsList());

        Structs.Property sand = Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue("194,178,128")
                .build();

        for (Structs.Polygon poly : aMesh.getPolygonsList()) {
            Structs.Polygon.Builder x = Structs.Polygon.newBuilder(poly);
            for (int idx : poly.getNeighborIdxsList()) {
                Structs.Polygon p = aMesh.getPolygons(idx);
                if (poly.getProperties(0).getValue() == "122,171,135" &&
                        (p.getProperties(0).getValue() == "0,0,100" || p.getProperties(0).getValue() == "0,0,55"))
                    x.setProperties(0, sand);
                iMesh.addPolygons(x);
            }
        }
        return iMesh.build();
    }
}
