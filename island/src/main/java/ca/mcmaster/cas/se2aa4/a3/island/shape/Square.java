package ca.mcmaster.cas.se2aa4.a3.island.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class Square extends Shape {
    private double maxX;
    private double maxY;

    public Square(double maxX, double maxY) {
        this.maxX = maxX;
        this.maxY = maxY;

        land = Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue("122,171,135")
                .build();
        ocean = Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue("0,0,55")
                .build();
    }

    public boolean contains(Structs.Vertex vertex) {
        return vertex.getX() >= maxX * 0.15 && vertex.getX() <= maxX * 0.85 &&
                vertex.getY() >= maxY * 0.15 && vertex.getY() <= maxY * 0.85;
    }

    public Structs.Mesh build(Structs.Mesh aMesh) {
        return super.build(aMesh);
    }
}
