package com.g2d.display.ui.text;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import com.g2d.Tools;

/**
 * 用于高速渲染的文本缓冲，一般用于静态文字显示。
 * @author WAZA
 */
public class TextLayer
{
	private static BufferedImage 	sb = Tools.createImage(1, 1);
	private static Graphics2D		sg = sb.createGraphics();
	
	private MultiTextLayout	layout;
	private BufferedImage 	buffer;
	
	public TextLayer(AttributedString atext, Color color) {
		setText(atext, color) ;
	}
	
	public TextLayer(String atext, Color color) {
		setText(atext, color) ;
	}
	
	public void setText(String atext, Color color) {
		try {
			this.setText(TextBuilder.buildScript(atext), color);
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	public void setText(AttributedString atext, Color color) 
	{
		this.buffer = sb;
		try {
			this.layout = new MultiTextLayout(true);
			this.layout.is_read_only  = true;
			this.layout.setText(atext);
			Dimension size = layout.drawText(sg, 0, 0);
			this.buffer = Tools.createImage(size.width, size.height);
			Graphics2D bg = buffer.createGraphics();
			bg.setColor(color);
			this.layout.drawText(bg, 0, 0, 0, 0, buffer.getWidth(), buffer.getHeight(), 0, 0, 0, 0);
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	public void setBorder(Color border_color) 
	{
		BufferedImage old_buffer = buffer;
		this.buffer = Tools.createImage(buffer.getWidth()+2, buffer.getHeight()+2);
		Graphics2D bg = buffer.createGraphics();
		
		bg.drawImage(old_buffer, 0, 0, null);
		bg.drawImage(old_buffer, 1, 0, null);
		bg.drawImage(old_buffer, 2, 0, null);
		bg.drawImage(old_buffer, 0, 1, null);
//		bg.drawImage(old_buffer, 1, 1, null);
		bg.drawImage(old_buffer, 2, 1, null);
		bg.drawImage(old_buffer, 0, 2, null);
		bg.drawImage(old_buffer, 1, 2, null);
		bg.drawImage(old_buffer, 2, 2, null);
		
		Tools.toAlpha(buffer, border_color.getAlpha()/255f, border_color.getRGB());
		
		bg.drawImage(old_buffer, 1, 1, null);
	}
	
	public void setShwdow(Color border_color, int shadow_x, int shadow_y) 
	{
		if (shadow_x != 0 || shadow_y != 0) 
		{
			BufferedImage old_buffer = buffer;
			this.buffer = Tools.createImage(buffer.getWidth()+Math.abs(shadow_x), buffer.getHeight()+Math.abs(shadow_y));
			Graphics2D bg = buffer.createGraphics();

			int sx = shadow_x > 0 ? shadow_x : 0;
			int sy = shadow_y > 0 ? shadow_y : 0;
			int gx = shadow_x < 0 ? -shadow_x : 0;
			int gy = shadow_y < 0 ? -shadow_y : 0;
			bg.drawImage(old_buffer, sx, sy, null);
			Tools.toAlpha(buffer, border_color.getAlpha(), border_color.getRGB());
			bg.drawImage(old_buffer, gx, gy, null);
		}
		
	}
	
	public int getWidth() {
		return buffer.getWidth();
	}
	
	public int getHeight() {
		return buffer.getHeight();
	}
	
	public void render(Graphics2D g, int x, int y) {
		g.drawImage(buffer, x, y, null);
	}
	
	public BufferedImage getBuffer() {
		return buffer;
	}
}
