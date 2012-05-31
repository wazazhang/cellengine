package com.g2d.display.ui;

import java.text.AttributedString;

import com.g2d.Color;
import com.g2d.Engine;
import com.g2d.Graphics2D;
import com.g2d.annotation.Property;
import com.g2d.text.MultiTextLayout;



public class TextPan extends UIComponent
{	
	transient int 						text_draw_x;
	transient int 						text_draw_y;
	transient MultiTextLayout			text				= Engine.getEngine().createMultiTextLayout();
	
	/**文字颜色*/
	@Property("文字颜色")
	public Color 						textColor			= new Color(0xffffffff);

	@Property("阴影偏移X")
	public int text_shadow_x = 0;
	@Property("阴影偏移Y")
	public int text_shadow_y = 0;
	@Property("阴影透明度")
	public float text_shadow_alpha = 1f;
	@Property("阴影颜色")
	public int text_shadow_color = 0;
	
	/**文字是否抗锯齿*/
	@Property("文字是否抗锯齿")
	public boolean	enable_antialiasing	 = false;

	@Property("文本")
	public String Text = "";
	
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
		this.Text = this.text.getText();
	}
	
	public void setText(AttributedString atext) {
		this.text.setText(atext);
		this.Text = this.text.getText();
	}
	
	public void appendLine(String text) {
		this.text.appendText(text);
		this.Text = this.text.getText();
	}
	
	public String getText() {
		return this.text.getText();
	}
	
	public void appendText(String text) {
		this.text.appendText(text);
		this.Text = this.text.getText();
	}
	
	
	public void update() 
	{
		super.update();
		if (!Text.equals(text.getText())) {
			setText(Text);
		}
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
