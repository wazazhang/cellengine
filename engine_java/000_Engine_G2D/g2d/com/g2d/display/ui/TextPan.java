package com.g2d.display.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;
import java.text.AttributedString;

import com.g2d.Version;
import com.g2d.display.ui.text.MultiTextLayout;



public class TextPan extends UIComponent implements Serializable
{
	private static final long 			serialVersionUID = Version.VersionG2D;
	
	transient int 						text_draw_x;
	transient int 						text_draw_y;
	transient MultiTextLayout			text				= new MultiTextLayout();
	
	/**文字颜色*/
	public Color 						textColor			= new Color(0xffffffff, true);

	/**文字是否抗锯齿*/
	public boolean	enable_antialiasing	 = false;
	
	
	public TextPan() 
	{
		this("");
	}
	
	public TextPan(String text)
	{
		this(text, 100, 100);
	}
	
	public TextPan(String text, int w, int h)
	{
		enable_key_input	= false;
		enable_mouse_wheel	= false;
		
		setText(text);
		setSize(w, h);
	}
	
	public void setText(String text)
	{
//		AttributedString astring = new AttributedString(text);
//		astring.addAttribute(TextAttribute.FONT, new Font("song", Font.PLAIN, 12), 0, text.length());
		this.text.setText(text);
	}
	
	public void setText(AttributedString atext) {
		this.text.setText(atext);
	}
	
	public void appendLine(String text) {
		this.text.appendText(text);
	}
	
	public String getText() {
		return this.text.getText();
	}
	
	public void appendText(String text) {
		this.text.appendText(text);
	}
	
	
	public void update() 
	{
		super.update();
		text.is_show_caret = false;
		
		text.setWidth((getWidth()-layout.BorderSize*2));
		text_draw_x = layout.BorderSize;
		text_draw_y = layout.BorderSize;
	}
	
	
	public void render(Graphics2D g) 
	{
		super.render(g);
		g.setColor(textColor);
		if (enable_antialiasing) {
			Object v = g.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			text.drawText(g, text_draw_x, text_draw_y, 0, 0, getWidth(), getHeight());
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, v);
		} else {
			text.drawText(g, text_draw_x, text_draw_y, 0, 0, getWidth(), getHeight());
		}
	}

	
	
}
