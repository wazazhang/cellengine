package com.g2d;

import com.cell.gfx.IImage;

public interface Image extends IImage
{
	public int 				getWidth();
	
	public int 				getHeight();
	
	public Graphics2D 		createGraphics();

	public int 				getRGB(int x, int y);
	
	public void 			setRGB(int x, int y, int argb);
	
	public BufferedImage 	getSubimage(int x, int y, int w, int h) ;
	
	public void 			setRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize);

	public int[] 			getRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize);
	
	public BufferedImage	getScaledInstance(int w, int h);
	
	public void				dispose();
}
