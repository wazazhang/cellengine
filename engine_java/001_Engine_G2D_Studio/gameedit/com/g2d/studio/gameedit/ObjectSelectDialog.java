package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.swing.G2DList;

public class ObjectSelectDialog<T extends ObjectNode<?>> extends ObjectSelect<T> implements PropertyCellEdit<T>
{
	private static final long serialVersionUID = 1L;
		
	public ObjectSelectDialog(Component owner, Class<T> type, int wcount, Object default_value, ObjectSelectFilter<T> filter)
	{
		super(owner, type, wcount, default_value, filter);
	}
	public ObjectSelectDialog(Component owner, Class<T> type, int wcount)
	{
		super(owner, type, wcount, null, null);
	}
	
	
	public Object[] showDialog2()
	{
		super.setVisible(true);
		return getUserObjects();		
	}

	protected Object[] getUserObjects()
	{
		return list.getSelectedItems();
	}
	
	@Override
	public T getValue() {
		return getSelectedObject();
	}
}
