package com.g2d.display.ui;

import java.text.AttributedString;

import com.g2d.Color;
import com.g2d.Engine;
import com.g2d.Graphics2D;
import com.g2d.text.MultiTextLayout;
import com.g2d.text.TextBuilder;
import com.g2d.util.Drawing;

/**
 * 用于显示高级文字的按钮
 * @author WAZA
 */
public class RichButton extends BaseButton 
{	
	transient protected MultiTextLayout	text		= Engine.getEngine().createMultiTextLayout();
	
	public int 					text_anchor = Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_VCENTER ;
	public int 					text_offset_x;
	public int 					text_offset_y;
		 
	public RichButton(String script, int width, int height) {
		super(width, height);
		setText(script);
	}
	
	public RichButton(String script) {
		super() ;
		setText(script);
	}
	
	public RichButton() {
		super();
	}
	
	
	public String getText() {
		return text.getText();
	}
	
	public void setText(String script) {
		this.text		.setSingleLine(true);
		this.text		.setText(TextBuilder.buildScript(script));
		this.text		.setReadOnly(true);
		this.text		.setShowCaret(false);
		this.text		.setShowSelect(false);
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

		
		if (isOnDragged()) {
			Drawing.drawString(g, text, text_offset_x, text_offset_y+1, getWidth(), getHeight(), text_anchor);
		}else{
			Drawing.drawString(g, text, text_offset_x, text_offset_y+0, getWidth(), getHeight(), text_anchor);
		}
	}
	
	
	
	
}
