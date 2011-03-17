package nehe.jogl.lib;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import net.java.games.jogl.*;

/*
 * Created on 17 juin 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
/**
 * <br>
 * ---------------------------------------------------------------
 * <br>
 * This file was created on 12:44:10 by konik 
 * <br>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 * <br>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 * <br>
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 * 
 * @author konik (konik0001@msn.com)
 * based on jogl ports from nehe.gamedev.net site 
 */
abstract public class Renderer extends Frame implements GLEventListener,KeyListener{
	
	/*
	 * Frame per second calculator variable
	 */
	private int 	v_fps 		= 0;
	private long 	v_lastTime	= System.currentTimeMillis();
	private String  v_lastFPS	= "";
	/*
	 * General variable for showing the rendering pane
	 */
	private Dimension 	v_dimension 	= new Dimension(800,600);
	private GLCanvas	v_glcanvas  	= null;
	private Animator	v_animator		= null;
	private GLPrinter	v_printer		= null;
    private final int 	SIZE_OF_KEYS 	= 250;
    private boolean[] 	v_keys 			= new boolean[SIZE_OF_KEYS];
    private double			v_screenW		= 0;
    private double			v_screenH		= 0;
    /*
	 * user driven variables
	 */
	private boolean 	v_fullscreen	= false;
	private boolean 	v_showFPS		= false;
	
	public Renderer(String title){
		super(title);
		setOptions();
		v_screenW=Toolkit.getDefaultToolkit().getScreenSize().getSize().width;
		v_screenH=Toolkit.getDefaultToolkit().getScreenSize().getSize().height;
	   	if (v_fullscreen == true)
	        super.setSize(Toolkit.getDefaultToolkit().getScreenSize().getSize());
	    else
	        super.setSize(v_dimension);
	    // create a GLCapabilities object for the requirements for GL
		GLCapabilities glCapabilities = new GLCapabilities();
		//create a GLCamvas based on the requirements from above
		v_glcanvas = GLDrawableFactory.getFactory().createGLCanvas(glCapabilities);
		// add a GLEventListener, which will get called when the
		// canvas is resized or needs to be repainted
		v_glcanvas.addGLEventListener(this);
		//add the content page to the frame
		add(v_glcanvas, java.awt.BorderLayout.CENTER);
		v_animator = new Animator(v_glcanvas);
        addWindowListener(new WindowAdapter()
        		{
                    public void windowClosing(WindowEvent e)
                    {
                        System.exit(0);
                    }
                });
        if(v_fullscreen)setUndecorated(true);
        show();
        v_animator.start();
        v_glcanvas.requestFocus();
	}
	/**
	 * Show the options pane defined in options.java
	 */
	private void setOptions(){
        Options options = new Options();
        while(!(options.getOK()) && !(options.getCancel()))
        {
            try { Thread.sleep(5); } catch(InterruptedException ie) {}
        }
        if (options.getCancel())
        {
            System.out.println("User closed application.");
            System.exit(0);
        }
        
        v_fullscreen = (options.getFullscreen()? true:false);
        v_dimension = options.getPixels();
        //options.getBPP();
        options.setOff();
        options = null;
	}
	/**
	 * Set the FPS 
	 */
	private void setFPS(){
		if(System.currentTimeMillis()>v_lastTime+5000){
			float fps = (float)(v_fps*1000)/(float)(System.currentTimeMillis()-v_lastTime);
			v_fps=0;
			v_lastTime=System.currentTimeMillis();
			v_lastFPS = "fps: "+fps;
		}
		++v_fps;
	}
	/** Called by the drawable to initiate OpenGL rendering by the client.
	 * After all GLEventListeners have been notified of a display event, the 
	 * drawable will swap its buffers if necessary.
	 * @param gLDrawable The GLDrawable object.
	 */    
	public void display(GLDrawable gLDrawable)
	{
		setFPS();
		final GL gl = gLDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		drawPersonnals(gLDrawable);
		gl.glLoadIdentity();
		// if we must draw the FPS
		if(v_showFPS){
			v_printer.glPrint(1,(int)(v_screenH-16),v_lastFPS,0,1f,0f,0f);			
		}
	    //gl.glFlush();
	}
	
	/**
	 * This function must be implemented by the childrens
	 *
	 */
	abstract public void drawPersonnals(GLDrawable gLDrawable);
	
	 /** Called when the display mode has been changed.  <B>!! CURRENTLY UNIMPLEMENTED IN JOGL !!</B>
	* @param gLDrawable The GLDrawable object.
	* @param modeChanged Indicates if the video mode has changed.
	* @param deviceChanged Indicates if the video device has changed.
	*/
	 public void displayChanged(GLDrawable gLDrawable, boolean modeChanged, boolean deviceChanged)
	{
	}
	
	 /** Called by the drawable immediately after the OpenGL context is 
	* initialized for the first time. Can be used to perform one-time OpenGL 
	* initialization such as setup of lights and display lists.
	* @param gLDrawable The GLDrawable object.
	*/
	public void init(GLDrawable gLDrawable)
	{
		final GL gl = gLDrawable.getGL();
        final GLU glu = gLDrawable.getGLU();
		gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);    // Black Background
        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);	// Really Nice Perspective Calculations
		gLDrawable.addKeyListener(this);
		v_printer = new GLPrinter(gLDrawable,"/nehe/jogl/data/Font.bmp");
	}
	
	
	 /** Called by the drawable during the first repaint after the component has 
	* been resized. The client can update the viewport and view volume of the 
	* window appropriately, for example by a call to 
	* GL.glViewport(int, int, int, int); note that for convenience the component
	* has already called GL.glViewport(int, int, int, int)(x, y, width, height)
	* when this method is called, so the client may not have to do anything in
	* this method.
	* @param gLDrawable The GLDrawable object.
	* @param x The X Coordinate of the viewport rectangle.
	* @param y The Y coordinate of the viewport rectanble.
	* @param width The new width of the window.
	* @param height The new height of the window.
	*/
	public void reshape(GLDrawable gLDrawable, int x, int y, int width, int height)
	{
	  final GL gl = gLDrawable.getGL();
	  final GLU glu = gLDrawable.getGLU();
	
	  if (height <= 0) // avoid a divide by zero error!
	    height = 1;
	  final float h = (float)width / (float)height;
	  gl.glViewport(0, 0, width, height);
	  gl.glMatrixMode(GL.GL_PROJECTION);
	  gl.glLoadIdentity();
	  glu.gluPerspective(45.0f, h, 1.0, 20.0);
	  gl.glMatrixMode(GL.GL_MODELVIEW);
	  gl.glLoadIdentity();
	}
	
	/** Invoked when a key has been pressed.
	 * See the class description for {@link KeyEvent} for a definition of
	 * a key pressed event.
	 * @param e The KeyEvent.
	 */
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode()){
		  	case KeyEvent.VK_ESCAPE:
		  	{
	            v_animator.stop();
			    System.exit(0);
		  		break;
		  	}
		  	case KeyEvent.VK_F1:    //resize the application
	        {
	            setVisible(false);
	            if (v_fullscreen){
	                setSize(v_dimension);
	            }else{
	                setSize(Toolkit.getDefaultToolkit().getScreenSize().getSize());
	            }
	            setVisible(true);
            	v_fullscreen = !v_fullscreen;
            	break;
	        }
		  	case KeyEvent.VK_F2:
	        {
	            v_showFPS = !v_showFPS;
	            System.out.println("Show FPS:"+v_showFPS);
	            break;
	        }
            default :
                if(e.getKeyCode()<250) // only interested in first 250 key codes, are there more?
                   v_keys[e.getKeyCode()]=true;	
                break;
		} 
	}
	
	/** Invoked when a key has been released.
	 * See the class description for {@link KeyEvent} for a definition of
	 * a key released event.
	 * @param e The KeyEvent.
	 */
	public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < 250) { v_keys[e.getKeyCode()] = false; }
	}
	
	/** Invoked when a key has been typed.
	 * See the class description for {@link KeyEvent} for a definition of
	 * a key typed event.
	 * @param e The KeyEvent.
	 */
	public void keyTyped(KeyEvent e) {}
	/**
	 * @return Returns the v_keys.
	 */
	public boolean[] getKeys() {
		return v_keys;
	}
	/**
	 * @return Returns the v_printer.
	 */
	public GLPrinter getPrinter() {
		return v_printer;
	}
	/**
	 * @return Returns the v_screenH.
	 */
	public double getScreenH() {
		return v_screenH;
	}
}


