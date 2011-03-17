package com.cell.gfx.gui;


import java.util.ArrayList;
import java.util.Collection;

import com.cell.gfx.AScreen;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IGraphics.StringAttribute;
import com.cell.gfx.IGraphics.StringLayer;
import com.cell.gfx.gui.RichTextBox.Scripts;

public class LabelRollBar extends Item 
{
	public String	FontName = null;
	public int		FontSize = 12;
	
	StringLayer	Text;
	public int RollTime  		= 50;
	public int RollSpeed		= -1;
	public int RollPos			= 0;
	public int RollTotalCount	= 0;
	
	public LabelRollBar()
	{
		this("", 10, 10);
	}
	
	public LabelRollBar(String text, int width, int height)
	{
		CanFocus	= false;
		W			= width;
		H			= height;
		RollPos		= 0;
		RollTime	= 50;
		setText(text);
	}
	
	@Override
	public void setText(String str) 
	{
		this.setText(str, new ArrayList<StringAttribute>());
	}
	
	public void setText(Scripts str) 
	{
		this.setText(str.Text, str.Attributes);
	}
	
	public void setText(String text, Collection<IGraphics.StringAttribute> attributes)
	{
		Lable = text;
		
		String old_name	= AScreen.CurGraphics.getFontName();
		int    old_size = AScreen.CurGraphics.getFontSize();
		
		if (FontName != null) {
			AScreen.CurGraphics.setFont(FontName, FontSize);
		}
		
		Text = AScreen.CurGraphics.createStringLayer(
				text, 
				attributes.toArray(new IGraphics.StringAttribute[attributes.size()]));
		
		AScreen.CurGraphics.setFont(old_name, old_size);
		
		RollPos = W;
		RollTime = 50;
	}
		
	public void resetRoll(int rollpos, int rolltime)
	{
		RollPos = rollpos;
		RollTime = rolltime;
	}
	
	public void resize(int width,int height){
		if (W != width || H != height){
			W = width;
			H = height;
		}
	}
	
	public void clicked(Form sender,int key,int x,int y)
	{
		if (this.IsEnabled) {
			if (this.Listener != null) {
				Listener.itemCommandAction(Click,this);
			}
		}
	}
	
	public boolean update(Form form) {
		return true;
	}
	
	public void renderText(IGraphics g, int x, int y)
	{
		if (Text == null) return;
		
		int vw = W-UserRect.BorderSize*2;
		int tw = Text.getWidth();
		int th = Text.getHeight();
		int dy = y + (H-th)/2 ;
		
		if (--RollTime < 0) {
			int old = RollPos;
			if ((old + RollSpeed) < -tw) {
				RollTotalCount ++;
				RollPos = vw;
			}
			RollPos += RollSpeed;
		}
		
		int sx = x + UserRect.BorderSize;
		
		int cx = g.getClipX();
		int cw = g.getClipWidth();
		g.setClip(sx, g.getClipY(), vw, g.getClipHeight());
		{
			int dx = sx + RollPos;
			g.setColor(TextColor);
			Text.render(g, dx, dy);
//			g.drawString(Lable, dx, dy);
		}
		g.setClip(cx, g.getClipY(), cw, g.getClipHeight());
	}
	
	public void render(IGraphics g, int x, int y)
	{
		g.pushClip();
		g.clipRect(x , y , W + 1, H + 1);
		
		// back
		UserRect.renderBody(g, x, y, W, H);
		//border
		UserRect.renderBorder(g, x, y, W, H);
		// text
		renderText(g, x, y);
		
		g.popClip();
	}

//	----------------------------------------------------------------------------------------------------------------
	
	
	
}



