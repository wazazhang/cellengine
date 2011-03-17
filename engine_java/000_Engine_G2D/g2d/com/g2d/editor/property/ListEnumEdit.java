package com.g2d.editor.property;

import java.awt.Component;
import java.util.EnumSet;
import java.util.Vector;

import javax.swing.JComboBox;

/**
 * 将枚举值列举在ComboBox中
 * @author WAZA
 * @param <T>
 */
public class ListEnumEdit<T extends Enum<T>> extends JComboBox implements PropertyCellEdit<T>
{
	private static final long serialVersionUID = 1L;
	
	Class<T> type;
	
	public ListEnumEdit(Class<T> cls) 
	{
		super(new Vector<T>(EnumSet.allOf(cls)));
		this.type = cls;
	}
	
	public Component getComponent(ObjectPropertyEdit panel) {
		return this;
	}
	
	public T setValue(T value) {
		T old = getValue();
		this.setSelectedItem(value);
		return old;
	}
	
	public T getValue() {
		Object item = getSelectedItem();
		if (item != null) {
			try {
				T ret = type.cast(item);
				return ret;
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
		return null;
	}
}
