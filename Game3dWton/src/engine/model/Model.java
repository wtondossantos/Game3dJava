package engine.model;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;

public class Model {

    public List<Vector3f> texture;
    public List<Vector3f> vertex;
    public List<Vector3f> normal;
    public List<Face> face;
    
    public Model(){
        texture = new ArrayList<>();
        vertex = new ArrayList<>();
        normal = new ArrayList<>();
        face = new ArrayList<>();
    
    }
}
