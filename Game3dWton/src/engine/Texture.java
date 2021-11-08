package engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;

public class Texture {
    private static BufferedImage image = null;
    private int texture;
    private String path;
    
    public Texture(int texture){
        this.texture = texture;
    }
        
    public void render(){
        try {
        
            image = ImageIO.read(new File(path));
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
            
            glBindTexture(GL_TEXTURE_2D, texture);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            glBindTexture(GL_TEXTURE_2D, 0);
            
        } catch (IOException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }
    }
        
    public void setPath(String path){this.path = path;}
    public String getPath(){return path;}
    
    public void setTexture(int texture){this.texture = texture;}
    public int getTexture(){return texture;}
    
}