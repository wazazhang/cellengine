package com.g3d.jogl.test;
import java.io.*;
import java.nio.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;

import javax.imageio.ImageIO;
import javax.media.opengl.*;

public class TextureImage 
{
	final private GL 	gl ;

	private int 		w, h;
	
	/* OpenGL name for the sprite texture */
	private int[] 		gl_texture;
	private ByteBuffer 	gl_pixels;
	
	BufferedImage		src_image;
	
	public TextureImage(GL gl) {
		this.gl = gl;
	}
	
	public void init (String filename)
	{
		try 
		{
			BufferedImage img = ImageIO.read(getClass().getResourceAsStream(filename));
		
			init(img);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void init(BufferedImage src) throws Exception 
	{
		try 
		{
			src_image = src;
			
			WritableRaster raster = Raster.createInterleavedRaster(
					DataBuffer.TYPE_BYTE, 
					src.getWidth(),
					src.getHeight(), 
					4,
					null);
			
			ComponentColorModel colorModel = new ComponentColorModel(
					ColorSpace.getInstance(ColorSpace.CS_sRGB), 
					new int[] { 8, 8, 8, 8 }, 
					true,
					false, 
					ComponentColorModel.TRANSLUCENT,
					DataBuffer.TYPE_BYTE);
			
			BufferedImage bufImg = new BufferedImage(
					colorModel,
					raster,
					false,
					null);
	
			Graphics2D g = bufImg.createGraphics();
//			g.translate(0, src.getHeight());
//			g.scale(1, -1);
			g.drawImage(src, null, null);
			g.dispose();
			
			DataBufferByte imgBuf 	= (DataBufferByte) raster.getDataBuffer();
			byte[] rgba 			= imgBuf.getData();
			
			init(src_image.getWidth(), src_image.getHeight(), rgba);
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init(float a, float r, float g, float b, int w, int h)
	{
		byte[] rgba = new byte[w * h * 4];
		for (int i = w * h - 1; i >= 0; i--) {
			rgba[i * 4 + 0] = (byte) ((int) (r * 255) & 0xff);
			rgba[i * 4 + 1] = (byte) ((int) (g * 255) & 0xff);
			rgba[i * 4 + 2] = (byte) ((int) (b * 255) & 0xff);
			rgba[i * 4 + 3] = (byte) ((int) (a * 255) & 0xff);
		}
		init(w, h, rgba);
	}
	
	private void init(int w, int h, byte[] rgba)
	{
		this.w = w;
		this.h = h;
		
		gl_pixels = ByteBuffer.wrap(rgba);
		
		gl_texture = new int[1];
		
		gl.glGenTextures(1, gl_texture, 0);
		
		gl.glBindTexture(GL.GL_TEXTURE_2D, gl_texture[0]);
		
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);	// 线形滤波
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);	// 线形滤波

		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, w, h, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, gl_pixels);
		
	}
	
	public void despose() 
	{
		if (gl_texture != null) {
			gl.glDeleteTextures(1, gl_texture, 0);
			gl_texture = null;
		}
	}

	
	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}
	
	public void draw(float x, float y)
	{
		gl.glTranslatef(x, y, 0);
		{
			gl.glEnable(GL.GL_TEXTURE_2D);
			gl.glBindTexture(GL.GL_TEXTURE_2D, gl_texture[0]);
			gl.glColor4f(1, 1, 1, 1);
			gl.glBegin(GL.GL_QUADS);
			{
				gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex2f( 0, 0);
				gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex2f( w, 0);
				gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex2f( w, h);
				gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex2f( 0, h);
			}
			gl.glEnd();
			gl.glDisable(GL.GL_TEXTURE_2D);
		}
		gl.glTranslatef(-x, -y, 0);
		
//		gl.glWindowPos2i((int)x, (int)y);
//		gl.glPixelZoom(1.0f, 1.0f); // x-factor, y-factor
//		gl.glDrawPixels(w, h, gl.GL_RGBA,
//				gl.GL_UNSIGNED_BYTE, gl_pixels.rewind());
	}


}
