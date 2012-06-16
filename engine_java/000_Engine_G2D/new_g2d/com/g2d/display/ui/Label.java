package com.g2d.display.ui;

import com.g2d.Color;
import com.g2d.Font;
import com.g2d.Graphics2D;
import com.g2d.annotation.Property;
import com.g2d.util.Drawing;
import com.g2d.util.Drawing.TextAnchor;

public class Label extends UIComponent
{	
	@Property("文字颜色")
	public Color textColor = Color.WHITE;
	
	@Property("文字边颜色")
	public Color textBorderColor = Color.BLACK;
	
	@Property("文字边透明度%")
	public float textBorderAlpha = 100.0f;

	@Property("text")
	public String text = getClass().getSimpleName();

	@Property("文字类型")
	public Font textFont = null;
	
	@Property("文字对齐")
	public TextAnchor text_anchor = TextAnchor.C_C;
	
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
		Font oldf = g.getFont();
		try {
			if (textFont != null) {
				g.setFont(textFont);
			}
			if (enable_antialiasing) {
				g.setFontAntialiasing(true);
			}
			textBorderColor.setAlpha(Math.min(Math.abs(textBorderAlpha)/100f, 1f));
			g.setColor(textColor);
			if (enable_antialiasing) {
				boolean flag = g.setFontAntialiasing(enable_antialiasing);
				Drawing.drawStringBorder(g, text, 0, 0, getWidth(),
						getHeight(), text_anchor, textBorderColor);
				g.setFontAntialiasing(flag);
			} else {
				Drawing.drawStringBorder(g, text, 0, 0, getWidth(),
						getHeight(), text_anchor, textBorderColor);
			}
		} finally {
			g.setFont(oldf);
		}
		
		
	}
	
}
