package com.cell.gfx.gui;


import com.cell.CMath;
import com.cell.gfx.IGraphics;

abstract public class Item extends Control
{
//	public static boolean IsTextBuffer = false;

	public boolean AllowDrop = false;
	
	public UIRect UserRect = new UIRect(false);
	
	public int X;
	public int Y;
	protected int W;
	protected int H;
	
	public boolean IsEnabled = true;
	
	public boolean IsVisible = true;
	
	public boolean CanFocus = true;

	public boolean CanAutoFocus = true;
	
	protected boolean IsFocused = false;
	
	//
	//public String FontName = null;
	//public int FontSize = 12;
	
	//
	public int TextColor = 0xffffffff;
	protected String Lable;

	
	// key
	public int KeyClick			= Form.KEY_A | Form.KEY_C;
	protected Command Click 	= null;
	protected ItemListener Listener;
	
	//protected Form OwnerForm;
/*
	public Form getOwnerForm()
	{
		return OwnerForm;
	}
	*/
	
	/**
	 * @param form TODO
	 * @return 
	 */
	abstract public boolean update(Form form);
	/**
	 * @param g
	 * @param x
	 * @param y
	 */
	abstract public void render(IGraphics g, int x, int y);
	/**
	 * @param width
	 * @param height
	 */
	abstract public void resize(int width,int height);
	/**
	 * @param sender
	 * @param key
	 * @param x 	screen pos
	 * @param y 	screen pos
	 */
	abstract public void clicked(Form sender,int key,int x,int y);

	
	
	public void pressed(Form sender){}
	public void released(Form sender){}
//	protected boolean IsPressed = false;
	
	protected void lossFocuse(Form sender){}
	protected void getFocuse(Form sender){}
	
	public void setEnable(boolean enable, Form form){
		if (enable){
			this.IsVisible = true;
			this.IsEnabled = true;
		}else{
			this.IsVisible = false;
			this.IsEnabled = false;
			if (form.getFocus()==this){
				form.focus(null);
			}
		}
	}
	
	public void setColorByEnable(boolean enable){
		if (enable){
			this.IsEnabled = true;
			this.CanFocus = true;
			this.TextColor = 0xFFF0F000;
		}else{
			this.IsEnabled = false;
			this.CanFocus = false;
			this.TextColor = 0xFF808080;
		}
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------------------
	
	public boolean includePoint(int x,int y){
		return CMath.includeRectPoint(X, Y, X+W, Y+H, x, y);
	}
	
	public void setText(String str){
		str = processReplaceTable(str);
		str = Control.LocalCovert.covert(str);
		Lable = str;
	}
	public String getText(){
		return Lable;
	}
	
	public int getWidth(){
		return W;
	}
	public int getHeight(){
		return H;
	}
	
	public boolean isFocused()
	{
		return this.IsFocused;
	}
	
	public void setClick(Command command){
		Click = command;
	}
	
	public Command getClick(){
		return Click;
	}
	
	public void setCommandListener(ItemListener listener){
		Listener = listener;
	}

//	-------------------------------------------------------

	
	
	
	
//	---------------------------------------------------------------------------------------------------------------------------
	
	
	public void notifyDestroy() {
		// TODO Auto-generated method stub
		
	}

	public void notifyLogic() {
		// TODO Auto-generated method stub
		
	}

	public void notifyPause() {
		// TODO Auto-generated method stub
		
	}

	public void notifyRender(IGraphics g) {
		// TODO Auto-generated method stub
		
	}

	public void notifyResume() {
		// TODO Auto-generated method stub
		
	}
	


}
