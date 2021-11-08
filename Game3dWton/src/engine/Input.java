package engine;

import action.Action;
import action.ActionType;
import entity.Bullet;
import entity.helper.Entity;
import entity.helper.EntityType;
import entity.helper.Helper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;

public class Input {
    
    private static Vector3f position = new Vector3f(0, 0, 0), rotation = new Vector3f(0, 0, 0);
    private static int mouseSpeed = 2, walkingSpeed = 10;
    private static final int maxLookUp = 85, maxLookDown = -85;
    private int score = 0;
    private Vector3f positionEntity;
    private boolean selected = false;
    private static Action action;
    private State state;
    private Collision collision;
    private Entity entityBullet = null, entityOther = null;
    private double acceleration = 1, theta =0, phi =0;
    
    public Input(State state) {
        collision = new Collision();
        this.state = state;
        action = new Action();
        action.setAction(ActionType.STOP);
        
        try {
            Keyboard.create();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public void commandInput(Helper helper, int delta){
        
        switch(state.getStateType()){
            case INTRO:
                if(Keyboard.isKeyDown(Keyboard.KEY_M)) state.setStateType(StateType.MAIN_MENU);
                break;
                
            case GAME:
                boolean playerFront = Keyboard.isKeyDown(Keyboard.KEY_W);
                boolean playerRight = Keyboard.isKeyDown(Keyboard.KEY_D);
                boolean playerBack = Keyboard.isKeyDown(Keyboard.KEY_S);
                boolean playerLeft = Keyboard.isKeyDown(Keyboard.KEY_A);
                boolean playerUp = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
                boolean playerDown = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
                boolean accelerate = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
                boolean decelerate = Keyboard.isKeyDown(Keyboard.KEY_TAB);
                double tempPositionY = 0;
                float angle = 0;
                
                if (Mouse.isGrabbed()) {
                    float mouseDX = Mouse.getDX() * mouseSpeed * 0.16f;
                    float mouseDY = Mouse.getDY() * mouseSpeed * 0.16f;
                    if (rotation.y + mouseDX >= 360) rotation.y = rotation.y + mouseDX - 360;
                    else if (rotation.y + mouseDX < 0) rotation.y = 360 - rotation.y + mouseDX;
                    else rotation.y += mouseDX;
                    if (rotation.x - mouseDY >= maxLookDown && rotation.x - mouseDY <= maxLookUp) rotation.x += -mouseDY;
                    else if (rotation.x - mouseDY < maxLookDown) rotation.x = maxLookDown;
                    else if (rotation.x - mouseDY > maxLookUp) rotation.x = maxLookUp;
                }
                
                if (accelerate && !decelerate) walkingSpeed *= 4f;
                if (!accelerate && decelerate) walkingSpeed /= 10f;
                //if (playerFront && playerRight && !playerBack && !playerLeft) angle = rotation.y + 45;
                //if (playerFront && !playerRight && !playerBack && playerLeft) angle = rotation.y - 45;
                //if (playerFront && !playerRight && !playerBack && !playerLeft) angle = rotation.y;
                //if (!playerFront && !playerRight && playerBack && playerLeft) angle = rotation.y - 135;
                //if (!playerFront && playerRight && playerBack && !playerLeft) angle = rotation.y + 135;
                //if (!playerFront && !playerRight && playerBack && !playerLeft) angle = rotation.y;
                //if (!playerFront && !playerRight && !playerBack && playerLeft) angle = rotation.y - 90;
                //if (!playerFront && playerRight && !playerBack && !playerLeft) angle = rotation.y + 90;
                if (playerUp || playerDown) tempPositionY = (walkingSpeed * 0.0002) * delta;
                if (playerUp && !playerDown) position.y -= tempPositionY;
                if (!playerUp && playerDown) position.y += tempPositionY;
                if (accelerate && !decelerate) walkingSpeed /= 4f;
                if (!accelerate && decelerate) walkingSpeed *= 10f;
            
                if (playerFront){// || playerRight || playerLeft) {
                    Vector3f tempPosition = new Vector3f(position);
                    float hypotenuse = (walkingSpeed * 0.0002f) * delta;
                    float adjacent = hypotenuse * (float) Math.cos(Math.toRadians(angle));
                    float opposite = (float) (Math.sin(Math.toRadians(angle)) * hypotenuse);
                    tempPosition.z += adjacent;
                    tempPosition.x -= opposite;
                    position.z = tempPosition.z;
                    position.x = tempPosition.x;
                }
                if (playerBack) {
                    Vector3f tempPosition = new Vector3f(position);
                    float hypotenuse = -(walkingSpeed * 0.0002f) * delta;
                    float adjacent = hypotenuse * (float) Math.cos(Math.toRadians(angle));
                    float opposite = (float) (Math.sin(Math.toRadians(angle)) * hypotenuse);
                    tempPosition.z += adjacent;
                    tempPosition.x += opposite;
                    position.z = tempPosition.z;
                    position.x = tempPosition.x;
                }
                
                /*
                while (Mouse.next()) {
                    if (Mouse.isButtonDown(0)) Mouse.setGrabbed(true);
                    if (Mouse.isButtonDown(1)) Mouse.setGrabbed(false);
                }*/
                
                while (Keyboard.next()) {
                    if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
                        position = new Vector3f(0, 0, 0);
                        rotation = new Vector3f(0, 0, 0);
                        entityBullet.setPosition(new Vector3f(-.4f, -1, -2));
                        //entityBullet.setAcceleration(1);
                        //entityBullet.setAngleTheta(0);
                        //entityBullet.setAnglePhi(0);
                    }
                    
                    if (Keyboard.isKeyDown(Keyboard.KEY_O)) mouseSpeed += 1;
                    if (Keyboard.isKeyDown(Keyboard.KEY_T)) if (mouseSpeed - 1 > 0) mouseSpeed -= 1;
                    if (Keyboard.isKeyDown(Keyboard.KEY_Q)) walkingSpeed += 1;
                    if (Keyboard.isKeyDown(Keyboard.KEY_Z)) walkingSpeed -= 1;
                    
                    if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                        if (!Mouse.isGrabbed() || Display.isFullscreen()) {
                            Display.destroy();
                            System.exit(0);
                        } else {
                            Mouse.setGrabbed(false);
                        }
                    }
                }
                
                for(int i=0;i<helper.entity.size();i++){
                    Entity entityTemp = helper.entity.get(i);
                    
                    if(entityBullet!=null){
                        if(collision.AABBxAABB(entityBullet, entityOther)){ 
                            if(entityOther.getType() == EntityType.Gold){
                                score+=10;
                                //entityBullet.setAcceleration(1);
                                //entityBullet.setAngleTheta(0);
                                //entityBullet.setAnglePhi(0);
                            }
                            helper.removeObject(entityBullet);
                            helper.addObject(new Bullet(
                                    new Vector3f(-.3f, .5f, 22),
                                    new Vector3f(.5f, .5f, .5f),
                                    new Vector3f(0,0,0),
                                    new Color(1,1,1,1),
                                    "res/bullet.png",
                                    EntityType.Bullet)
                            );
                            //entityBullet.setPosition(new Vector3f(-.4f, -1, -2));
                            action.setAction(ActionType.STOP);           
                        }
                        if(entityBullet.getPosition().z <-51.0f || entityBullet.getPosition().z>50.0f || entityBullet.getPosition().x<-20.0f || entityBullet.getPosition().x>20.0f){
                            entityBullet.setPosition(new Vector3f(-.4f, -1, -2));
                            //entityBullet.setAcceleration(1);
                            //entityBullet.setAngleTheta(0);
                            //entityBullet.setAnglePhi(0);
                            action.setAction(ActionType.STOP);
                        }
                        if(entityBullet.getPosition().y < -1.1f){
                            positionEntity = entityBullet.getPosition();
                            positionEntity.y = -1;
                            entityBullet.setPosition(positionEntity);
                            action.setAction(ActionType.STOP);
                        }
                    }
                    
                    if(entityTemp.getType() == EntityType.Bullet){
                        entityBullet = entityTemp;
                        
                        /*
                        if(Keyboard.isKeyDown(Keyboard.KEY_R)){
                            selected = false;
                            entityTemp.setSelected(false);
                            helper.removeObject(entityTemp);
                        }*/
                        
                        if(Keyboard.isKeyDown(Keyboard.KEY_A) && Keyboard.isKeyDown(Keyboard.KEY_PERIOD)  && acceleration < 4.1f){ 
                            acceleration = entityTemp.getAcceleration();
                            acceleration +=.1f;
                            entityTemp.setAcceleration(acceleration);
                        }else if(Keyboard.isKeyDown(Keyboard.KEY_A) && Keyboard.isKeyDown(Keyboard.KEY_COMMA) && acceleration >= 0){
                            acceleration = entityTemp.getAcceleration();
                            acceleration -=.1f;
                            entityTemp.setAcceleration(acceleration);
                        }
                        if(Keyboard.isKeyDown(Keyboard.KEY_L) && Keyboard.isKeyDown(Keyboard.KEY_PERIOD)){ 
                            phi = entityTemp.getAnglePhi();
                            phi +=1f;
                            entityTemp.setAnglePhi(phi);
                        }else if(Keyboard.isKeyDown(Keyboard.KEY_L) && Keyboard.isKeyDown(Keyboard.KEY_COMMA)){
                            phi = entityTemp.getAnglePhi();
                            phi -=1f;
                            entityTemp.setAnglePhi(phi);
                        }
                        if(Keyboard.isKeyDown(Keyboard.KEY_P) && Keyboard.isKeyDown(Keyboard.KEY_PERIOD)){ 
                            theta = entityTemp.getAngleTheta();
                            theta +=1f;
                            entityTemp.setAngleTheta(theta);
                        }else if(Keyboard.isKeyDown(Keyboard.KEY_P) && Keyboard.isKeyDown(Keyboard.KEY_COMMA)){
                            theta = entityTemp.getAngleTheta();
                            theta -=1f;
                            entityTemp.setAngleTheta(theta);
                        }
                        
                        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){//tecla: Direção "Esquerda"
                            positionEntity = entityTemp.getPosition();
                            positionEntity.x -= 0.002f * delta;
                            entityTemp.setPosition(positionEntity);
                        }else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){//tecla: Direção "Direita"
                            positionEntity = entityTemp.getPosition();
                            positionEntity.x += 0.002f * delta;
                            entityTemp.setPosition(positionEntity);
                        }
                        if(Keyboard.isKeyDown(Keyboard.KEY_B)){//tecla: Direção "Baixo"
                            positionEntity = entityTemp.getPosition();
                            positionEntity.y -= 0.002f * delta;
                            entityTemp.setPosition(positionEntity);
                        }else if(Keyboard.isKeyDown(Keyboard.KEY_N)){//tecla: Direção "Cima"
                            positionEntity = entityTemp.getPosition();
                            positionEntity.y += 0.002f * delta;
                            entityTemp.setPosition(positionEntity);
                        }
                        if(Keyboard.isKeyDown(Keyboard.KEY_UP)){//tecla: "Z" Diminui cubo
                            positionEntity = entityTemp.getPosition();
                            positionEntity.z -= 0.002f * delta;// delta;
                            entityTemp.setPosition(positionEntity);
                        }else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){//tecla: "X" Aumenta cubo
                            positionEntity = entityTemp.getPosition();
                            positionEntity.z += 0.002f * delta;
                            entityTemp.setPosition(positionEntity);
                        }
                        
                        if(Keyboard.isKeyDown(Keyboard.KEY_1)) action.setAction(ActionType.MU);
                        if(Keyboard.isKeyDown(Keyboard.KEY_2)) action.setAction(ActionType.MUA);
                        if(Keyboard.isKeyDown(Keyboard.KEY_3)) action.setAction(ActionType.FREEFALL);
                        if(Keyboard.isKeyDown(Keyboard.KEY_4)) action.setAction(ActionType.THROW2D);
                        if(Keyboard.isKeyDown(Keyboard.KEY_5)) action.setAction(ActionType.THROW3D);
                        
                        if(action.getAction() != ActionType.STOP){
                            if(action.getAction() == ActionType.MU){
                                if(entityTemp.getInitSpeed()==0) entityTemp.setInitSpeed(0.01f);
                                helper.uniformMovement(entityTemp, entityTemp.getPosition());
                            }
                            if(action.getAction() == ActionType.MUA) helper.uniformlyVariableMovement(entityTemp, entityTemp.getPosition());
                            if(action.getAction() == ActionType.FREEFALL) helper.freefall(entityTemp, entityTemp.getPosition());
                            if(action.getAction() == ActionType.THROW2D) helper.throw2d(entityTemp, entityTemp.getPosition(), entityTemp.getAngleTheta());
                            if(action.getAction() == ActionType.THROW3D) helper.throw3d(entityTemp, entityTemp.getPosition(), entityTemp.getAngleTheta(), entityTemp.getAnglePhi());
                        }       
                    }else{
                        entityOther = entityTemp;
                    }
                }
                break;
                
            case MAIN_MENU:
                if(Keyboard.isKeyDown(Keyboard.KEY_G)) state.setStateType(StateType.GAME);
                break;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            Display.destroy();
            System.exit(0);
        }
    }
    
    public int getScore(){
        return score;
    }
    
    public Entity getBullet(){
        return entityBullet;
    }
    
    public Vector3f getRotation(){
        return rotation;
    }
    
    public Vector3f getPosition(){
        return position;
    }
   
    public static Action getInstanceAction(){
        return action;
    }
}
