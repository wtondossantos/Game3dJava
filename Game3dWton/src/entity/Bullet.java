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

public class Bullet extends Entity{
    
    private final static Action action = Input.getInstanceAction();
    private Helper helper = new Helper();
    public Texture texture = new Texture(glGenTextures());

    public Bullet(Vector3f position, Vector3f rotation, Vector3f dimension, Color color, String texture, EntityType type) {
        super(position, rotation, dimension, color, texture, type);
        this.texture.setPath(texture);
    }

    @Override
    public void update(LinkedList<Entity> entity) {
        
        /*
        for(int i=0;i<entity.size();i++){
            Entity entityTemp = entity.get(i);
            
            if(collision.AABBxAABB(entityBlock, entityOther)) action.setAction(ActionType.STOP);
            
            if(entityTemp.getType() == EntityType.Bullet){
                entityBlock = entityTemp;
                if(action.getAction() != ActionType.STOP){
                    if(action.getAction() == ActionType.MU){
                        if(entityTemp.getInitSpeed()==0) entityTemp.setInitSpeed(0.01f);
                        helper.uniformMovement(entityTemp, entityTemp.getPosition());
                    }else if(action.getAction() == ActionType.MUA){
                        if(entityTemp.getAcceleration()==0) entityTemp.setAcceleration(1);
                        helper.uniformlyVariableMovement(entityTemp, entityTemp.getPosition());
                    }else if(action.getAction() == ActionType.FREEFALL){
                        helper.freefall(entityTemp, entityTemp.getPosition());
                    }else if(action.getAction() == ActionType.THROW2D){
                        if(entityTemp.getAcceleration()==0) entityTemp.setAcceleration(2.5f);
                        helper.throw2d(entityTemp, entityTemp.getPosition(), angleTheta);
                    }else if(action.getAction() == ActionType.THROW3D){
                        if(entityTemp.getAcceleration()==0) entityTemp.setAcceleration(6.0f);
                        helper.throw3d(entityTemp, entityTemp.getPosition(), angleTheta, anglePhi);
                    }
                }
            }else{
                entityOther = entityTemp;
            }
            
        }*/
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
