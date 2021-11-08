/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;

public class Fog {
    
    public Fog(float near, float far,Color color){
        glEnable(GL_FOG);
            FloatBuffer fogColours = BufferUtils.createFloatBuffer(4);
            fogColours.put(new float[]{color.r, color.g, color.b, color.a});
            glClearColor(color.r, color.g, color.b, color.a);
            fogColours.flip();
            glFog(GL_FOG_COLOR, fogColours);
            glFogi(GL_FOG_MODE, GL_LINEAR);
            glHint(GL_FOG_HINT, GL_NICEST);
            glFogf(GL_FOG_START, near);
            glFogf(GL_FOG_END, far);
            glFogf(GL_FOG_DENSITY, 0.005f);
    }
}
