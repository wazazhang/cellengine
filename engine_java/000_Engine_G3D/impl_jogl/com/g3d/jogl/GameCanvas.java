package com.g3d.jogl;

import java.awt.Canvas;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.g3d.jogl.test.Imaging;
import com.g3d.jogl.test.Pyramid;
import com.g3d.jogl.test.SolarSystem;
import com.g3d.jogl.test.TextLayer;
import com.g3d.jogl.test.TextureImage;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;

@SuppressWarnings("serial")
public class GameCanvas extends GLCanvas implements GLEventListener
{
    private GLU 		glu;
    private GLUT 		glut;
    private int 		w, h;
    private Animator 	animator;

    private int			main_timer;
    private long		last_update_time = System.currentTimeMillis();
    private float		last_fps = 1f;
    
    
    public GameCanvas(GLCapabilities capabilities)
    {
    	super(capabilities);
        this.addGLEventListener(this);
	}
    
    
    public void setFPS(int fps) {
    	stop();
		start(fps);
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
    
    public float getFPS() {
    	return last_fps;
    }
    
//	---------------------------------------------------------------------------------------------------------------
//	
//	---------------------------------------------------------------------------------------------------------------

    Imaging 		imaging = new Imaging("/com/g3d/jogl/test/image.png");
    TextureImage 	timaging;
    TextLayer		text;
    public void init(GLAutoDrawable drawable) 
    {
    	w = drawable.getWidth();
        h = drawable.getHeight();
        
        GL gl = drawable.getGL();
        glu = new GLU();
        glut = new GLUT();

		gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearColor(0.0f,0.0f,0.0f,0.0f);
        gl.glClearDepth(1.0f);												// Depth Buffer Setup
//    	gl.glEnable(GL.GL_DEPTH_TEST);										// Enables Depth Testing
//    	gl.glDepthFunc(GL.GL_LEQUAL);										// The Type Of Depth Test To Do
//    	gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);			// Really Nice Perspective Calculations
        
        imaging = new Imaging("/com/g3d/jogl/test/image.png");
        
        timaging = new TextureImage(gl);
		timaging.init("/com/g3d/jogl/test/image.png");
		
		text = new TextLayer();
	}

    public void display(GLAutoDrawable drawable) 
    {
        GL gl = drawable.getGL();
        
        w = drawable.getWidth();
        h = drawable.getHeight();

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
//		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);
//		gl.glBlendFunc(GL.GL_ONE , GL.GL_ZERO ); // 源色将覆盖目标色。
//		gl.glBlendFunc(GL.GL_ZERO , GL.GL_ONE ); // 目标色将覆盖源色。 
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA); // 是最常使用的。
//		gl.glBlendFunc(GL.GL_SRC_ALPHA_SATURATE, GL.GL_ONE);

    	gl.glEnable(GL.GL_BLEND);
        gl.glDisable(GL.GL_DEPTH_TEST);			
//		gl.glDepthMask(false); 
        
    	text.draw(gl, 200, 200);

//		gl.glColor4f(1.0f, 1.0f, 1.0f, 1f);
    	int x = 0;
    	int y = 0;
        for (int i=0; i<100; i++) {
        	timaging.draw(x, y);
        	if (x < w) {
            	x += timaging.getWidth();
        	} else {
        		x = 0;
        		y += timaging.getHeight()/2;
        	} 
        }
        
    	text.draw(gl, 100, 100);

        
        main_timer ++;
        long curtime = System.currentTimeMillis();
        long interval = curtime - last_update_time;
        if (interval > 0) {
        	 last_fps = (10000 / interval) / 10f;
        }
        last_update_time = curtime;
    }

	public void reshape(GLAutoDrawable drawable, int x, int y, int w2, int h2) 
    {
		GL gl = drawable.getGL();
        
        w = drawable.getWidth();
        h = drawable.getHeight();
        
        gl.glViewport(0, 0, w, h);											// Reset The Current Viewport
        gl.glMatrixMode(GL.GL_PROJECTION);									// Select The Projection Matrix
        gl.glLoadIdentity();												// Reset The Projection Matrix
//		glu.gluPerspective(45.0f,(float)w/(float)h,0.1f,100.0f);			// Calculate The Aspect Ratio Of The Window
        gl.glMatrixMode(GL.GL_MODELVIEW);	
        gl.glLoadIdentity();												// Reset The Modelview Matrix     
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) 
    {
    	
    }
    
//	---------------------------------------------------------------------------------------------------------------
//	
//	---------------------------------------------------------------------------------------------------------------
    


   


}
