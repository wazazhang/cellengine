package com.g2d.util;

import com.cell.CUtil;
import com.g2d.Color;
import com.g2d.Graphics2D;

public class Util 
{
	public static void drawHeapStatus(Graphics2D g, Color textcolor, Color backcolor, int x, int y, int w, int h) 
	{
		drawHeapStatus(g, 
				textcolor,
				backcolor, 
				Runtime.getRuntime().freeMemory(),
				Runtime.getRuntime().totalMemory(),
				Runtime.getRuntime().maxMemory(),
				x, y, w, h
				);
	}
	
	public static void drawHeapStatus(Graphics2D g, Color color, int x, int y, int w, int h) 
	{
		drawHeapStatus(g, 
				color,
				color,
				Runtime.getRuntime().freeMemory(),
				Runtime.getRuntime().totalMemory(),
				Runtime.getRuntime().maxMemory(),
				x, y, w, h
				);
	}
	
	public static void drawHeapStatus(Graphics2D g, Color color, int x, int y, int w, int h, boolean drawText) 
	{
		drawHeapStatus(g, 
				color,
				color,
				Runtime.getRuntime().freeMemory(),
				Runtime.getRuntime().totalMemory(),
				Runtime.getRuntime().maxMemory(),
				x, y, w, h, drawText
				);
	}
	
	public static void drawHeapStatus(
			Graphics2D g, 
			Color backcolor, 
			long free, long total, long max,
			int x, int y, int w, int h) 
	{
		drawHeapStatus(g, 
				backcolor, 
				backcolor,
				free, total, max, x, y, w, h);
	}
	
	public static void drawHeapStatus(
			Graphics2D g, 
			Color textcolor,
			Color backcolor, 
			long free, long total, long max,
			int x, int y, int w, int h) 
	{
		drawHeapStatus(g, textcolor, backcolor, free, total, max, x, y, w, h, true);
	}
	
	public static void drawHeapStatus(
			Graphics2D g, 
			Color textcolor,
			Color backcolor, 
			long free, long total, long max,
			int x, int y, int w, int h, boolean drawText) 
	{
		long used = total - free;
		
		g.setColor(backcolor);
		g.drawRect(x, y, w-1, h-1);
		
		x += 2;
		y += 2;
		w -= 4;
		h -= 4;
		{
			g.setColor(new Color(backcolor.getRed(), backcolor.getGreen(), backcolor.getBlue(), 0.25f));
			g.fillRect(x, y, (int)(w * used / max), h);
			g.setColor(new Color(backcolor.getRed(), backcolor.getGreen(), backcolor.getBlue(), 0.5f));
			g.fillRect(x, y, (int)(w * total/ max), h);
			if (drawText) {
				g.setColor(textcolor);
				Drawing.drawString((Graphics2D)g, 
						getHeapString(free, total, max), 
						x, y, w, h, 
						Drawing.TEXT_ANCHOR_LEFT | Drawing.TEXT_ANCHOR_VCENTER);
			}
		}
	}
	
	public static String getHeapString() {
		return getHeapString(
				Runtime.getRuntime().freeMemory(),
				Runtime.getRuntime().totalMemory(),
				Runtime.getRuntime().maxMemory()
				);
	}
	
	public static String getHeapString(long free, long total, long max) 
	{
		long used = total - free;
		return CUtil.getBytesSizeString(used) + " / " +
			CUtil.getBytesSizeString(total) + " / " + 
			CUtil.getBytesSizeString(max);
	}
	
}
