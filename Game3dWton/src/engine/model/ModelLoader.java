package engine.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;

public class ModelLoader {

    private ModelLoader(){} 

    public static Model loadModel(File file, Model model) throws FileNotFoundException, IOException { 
        Model m = model; 
        String line; 
        boolean v = false; 
        boolean vn = false; 
        boolean vt = false; 
        Vector3f vertexIndices = null; 
        Vector3f textureIndices = null; 
        Vector3f normalIndices = null; 
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) { 
            while ((line = reader.readLine()) != null) { 
                if (line.startsWith("v ")) { 
                    v = true; 
                    float x = Float.valueOf(line.split(" ")[1]); 
                    float y = Float.valueOf(line.split(" ")[2]); 
                    float z = Float.valueOf(line.split(" ")[3]); 
                    m.vertex.add(new Vector3f(x, y, z)); 
                } else if (line.startsWith("vn ")) { 
                    vn = true; 
                    float x3 = Float.valueOf(line.split(" ")[1]); 
                    float y3 = Float.valueOf(line.split(" ")[2]); 
                    float z3 = Float.valueOf(line.split(" ")[3]); 
                    m.normal.add(new Vector3f(x3, y3, z3)); 
                } else if (line.startsWith("vt ")){ 
                    vt = true; 
                    float x2 = Float.valueOf(line.split(" ")[1]); 
                    float y2 = Float.valueOf(line.split(" ")[2]); 
                    m.texture.add(new Vector3f(x2, y2, 0.0f)); 
                } 
                else if (line.startsWith("f ")) 
                { 
                    if (v){ 
                        vertexIndices = new Vector3f(Float.valueOf( 
                        line.split(" ")[1].split("/")[0]), 
                        Float.valueOf(line.split(" ")[2].split("/")[0]), 
                        Float.valueOf(line.split(" ")[3].split("/")[0])); 
                    } 
                    if (vt){ 
                        
                        textureIndices = new Vector3f(Float.valueOf( 
                        line.split(" ")[1].split("/")[1]), 
                        Float.valueOf(line.split(" ")[2].split("/")[1]), 
                        Float.valueOf(line.split(" ")[3].split("/")[1])); 
                    } 
                    if (vn) { 
                        
                        normalIndices = new Vector3f( 
                        Float.valueOf(line.split(" ")[1].split("/")[2]), 
                        Float.valueOf(line.split(" ")[2].split("/")[2]), 
                        Float.valueOf(line.split(" ")[3].split("/")[2])); 
                    } 
                    model.face.add(new Face(vertexIndices, textureIndices, normalIndices)); 
                } 
            } 
        } 
        return m; 
    } 

}
