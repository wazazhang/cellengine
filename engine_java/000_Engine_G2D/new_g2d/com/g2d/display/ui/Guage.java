package com.g2d.display.ui;


import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.Tools;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.util.Drawing;

public class Guage extends UIComponent 
{	
	double 					max;
	double 					min;
	double 					value;
	
	public Color			textColor;

	public boolean 			is_show_text;
	public boolean 			is_show_percent;
	
	public String			text = null;
	
	transient public Image	strip_fore;
	
	public Guage() 
	{
		max 			= 100;
		min 			= -50;
		value 			= 50;
		
		textColor 		= Color.WHITE;
		is_show_text	= true;
		is_show_percent	= true;
		
		strip_fore = Tools.createImage(100, 10);
		Graphics2D g2d = strip_fore.getGraphics();
		g2d.setColor(Color.GREEN);
		g2d.fillRect(0, 0, 100, 10);
	}
	
	public Guage(int w, int h)
	{
		this();
		setSize(w, h);
	}
	
	public double getMax() {
		return max;
	}
	
	public double getMin() {
		return min;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setRange(double max, double min) {
		this.max = Math.max(max, min);
		this.min = Math.min(max, min);
		this.value = Math.max(value, min);
		this.value = Math.min(value, max);
	}
	
	public void setMax(double max) {
		this.max = max;
		this.value = Math.min(value, max);
	}
	
	public void setValue(double value) {
		value = Math.max(value, min);
		value = Math.min(value, max);
		this.value = value;
	}
	
	public int getPercent() {
		double sw = max - min;
		double sx = value - min;
		return (int)(sx / sw * 100);
	}
	
	public void render(Graphics2D g)
	{
		super.render(g);
		
		double sw = max - min;
		double sx = value - min;
		UILayout ui = this.getLayout();
		int x = ui.BorderSize;
		int y = ui.BorderSize;
		int w = getWidth() - ui.BorderSize * 2;
		int h = getHeight() - ui.BorderSize * 2;
		
//		g.drawImage(strip_back, 0, 0, getWidth(), getHeight(), null);
		g.clipRect(x, y, (int)(w*sx/sw), h);
		g.drawImage(strip_fore, x, y, w, h);
		g.setClip(local_bounds);
		
		if (is_show_text) {
			drawText(g, sx, sw);
		}
	}
	
	protected void drawText(Graphics2D g, double sx, double sw) 
	{
		g.setColor(textColor);
		
		int anchor = Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_VCENTER;
		if (text != null) {
			Drawing.drawString(g, text, 0, 0, getWidth(), getHeight(), anchor);
		} else if (is_show_percent || min < 0) {
			Drawing.drawString(g, (int)(sx / sw * 100) + "%", 0, 0, getWidth(), getHeight(), anchor);
		} else {
			Drawing.drawString(g, (int)value + "/"+ (int)max, 0, 0, getWidth(), getHeight(), anchor);
		}
		
	}
}
