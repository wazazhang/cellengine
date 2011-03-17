package com.g2d.display.ui;

import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.util.Drawing;

public class Label extends UIComponent
{	
	public Color	textColor	= Color.WHITE;
	
	public String	text		= getClass().getSimpleName();
	
	public int		text_anchor	= Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_VCENTER ;

	/**文字是否抗锯齿*/
	public boolean	enable_antialiasing	 = false;
	

	public Label() 
	{
		this.enable_focus = false;
	}
	
	public Label(int w, int h) 
	{
		this();
		setSize(w, h);
	}
	
	public Label(String text)
	{
		this();
		this.text = text;
	}
	
	public Label(String text, int width, int height) 
	{
		this();
		this.text = text;
		this.setSize(width, height);
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public void render(Graphics2D g) 
	{
		super.render(g);
		
		g.setColor(textColor);
		if (enable_antialiasing) {
			boolean flag = g.setFontAntialiasing(enable_antialiasing);
			Drawing.drawStringBorder(g, text, 0, 0, getWidth(), getHeight(), text_anchor);
			g.setFontAntialiasing(flag);
		} else {
			Drawing.drawStringBorder(g, text, 0, 0, getWidth(), getHeight(), text_anchor);
		}
		
		
	}
	
}
