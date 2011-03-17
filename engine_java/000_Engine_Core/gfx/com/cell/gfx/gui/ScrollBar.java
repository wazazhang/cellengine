package com.cell.gfx.gui;


import java.awt.Rectangle;

import com.cell.CMath;
import com.cell.CObject;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;

public class ScrollBar extends CObject
{

	public boolean IsVisible = true;
	
	Rectangle Head;
	Rectangle Tail;
	Rectangle Back;
	
	Rectangle Strip;
	
	
	public int X;
	public int Y;
	public int W;
	public int H;
	
	private boolean IsH;
	
	private int Max = 0;
	private int Min = 1;
	
	public int Value = 0;
	public int ValueLength = 1;
	

	// Background Image
	public IImage	BackImage		= null;
	public IImage	BackImageH		= null;
	public IImage	BackImageT		= null;
	
	// Bar Image
	public IImage	ForeImage		= null;
	public IImage	ForeImageH		= null;
	public IImage	ForeImageT		= null;
	
	// Rect Size
	//public int		BarSize		= 8;
	//public int		HeadSize	= 8;
	
	public int		BorderColor		= 0xff000000;
	public int	 	BackColor		= 0xff404040;
	public int		ForeColor 		= 0xffa0a0a0;
	
	
	// private 
	private boolean DragStart = false;
	private int DragOffsetX;
	private int DragOffsetY;
	
	/*
	private int ScopeX;
	private int ScopeY;
	private int ScopeW;
	private int ScopeH;
	
	private int StripX;
	private int StripY;
	private int StripW;
	private int StripH;
	*/
	
	//
	public static ScrollBar createScrollBarH(int barSize)
	{
		return new ScrollBar(barSize, true);
	}
	
	public static ScrollBar createScrollBarV(int barSize)
	{
		return new ScrollBar(barSize, false);
	}
	
	//
	public ScrollBar(ScrollBar other)
	{
		// Background Image
		BackImage 		= other.BackImage;
		BackImageH		= other.BackImageH;
		BackImageT		= other.BackImageT;
		
		// Bar Image
		ForeImage		= other.ForeImage;
		ForeImageH		= other.ForeImageH;
		ForeImageT		= other.ForeImageT;
		
		// Rect Size
		//BarSize			= other.BarSize;
		//HeadSize		= other.HeadSize;
		
		BorderColor		= other.BorderColor;
		BackColor		= other.BackColor;
		ForeColor 		= other.ForeColor;
		
		IsH				= other.IsH;
		
		setBarSize(other.getBarSize(), other.getHeadSize());
	}

	
	public ScrollBar(int barSize, boolean isHorizontal){

		IsH = isHorizontal;
		
		//BarSize 	= barSize;
		//HeadSize	= BarSize;
		

		setBarSize(barSize, barSize);
		
	}

	//
	public void set(ScrollBar other)
	{
		// Background Image
		BackImage 		= other.BackImage;
		BackImageH		= other.BackImageH;
		BackImageT		= other.BackImageT;
		
		// Bar Image
		ForeImage		= other.ForeImage;
		ForeImageH		= other.ForeImageH;
		ForeImageT		= other.ForeImageT;
		
		// Rect Size
		Head			= other.Head;
		Tail			= other.Tail;
		
		BorderColor		= other.BorderColor;
		BackColor		= other.BackColor;
		ForeColor 		= other.ForeColor;
		
		IsH				= other.IsH;
		
		setBarSize(other.getBarSize(), other.getHeadSize());
	}
	
	public void setBarSize(int barsize, int headsize){
		if (IsH){
			Head	= new Rectangle(0, 0, headsize, barsize);
			Tail	= new Rectangle(0, 0, headsize, barsize);
			Back	= new Rectangle(0, 0, 1, barsize);
			Strip	= new Rectangle(0, 0, 1, barsize);
		}else{
			Head	= new Rectangle(0, 0, barsize, headsize);
			Tail	= new Rectangle(0, 0, barsize, headsize);
			Back	= new Rectangle(0, 0, barsize, 1);
			Strip	= new Rectangle(0, 0, barsize, 1);
		}
	}
	
	public int getBarSize(){
		if (IsH){
			return Head.height;
		}else{
			return Head.width;
		}
	}
	public int getHeadSize(){
		if (IsH){
			return Head.width;
		}else{
			return Head.height;
		}
	}
	
	public void setScope(int max,int min){
		Max = Math.max(max, min);
		Min = Math.min(min, max);
	}
	
	public int getMax()
	{
		return Max;
	}
	
	public int getMin()
	{
		return Min;
	}
	
	public void setValue(int value, int length){
		Value = value;
		ValueLength = length;

	}
	
	public int getValue(){
		return Value;
	}
	
	public int getValueLength(){
		return ValueLength;
	}
	
	
//	public void resize(int width, int height) {
//		W = width;
//		H = height;
//		
//		if(IsH){
//			BarSize = H;
//		}else{
//			BarSize = W;
//		}
//	}

	
	/**
	 * @return is catch pointer
	 */
	public boolean update() 
	{
		if(!IsVisible)return false;
		
		boolean ret = false;
		
//		 operate pointer
		int px = Form.getPointerX();
		int py = Form.getPointerY();
		
		if(Form.isPointerDown())
		{
			DragStart = false;
			
			if (CMath.includeRectPoint2(X, Y, W, H, px, py)) {
				if (Strip.contains(px, py)) {
					DragStart = true;
					DragOffsetX = px - Strip.x;
					DragOffsetY = py - Strip.y;
				} else if (Back.contains(px, py)) {
					int d = 0;
					if (IsH) {
						d = px > Strip.x ? 1 : -1;
					} else {
						d = py > Strip.y ? 1 : -1;
					}
					Value += ValueLength * d;
				}
				ret = true;
			}
		}
		else if(Form.isPointerHold())
		{
			if(DragStart)
			{
				int dx = px - DragOffsetX;
				int dy = py - DragOffsetY;
				
				int dv = 0;
				int dw = 0;
				if(IsH){
					dv = dx - Back.x;
					dw = Back.width;
				}else{
					dv = dy - Back.y;
					dw = Back.height;
				}
				
				Value = Math.abs(Max-Min)*dv/dw;
				
				ret = true;
			}
			else if (CMath.includeRectPoint2(X, Y, W, H, px, py))
			{
				if (Form.isEventPointerHold()) {
					if (Strip.contains(px, py)) {
					} else if (Back.contains(px, py)) {
						int d = 0;
						if (IsH) {
							d = px > Strip.x ? 1 : -1;
						} else {
							d = py > Strip.y ? 1 : -1;
						}
						Value += ValueLength * d;
					}
				}
				ret = true;
			}
			
		}
		else if(Form.isPointerUp())
		{
			DragStart = false;
		}
		
//		rage max min 
		{
			if(ValueLength>Max-Min){
				ValueLength = Max-Min;
			}
			if(Value+ValueLength>Max){
				Value=Max-ValueLength;
			}
			if(Value<Min){
				Value=Min;
			}
		}
		
		return ret;
	}

	public void render(IGraphics g, int x, int y, int w, int h)
	{
		if(!IsVisible)return ;
		
		g.pushClip();
    	
		X = x;
		Y = y;
		W = w;
		H = h;
		
		// background
		//if(false)
		{
			if(IsH){
				Head.x = x;
				Tail.x = x + w - Tail.width;
				Back.x = x + Head.width;
				Back.width = w - Head.width - Tail.width;
				Back.y = Head.y = Tail.y = y;
			}else{
				Head.y = y;
				Tail.y = y + h - Tail.height;
				Back.y = y + Head.height;
				Back.height = h - Head.height - Tail.height;
				Back.x = Head.x = Tail.x = x;
			}

			// draw bg
			if (BackImage != null) {
				g.drawRoundImage(BackImage, Back.x, Back.y, Back.width, Back.height, 0);
			} else {
				g.setColor(BackColor);
				g.fillRect(x, y, w, h);
				g.setColor(BorderColor);
				g.drawRect(x, y, w, h);
			}
			
			// draw bg head and tail
			if(BackImageH!=null && BackImageT!=null){
				g.drawRoundImage(BackImageH, Head.x, Head.y, Head.width, Head.height, 0);
				g.drawRoundImage(BackImageT, Tail.x, Tail.y, Tail.width, Tail.height, 0);
			} else {
				g.setColor(ForeColor);
				g.fillRect(Head.x, Head.y, Head.width, Head.height);//
				g.fillRect(Tail.x, Tail.y, Tail.width, Tail.height);//
			}
			
		}
		
		// strip bar
		//if(false)
		{
			int vlen = Max - Min;
			
			if (IsH) {
				Strip.x = Back.x + ((Value - Min) * Back.width / vlen) + 1;
				Strip.y = Back.y;
				Strip.width = Back.width * ValueLength / vlen - 1;
			} else {
				Strip.x = Back.x;
				Strip.y = Back.y + ((Value - Min) * Back.height / vlen) + 1;
				Strip.height = Back.height * ValueLength / vlen - 1;
			}

			if (ForeImage!=null && ForeImageH!=null && ForeImageT!=null) {
				if(IsH){
					g.drawRoundImage(ForeImage, 
							Strip.x + ForeImageH.getWidth(), 
							Strip.y, 
							Strip.width - ForeImageH.getWidth() - ForeImageT.getWidth(),
							Strip.height, 0);
					g.drawImage(ForeImageH, Strip.x, Strip.y, 0);
					g.drawImage(ForeImageT, Strip.x + Strip.width - ForeImageT.getWidth(), Strip.y, 0);
				}else{
					g.drawRoundImage(ForeImage, 
							Strip.x, 
							Strip.y + ForeImageH.getHeight(), 
							Strip.width,
							Strip.height - ForeImageH.getHeight() - ForeImageT.getHeight(), 0);
					g.drawImage(ForeImageH, Strip.x, Strip.y, 0);
					g.drawImage(ForeImageT, Strip.x, Strip.y + Strip.height - ForeImageT.getHeight(), 0);
				}
			} else {
				g.setColor(ForeColor);
				g.fillRect(Strip.x, Strip.y, Strip.width, Strip.height);
			}
		}
		
		g.popClip();
	
		
	}


}
