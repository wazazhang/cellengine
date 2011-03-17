package com.g2d.display.ui.event;

import com.g2d.display.event.Event;
import com.g2d.display.ui.UIComponent;

public class ActionEvent extends Event<UIComponent> 
{
	final public Action action;
	
	public ActionEvent(UIComponent source, Action action) {
		this.source = source;
		this.action = action;
	}
	
	
}
