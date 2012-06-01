package com.g2d.awt.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.cell.CIO;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CSprite;
import com.cell.io.BigIODeserialize;
import com.cell.io.BigIOSerialize;
import com.cell.io.CFile;

import com.g2d.Engine;
import com.g2d.java2d.impl.AwtEngine;


public class Tools
{

//	--------------------------------------------------------------------------------

	static GraphicsEnvironment		ge;
	static GraphicsDevice			gd;
	static GraphicsConfiguration	gc;
	
	static Font 					default_font;
	static AlphaComposite[] 		all_composite;
	
	static
	{
		ge		= GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd		= ge.getDefaultScreenDevice();
		gc		= gd.getDefaultConfiguration();
		
		all_composite = new AlphaComposite[] {
				AlphaComposite.Clear, 
				AlphaComposite.Src,
				AlphaComposite.Dst,
				AlphaComposite.SrcOver,
				AlphaComposite.DstOver,
				AlphaComposite.SrcIn, 
				AlphaComposite.DstIn, 
				AlphaComposite.SrcOut,
				AlphaComposite.DstOut,
				AlphaComposite.SrcAtop,
				AlphaComposite.DstAtop, 
				AlphaComposite.Xor,
				};
		
		try{
			Graphics2D g2d = gc.createCompatibleImage(1, 1, Transparency.TRANSLUCENT).createGraphics();
			default_font = g2d.getFont();
			g2d.dispose();
		}catch(Exception err){
			err.printStackTrace();
		}
	}
	
//	--------------------------------------------------------------------------------

	/**
	 * {@link CommonCanvasAdapter}.setDefaultFont(Font font)
	 * @param font
	 */
	static void setDefaultFont(Font font)
	{
		default_font = font;
	}

	public static Font getDefaultFont()
	{
		return default_font;
	}
	
	public static Cursor createCustomCursor(Image cursor, Point hotSpot, String name)
	{
		Dimension bestCursorSize = Toolkit.getDefaultToolkit().getBestCursorSize(
				cursor.getWidth(null), 
				cursor.getHeight(null));

//		Dimension bestCursorSize = new Dimension(
//				cursor.getWidth(null),
//				cursor.getHeight(null));

//		System.out.println(
//				cursor.getWidth(null) +","+ cursor.getHeight(null) + " -> " + 
//				bestCursorSize.width + "," + bestCursorSize.height
//		);
		
		
		
		if (cursor.getWidth(null) > bestCursorSize.width ||
			cursor.getHeight(null) > bestCursorSize.height ) 
		{
			BufferedImage bufferedImage = combianImage(
					bestCursorSize.width, 
					bestCursorSize.height,
					cursor);
			
			return Toolkit.getDefaultToolkit().createCustomCursor(
					bufferedImage,
					new Point(
					(int)(hotSpot.getX() * bestCursorSize.width / cursor.getWidth(null)),
					(int)(hotSpot.getY() * bestCursorSize.height/ cursor.getHeight(null))
					), 
					name);
		}
		else
		{
			BufferedImage bufferedImage = createImage(
					bestCursorSize.width, 
					bestCursorSize.height);
			
			Graphics g = bufferedImage.getGraphics();
			g.drawImage(cursor, 0, 0, null);
			g.dispose();
			
			return Toolkit.getDefaultToolkit().createCustomCursor(
					bufferedImage, hotSpot, name);
		}
	}

	/**
	 * 由指定的精灵，创建一个动画鼠标
	 * @param cspr
	 * @return
	 */
	public static Cursor[] createSpriteCursor(String name, CSprite cspr, int anim)
	{
		int frame_count = cspr.getFrameCount(anim);

		Cursor[] cursors = new Cursor[frame_count];

		for (int f = 0; f < frame_count; f++) {
			BufferedImage img = getFrameSnapshot(cspr, anim, f);
			CCD bounds = cspr.getFrameBounds(anim, f);
			Cursor cur = Tools.createCustomCursor(img, 
					new Point(-bounds.X1, -bounds.Y1), 
					name + "_" + anim + "_" + f);
			cursors[f] = cur;
//			System.out.println(name + "_" + anim + "_" + f);
		}

		return cursors;
	}
	
	public static BufferedImage getFrameSnapshot(CSprite cspr, int anim, int frame)
	{
		CCD border = cspr.getFrameBounds(anim, frame);
		
		BufferedImage ret = Tools.createImage(border.getWidth(), border.getHeight());
		Graphics2D g = (Graphics2D)ret.getGraphics();
		cspr.render(wrap(g), -border.X1, -border.Y1, anim, frame);
		g.dispose();
		
		return ret;
	}
	

//	--------------------------------------------------------------------------------
	
	
	
	public static ImageIcon createIcon(Image image)
	{
		ImageIcon icon = new ImageIcon(image);
		return icon;
	}
	
	
//	--------------------------------------------------------------------------------
	
	static public void setAlpha(Graphics2D g, float alpha)
	{
		if (alpha>=0 && alpha<1f){
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		}
	}
	
	static public void setTextAntialiasing(Graphics2D g, boolean enable)
	{
		if (enable) {
			g.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING, 
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
		else {
			g.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING, 
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		}
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
			BufferedImage buf = gc.createCompatibleImage(width, height, Transparency.TRANSLUCENT);

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
			BufferedImage buf = gc.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
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
			BufferedImage buf = gc.createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);
			Graphics2D g = (Graphics2D)buf.createGraphics();
			IGraphics ig = wrap(g);
			ig.drawImage(image, 0, 0, 0);
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
			BufferedImage buf = gc.createCompatibleImage(bounds.getWidth(), bounds.getHeight(), Transparency.TRANSLUCENT);
			Graphics2D g = (Graphics2D)buf.createGraphics();
			IGraphics ig = wrap(g);
			try {
				spr.render(ig, -bounds.X1, -bounds.Y1, anim, frame);
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
	
	static public BufferedImage combianImage(int width, int height, Image ... args)
	{
		try
		{
			BufferedImage dst = createImage(width, height);
			Graphics2D g = (Graphics2D)dst.createGraphics();
			for (Image img : args) {
				g.drawImage(img, 0, 0, width, height, null);
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
	
	static public BufferedImage scaleImage(Image args, double scale)
	{
		double dw = args.getWidth(null) * scale;
		double dh = args.getHeight(null) * scale;
		return combianImage((int)dw, (int)dh, args);
	}
	
	static public BufferedImage createTransformImage(Image src, int width, int height, AffineTransform transform)
	{
		try
		{
			BufferedImage dst = createImage(width, height);
			Graphics2D g = (Graphics2D)dst.createGraphics();
			
			g.setTransform(transform);
			g.drawImage(src, 0, 0, null);
			
			g.dispose();
			return dst;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		return null;
	}
	
	static public BufferedImage createAlphaImage(Image src, float alpha)
	{
		try
		{
			BufferedImage dst = createImage(src.getWidth(null), src.getHeight(null));
			Graphics2D g = (Graphics2D)dst.createGraphics();
			
			setAlpha(g, alpha);
			g.drawImage(src, 0, 0, null);
			
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
		try
		{
			InputStream is = CIO.loadStream(file);

			BufferedImage src = ImageIO.read(is);
			
//			Image buf = gc.createCompatibleVolatileImage(src.getWidth(), src.getHeight(), Transparency.TRANSLUCENT);
			BufferedImage buf = gc.createCompatibleImage(src.getWidth(), src.getHeight(), Transparency.TRANSLUCENT);

			Graphics g = buf.getGraphics();
			g.drawImage(src, 0, 0, null);
			g.dispose();
			return buf;
		}
		catch(Exception err)
		{
			System.err.println(file);
			err.printStackTrace();
		}
		return null;
	}
	
	
	static public BufferedImage readImage(InputStream is)
	{
		try
		{
			BufferedImage src = ImageIO.read(is);
			
//			Image buf = gc.createCompatibleVolatileImage(src.getWidth(), src.getHeight(), Transparency.TRANSLUCENT);
			BufferedImage buf = gc.createCompatibleImage(src.getWidth(), src.getHeight(), Transparency.TRANSLUCENT);

			Graphics g = buf.getGraphics();
			g.drawImage(src, 0, 0, null);
			g.dispose();
			return buf;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		
		return null;
		
	}
	
	static public BufferedImage subImage(Image src, int sx, int sy, int sw, int sh)
	{
		try
		{
//			Image buf = gc.createCompatibleVolatileImage(src.getWidth(), src.getHeight(), Transparency.TRANSLUCENT);
			BufferedImage buf = gc.createCompatibleImage(sw, sh, Transparency.TRANSLUCENT);

			Graphics g = buf.getGraphics();
			g.drawImage(src, -sx, -sy, null);
			g.dispose();
			return buf;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		
		return null;
		
	}
	
	static public BufferedImage cloneImage(Image src)
	{
		try
		{
			BufferedImage buf = gc.createCompatibleImage(src.getWidth(null), src.getHeight(null), Transparency.TRANSLUCENT);
			
			Graphics g = buf.getGraphics();
			g.drawImage(src, 0, 0, null);
			g.dispose();
			
			return buf;
		}
		catch(Exception err)
		{
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
			ImageIO.write(buf, format, baos);
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
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection text = new StringSelection(str);
        clipboard.setContents(text,null);
	}
	
	static public String getClipboardText(){
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(clipboard);
        DataFlavor flavor = DataFlavor.stringFlavor;
        if(contents.isDataFlavorSupported(flavor)){
            try{
                return (String)contents.getTransferData(flavor);
            }catch(Exception ee){ee.printStackTrace();}    
        }
        return "";
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
	
	
	
	
	
	
	
	
	
	
	
//	----------------------------------------------------------------------------------------------------------------
	
	/**
	 * 使用java2d包装渲染系统
	 * @param g2d
	 * @return
	 */
	static public IGraphics wrap(java.awt.Graphics2D g2d) 
	{
		return AwtEngine.wrap(g2d);
	}

	/**
	 * 包装为普通界面使用的图片
	 * @param img
	 * @return
	 */
	static public com.g2d.BufferedImage wrap_awt(java.awt.Image img) 
	{
		return AwtEngine.wrap_awt(img);
	}
	
	/**
	 * 将image解包为java.awt.image.BufferedImage
	 * @param image
	 * @return
	 */
	static public java.awt.image.BufferedImage unwrap(IImage image) 
	{
		return AwtEngine.unwrap(image);
	}
	

	/**
	 * 包装为渲染系统用的图片
	 * @param img
	 * @return
	 */
	static public com.g2d.BufferedImage wrap_g2d(java.awt.Image img) 
	{
		return AwtEngine.wrap_g2d(img);
	}
	
	static public com.g2d.BufferedImage dialogLoadImage(
			Frame parent, 
			String path, 
			AtomicReference<File> outpath)
	{
		FileDialog fd = new FileDialog(parent);
		if (parent != null) {
			fd.setLocation(parent.getLocation());
		}
		fd.setDirectory(path);
		fd.setTitle("选择图片");
		fd.setMode(FileDialog.LOAD);
		fd.setVisible(true);
		try {
			if (fd.getFile() != null) {
				String file = fd.getDirectory() + fd.getFile();
				File ofile = new File(file).getCanonicalFile();
				System.out.println("You chose to open this file: " + file);
				FileInputStream fis = new FileInputStream(ofile);
				com.g2d.BufferedImage  ret = Engine.getEngine().createImage(fis);
				outpath.set(ofile);
				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
