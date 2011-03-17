package com.g3d.jogl.test;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class Pyramid 
{

    private float angle = 0.0f;
    
    public void drawScene(GLAutoDrawable drawable)
    {
    	GL gl = drawable.getGL();
    	
    	angle++;
    	
    	gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        
        gl.glTranslatef( 0.0f,0.0f,-6.0f );									// Move back 6 units
    	gl.glRotatef(angle, 0.0f, 1.0f, -0.1f);								// Rotate by angle

    	drawPyramid(drawable);    	
    }
    
    private void drawPyramid(GLAutoDrawable drawable)
    {
    	GL gl = drawable.getGL();
    	
    	gl.glPushMatrix();
			gl.glBegin(GL.GL_TRIANGLES);									// Begin drawing triangle sides
			
			gl.glColor3f( 1.0f, 0.0f, 0.0f);								// Set colour to red
			gl.glVertex3f( 0.0f, 1.0f, 1.0f);								// Top vertex
			gl.glVertex3f(-1.0f,-1.0f, 0.0f);								// Bottom left vertex
			gl.glVertex3f( 1.0f,-1.0f, 0.0f);								// Bottom right vertex
			
			gl.glColor3f( 0.0f, 1.0f, 0.0f);								// Set colour to green
			gl.glVertex3f( 0.0f, 1.0f, 1.0f);								// Top vertex
			gl.glVertex3f(-1.0f,-1.0f, 2.0f);								// Bottom left vertex
			gl.glVertex3f( -1.0f,-1.0f, 0.0f);								// Bottom right vertex
			
			gl.glColor3f( 0.0f, 0.0f, 1.0f);								// Set colour to blue
			gl.glVertex3f( 0.0f, 1.0f, 1.0f);								// Top vertex
			gl.glVertex3f(-1.0f,-1.0f, 2.0f);								// Bottom left vertex
			gl.glVertex3f( 1.0f,-1.0f, 2.0f);								// Bottom right vertex
			
			gl.glColor3f( 0.5f, 0.0f, 0.5f);								// Set colour to purple
			gl.glVertex3f( 0.0f, 1.0f, 1.0f);								// Top vertex
			gl.glVertex3f( 1.0f,-1.0f, 0.0f);								// Bottom left vertex
			gl.glVertex3f( 1.0f,-1.0f, 2.0f);								// Bottom right vertex
			gl.glEnd();														// Finish drawing triangle sides
		gl.glPopMatrix();
	
		gl.glPushMatrix();
			gl.glBegin(GL.GL_QUADS);										// Begin drawing square bottom
			
			gl.glColor3f( 1.0f, 1.0f, 0.0f);								// Set colour to yellow
			gl.glVertex3f(-1.0f,-1.0f, 0.0f);								// Bottom left vertex
			gl.glVertex3f(-1.0f,-1.0f, 2.0f);								// Top left vertex
			gl.glVertex3f( 1.0f,-1.0f, 2.0f);								// Bottom right vertex
			gl.glVertex3f( 1.0f,-1.0f, 0.0f);								// Top right vertex
			
			gl.glEnd();														// Finish drawing square bottom
		gl.glPopMatrix();
    }
    
}
