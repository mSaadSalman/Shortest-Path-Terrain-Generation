package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties.ColorProperty;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class GraphicRenderer implements Renderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.2f);
        canvas.setStroke(stroke);
        drawPolygons(aMesh,canvas);
        drawNeighbourhood(aMesh,canvas);
        drawCentroids(aMesh,canvas);

    }

    private void drawPolygons(Mesh aMesh, Graphics2D canvas) {
        for(Structs.Polygon p: aMesh.getPolygonsList()){
            drawAPolygon(p, aMesh, canvas);
        }
    }

    private void drawCentroids(Structs.Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.DARK_GRAY);

        for (int i = 0; i < aMesh.getVerticesCount(); i++) {
            Structs.Vertex.Builder p = Structs.Vertex.newBuilder(aMesh.getVertices(i));
            if(p.getProperties(0).getValue().equals("100")){
                Ellipse2D circle = new Ellipse2D.Float((float) p.getX()-1.5f, (float) p.getY()-1.5f,
                        7, 7);
                canvas.fill(circle);
            }
            if(p.getProperties(0).getValue().equals("500")){
                Ellipse2D circle = new Ellipse2D.Float((float) p.getX()-1.5f, (float) p.getY()-1.5f,
                        10, 10);
                canvas.fill(circle);
            }
            if(p.getProperties(0).getValue().equals("1000")){
                Ellipse2D circle = new Ellipse2D.Float((float) p.getX()-1.5f, (float) p.getY()-1.5f,
                        18, 18);
                canvas.fill(circle);
            }

            if(p.getProperties(0).getValue().equals("10000")){
                Ellipse2D circle = new Ellipse2D.Float((float) p.getX()-1.5f, (float) p.getY()-1.5f,
                        25, 25);
                canvas.fill(circle);
            }
        }
    }

    private void drawNeighbourhood(Structs.Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.LIGHT_GRAY);
        Stroke stroke = new BasicStroke(5);
        canvas.setStroke(stroke);

        Set<Set<Structs.Polygon>> drawn = new HashSet<>();
        for(Structs.Polygon p: aMesh.getPolygonsList()){
            Structs.Polygon.Builder poll = Structs.Polygon.newBuilder(p);
            Structs.Vertex centroid = aMesh.getVertices(p.getCentroidIdx());

            if(poll.getProperties(5).getValue().equals("1")) {
            for(Integer neigbourIdx: p.getNeighborIdxsList()) {
                Structs.Polygon neighbour = aMesh.getPolygons(neigbourIdx);
                Structs.Vertex neighbourCentroid = aMesh.getVertices(neighbour.getCentroidIdx());


                if(centroid.getProperties(0).getValue().equals("10000")){
                    if(neighbour.getProperties(5).getValue().equals("1")){
                        drawLink(centroid, neighbourCentroid, canvas);
                    }
                }

                else if (!drawn.contains(Set.of(p, neighbour))&&neighbour.getProperties(0).getValue()!="0,0,55") {
                    drawLink(centroid, neighbourCentroid, canvas);
                    drawn.add(Set.of(p, neighbour));
                    break;
                }
            }
            }
        }
    }

    private void drawLink(Structs.Vertex centroid, Structs.Vertex neighbourCentroid, Graphics2D canvas) {
        Line2D line = new Line2D.Float((float) centroid.getX(), (float) centroid.getY(),
                (float) neighbourCentroid.getX(),(float) neighbourCentroid.getY());
        canvas.draw(line);
    }



    private void drawAPolygon(Structs.Polygon p, Mesh aMesh, Graphics2D canvas) {
        Hull hull = new Hull();
        for(Integer segmentIdx: p.getSegmentIdxsList()) {
            Structs.Segment s = aMesh.getSegments(segmentIdx);
            Optional<Color> fill = new ColorProperty().extract(s.getPropertiesList());
            if(fill.isPresent()){
                canvas.setColor(fill.get());
                Stroke stroke = new BasicStroke(10);
                canvas.setStroke(stroke);
                Vertex v1 = aMesh.getVertices(s.getV1Idx());
                Vertex v2 = aMesh.getVertices(s.getV2Idx());
                canvas.drawLine((int)v1.getX(), (int)v1.getY(),(int) v2.getX(), (int)v2.getY());
            }
            Stroke stroke = new BasicStroke(0.2f);
            canvas.setStroke(stroke);
            hull.add(aMesh.getSegments(segmentIdx), aMesh);
        }
        Path2D path = new Path2D.Float();
        Iterator<Vertex> vertices = hull.iterator();
        Vertex current = vertices.next();
        path.moveTo(current.getX(), current.getY());
        while (vertices.hasNext()) {
            current = vertices.next();
            path.lineTo(current.getX(), current.getY());
        }
        path.closePath();
        canvas.draw(path);
        Optional<Color> fill = new ColorProperty().extract(p.getPropertiesList());
        if(fill.isPresent()) {
            Color old = canvas.getColor();
            canvas.setColor(fill.get());
            canvas.fill(path);
            canvas.setColor(old);
        }
    }

 

}
