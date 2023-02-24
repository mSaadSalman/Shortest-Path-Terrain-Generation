package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.ArrayList;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

public class MeshADT {
    
    private final int precision;
    private final ArrayList<Vertex> vertices;
    private final ArrayList<Segment> segments;
    private final ArrayList<Polygon> polygons;

    public MeshADT(int precision){
        this.precision = precision;
        this.vertices = new ArrayList<>();
        this.segments = new ArrayList<>();
        this.polygons = new ArrayList<>();
    }

    private double round(double x){
        return Math.round(x * Math.pow(10, precision)) / Math.pow(10, precision);
    }

    public void addVertex(double x, double y){
        Vertex v = Vertex.newBuilder().setX(round((double) x)).setY(round((double) y)).build();
        
        vertices.add(v);
        
    }

    public ArrayList<Vertex> getVertexs(){
        return vertices;
    }

    public void addSegment(int v1idx, int v2idx){
        Segment e = Segment.newBuilder().setV1Idx(v1idx).setV2Idx(v2idx).build();
        
        segments.add(e);
        
    } 

    public ArrayList<Segment> getSegments(){
        return segments;
    }

    



}
