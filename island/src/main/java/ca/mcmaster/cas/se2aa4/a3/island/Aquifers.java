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
            if (val==0){
                break;
            }
            Structs.Polygon temp= aMesh.getPolygons(rand.nextInt(poly_size));

            if (temp.getProperties(0).getValue() == "0,0,100"||
            temp.getProperties(0).getValue() == "0,0,55"){
            Structs.Polygon.Builder x = Structs.Polygon.newBuilder(temp);
            x.setProperties(0, aqua);
            iMesh.addPolygons(x);
            val--;
            }

            
        }

        



        return iMesh.build();
    }
}
