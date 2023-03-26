package ca.mcmaster.cas.se2aa4.a3.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.Properties.Properties;

public class Temp {
    private Structs.Mesh aMesh;

    public Temp(Structs.Mesh iMesh) {
        this.aMesh = iMesh;
    }

    public Structs.Mesh enrichTemp() {
        Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder();
        iMesh.addAllVertices(aMesh.getVerticesList());
        iMesh.addAllSegments(aMesh.getSegmentsList());
        iMesh.addAllPolygons(aMesh.getPolygonsList());

        Structs.Property cold = Properties.get_temp_temperature();
        Structs.Property warm = Properties.get_hot_temperature();

        for (int i = 0; i < aMesh.getPolygonsCount(); i++) {
            Structs.Polygon.Builder p = Structs.Polygon.newBuilder(iMesh.getPolygons(i));

            if (p.getProperties(0).getValue() == Properties.aquaColors) {
                p.addProperties(cold);
            }

             else {
                p.addProperties(warm);
            }
            
            iMesh.setPolygons(i, p);
        }


        return iMesh.build();
    }

    
}
