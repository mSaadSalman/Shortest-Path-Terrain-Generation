import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        DotGen generator = new DotGen();

        if (args[1].equals("grid")) {
            Mesh myMesh = generator.generateGridMesh();
            MeshFactory factory = new MeshFactory();
            factory.write(myMesh, args[0]);
        }
        else if (args[1].equals("irregular")) {
            Mesh myMesh = generator.generateIrregularMesh();
            MeshFactory factory = new MeshFactory();
            factory.write(myMesh, args[0]);
        }
    }

}
