package ca.mcmaster.cas.se2aa4.a3.island;

import java.io.IOException;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.shape.Circle;
import ca.mcmaster.cas.se2aa4.a3.island.shape.Square;
import ca.mcmaster.cas.se2aa4.a3.island.shape.TiltedOval;
import ca.mcmaster.cas.se2aa4.a3.island.shape.Shape;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.Volcano;
import ca.mcmaster.cas.se2aa4.a3.island.lagoon.Lagoon;

public class islandGenerator {
    private Structs.Mesh aMesh;

    public islandGenerator() throws IOException {
        aMesh = new MeshFactory().read("img/irregular.mesh");
    }

    public Structs.Mesh generate(String shape) throws IOException {
        MeshDimension dim = new MeshDimension(aMesh); // finds mesh dimensions
        Shape iMesh = null;

        if (shape.equals("square"))
            iMesh = new Square(dim.maxX, dim.maxY); // Creates square island and passes mesh dimension
        else if (shape.equals("circle"))
            iMesh = new Circle(dim.maxX, dim.maxY);
        else if (shape.equals("oval"))
            iMesh = new TiltedOval(dim.maxX, dim.maxY);

        if (iMesh == null)
            throw new IllegalArgumentException("Unknown shape: " + shape);

        Structs.Mesh mesh = iMesh.build(aMesh); // Calls build function from Shape 
        mesh = new Lagoon(mesh).build(); // adds lagoon to mesh
        mesh = new Beaches(mesh).enrichBeaches(); // adds beacehs to mesh
        mesh = new Aquifers(mesh).enrichAquifers(); //adds aquifer
        mesh = new Volcano().build(mesh);
        return mesh; // returns the mesh
    }
}
