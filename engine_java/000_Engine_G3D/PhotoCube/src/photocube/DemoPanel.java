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

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import org.jdesktop.animation.timing.Cycle;
import org.jdesktop.animation.timing.Envelope;
import org.jdesktop.animation.timing.TimingController;
import org.jdesktop.animation.timing.TimingEvent;
import org.jdesktop.animation.timing.TimingListener;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.interpolation.KeyFrames;
import org.jdesktop.animation.timing.interpolation.KeyTimes;
import org.jdesktop.animation.timing.interpolation.KeyValues;
import org.jdesktop.animation.timing.interpolation.ObjectModifier;
import org.jdesktop.animation.timing.interpolation.PropertyRange;
import photocube.gl.Camera;
import photocube.gl.KeyValuesPoint3f;
import photocube.gl.Point3f;
import photocube.gl.CompositeGLJPanel;

/**
 * The one and only panel in the application.  Displays and controls
 * the gradient background, photo cube, and text bubbles.
 *
 * @author Chris Campbell
 */
public class DemoPanel extends CompositeGLJPanel {

    private static final Point3f cameraStart = new Point3f(0f, 10f, 90f);
    
    private TextBubble bgText, fgText;
    private PhotoCube cube;
    private Camera camera;
    private TimingController bgAnimator, cubeFader,
                             cubeRotator, fgAnimator, cameraAnimator;
    private float cubeAngle;
    private boolean rotateSideToSide = true;
    private float bgAlpha;
    private JCheckBox cbSpinCube;
    
    public DemoPanel() {
        super(false, true);

        initComponents();
        initTextBubbles();
        initCamera();
        initCube();
        initTimers();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        cbSpinCube = new JCheckBox("Spin Cube", true) {
            @Override
            public void paintComponent(Graphics g) {
                // make the checkbox semi-translucent
                Graphics2D g2d = (Graphics2D)g;
                Composite oldcomp = g2d.getComposite();
                g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f*bgAlpha));
                super.paintComponent(g2d);
                g2d.setComposite(oldcomp);
            }
        };
        cbSpinCube.setOpaque(false);
        cbSpinCube.setForeground(new Color(110, 110, 128));
        cbSpinCube.setHorizontalAlignment(SwingConstants.LEFT);
        add(cbSpinCube, BorderLayout.SOUTH);
    }

    private void initTextBubbles() {
        bgText = new TextBubble("Some Java 2D on the background!");
        bgText.setForeground(Color.RED);
        bgText.setAlpha(0f);
        fgText = new TextBubble("Foreground rendered w/ Java 2D!");
        fgText.setAlpha(0f);
    }
    
    private void initCamera() {
        camera = new Camera();
        camera.setLocation(cameraStart);
    }
    
    private void initCube() {
        cube = new PhotoCube();
        cube.setSize(35f);
        cube.setAlpha(0f);
    }
    
    private void initTimers() {
        Cycle cycle;
        Envelope env;

        // Background animator
        cycle = new Cycle(2000, 20);
        env = new Envelope(1, 2000,
                           Envelope.RepeatBehavior.FORWARD,
                           Envelope.EndBehavior.HOLD);
        bgAnimator = new TimingController(cycle, env, new TimingTarget() {
            public void begin() {
            }
            public void timingEvent(long cycleElapsed, long totalElapsed, float frac) {
                bgAlpha = frac;
                bgText.setAlpha(bgAlpha);
                repaint();
            }
            public void end() {
                cubeFader.start();
            }
        });
        bgAnimator.setDeceleration(0.3f);
        
        // Cube fader
        cycle = new Cycle(3000, 20);
        env = new Envelope(1, 1000,
                           Envelope.RepeatBehavior.FORWARD,
                           Envelope.EndBehavior.HOLD);
        cubeFader = new TimingController(cycle, env, new TimingTarget() {
            public void begin() {
            }
            public void timingEvent(long cycleElapsed, long totalElapsed, float frac) {
                cube.setAlpha(frac);
                repaint();
            }
            public void end() {
                fgAnimator.start();
            }
        });
        cubeFader.setAcceleration(0.4f);

        // Foreground animator
        cycle = new Cycle(2000, 20);
        env = new Envelope(1, 0,
                           Envelope.RepeatBehavior.FORWARD,
                           Envelope.EndBehavior.HOLD);
        fgAnimator = new TimingController(cycle, env, new TimingTarget() {
            public void begin() {
            }
            public void timingEvent(long cycleElapsed, long totalElapsed, float frac) {
                fgText.setAlpha(frac);
                repaint();
            }
            public void end() {
                cameraAnimator.start();
            }
        });
        fgAnimator.setDeceleration(0.3f);
        
        // Camera animator (note that we use KeyFrames, PropertyRange, and
        // ObjectModifier as a more convenient way to drive this animation)
        cycle = new Cycle(4000, 20);
        env = new Envelope(1, 1000,
                           Envelope.RepeatBehavior.FORWARD,
                           Envelope.EndBehavior.HOLD);
        Point3f cameraMid = new Point3f(0f, 20f, 100f);
        Point3f cameraEnd = new Point3f(-80f, 60f, 110f);
        KeyValues values = new KeyValuesPoint3f(cameraStart, cameraMid, cameraEnd);
        KeyTimes times = new KeyTimes(0f, 0.25f, 1f);
        KeyFrames frames = new KeyFrames(values);
        PropertyRange range = new PropertyRange("location", frames);
        cameraAnimator = new TimingController(cycle, env, new ObjectModifier(camera, range) {
            public void timingEvent(long cycleElapsed, long totalElapsed, float frac) {
                super.timingEvent(cycleElapsed, totalElapsed, frac);
                repaint();
            }
            public void end() {
                cubeRotator.start();
            }
        });
        cameraAnimator.setAcceleration(0.2f);
        cameraAnimator.setDeceleration(0.3f);
        
        // Cube rotator (this time we'll use a simple PropertyRange
        // to control the cubeAngle property, just to show what's possible)
        cycle = new Cycle(7000, 20);
        env = new Envelope(TimingController.INFINITE, 1000,
                           Envelope.RepeatBehavior.FORWARD,
                           Envelope.EndBehavior.RESET);
        range = PropertyRange.createPropertyRangeFloat("cubeAngle", 0f, 360f*10f);
        cubeRotator = new TimingController(cycle, env, new ObjectModifier(this, range) {
            public void timingEvent(long cycleElapsed, long totalElapsed, float frac) {
                if (!cbSpinCube.isSelected()) {
                    return;
                }
                super.timingEvent(cycleElapsed, totalElapsed, frac);
                repaint();
            }
        });
        cubeRotator.addTimingListener(new TimingListener() {
            public void timerRepeated(TimingEvent e) {
                rotateSideToSide = !rotateSideToSide;
            }
            public void timerStarted(TimingEvent e) {
            }
            public void timerStopped(TimingEvent e) {
            }
        });
        cubeRotator.setAcceleration(0.4f);
        cubeRotator.setDeceleration(0.3f);
        
        bgAnimator.start();
    }
    
    @Override
    protected void render2DBackground(Graphics2D g2d) {
        if (bgAlpha <= 1f) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
        Composite oldcomp = g2d.getComposite();
        g2d.setComposite(AlphaComposite.SrcOver.derive(bgAlpha));
        g2d.setPaint(new GradientPaint(0, 0, new Color(110, 110, 128),
                                       0, getHeight(), Color.BLACK));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setComposite(oldcomp);
        bgText.render(g2d, getWidth()/8, getHeight()/3);
    }
    
    @Override
    protected void init3DResources(GL gl, GLU glu) {
        cube.initTextures();
    }
    
    @Override
    protected void render3DScene(GL gl, GLU glu) {
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        camera.setup(gl, glu);
        if (rotateSideToSide) {
            gl.glRotatef(cubeAngle, 0f, 1f, 0f);
        } else {
            gl.glRotatef(cubeAngle, 1f, 0f, 0f);
        }
        cube.render(gl);
    }
    
    @Override
    protected void render2DForeground(Graphics2D g2d) {
        fgText.render(g2d, getWidth()/2, getHeight()*2/3);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    public float getCubeAngle() {
        return cubeAngle;
    }

    public void setCubeAngle(float cubeAngle) {
        this.cubeAngle = cubeAngle;
    }
}
