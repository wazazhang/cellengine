/*
 * Created on 17 juin 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package nehe.jogl.lib;

import java.io.InputStream;
import java.net.URL;

import net.java.games.jogl.GL;
import net.java.games.jogl.GLDrawable;
import net.java.games.jogl.GLU;

/**
 * <br>
 * ---------------------------------------------------------------
 * <br>
 * This file was created on 19:39:32 by konik 
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
public class TextureLoader {
	private GL 		gl		= null;
	private GLU 	glu		= null;
	private int[] 	texture = new int[1];
	
	/**
	 * Initialise and load the texture
	 * @param gld the GLDrawable object
	 * @param filename the filename of the texture
	 */
	public TextureLoader(GLDrawable gld,String filename){
    	texture[0]=-1;
		gl=gld.getGL();
        glu=gld.getGLU();
        loadGLTextures(filename);
	}
	/**
	 * Return the id of the texture
	 * @return Returns the texture.
	 */
	public int getTexture() {
		return texture[0];
	}

	/*
	 * Private methods
	 */
	/** This method loads textures into the texture array
	 * @param gl A GL object to reference when setting values for it
	 * @param glu A GLU object to reference when setting values for it
	 * @param imgLoc The string location of the image/texture to load.
	 */    
    private void loadGLTextures(String imgLoc)
    {
        // make room for the number of textures/filters
        gl.glGenTextures(1, texture);
        // Create Linear Filtered TextureLoader
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]); 
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        makeTexture(readImage(imgLoc), GL.GL_TEXTURE_2D, false);
    }
    //method made to load images based on if it is a bmp or png file...
    //the different types create diff. objects which are returned and tested for
    //when the textures are loaded...
    //the classes themselves were created by other people whos credit is in the file
    //however those files are edited by me to best suit my needs and wants for my apps
    /** This program makes a series of calls to read an image
     * and return an Object that has already been run just
     * so that information can be obtained about the image.
     * @param resource Tells the program which image to use.
     * @return Returns an Object that is from the package
     * LoadImageTypes
     */    
    private Object readImage(String resource)
    {
        if (resource.endsWith("png"))
        {
            LoadPNGImages loadPNGImage = new LoadPNGImages();
            loadPNGImage.getDataAsByteBuffer(getResourceA(resource));
            return loadPNGImage;
        }
        else if (resource.endsWith("bmp"))
        {
            LoadBMPImages loadBMPImage = new LoadBMPImages();
            //needed to load a stream in, plus it is better for this anyway
            //check the source of LoadBMPImages if you really want to know
            loadBMPImage.generateTextureInfo(getResourceB(resource), resource, false);
            return loadBMPImage;
        }
        else { 
        	throw new RuntimeException("Unsupported file type : currently support only PNG and BMP files"); 
        }
    }
    /** Retrieve a URL resource from the jar.  If the resource is not found, then
     * the local disk is also checked.
     * @param filename Complete filename, including parent path
     * @return a URL object if resource is found, otherwise null.
     */  
    private URL getResourceA(String filename)
    {
      URL url = getClass().getResource(filename);
      if (url == null)
      {
        System.out.println("URL is null...");
        try { url = new URL("file", "localhost", filename); }
        catch (Exception urlException) {
        	throw new RuntimeException("Unable to aquire ressource :"+filename+"\n"+urlException.getMessage());
        } 
      }
      return url;
    }
    
    /** Retrieve an InputStream resource from the jar.  If the resource is not found, then
     * the local disk is also checked.
     * @param filename Complete filename, including parent path
     * @return a InputStream object if resource is found, otherwise null.
     */  
    private InputStream getResourceB(String filename)
    {
      // Try to load resource
      InputStream stream = getClass().getResourceAsStream(filename);
      if (stream == null)
      {
    	throw new RuntimeException("Unable to aquire ressource :"+filename+"\n");
      }
      return stream;
    }
    /* Helper methods for getting textures */
    //Definitely gotten from Kevin Duling (jattier@hotmail.com) in his ports to the NeHe
    //tuts. Same with the few methods that follow. I had to change the method getResource() to work 
    //better for locating the image and returning a valid resource. I have also changed some other
    //things but just to make it nicer and so that it fits better with my scheme of programming.
    private void makeTexture(Object img, int target, boolean mipmapped)
    {
      if (mipmapped)
      {
        if (img instanceof LoadBMPImages)
            glu.gluBuild2DMipmaps(target, 
                                    GL.GL_RGB8, 
                                    ((LoadBMPImages)img).getWidth(), 
                                    ((LoadBMPImages)img).getHeight(), 
                                    GL.GL_RGB, 
                                    GL.GL_UNSIGNED_BYTE, 
                                    ((LoadBMPImages)img).getData());
        else if (img instanceof LoadPNGImages)
            glu.gluBuild2DMipmaps(target, 
                                    GL.GL_RGB8, 
                                    ((LoadPNGImages)img).getWidth(), 
                                    ((LoadPNGImages)img).getHeight(), 
                                    GL.GL_RGB, 
                                    GL.GL_UNSIGNED_BYTE, 
                                    ((LoadPNGImages)img).getDest());
      }
      else
      {
        if (img instanceof LoadBMPImages)
            gl.glTexImage2D(target, 
                            0, 
                            3, 
                            ((LoadBMPImages)img).getWidth(), 
                            ((LoadBMPImages)img).getHeight(), 
                            0,
                            GL.GL_RGB, 
                            GL.GL_UNSIGNED_BYTE, 
                            ((LoadBMPImages)img).getData());
        else if (img instanceof LoadPNGImages)
            gl.glTexImage2D(target, 
                            0, 
                            3,  
                            ((LoadPNGImages)img).getWidth(), 
                            ((LoadPNGImages)img).getHeight(), 
                            0,
                            GL.GL_RGB, 
                            GL.GL_UNSIGNED_BYTE, 
                            ((LoadPNGImages)img).getDest());
      }
    }
}
