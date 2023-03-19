package ca.mcmaster.cas.se2aa4.a3.island;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.Green;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.Lagoon.Lagoon;
import ca.mcmaster.cas.se2aa4.a3.island.shape.Circle;
import ca.mcmaster.cas.se2aa4.a3.island.shape.Shape;
import ca.mcmaster.cas.se2aa4.a3.island.shape.Square;

public class islandGenerator {
    private Structs.Mesh aMesh;

    public islandGenerator() throws IOException {
        aMesh = new MeshFactory().read("img/irregular.mesh");
    }

    public Structs.Mesh generate() throws IOException {

        CenterMesh dim = new CenterMesh(aMesh);
        Shape shape = new Circle(dim.maxX, dim.maxY);
        Lagoon lagoon= new Lagoon(dim.maxX, dim.maxY);
        return shape.build(aMesh);


    }


}
