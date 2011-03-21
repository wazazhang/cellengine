package com.g2d;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.AttributedString;
import java.util.Random;

import com.cell.CIO;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CSprite;
import com.cell.io.BigIODeserialize;
import com.cell.io.BigIOSerialize;
import com.cell.io.CFile;
import com.g2d.geom.AffineTransform;
import com.g2d.geom.Point;
import com.g2d.text.TextBuilder;


public class Tools
{
//	--------------------------------------------------------------------------------

	public static AnimateCursor createCustomCursor(Image cursor, Point hotSpot, String name)
	{
		return Engine.getEngine().createCursor(new Image[]{cursor}, hotSpot.x, hotSpot.y, name);
	}

	/**
	 * 由指定的精灵，创建一个动画鼠标
	 * @param cspr
	 * @return
	 */
	public static AnimateCursor createSpriteCursor(String name, CSprite cspr, int anim)
	{
		int frame_count	= cspr.getFrameCount(anim);
		CCD max_bounds 	= cspr.getFrameBounds(anim);
		Image[] array = new Image[frame_count];
		for (int f = 0; f < frame_count; f++) {
			BufferedImage buff = createImage(max_bounds.getWidth(), max_bounds.getHeight());
			Graphics2D g2d = buff.createGraphics();
			cspr.render(g2d, -max_bounds.X1, -max_bounds.Y1, anim, f);
			g2d.dispose();
			array[f] = buff;
		}
		return Engine.getEngine().createCursor(array, -max_bounds.X1, -max_bounds.Y1, name);
	}
	
	public static BufferedImage getFrameSnapshot(CSprite cspr, int anim, int frame)
	{
		CCD border = cspr.getFrameBounds(anim, frame);
		BufferedImage ret = Tools.createImage(border.getWidth(), border.getHeight());
		Graphics2D g = ret.createGraphics();
		cspr.render(g, -border.X1, -border.Y1, anim, frame);
		g.dispose();
		return ret;
	}
	

//	--------------------------------------------------------------------------------
	
	
//	--------------------------------------------------------------------------------
	
	static public void setAlpha(Graphics2D g, float alpha) {
		if (alpha >= 0 && alpha < 1f) {
			g.setAlpha(alpha);
		}
	}

	static public void setTextAntialiasing(Graphics2D g, boolean enable) {
		g.setFontAntialiasing(enable);
	}
	
//	--------------------------------------------------------------------------------

	/**
	 * 将一个图片所有不透明的部分，都设置成指定的颜色和ALPHA值
	 * @param src
	 * @param alpha
	 * @param rgb
	 * @return 返回原图
	 */
	static public BufferedImage toAlpha(BufferedImage src, float alpha, int rgb)
	{
		rgb = 0x00ffffff & rgb;
		int a ;
		int w = src.getWidth();
		int h = src.getHeight();
		for (int x = w - 1; x >= 0; x--) {
			for (int y = h - 1; y >= 0; y--) {
				a = src.getRGB(x, y) & 0xff000000;
				if (a != 0) {
					a = a >>> 24;
					a = ((int) (a * alpha)) << 24;
					src.setRGB(x, y, a | rgb);
				}
			}
		}
		return src;
	}
	
	/**
	 * 将一个图片所有不透明的部分，都设置成指定的颜色和ALPHA值
	 * @param src
	 * @param alpha
	 * @param rgb
	 * @return 返回原图
	 */
	static public BufferedImage toColor(BufferedImage src, float alpha, int rgb)
	{
		rgb = 0x00ffffff & rgb;

		int a ;
		int w = src.getWidth();
		int h = src.getHeight();
		
		for (int x = w - 1; x >= 0; x--) {
			for (int y = h - 1; y >= 0; y--) {
				
				a = src.getRGB(x, y) & 0xff000000;
				if (a != 0) {
					a = a >>> 24;
					a = ((int) (a * alpha)) << 24;
					src.setRGB(x, y, a | rgb);
				}
			}
		}
		
		return src;
	}
	
	/**
	 * 将一幅图片的所有像素设置成灰度
	 * @param src
	 * @return 返回原图
	 */
	public static BufferedImage toTurngrey(BufferedImage src)
	{
		int rgb;
		int a, r, g, b;
		int w = src.getWidth();
		int h = src.getHeight();
		
		for (int x = w - 1; x >= 0; x--) {
			for (int y = h - 1; y >= 0; y--) {
				
				rgb = src.getRGB(x, y);
				a = (rgb & 0xff000000) >>> 24;
				if (a != 0) {
					r = (rgb & 0x00ff0000) >>> 16;
					g = (rgb & 0x0000ff00) >>> 8;
					b = (rgb & 0x000000ff);
					r = g;
					b = g;
					rgb = (a << 24) | (r << 16) | (g << 8) | b;
					src.setRGB(x, y, rgb);
				}
			}
		}
		
		return src;
	}

	/**
	 * 将一个图片所有不透明的部分，都设置成指定的颜色和ALPHA值
	 * @param src
	 * @param alpha
	 * @param rgb
	 * @return 返回一个新的图片
	 */
	static public BufferedImage createAlpha(BufferedImage src, float alpha, int rgb)
	{
		BufferedImage dst = combianImage(src.getWidth(), src.getHeight(), src);
		return toAlpha(dst, alpha, rgb);
	}
	
	/**
	 * 将一幅图片的所有像素设置成灰度
	 * @param src
	 * @return 返回一个新的图片
	 */
	static public BufferedImage createTurngrey(BufferedImage src) 
	{
		BufferedImage dst = combianImage(src.getWidth(), src.getHeight(), src);
		return toTurngrey(dst);
	}

	static public BufferedImage createImage(int width, int height)
	{
		try
		{
			BufferedImage buf = Engine.getEngine().createImage(width, height);

			return buf;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		return null;
	}
	
	static public BufferedImage createImage(int width, int height, Color color)
	{
		try
		{
			BufferedImage buf = Engine.getEngine().createImage(width, height);
			Graphics2D g = (Graphics2D)buf.createGraphics();
			g.setColor(color);
			g.fillRect(0, 0, width, height);
			g.dispose();
			return buf;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		return null;
	}
	
	static public BufferedImage createImage(IImage image)
	{
		try
		{
			BufferedImage buf = Engine.getEngine().createImage(image.getWidth(), image.getHeight());
			Graphics2D g = (Graphics2D)buf.createGraphics();
			g.drawImage(image, 0, 0, 0);
			g.dispose();
			return buf;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		return null;
	}
	
	static public BufferedImage createImage(CSprite spr, int anim, int frame)
	{
		try
		{	
			CCD bounds = spr.getFrameBounds(anim, frame);
			BufferedImage buf = Engine.getEngine().createImage(bounds.getWidth(), bounds.getHeight());
			Graphics2D g = (Graphics2D)buf.createGraphics();
			try {
				spr.render(g, -bounds.X1, -bounds.Y1, anim, frame);
			} finally {
				g.dispose();
			}
			return buf;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		return null;
	}
	
	static public BufferedImage combianImage(int width, int height, IImage ... args)
	{
		try
		{
			BufferedImage dst = createImage(width, height);
			IGraphics g = dst.createGraphics();
			for (IImage img : args) {
				g.drawImage(img, 0, 0, width, height, 0);
			}
			g.dispose();
			return dst;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		return null;
	}
	
	static public BufferedImage scaleImage(IImage args, double scale)
	{
		double dw = args.getWidth() * scale;
		double dh = args.getHeight() * scale;
		return combianImage((int)dw, (int)dh, args);
	}
	
//	static public BufferedImage createTransformImage(Image src, int width, int height, AffineTransform transform)
//	{
//		try
//		{
//			BufferedImage dst = createImage(width, height);
//			Graphics2D g = (Graphics2D)dst.createGraphics();
//			g.setTransform(transform);
//			g.drawImage(src, 0, 0);
//			g.dispose();
//			return dst;
//		}
//		catch(Exception err)
//		{
//			err.printStackTrace();
//		}
//		return null;
//	}
	
	static public BufferedImage createAlphaImage(Image src, float alpha)
	{
		try
		{
			BufferedImage dst = createImage(src.getWidth(), src.getHeight());
			Graphics2D g = (Graphics2D)dst.createGraphics();
			setAlpha(g, alpha);
			g.drawImage(src, 0, 0);
			g.dispose();
			return dst;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		return null;
	}
	
	static public BufferedImage readImage(String file)
	{
		try {
			InputStream is = CIO.getInputStream(file);
			try{
				BufferedImage src = Engine.getEngine().createImage(is);
				return src;
			} finally {
				is.close();
			}
		} catch (Exception err) {
			System.err.println(file);
			err.printStackTrace();
		}
		return null;
	}
	
	
	static public BufferedImage readImage(InputStream is) {
		try {
			try {
				BufferedImage src = Engine.getEngine().createImage(is);
				return src;
			} finally {
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	static public BufferedImage subImage(Image src, int sx, int sy, int sw, int sh)
	{
		try {
			BufferedImage buf = Engine.getEngine().createImage(sw, sh);
			Graphics2D g = buf.createGraphics();
			g.drawImage(src, -sx, -sy);
			g.dispose();
			return buf;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	static public BufferedImage cloneImage(Image src)
	{
		try {
			BufferedImage buf = Engine.getEngine().createImage(
					src.getWidth(),
					src.getHeight());
			Graphics2D g = buf.createGraphics();
			g.drawImage(src, 0, 0);
			g.dispose();
			return buf;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}

//	--------------------------------------------------------------------------------
	
	static public byte[] writeImage(String file, Image image)
	{
		if (file.endsWith(".jpg")) {
			return writeImage(file, "jpg", image);
		} 
		else if (file.endsWith(".png")) {
			return writeImage(file, "png", image);
		} 
		else if (file.endsWith(".bmp")) {
			return writeImage(file, "bmp", image);
		} 
		else if (file.endsWith(".gif")) {
			return writeImage(file, "gif", image);
		}
		else {
			return writeImage(file, "png", image);
		}
	}
	
	static public byte[] writeImage(String file, String format, Image image)
	{
		try {
			BufferedImage buf = null;
			if (image instanceof BufferedImage) {
				buf = (BufferedImage) image;
			} else {
				buf = cloneImage(image);
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream(10240);
			Engine.getEngine().encodeImage(baos, buf, format);
			byte[] data = baos.toByteArray();
			CFile.writeData(new File(file), data);
			return data;
		} catch (Exception err) {
			System.err.println(file);
			err.printStackTrace();
			return null;
		}
	}
	
	
//	--------------------------------------------------------------------------------
	
	static private Random random = new Random();
	
	static public int getRandomInt() {
		return random.nextInt();
	}
	
	static public int getRandomUInt() {
		return Math.abs(random.nextInt());
	}
	
	static public float getRandomFloat() {
		return random.nextFloat();
	}
	
	static public float getRandomUFloat() {
		return Math.abs(random.nextFloat());
	}
	
	static public Random getRandom() {
		return random;
	}
	
//	--------------------------------------------------------------------------------

	static public void setClipboardText(String str){
		Engine.getEngine().setClipboardText(str);
	}
	
	static public String getClipboardText(){
		return Engine.getEngine().getClipboardText();
	}
	
//	--------------------------------------------------------------------------------
	
	static public void printError(String text) {
		try {
			throw new Exception(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	--------------------------------------------------------------------------------
	
//	private static ThreadPool g2d_thread_pool;
//	
//	synchronized public static ThreadPool getThreadPool() 
//	{
//		if (g2d_thread_pool == null) {
//			g2d_thread_pool = new ThreadPool("G2D");
//		}
//		return g2d_thread_pool;
//	}
	
//	--------------------------------------------------------------------------------

	
	public static String toString(AttributedString texta) {
		return TextBuilder.toString(texta);
	}

	public static AttributedString linkAttributedString(AttributedString texta, AttributedString textb) {
		return TextBuilder.linkAttributedString(texta, textb);
	}
	
	
	
	
	
	
	/**
	 * 将图片中不透明部分输出成1位信息
	 * @param bi
	 * @return
	 */
	public static byte[] encodeImageMask(BufferedImage bi)
	{
		if (bi != null) {
			try {
				int [] rgb = new int[bi.getWidth() * bi.getHeight()];
				bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), rgb, 0, bi.getWidth());
				ByteArrayOutputStream baos = new ByteArrayOutputStream(rgb.length / 8 + 10);
				BigIOSerialize.putInt(baos, bi.getWidth());
				BigIOSerialize.putInt(baos, bi.getHeight());
				for (int i = 0; i < rgb.length; i+=8) {
					byte state = 0;
					for (int s = 0; s < 8; s ++) {
						int index = i + s;
						if (index < rgb.length && ((rgb[index] & 0xff000000) != 0)) {
							state += (0x01 << s);
						}
					}
					BigIOSerialize.putByte(baos, state);
				}
				return baos.toByteArray();
			} catch (Throwable tx) {
				tx.printStackTrace();
			}
		}
		return null;
	}
	

	/**
	 * @see encodeImageMask(BufferedImage bi)
	 * @param bi
	 * @return
	 */
	public static BufferedImage decodeImageMask(byte[] data, int rgb) 
	{
		if (data != null)
		{
			try 
			{
				ByteArrayInputStream idata = new ByteArrayInputStream(data);
				
				int width	= BigIODeserialize.getInt(idata);
				int height	= BigIODeserialize.getInt(idata);
				BufferedImage buffer = Tools.createImage(width, height);
				int len = width * height;
				for (int i = 0; i < len; i++) {
					byte state = BigIODeserialize.getByte(idata);
					for (int s = 0; s < 8; s++) {
						int index = i * 8 + s;
						int x = index % width;
						int y = index / width;
						int b = ((0x00ff & state) >> s) & 0x01;
						if (b != 0) {
							if (x < width && y < height) {
								buffer.setRGB(x, y, 0xff000000 | rgb);
							}
						}
					}
				}
				return buffer;
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
