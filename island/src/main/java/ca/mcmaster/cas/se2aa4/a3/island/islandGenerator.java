package ca.mcmaster.cas.se2aa4.a3.island;


import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.lagoon.Lagoon;
import ca.mcmaster.cas.se2aa4.a3.island.shape.Circle;
import ca.mcmaster.cas.se2aa4.a3.island.shape.Shape;


public class islandGenerator {
    private Structs.Mesh aMesh;

    public islandGenerator() throws IOException {
        aMesh = new MeshFactory().read("img/irregular.mesh");
    }

    public Structs.Mesh generate() throws IOException {

        CenterMesh dim = new CenterMesh(aMesh);
        Shape iMesh = new Circle(dim.maxX, dim.maxY);
        Structs.Mesh mesh = iMesh.build(aMesh);
        Lagoon lMesh = new Lagoon(mesh);
        mesh = lMesh.build();
        Beaches sMesh = new Beaches(mesh);
        return sMesh.enrichBeaches();
        


    }


}
