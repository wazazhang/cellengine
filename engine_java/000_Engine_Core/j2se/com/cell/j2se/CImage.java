package com.cell.j2se;

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
import java.awt.image.VolatileImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.IPalette;


class CImage implements IImage 
{
	static GraphicsEnvironment 		ge	= GraphicsEnvironment.getLocalGraphicsEnvironment();
	static GraphicsDevice 			gd	= ge.getDefaultScreenDevice();
	static GraphicsConfiguration	gc	= gd.getDefaultConfiguration();

	private BufferedImage	m_image;
	
	private WritableRaster	m_src_index_color_raster;
	private ColorModel		m_src_index_color_model;
	
//	private VolatileImage v_image;
	
	CImage(Image image)
	{
		m_image = createBuffer(image);
	}
	
	CImage()
	{
		m_image = createBuffer(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB));
	}
	
	CImage(String file) 
	{
		InputStream is = CIO.getInputStream(file);
		try{
			m_image = createBuffer(ImageIO.read(is));
		}catch(Exception err){
			err.printStackTrace();
			System.err.println("CImage.<init> :  File="+file+" Failed !");
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	CImage(InputStream is)
	{
		try{
			m_image = createBuffer(ImageIO.read(is));
		}catch(Exception err){
			err.printStackTrace();
			System.err.println("CImage.<init> : Failed !");
		}
	}
	
	private final BufferedImage createBuffer(Image src)
	{
		try
		{
			if ( (src instanceof BufferedImage) && (((BufferedImage)src).getColorModel() instanceof IndexColorModel) )
			{
				BufferedImage srci = (BufferedImage)src;
				m_src_index_color_raster	= srci.getRaster();
				m_src_index_color_model		= srci.getColorModel();
//				m_src_index_color_image = new BufferedImage(srci.getColorModel(), srci.getRaster(), srci.isAlphaPremultiplied(), null);
			}
			
			{
				m_image = gc.createCompatibleImage(
						src.getWidth(null), 
						src.getHeight(null), 
						Transparency.TRANSLUCENT);
				
				Graphics2D g = m_image.createGraphics();
				g.drawImage(src, 0, 0, null);
				g.dispose();				
			}
			
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
//		if (v_image != null) {
//			v_image.flush();
//		}
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
	
	public Image getSrc() 
	{
//		if (v_image.validate(g.getDeviceConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE) 
//		{
//			v_image.flush();
//			v_image = gc.createCompatibleVolatileImage(getWidth(), getHeight(), Transparency.TRANSLUCENT);
//			Graphics2D g2d = v_image.createGraphics();
//			g2d.drawImage(m_image, 0, 0, null);
//			g2d.dispose();
//		}
//		
		return m_image;
	}
	
	
	
	public IImage createBuffer(int width, int height) 
	{
		return new CImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
	}

	public IImage subImage(int x, int y, int width, int height)
	{
		if ( (m_src_index_color_raster != null) && (m_src_index_color_model != null) )
		{
			BufferedImage new_image = new BufferedImage(m_src_index_color_model, 
					m_src_index_color_raster, m_src_index_color_model.isAlphaPremultiplied(), null);
			
			return (new CImage(new_image.getSubimage(x, y, width, height)));
		}
		else
		{
			return (new CImage(m_image.getSubimage(x, y, width, height)));
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
				buf = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_INDEXED, (IndexColorModel)color_model);			
			}
			else
			{			
				buf = gc.createCompatibleImage(
						newWidth, 
						newHeight, 
						Transparency.TRANSLUCENT);
			}
			
			Graphics2D g = buf.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.drawImage(m_image, 0, 0, newWidth, newHeight, null);
			g.dispose();
			return new CImage(buf);				
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}

		return null;
	}
	
	public IGraphics getIGraphics() 
	{
		return new CGraphics((Graphics2D)m_image.getGraphics());
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
				
				return new CPalette(data, (short)size, (short)transparent_color_index);
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
	
	
}
