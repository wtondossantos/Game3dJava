package main;

import engine.*;
import entity.*;
import entity.helper.EntityType;
import entity.helper.Helper;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;

public class GamePanel{
    
    public static final int SCALE = 2;
    public static final int WIDTH = 512*SCALE;
    public static final int HEIGHT = WIDTH/16 * 9;
    
    private static final boolean fullscreen = false, resizable = true;
    private static final float zNear = 0.3f, zFar = 500f;
    private static long lastFps, lastFrame;
    private static final int fov = 68;
    
    private static float[] lightAmbient = {3, 3, 3.4f, 1};
    private static float[] lightDiffuse = {5, 5, 4.5f, 1};
    private static float[] lightPosition = {1.5f, 1.5f, 1.5f, 1};
    
    private Texture texture;

    private int sceneList, boxList, objectList;
    private static int font;
    
    private static BufferedImage image = null;
    
    public Fog fog;
    public Helper helper;
    public StateType stateType = StateType.GAME;
    public State state;
    public Input input;
    public StyleFont styleFont;
    
    public GamePanel(){
        try {
            if (fullscreen) Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
            else Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
            
            Display.setResizable(resizable);
            Display.setTitle("Game Wton");
            Display.setVSyncEnabled(true);
            Display.create();
            
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public synchronized void start(){
        
        init();
        getDelta();
        lastFps = getTime();
        try {
            setUpTextures();
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            glCallList(boxList);
            glCallList(sceneList);
            glCallList(objectList);
            
        
            glLoadIdentity();
            
            glRotatef(input.getRotation().x, 2, 0, 0);
            glRotatef(input.getRotation().y, 0, 2, 0);
            glRotatef(input.getRotation().z, 0, 0, 2);
            glTranslatef(input.getPosition().x, input.getPosition().y, input.getPosition().z);
            lightPosition = new float[]{input.getPosition().x, input.getPosition().y, input.getPosition().z, 1};
            glLight(GL_LIGHT1, GL_POSITION, asFloatBuffer(lightPosition));
            
            update(getDelta());
            render();
            
            Display.update();
            Display.sync(60);
        }
        stop();
    }

    public synchronized void stop(){
        
        glDeleteLists(boxList, 1);
        glDeleteLists(sceneList, 1);
        glDeleteLists(objectList, 1);
        Display.destroy();
        System.exit(0);
        
    }

    public void init(){
        if (fullscreen) Mouse.setGrabbed(true);
        else Mouse.setGrabbed(false);
        
        font = glGenTextures();
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(fov, (float) Display.getWidth() / (float) Display.getHeight(), zNear, zFar);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        
        glEnable(GL_TEXTURE_2D);
        glShadeModel(GL_SMOOTH);
        glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        glClearDepth(1.0f);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        
        glLight(GL_LIGHT1, GL_AMBIENT, asFloatBuffer(lightAmbient));
        glLight(GL_LIGHT1, GL_DIFFUSE, asFloatBuffer(lightDiffuse));
        glLight(GL_LIGHT1, GL_POSITION, asFloatBuffer(lightPosition));
        glEnable(GL_LIGHT1);
        glEnable(GL_LIGHTING);
        
        fog = new Fog(15,80,new Color(1f, 1f, 1f, 1f));//nevoeiro
        styleFont = new StyleFont();
        helper = new Helper();
        state = new State(helper);
        state.setStateType(StateType.GAME);
        input = new Input(state);
        texture = new Texture(glGenTextures());
        texture.setPath("res/Spider_Guard_D.png");
        
        sceneList = glGenLists(1);
        glNewList(sceneList, GL_COMPILE);
            helper.addObject(new Floor(
                new Vector3f(0, 0, 0),
                new Vector3f(20, -1, 50),
                new Vector3f(0,0,0),
                new Color(.5f,.6f,.2f,1),
                "res/grass.png",
                .2f,
                EntityType.Floor)
            );
            
            helper.addObject(new Cube(
                    new Vector3f(-3f, 14.3f, -42.5f),
                    //new Vector3f(-2, 0, 0),
                    new Vector3f(6f, 4f, 7f),
                    new Vector3f(0,0,0),
                    new Color(.5f,.5f,.5f,0),
                    "res/gold.png",
                    EntityType.Gold)
            );
            /*
            helper.addObject(new Cube(
                    new Vector3f(-1f, -1f, 32),
                    new Vector3f(.5f, .5f, .5f),
                    new Vector3f(0,0,0),
                    new Color(1,1,1,1),
                    "res/wood.png",
                    EntityType.Wood)
            );
            helper.addObject(new Cube(
                    new Vector3f(1f, -1f, 32),
                    new Vector3f(.5f, .5f, .5f),
                    new Vector3f(0,0,0),
                    new Color(1,1,1,1),
                    "res/terrain.png",
                    EntityType.Terrain)
            );
            
            helper.addObject(new Cube(
                    new Vector3f(0, -1f, 32),
                    new Vector3f(.5f, .5f, .5f),
                    new Vector3f(0,0,0),
                    new Color(1,1,1,1),
                    "res/stone.png",
                    EntityType.Stone)
            );*/
        glEndList();
        
        boxList = glGenLists(1);
        glNewList(boxList, GL_COMPILE);
            helper.addObject(new Bullet(
                    new Vector3f(-.3f, -1, -3f),
                    new Vector3f(.5f, .5f, .5f),
                    new Vector3f(0,0,0),
                    new Color(1,1,1,1),
                    "res/bullet.png",
                    EntityType.Bullet)
            );
        glEndList();
        
        objectList = glGenLists(1);
        glNewList(objectList, GL_COMPILE); 
            helper.addObject(new ModelObject(
                    new Vector3f(0, -1, -25),
                    new Vector3f(1.5f, 1.5f, 1.5f),
                    new Vector3f(0,0,0),
                    new Color(1,1,1,1),
                    "res/Spider_Guard_D.png",
                    "res/Spider_Guard.obj",
                    EntityType.Spider)
            );
        glEndList();
    }
    
    public void update(int delta){
        helper.update();
        input.commandInput(helper, delta);
        
        if (resizable) {
            if (Display.wasResized()) {
                glViewport(0, 0, Display.getWidth(), Display.getHeight());
                glMatrixMode(GL_PROJECTION);
                glLoadIdentity();
                gluPerspective(fov, (float) Display.getWidth() / (float) Display.getHeight(), zNear, zFar);
                glMatrixMode(GL_MODELVIEW);
                glLoadIdentity();
            }
        }
        updateFps();
    }
    
    public void render(){
        
        styleFont.render("Ângulo profundidade: " + input.getBullet().getAngleTheta() + " Ângulo largura: " + input.getBullet().getAnglePhi(), font, 16,new Vector3f(input.getPosition().x-2.85f,input.getPosition().y+.7f,-input.getPosition().z-2.5f), 0.35f, 0.205f);
        styleFont.render("Aceleração: " + (float)input.getBullet().getAcceleration(), font, 16,new Vector3f(input.getPosition().x-2.85f,input.getPosition().y+.55f,-input.getPosition().z-2.5f), 0.35f, 0.205f);
        styleFont.render("Pontos: " + input.getScore(), font, 16,new Vector3f(input.getPosition().x-2.85f,input.getPosition().y+.4f,-input.getPosition().z-2.5f), 0.35f, 0.205f);
        state.render();
        helper.render();
        texture.render();
        
    }
    
    
    private static void setUpTextures() throws IOException {
        // Create a new texture for the bitmap font.
        font = glGenTextures();
        // Bind the texture object to the GL_TEXTURE_2D target, specifying that it will be a 2D texture.
        glBindTexture(GL_TEXTURE_2D, font);
        
            image = ImageIO.read(new File("res/font.png"));
            int w = image.getWidth();
            int h = image.getHeight();
            int[] pixels = image.getRGB(0, 0, w, h, null, 0, w);

            ByteBuffer buffer = BufferUtils.createByteBuffer(w * h *4);

            for(int x = 0; x<w; x++) {
                for(int y = 0; y<h; y++) {
                    Color color = new Color(pixels[x + y * w]);

                    buffer.put((byte)color.getRed());
                    buffer.put((byte)color.getGreen());
                    buffer.put((byte)color.getBlue());
                    buffer.put((byte)color.getAlpha());
                }
            }
            buffer.flip();
            
            // Load the previously loaded texture data into the texture object.
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, w, h, 0, GL_RGBA, GL_UNSIGNED_BYTE,buffer);
            // Unbind the texture.
            glBindTexture(GL_TEXTURE_2D, 0);
    }
    
    public static FloatBuffer asFloatBuffer(float[] values){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        
        return buffer;
    }
    
    private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private static int getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        lastFrame = getTime();
        
        return delta;
    }

    private static void updateFps() {
        if (getTime() - lastFps > 1000) {
            lastFps += 1000;
        }
    }
    
}
