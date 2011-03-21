package com.g2d.jogl.impl;

import java.io.InputStream;
import java.nio.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.color.ColorSpace;

import javax.imageio.ImageIO;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

import com.cell.CIO;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.IPalette;
import com.g2d.java2d.CommonPalette;

public class GLImage implements com.g2d.BufferedImage
{
	private BufferedImage	m_image;
	private WritableRaster	m_src_index_color_raster;
	private ColorModel		m_src_index_color_model;

	/* OpenGL name for the sprite texture */
	final private GL		gl;
	private int[] 			gl_texture;
	private int				w, h;
	
	GLImage(GL gl, Image src) 
	{
		this.gl	= gl;
		this.m_image = createBuffer(src);
	}
	
	GLImage(GL gl, int w, int h)
	{
		this.gl = gl;
		this.m_image = GLEngine.createRaster(w, h);
		DataBuffer imgBuf = m_image.getRaster().getDataBuffer();
//		byte[] rgba = imgBuf.getData();
		init(imgBuf, m_image.getWidth(), m_image.getHeight());
	}
	
	BufferedImage createBuffer(Image src)
	{
		if (src instanceof BufferedImage) {
			BufferedImage simg = (BufferedImage) src;
			if (simg.getColorModel() instanceof IndexColorModel) {
				m_src_index_color_raster = simg.getRaster();
				m_src_index_color_model = simg.getColorModel();
			}
		}
		
		BufferedImage m_image = GLEngine.createRaster(src.getWidth(null), src.getHeight(null));

		Graphics2D g = m_image.createGraphics();
		g.drawImage(src, null, null);
		g.dispose();
		
		DataBuffer imgBuf =  m_image.getRaster().getDataBuffer();
//		byte[] rgba = imgBuf.getData();
		
		init(imgBuf, m_image.getWidth(), m_image.getHeight());
		
		return m_image;
	}
	
	void init(DataBuffer rgba, int w, int h)
	{
		this.w = w;
		this.h = h;
		
		this.gl_texture = new int[1];
		
		gl.glGenTextures(1, gl_texture, 0);
		
		gl.glBindTexture(GL.GL_TEXTURE_2D, gl_texture[0]);
		

		if (rgba instanceof DataBufferByte) 
		{
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR );	// 线形滤波
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR );	// 线形滤波

			byte[] pixels = ((DataBufferByte)rgba).getData();
			Buffer gl_pixels = ByteBuffer.wrap(pixels);
//			gl.glTexImage2D(
//					GL.GL_TEXTURE_2D, 0, 
//					GL.GL_RGBA, w, h, 0,
//					GL.GL_RGBA, 
//					GL.GL_UNSIGNED_BYTE, 
//					gl_pixels);
			new GLU().gluBuild2DMipmaps(
					GL.GL_TEXTURE_2D,
					GL.GL_RGBA, w, h, 
					GL.GL_RGBA,
					GL.GL_UNSIGNED_BYTE, 
					gl_pixels);
		} 
		else if (rgba instanceof DataBufferInt) 
		{
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR );	// 线形滤波
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR );	// 线形滤波

			int[] pixels = ((DataBufferInt)rgba).getData();
			Buffer gl_pixels = IntBuffer.wrap(pixels);
//			gl.glTexImage2D(
//					GL.GL_TEXTURE_2D, 0, 
//					GL.GL_RGBA, w, h, 0, 
//					GL.GL_RGBA, 
//					GL.GL_UNSIGNED_INT_8_8_8_8, 
//					gl_pixels);
			new GLU().gluBuild2DMipmaps(
					GL.GL_TEXTURE_2D,
					GL.GL_RGBA, w, h, 
					GL.GL_RGBA,
					GL.GL_UNSIGNED_INT_8_8_8_8, 
					gl_pixels);
		} else {
			
		}
		

	}
	
	void draw(GL gl, float dx, float dy, float dw, float dh, float sx, float sy, float sw, float sh, float blend_alpha)
	{
		float cw = dw / sw;
		float ch = dw / sw;
		gl.glTranslatef(dx, dy, 0);
		{
			gl.glEnable(GL.GL_TEXTURE_2D);
			gl.glBindTexture(GL.GL_TEXTURE_2D, gl_texture[0]);
			gl.glBegin(GL.GL_QUADS);
			{
				gl.glColor4f(1, 1, 1, blend_alpha);
				gl.glTexCoord2f(0f, 0f); gl.glVertex2f(  0,  0);
				gl.glTexCoord2f(cw, 0f); gl.glVertex2f( dw,  0);
				gl.glTexCoord2f(cw, ch); gl.glVertex2f( dw, dh);
				gl.glTexCoord2f(0f, ch); gl.glVertex2f(  0, dh);
			}
			gl.glEnd();
			gl.glDisable(GL.GL_TEXTURE_2D);
		}
		gl.glTranslatef(-dx, -dy, 0);
	}
	
	public void dispose() 
	{
		if (gl_texture != null) {
//			System.out.println("glDeleteTextures : " + gl_texture[0]);
			GLDisposePool.glDeleteTextures(gl, gl_texture);
			gl_texture = null;
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		dispose();
	}

	public BufferedImage getSrc() {
		return m_image;
	}

	
	
//	---------------------------------------------------------------------------------------------------------------------------------
	
	


	
	
	
//	----------------------------------------------------------------------------------------------------------------------------------------
	
	public IImage newInstance()
	{
		IImage ret = subImage(0, 0, getWidth(), getHeight());
		return ret;
	}
	
	public IImage createBuffer(int width, int height) 
	{
		return new GLImage(gl, width, height);
	}

	public IImage subImage(int x, int y, int width, int height) 
	{
		return getSubimage(x, y, width, height);
	}
	
	public IImage resize(int newWidth, int newHeight) 
	{
		if ( (m_src_index_color_raster != null) && (m_src_index_color_model != null) )
		{
			BufferedImage new_image = new BufferedImage(
					m_src_index_color_model, 
					m_src_index_color_raster, 
					m_src_index_color_model.isAlphaPremultiplied(), null);
			return (new GLImage(gl, new_image.getScaledInstance(
					newWidth, newHeight, BufferedImage.SCALE_SMOOTH)));
		}
		else
		{
			return (new GLImage(gl, m_image.getScaledInstance(
					newWidth, newHeight, BufferedImage.SCALE_SMOOTH)));
		}
	}

	public int getHeight() {
		return h;
	}

	public int getWidth() {
		return w;
	}

	public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y,
			int width, int height) {
		m_image.getRGB(x, y, width, height, rgbData, offset, scanlength);
	}

	public int getColorModel()
	{
		if (m_src_index_color_model != null) {
			return COLOR_MODEL_INDEX;
		} else {
			return COLOR_MODEL_DIRECT;
		}
	}

//	---------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * if the color model of image is model index, means it has a palette
	 * @return get the palette if it exists
	 */
	public IPalette getPalette()
	{
		if (m_src_index_color_model != null)
		{
			ColorModel color_model = m_src_index_color_model;
			
			if (color_model instanceof IndexColorModel)
			{
				IndexColorModel model = (IndexColorModel)color_model;
				
				int size = model.getMapSize();
				
				byte[] ra = new byte[size];
				byte[] ga = new byte[size];
				byte[] ba = new byte[size];
				byte[] alphaa = new byte[size];
				model.getReds(ra);
				model.getGreens(ga);
				model.getBlues(ba);
				model.getAlphas(alphaa);
				
				int transparent_color_index = -1;
				
				byte[] data = new byte[256*3];
				for ( int i=0, j=0; (i<size)&&(j<data.length); ++i )
				{
					data[j++] = ra[i];
					data[j++] = ga[i];
					data[j++] = ba[i];
					if (alphaa[i] == 0)
						transparent_color_index = i;
				}
				
				return new CommonPalette(data, (short)size, (short)transparent_color_index);
			}	
		}
		return null;
	}
	
	/**
	 * if the color model of image is model index, set a new palette
	 * @param palette
	 * @return get the old palette
	 */
	public void setPalette(IPalette palette)
	{
		if (m_src_index_color_raster == null) {
			throw new IllegalStateException("this image is not support index color model !");
		}
		
		if (palette != null)
		{
			try
			{
				byte[] colors = palette.getIndexColors();
				int color_count = palette.getIndexColorCount();
				int transparent_color_index = palette.getTransparentColorIndex();
				byte[] ra = new byte[color_count];
				byte[] ga = new byte[color_count];
				byte[] ba = new byte[color_count];
				byte[] ralpha = new byte[color_count];
				
				for ( int i=0,j=0; (i<colors.length)&&(j<color_count); i+=3,++j )
				{
					ra[j] = colors[i];
					ga[j] = colors[i+1];
					ba[j] = colors[i+2];
					ralpha[j] = (byte)((j==transparent_color_index)? 0 : 255);
				}
				
				IndexColorModel icm = new IndexColorModel(8, color_count, ra, ga, ba, ralpha);
				
				BufferedImage new_image = new BufferedImage(
						icm, m_src_index_color_raster, 
						icm.isAlphaPremultiplied(), null);
				
				this.m_image = createBuffer(new_image);
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------
	
	public com.g2d.Graphics2D createGraphics() {
		return new GLGraphics2D.GLGraphicsPBuffer(
				gl, 
				(Graphics2D)m_image.getGraphics());
	}
	
	@Override
	public int[] getRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize) {
		return m_image.getRGB(startX, startY, w, h, rgbArray, offset, scansize);
	}
	
	@Override
	public int getRGB(int x, int y) {
		return m_image.getRGB(x, y);
	}
	
	@Override
	public com.g2d.BufferedImage getSubimage(int x, int y, int w, int h) 
	{
		if ( (m_src_index_color_raster != null) && (m_src_index_color_model != null) )
		{
			BufferedImage new_image = new BufferedImage(
					m_src_index_color_model, 
					m_src_index_color_raster, 
					m_src_index_color_model.isAlphaPremultiplied(), null);
			
			return (new GLImage(gl, new_image.getSubimage(x, y, w, h)));
		}
		else
		{
			return (new GLImage(gl, m_image.getSubimage(x, y, w, h)));
		}
	}
	
	@Override
	public void setRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize) {
		m_image.setRGB(startX, startY, w, h, rgbArray, offset, scansize);
	}
	@Override
	public void setRGB(int x, int y, int argb) {
		m_image.setRGB(x, y, argb);
	}
	
	@Override
	public com.g2d.BufferedImage getScaledInstance(int w, int h) {
		return (com.g2d.BufferedImage)resize(w, h);
	}

}
