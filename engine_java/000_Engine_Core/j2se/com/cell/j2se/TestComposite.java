package com.cell.j2se;

import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;




public class TestComposite implements java.awt.Composite
{
	public static TestComposite DEFAULT = new TestComposite();

	private TestComposite() { }

	@Override
	public CompositeContext createContext(ColorModel arg0, ColorModel arg1, RenderingHints arg2) 
	{
		return new CompositeContext()
		{
			public void dispose()
			{

			}

			@Override
			public void compose(Raster src, Raster dst_in, WritableRaster dst_out)
			{
				int src_in_width = src.getWidth();
				int src_in_height = src.getHeight();
				int src_in_min_x = src.getMinX();
				int src_in_min_y = src.getMinY();

				int dst_in_width = dst_in.getWidth();
				int dst_in_height = dst_in.getHeight();
				int dst_in_min_x = dst_in.getMinX();
				int dst_in_min_y = dst_in.getMinY();

				int dst_in_max_x = dst_in_min_x + dst_in_width; // -1
				int dst_in_max_y = dst_in_min_y + dst_in_height; // -1

				float[] src_pixel = new float[4];
				float[] dst_pixel = new float[4];

				for ( int i=src_in_min_y; i<src_in_min_y+src_in_height; ++i )
				{
					for ( int j=src_in_min_x; j<src_in_min_x+src_in_width; ++j )
					{
						src.getPixel(j, i, src_pixel);
						//							for ( int n=0; n<src_pixel.length; ++n )
						//								src_pixel[n] /= 255.0f;

						if ( (dst_in_min_x<=j) && (j<dst_in_max_x) && (dst_in_min_y<=i) && (i<dst_in_max_y) )
						{
							dst_in.getPixel(j, i, dst_pixel);
							//								for ( int n=0; n<dst_pixel.length; ++n )
							//									dst_pixel[n] /= 255.0f;								

							dst_pixel[0] = Math.max(src_pixel[0], dst_pixel[0]);// / 255.0f;
							dst_pixel[1] = Math.max(src_pixel[1], dst_pixel[1]);// / 255.0f;
							dst_pixel[2] = Math.max(src_pixel[2], dst_pixel[2]);// / 255.0f;
							dst_pixel[3] = Math.max(src_pixel[3], dst_pixel[3]);// / 255.0f;

							if (dst_pixel[0] < 0)
								dst_pixel[0] = 0;
							if (dst_pixel[1] < 0)
								dst_pixel[1] = 0;
							if (dst_pixel[2] < 0)
								dst_pixel[2] = 0;
							//								if (dst_pixel[3] < 0)
							//									dst_pixel[3] = 0;								
							if (dst_pixel[0] > 255.0f)
								dst_pixel[0] = 255.0f;		
							if (dst_pixel[1] > 255.0f)
								dst_pixel[1] = 255.0f;
							if (dst_pixel[2] > 255.0f)
								dst_pixel[2] = 255.0f;	
							//								if (dst_pixel[3] > 255.0f)
							//									dst_pixel[3] = 255.0f;								

							//								for ( int n=0; n<dst_pixel.length; ++n )
							//									dst_pixel[n] *= 255.0f;									
							dst_out.setPixel(j, i, dst_pixel);
						}
					}
				}					
			}

		};
	}
};




	
	
