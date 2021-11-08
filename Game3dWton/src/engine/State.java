package engine;

import entity.helper.Helper;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;


public class State {
    private StateType stateType;
    private boolean activeGame = true;
    private float fade = 0;
    private Helper helper;
    
    public State(Helper helper){
        this.helper = helper;
    }
    
    public void render(){
        
        switch(stateType){
            case INTRO:
                glColor3f(0.5f,0,0);
                glRectf(-1,-1,2,2);
                break;
            case GAME:
                /*if(activeGame){
                    activeGame = false;
                    helper.createLevel();
                }*/
                break;
            case MAIN_MENU:
                if(fade<90){
                    fade += 9f;
                }else{
                    glColor3f(0.5f,0,0);
                    glRectf(0,0,Display.getWidth(),Display.getHeight());
                    
                }
                glColor4f(0,0,0.5f,(float) Math.sin(Math.toRadians(fade)));
                glRectf(0,0,Display.getWidth(),Display.getHeight());
                break;
        }
    }
    
    public StateType getStateType(){
        return stateType;
    }
    public void setStateType(StateType stateType){
        this.stateType = stateType;
    }
}
