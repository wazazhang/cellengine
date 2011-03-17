package com.g2d.display.event;

import com.g2d.display.InteractiveObject;


public class KeyEvent extends Event<InteractiveObject>
{
	final static public int 					EVENT_KEY_DOWN		= 1;
	final static public int 					EVENT_KEY_UP		= 2;
	final static public int 					EVENT_KEY_TYPED		= 3;
	
	final static public int						VK_ENTER			= java.awt.event.KeyEvent.VK_ENTER;
	
	final public java.awt.event.KeyEvent 		superEvent;
	final public int 							type;
	final public int 							keyCode;
	final public char 							keyChar;
	
	public KeyEvent(java.awt.event.KeyEvent event, int type) 
	{
		this.superEvent = event;
		this.keyCode = event.getKeyCode();
		this.keyChar = event.getKeyChar();
		this.type = type;
	}
	

	
}
