package com.g2d.display.event;

import com.g2d.Version;
import com.g2d.display.InteractiveObject;


public class MouseEvent extends Event<InteractiveObject>
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	final static public int 					EVENT_MOUSE_DOWN		= 1;
	final static public int 					EVENT_MOUSE_UP			= 2;
//	final static public int 					EVENT_MOUSE_MOVED		= 3;
	final static public int 					EVENT_MOUSE_DRAGGED		= 10;
	final static public int 					EVENT_MOUSE_WHEELD		= 20;
	
	final static public int 					BUTTON_LEFT 			= java.awt.event.MouseEvent.BUTTON1;
	final static public int						BUTTON_RIGHT 			= java.awt.event.MouseEvent.BUTTON3;
	
	
	static private int 							s_mouse_down_count 		= 0;
	
	final public java.awt.event.MouseEvent 		superEvent;
	final public int 							mouseDownCount;
	final public int 							type;
	final public int 							mouseButton;
	
	public MouseEvent(java.awt.event.MouseEvent event, int type) {
		superEvent = event;
		mouseButton = event.getButton();
		this.type = type;
		if (type == EVENT_MOUSE_DOWN) {
			s_mouse_down_count ++;
		}
		mouseDownCount = s_mouse_down_count;
	}
	
}
