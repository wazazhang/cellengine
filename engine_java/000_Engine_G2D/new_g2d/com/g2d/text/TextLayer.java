package com.g2d.text;

import java.text.AttributedString;

import com.g2d.BufferedImage;
import com.g2d.Color;
import com.g2d.Engine;
import com.g2d.Graphics2D;
import com.g2d.Tools;
import com.g2d.geom.Dimension;

/**
 * 用于高速渲染的文本缓冲，一般用于静态文字显示。
 * @author WAZA
 */
public class TextLayer
{
	private static BufferedImage 	sb = Engine.getEngine().createImage(1, 1);
	private static Graphics2D		sg = sb.createGraphics();
	
	private MultiTextLayout			layout;
	private BufferedImage 			buffer;
	
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
			this.layout = Engine.getEngine().createMultiTextLayout();
			this.layout.setSingleLine(true);
			this.layout.setReadOnly(true);
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
		
		bg.drawImage(old_buffer, 0, 0);
		bg.drawImage(old_buffer, 1, 0);
		bg.drawImage(old_buffer, 2, 0);
		bg.drawImage(old_buffer, 0, 1);
//		bg.drawImage(old_buffer, 1, 1);
		bg.drawImage(old_buffer, 2, 1);
		bg.drawImage(old_buffer, 0, 2);
		bg.drawImage(old_buffer, 1, 2);
		bg.drawImage(old_buffer, 2, 2);
		
		Tools.toAlpha(buffer, border_color.getAlpha()/255f, border_color.getARGB());
		
		bg.drawImage(old_buffer, 1, 1);
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
			bg.drawImage(old_buffer, sx, sy);
			Tools.toAlpha(buffer, border_color.getAlpha(), border_color.getARGB());
			bg.drawImage(old_buffer, gx, gy);
		}
		
	}
	
	public int getWidth() {
		return buffer.getWidth();
	}
	
	public int getHeight() {
		return buffer.getHeight();
	}
	
	public void render(Graphics2D g, int x, int y) {
		g.drawImage(buffer, x, y);
	}
	
	public BufferedImage getBuffer() {
		return buffer;
	}
}
