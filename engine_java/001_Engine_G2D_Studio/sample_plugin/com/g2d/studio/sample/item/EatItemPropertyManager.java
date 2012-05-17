package com.g2d.studio.sample.item;

import java.util.HashSet;
import java.util.Set;

import com.cell.rpg.item.ItemPropertyManager;
import com.g2d.editor.property.PropertyEditor;


public class EatItemPropertyManager extends ItemPropertyManager
{
	HashSet<Class<?>> classes = new HashSet<Class<?>>();
		
	public EatItemPropertyManager() {
		for (Class<?> cls : EatItemProperty.class.getClasses()) {
			classes.add(cls);
		}
	}
	
	@Override
	public Set<Class<?>> getAllTypes() {
		return classes;
	}
	
	@Override
	public Set<PropertyEditor<?>> getAllAdapters() {
		HashSet<PropertyEditor<?>> ret = new HashSet<PropertyEditor<?>>();
		return ret;
	}
}
