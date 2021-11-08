package entity.helper;

import java.util.LinkedList;
import org.newdawn.slick.Color;
import org.lwjgl.util.vector.Vector3f;

public abstract class Entity {

    protected double gravity = 9.8f, time = 0.01f, acceleration = 0, initSpeed = 0, angleTheta = 0, anglePhi = 0;
    private float mass, weight, speedX = 0, speedY = -1, energy;
    protected int shunt = 0;
    
    protected Vector3f position, rotation, dimension;
    protected boolean selected = false;
    protected EntityType type;
    protected Color color;
    protected String path;
    
    public Entity(Vector3f position, Vector3f dimension, Vector3f rotation, Color color, String path, EntityType type){
        this.dimension = dimension;
        this.position = position;
        this.rotation = rotation;
        this.color = color;
        this.path = path;
        this.type = type;
    }
    
    public double getAnglePhi() {return anglePhi;}
    public void setAnglePhi(double a) {this.anglePhi = a;}
    
    public double getAngleTheta() {return angleTheta;}
    public void setAngleTheta(double a) {this.angleTheta = a;}
            
    public abstract void update(LinkedList<Entity> entity);
    public abstract void render();
    
    public Vector3f getDimension() {return dimension;}
    public void setDimension(Vector3f dimension) {this.dimension = dimension;}
    
    public Vector3f getPosition() {return position;}
    public void setPosition(Vector3f position) {this.position = position;}
    
    public Vector3f getRotation() {return rotation;}
    public void setRotation(Vector3f rotation) {this.rotation = rotation;}
    
    public Color getColor() {return color;}
    public void setColor(Color color) {this.color = color;}
    
    public EntityType getType() {return type;}
    public void setType(EntityType type) {this.type = type ;}
    
    
    public float getSpeedX() {return speedX;}
    public void setSpeedX(float s) {this.speedX = s;}
    
    public float getSpeedY() {return speedY;}
    public void setSpeedY(float s) {this.speedY = s;}
    
    public double getTime() {return time;}
    public void setTime(double t) {this.time = t;}
    
    public double getInitSpeed() {return initSpeed;}
    public void setInitSpeed(double s) {this.initSpeed = s;}
    
    public double getGravity() {return gravity;}
    public void setGravity(double g) {this.gravity = g;}
    
    public double getAcceleration() {return acceleration;}
    public void setAcceleration(double a) {this.acceleration = a;}
    
    public float getMass() {return mass;}
    public void setMass(float m) {this.mass = m;}
    
    public float getWeight() {return weight;}
    public void setWeight(float w) {this.weight = w;}
    
    public int getShunt() {return shunt;}
    public void setShunt(int s) {this.shunt = s;}
    
    public float getEnergy() {return energy;}
    public void setEnergy(float e) {this.energy = e;}
    
    public boolean getSelected() {return selected;}
    public void setSelected(boolean s){this.selected = s;}
    
}

