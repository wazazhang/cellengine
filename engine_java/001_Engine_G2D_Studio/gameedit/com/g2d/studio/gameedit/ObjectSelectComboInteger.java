package com.g2d.studio.gameedit;

import java.awt.Component;
import java.util.Vector;

import javax.swing.JComboBox;

import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.entity.ObjectNode;


@SuppressWarnings("serial")
public class ObjectSelectComboInteger<T extends ObjectNode<?>>  extends JComboBox implements PropertyCellEdit<Integer>
{
	private static final long serialVersionUID = 1L;

	ObjectPropertyEdit panel;
	
	public ObjectSelectComboInteger(Class<T> object_type) 
	{
		this(object_type, null);
	}
	
	public ObjectSelectComboInteger(Class<T> object_type, Object selected) 
	{
		this(object_type, null, null);
	}
	
	public ObjectSelectComboInteger(Class<T> object_type, Object selected, ObjectSelectFilter<T> filter) 
	{
		super(ObjectSelectCellEdit.getObjects(object_type, filter));
		if (selected != null) {
			super.setSelectedItem(selected);
		}
	}
	
	public Component getComponent(ObjectPropertyEdit panel) {		
		this.panel = panel;
		return this;
	}
	
	public Integer getValue() {
		Object item = getSelectedItem();
		if (item instanceof ObjectNode<?>) {
			return ((ObjectNode<?>) item).getIntID();
		}
		return null;
	}

	
}
