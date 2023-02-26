package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.lang.Double.compare;

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

        // Random bag = new Random();
        // int tempx = 0;
        // int tempy = 0;
        // for (int x = 0; x < width; x += square_size) {
        // for (int y = 0; y < height; y += square_size) {
        // tempx += bag.nextInt(20)+10;
        // tempy += bag.nextInt(20)+10;
        // mesh.addVertex(tempx, tempy);
        // }
        // }

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


        //=======================================================================================================================
        // Distribute the average of each color randomly and also give the segments thickness values
        // ArrayList<Segment> edgesWithProps = new ArrayList<>();
        // for (Segment e : mesh.getSegments()) {
        //     int avgRed;
        //     int avgGreen;
        //     int avgBlue;

        //     Vertex v1 = verticesWithProps.get(e.getV1Idx());
        //     Vertex v2 = verticesWithProps.get(e.getV2Idx());
        //     String v1_colorCode = "";
        //     String v2_colorCode = "";

        //     // List stores the properties of the vertices
        //     List<Property> v1_PropsList = v1.getPropertiesList();
        //     List<Property> v2_PropsList = v2.getPropertiesList();

        //     // Extracts the color props
        //     for (int i = 0; i < v1_PropsList.size(); i++) {
        //         if (v1_PropsList.get(i).getKey().equals("rgb_color")) {
        //             v1_colorCode = v1_PropsList.get(i).getValue();
        //         }
        //     }
        //     for (int i = 0; i < v2_PropsList.size(); i++) {
        //         if (v2_PropsList.get(i).getKey().equals("rgb_color")) {
        //             v2_colorCode = v2_PropsList.get(i).getValue();
        //         }
        //     }

        //     // Extracting the colors
        //     String[] v1_colors = v1_colorCode.split(",");
        //     String[] v2_colors = v2_colorCode.split(",");

        //     int v1_red = Integer.parseInt(v1_colors[0]);
        //     int v1_green = Integer.parseInt(v1_colors[1]);
        //     int v1_blue = Integer.parseInt(v1_colors[2]);
        //     int v2_red = Integer.parseInt(v2_colors[0]);
        //     int v2_green = Integer.parseInt(v2_colors[1]);
        //     int v2_blue = Integer.parseInt(v2_colors[2]);

        //     avgRed = (v1_red + v2_red) / 2;
        //     avgGreen = (v1_green + v2_green) / 2;
        //     avgBlue = (v1_blue + v2_blue) / 2;

        //     String colorCode = avgRed + "," + avgGreen + "," + avgBlue;
        //     Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
        //     String thicknessVal = String.valueOf(bag.nextInt(5) + 1);
        //     Property thickness = Property.newBuilder().setKey("thickness").setValue(thicknessVal).build();
        //     Segment segmentProps = Segment.newBuilder(e).addProperties(color).addProperties(thickness).build();

        //     edgesWithProps.add(segmentProps);
        // }



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
        IrregularMesh diagram = new IrregularMesh();
        ArrayList<ArrayList<ArrayList<Double>>> voronoiPoly = diagram.voronoiDiagram();
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Segment> segments = new ArrayList<>();
        ArrayList<Polygon> polygons = new ArrayList<>();


        for (int i = 0; i < voronoiPoly.size(); i++){
            for (int j = 0; j < voronoiPoly.get(i).size(); j++){
                boolean contains = false;
                Double x = voronoiPoly.get(i).get(j).get(0);
                Double y = voronoiPoly.get(i).get(j).get(1);

                if (vertices.size() == 0){
                    vertices.add(Vertex.newBuilder().setX(x).setY(y).build());
                    contains = true;
                }
                else{
                    for (Vertex v : vertices){
                        if ((compare(x,v.getX()) == 0) && (compare(y,v.getY()) == 0)){
                        }
                    }
                    if (contains == false) {
                        //System.out.println("Adding new");
                        vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).build());
                    }
                }
            }
        }

        //list of segments
        for (int i = 0; i < voronoiPoly.size(); i++){
            // System.out.println("Starting");
            // System.out.println(voronoiPoly.size());
            // System.out.println("\n\nPolygon "+i);
            ArrayList<Integer> segIdxs = new ArrayList<Integer>(voronoiPoly.get(i).size());

            for (int j = 0; j < (voronoiPoly.get(i).size() - 1); j++){

                Double x1 = voronoiPoly.get(i).get(j).get(0);
                Double y1 = voronoiPoly.get(i).get(j).get(1);
                Double x2 = voronoiPoly.get(i).get(j+1).get(0);
                Double y2 = voronoiPoly.get(i).get(j+1).get(1);

                int v1Idx = 0;
                int v2Idx = 0;
                int segIdx = 0;

                for (int a = 0; a < vertices.size(); a++){
                    Vertex vert = vertices.get(a);
                    Double vertX = vert.getX();
                    Double vertY = vert.getY();

                    if ((compare((double) vertX, (double) x1) == 0) && (compare((double) vertY, (double) y1) == 0)){
                        v1Idx = a;
                    }
                    if ((compare(vertX,x2) == 0) && (compare(vertY,y2) == 0)){
                        v2Idx = a;
                    }
                }

                boolean contains = false;
                if (segments.size() == 0){
                    segments.add(Segment.newBuilder().setV1Idx(v1Idx).setV2Idx(v2Idx).build());
                    segIdx = 0;
                    segIdxs.add(segIdx);
                }
                else{
                    for (int k = 0; k < segments.size(); k++){
                        Segment seg = segments.get(k);
                        if (((seg.getV1Idx() == v1Idx) && (seg.getV2Idx() == v2Idx)) || ((seg.getV2Idx() == v1Idx) && (seg.getV1Idx()) == v2Idx)){
                            contains = true;
                            segIdx = k;
                            segIdxs.add(segIdx);
                        }
                    }
                    if (contains == false) {
                        segIdx = segments.size();
                        segIdxs.add(segIdx);
                        segments.add(Segment.newBuilder().setV1Idx(v1Idx).setV2Idx(v2Idx).build());

                    }
                }

                //add polygons segments
                // for (Segment s : segments){
                //     System.out.println(s.getV1Idx() +" "+ s.getV2Idx());
                // }
            }
            polygons.add(Polygon.newBuilder().setCentroidIdx(i).addAllSegmentIdxs(segIdxs).build());
        }


        Random bag = new Random();
        ArrayList<Vertex> points = new ArrayList<>();

        //Distribute points colours and thickness (red, 3).
        ArrayList<Vertex> pointsWithProps = new ArrayList<>();
        int pointThicknessNumber = 3;
        for(Vertex p: points) {
            Property color = Property.newBuilder().setKey("rgb_color").setValue("255,0,0,").build();
            String pointThicknessValue = String.valueOf(pointThicknessNumber);
            Property pointThickness = Property.newBuilder().setKey("thickness").setValue(pointThicknessValue).build();
            Vertex pointProps = Vertex.newBuilder(p).addProperties(color).addProperties(pointThickness).build();
            pointsWithProps.add(pointProps);
        }

        //Distribute points colours and thickness (red, 3).
        ArrayList<Vertex> verticesWithProps = new ArrayList<>();
        int vertexThicknessNumber = 3;
        for(Vertex v: vertices) {
            Property color = Property.newBuilder().setKey("rgb_color").setValue("0,0,0,").build();
            String vertexThicknessValue = String.valueOf(vertexThicknessNumber);
            Property vertexThickness = Property.newBuilder().setKey("thickness").setValue(vertexThicknessValue).build();
            Vertex withProperties = Vertex.newBuilder(v).addProperties(color).addProperties(vertexThickness).build();
            verticesWithProps.add(withProperties);
        }


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

        return Mesh.newBuilder().addAllVertices(verticesWithProps).addAllSegments(edgesWithProps).addAllVertices(pointsWithProps).build();
    }

}