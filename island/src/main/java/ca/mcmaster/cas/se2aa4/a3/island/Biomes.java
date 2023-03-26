package ca.mcmaster.cas.se2aa4.a3.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.Properties.Properties;

public class Biomes {
    private Structs.Mesh aMesh;

    public Biomes(Structs.Mesh iMesh) {
        this.aMesh = iMesh;
    }

    public Structs.Mesh enrichBiomes() {
        Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder();
        iMesh.addAllVertices(aMesh.getVerticesList());
        iMesh.addAllSegments(aMesh.getSegmentsList());
        iMesh.addAllPolygons(aMesh.getPolygonsList());
        Structs.Property forest = Properties.getForestProps();
        Structs.Property land = Properties.getLandProps();
        Structs.Property tundra = Properties.getTundraProps();

        for (int i = 0; i < aMesh.getPolygonsCount(); i++) {
            Structs.Polygon.Builder p = Structs.Polygon.newBuilder(iMesh.getPolygons(i));

            if (p.getProperties(2).getValue() == "90" && p.getProperties(0).getValue() != Properties.lakeColors) {
                p.setProperties(0, forest);
                
            }

            if (p.getProperties(2).getValue() == "60" && p.getProperties(3).getValue() == "15") {
                p.setProperties(0, tundra);
                
            }

            else if (p.getProperties(2).getValue() == "60"
                    && (p.getProperties(0).getValue() == Properties.beachColors ||
                            p.getProperties(0).getValue() == Properties.landColors)) {
                p.setProperties(0, land);
            }

            iMesh.setPolygons(i, p);

        }

        return iMesh.build();
    }

}
