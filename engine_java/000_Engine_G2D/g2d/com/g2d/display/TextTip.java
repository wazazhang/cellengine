package com.g2d.display;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import com.g2d.Tools;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.text.MultiTextLayout;

public class TextTip extends Tip 
{
	public static int 			DefaultTipSize = 250;
	public static UILayout		DefaultLayout;
	
//	---------------------------------------------------------------------------------------------------------------
	
	public UILayout				layout;
	public int 					text_width;

	public Color 				textColor;
	public Color				backColor;
	public int					shadow_x;
	public int					shadow_y;
	public boolean				enable_antialiasing	 = false;
	
	transient MultiTextLayout	text;
	transient BufferedImage		buffer;
	transient Graphics2D		buffer_g;

	public TextTip()
	{
		this.textColor 	= new Color(0xffffffff, true);
		this.backColor	= new Color(0x60000000, true);	
		this.shadow_x	= 0;
		this.shadow_y	= 0;
		this.text		= new MultiTextLayout();
		this.text.is_read_only = true;
		this.text.is_show_caret = false;
		
		this.layout		= DefaultLayout;
		this.text_width	= DefaultTipSize;
		
		this.buffer		= Tools.createImage(1, 1);
		this.buffer_g	= buffer.createGraphics();
	}
	
	public TextTip(String text)
	{
		this();
		setText(text);
	}
	
	final public void setText(String text) {
		this.text.setText(text);
	}
	
	final public void setText(AttributedString atext) {
		this.text.setText(atext);
	}

	public void update() 
	{
		int bw = 4;
		if (layout!=null){
			bw = layout.BorderSize;
		}
		buffer_g.setFont(getStage().getRoot().getDefaultFont());
		text.setWidth(text_width - (bw<<1));
		if (enable_antialiasing) {
			buffer_g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		} else {
			buffer_g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		}
		Dimension draw_size		= text.drawText(buffer_g, 0, 0);
		
		draw_size.height		= text.getHeight();
		this.setSize(draw_size.width+(bw<<1), draw_size.height+(bw<<1));
	}
	
	public void render(Graphics2D g) 
	{
		int bw = 4;
		if (layout!=null){
			bw = layout.BorderSize;
			layout.render(g, 0, 0, getWidth(), getHeight());
		}else{
			g.setColor(backColor);
			g.fill(local_bounds);
		}
		{
			if (shadow_x!=0 || shadow_y!=0) {
				Composite composite = g.getComposite();
				g.setComposite(AlphaComposite.DstIn);
				g.setColor(Color.BLACK);
				text.drawText(g, bw+shadow_x, bw+shadow_y);
				g.setComposite(composite);
			}
			g.setColor(textColor);
			text.drawText(g, bw, bw);
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------

	
}
