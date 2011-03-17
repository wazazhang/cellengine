/**
 * Copyright (c) 2006, Sun Microsystems, Inc.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following 
 *     disclaimer in the documentation and/or other materials provided 
 *     with the distribution.
 *   * Neither the name of the PhotoCube project nor the names of its
 *     contributors may be used to endorse or promote products derived 
 *     from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package photocube;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureCoords;
import com.sun.opengl.util.texture.TextureIO;
import java.io.IOException;
import javax.media.opengl.GL;
import static javax.media.opengl.GL.*;

/**
 * A simple representation of a cube with photos plastered on each of
 * the six faces.
 *
 * @author Chris Campbell
 */
public class PhotoCube {
    
    private Texture[] textures;
    private float size;
    private float alpha;
    
    /**
     * Default constructor.  Initializes the size to 10f and alpha to 1f.
     */
    public PhotoCube() {
        this.size = 10.0f;
        this.alpha = 1.0f;
    }

    /**
     * Loads each of the six images into texture memory.
     *
     * REMIND: This method currently uses hardcoded filenames.  For extra
     * credit, one could pass these names as method parameters, to allow
     * for loading the images from other locations, e.g. from the disk or
     * over the network.
     */
    public void initTextures() {
        if (textures != null) {
            for (Texture t : textures) {
                t.dispose();
            }
        } else {
            textures = new Texture[6];
        }
        textures[0] = createTexture("photos/rhinecliff.jpg");
        textures[1] = createTexture("photos/candice.jpg");
        textures[2] = createTexture("photos/relics.jpg");
        textures[3] = createTexture("photos/tmsk.jpg");
        textures[4] = createTexture("photos/caldera.jpg");
        textures[5] = createTexture("photos/mule.jpg");
    }
    
    /**
     * Loads an image from the application JAR with the given filename 
     * and then makes a texture out of it.  The texture is set to use
     * "linear" (smooth) filtering by default.
     * <p>
     * REMIND: Ideally, we would load these textures at application
     * startup time (on a separate thread).  Currently, we're doing this
     * on the Event Dispatch Thread, which means there will be a noticeable
     * lag when the window is first shown or when it is resized, as we load
     * the textures from disk.  I'll leave this as an exercise for the reader.
     * <p>
     * (Hint: use TextureIO.newTextureData() to load the textures from disk
     * on a separate thread at startup and keep them in memory.  Then when
     * DemoPanel.init3DResources() is called, pass each TextureData object to
     * TextureIO.newTexture(), which will cause the textures to be loaded
     * into video memory.)
     */
    private Texture createTexture(String filename) {
        Texture t = null;
        try {
             t = TextureIO.newTexture(PhotoCube.class.getResource(filename),
                                      false, ".jpg");
             t.setTexParameteri(GL_TEXTURE_MIN_FILTER, GL_LINEAR);
             t.setTexParameteri(GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        } catch (IOException e) {
            System.err.println("Error loading " + filename);
        }
        return t;
    }
    
    /**
     * Renders the given texture so that it is centered within the given
     * dimensions.
     */
    private void renderFace(GL gl, Texture t, float w, float h) {
        TextureCoords tc = t.getImageTexCoords();
        float tx1 = tc.left();
        float ty1 = tc.top();
        float tx2 = tc.right();
        float ty2 = tc.bottom();
        
        int imgw = t.getImageWidth();
        int imgh = t.getImageHeight();
        if (imgw > imgh) {
            h *= ((float)imgh) / imgw;
        } else {
            w *= ((float)imgw) / imgh;
        }
        float w2 = w/2f;
        float h2 = h/2f;

        t.enable();
        t.bind();
        gl.glColor4f(alpha, alpha, alpha, alpha);
        gl.glBegin(GL_QUADS);
        gl.glTexCoord2f(tx1, ty1); gl.glVertex3f(-w2,  h2, 0f);
        gl.glTexCoord2f(tx2, ty1); gl.glVertex3f( w2,  h2, 0f);
        gl.glTexCoord2f(tx2, ty2); gl.glVertex3f( w2, -h2, 0f);
        gl.glTexCoord2f(tx1, ty2); gl.glVertex3f(-w2, -h2, 0f);
        gl.glEnd();
        t.disable();
    }
    
    /**
     * Renders the cube into the given graphics context.  The current OpenGL
     * transformation matrix determines where the cube is positioned in
     * world coordinates.
     */
    public void render(GL gl) {
        if (textures == null) {
            // REMIND: ideally we would load these on a separate thread
            initTextures();
        }
        
        if (alpha == 0f) {
            return;
        }
        
        float size2 = size * 0.75f;
        
        if (alpha <= 1.0f) {
            // enable blending, using the SrcOver rule
            gl.glEnable(GL_BLEND);
            gl.glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        }
        
        // use the GL_MODULATE texture function to effectively multiply
        // each pixel in the texture by the current alpha value (this controls
        // the opacity of the cube)
        gl.glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
        
        // front
        gl.glPushMatrix();
        gl.glTranslatef(0f, 0f, size2);
        renderFace(gl, textures[0], size, size);
        gl.glPopMatrix();
        
        // left
        gl.glPushMatrix();
        gl.glRotatef(270.0f, 0f, 1f, 0f);
        gl.glTranslatef(0f, 0f, size2);
        renderFace(gl, textures[1], size, size);
        gl.glPopMatrix();

        // back
        gl.glPushMatrix();
        gl.glRotatef(180.0f, 0f, 1f, 0f);
        gl.glTranslatef(0f, 0f, size2);
        renderFace(gl, textures[2], size, size);
        gl.glPopMatrix();
        
        // right
        gl.glPushMatrix();
        gl.glRotatef(90.0f, 0f, 1f, 0f);
        gl.glTranslatef(0f, 0f, size2);
        renderFace(gl, textures[3], size, size);
        gl.glPopMatrix();
        
        // top
        gl.glPushMatrix();
        gl.glRotatef(270.0f, 1f, 0f, 0f);
        gl.glTranslatef(0f, 0f, size2);
        renderFace(gl, textures[4], size, size);
        gl.glPopMatrix();

        // bottom
        gl.glPushMatrix();
        gl.glRotatef(90.0f, 1f, 0f, 0f);
        gl.glTranslatef(0f, 0f, size2);
        renderFace(gl, textures[5], size, size);
        gl.glPopMatrix();
        
        if (alpha <= 1.0f) {
            gl.glDisable(GL_BLEND);
        }
    }
    
    public float getAlpha() {
        return alpha;
    }
    
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}
