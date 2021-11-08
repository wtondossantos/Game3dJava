package engine;

import entity.helper.Entity;
import org.lwjgl.util.vector.Vector3f;

public class Collision {
    public Vector3f center = new Vector3f(0,0,0);
    
    public Collision(){}
    
    public boolean AABBxAABB(Entity obj1, Entity obj2) {
        if (obj1 == null || obj2 == null) return false;
        
        if (Math.abs(obj1.getPosition().x - obj2.getPosition().x) > (obj1.getDimension().x*0.5f + obj2.getDimension().x*0.5f)) return false;
        if (Math.abs(obj1.getPosition().y - obj2.getPosition().y) > (obj1.getDimension().y*0.5f + obj2.getDimension().y*0.5f)) return false;
        if (Math.abs(obj1.getPosition().z - obj2.getPosition().z) > (obj1.getDimension().z*0.5f + obj2.getDimension().z*0.5f)) return false;
        
        return true;
    }
}