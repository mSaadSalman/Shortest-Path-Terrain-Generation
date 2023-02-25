package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class GraphicRenderer {

    private boolean debugMode = false;

    public GraphicRenderer(String flag) {
        if (Objects.equals(flag, "-X")) {
            this.debugMode = true;
        }
    }

    public void render(Mesh aMesh, Graphics2D canvas) {

        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);

        // Create vertices
        for (Vertex v : aMesh.getVerticesList()) {
            int THICKNESS = extractThickness(v.getPropertiesList());
            double centre_x = v.getX() - (THICKNESS / 2.0d);
            double centre_y = v.getY() - (THICKNESS / 2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }

        // Create centorids
        for (Vertex c : aMesh.getVerticesList()) {
            int THICKNESS = 3;
            double centre_x = c.getX() - (THICKNESS / 2.0d);
            double centre_y = c.getY() - (THICKNESS / 2.0d);

            Color old = canvas.getColor();
            canvas.setColor(Color.RED);
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }

        // Create edges
        for (Segment e : aMesh.getSegmentsList()) {
            int v1Index = e.getV1Idx();
            int v2Index = e.getV2Idx();
            Vertex v1 = aMesh.getVertices(v1Index);
            Vertex v2 = aMesh.getVertices(v2Index);

            Color old = canvas.getColor();
            canvas.setColor(extractColor(e.getPropertiesList()));

            Stroke newStroke = new BasicStroke(extractThickness(e.getPropertiesList()));
            canvas.setStroke(newStroke);

            
            canvas.drawLine((int) v1.getX(), (int) v1.getY(), (int) v2.getX(), (int) v2.getY());
            
            canvas.setColor(old);
            canvas.setStroke(stroke);
        }

    }

    private Color extractColor(List<Property> properties) {
        String val = null;
        if (!debugMode) {
            for (Property p : properties) {
                if (p.getKey().equals("rgb_color")) {
                    System.out.println(p.getValue());
                    val = p.getValue();
                }
            }
        }

        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        return new Color(red, green, blue);
    }

    private int extractThickness(List<Property> properties) {
        String val = null;
        if (!debugMode) {
            for (Property p : properties) {
                if (p.getKey().equals("thickness")) {
                    System.out.println(p.getValue());
                    val = p.getValue();
                }
            }
        }
        if (val == null)
            return 3;
        int thickness = Integer.parseInt(val);
        return thickness;
    }

}