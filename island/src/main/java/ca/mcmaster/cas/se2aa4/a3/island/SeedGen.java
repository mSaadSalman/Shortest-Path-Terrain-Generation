package ca.mcmaster.cas.se2aa4.a3.island;


import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;




public class SeedGen {
    // private Dictionary<Long, Structs.Mesh> meshes;

    // public void storeMesh(long seed, Structs.Mesh mesh) {
    //     meshes = new Hashtable<>();
    //     meshes.put(seed, mesh);
    // }


    public void saveMesh(long seed, Structs.Mesh mesh) throws IOException{
        String seedStr = "img/"+String.valueOf(seed)+".mesh";
        new MeshFactory().write(mesh, seedStr);
    }

    public Structs.Mesh getMesh(long seed) throws IOException{
        String seedStr = "img/"+String.valueOf(seed)+".mesh";
        System.out.println(seedStr);
        return new MeshFactory().read(seedStr);
    }
}

// public class SeedGen {
//     private Dictionary<Long, Structs.Mesh> meshes;

//     public void storeMesh(long seed, Structs.Mesh mesh) {
//         meshes = new Hashtable<>();
//         meshes.put(seed, mesh);
//     }

//     public Structs.Mesh getMesh(long seed){
//         return meshes.get(seed);
//     }
// }

// public class SeedGen {
//     private Map<Long, Structs.Mesh> meshes;

//     public SeedGen() {
//         // Initialize the dictionary from a saved file, or create a new one if the file doesn't exist
//         try {
//             FileInputStream fileIn = new FileInputStream("meshes.ser");
//             ObjectInputStream in = new ObjectInputStream(fileIn);
//             meshes = (Map<Long, Structs.Mesh>) in.readObject();
//             in.close();
//             fileIn.close();
//         } catch (IOException | ClassNotFoundException e) {
//             System.out.println("Creating new meshes dictionary");
//             meshes = new HashMap<>();
//         }
//     }

//     public void storeMesh(long seed, Structs.Mesh mesh) {
//         meshes.put(seed, mesh);
//         saveMeshes();
//     }

//     public Structs.Mesh getMesh(long seed){
//         return meshes.get(seed);
//     }

//     private void saveMeshes() {
//         try {
//             FileOutputStream fileOut = new FileOutputStream("meshes.ser");
//             ObjectOutputStream out = new ObjectOutputStream(fileOut);
//             out.writeObject(meshes);
//             out.close();
//             fileOut.close();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }











// public static void Reproducibility(Configuration config) throws IOException {

//     // Generate seed
//     long seed;
//     if (config.seed() != null) {
//         seed = Long.parseLong(config.seed());
//         Structs.Mesh mesh = new islandGenerator().generate(config.shape(), config.mode(), config.lakes(), config.seed());
//     }  
//     else {
//         seed = System.currentTimeMillis();
//         System.out.println("No seed provided, using random seed: " + seed);
//     }

//     // Generate mesh with provided or random seed
        

//     // Write mesh to file
//     new MeshFactory().write(mesh, config.output());
// }
