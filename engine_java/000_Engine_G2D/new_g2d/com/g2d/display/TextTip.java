package com.g2d.display;

import java.text.AttributedString;

import com.g2d.BufferedImage;
import com.g2d.Color;
import com.g2d.Engine;
import com.g2d.Graphics2D;
import com.g2d.Tools;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.geom.Dimension;
import com.g2d.text.MultiTextLayout;

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
	
	transient MultiTextLayout	text;
	transient BufferedImage		buffer;
	transient Graphics2D		buffer_g;

	public TextTip()
	{
		this.textColor 	= new Color(0xffffffff);
		this.backColor	= new Color(0x60000000);	
		this.shadow_x	= 0;
		this.shadow_y	= 0;
		this.text		= Engine.getEngine().createMultiTextLayout();
		this.text		.setReadOnly(true);
		this.text		.setShowCaret(false);
		this.text		.setShowSelect(false);
		
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
			g.fillRect(local_bounds);
		}
		{
			g.setColor(textColor);
			text.drawText(g, bw, bw, 0, 0, text.getWidth(), text.getHeight(), shadow_x, shadow_y, 1f, 0);
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------

	
}
