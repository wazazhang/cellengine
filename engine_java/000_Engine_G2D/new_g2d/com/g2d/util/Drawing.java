package com.g2d.util;


import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.Paint;
import com.g2d.Tools;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.geom.Rectangle;
import com.g2d.geom.Shape;
import com.g2d.text.MultiTextLayout;

public class Drawing 
{
	final static public void fillPaintRect(Graphics2D g, Paint paint, int x, int y, int width, int height) 
	{
		g.pushPaint();
		g.translate(x, y);
		try {
			g.setPaint(paint);
			g.fillRect(0, 0, width, height);
		} finally {
			g.translate(-x, -y);
			g.popPaint();
		}
	}	
	
	final static public void fillPaint(Graphics2D g, Paint paint, int x, int y, Shape shape) 
	{
		g.pushPaint();
		g.translate(x, y);
		try {
			g.setPaint(paint);
			g.fill(shape);
		} finally {
			g.translate(-x, -y);
			g.popPaint();
		}
	}
	
	final static public void drawRoundImage(Graphics2D g, Image src, int x, int y, int width, int height)
	{
		g.pushClip();
		
		g.clipRect(x , y , width, height);
		
		int w = src.getWidth();
		int h = src.getHeight();
		
		for (int dx = 0; dx < width;) {
			for (int dy = 0; dy < height;) {
				g.drawImage(src, x + dx, y + dy);
				dy += h;
			}
			dx += w;
		}
		
		g.popClip();
	}
	
	final static public void drawRoundImageH(Graphics2D g, Image src, int x, int y, int width, int height)
	{
		g.pushClip();
		
		g.clipRect(x , y , width, height);
		
		int w = src.getWidth();
		
		for(int dx=0;dx<width;){
			g.drawImage(src, x+dx, y, w, height);
			dx += w;
		}
		
		g.popClip();
	}
	
	final static public void drawRoundImageV(Graphics2D g, Image src, int x, int y, int width, int height)
	{
		g.pushClip();
		
		g.clipRect(x , y , width, height);
		
		int h = src.getHeight();
		
		for(int dy=0;dy<height;){
			g.drawImage(src, x, y+dy, width, h);
			dy += h;
		}
		
		g.popClip();
	}
	

//	-----------------------------------------------------------------------------------------------------------------------------
//	draw string
//	-----------------------------------------------------------------------------------------------------------------------------
	
	final static public int TEXT_ANCHOR_LEFT 	= 0x00;
	final static public int TEXT_ANCHOR_RIGHT 	= 0x01;
	final static public int TEXT_ANCHOR_HCENTER = 0x02;
	final static public int TEXT_ANCHOR_TOP 	= 0x00;
	final static public int TEXT_ANCHOR_BOTTON 	= 0x10;
	final static public int TEXT_ANCHOR_VCENTER = 0x20;
	
	final static public Rectangle drawString(Graphics2D g, String src, int x, int y)
	{
		Rectangle rect = g.getFont().getStringBounds(src, g);
		rect.setBounds(0, 0, rect.width, rect.height);
		g.drawString(src, x, y);
		return rect;
	}
	
	final static public Rectangle drawString(Graphics2D g, String src, int x, int y, int anchor)
	{
		Rectangle rect = g.getFont().getStringBounds(src, g);
		rect.setBounds(0, 0, rect.width, rect.height);

		if ((anchor & TEXT_ANCHOR_RIGHT) != 0) {
			x -= rect.width;
		} else if ((anchor & TEXT_ANCHOR_HCENTER) != 0) {
			x -= (rect.width >> 1);
		}

		if ((anchor & TEXT_ANCHOR_BOTTON) != 0) {
			y -= rect.height;
		} else if ((anchor & TEXT_ANCHOR_VCENTER) != 0) {
			y -= (rect.height >> 1);
		}
		
		g.drawString(src, x, y);
		
		return rect;
	}
	
	final static public Rectangle drawString(Graphics2D g, String src, int x, int y, int w, int h, int anchor)
	{
		Rectangle rect = g.getFont().getStringBounds(src, g);
		rect.setBounds(0, 0, rect.width, rect.height);
		
		if ((anchor & TEXT_ANCHOR_RIGHT) != 0) {
			x += w - rect.width;
		} else if ((anchor & TEXT_ANCHOR_HCENTER) != 0) {
			x += ((w - rect.width) >> 1);
		}

		if ((anchor & TEXT_ANCHOR_BOTTON) != 0) {
			y += h - rect.height;
		} else if ((anchor & TEXT_ANCHOR_VCENTER) != 0) {
			y += ((h - rect.height) >> 1);
		}
		
		g.drawString(src, x, y);
		
		return rect;
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------
//	draw string border
//	-----------------------------------------------------------------------------------------------------------------------------
	
	final static public Rectangle drawStringBorder(Graphics2D g, String src, int x, int y, int w, int h, int anchor)
	{
		return drawStringBorder(g, src, x, y, w, h, anchor, Color.BLACK);
	}
	
	final static public Rectangle drawStringBorder(Graphics2D g, String src, int x, int y, int anchor)
	{
		return drawStringBorder(g, src, x, y, anchor, Color.BLACK);
	}
	
	final static public Rectangle drawStringBorder(Graphics2D g, String src, int x, int y, int anchor, Color back_color)
	{
		if ((anchor & TEXT_ANCHOR_RIGHT) != 0) {
			x -= 1;
		} else if ((anchor & TEXT_ANCHOR_HCENTER) != 0) {
		} else {
			x += 1;
		}
		if ((anchor & TEXT_ANCHOR_BOTTON) != 0) {
			y -= 1;
		} else if ((anchor & TEXT_ANCHOR_VCENTER) != 0) {
		} else {
			y += 1;
		}
		Color c = g.getColor();
		{
			g.setColor(back_color);
			drawString(g, src, x-1, y-1, anchor);
			drawString(g, src, x-1, y-0, anchor);
			drawString(g, src, x-1, y+1, anchor);
			drawString(g, src, x-0, y-1, anchor);
			drawString(g, src, x-0, y+1, anchor);
			drawString(g, src, x+1, y-1, anchor);
			drawString(g, src, x+1, y-0, anchor);
			drawString(g, src, x+1, y+1, anchor);
		}
		g.setColor(c);
		return drawString(g, src, x, y, anchor);
	}

	final static public Rectangle drawStringBorder(Graphics2D g, String src, int x, int y, int w, int h, int anchor, Color back_color)
	{
		if ((anchor & TEXT_ANCHOR_RIGHT) != 0) {
			x -= 1;
		} else if ((anchor & TEXT_ANCHOR_HCENTER) != 0) {
		} else {
			x += 1;
		}
		if ((anchor & TEXT_ANCHOR_BOTTON) != 0) {
			y -= 1;
		} else if ((anchor & TEXT_ANCHOR_VCENTER) != 0) {
		} else {
			y += 1;
		}
		Color c = g.getColor();
		{
			g.setColor(back_color);
			drawString(g, src, x-1, y-1, w, h, anchor);
			drawString(g, src, x-1, y-0, w, h, anchor);
			drawString(g, src, x-1, y+1, w, h, anchor);
			drawString(g, src, x-0, y-1, w, h, anchor);
			drawString(g, src, x-0, y+1, w, h, anchor);
			drawString(g, src, x+1, y-1, w, h, anchor);
			drawString(g, src, x+1, y-0, w, h, anchor);
			drawString(g, src, x+1, y+1, w, h, anchor);
		}
		g.setColor(c);
		return drawString(g, src, x, y, w, h, anchor);
	}
	

//	-----------------------------------------------------------------------------------------------------------------------------
//	draw string shadow
//	-----------------------------------------------------------------------------------------------------------------------------
	
	
	final static public Rectangle drawStringShadow(Graphics2D g, String src, int x, int y, int anchor)
	{
		return drawStringShadow(g, src, x, y, anchor, Color.BLACK);
	}

	final static public Rectangle drawStringShadow(Graphics2D g, String src, int x, int y, int w, int h, int anchor)
	{
		return drawStringShadow(g, src, x, y, w, h, anchor, Color.BLACK);
	}
	
	final static public Rectangle drawStringShadow(Graphics2D g, String src, int x, int y, int anchor, Color back_color)
	{
		if ((anchor & TEXT_ANCHOR_RIGHT) != 0) {
			x -= 1;
		}
		if ((anchor & TEXT_ANCHOR_BOTTON) != 0) {
			y -= 1;
		}
		Color c = g.getColor();
		{
			g.setColor(back_color);
			drawString(g, src, x-0, y+1, anchor);
			drawString(g, src, x+1, y+1, anchor);
		}
		g.setColor(c);
		return drawString(g, src, x, y, anchor);
	}

	final static public Rectangle drawStringShadow(Graphics2D g, String src, int x, int y, int w, int h, int anchor, Color back_color)
	{
		if ((anchor & TEXT_ANCHOR_RIGHT) != 0) {
			x -= 1;
		}
		if ((anchor & TEXT_ANCHOR_BOTTON) != 0) {
			y -= 1;
		}
		Color c = g.getColor();
		{
			g.setColor(back_color);
			drawString(g, src, x-0, y+1, w, h, anchor);
			drawString(g, src, x+1, y+1, w, h, anchor);
		}
		g.setColor(c);
		return drawString(g, src, x, y, w, h, anchor);
	}
	

//	-----------------------------------------------------------------------------------------------------------------------------
//	draw advance string
//	-----------------------------------------------------------------------------------------------------------------------------
	
	final static public void drawString(Graphics2D g, MultiTextLayout src, int x, int y, int anchor)
	{
		if ((anchor & TEXT_ANCHOR_RIGHT) != 0) {
			x -= src.getWidth();
		} else if ((anchor & TEXT_ANCHOR_HCENTER) != 0) {
			x -= (src.getWidth() >> 1);
		}

		if ((anchor & TEXT_ANCHOR_BOTTON) != 0) {
			y -= src.getHeight();
		} else if ((anchor & TEXT_ANCHOR_VCENTER) != 0) {
			y -= (src.getHeight() >> 1);
		}
		
		src.drawText(g, x, y);
		
	}
	
	final static public void drawString(Graphics2D g, MultiTextLayout src, int x, int y, int w, int h, int anchor)
	{
		if ((anchor & TEXT_ANCHOR_RIGHT) != 0) {
			x += w - src.getWidth();
		} else if ((anchor & TEXT_ANCHOR_HCENTER) != 0) {
			x += ((w - src.getWidth()) >> 1);
		}

		if ((anchor & TEXT_ANCHOR_BOTTON) != 0) {
			y += h - src.getHeight();
		} else if ((anchor & TEXT_ANCHOR_VCENTER) != 0) {
			y += ((h - src.getHeight()) >> 1);
		}
		
		src.drawText(g, x, y, 0, 0, w, h);
	}
	
	final static public void drawStringShwdow(Graphics2D g, MultiTextLayout src, int x, int y, int w, int h, int anchor)
	{
		if ((anchor & TEXT_ANCHOR_RIGHT) != 0) {
			x += w - src.getWidth();
		} else if ((anchor & TEXT_ANCHOR_HCENTER) != 0) {
			x += ((w - src.getWidth()) >> 1);
		}

		if ((anchor & TEXT_ANCHOR_BOTTON) != 0) {
			y += h - src.getHeight();
		} else if ((anchor & TEXT_ANCHOR_VCENTER) != 0) {
			y += ((h - src.getHeight()) >> 1);
		}

		src.drawText(g, x, y, 0, 0, w, h, 1, 1, 1f, 0);
	}
	
	
	
	
	
	
	
	
	
	/**<pre>
	 * 将图片向外渐隐扩展显示。
	 * 显示范围vtimer递增
	 *   |---- keep_time ----|
	 *   |---- cycle_time ------------|
	 *   ^                   ^        ^
	 *   1                   2        3
	 * 1~2阶段：
	 *   从原尺寸过渡到目标尺寸为（src_size -> src_size+out_size），
	 *   alpha度从1过度到0
	 * 2~3阶段：
	 *   不显示
	 * @param g
	 * @param image
	 * @param src_w 原尺寸
	 * @param src_h 原尺寸
	 * @param out_w 放大多少尺寸
	 * @param out_h 放大多少尺寸
	 * @param vtimer 当前时间（帧）
	 * @param keep_time  显示保持时间（帧）
	 * @param cycle_time 显示循环时间（帧）必须大于keep_time
	 */
	public static void drawImageOutShadow(
			Graphics2D g, 
			Image image,
			int src_w, int src_h, 
			int out_w, int out_h,
			int vtimer, 
			int keep_time, 
			int cycle_time)
	{
		int dtime = vtimer % cycle_time;
		if (dtime < keep_time) {
			float alpha = ((float)(dtime)/(float)keep_time);
			int tw = src_w + (int)(out_w * alpha);
			int th = src_h + (int)(out_h * alpha);
			g.pushComposite();
			try {
				Tools.setAlpha(g, 1 - alpha);
				g.drawImage(image, (src_w-tw)>>1, (src_h-th)>>1, tw, th);
			} finally {
				g.popComposite();
			}
		}
	}
	

	/**
	 * @see #drawImageOutShadow(Graphics2D, Image, int, int, int, int, int, int, int)
	 * @param g
	 * @param image
	 * @param src_w
	 * @param src_h
	 * @param out_w
	 * @param out_h
	 * @param vtimer
	 * @param keep_time
	 * @param cycle_time
	 */
	public static void drawUILayoutOutShadow(
			Graphics2D g, 
			UILayout image,
			int src_w, int src_h, 
			int out_w, int out_h,
			int vtimer, 
			int keep_time, 
			int cycle_time)
	{
		int dtime = vtimer % cycle_time;
		if (dtime < keep_time) {
			float alpha = ((float)(dtime)/(float)keep_time);
			int tw = src_w + (int)(out_w * alpha);
			int th = src_h + (int)(out_h * alpha);
			g.pushComposite();
			try {
				Tools.setAlpha(g, 1 - alpha);
				image.render(g, (src_w-tw)>>1, (src_h-th)>>1, tw, th);
			} finally {
				g.popComposite();
			}
		}
	}

	
}
