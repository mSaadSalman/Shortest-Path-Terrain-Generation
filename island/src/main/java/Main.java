import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.protobuf.Struct;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a3.island.islandGenerator;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;

public class Main {
    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration(args);
        Structs.Mesh iMesh = new islandGenerator().LandPoly();
        new MeshFactory().write(iMesh, config.output());

        
    }
}
