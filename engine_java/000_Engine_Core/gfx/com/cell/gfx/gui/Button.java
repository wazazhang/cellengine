package com.cell.gfx.gui;


import java.util.Vector;

import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;

public class Button extends LabelBar 
{
	public static class ButtonGroup extends Group
	{
		final static public int LAYOUT_SINGLE_SELECT = 0;
		
		Vector<Button> Buttons = new Vector<Button>();
		
		int Layout = 0;
		
		public ButtonGroup(int layout)
		{
			Layout = layout;
		}
		
		public void addButton(Button btn)
		{
			switch (Layout)
			{
			case LAYOUT_SINGLE_SELECT:
				btn.IsPressOnClick 	= true;
				if (Buttons.isEmpty()){
					btn.setPressed(true);
				}
				break;
			}
			
			Buttons.addElement(btn);
		}
		
		protected boolean itemAction(Command command, Item item, Group group) 
		{
			if (group == this && item instanceof Button && Buttons.contains(item))
			{
				Button btn = (Button)item;
				
				switch (Layout)
				{
				case LAYOUT_SINGLE_SELECT:
					
					btn.setPressed(true);
					
					for (int i=Buttons.size()-1; i>=0; i--)
					{
						if (Buttons.elementAt(i) != btn)
						{
							Buttons.elementAt(i).setPressed(false);
						}
					}
					
					break;
				}
				
				return true;
			}
			return false;
		}

		protected void update(Form form) 
		{
			
		}
		
	}
	
//	public static UIRect CommonUserRect 		= new UIRect();
//	public static UIRect CommonPressDownRect 	= new UIRect();
//	static
//	{
//		CommonUserRect.BackColor   = 0xffc0c0c0;
//		CommonUserRect.BorderColor = 0xff000000;
//		
//		CommonPressDownRect.BackColor   = 0xffc0c0c0;
//		CommonPressDownRect.BorderColor = 0xff000000;
//	}
	
	public UIRect PressDownRect = new UIRect(false);
	public IImage PressDownIcon = null;
	
	public int ColorFocusedText 	= 0xffffffff;
	public int ColorUnFocusedText 	= 0xffffff00;
	
	public boolean IsPressOnClick = false;
	
	protected boolean IsPressed = false;
	
	public Button() {
		this(10, 10, new Command(""));
	}
	
	public Button(String text, int width,int height) {
		this(width, height, new Command(text));
	}
	
	public Button(int width,int height, Command click) {
		super(click.Lable,width,height);
		super.Icon = click.Icon;

//		if(CommonUserRect!=null)
//			UserRect = CommonUserRect;
//		if(CommonPressDownRect!=null)
//			PressDownRect = CommonPressDownRect;
		
		
		IsTextCenter = true;
		CanFocus = true;
		Click = click;
		setClick(Click);
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

	public boolean isPressed(){
		return IsPressed;	
	}
	
	
	public void pressed(Form sender) {
		super.pressed(sender);
		if(IsPressOnClick){
			setPressed(!IsPressed);
		}else{
			setPressed(true);
		}
	}

	public void setPressed(boolean pressed) {
		if (pressed) {
			if (m_Repels!=null){
				for (int i=0; i<m_Repels.length; i++) {
					if (m_Repels[i]!=null && m_Repels[i]!=this){
						m_Repels[i].setPressed(false);
					}
				}
			}
		}
		IsPressed = pressed;
	}
	
	public void released(Form sender) {
		super.released(sender);
		if(!IsPressOnClick){
			setPressed(false);
		}
	}


	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public boolean update(Form form) {
		if (!IsPressOnClick) {
			if (form.getFocus() != this || !Form.isPointerHold()) {
				setPressed(false);
			}
		}
		return true;
	}
	
	public void render(IGraphics g, int x, int y)
	{
		UIRect rect = UserRect;
		if(IsPressed)UserRect = PressDownRect;

		TextColor = this.IsEnabled? 
				(IsFocused ? ColorFocusedText : ColorUnFocusedText)
				: (0xFF808080); 
		
		//super.render(g, x, y);
		{
			g.pushClip();
			g.clipRect(x , y , W + 1, H + 1);
			
			// back
			UserRect.renderBody(g, x, y, W, H);
			//border
			UserRect.renderBorder(g, x, y, W, H);
			
			if(IsPressed){
				renderPressIcon(g, x, y+1);
				renderText(g, x, y+1);
			}else{
				renderText(g, x, y);
				renderIcon(g, x, y);
			}
			
			g.popClip();
		}
		
		UserRect = rect;
	}

	Button[] m_Repels;
	
	public void setRepels(Button[] repels){
		m_Repels = repels;
	}

	public void renderPressIcon(IGraphics g, int x, int y){
		if(PressDownIcon!=null){
			if (IsIconCenter){
				g.drawImage(PressDownIcon, x +(W-PressDownIcon.getWidth())/2, y+(H-PressDownIcon.getHeight())/2, 0);
			} else{
				g.drawImage(PressDownIcon, x +(H-PressDownIcon.getWidth())/2, y+(H-PressDownIcon.getHeight())/2, 0);
			}
		}else{
			super.renderIcon(g, x, y);
		}
	}
	
}
