package entity;

import action.Action;
import engine.Input;
import engine.Texture;
import entity.helper.Entity;
import entity.helper.EntityType;
import entity.helper.Helper;
import java.util.LinkedList;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;

public class Floor extends Entity{
    
    private final static Action action = Input.getInstanceAction();
    public Texture texture = new Texture(glGenTextures());
    private Helper helper = new Helper();
    private float tileSize;
    
    int modelList;

    public Floor(Vector3f position, Vector3f rotation, Vector3f dimension, Color color, String texture, float tileSize, EntityType type) {
        super(position, rotation, dimension, color, texture, type);
        this.tileSize = tileSize;
        this.texture.setPath(texture);
        
    }

    @Override
    public void update(LinkedList<Entity> entity) {
        
    }

    @Override
    public void render() {
        texture.render();
        draw();
    }
    
    public void draw(){
        glBindTexture(GL_TEXTURE_2D, texture.getTexture());
            glBegin(GL_QUADS);
                glTexCoord2f(0, 0);
                glVertex3f(-dimension.x, dimension.y, -dimension.z);
                glTexCoord2f(0, dimension.x * 10 * tileSize);
                glVertex3f(-dimension.x, dimension.y, dimension.z);
                glTexCoord2f(dimension.x * 10 * tileSize, dimension.z * 10 * tileSize);
                glVertex3f(dimension.x, dimension.y, dimension.z);
                glTexCoord2f(dimension.x * 10 * tileSize, 0);
                glVertex3f(dimension.x, dimension.y, -dimension.z);
            glEnd();
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
