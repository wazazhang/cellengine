package com.cell.gfx.gui;

import com.cell.CMath;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;

public class SelectBox extends LabelBar 
{
//	public static UIRect CommonUserRect 		= new UIRect();
//	public static IImage CommonIconKeyNext;
//	public static IImage CommonIconKeyPrew;
//	static
//	{
//		CommonUserRect.BackColor   = 0xffc0c0c0;
//		CommonUserRect.BorderColor = 0xffffffff;
//	}
	
	public IImage IconKeyNext;
	public IImage IconKeyPrew;
	
	
	
	protected String[] Items;
	public int CurIndex ;
	
	public int KeySelectNext = Form.KEY_RIGHT;
	public int KeySelectPrew = Form.KEY_LEFT;
	
	public int FreezeKeyTime = 0;
	public int FreezeKeyDelay = 10;
	public int FreezeKeySpeed = 10;
	
	
	public int ColorTextKeyHIGHT = 0xffffffff;
	public int ColorTextKeyLOW = 0xff808080;
	
	int ColorTextKeyL = ColorTextKeyLOW;
	int ColorTextKeyR = ColorTextKeyLOW;
	String TextKeyL = "<";
	String TextKeyR = ">";
	
	public SelectBox(String[] items, int width, int height) {
		super("", width, height);
		setItems(items);
		this.CanFocus = true;
		this.UserRect.BackColor = 0xffffffff;
		
		setCurrent(0);
		
//		if(CommonUserRect!=null)
//			UserRect = CommonUserRect;
//		if(CommonIconKeyNext!=null)
//			IconKeyNext = CommonIconKeyNext;
//		if(CommonIconKeyPrew!=null)
//			IconKeyPrew = CommonIconKeyPrew;
	}

	

	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public void setItems(String[] items){
		Items = items;
		for (int i=0; i<Items.length; i++){
			Items[i] = processReplaceTable(Items[i]);
			Items[i] = Control.LocalCovert.covert(Items[i]);
		}
	}
	
	public void setItem(String item,int index){
		Items[index] = item;
		Items[index] = processReplaceTable(Items[index]);
		Items[index] = Control.LocalCovert.covert(Items[index]);
	}
	
	public String getItem(int index){
		return Items[index];
	}
	
	public int getItemCount(){
		return Items.length;
	}
	
	
	public boolean update(Form form) {
		
		this.Lable = Items[CurIndex];
		
		ColorTextKeyL = ColorTextKeyLOW;
		ColorTextKeyR = ColorTextKeyLOW;
		
		
//		if(Form.IsUsePointer)
		{
			int ix = form.toFormPosX(Form.getPointerX());
			int iy = form.toFormPosY(Form.getPointerY());
			
			if(Form.isPointerDown()){
				if (CMath.includeRectPoint(X, Y, X + H, Y + H, ix, iy)) {
					CurIndex = CMath.cycNum(CurIndex, -1, Items.length);
					FreezeKeyTime = 0;
					ColorTextKeyL = ColorTextKeyHIGHT;
				}
				else if(CMath.includeRectPoint(X+W-H, Y, X+W, Y+H, ix, iy)){
					CurIndex = CMath.cycNum(CurIndex, 1, Items.length);
					FreezeKeyTime = 0;
					ColorTextKeyR = ColorTextKeyHIGHT;
				}
			}
			else if (FreezeKeyTime>FreezeKeyDelay && FreezeKeyTime%FreezeKeySpeed==0){
				if(Form.isPointerHold()){
					if(CMath.includeRectPoint(X, Y, X+H, Y+H, ix, iy)){
						CurIndex = CMath.cycNum(CurIndex,-1, Items.length);
						ColorTextKeyL = ColorTextKeyHIGHT;
					}
					else if(CMath.includeRectPoint(X+W-H, Y, X+W, Y+H, ix, iy)){
						CurIndex = CMath.cycNum(CurIndex, 1, Items.length);
						ColorTextKeyR = ColorTextKeyHIGHT;
					}
				}
			}
		}
		
		
		if(Form.isKeyDown(KeySelectNext)){
			CurIndex = CMath.cycNum(CurIndex, 1, Items.length);
			FreezeKeyTime = 0;
			ColorTextKeyR = ColorTextKeyHIGHT;
		}
		else if(Form.isKeyDown(KeySelectPrew)){
			CurIndex = CMath.cycNum(CurIndex,-1, Items.length);
			FreezeKeyTime = 0;
			ColorTextKeyL = ColorTextKeyHIGHT;
		}
		else if(FreezeKeyTime>FreezeKeyDelay && FreezeKeyTime%FreezeKeySpeed==0){
			if(Form.isKeyHold(KeySelectNext)){
				CurIndex = CMath.cycNum(CurIndex, 1, Items.length);
				ColorTextKeyR = ColorTextKeyHIGHT;
			}
			if(Form.isKeyHold(KeySelectPrew)){
				CurIndex = CMath.cycNum(CurIndex,-1, Items.length);
				ColorTextKeyL = ColorTextKeyHIGHT;
			}
		}
		
		super.update(form);
		
		FreezeKeyTime ++;
		
		return true;
	}
	
	public void setCurrent(int currentIndex){
		if(Items!=null&&Items.length>0){
			CurIndex = currentIndex;
			this.Lable = Items[CurIndex];
		}
	}
	public int getCurrentIndex()
	{
		return this.CurIndex;
	}
	
	
	public void render(IGraphics g, int x, int y) {
		
		int temp = super.TextColor;
//		super.TextColor = 0;
		super.render(g, x, y);
//		super.TextColor = temp;
		
		int tw = g.getStringWidth(Lable);
		int th = g.getStringHeight();
		
//		g.setColor(TextColor);
//		g.drawString(Lable, x + UserRect.BorderSize + (W-tw)/2 , y + UserRect.BorderSize);
		
		
		
		if(IconKeyNext!=null){
			g.drawImage(IconKeyNext, x, y, 0);
		}else{
			g.setColor(ColorTextKeyL);
			g.drawString(TextKeyL, x + UserRect.BorderSize, y + (H-th)/2);
		}
		
		if(IconKeyPrew!=null){
			g.drawImage(IconKeyPrew, x + W - IconKeyPrew.getWidth(), y, IImage.TRANS_H);
		}else{
			g.setColor(ColorTextKeyR);
			g.drawString(TextKeyR, x + W - UserRect.BorderSize - g.getStringWidth(TextKeyR), y + (H-th)/2);
		}
		
		
		
		
	}
	
	
	public void renderIcon(IGraphics g, int x, int y) {
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
