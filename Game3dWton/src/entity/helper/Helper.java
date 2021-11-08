package entity.helper;

import java.util.LinkedList;
import org.lwjgl.util.vector.Vector3f;

public class Helper{

    public LinkedList<Entity> entity = new LinkedList<Entity>();
    private Entity entityTemp;
    private Vector3f position = new Vector3f(0,0,0);
    private float x = 0;
    
    //Atualiza a cada frame
    public void update() {
        for(int i=0;i< entity.size(); i++){
            entityTemp = entity.get(i);
            entityTemp.update(entity);
        }
    }
    
    //Atualizar objetos da lista 
    public void render(){
        for(int i=0;i< entity.size(); i++){
            entityTemp = entity.get(i);
            position = entityTemp.getPosition();
            if(position.y <= -1.1f){ 
                position.y = -1.1f;
                entityTemp.setPosition(position);
            }
            entityTemp.render();
            
        }
    }
    
    //Addiconar objetos na lista 
    public void addObject(Entity entity){
        this.entity.add(entity);
    }
    
    //Remove objetos da lista 
    public void removeObject(Entity entity){
        this.entity.remove(entity);
    }
    
    //MU
    public void uniformMovement(Entity entity, Vector3f position){
        position.z -= entity.getInitSpeed() + entity.getAcceleration()*entity.getTime();
        entity.setPosition(position);
    }
    
    //MUA
    public void uniformlyVariableMovement(Entity entity, Vector3f position){
        position.z -= entity.getInitSpeed() + (entity.getAcceleration()*(Math.pow(entity.getTime(), 2)))/2;
        entity.setTime(entity.getTime()+0.01f);
        entity.setPosition(position);
    }
    
    //Queda livre
    public void freefall(Entity entity, Vector3f position){
        position.y -= entity.getInitSpeed() + (entity.getGravity()*(Math.pow(entity.getTime(), 2)))/2;
        entity.setTime(entity.getTime()+0.01f);
        entity.setPosition(position);
    }
                    
    public void throw2d(Entity entity, Vector3f position, double angleTheta){
        position.z -= Math.cos(angleTheta*Math.PI/180)*entity.getAcceleration()*entity.getTime();
        position.y += Math.sin(angleTheta*Math.PI/180)*entity.getAcceleration()*entity.getTime() - (entity.getGravity()*(Math.pow(entity.getTime(), 2)))/2;
        entity.setTime(entity.getTime()+0.01f);
        entity.setPosition(position);
    }
    
    public void throw3d(Entity entity, Vector3f position, double angleTheta, double anglePhi){
        position.z -= Math.sin((angleTheta)*Math.PI/180)*Math.cos(anglePhi*Math.PI/180)*entity.getAcceleration()*entity.getTime();
        position.x += Math.sin((angleTheta)*Math.PI/180)*Math.sin(anglePhi*Math.PI/180)*entity.getAcceleration()*entity.getTime();
        position.y += Math.cos(angleTheta*Math.PI/180)*entity.getAcceleration()*entity.getTime() - (entity.getGravity()*(Math.pow(entity.getTime(), 2)))/2;
        entity.setTime(entity.getTime()+0.01f);
        entity.setPosition(position);
    }
    
    //Cria objetos do nível
    public void createLevel(){
        
    }

}
