package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.ArrayList;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.generator.MeshADT;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;
    private final int precision = 2;

    MeshADT mesh = new MeshADT(precision);

    public Mesh generate() {
        //ArrayList<Vertex> vertices = new ArrayList<>();
        // Create all the vertices
        for (int x = 0; x < width; x += square_size*2) {
            for (int y = 0; y < height; y += square_size*2) {
                mesh.addVertex(x,y);
                mesh.addVertex(x+square_size, y);
                mesh.addVertex(x, y+square_size);
                mesh.addVertex(x+square_size, y+square_size);

                // vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).build());
                // vertices.add(Vertex.newBuilder().setX((double) x + square_size).setY((double) y).build());
                // vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y + square_size).build());
                // vertices.add(Vertex.newBuilder().setX((double) x + square_size).setY((double) y + square_size).build());

            }
        }
        // Distribute colors randomly. Vertices are immutable, need to enrich them
        ArrayList<Vertex> verticesWithColors = new ArrayList<>();
        Random bag = new Random();
        for (Vertex v : mesh.getVertexs()) {
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
        }

        // Create all the edges
        for (int i = 0; i < mesh.getVertexs().size() - 2; i+=2) {
            mesh.addSegment(i, i+1);
            mesh.addSegment(i, i+2);
            mesh.addSegment(i+1, i+3);
            mesh.addSegment(i+2, i+3);
            if(mesh.getVertexs().size() - (((width/square_size)*2)+2) > i){ //hard coded .. find the pattern
                mesh.addSegment(i+1, i+((width/square_size)*2)+2);
                mesh.addSegment(i+3, i+((width/square_size)*2)+2);
            }
            // edges.add(Segment.newBuilder().setV1Idx(i).setV2Idx(i + 1).build());
            // edges.add(Segment.newBuilder().setV1Idx(i).setV2Idx(i + 2).build());
            // edges.add(Segment.newBuilder().setV1Idx(i + 1).setV2Idx(i + 3).build());
            // edges.add(Segment.newBuilder().setV1Idx(i + 2).setV2Idx(i + 3).build());
        }

        // Distribute the average of each color randomly
        ArrayList<Segment> edgesWithColors = new ArrayList<>();
        for (Segment e : mesh.getSegments()) {
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            int avgRed = red / 2;
            int avgGreen = green / 2;
            int avgBlue = blue / 2;
            String colorCode = avgRed + "," + avgGreen + "," + avgBlue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Segment colored = Segment.newBuilder(e).addProperties(color).build();
            edgesWithColors.add(colored);
        }

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(edgesWithColors).build();
    }

}