package com.cell.gfx.gui;


import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;

public class LabelBar extends Item 
{
//	public static UIRect CommonUserRect 		= new UIRect();
//	static
//	{
//		CommonUserRect.BackColor   = 0xffc0c0c0;
//		CommonUserRect.BorderColor = 0xffffffff;
//	}
	
	
	public boolean CanEdit = false;
	
	public boolean IsIconCenter = false;
	public boolean IsTextCenter = true;
	private int RollSpeed = -1;
	public int RollTime  = W;
	public int RollPos   = 0;

	public IImage Icon;
	
	public boolean IsTextShadow = false;
	public byte TextShadowPosX = 0;
	public byte TextShadowPosY = 2;
	public int TextColorShadow = 0xff000000;
	
	public LabelBar() {
		this("", 10, 10);
	}
	
	public LabelBar(String text, int width, int height) {
	
		CanFocus = false;
		W = width;
		H = height;
		
		RollTime = W/2;
		RollPos = 0;
		
		setText(text);
//		if(CommonUserRect!=null)
//			UserRect = CommonUserRect;
	}

	public LabelBar(String text, IImage icon, int width, int height) {
		
		CanFocus = false;
		W = width;
		H = height;
		
		RollTime = W/2;
		RollPos = 0;
		
		setText(text);

		Icon = icon;
	}
	
	public void resize(int width,int height){
		if (W != width || H != height){
			W = width;
			H = height;
//			RollTime = W/2;
//			RollPos = 0;
		}
	}
	
	public void clicked(Form sender,int key,int x,int y)
	{
		if (this.IsEnabled)
		{
			if (this.Listener != null)
			{
				Listener.itemCommandAction(Click,this);
			}
		}
	}
	
//	public void setBorderImage(IImage[] images){
//		UserRect.setImage(images);
//	}
	
	public boolean update(Form form) {

		return true;
	}
	
	public void renderText(IGraphics g, int x, int y){
		if (Lable == null) return;
		int vw = W-UserRect.BorderSize*2-(Icon!=null?H:0);
		int tw = g.getStringWidth(Lable);
		int th = g.getStringHeight();
		int dy = y + (H-th)/2 ;
		
		if(TextColor==0 || Lable==null)return ;
		
		
		if(--RollTime<0){
			RollPos += RollSpeed;
		}else{
			RollPos = -vw;
		}
		
		if(vw<tw){
			int size = (tw+vw) ;
			int sx = x + UserRect.BorderSize + (Icon!=null?H:0) ;
			if(size>0){
				int cx = g.getClipX();
				int cw = g.getClipWidth();
				g.setClip(sx, g.getClipY(), vw, g.getClipHeight());
				int dx = sx + vw + RollPos%size;
				
				if(IsTextShadow){
					g.setColor(TextColorShadow);
					g.drawString(Lable, dx+TextShadowPosX, dy+TextShadowPosY );
				}
				g.setColor(TextColor);
				g.drawString(Lable, dx, dy );
				g.setClip(cx, g.getClipY(), cw, g.getClipHeight());
			}
		}else{
			if(IsTextCenter){
				int dx = x + (Icon!=null?H:0) + (W-(Icon!=null?H:0)-tw)/2;
				if(IsTextShadow){
					g.setColor(TextColorShadow);
					g.drawString(Lable, dx+TextShadowPosX, dy+TextShadowPosY );
				}
				g.setColor(TextColor);
				g.drawString(Lable, dx, dy);
			}else{
				int dx = x + (Icon!=null?H:0) + UserRect.BorderSize;
				if(IsTextShadow){
					g.setColor(TextColorShadow);
					g.drawString(Lable, dx+TextShadowPosX, dy+TextShadowPosY );
				}
				g.setColor(TextColor);
				g.drawString(Lable, dx, dy);
			}
		}
		
	}
	
	public void renderIcon(IGraphics g, int x, int y){
		if(Icon!=null){
			if (IsIconCenter){
				g.drawImage(Icon, x +(W-Icon.getWidth())/2, y+(H-Icon.getHeight())/2, 0);
			}
			else{
				g.drawImage(Icon, x +(H-Icon.getWidth())/2, y+(H-Icon.getHeight())/2, 0);
			}
		}
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
		// icon
		renderIcon(g, x, y);
		
		g.popClip();
	}

//	----------------------------------------------------------------------------------------------------------------
	
	
	
}



