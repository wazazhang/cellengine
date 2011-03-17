package com.g2d.display.event;

public class MouseWheelEvent extends MouseEvent
{
	final public int scrollDirection;
	
	public MouseWheelEvent(int button, int scroll_direct) {
		super(button, 0, EVENT_MOUSE_WHEELD);
		scrollDirection = scroll_direct;
	}
	
}
