/*
 * Created on 17 juin 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package nehe.jogl.sample;

import nehe.jogl.lib.Renderer;
import net.java.games.jogl.GL;
import net.java.games.jogl.GLDrawable;

/**
 * <br>
 * ---------------------------------------------------------------
 * <br>
 * This file was created on 19:09:55 by konik 
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
 */
public class SampleJOGL extends Renderer{
	
	/* (non-Javadoc)
	 * @see nehe.jogl.lib.Renderer#drawPersonnals(net.java.games.jogl.GLDrawable)
	 */
	public void drawPersonnals(GLDrawable gLDrawable) {
		/*
		 * Draw a single square on the middle left of the screen
		 */
		final GL gl = gLDrawable.getGL();
		gl.glTranslatef(-1.5f,0.0f,-6.0f);
        gl.glDisable(GL.GL_BLEND);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glBegin(GL.GL_QUADS);
	        gl.glVertex3f(-1.0f, 1.0f, 0.0f);				// Top Left
	        gl.glVertex3f( 1.0f, 1.0f, 0.0f);				// Top Right
	        gl.glVertex3f( 1.0f,-1.0f, 0.0f);				// Bottom Right
	        gl.glVertex3f(-1.0f,-1.0f, 0.0f);				// Bottom Left
        gl.glEnd();
        gl.glEnable(GL.GL_BLEND);
	}
	/**
	 * @param title
	 */
	public SampleJOGL(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args)
	{
		// create a new frame with the setted title
		SampleJOGL rdr=new SampleJOGL("Konik's OpenGL framework");
	}
}
