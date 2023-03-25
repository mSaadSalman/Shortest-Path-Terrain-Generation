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
import ca.mcmaster.cas.se2aa4.a3.island.lakes.Lakes;

public class islandGenerator {
    private Structs.Mesh aMesh;

    public islandGenerator() throws IOException {
        aMesh = new MeshFactory().read("img/irregular.mesh");
    }

    public Structs.Mesh generate(String shape, String mode, String numlakes, String seedStr) throws IOException {
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
        if (mode.equals("lagoon"))
            mesh = new Lagoon(mesh).build(); // adds lagoon to mesh
        mesh = new Beaches(mesh).enrichBeaches(); // adds beacehs to mesh
        mesh = new Aquifers(mesh).enrichAquifers(); //adds aquifer
        mesh = new Lakes(mesh).generateLakes(Integer.parseInt(numlakes));
        mesh = new Volcano().build(mesh);

        // Seed generation
        SeedGen seedGen = new SeedGen();
        long seed = (seedStr == null) ? (seedGen.createSeed()): Long.parseLong(seedStr);
        seedGen.storeMesh(seed, mesh);
        mesh = seedGen.getMesh(seed);
        System.out.println("SEED: " + seed);
        
        return mesh; // returns the mesh
    }
}