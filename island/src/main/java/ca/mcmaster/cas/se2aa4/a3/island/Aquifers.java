package ca.mcmaster.cas.se2aa4.a3.island;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.Properties.Properties;

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
                .setValue("0,0,30")
                .build();

        Structs.Property moist = Structs.Property.newBuilder()
                .setKey("moisture")
                .setValue("1")
                .build();

        Structs.Property moist0 = Structs.Property.newBuilder()
                .setKey("moisture")
                .setValue("0")
                .build();

        int poly_size = aMesh.getPolygonsCount();
        Random rand= new Random();

        for (Structs.Polygon poly : aMesh.getPolygonsList()) {
            Structs.Polygon.Builder x = Structs.Polygon.newBuilder(poly);
            if (poly.getProperties(0).getValue() == Properties.landColors||
            poly.getProperties(0).getValue() == Properties.lagoonColors||
            poly.getProperties(0).getValue() == Properties.oceanColors||poly.getProperties(0).getValue()==Properties.beachColors){
                iMesh.addPolygons(x);
            }

        }

        for(Structs.Polygon poly : aMesh.getPolygonsList()){
            if (val==0){
                break;
            }
            int y = rand.nextInt(poly_size);
            Structs.Polygon temp= aMesh.getPolygons(y);

            if (temp.getProperties(0).getValue() == Properties.landColors||
            temp.getProperties(0).getValue() == Properties.beachColors){
            Structs.Polygon.Builder x = Structs.Polygon.newBuilder(temp);
            x.setProperties(0, aqua);
            iMesh.setPolygons(y, x);
            val--;
            }
        }

        for (int i = 0; i < aMesh.getPolygonsCount(); i++) {
            Structs.Polygon.Builder p = Structs.Polygon.newBuilder(iMesh.getPolygons(i));

            for (int j = 0; j < p.getNeighborIdxsCount(); j++) {
                int neighborIndex = p.getNeighborIdxs(j);
                Structs.Polygon.Builder neighbor = Structs.Polygon.newBuilder(iMesh.getPolygons(neighborIndex));
                

                 if (p.getProperties(0).getValue() == Properties.aquaColors) {
                        neighbor.addProperties(moist);
                        iMesh.setPolygons(neighborIndex, neighbor.build());
                    }

                 else {
                      neighbor.addProperties(moist0);
                     iMesh.setPolygons(neighborIndex, neighbor.build());

                     }  
            }

        }        

        return iMesh.build();
    }
}
