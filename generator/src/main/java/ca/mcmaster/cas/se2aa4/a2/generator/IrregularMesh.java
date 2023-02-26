package ca.mcmaster.cas.se2aa4.a2.generator;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class IrregularMesh {
    private final int width = 500;
    private final int height = 500;
    public List<Coordinate> randomCoords = new ArrayList<>();
    public List<Polygon> polygons = new ArrayList<>();

    // Function to generate a Voronoi diagram and return the polygon coordinates (needed for the irreg mesh in the other file)
    public ArrayList<ArrayList<ArrayList<Double>>> voronoiDiagram() {
        // Number of random points to generate (what happens if u generate more?)
        int numPoints = 120;
        Random bag = new Random();
        // Create an ArrayList to store the polygon coordinates
        ArrayList<ArrayList<ArrayList<Double>>> polygonCoords = new ArrayList<>(numPoints);
    
         // Generate random points within the range of the width and height
        for (int i = 0; i < numPoints; i++) {
            int randX = bag.nextInt(width);
            int randY = bag.nextInt(height);
            randomCoords.add(new Coordinate(randX, randY));
        }
    
        // GeometryFactory to generate the Voronoi diagram
        GeometryFactory geomFact = new GeometryFactory();


        VoronoiDiagramBuilder voronoi = new VoronoiDiagramBuilder();
        // set the random points as the sites
        voronoi.setSites(randomCoords);

        // clipping envelope is size of the width and height
        Envelope clipEnv = new Envelope(0.0, width, 0.0, height);
        voronoi.setClipEnvelope(clipEnv);

        // Generates Voronoi diagram
        Geometry diagram = voronoi.getDiagram(geomFact);
    
        // If the Voronoi diagram contains multiple polygons, extract their coords
        if (diagram instanceof GeometryCollection) {
            GeometryCollection geometryCollection = (GeometryCollection) diagram;
            for (int i = 0; i < geometryCollection.getNumGeometries(); i++) {
                Polygon p = (Polygon) geometryCollection.getGeometryN(i);
                Coordinate[] coords = p.getCoordinates();
                ArrayList<ArrayList<Double>> coordinates = new ArrayList<>(coords.length);
                for (int j = 0; j < coords.length; j++) {
                    Double x = coords[j].getX();
                    Double y = coords[j].getY();
                    ArrayList<Double> point = new ArrayList<>();
                    point.add(x);
                    point.add(y);
                    coordinates.add(point);
                }
                // Add the list of points to the polygon coordinates ArrayList
                polygonCoords.add(coordinates);

                // Add the polygon to the list of polygons
                polygons.add(p);
            }
        }
        return polygonCoords;
    }
}
