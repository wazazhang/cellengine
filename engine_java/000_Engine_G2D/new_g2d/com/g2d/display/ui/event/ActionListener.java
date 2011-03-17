package com.g2d.display.ui.event;

import com.g2d.display.event.EventListener;
import com.g2d.display.ui.UIComponent;

public interface ActionListener extends EventListener 
{
	public void itemAction(UIComponent item, ActionEvent event);
	
	
}
