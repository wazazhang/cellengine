package com.g2d.display.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.util.Drawing;

public class Label extends UIComponent
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	@Property("文字颜色")
	public Color	textColor	= Color.WHITE;
	
	@Property("text")
	public String	text		= getClass().getSimpleName();
	
	@Property("text_anchor")
	public int		text_anchor	= Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_VCENTER ;

	/**文字是否抗锯齿*/
	@Property("文字是否抗锯齿")
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
			Object v = g.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			Drawing.drawStringBorder(g, text, 0, 0, getWidth(), getHeight(), text_anchor);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, v);
		} else {
			Drawing.drawStringBorder(g, text, 0, 0, getWidth(), getHeight(), text_anchor);
		}
		
		
	}
	
}
