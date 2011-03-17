package com.g2d.display.event;

public class MouseMoveEvent extends MouseEvent
{
	final public int mouseDownStartX, mouseDownStartY;
	

	public boolean is_click_resize = false;
	
	
	public MouseMoveEvent(int button, int startDragX, int startDragY) {
		super(button, 0, EVENT_MOUSE_DRAGGED);
		mouseDownStartX = startDragX;
		mouseDownStartY = startDragY;
	}
	
}
