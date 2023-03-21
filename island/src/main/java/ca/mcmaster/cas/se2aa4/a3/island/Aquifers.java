package ca.mcmaster.cas.se2aa4.a3.island;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import java.util.Random;


public class Aquifers {
    private Structs.Mesh aMesh;

    public Aquifers(Structs.Mesh iMesh) {
        this.aMesh = iMesh;
    }

    public Structs.Mesh enrichAquifers() {
        int val =20;
        Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder();
        iMesh.addAllVertices(aMesh.getVerticesList());
        iMesh.addAllSegments(aMesh.getSegmentsList());

        Structs.Property aqua = Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue("100,40,0")
                .build();

        int poly_size = aMesh.getPolygonsCount();
        Random rand= new Random();

        for (Structs.Polygon poly : aMesh.getPolygonsList()) {
            Structs.Polygon.Builder x = Structs.Polygon.newBuilder(poly);
            for(int i =0; i<=val;i++){
                int ran_index = rand.nextInt(poly_size);
                x.setProperties(0, aqua);
                iMesh.addPolygons(x);
            }
        }
        return iMesh.build();
    }
}
