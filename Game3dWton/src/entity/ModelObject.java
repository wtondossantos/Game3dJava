package entity;

import action.Action;
import action.ActionType;
import engine.Texture;
import engine.model.Face;
import engine.model.Model;
import engine.model.ModelLoader;
import entity.helper.Entity;
import entity.helper.EntityType;
import entity.helper.Helper;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;

public class ModelObject extends Entity{
    public Texture texture = new Texture(glGenTextures());
    private Helper helper = new Helper();
    private String object;

    public ModelObject(Vector3f position, Vector3f dimension, Vector3f rotation, Color color, String texture, String object, EntityType type) {
        super(position, dimension, rotation, color, texture, type);
        this.texture.setPath(texture);
        this.object = object;
    }

    @Override
    public void update(LinkedList<Entity> entity) {
        
        for(int i=0;i<entity.size();i++){
            Entity entityTemp = entity.get(i);
            if(entityTemp.getType() == EntityType.Spider){
                entityTemp.setGravity(2000);
                helper.freefall(entityTemp, entityTemp.getPosition());
                //position.y -=.5;
                //entityTemp.setPosition(position);
            }
        }
    }

    @Override
    public void render() {
        texture.render();
        draw();
    }
    
    public void draw(){
        glBindTexture(GL_TEXTURE_2D, texture.getTexture());
        Model model = null;
        try { 
            model = ModelLoader.loadModel(new File(object), new Model()); 
        } catch (IOException e) { 
            e.printStackTrace();
        } 
        glScalef(dimension.x, dimension.y, dimension.z); 
        glRotatef(rotation.x, rotation.y, rotation.z, 0);
        glTranslatef(position.x, position.y, position.z);


        glBegin(GL_TRIANGLES); 
        for (Face face : model.face) 
        { 
            Vector3f n1 = model.normal.get((int) face.normal.x - 1); 
            glNormal3f(n1.x, n1.y, n1.z); 
            Vector3f n2 = model.normal.get((int) face.normal.y - 1); 
            glNormal3f(n2.x, n2.y, n2.z); 
            Vector3f n3 = model.normal.get((int) face.normal.z - 1); 
            glNormal3f(n3.x, n3.y, n3.z); 

            Vector3f vt1 = model.texture.get((int) face.texture.x - 1); 
            glTexCoord2f(vt1.x, vt1.y); 
            Vector3f vt2 = model.texture.get((int) face.texture.y - 1); 
            glTexCoord2f(vt2.x, vt2.y); 
            Vector3f vt3 = model.texture.get((int) face.texture.z - 1); 
            glNormal3f(vt3.x, vt3.y, vt3.z); 

            Vector3f v1 = model.vertex.get((int) face.vertex.x - 1); 
            glVertex3f(v1.x, v1.y, v1.z); 
            Vector3f v2 = model.vertex.get((int) face.vertex.y - 1); 
            glVertex3f(v2.x, v2.y, v2.z); 
            Vector3f v3 = model.vertex.get((int) face.vertex.z - 1); 
            glVertex3f(v3.x, v3.y, v3.z); 
        }
        glEnd();
        glBindTexture(GL_TEXTURE_2D, 0);
    
    }
}
