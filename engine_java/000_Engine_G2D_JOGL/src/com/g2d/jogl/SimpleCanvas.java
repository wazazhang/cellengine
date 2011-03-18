package com.g2d.jogl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLJPanel;
import javax.media.opengl.glu.GLU;

import com.g2d.Canvas;
import com.g2d.jogl.impl.GLCanvasAdapter;
import com.g2d.jogl.impl.GLEngine;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;

public class SimpleCanvas extends GLJPanel
{
    private FPSAnimator		animator;

    private GLCanvasAdapter	canvas;
    
    public SimpleCanvas(int w, int h)
    {	
    	super(GLEngine.getEngine().getGLCapabilities());
    	super.setLayout(null);
    	super.setSize(w, h);
    	super.setMinimumSize(new Dimension(w, h));
    	this.canvas = new GLCanvasAdapter(this, w, h);
        this.addGLEventListener(canvas);
	}
    
    public void setFPS(int fps) {
    	stop();
		start(fps);
    }
    
    public Canvas getCanvasAdapter() {
    	return canvas;
    }
    
    public void start(int fps) {
		if (animator == null) {
			animator = new FPSAnimator(fps);
			animator.add(this);
			animator.start();
		}
    }
    
    public void stop() {
    	if (animator != null) {
        	animator.stop();
        	animator.remove(this);
    	}
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
    		BufferedImage buff = ((Graphics2D)g).getDeviceConfiguration().createCompatibleImage(
    				200, 200,
    				Transparency.TRANSLUCENT);
    
    		Graphics2D g2d = buff.createGraphics();
    		try {
	            g2d.setColor(Color.GREEN);
	        	g2d.drawString("FPS=" + canvas.getFPS() + " childs=" + canvas.getStage().getChildCount(), 10, 10);
    		} finally {
    			g2d.dispose();
    		}
        	g.drawImage(buff, 0, 0, null);
        } catch (Exception err) {}
    }
    
}
