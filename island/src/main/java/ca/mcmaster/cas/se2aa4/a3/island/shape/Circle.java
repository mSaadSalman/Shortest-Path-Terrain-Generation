package ca.mcmaster.cas.se2aa4.a3.island.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class Circle extends Shape {
    
    private double centerX;
    private double centerY; 
    private double radius;

    public Circle(double maxX, double maxY){
        
        this.centerX = maxX/2;
        this.centerY = maxY/2;
        this.radius = maxX*0.375;
        land = Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue("124,252,0")
                .build();
        ocean = Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue("0,0,55")
                .build();
    }

    public boolean contains(Structs.Vertex v) {

        return (Math.sqrt(Math.pow(v.getX() - centerX, 2) + Math.pow(v.getY() - centerY, 2))) <= radius;
    }

    public Structs.Mesh build(Structs.Mesh aMesh){
        return super.build(aMesh);
    }

}
