package com.g2d.display.ui;

import java.text.AttributedString;

import com.g2d.Color;
import com.g2d.Engine;
import com.g2d.Graphics2D;
import com.g2d.text.MultiTextLayout;



public class TextPan extends UIComponent
{	
	transient int 						text_draw_x;
	transient int 						text_draw_y;
	transient MultiTextLayout			text				= Engine.getEngine().createMultiTextLayout();
	
	/**文字颜色*/
	public Color 						textColor			= new Color(0xffffffff);
	
	public int 						text_shadow_x		= 0;
	public int 						text_shadow_y		= 0;
	public float					text_shadow_alpha	= 1f;
	public int 						text_shadow_color	= 0;
	
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

	public MultiTextLayout getTextLayout() {
		return text;
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
		text.setShowCaret(false);
		
		text.setWidth((getWidth()-layout.BorderSize*2));
		text_draw_x = layout.BorderSize;
		text_draw_y = layout.BorderSize;
	}
	
	
	public void render(Graphics2D g) 
	{
		super.render(g);
		g.setColor(textColor);
		boolean flag = g.setFontAntialiasing(enable_antialiasing);
		try {
			text.drawText(g, text_draw_x, text_draw_y, 0, 0, getWidth(), getHeight(), 
					text_shadow_x, 
					text_shadow_y,
					text_shadow_alpha,
					text_shadow_color);
		} finally {
			g.setFontAntialiasing(flag);
		}
	}

	
	
}
