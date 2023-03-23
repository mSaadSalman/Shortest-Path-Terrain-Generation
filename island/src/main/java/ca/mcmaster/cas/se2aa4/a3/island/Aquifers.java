package ca.mcmaster.cas.se2aa4.a3.island;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import java.util.ArrayList;
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

        int poly_size = aMesh.getPolygonsCount();
        Random rand= new Random();

        for (Structs.Polygon poly : aMesh.getPolygonsList()) {
            Structs.Polygon.Builder x = Structs.Polygon.newBuilder(poly);
            if (poly.getProperties(0).getValue() == "122,171,135"||
            poly.getProperties(0).getValue() == "0,0,100"||
            poly.getProperties(0).getValue() == "0,0,55"||poly.getProperties(0).getValue()=="194,178,128"){
                iMesh.addPolygons(x);
            }

        }


        
        for(Structs.Polygon poly : aMesh.getPolygonsList()){
            for (Integer idx : poly.getNeighborIdxsList()) {

            if (val==0){
                break;
            }
            int y = rand.nextInt(poly_size);
            Structs.Polygon temp= aMesh.getPolygons(y);
            Structs.Polygon neigbor = aMesh.getPolygons(idx);

            if (temp.getProperties(0).getValue() == "122,171,135"||
            temp.getProperties(0).getValue() == "194,178,128"){
            Structs.Polygon.Builder x = Structs.Polygon.newBuilder(temp);
            Structs.Polygon.Builder t = Structs.Polygon.newBuilder(neigbor);
            
            x.setProperties(0, aqua);
            iMesh.setPolygons(y, x);
            t.addProperties(moist);


            val--;
            }

            
        }
    }

        



        return iMesh.build();
    }
}
