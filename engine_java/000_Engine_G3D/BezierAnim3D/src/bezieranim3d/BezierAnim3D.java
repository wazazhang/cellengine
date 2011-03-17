/**
 * Copyright (c) 2007, Sun Microsystems, Inc.
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
 *   * Neither the name of the BezierAnim3D project nor the names of its
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

package bezieranim3d;

import bezieranim3d.gl.Camera;
import bezieranim3d.gl.EvaluatorPoint3f;
import bezieranim3d.PhotoCube;
import bezieranim3d.gl.Point3f;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.font.*;
import java.text.*;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import static javax.media.opengl.GL.*;
import com.sun.opengl.util.j2d.*;
import com.sun.opengl.util.texture.*;

import org.jdesktop.animation.timing.*;
import org.jdesktop.animation.timing.interpolation.*;
import org.jdesktop.animation.timing.triggers.*;

/**
 * The main class that includes all the JOGL event listeners, the
 * animator setup code; basically, all of the application logic.
 *
 * @author Chris Campbell
 */
public class BezierAnim3D implements GLEventListener {

    private static final Point3f cameraStart = new Point3f(0f, 0f, 70f);
    private static final GLU glu = new GLU();

    private BezierAnim bezierAnim;
    private TextureRenderer animRenderer;
    private Animator bezierFader, reflectionFader;
    private float bezierAlpha, reflectionAlpha;

    private PhotoCube cube;
    private Animator cubeFader, cubeRotator;

    private Camera camera;
    private Animator cameraAnimator;

    private TextRenderer textRenderer, altRenderer;
    private String fpsText;
    private int fpsWidth;
    private long startTime;
    private int frameCount;
    private DecimalFormat format = new DecimalFormat("####.00");

    private Animator textFader, textRotator, textColorer;
    private int textIndex;
    private Color textColor = Color.WHITE;
    private float textAlpha, textAngle;

    private static final Object[] data = new Object[] {
        "Rendering text in JOGL apps used to be difficult...", 0.25f,
        "But now it's dead simple, thanks to JOGL's TextRenderer class.", 0.5f,
        "You can position text anywhere (even in 3D space)...", 0.75f,
        "You can change the color...", 0.25f,
        "Or you can use a different font...", 0.5f,
        "But let's move on to something a bit cooler...", 0.75f,
        "You've probably seen Java 2D demos like this before...", 0.95f,
        "And Romain has shown you how to add reflection tricks...", 0.95f,
        "But did you know it was easy to use dynamic Java 2D content...", 0.95f,
        "... in a 3D space?", 0.95f,
        "Well, now it's just that easy, using JOGL's TextureRenderer class.", 0.95f,
        "The ease of Java 2D images, and the power of OpenGL textures...", 0.95f,
        "... all rolled into one.", 0.95f,
        "You can plaster them anywhere, like on this cube, for example...", 0.95f,
        "Anyway, that's it.  Demo over.  Time to go home.", 0.95f,
    };

    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.setSwapInterval(0);
        gl.glEnable(GL_DEPTH_TEST);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        initTextRenderer();
        initAnimRenderer();

        initCamera();
        initCube();
        initTimers();
    }

    private void initCamera() {
        camera = new Camera();
        camera.setLocation(cameraStart);
    }

    private void initCube() {
        cube = new PhotoCube();
        cube.setSize(20f);
        cube.setAlpha(0f);
        cube.setLocation(new Point3f(35f, 0f, 0f));
        cube.setOverride(animRenderer.getTexture());
    }

    private void initTimers() {
        // Text fader
        textFader = PropertySetter.createAnimator(6500, this, "textAlpha",
                                                  0f, 1f, 1f, 1f, 0f);
        textFader.setStartDelay(1500);
        textFader.setRepeatCount(data.length/2);
        textFader.setAcceleration(0.3f);
        textFader.addTarget(new TimingTargetAdapter() {
            public void repeat() {
                textIndex++;
                switch (textIndex) {
                case 2:
                    textRotator.start();
                    break;
                case 3:
                    textColorer.start();
                    break;
                case 6:
                    bezierFader.start();
                    break;
                case 7:
                    reflectionFader.start();
                    break;
                case 8:
                    cameraAnimator.start();
                    break;
                case 11:
                    cubeFader.start();
                    break;
                case 14:
                    textColor = Color.GREEN;
                    break;
                default:
                    break;
                }
            }
        });

        // Text rotator
        textRotator = PropertySetter.createAnimator(1500, this, "textAngle",
                                                    0f, 360f);
        textRotator.setStartDelay(3200);
        textRotator.setAcceleration(0.3f);
        textRotator.setDeceleration(0.5f);

        // Text color animator
        textColorer = PropertySetter.createAnimator(3500, this, "textColor",
                                                    Color.WHITE, Color.RED,
                                                    Color.GREEN, Color.BLUE,
                                                    Color.WHITE, Color.WHITE);
        textColorer.setStartDelay(700);

        // BezierAnim fader
        bezierFader = PropertySetter.createAnimator(4000, this,
                                                    "bezierAlpha",
                                                    0f, 1f);
        bezierFader.setStartDelay(2000);
        bezierFader.setAcceleration(0.4f);

        // BezierAnim reflection fader
        reflectionFader = PropertySetter.createAnimator(3000, this,
                                                        "reflectionAlpha",
                                                        0f, 1f);
        reflectionFader.setAcceleration(0.4f);

        // Camera animator (note that we use KeyFrames and PropertySetter
        // as a more convenient way to drive this animation)
        Point3f cameraMid = new Point3f(0f, 20f, 77f);
        Point3f cameraEnd = new Point3f(-30f, 30f, 82f);
        KeyValues values = KeyValues.create(new EvaluatorPoint3f(),
                                            cameraStart, cameraMid, cameraEnd);
        KeyTimes times = new KeyTimes(0f, 0.25f, 1f);
        KeyFrames frames = new KeyFrames(values);
        PropertySetter ps = new PropertySetter(camera, "location", frames);
        cameraAnimator = new Animator(4000, ps);
        cameraAnimator.setStartDelay(4000);
        cameraAnimator.setAcceleration(0.2f);
        cameraAnimator.setDeceleration(0.3f);

        // Cube fader
        cubeFader = PropertySetter.createAnimator(3000, cube, "alpha", 0f, 1f);
        cubeFader.setStartDelay(3000);
        cubeFader.setAcceleration(0.4f);

        // Cube rotator
        ps = new PropertySetter(cube, "angle", 0f, 360f*8f);
        cubeRotator = new Animator(10000, Animator.INFINITE, null, ps);
        cubeRotator.setAcceleration(0.4f);
        cubeRotator.setDeceleration(0.3f);
        cubeRotator.addTarget(new TimingTargetAdapter() {
            public void repeat() {
                cube.setRotateSideToSide(!cube.isRotateSideToSide());
            }
        });

        // Chain the animations together and kick it all off...
        cubeFader.addTarget(
            TimingTrigger.createTrigger(cubeRotator,
                                        TimingTriggerEvent.STOP));
        textFader.start();
    }

    private void initTextRenderer() {
        // Create the text renderer
        Font font = new Font("SansSerif", Font.BOLD, 24);
        textRenderer = new TextRenderer(font, true, false);
    }

    private void displayDemoText(GLAutoDrawable drawable) {
        TextRenderer renderer;

        if (textIndex == 4) {
            // Just a simple hack that changes the font in the middle
            // of the demo...
            if (altRenderer == null) {
                Font font = new Font("Monospaced", Font.BOLD, 32);
                altRenderer = new TextRenderer(font, true, false);
            }
            renderer = altRenderer;
        } else {
            renderer = textRenderer;
        }

        // Calculate text location and color
        String str = (String)data[textIndex*2];
        float pos = (Float)data[textIndex*2+1];
        int textW = (int)renderer.getBounds(str).getWidth();
        int x = (drawable.getWidth() / 2) - (textW / 2);
        int y = drawable.getHeight() - (int)(drawable.getHeight() * pos);
        float r = textColor.getRed()   / 255f;
        float g = textColor.getGreen() / 255f;
        float b = textColor.getBlue()  / 255f;

        if (textIndex != 2) {
            // Render the text in 2D
            renderer.beginRendering(drawable.getWidth(), drawable.getHeight());
            renderer.setColor(r, g, b, textAlpha);
            renderer.draw(str, x, y);
            renderer.endRendering();
        } else {
            // Render the text in 3D
            GL gl = drawable.getGL();
            renderer.begin3DRendering();
            renderer.setColor(r, g, b, textAlpha);
            gl.glRotatef(textAngle, 0f, 1f, 0f);
            renderer.draw3D(str, -30f, -17f, 0f, 0.1f);
            renderer.end3DRendering();
        }
    }

    private void displayFPSText(GLAutoDrawable drawable) {
        if (++frameCount == 100) {
            long endTime = System.currentTimeMillis();
            float fps = 100.0f / (float) (endTime - startTime) * 1000;
            frameCount = 0;
            startTime = System.currentTimeMillis();

            fpsText = "FPS: " + format.format(fps);
            if (fpsWidth == 0) {
                // Place it at a fixed offset wrt the upper right corner
                fpsWidth = (int)
                    textRenderer.getBounds("FPS: 10000.00").getWidth();
            }
        }

        if (fpsWidth == 0) {
            return;
        }

        // Calculate text location and color
        int x = drawable.getWidth() - fpsWidth - 5;
        int y = drawable.getHeight() - 30;
        float c = 0.55f;

        // Render the text
        textRenderer.beginRendering(drawable.getWidth(), drawable.getHeight());
        textRenderer.setColor(c, c, c, c);
        textRenderer.draw(fpsText, x, y);
        textRenderer.endRendering();
    }

    private void initAnimRenderer() {
        // Create the BezierAnim renderer
        bezierAnim = new BezierAnim();
        bezierAnim.reset(400, 300);
        animRenderer = new TextureRenderer(400, 300, false);
    }

    private void updateAnimRenderer() {
        int w = animRenderer.getWidth();
        int h = animRenderer.getHeight();
        Graphics2D g2d = animRenderer.createGraphics();
        bezierAnim.step(w, h);
        bezierAnim.render(w, h, g2d);
        g2d.dispose();
        animRenderer.markDirty(0, 0, w, h);
    }

    private void displayAnimRenderer(GLAutoDrawable drawable) {
        if (bezierAlpha == 0f) {
            return;
        }
        
        // Draw to the animRenderer's texture using Java 2D
        updateAnimRenderer();

        // Draw from the animRenderer's texture using JOGL
        GL gl = drawable.getGL();
        Texture tex = animRenderer.getTexture();
        TextureCoords tc = tex.getImageTexCoords();
        float tx1 = tc.left();
        float ty1 = tc.top();
        float tx2 = tc.right();
        float ty2 = tc.bottom();

        // Enable blending, using the SrcOver rule
        gl.glEnable(GL_BLEND);
        gl.glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        // Use the GL_MODULATE texture function to effectively multiply
        // each pixel in the texture by the current alpha value
        gl.glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

        float x = -37f;
        float y = -10f;
        float w = 45f;
        float h = w * 0.75f;

        tex.bind();
        tex.enable();
        gl.glBegin(GL.GL_QUADS);
        // Render image right-side up
        float a = bezierAlpha;
        gl.glColor4f(a, a, a, a);
        gl.glTexCoord2f(tx1, ty1); gl.glVertex3f(x  , y+h, 0f);
        gl.glTexCoord2f(tx2, ty1); gl.glVertex3f(x+w, y+h, 0f);
        gl.glTexCoord2f(tx2, ty2); gl.glVertex3f(x+w, y  , 0f);
        gl.glTexCoord2f(tx1, ty2); gl.glVertex3f(x  , y  , 0f);
        y -= h;
        // Render "reflected" image
        a = reflectionAlpha;
        gl.glColor4f(a*0.4f, a*0.4f, a*0.4f, a*0.4f);
        gl.glTexCoord2f(tx1, ty2); gl.glVertex3f(x  , y+h, 0f);
        gl.glTexCoord2f(tx2, ty2); gl.glVertex3f(x+w, y+h, 0f);
        gl.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(tx2, ty1+((ty2-ty1)/3)); gl.glVertex3f(x+w, y+h/3, 0f);
        gl.glTexCoord2f(tx1, ty1+((ty2-ty1)/3)); gl.glVertex3f(x  , y+h/3, 0f);
        gl.glEnd();
        tex.disable();

        gl.glDisable(GL_BLEND);
    }

    public void display(GLAutoDrawable drawable) {
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        }
        
        GL gl = drawable.getGL();
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();

        camera.setup(gl, glu);

        displayAnimRenderer(drawable);
        cube.render(gl);

        displayDemoText(drawable);
        displayFPSText(drawable);
    }
    
    public void reshape(GLAutoDrawable drawable,
                        int x, int y, int width, int height)
    {
        GL gl = drawable.getGL();

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();
        double aspectRatio = (double)width / (double)height;
        glu.gluPerspective(45.0, aspectRatio, 1.0, 400.0);
        
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void displayChanged(GLAutoDrawable drawable,
                               boolean modeChanged, boolean deviceChanged)
    {
    }

    public float getTextAlpha() { return textAlpha; }
    public void setTextAlpha(float alpha) { this.textAlpha = alpha; }

    public Color getTextColor() { return textColor; }
    public void setTextColor(Color color) { this.textColor = color; }

    public float getTextAngle() { return textAngle; }
    public void setTextAngle(float angle) { this.textAngle = angle; }

    public float getBezierAlpha() { return bezierAlpha; }
    public void setBezierAlpha(float alpha) { this.bezierAlpha = alpha; }

    public float getReflectionAlpha() { return reflectionAlpha; }
    public void setReflectionAlpha(float alpha) { this.reflectionAlpha = alpha; }

    public static void main(String[] args) {
        Frame frame = new Frame("BezierAnim3D");
        GLCapabilities caps = new GLCapabilities();
        if (args.length == 0 || args[0].equals("-noaa")) {
            caps.setSampleBuffers(true);
            caps.setNumSamples(4);
        }
        GLCanvas canvas = new GLCanvas(caps);
        canvas.addGLEventListener(new BezierAnim3D());
        frame.add(canvas);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        final com.sun.opengl.util.Animator animator =
            new com.sun.opengl.util.Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        frame.setVisible(true);
        animator.start();
    }
}
