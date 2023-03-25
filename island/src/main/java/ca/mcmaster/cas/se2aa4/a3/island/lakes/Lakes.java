package ca.mcmaster.cas.se2aa4.a3.island.lakes;

import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.Properties.Properties;

public class Lakes {
    private Structs.Mesh aMesh;
    private Random random;

    public Lakes(Structs.Mesh iMesh) {
        this.aMesh = iMesh;
        random = new Random();
    }

    public Structs.Mesh generateLakes(int numLakes) {
        Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder();
        iMesh.addAllVertices(aMesh.getVerticesList());
        iMesh.addAllSegments(aMesh.getSegmentsList());
        iMesh.addAllPolygons(aMesh.getPolygonsList());

        Structs.Property lakes = Properties.getLakeProps();

        if (numLakes > aMesh.getPolygonsCount()) {
            throw new IllegalArgumentException("Not possible to have more lakes than polygons");
        }

        int randomPolyIdx = random.nextInt(aMesh.getPolygonsCount());
        while (numLakes > 0) {
            if (aMesh.getPolygons(randomPolyIdx).getProperties(0).getValue() == Properties.landColors) {
                Structs.Polygon randomPoly = aMesh.getPolygons(randomPolyIdx); // Get polygon at the random index
                Structs.Polygon.Builder p = Structs.Polygon.newBuilder(randomPoly);
                p.setProperties(0, lakes);
                p.addProperties(Properties.getLakeHumidityProps());
                numLakes--;
                iMesh.setPolygons(randomPolyIdx, p);
            }
            randomPolyIdx = random.nextInt(aMesh.getPolygonsCount());
        }

        return iMesh.build();
    }
}