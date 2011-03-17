package com.g2d.display.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.AttributedString;

import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.ui.text.MultiTextLayout;
import com.g2d.display.ui.text.TextBuilder;
import com.g2d.util.Drawing;

/**
 * 用于显示高级文字的按钮
 * @author WAZA
 */
public class RichButton extends BaseButton 
{
	private static final long serialVersionUID	= Version.VersionG2D;
	
	@Property("text")
	transient protected MultiTextLayout	text		= new MultiTextLayout(true);
	
	@Property("text_anchor")
	public int 					text_anchor = Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_VCENTER ;
	public int 					text_offset_x;
	public int 					text_offset_y;
	
	public Object				antialiasing = RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
	 
	public RichButton(String script, int width, int height) {
		super(width, height);
		setText(script);
		text.is_read_only 		= true;
		text.is_show_caret 		= false;
	}
	
	public RichButton(String script) {
		super() ;
		setText(script);
		text.is_read_only 		= true;
		text.is_show_caret 		= false;
	}
	
	public RichButton() {
		super();
		text.is_read_only 		= true;
		text.is_show_caret 		= false;
	}
	
	
	public String getText() {
		return text.getText();
	}
	
	public void setText(String script) {
		text.setText(TextBuilder.buildScript(script));
	}
	
	public void setText(AttributedString text) {
		this.text.setText(text);
	}
	
	@Override
	public void render(Graphics2D g) {
		super.render(g);
		render_text(g);
	}
	
	protected void render_text(Graphics2D g)
	{
		g.setColor(Color.WHITE);
//		Drawing.drawString(g, text, text_offset_x, text_offset_y, text_anchor);
//		text.drawText(g, text_offset_x, text_offset_y, 0, 0, getWidth(), getHeight());

		Object oldanti = g.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antialiasing);
		if (isOnDragged()) {
			Drawing.drawString(g, text, text_offset_x, text_offset_y+1, getWidth(), getHeight(), text_anchor);
		}else{
			Drawing.drawString(g, text, text_offset_x, text_offset_y+0, getWidth(), getHeight(), text_anchor);
		}
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, oldanti);
	}
	
	
	
	
}
