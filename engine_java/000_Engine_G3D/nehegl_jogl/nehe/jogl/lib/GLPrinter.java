/*
 * Created on 17 juin 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package nehe.jogl.lib;

import java.awt.Toolkit;

import net.java.games.jogl.GL;
import net.java.games.jogl.GLDrawable;
import net.java.games.jogl.GLU;


/**
 * <br>
 * ---------------------------------------------------------------
 * <br>
 * This file was created on 18:08:40 by konik 
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
 * based on work of Nicholas Campbell - campbelln@hartwick.edu
 */
public class GLPrinter {
	
	private int 	base	= 0;
	private int 	texture = 0;
	private GL 		gl		= null;
	private GLU 	glu		= null;
	private double	screenwidth 	= 0;
	private double	screenheight 	= 0;
    public GLPrinter(GLDrawable gLDrawable,String fileName)
    {
    	// Get the screen width and height
    	screenwidth=Toolkit.getDefaultToolkit().getScreenSize().getSize().getWidth();
    	screenheight=Toolkit.getDefaultToolkit().getScreenSize().getSize().getHeight();
        // init private variable
    	gl=gLDrawable.getGL();
        glu=gLDrawable.getGLU();
        // load the texture file
        TextureLoader myText = new TextureLoader(gLDrawable,fileName);
        texture = myText.getTexture();
        // create the list
        base = gl.glGenLists(256);
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture);
        //character coords
        float cx;
        float cy;
        // build the caracter list
        for (int loop = 0; loop < 256; loop++)
        {
            cx = ((float)(loop%16))/16.0f;    // X Position Of Current Character
            cy = ((float)(loop/16))/16.0f;	// Y Position Of Current Character
            gl.glNewList(base+loop, GL.GL_COMPILE);
                gl.glBegin(GL.GL_QUADS);
                    gl.glTexCoord2f(cx,1-cy-0.0625f);   // TextureLoader Coord (Bottom Left)
                    gl.glVertex2i(0,0);	// Vertex Coord (Bottom Left)
                    gl.glTexCoord2f(cx+0.0625f,1-cy-0.0625f);   // TextureLoader Coord (Bottom Right)
                    gl.glVertex2i(16,0);    // Vertex Coord (Bottom Right)
                    gl.glTexCoord2f(cx+0.0625f,1-cy);   // TextureLoader Coord (Top Right)
                    gl.glVertex2i(16,16);	// Vertex Coord (Top Right)
                    gl.glTexCoord2f(cx,1-cy);   // TextureLoader Coord (Top Left)
                    gl.glVertex2i(0,16);    // Vertex Coord (Top Left)
                gl.glEnd(); // Done Building Our Quad (Character)
                gl.glTranslated(10,0,0);    // Move To The Right Of The Character
            gl.glEndList();		
        }
    }
    
    /**
     * Print a text on the screen
     * @param x
     * @param y
     * @param text
     * @param set
     * @param gl
     */
    public void glPrint(int x, int y, String text,int set,float r,float g,float b)
    {
        int tmp = 0;
        if (set > 1) { set = 1; }
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glEnable(GL.GL_BLEND);
        gl.glColor3f(r,g,b);
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture);
        gl.glDisable(GL.GL_DEPTH_TEST);
        gl.glMatrixMode(GL.GL_PROJECTION);    // Select The Projection Matrix
    	gl.glPushMatrix();			// Store The Projection Matrix
                gl.glLoadIdentity();
                gl.glOrtho(0, screenwidth, 0, screenheight, -1, 1);
                gl.glMatrixMode(GL.GL_MODELVIEW);
                gl.glPushMatrix();
                	gl.glTranslated(x, y, 0);
                	gl.glListBase(base-32+(128*set));
                	gl.glCallLists(text.length(), GL.GL_BYTE, text.getBytes());
                gl.glMatrixMode(GL.GL_PROJECTION);	// Select The Projection Matrix
		        gl.glPopMatrix();	// Restore The Old Projection Matrix
		        gl.glMatrixMode(GL.GL_MODELVIEW);
		        gl.glPopMatrix();
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDisable(GL.GL_BLEND);
        gl.glDisable(GL.GL_TEXTURE_2D);
    }
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable {
		gl.glDeleteLists(base,256);
		super.finalize();
	}
}
