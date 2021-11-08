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

public class Cube extends Entity{
    
    private final static Action action = Input.getInstanceAction();
    public Texture texture = new Texture(glGenTextures());
    private Helper helper = new Helper();
    
    int modelList;

    public Cube(Vector3f position, Vector3f rotation, Vector3f dimension, Color color, String texture, EntityType type) {
        super(position, rotation, dimension, color, texture, type);
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
            //front face
            glNormal3f(0f, 0f, 1f);
            glTexCoord2f(0f, 1f);
            glVertex3f(position.x+dimension.x, position.y, position.z); //1
            glTexCoord2f(1f, 1f);
            glVertex3f(position.x, position.y, position.z); // 2
            glTexCoord2f(1f, 0f);
            glVertex3f(position.x, position.y+dimension.y, position.z); // 3
            glTexCoord2f(0f, 0f);
            glVertex3f(position.x+dimension.x, position.y+dimension.y, position.z); // 4
        //right face
            glNormal3f(0f, 0f, -1f);
            glTexCoord2f(0f, 1f);
            glVertex3f(position.x, position.y, position.z+dimension.z); //1
            glTexCoord2f(1f, 1f);
            glVertex3f(position.x+dimension.x, position.y, position.z+dimension.z); // 2
            glTexCoord2f(1f, 0f);
            glVertex3f(position.x+dimension.x, position.y+dimension.y, position.z+dimension.z); // 3
            glTexCoord2f(0f, 0f);
            glVertex3f(position.x, position.y+dimension.y, position.z+dimension.z); // 4
        //top face
            glNormal3f(0f, -1f, 0f);
            glTexCoord2f(0f, 1f);
            glVertex3f(position.x+dimension.x, position.y+dimension.y, position.z); //1
            glTexCoord2f(1f, 1f);
            glVertex3f(position.x, position.y+dimension.y, position.z); // 2
            glTexCoord2f(1f, 0f);
            glVertex3f(position.x, position.y+dimension.y, position.z+dimension.z); // 3
            glTexCoord2f(0f, 0f);
            glVertex3f(position.x+dimension.x, position.y+dimension.y, position.z+dimension.z); // 4
        //bottom face
            glNormal3f(0f, 1f, 0f);
            glTexCoord2f(0f, 1f);
            glVertex3f(position.x+dimension.x, position.y, position.z+dimension.z); //1
            glTexCoord2f(1f, 1f);
            glVertex3f(position.x, position.y, position.z+dimension.z); // 2
            glTexCoord2f(1f, 0f);
            glVertex3f(position.x, position.y, position.z); // 3
            glTexCoord2f(0f, 0f);
            glVertex3f(position.x+dimension.x, position.y, position.z); // 4
        //left face
            glNormal3f(-1f, 0f, 0f);
            glTexCoord2f(0f, 1f);
            glVertex3f(position.x+dimension.x, position.y, position.z+dimension.z); //1
            glTexCoord2f(1f, 1f);
            glVertex3f(position.x+dimension.x, position.y, position.z); // 2
            glTexCoord2f(1f, 0f);
            glVertex3f(position.x+dimension.x, position.y+dimension.y, position.z); // 3
            glTexCoord2f(0f, 0f);
            glVertex3f(position.x+dimension.x, position.y+dimension.y, position.z+dimension.z); // 4
        //right face
            glNormal3f(1f, 0f, 0f);
            glTexCoord2f(0f, 1f);
            glVertex3f(position.x, position.y, position.z); //1
            glTexCoord2f(1f, 1f);
            glVertex3f(position.x, position.y, position.z+dimension.z); // 2
            glTexCoord2f(1f, 0f);
            glVertex3f(position.x, position.y+dimension.y, position.z+dimension.z); // 3
            glTexCoord2f(0f, 0f);
            glVertex3f(position.x, position.y+dimension.y, position.z); // 4
        
        glEnd();
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
