package ca.mcmaster.cas.se2aa4.a3.island.elevation;

import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a3.island.MeshDimension;

public class Volcano extends Elevation {
    
    
    public Volcano(){
        

        tier1 = Structs.Property.newBuilder()
            .setKey("rgb_color")
            .setValue("255,0,0")
            .build();
        tier2 = Structs.Property.newBuilder()
            .setKey("rgb_color")
            .setValue("32,32,32")
            .build();
        tier3 = Structs.Property.newBuilder()
            .setKey("rgb_color")
            .setValue("64,64,64")
            .build();
        tier4 = Structs.Property.newBuilder()
            .setKey("rgb_color")
            .setValue("96,96,96")
            .build();

    }

    @Override
    public int highestPeak(Structs.Mesh mesh){
        
        Random rand = new Random();
        int p = rand.nextInt(mesh.getPolygonsCount());

        while(mesh.getPolygons(p).getProperties(0).getValue() != "122,171,135")
            p = rand.nextInt(mesh.getPolygonsCount());
    

        return p;
        
    }

    public Structs.Mesh build(Structs.Mesh mesh) {
        return super.build(mesh);
    }


}
