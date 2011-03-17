package com.g2d.editor.property;

import java.awt.Component;

public interface PropertyCellEdit<T>
{
	public T getValue();
	
	public Component getComponent(ObjectPropertyEdit panel);
	
}
