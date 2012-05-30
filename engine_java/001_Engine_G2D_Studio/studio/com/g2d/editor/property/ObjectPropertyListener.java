package com.g2d.editor.property;

import java.lang.reflect.Field;

public interface ObjectPropertyListener {

	
	public void onFieldChanged(Object object, Field field);
}
