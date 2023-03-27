package ca.mcmaster.cas.se2aa4.a3.island;

import java.util.Arrays;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.Properties.Properties;

public class Biomes {
    private Structs.Mesh aMesh;

    public Biomes(Structs.Mesh iMesh) {
        this.aMesh = iMesh;
    }

    public Structs.Mesh enrichBiomes() {
        Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder();
        iMesh.addAllVertices(aMesh.getVerticesList());
        iMesh.addAllSegments(aMesh.getSegmentsList());
        iMesh.addAllPolygons(aMesh.getPolygonsList());
        Structs.Property forest = Properties.getForestProps();
        ///Structs.Property land = Properties.getLandProps();
        Structs.Property tundra = Properties.getTundraProps();
        Structs.Property beach = Properties.getBeachProps();
        Structs.Property plains = Properties.get_PlainsProps();
        

        for (int i = 0; i < aMesh.getPolygonsCount(); i++) {
            Structs.Polygon.Builder p = Structs.Polygon.newBuilder(iMesh.getPolygons(i));

            boolean ismountain = !Arrays.asList(Properties.rockMountTier1Colors, Properties.rockMountTier2Colors, Properties.rockMountTier3Colors, Properties.rockMountTier4Colors)
                      .contains(p.getProperties(0).getValue());
            
            boolean isvolcano = !Arrays.asList(Properties.volcanoTier1Colors, Properties.volcanoTier2Colors, Properties.volcanoTier3Colors, Properties.volcanoTier4Colors)
                      .contains(p.getProperties(0).getValue());

            //creates forest around lakes biome
            if (p.getProperties(2).getValue() == "90" && p.getProperties(0).getValue() != Properties.lakeColors 
            && ismountain && isvolcano) {
                p.setProperties(0, forest);
            }

            //creates beaches biome
            if (p.getProperties(2).getValue() == "60" && p.getProperties(0).getValue() != Properties.lakeColors
            &&  p.getProperties(4).getValue() == "25"
            && ismountain && isvolcano) {
                p.setProperties(0, Properties.getLandMediumProps());
            }

            //creates plains biome
            if (p.getProperties(2).getValue() == "60" && p.getProperties(0).getValue() != Properties.lakeColors
            &&  p.getProperties(4).getValue() == "15"
            && ismountain && isvolcano) {
                p.setProperties(0, plains);
            }

            //creates tundra biome
            if (p.getProperties(2).getValue() == "60" && p.getProperties(0).getValue() != Properties.lakeColors
            &&  p.getProperties(4).getValue() == "5"
            && ismountain && isvolcano) {
                p.setProperties(0, tundra);
            }




            iMesh.setPolygons(i, p);

        }

        return iMesh.build();
    }

}
