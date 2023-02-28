package ca.mcmaster.cas.se2aa4.a2.generator;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class IrregularMesh {
    
    public ArrayList<Coordinate> randPts;
    public ArrayList<org.locationtech.jts.geom.Polygon> polygonList;
    

    public IrregularMesh(){
        this.randPts = new ArrayList<>();
        this.polygonList = new ArrayList<>();

    }

    public void addRandPts(int x,int y){
        randPts.add(new Coordinate(x, y));
    }

    public ArrayList<Coordinate> returnRandPts(){
        return randPts;
    }

    private Envelope regionEnvelope(int width, int height){
        return new Envelope(0.0, width, 0.0, height);
    } 

    public void voronoiDiagram(int width, int height){
        
        VoronoiDiagramBuilder buildVoro = new VoronoiDiagramBuilder();
        buildVoro.setSites(randPts);
        buildVoro.setClipEnvelope(regionEnvelope(width,height));

        Geometry pCollection = buildVoro.getDiagram(new GeometryFactory());
        
        if(pCollection instanceof GeometryCollection){
            GeometryCollection gCollection = (GeometryCollection) pCollection;

            for(int i=0; i<gCollection.getNumGeometries(); i++){
                polygonList.add((org.locationtech.jts.geom.Polygon) gCollection.getGeometryN(i));
            }
        }
        
    }

    public ArrayList<org.locationtech.jts.geom.Polygon> getPolygons(){
        return polygonList;
    }
         
    }

