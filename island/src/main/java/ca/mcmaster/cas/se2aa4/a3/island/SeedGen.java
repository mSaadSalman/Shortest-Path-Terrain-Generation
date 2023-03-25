package ca.mcmaster.cas.se2aa4.a3.island;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class SeedGen {

    private ArrayList<Long> seeds;
    private Random random;
    private Dictionary<Long, Structs.Mesh> meshes;

    public SeedGen(){
        random = new Random();
    }

    // Create a random seed
    public long createSeed() {
        seeds = new ArrayList<>();
        long seed = random.nextLong(); // Generate random long number

        while (seeds.contains(seed)){ // generate new seeds while the 'seeds' array contains the generated seed (this is to make sure no duplicates)
            seed = random.nextLong();
        } 
        seeds.add(seed); // Store the seeds in an array 
        return seed;
    }

    public void storeMesh(long seed, Structs.Mesh mesh) {
        meshes = new Hashtable<>();
        meshes.put(seed, mesh);
    }

    public Structs.Mesh getMesh(long seed){
        return meshes.get(seed);
    }
}