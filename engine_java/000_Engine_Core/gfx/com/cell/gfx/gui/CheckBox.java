package com.cell.gfx.gui;

import com.cell.CMath;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;

public class CheckBox extends LabelBar
{
//	public static UIRect CommonUserRect = new UIRect();
//	public static IImage CommonCheckedImage ;
//	public static IImage CommonUncheckedImage ;
//	static
//	{
//		CommonUserRect.BackColor   = 0xffc0c0c0;
//		CommonUserRect.BorderColor = 0xff000000;
//	}
	
	public IImage CheckedImage ;
	public IImage UncheckedImage ;

	public String CheckedText = "(check)";
	public String UncheckedText = "(uncheck)";
	
	public boolean IsClickCheck = true;
	
	protected boolean IsChecked = false;
	
	public boolean CanClickCheck = true;
	
	public CheckBox() {
		this("", 10, 10);
	}
	
	public CheckBox(String lable, int width, int height) {
		super(lable,width,height);
		IsTextCenter = false;
		
//		if(CommonUserRect!=null)
//			UserRect = CommonUserRect;
//		if(CommonCheckedImage!=null)
//			CheckedImage = CommonCheckedImage;
//		if(CommonUncheckedImage!=null)
//			UncheckedImage = CommonUncheckedImage;
			
		CanFocus = true;
	}

	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public void clicked(Form sender,int key,int x,int y){
		
		if (!this.CanClickCheck)
			return;
		
		if(IsClickCheck){
			setCheck(!IsChecked);
			
			if(Listener!=null){
				Listener.itemCommandAction(Click,this);
			}
		}else{
			if(CMath.includeRectPoint2(
					sender.toScreenPosX(X), 
					sender.toScreenPosY(Y), 
					H, H, 
					x, y)){
				setCheck(!IsChecked);
			}
			else if(Listener!=null){
				Listener.itemCommandAction(Click,this);
			}
		}
		
	}
	
	public boolean isChecked(){
		return IsChecked;
	}
	
	public void setCheck(boolean check){
		if (check) {
			if (m_Repels!=null){
				for (int i=0; i<m_Repels.length; i++) {
					if (m_Repels[i]!=null && m_Repels[i]!=this){
						m_Repels[i].setCheck(false);
					}
				}
			}
		}
		IsChecked = check;
	}
	
	public boolean update(Form form) {
		
		return true;
	}
	
	public void render(IGraphics g, int x, int y) {
		String temp = Lable;
		Icon = IsChecked?CheckedImage:UncheckedImage;
		if(Icon==null){
			Lable = (IsChecked?CheckedText:UncheckedText) + Lable;
		}
		super.render(g, x, y);
		if(Icon==null){
			Lable = temp;
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////
	//
	CheckBox[] m_Repels;
	
	public void setRepels(CheckBox[] repels){
		m_Repels = repels;
	}

}
