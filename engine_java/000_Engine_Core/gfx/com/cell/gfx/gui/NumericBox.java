package com.cell.gfx.gui;

import com.cell.CMath;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;

public class NumericBox extends LabelBar 
{
//	public static UIRect CommonUserRect 		= new UIRect();
//	public static IImage CommonIconKeyAdd;
//	public static IImage CommonIconKeySub;
//	static
//	{
//		CommonUserRect.BackColor   = 0xffc0c0c0;
//		CommonUserRect.BorderColor = 0xffffffff;
//	}
	
	
	public IImage IconKeyAdd;
	public IImage IconKeySub;
	
	long Max = Integer.MAX_VALUE;
	long Min = 0;//Integer.MIN_VALUE;
	long Value ;

	public boolean IsCanEdit = false;
	
	public int KeyValueAdd = Form.KEY_RIGHT;
	public int KeyValueSub = Form.KEY_LEFT;
	
	public int ColorTextKeyHIGHT = 0xffffffff;
	public int ColorTextKeyLOW = 0xff808080;
	
	int ColorTextKeyL = ColorTextKeyLOW;
	int ColorTextKeyR = ColorTextKeyLOW;
	String TextKeyL = "-";
	String TextKeyR = "+";
	
	public NumericBox() {
		this(0, 10, 10);
	}
	
	public NumericBox(long value, int width, int height) 
	{
		super(value + "", width, height);
		Value = value;
		this.CanFocus = true;
		this.UserRect.BackColor = 0xffffffff;
	
		CanEdit = true;
		
		setValue(Value);
		
//		if(CommonUserRect!=null)
//			UserRect = CommonUserRect;
//		if(CommonIconKeyAdd!=null)
//			IconKeyAdd = CommonIconKeyAdd;
//		if(CommonIconKeySub!=null)
//			IconKeySub = CommonIconKeySub;

	}

	public void resize(int width, int height) 
	{
		super.resize(width, height);
	}
	
	public void clicked(Form sender,int key,int x,int y)
	{
		if(Listener!=null){
			Listener.itemCommandAction(Click,this);
		}
		
		if (this.IsEnabled)
		{
			if(CanEdit){

				int ix = sender.toFormPosX(x);
				int iy = sender.toFormPosY(y);

				if(CMath.includeRectPoint(X, Y, X+H, Y+H, ix, iy)){

				}
				else if(CMath.includeRectPoint(X+W-H, Y, X+W, Y+H, ix, iy)){

				}
			}
		}
	}
	
	
	public boolean update(Form form) 
	{
		ColorTextKeyL = ColorTextKeyLOW;
		ColorTextKeyR = ColorTextKeyLOW;
		
		if (this.IsEnabled)
		{		

			if(Form.isEventPointerHold())
			{
				int ix = form.toFormPosX(Form.getPointerX());
				int iy = form.toFormPosY(Form.getPointerY());
				
				if(CMath.includeRectPoint(X, Y, X+H, Y+H, ix, iy)){
					Value -= 1;
					ColorTextKeyL = ColorTextKeyHIGHT;
				}
				else if(CMath.includeRectPoint(X+W-H, Y, X+W, Y+H, ix, iy)){
					Value += 1;
					ColorTextKeyR = ColorTextKeyHIGHT;
				}
			}
			else if(Form.isEventKeyHold(KeyValueAdd)){
				Value += 1;
				ColorTextKeyR = ColorTextKeyHIGHT;
			}
			else if(Form.isEventKeyHold(KeyValueSub)){
				Value -= 1;
				ColorTextKeyL = ColorTextKeyHIGHT;
			}
			
		}
		
		if (Value > Max) Value = Max;
		if (Value < Min) Value = Min;
		
		super.update(form);
		
		setValue(Value);
		
		return true;
	}

	public void setValue(long value)
	{
		this.Value = value;
		if (Value > Max) Value = Max;
		if (Value < Min) Value = Min;
		
		this.Lable = "" + this.Value;
	}
	
	public void setRange(long min, long max)
	{
		this.Min = Math.min(min, max);
		this.Max = Math.max(min, max);
//		
//		if (this.Max < this.Min)
//			this.Max = this.Min;
		
		if (this.Value > this.Max)
			this.Value = this.Max;
		
		if (this.Value < this.Min)
			this.Value = this.Min;
	}
	
	public void setRangeMax(long max)
	{
		this.Max = max;
		
		if (this.Max < this.Min)
			this.Max = this.Min;
		
		if (this.Value > this.Max)
			this.Value = this.Max;		
	}
	
	public void setRangeMin(long min)
	{
		this.Min = min;
		
		if (this.Min > this.Max)
			this.Min = this.Max;
		
		if (this.Value < this.Min)
			this.Value = this.Min;
	}
	
	public long getValue() 	{ return this.Value; }
	
	public int getIntValue() 	{ return (int)this.Value; }
	
	public long getMin()		{ return this.Min; }
	
	public long getMax()		{ return this.Max; }
	
	public void render(IGraphics g, int x, int y) 
	{
		
		int temp = super.TextColor;
		super.TextColor = 0;
		super.render(g, x, y);
		super.TextColor = temp;
		
		int tw = g.getStringWidth(Lable);
		int th = g.getStringHeight();
		
		g.setColor(TextColor);
		g.drawString(Lable, x + UserRect.BorderSize + (W-tw)/2 , y + (H-th)/2);
		
		if (IconKeyAdd != null)
		{
			g.drawImage(IconKeyAdd, x, y, 0);
		}
		else
		{
			g.setColor(ColorTextKeyL);
			g.drawString(TextKeyL, x + UserRect.BorderSize, y + (H-th)/2);
		}
		
		if (IconKeySub != null)
		{
			g.drawImage(IconKeySub, x + W - IconKeySub.getWidth(), y, IGraphics.TRANS_MIRROR);
		}
		else
		{
			g.setColor(ColorTextKeyR);
			g.drawString(TextKeyR, x + W - UserRect.BorderSize - g.getStringWidth(TextKeyR), y + (H-th)/2);
		}
		
	}

	public void renderIcon(IGraphics g, int x, int y) {
	}

	
	
}



