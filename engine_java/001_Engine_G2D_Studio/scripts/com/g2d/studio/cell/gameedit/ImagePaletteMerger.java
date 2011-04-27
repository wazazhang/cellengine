package com.g2d.studio.cell.gameedit;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import com.cell.CIO;
import com.cell.gfx.ColorUtilities;
import com.cell.gfx.IPalette;
import com.cell.io.BigIOSerialize;
import com.cell.io.LittleIOSerialize;
import com.cell.util.Pair;
import com.cell.util.zip.ZipUtil;
import com.g2d.Engine;
import com.g2d.Image;
import com.g2d.Quantize;
import com.g2d.Tools;
import com.g2d.java2d.impl.AwtEngine;
import com.g2d.java2d.impl.AwtImage;
import com.g2d.studio.io.IO;
import com.g2d.studio.io.file.FileIO;





public class ImagePaletteMerger
{
	public ImagePaletteMerger()
	{
		
	}
	
	public boolean isPaletteMergable(IPalette[] palettes)
	{
		// 判断透明色是否一样，只有透明色一样的调色板才可以合并
		boolean is_pals_same_trans = isPaletteSameTransColor(palettes);
		if (!is_pals_same_trans)
		{
			System.err.println("几个调色板的透明色不一样，所以无法合并!!");
			return false;
		}
		
		return true;
	}
	
	public IPalette mergedPalette(Image[] images)
	{
		IPalette[] palettes = getPalettes(images);

		if (!isPaletteMergable(palettes))
			return null;
		
		int trans_color_index = palettes[0].getTransparentColorIndex();
		
		// 以特定格式分别读出每个图片的调色板和索引像素
		int[][] pal_colors_ia = new int[palettes.length][]; 
		int[][] color_indices_ia = new int[palettes.length][];
		for ( int n=0; n<palettes.length; ++n )
		{
			IPalette pal = palettes[n];		
			
			int[] colors = getPaletteData(pal);				
			pal_colors_ia[n] = colors;
			
			int[] buffer = getImageIndexedPixels((AwtImage)images[n]);
			color_indices_ia[n] = buffer;
			
			// 测试原图的颜色值是否正确
//			int[] buffer2 = new int[buffer.length];
//			for ( int j=0; j<buffer.length; ++j )
//				buffer2[j] = colors[buffer[j]];	
//			writeImageFile(buffer2, 672, 405, BufferedImage.TYPE_INT_ARGB, "e:/test"+n+".png");
//			writeDataToFile(buffer2, "e:/txt"+n+".txt");
			
			System.out.println("<<<< original palette " + n);
			System.out.println("colors: " + pal.getIndexColorCount());
//			System.err.println("trans: " + ((trans_color!=null)? String.format("%08X", trans_color) : "null"));
			System.out.println(formatString(colors, 16));					
		}
		
		
		int[][] all_colors = pal_colors_ia.clone();
		
		// 对大调色板做八叉树颜色量化，将颜色数缩减到256个，单个颜色以 ARGB 32位整形颜色值形式存放
		int[] new_pal = Quantize.quantizeImage(all_colors, 256);
		
		// 对调色板做Hue排序
		int[] pal = sortPalette(new_pal);
		int[] sorted_pal_indices = new_pal;
		new_pal = pal; // new_pal 仍然指代最终的调色板
		////////
		
		// 保证调色板的颜色数为256
		if (new_pal.length < 256)
		{
			int[] tmp_pal = new int[256];
			System.arraycopy(new_pal, 0, tmp_pal, 0, new_pal.length);
			new_pal = tmp_pal;
		}
		
		System.out.println(">>>> merged palette ");
		System.out.println(formatString(new_pal, 16));
		System.out.println("===================================================================================================");					

		// 原先是直接储存颜色值的数组变成储存调色板元素索引的数组
		int[][] all_color_indices = all_colors;
		
		int new_color_index = (trans_color_index >= 0)? sorted_pal_indices[all_color_indices[0][trans_color_index]] : -1;
		
		IPalette new_palette = Engine.getEngine().createPalette(
				getPaletteDataByteArray(new_pal), 
				(short)new_pal.length, 
				(short)new_color_index);
		
		for ( int n=0; n<palettes.length; ++n )
		{
			int[] color_indices = all_color_indices[n];
			int[] buffer = color_indices_ia[n];
			
			for ( int m=0; m<buffer.length; ++m )
				buffer[m] = sorted_pal_indices[ 
				               color_indices[buffer[m]] // 得到在“未经排序的新调色板”中的索引
				               // 而 “未经排序的新调色板”的元素值又是在“经过排序的新调色板”中的索引
				            ]; 
			
//			int[] buffer3 = new int[buffer.length];
//			for ( int j=0; j<buffer.length; ++j )
//				buffer3[j] = new_pal[buffer[j]];
											
//			writeImageFile(buffer3, 672, 405, BufferedImage.TYPE_INT_ARGB, "e:/test___"+n+".png");
//			writeDataToFile(buffer3, "e:/txt___"+n+".txt");
	
			setImageIndexedPixels((AwtImage)images[n], buffer, new_palette);
		}		
		
		return new_palette;
	}
	
	private class PaletteElem
	{
		int color_;
		
		float hue_;
		
		int index_;
		
		public PaletteElem(int color, int index)
		{
			color_ = color;
			hue_ = this.getHue(color_);
			index_ = index;
		}
		
		public float getHue(int color)
		{
			float[] hsl = ColorUtilities
						.RGBtoHSL(((color & 0x00FF0000) >> 16), 
								  ((color & 0x0000FF00) >>  8),
								  ((color & 0x000000FF) >>  0) );
			
			return hsl[0];
		}
		
	}; // class PaletteElem
	
	public int[] sortPalette(int[] pal_data_ia)
	{		
		ArrayList<PaletteElem> elems = new ArrayList<PaletteElem>(pal_data_ia.length);
		for ( int i=0; i<pal_data_ia.length; ++i )
			elems.add(new PaletteElem(pal_data_ia[i], i));

		Collections.sort(elems,
				new Comparator<PaletteElem>() 
				{
					@Override
					public int compare(PaletteElem a, PaletteElem b) 
					{
						return (int)(a.hue_*10000000 - b.hue_*10000000);
					}			
				});
		
		int[] ret = new int[pal_data_ia.length];
		for ( int i=0; i<pal_data_ia.length; ++i )
		{
			PaletteElem elem = elems.get(i);
			ret[i] = elem.color_;
			pal_data_ia[elem.index_] = i;
		}

		return ret;
	}
	
	public int[] getPaletteData(IPalette palette)
	{
		byte[] color_array = palette.getIndexColors();
		
		int[] colors = new int[color_array.length / 3];
		
		for ( int i=0,j=0; i<color_array.length; i+=3,++j )
		{			
			colors[j] = 0xFF000000 
						| ((color_array[i+0] & 0x000000FF) << 16) 
						| ((color_array[i+1] & 0x000000FF) << 8) 
						| ((color_array[i+2] & 0x000000FF) << 0);
		}
		
		return colors;
	}
	
	public byte[] getPaletteDataByteArray(int[] pal_data_ia)
	{
		// 将调色板数据转化为字节数组, 以RGB每元素单字节存放
		byte[] new_pal_ba = new byte[pal_data_ia.length*3];
		for ( int i=0,j=0; i<pal_data_ia.length; ++i,j+=3 )
		{
			int pixel = pal_data_ia[i];
			new_pal_ba[j+0] = (byte)((pixel & 0x00FF0000) >> 16);
			new_pal_ba[j+1] = (byte)((pixel & 0x0000FF00) >>  8);
			new_pal_ba[j+2] = (byte)((pixel & 0x000000FF) >>  0);
		}
//		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
//		for ( int i=0; i<new_pal_data.length; i+=16*3 )
//		{
//			byte[] colors = new_pal_data;
//			System.out.println(String.format("%02X%02X%02X %02X%02X%02X " +
//											 "%02X%02X%02X %02X%02X%02X " +
//											 "%02X%02X%02X %02X%02X%02X " +
//											 "%02X%02X%02X %02X%02X%02X " +
//											 "%02X%02X%02X %02X%02X%02X " +
//											 "%02X%02X%02X %02X%02X%02X " +
//											 "%02X%02X%02X %02X%02X%02X " +
//											 "%02X%02X%02X %02X%02X%02X ", 
//					colors[i+0]&0xFF, colors[i+1]&0xFF, colors[i+2]&0xFF, colors[i+3]&0xFF, colors[i+4]&0xFF, colors[i+5]&0xFF, 
//					colors[i+6]&0xFF, colors[i+7]&0xFF, colors[i+8]&0xFF, colors[i+9]&0xFF, colors[i+10]&0xFF, colors[i+11]&0xFF, 
//					colors[i+12]&0xFF, colors[i+13]&0xFF, colors[i+14]&0xFF, colors[i+15]&0xFF, colors[i+16]&0xFF, colors[i+17]&0xFF,
//					colors[i+18]&0xFF, colors[i+19]&0xFF, colors[i+20]&0xFF, colors[i+21]&0xFF, colors[i+22]&0xFF, colors[i+23]&0xFF,
//					colors[i+24]&0xFF, colors[i+25]&0xFF, colors[i+26]&0xFF, colors[i+27]&0xFF, colors[i+28]&0xFF, colors[i+29]&0xFF,
//					colors[i+30]&0xFF, colors[i+31]&0xFF, colors[i+32]&0xFF, colors[i+33]&0xFF, colors[i+34]&0xFF, colors[i+35]&0xFF,
//					colors[i+36]&0xFF, colors[i+37]&0xFF, colors[i+38]&0xFF, colors[i+39]&0xFF, colors[i+40]&0xFF, colors[i+41]&0xFF,
//					colors[i+42]&0xFF, colors[i+43]&0xFF, colors[i+44]&0xFF, colors[i+45]&0xFF, colors[i+46]&0xFF, colors[i+47]&0xFF));
//		}
//		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		
		return new_pal_ba;
	}
	
	public Integer getTransparentColor(IPalette palette)
	{
		byte[] trans_color = palette.getTransparentColor();
		if (trans_color == null)
			return null;
		
		int color = 0xFF000000 
				 | ((trans_color[0] & 0x000000FF) << 16) 
				 | ((trans_color[1] & 0x000000FF) << 8) 
				 | ((trans_color[2] & 0x000000FF) << 0);
		
		return color;
	}
	
	public boolean isPaletteSameTransColor(IPalette[] palettes)
	{
		if ( (palettes != null) && (palettes.length > 0) )
		{
			if (palettes.length > 1)
			{
				Integer first_trans = getTransparentColor(palettes[0]);
				
				for ( int i=1; i<palettes.length; ++i )
				{
					if (first_trans != getTransparentColor(palettes[i]))
						return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public int[] getImageIndexedPixels(AwtImage image)
	{
		Raster raster = image.getAwtRaster();
		byte[] tmp_buf = new byte[raster.getWidth() * raster.getHeight()];
		raster.getDataElements(0, 0, raster.getWidth(), raster.getHeight(), tmp_buf);
		int[] buffer = new int[tmp_buf.length];
		for ( int i=0; i<buffer.length; ++i )
			buffer[i] = tmp_buf[i] & 0x000000FF;
		
		return buffer;
	}
	
	public void setImageIndexedPixels(AwtImage image, int[] buffer, IPalette palette)
	{
		WritableRaster raster = (WritableRaster) image.getAwtRaster();			
		byte[] tmp_buf = new byte[buffer.length];
		for ( int i=0; i<buffer.length; ++i )
			tmp_buf[i] = (byte)buffer[i];			
		raster.setDataElements(0, 0, raster.getWidth(), raster.getHeight(), tmp_buf);			
		image.setAwtRasterPalette(raster, palette);
	}
	
	public IPalette[] getPalettes(Image[] images)
	{
		IPalette[] palettes = new IPalette[images.length];
		
		for ( int i=0; i<images.length; ++i )
			palettes[i] = images[i].getPalette();
		
		return palettes;
	}
	
	public void writeDataToFile(int[] buffer, String filename)
	{
		StringBuffer sb = new StringBuffer();
		for ( int c : buffer )
			sb.append(c).append(",");
		File file = new File(filename);
		try {
			FileOutputStream fs = new FileOutputStream(file);
			fs.write(sb.toString().getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	public void writeImageFile(int[] buffer, int width, int height, int pixel_format, String filename)
	{
		java.awt.image.BufferedImage image = createAwtBufferedImage(buffer, width, height, pixel_format);
		
		try {
			ImageIO.write(image, "png", new File(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	public void writeAwtImageFile(Image image, String filename)
	{
		try {
			ImageIO.write(((AwtImage)image).getAwtBufferedImage(), "png", new File(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	public java.awt.image.BufferedImage createAwtBufferedImage(int[] buffer, int width, int height, int pixel_format)
	{
		java.awt.Image timage = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, buffer, 0, width));
		BufferedImage bi = new BufferedImage(width, height, pixel_format);
		Graphics g = bi.getGraphics();
		g.drawImage(timage, 0, 0, width, height, null);
		
		return bi;
	}
	
	public int[] getAwtBufferedImageData(Image image)
	{
		java.awt.image.BufferedImage ibi = ((AwtImage)image).getAwtBufferedImage();
		int[] buffer = new int[ibi.getWidth()*ibi.getHeight()];
		ibi.getRaster().getDataElements(0, 0, ibi.getWidth(), ibi.getHeight(), buffer);
		
		return buffer;
	}
	
	public String formatString(int[] buffer, int units_per_line)
	{
		if ( (buffer != null) && (buffer.length > 0) )
		{
			int lines = buffer.length / units_per_line;
			int last_line_units = buffer.length - units_per_line*lines;
			if (last_line_units > 0)
				lines++;
			
			StringBuffer sb = new StringBuffer();
			
			int i = 0;
			for ( int j=0; j<lines-1; ++j,i+=units_per_line )
			{					
				for ( int k=0; k<units_per_line; ++k )
					sb.append(String.format("%08X ", buffer[i+k]));
				sb.append("\n");					
			}
			
			if (last_line_units > 0)
			{
				for ( int k=0; k<last_line_units; ++k )
					sb.append(String.format("%08X ", buffer[i+k]));
				sb.append("\n");
			}
			
			return sb.toString();
		}
		
		return "";
	}	

	
	private static Image readImageFromFile(IO io, String filename)
	{
		String[] strs = filename.trim().split("@");

		if (strs.length > 1)
		{
			String img_file = strs[0];
			String pak_file = strs[1];

			try
			{
				com.g2d.studio.io.File pak = io.createFile(pak_file);
				ZipInputStream zip_stream = new ZipInputStream(pak.getInputStream());
				for (ZipEntry ze=zip_stream.getNextEntry(); ze!=null; ze=zip_stream.getNextEntry()) 
				{
					String ze_name = ze.getName();
//					System.err.println(ze_name);
					if (ze_name.equals(img_file))
					{
						byte[] data = ZipUtil.readBytes(zip_stream);
						ByteArrayInputStream bais = new ByteArrayInputStream(data);
						Image image = Tools.readImage(bais);
						return image;
					}
				}
				zip_stream.close();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			Image image = Tools.readImage(strs[0]);
			return image;
		}
		
		return null;
	}
	
	private static void writeImageToFile(IO io, Image image, String filename)
	{
		String[] strs = filename.split("@");

		if (strs.length > 1)
		{
			String img_file = strs[0];
			String pak_file = strs[1];

			try
			{
				BufferedImage bimg = ((AwtImage)image).getAwtBufferedImage();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(bimg, "png", baos);
				byte[] img_data = baos.toByteArray();
				InputStream img_stream = new ByteArrayInputStream(img_data);
				
				ArrayList<Pair<InputStream, String>> streams_to_pack = new ArrayList<Pair<InputStream, String>>();
				com.g2d.studio.io.File pak = io.createFile(pak_file);
				if (pak.exists())
				{
					// 如果pak文件已经存在的话，还需要将原先pak内的文件都一并再打包
					boolean has_ori = false;
					ZipInputStream zip_stream = new ZipInputStream(pak.getInputStream());
					for (ZipEntry ze=zip_stream.getNextEntry(); ze!=null; ze=zip_stream.getNextEntry()) 
					{
						String ze_name = ze.getName();
//						System.err.println(ze_name);
						if (ze_name.equals(img_file))
						{
							streams_to_pack.add(new Pair(img_stream, ze_name));
							has_ori = true;
						}
						else
						{
							byte[] data = ZipUtil.readBytes(zip_stream);
							ByteArrayInputStream bais = new ByteArrayInputStream(data);
							streams_to_pack.add(new Pair(bais, ze_name));							
						}
					}
					zip_stream.close();
					
					if (!has_ori)
						streams_to_pack.add(new Pair(img_stream, img_file));
				}
				else
				{
					streams_to_pack.add(new Pair(img_stream, img_file));
				}
				
				ByteArrayOutputStream out_stream = ZipUtil.packStreams(streams_to_pack, System.currentTimeMillis());
				pak.writeBytes(out_stream.toByteArray());
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}			
		}
		else
		{
			try 
			{
				BufferedImage bimg = ((AwtImage)image).getAwtBufferedImage();
				ImageIO.write(bimg, "png", new File(filename));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private static void writePaletteToFileACT(IO io, IPalette palette, String filename)
	{
		com.g2d.studio.io.File file = io.createFile(filename);
		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
						
			short trans_color_index = (short)palette.getTransparentColorIndex();
			
			if ( (trans_color_index > 0) && (trans_color_index != 0) )
			{
				byte[] trans_color_ba = palette.getTransparentColor();
				baos.write(trans_color_ba);	
				trans_color_index = 0;
				
				byte[] indexed_colors = palette.getIndexColors();
				
				for ( int i=0; i<indexed_colors.length; i+=3 )
				{
					if ( (indexed_colors[i+0]!=trans_color_ba[0])
					  || (indexed_colors[i+1]!=trans_color_ba[1])
					  || (indexed_colors[i+2]!=trans_color_ba[2]) )
						baos.write(indexed_colors, i, 3);
				}
			}
			else
			{
				byte[] indexed_colors = palette.getIndexColors();
				baos.write(indexed_colors);
			}

			short color_count = (short)palette.getIndexColorCount();
			BigIOSerialize.putShort(baos, color_count);
			
			BigIOSerialize.putShort(baos, trans_color_index);
			file.writeBytes(baos.toByteArray());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public static String trimExtension(String filename) 
    {
        if ((filename != null) && (filename.length() > 0)) 
        {
            int i = filename.lastIndexOf('.');
            
            if ((i >-1) && (i < (filename.length()))) 
                return filename.substring(0, i);
        }

        return filename;
    } 	
	
	public static void doOneImageListPaletteMerge(IO io, String listfile)
	{
		HashMap<Image, String> image_map = new HashMap<Image, String>();
		
		ArrayList<Image> tmp_imgs = new ArrayList<Image>();		
		
		String[] imgfiles = CIO.readAllLine(listfile);					
		for ( String imgfile : imgfiles )
		{
			if (!imgfile.trim().equals(""))
			{
				Image image = readImageFromFile(io, imgfile);
				if (image != null)
				{
					image_map.put(image, imgfile);
					tmp_imgs.add(image);
				}
			}
		}
		
		Image[] images = new Image[tmp_imgs.size()];
		tmp_imgs.toArray(images);
		
		ImagePaletteMerger pal_merger = new ImagePaletteMerger();
		
		IPalette new_pal = pal_merger.mergedPalette(images);
		
		try
		{
			for ( int i=0; i<images.length; ++i )
			{
				writeImageToFile(io, images[i], image_map.get(images[i]));
			}

			writePaletteToFileACT(io, new_pal, trimExtension(listfile)+".act");
		}
		catch (Exception exp) 
		{
			exp.printStackTrace();
		}		
	}

	public static void main(String[] args) throws IOException
	{
		try
		{
			if ( (args != null) && (args.length > 0) )
			{
				new AwtEngine();		
				
				IO io = new FileIO(new String[]{});
								
				for ( int i=0; i<args.length; ++i )
				{
					String listfile = args[i];
					
					doOneImageListPaletteMerge(io, listfile);
				}
				
				System.out.println("^_^ all done!!");
			}		
		}
		catch (Throwable exp)
		{
			exp.printStackTrace();
		}
	}

}; // class ImagePaletteMerger


