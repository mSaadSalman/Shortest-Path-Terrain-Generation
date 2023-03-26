import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.SeedGen;
import ca.mcmaster.cas.se2aa4.a3.island.islandGenerator;

public class SeedGenTest {
    private Structs.Mesh mesh; // Store Mesh object
    private SeedGen seedGen; // Stores SeedGen object

    @Before
    public void setUp() throws IOException{
        mesh = new MeshFactory().read("../img/lagoon.mesh"); // Creates Mesh object to use in tests
        seedGen = new SeedGen(); // Create a SeedGen object to use in tests
    }

    @Test
    public void testSaveAndGetMesh() throws IOException {
        long seed = System.currentTimeMillis();
        seedGen.saveMesh(seed, mesh);

        // Make sure fil was created and isnt size 0
        File meshFile = new File("../img/java" + String.valueOf(seed) + ".mesh");
        assertNotNull(meshFile);
        assertEquals(true, meshFile.exists());
        assertEquals(true, meshFile.length() > 0);

        // Load mesh object from file
        Structs.Mesh loadedMesh = seedGen.getMesh(seed);

        // make sure loaded mesh is not null and has the same number of vertices as the original mesh (best way to check that the mesh stays the same since vertices can change depending on number of polygons)
        assertNotNull(loadedMesh);
        assertEquals(mesh.getVerticesCount(), loadedMesh.getVerticesCount());
    }

    @After
    public void tearDown() {
        // Delete mesh files created during testing
        File[] meshFiles = new File("img").listFiles((dir, name) -> name.endsWith(".mesh"));
        if (meshFiles != null) {
            for (File meshFile : meshFiles) {
                meshFile.delete();
            }
        }
    }
}