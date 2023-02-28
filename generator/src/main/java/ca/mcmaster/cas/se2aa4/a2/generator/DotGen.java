package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;
import org.locationtech.jts.geom.GeometryFactory;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;
    private final int precision = 2;

    MeshADT mesh = new MeshADT(precision);

    public Mesh generateGridMesh() {

        // Create all the vertices
        for (int x = 0; x < width; x += square_size) {
            for (int y = 0; y < height; y += square_size) {
                mesh.addVertex(x, y);
            }
        }

        // Distribute colors randomly. Vertices are immutable, need to enrich them
        ArrayList<Vertex> verticesWithProps = new ArrayList<>();
        Random bag = new Random();
        for (Vertex v : mesh.getVertexs()) {
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);

            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            String thicknessVal = String.valueOf(bag.nextInt(8) + 1);
            Property thickness = Property.newBuilder().setKey("thickness").setValue(thicknessVal).build();
            Vertex verticesProps = Vertex.newBuilder(v).addProperties(color).addProperties(thickness).build();
            verticesWithProps.add(verticesProps);
        }

        // Create all the edges
        for (int i = 0; i < mesh.getVertexs().size() - 25; i += width / square_size) {
            for (int j = i; j < height / square_size + i; j += 1)
                mesh.addSegment(j, j + 1);
            for (int k = i; k < height / square_size + i; k += 1)
                mesh.addSegment(k, k + (height / square_size));
        }
        for (int j = mesh.getVertexs().size() - 25; j < mesh.getVertexs().size() - 1; j += 1)
            mesh.addSegment(j, j + 1);

        // Distribute the average of each color randomly and also give the segments thickness values
        ArrayList<Segment> edgesWithProps = new ArrayList<>();
        for (Segment e : mesh.getSegments()) {
            // Get the vertices and their properties
            Vertex v1 = verticesWithProps.get(e.getV1Idx());
            Vertex v2 = verticesWithProps.get(e.getV2Idx());
            List<Property> v1PropsList = v1.getPropertiesList();
            List<Property> v2PropsList = v2.getPropertiesList();

            // Extract the RGB color values from the vertices' properties
            int[] v1RGB = new int[3];
            int[] v2RGB = new int[3];
            for (int i = 0; i < v1PropsList.size(); i++) {
                Property prop = v1PropsList.get(i);
                if (prop.getKey().equals("rgb_color")) {
                    String[] rgbValues = prop.getValue().split(",");
                    for (int j = 0; j < 3; j++) {
                        v1RGB[j] = Integer.parseInt(rgbValues[j]);
                    }
                }
            }
            for (int i = 0; i < v2PropsList.size(); i++) {
                Property prop = v2PropsList.get(i);
                if (prop.getKey().equals("rgb_color")) {
                    String[] rgbValues = prop.getValue().split(",");
                    for (int j = 0; j < 3; j++) {
                        v2RGB[j] = Integer.parseInt(rgbValues[j]);
                    }
                }
            }

            // Calculate the average RGB color values
            int[] avgRGB = new int[3];
            for (int i = 0; i < 3; i++) {
                avgRGB[i] = (v1RGB[i] + v2RGB[i]) / 2;
            }
            String colorCode = avgRGB[0] + "," + avgRGB[1] + "," + avgRGB[2];

            // Generate a random thickness value between 1 and 5 (inclusive)
            String thicknessVal = String.valueOf(bag.nextInt(5) + 1);

            // Build the segment with the color and thickness properties
            Segment segmentProps = Segment.newBuilder(e)
                    .addProperties(Property.newBuilder().setKey("rgb_color").setValue(colorCode).build())
                    .addProperties(Property.newBuilder().setKey("thickness").setValue(thicknessVal).build())
                    .build();

            edgesWithProps.add(segmentProps);
        }



        // creating all the centroids
        ArrayList<Vertex> centroids = new ArrayList<>();
        for (int i = 0; i < width; i += square_size) {
            for (int j = 0; j < height; j += square_size) {
                double x = i + (square_size / 2);
                double y = j + (square_size / 2);
                centroids.add(Vertex.newBuilder().setX(x).setY(y).build());
            }
        }

        ArrayList<Vertex> centroidsWithProps = new ArrayList<>();
        for (Vertex c : centroids) {
            String colorCode = 255 + "," + 0 + "," + 0;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex centroidProps = Vertex.newBuilder(c).addProperties(color).build();
            verticesWithProps.add(centroidProps);
        }

        // create polygons
        for (int i = 0, j = 0; i < mesh.getSegments().size() - 25; i += width / square_size, j++) {
            for (int k = i; k < height / square_size + i; k += 1)
                mesh.addPolygon(k, k + (width / square_size) - 1, k + (2 * (width / square_size)) - 1,
                        k + (width / square_size), j);
        }

        return Mesh.newBuilder()
                .addAllVertices(verticesWithProps)
                .addAllSegments(edgesWithProps)
                .addAllVertices(centroidsWithProps)
                .addAllPolygons(mesh.getPolygons())
                .build();
    }





    public Mesh generateIrregularMesh() {
        Random bag = new Random();
        IrregularMesh irregularMesh = new IrregularMesh();
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Segment> segments = new ArrayList<>();
        ArrayList<Polygon> poly = new ArrayList<>();
        

        //Create random points
        int numVertices = 100;
        for (int i = 0; i < numVertices; i++) {
            irregularMesh.addRandPts(bag.nextInt(width), bag.nextInt(height));
        }

        //Creates the voronoi diagram
        irregularMesh.voronoiDiagram(width, height);
        ArrayList<org.locationtech.jts.geom.Polygon> polygons = irregularMesh.getPolygons();

        //Adding the vertices of the polygons to the Vertices struct
        for(int i=0; i<polygons.size(); i++){
            for(int j=0; j<polygons.get(i).getNumPoints(); j++){
                Coordinate[] coords = (polygons.get(i).getCoordinates());
                vertices.add(Vertex.newBuilder().setX(coords[0].x).setY(coords[0].y).build());
            }
             
        }

        System.out.println(polygons.get(0));
        System.out.println(polygons.get(0).getCentroid());


        //Create Segments
        for (int i = 0; i < polygons.size(); i++) {
            // Store info on indcies of segments that form polygon
            ArrayList<Integer> segIdxs = new ArrayList<>();

            // Iterate through each vertex of polygon
            for (int j = 0; j < (polygons.get(i).getNumPoints() - 1); j++) {
                double x1 = polygons.get(i).getCoordinates()[j].x;
                double y1 = polygons.get(i).getCoordinates()[j].y;
                double x2 = polygons.get(i).getCoordinates()[j+1].x;
                double y2 = polygons.get(i).getCoordinates()[j+1].y;

                // if vertex not found, new vertex object created
                int v1Idx = vertices.indexOf(Vertex.newBuilder().setX((double) x1).setY((double) y1).build());
                int v2Idx = vertices.indexOf(Vertex.newBuilder().setX((double) x2).setY((double) y2).build());
                if (v1Idx == -1) {
                    vertices.add(Vertex.newBuilder().setX((double) x1).setY((double) y1).build());
                    v1Idx = vertices.size() - 1;
                }
                if (v2Idx == -1) {
                    vertices.add(Vertex.newBuilder().setX((double) x2).setY((double) y2).build());
                    v2Idx = vertices.size() - 1;
                }

                // search for segments that connect 2 vertices. new segment created if not found
                int segIdx = segments.indexOf(Segment.newBuilder().setV1Idx(v1Idx).setV2Idx(v2Idx).build());
                if (segIdx == -1) {
                    segIdx = segments.size();
                    segments.add(Segment.newBuilder().setV1Idx(v1Idx).setV2Idx(v2Idx).build());
                }
                segIdxs.add(segIdx);
            }
            poly.add(Polygon.newBuilder().setCentroidIdx(i).addAllSegmentIdxs(segIdxs).build());
        }

        ArrayList<Vertex> verticesWithProps = new ArrayList<>();
        for (Vertex v : vertices) {
            String colorCode = 0 + "," + 0 + "," + 0;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex verticesProps = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithProps.add(verticesProps);
        }

        // Determine colors of segments based on average of its vertices
        ArrayList<Segment> edgesWithProps = new ArrayList<>();
        for (Segment s : segments) {
            String colorCode = 0 + "," + 0 + "," + 0;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Segment segmentProps = Segment.newBuilder(s).addProperties(color).build();
            edgesWithProps.add(segmentProps);
        }
        
        return Mesh.newBuilder().addAllVertices(vertices).addAllSegments(edgesWithProps).build();
    }
}