package com.g2d.jogl.impl;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Transparency;
import java.awt.image.VolatileImage;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.g2d.AnimateCursor;
import com.g2d.Color;
import com.g2d.Font;
import com.g2d.display.Stage;
import com.g2d.java2d.CommonAnimateCursor;
import com.sun.opengl.util.GLUT;

public class GLCanvasAdapter extends com.g2d.java2d.CommonCanvasAdapter implements GLEventListener
{	 
	private GL 					gl;

	public GLCanvasAdapter(Component comp, int stage_width, int stage_height) 
	{
		super(comp, stage_width, stage_height);
	}
	
//	--------------------------------------------------------------------------------------------------------

	@Override
	public void init(GLAutoDrawable drawable) 
	{
        gl = drawable.getGL();
		gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearColor(0.0f,0.0f,0.0f,0.0f);
        gl.glClearDepth(1.0f);												// Depth Buffer Setup
//    	gl.glEnable(GL.GL_DEPTH_TEST);										// Enables Depth Testing
//    	gl.glDepthFunc(GL.GL_LEQUAL);										// The Type Of Depth Test To Do
//    	gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);			// Really Nice Perspective Calculations
        
	}
	
	@Override
	public void display(GLAutoDrawable drawable) 
	{
        gl = drawable.getGL();
        int w = drawable.getWidth();
        int h = drawable.getHeight();

        gl.glViewport(0, 0, w, h);											// Reset The Current Viewport
        gl.glMatrixMode(GL.GL_PROJECTION);									// Select The Projection Matrix
        gl.glLoadIdentity();												// Reset The Projection Matrix
//		glu.gluPerspective(45.0f,(float)w/(float)h,0.1f,100.0f);			// Calculate The Aspect Ratio Of The Window
        gl.glMatrixMode(GL.GL_MODELVIEW);	
        gl.glLoadIdentity();												// Reset The Modelview Matrix     

        gl.glClear(GL.GL_COLOR_BUFFER_BIT|GL.GL_DEPTH_BUFFER_BIT);			// Clear the colour and depth buffer

        gl.glScalef(1, -1, 1);
        gl.glOrtho(0, w, 0, h, -1, 1);
        
//		gl.glBlendFunc(GL.GL_ONE_MINUS_SRC_COLOR, GL.GL_ONE_MINUS_SRC_ALPHA);
//		gl.glBlendFunc(GL.GL_SRC_COLOR, GL.GL_SRC_COLOR);
//		gl.glBlendFunc(GL.GL_ONE, GL.GL_SRC_ALPHA);
//		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE); // 滤色ALPHA混白
//		gl.glBlendFunc(GL.GL_ONE , GL.GL_ZERO ); // 源色将覆盖目标色。
//		gl.glBlendFunc(GL.GL_ZERO , GL.GL_ONE ); // 目标色将覆盖源色。 
//		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA); // 是最常使用的。
//		gl.glBlendFunc(GL.GL_SRC_ALPHA_SATURATE, GL.GL_ONE);

//		gl.glDepthMask(false); 
        
		update((Graphics2D)getComponent().getGraphics());
        
        
	}

	
	@Override
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) 
	{
		
	}


	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{
		GL gl = drawable.getGL();
		int w = drawable.getWidth();
		int h = drawable.getHeight();
        
        gl.glViewport(0, 0, w, h);											// Reset The Current Viewport
        gl.glMatrixMode(GL.GL_PROJECTION);									// Select The Projection Matrix
        gl.glLoadIdentity();												// Reset The Projection Matrix
//		glu.gluPerspective(45.0f,(float)w/(float)h,0.1f,100.0f);			// Calculate The Aspect Ratio Of The Window
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
	}

//	--------------------------------------------------------------------------------------------------------

	/**
	 * 设置场景象素大小
	 * @param width
	 * @param height
	 */
	public void setStageSize(int width, int height)
	{
		super.setStageSize(width, height);
	}
	
//	--------------------------------------------------------------------------------
//	game
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println(getClass().getName() + " : finalize");
	}
	
	@Override
	protected void updateStage(java.awt.Graphics2D g, Stage currentStage)
	{
		if (currentStage != null)
		{
			g.setClip(0,0, getStageWidth(), getStageHeight());
			GLGraphics2D gl_g2d = new GLGraphics2D.GLGraphicsScreen(gl, g);
			try {
				currentStage.onUpdate(this, getStageWidth(), getStageHeight());
				currentStage.onRender(this, gl_g2d);
			} finally {
				gl_g2d.dispose();
			}
		}
	}
	
	
	private void test(GLGraphics2D gl_g2d)
	{
		gl_g2d.setClip(10, 10, 100, 100);
		// test
		gl_g2d.pushTransform();
		gl_g2d.pushClip();
		{
			gl_g2d.setAlpha(.5f);
	
			gl_g2d.setColor(Color.GREEN);
			{
				
				gl_g2d.rotate(.1);
				gl_g2d.translate(20, 20);
				
				test_draw(gl_g2d, 10, 10);
			}
			gl_g2d.setColor(Color.RED);
			{
				gl_g2d.translate(20, 20);
				test_draw(gl_g2d, 10, 10);
				test_draw(gl_g2d, 20, 20);
			}
			gl_g2d.setColor(Color.YELLOW);
			{
				gl_g2d.translate(100, 100);
				gl_g2d.scale(2, 2);
				gl_g2d.rotate(.1);
				test_draw(gl_g2d, 10, 10);
//				System.out.println(
//						getClipX() + "," + getClipY() + "," + 
//						getClipWidth() + "," + getClipHeight()) ;
			}
		}
		gl_g2d.popClip();
		gl_g2d.popTransform();
	}

	private void test_draw(GLGraphics2D gl_g2d, int x, int y)
	{
		gl_g2d.drawRect(x, y, 100, 100);
		gl_g2d.fillRect(x + 10, y + 10, 10, 10);
		
		gl_g2d.fillRect(x - 20, y + 50, 200, 20);
		gl_g2d.fillRect(x + 50, y - 20, 20, 200);
	}
	
}

