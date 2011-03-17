package com.g2d.studio.gameedit;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;

import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.entity.ObjectNode;


@SuppressWarnings("serial")
public class ObjectSelectCellEdit<T extends ObjectNode<?>> extends ObjectSelectDialogString<T> implements PropertyCellEdit<String>
{
	public ObjectSelectCellEdit(Component owner, Class<T> object_type) 
	{
		this(owner, object_type, null);
	}
	
	public ObjectSelectCellEdit(Component owner, Class<T> object_type, Object selected) 
	{
		this(owner, object_type, null, null);
	}
	
	public ObjectSelectCellEdit(Component owner, Class<T> object_type, Object selected, ObjectSelectFilter<T> filter) 
	{
		super(owner, object_type, 4, selected, filter);
	}
	
	

}
