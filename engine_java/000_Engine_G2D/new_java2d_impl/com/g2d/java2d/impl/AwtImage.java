package com.g2d.java2d.impl;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.cell.CIO;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.IPalette;


public class AwtImage implements com.g2d.BufferedImage
{
	private BufferedImage	m_image;
	
	private WritableRaster	m_src_index_color_raster;
	private ColorModel		m_src_index_color_model;
		
	protected AwtImage(Image image)
	{
		m_image = createBuffer(image);
	}
	
	protected AwtImage() {
		m_image = createBuffer(AwtEngine.getEngine().getGC()
				.createCompatibleImage(1, 1, Transparency.TRANSLUCENT));
	}

	protected AwtImage(String file) {
		InputStream is = CIO.getInputStream(file);
		try {
			m_image = createBuffer(ImageIO.read(is));
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected AwtImage(java.awt.image.BufferedImage buff) {
		try {
			m_image = createBuffer(buff);
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	protected AwtImage(int w, int h) {
		m_image = createBuffer(AwtEngine.getEngine().getGC()
				.createCompatibleImage(w, h, Transparency.TRANSLUCENT));
	}
	
	protected BufferedImage createBuffer(Image src)
	{
		try
		{
			if (src instanceof BufferedImage)
			{
				BufferedImage simg = (BufferedImage)src;
				
				if (simg.getColorModel() instanceof IndexColorModel)
				{
					m_src_index_color_raster	= simg.getRaster();
					m_src_index_color_model		= simg.getColorModel();
				}
				
				if (simg.getCapabilities(AwtEngine.getEngine().getGC()).isAccelerated()) 
				{
					m_image = simg;
					return m_image;
				}
			}
			
			m_image = AwtEngine.getEngine().getGC().createCompatibleImage(
					src.getWidth(null), 
					src.getHeight(null), 
					Transparency.TRANSLUCENT);
			Graphics2D g = m_image.createGraphics();
			g.drawImage(src, 0, 0, null);
			g.dispose();
			
//			v_image = gc.createCompatibleVolatileImage(
//					src.getWidth(null), 
//					src.getHeight(null), 
//					Transparency.TRANSLUCENT);
//			Graphics2D g2d = v_image.createGraphics();
//			g2d.drawImage(m_image, 0, 0, null);
//			g2d.dispose();
			
			return m_image;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void finalize() throws Throwable {
		if (m_image != null) {
			m_image.flush();
		}
	}
	
	
//	----------------------------------------------------------------------------------------------------------------------------------------
	
	public IImage newInstance()
	{
		IImage ret = subImage(0, 0, getWidth(), getHeight());
		return ret;
	}
	
	public BufferedImage getSrc() 
	{
		return m_image;
	}
	
	public IImage createBuffer(int width, int height) 
	{
		return new AwtImage(width, height);
	}

	public IImage subImage(int x, int y, int width, int height) 
	{
		if ( (m_src_index_color_raster != null) && (m_src_index_color_model != null) )
		{
			BufferedImage new_image = new BufferedImage(m_src_index_color_model, 
					m_src_index_color_raster, m_src_index_color_model.isAlphaPremultiplied(), null);
			
			return (new AwtImage(new_image.getSubimage(x, y, width, height)));
		}
		else
		{
			return (new AwtImage(m_image.getSubimage(x, y, width, height)));
		}
	}
	
	public IImage resize(int newWidth, int newHeight)
	{
		try
		{
			BufferedImage buf = null;
			
			ColorModel color_model = m_image.getColorModel();
			
			if (color_model instanceof IndexColorModel)
			{
				buf = new BufferedImage(
						newWidth,
						newHeight, 
						BufferedImage.TYPE_BYTE_INDEXED, 
						(IndexColorModel)color_model);			
			}
			else
			{
				buf = AwtEngine.getEngine().getGC().createCompatibleImage(
						newWidth, 
						newHeight, 
						Transparency.TRANSLUCENT);
			}
			
			Graphics2D g = buf.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.drawImage(m_image, 0, 0, newWidth, newHeight, null);
			g.dispose();
			return new AwtImage(buf);				
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}

		return null;
	}
	
	public IGraphics getIGraphics() 
	{
		return new AwtGraphics2D((Graphics2D)m_image.getGraphics());
	}

	public int getHeight()
	{
		return m_image.getHeight();
	}

	public int getWidth()
	{
		return m_image.getWidth();
	}

	public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) 
	{
		m_image.getRGB(x, y, width, height, rgbData, offset, scanlength);
	}

	public int setMode(int mode) 
	{
		return mode;
	}
	
	
	/**
	 * get the color model of the image
	 * @return color model
	 */
	public int getColorModel()
	{
		if (m_src_index_color_model != null) {
			return COLOR_MODEL_INDEX;
		} else {
			return COLOR_MODEL_DIRECT;
		}
	}
	
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
				
				return new AwtPalette(data, (short)size, (short)transparent_color_index);
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
				
				BufferedImage new_image = new BufferedImage(icm, m_src_index_color_raster, icm.isAlphaPremultiplied(), null);
				
				m_image = createBuffer(new_image);
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
		}
	}

//	---------------------------------------------------------------------------------------------------------------------------------	
	
	@Override
	public com.g2d.Graphics2D createGraphics() {
		return new AwtGraphics2D((Graphics2D)m_image.createGraphics());
	}
	
	@Override
	public com.g2d.Graphics2D getGraphics() {
		return new AwtGraphics2D((Graphics2D)m_image.getGraphics());
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
	public com.g2d.BufferedImage getSubimage(int x, int y, int w, int h) {
		return new AwtImage(m_image.getSubimage(x, y, w, h));
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
		return new AwtImage(m_image.getScaledInstance(w, h, java.awt.image.BufferedImage.SCALE_FAST));
	}
	
	
//	---------------------------------------------------------------------------------------------------------------------------------
	
	
	public Raster getAwtRaster()
	{
		if (m_src_index_color_raster != null)
			return m_src_index_color_raster;
		
		return m_image.getRaster();
	}
	
	public void setAwtRaster(WritableRaster raster)
	{
		if (m_src_index_color_raster != null)
		{
			m_src_index_color_raster = raster;
			
			BufferedImage new_image = new BufferedImage(m_src_index_color_model, 
					m_src_index_color_raster, m_src_index_color_model.isAlphaPremultiplied(), null);
			
			m_image = createBuffer(new_image);		
		}
		else
		{		
			m_image.setData(raster);
		}
	}
	
	public BufferedImage getAwtBufferedImage()
	{
		if (m_src_index_color_raster != null)
		{
			BufferedImage new_image = new BufferedImage(m_src_index_color_model, 
					m_src_index_color_raster, m_src_index_color_model.isAlphaPremultiplied(), null);
			
			return new_image;
		}
		
		return m_image;
	}
	
	public void setAwtRasterPalette(WritableRaster raster, IPalette palette)
	{
		m_src_index_color_raster = raster;

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
				
				BufferedImage new_image = new BufferedImage(icm, m_src_index_color_raster, icm.isAlphaPremultiplied(), null);
				
				m_image = createBuffer(new_image);
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
		}		
	}
		
//	---------------------------------------------------------------------------------------------------------------------------------
	

	
}




