package com.g2d.display.event;

import com.g2d.Version;
import com.g2d.display.InteractiveObject;


public class KeyEvent extends Event<InteractiveObject>
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	
	final static public int 					EVENT_KEY_DOWN		= 1;
	final static public int 					EVENT_KEY_UP		= 2;
	final static public int 					EVENT_KEY_TYPED		= 3;
	
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
