package com.g2d.display.ui.xml;

import com.g2d.display.ui.UIComponent;

public interface ComponentInputListener
{
	public void componentPropertySet(UIComponent component, String key, String value);
	
	public void componentAttributeSet(UIComponent component, String key, String value);

}
