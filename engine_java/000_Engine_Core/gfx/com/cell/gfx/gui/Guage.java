package com.cell.gfx.gui;

import com.cell.CMath;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;

public class Guage extends LabelBar
{
//	public static UIRect CommonUserRect 		= new UIRect();
//	public static IImage CommonStripBar			= null;
//	public static IImage CommonStripImage 		= null;
//	static
//	{
//		CommonUserRect.BackColor   = 0xffc0c0c0;
//		CommonUserRect.BorderColor = 0xffffffff;
//	}
	
	public IImage StripBar	= null;
	public IImage StripImage = null;
	
	public boolean IsShowPercent = true;
	public boolean IsCanDrag = false;
	public boolean IsShowData = true;
	
	public int StripColor = 0xff800000;
	public int StripBackColor = 0;
	public int StripSize = 2;
	
	private long Value 	= 0;
	private long Max   	= 100;
	private long Min	= 0;
	
	//
	private boolean IsDragDown = false;
	
	//
	public int KeyValueAdd = Form.KEY_RIGHT;
	public int KeyValueSub = Form.KEY_LEFT;
	public int FreezeKeyTime = 0;
	public int FreezeKeyDelay = 10;
	public int FreezeKeySpeed = 1;
	
	//
	final static public byte LAYOUT_NORMAL 	= 0;
	final static public byte LAYOUT_TOP 	= 1;
	final static public byte LAYOUT_BOTTOM 	= 2;
	final static public byte LAYOUT_LEFT 	= 3;
	final static public byte LAYOUT_RIGHT 	= 4;
	public byte Layout = 0;
	
	public Guage() {
		this(10, 10);
	}
	
	public Guage(int width, int height) 
	{
		super("", width, height);

		CanFocus = true;
		
		IsTextCenter = true;
		
//		if(CommonUserRect!=null)
//			UserRect = CommonUserRect;
//		if(CommonStripBar != null)
//			StripBar = CommonStripBar;
//		if(CommonStripImage != null)
//			StripImage = CommonStripImage;
		
		setValue(0);
	}

	public void pressed(Form sender) {
		this.IsDragDown = false;
	}
	public void released(Form sender) {
		this.IsDragDown = false;
	}

	protected void getFocuse(Form sender) {
		super.getFocuse(sender);
		this.IsDragDown = false;
	}

	protected void lossFocuse(Form sender) {
		super.lossFocuse(sender);
		this.IsDragDown = false;
	}

	public void setRange(long min , long max){
		Min = Math.min(min, max);
		Max = Math.max(min, max);
		setValue(Value);
	}
	
	
	public void setValue(long value){
		Value = value;
		Value = (Value>Max?Max:Value);
		Value = (Value<Min?Min:Value);
		
		if(IsShowPercent){
			if(Max==0 && Max==Min){
				setText("");
			}else{
				if(Value==Min){
					setText("0%");
				}else{
					setText((100 * (Min + Value + 1)/getDuration())+"%");
				}
			}
		}else{
			setText(""+Value);
		}
		
	}
	
	
	public void increase(int i){
		setValue(getValue()+i);
	}
	
	public long getValue(){
		return Value;
	}
	
	public int getIntValue(){
		return (int)Value;
	}
	
	public long getMax(){
		return Max;
	}
	public long getMin(){
		return Min;
	}
	public long getDuration(){
		return Math.abs(Max-Min)+1;
	}
	
	public boolean isFull(){
		return getValue() >= getMax();
	}
	
	public boolean update(Form form) {
		
		if(IsCanDrag)
		{
			int x = form.toScreenPosX(X);
			int y = form.toScreenPosY(Y);
			int sx = x+UserRect.BorderSize+1+(Icon!=null?H:0);
			int sy = y+UserRect.BorderSize+1;
			int sw = (int)(W - UserRect.BorderSize*2-2-(Icon!=null?H:0));
			int sh = (int)(H - UserRect.BorderSize*2-2);
				
			// mouse operation
			if(Form.isPointerDown()){
				IsDragDown = false;
				if(CMath.includeRectPoint2(
						sx, sy, 
						sw, sh, 
						Form.getPointerX(), 
						Form.getPointerY()))
				{
					IsDragDown = true;
					
					int dx = Form.getPointerX();
					dx = Math.max(dx, sx);
					dx = Math.min(dx, sx+sw+1);
					
					setValue(getMin() + getDuration() * (Math.abs(dx-sx)) / sw); 
				}
			}
			else if(Form.isPointerHold()){
				if(IsDragDown)
				{
					int dx = Form.getPointerX();
					dx = Math.max(dx, sx);
					dx = Math.min(dx, sx+sw+1);
					
					setValue(getMin() + getDuration() * (Math.abs(dx-sx)) / sw); 
				}
			}
			else if(Form.isPointerUp()){
				IsDragDown = false;
			}
			
			// key operation
			if (Form.isKeyDown(KeyValueAdd))
			{
				Value += 1;
				FreezeKeyTime = 0;
				setValue(Value); 
			}
			else if (Form.isKeyDown(KeyValueSub))
			{
				Value -= 1;
				FreezeKeyTime = 0;
				setValue(Value); 
			}
			else if (FreezeKeyTime>FreezeKeyDelay && FreezeKeyTime%FreezeKeySpeed==0)
			{
				if (Form.isKeyHold(KeyValueAdd))
				{
					Value += FreezeKeyTime-FreezeKeyDelay;
				}
				if (Form.isKeyHold(KeyValueSub))
				{
					Value -= FreezeKeyTime-FreezeKeyDelay;
				}
				setValue(Value); 
			}
			
			
			
		}
		
		super.update(form);
		return true;
	}
	
	public void renderStrip(IGraphics g, int x, int y){
		if(Max>0){
			int sx = x+UserRect.BorderSize+1+(Icon!=null?H:0);
			int sy = y+UserRect.BorderSize+1;
			int sw = (int)(W - UserRect.BorderSize*2-2-(Icon!=null?H:0));
			int sh = (int)(H - UserRect.BorderSize*2-2);
			
			int w = getDuration()==1?sw:(int)(sw * (Math.abs(Value-Min))/(getDuration()-1));
			int h = getDuration()==1?sh:(int)(sh * (Math.abs(Value-Min))/(getDuration()-1));
			
			if(Layout == LAYOUT_NORMAL){
				g.setColor(StripBackColor);
				g.fillRect(sx, sy, sw, sh);
				
				if(StripImage==null){
					g.setColor(StripColor);
					g.fillRect(sx, sy, w, sh);
				}else{
					g.drawRoundImage(StripImage, sx, sy, w, sh, 0);
				}
				
				if(IsCanDrag && StripBar!=null){
					g.drawImage(StripBar, 
							sx+w-StripBar.getWidth()/2, 
							y+(H-StripBar.getHeight())/2,
							0);
				}
			}else{
				switch(Layout){
				case LAYOUT_BOTTOM:
					sy = sy + sh - StripSize;
				case LAYOUT_TOP:
					sh = h = StripSize;
					break;
				case LAYOUT_RIGHT:
					sx = sx + sw - StripSize;
				case LAYOUT_LEFT:
					sw = w = StripSize;
					break;
				}
				
				g.setColor(StripBackColor);
				g.fillRect(sx, sy, sw, sh);
				g.setColor(StripColor);
				g.fillRect(sx, sy, w, h);
			}
			
		}
	}

	public void render(IGraphics g, int x, int y) 
	{
		Value = (Value>Max?Max:Value);
		Value = (Value<Min?Min:Value);
		
		int tempColor = TextColor;
		
		TextColor=0;
		super.render(g, x, y);
		TextColor = tempColor;
		
		renderStrip(g, x, y);
		if (IsShowData){
			renderText(g, x, y);
		}
	}
	
	
	
	
	
	
}
