package com.g2d.composite;

import java.awt.Color;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * 将不透明部分渲染为同一颜色
 * @author WAZA
 */
public class SingleColorComposite implements Composite
{
	Color color;
	
	public SingleColorComposite(Color color) {
		this.color		= color;
	}
	
	@Override
	public CompositeContext createContext(
			ColorModel srcColorModel,
			ColorModel dstColorModel, 
			RenderingHints hints) {
		return new SingleColorCompositeContext(this, srcColorModel, dstColorModel);
	}
	
	class SingleColorCompositeContext implements CompositeContext
	{
		ColorModel srcCM;
		ColorModel dstCM;

		public SingleColorCompositeContext(
				SingleColorComposite paramAlphaComposite,
				ColorModel paramColorModel1, 
				ColorModel paramColorModel2) 
		{
			if (paramColorModel1 == null)
				throw new NullPointerException("Source color model cannot be null");

			if (paramColorModel2 == null)
				throw new NullPointerException("Destination color model cannot be null");

			this.srcCM = paramColorModel1;
			this.dstCM = paramColorModel2;
		}

		public void dispose() {}

		public void compose(Raster src, Raster dstIn, WritableRaster dstOut)
		{
			if (dstIn != dstOut) {
				dstOut.setDataElements(0, 0, dstIn);
			}
		    WritableRaster srcOut;
			if (src instanceof WritableRaster) {
				srcOut = (WritableRaster) src;
			} else {
				srcOut = src.createCompatibleWritableRaster();
				srcOut.setDataElements(0, 0, src);
			}

		    BufferedImage localBufferedImage1 = new BufferedImage(this.srcCM, srcOut, this.srcCM.isAlphaPremultiplied(), null);
		    BufferedImage localBufferedImage2 = new BufferedImage(this.dstCM, dstOut, this.dstCM.isAlphaPremultiplied(), null);
		    for (int x=0; x<localBufferedImage2.getWidth(); x++) {
		    	 for (int y=0; y<localBufferedImage2.getHeight(); y++) {
		    		 if (localBufferedImage1.getRGB(x, y)!=0) {
		    			 localBufferedImage2.setRGB(x, y, color.getRGB());
		    		 }
		    	 }
		    }
		  }
		
	}
	
}

