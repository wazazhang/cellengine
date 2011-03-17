package com.cell.gfx.gui;

import com.cell.CMath;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;

public class ImageSelectBox extends LabelBar 
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
	
	protected IImage[] Items;
	private int CurIndex ;
	
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
	
	public ImageSelectBox() {
		this(null, 10, 10);
	}
	
	public ImageSelectBox(IImage[] items, int width, int height) {
		super("", width, height);
		Items = items;
		this.CanFocus = true;

//		if(CommonUserRect!=null)
//			UserRect = CommonUserRect;
//		if(CommonIconKeyNext!=null)
//			IconKeyNext = CommonIconKeyNext;
//		if(CommonIconKeyPrew!=null)
//			IconKeyPrew = CommonIconKeyPrew;
		
		
		setCurrent(0);
	}

	

	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public void clicked(Form sender,int key,int x,int y){
		if(Listener!=null){
			Listener.itemCommandAction(Click,this);
		}
	}
	
	
	public void setItems(IImage[] items){
		Items = items;
	}
	
	public void setItem(IImage item,int index){
		Items[index] = item;
	}
	
	public IImage getItem(int index){
		return Items[index];
	}
	
	public int getItemCount(){
		return Items.length;
	}
	
	
	public boolean update(Form form) {
		
		this.Icon = Items[CurIndex];
		
		ColorTextKeyL = ColorTextKeyLOW;
		ColorTextKeyR = ColorTextKeyLOW;
		
//		if(Form.IsUsePointer)
		{
			int ix = form.toFormPosX(Form.getPointerX());
			int iy = form.toFormPosY(Form.getPointerY());
			
			if(Form.isPointerDown()){
				if(CMath.includeRectPoint(X, Y, X+H, Y+H, ix, iy)){
					CurIndex = CMath.cycNum(CurIndex,-1, Items.length);
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
			this.Icon = Items[CurIndex];
		}
	}
	
	public int getCurrentIndex()
	{
		return this.CurIndex;
	}
	
	
	public void render(IGraphics g, int x, int y) {
		
		int temp = super.TextColor;
		super.TextColor = 0;
		super.render(g, x, y);

		int th = g.getStringHeight();
		
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
		if(Icon!=null){
			g.drawImage(Icon, x +(W-Icon.getWidth())/2, y+(H-Icon.getHeight())/2, 0);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
