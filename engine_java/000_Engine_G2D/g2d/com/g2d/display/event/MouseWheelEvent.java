package com.g2d.display.event;

import com.g2d.Version;


public class MouseWheelEvent extends MouseEvent
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	final public int scrollDirection;
	
	public MouseWheelEvent(java.awt.event.MouseWheelEvent event) {
		super(event, EVENT_MOUSE_WHEELD);
		scrollDirection = event.getWheelRotation();
	}
	
}
