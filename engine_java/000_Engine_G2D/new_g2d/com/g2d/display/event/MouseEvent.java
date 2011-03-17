package com.g2d.display.event;

import com.g2d.display.InteractiveObject;


public class MouseEvent extends Event<InteractiveObject>
{
	final static public int 					EVENT_MOUSE_DOWN		= 1;
	final static public int 					EVENT_MOUSE_UP			= 2;
//	final static public int 					EVENT_MOUSE_MOVED		= 3;
	final static public int 					EVENT_MOUSE_DRAGGED		= 10;
	final static public int 					EVENT_MOUSE_WHEELD		= 20;
	
	final static public int 					BUTTON_LEFT 			= java.awt.event.MouseEvent.BUTTON1;
	final static public int						BUTTON_RIGHT 			= java.awt.event.MouseEvent.BUTTON3;
	
	
	static private int 							s_mouse_down_count 		= 0;
	
//	final public java.awt.event.MouseEvent 		superEvent;
	final public int 							mouseDownCount;
	final public int 							type;
	final public int 							mouseButton;
	final public int							clickCount;
	
	public MouseEvent(int button, int click_count, int type) {
//		superEvent = event;
		this.clickCount = click_count;
		mouseButton = button;
		this.type = type;
		if (type == EVENT_MOUSE_DOWN) {
			s_mouse_down_count ++;
		}
		mouseDownCount = s_mouse_down_count;
	}
	
}
