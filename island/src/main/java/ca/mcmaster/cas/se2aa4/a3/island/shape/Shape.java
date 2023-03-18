package ca.mcmaster.cas.se2aa4.a3.island.shape;

import com.google.protobuf.Struct;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.CenterMesh;

public abstract class Shape {

    protected Structs.Property land;
    protected Structs.Property ocean;
    public abstract boolean contains(Structs.Vertex vertex);

    public Structs.Mesh build(Structs.Mesh aMesh){
        Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder();
        iMesh.addAllVertices(aMesh.getVerticesList());
        iMesh.addAllSegments(aMesh.getSegmentsList());

        for (Structs.Polygon poly : aMesh.getPolygonsList()) {
            Structs.Vertex centroid = aMesh.getVertices(poly.getCentroidIdx());
            Structs.Polygon.Builder p = Structs.Polygon.newBuilder(poly);
            if(contains(centroid))
                p.addProperties(land);
            else 
                p.addProperties(ocean);

            iMesh.addPolygons(p);
            
    }

    return iMesh.build();

    }
}
