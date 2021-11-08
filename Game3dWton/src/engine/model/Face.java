package engine.model;

import org.lwjgl.util.vector.Vector3f;

public class Face {

    public Vector3f vertex; 
    public Vector3f texture; 
    public Vector3f normal; 

    public Face(Vector3f vertex, Vector3f texture, Vector3f normal){ 
        this.vertex = new Vector3f(); 
        this.vertex = vertex; 
        this.texture = new Vector3f(); 
        this.texture = texture; 
        this.normal = new Vector3f(); 
        this.normal = normal; 
    } 
}
