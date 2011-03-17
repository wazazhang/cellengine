package com.g2d.editor.property;

import java.awt.Component;

import javax.swing.JComboBox;

/**
 * 将枚举值列举在ComboBox中
 * @author WAZA
 * @param <T>
 */
public class ComboBooleanEdit extends JComboBox implements PropertyCellEdit<Boolean>
{
	private static final long serialVersionUID = 1L;
	
	public ComboBooleanEdit(Boolean value) {
		super(new String[]{"false", "true"});
		if (value!=null) {
			setSelectedItem(value.toString().toLowerCase());
		}
	}
	
	public ComboBooleanEdit() 
	{
		this(false);
	}
	
	public Component getComponent(ObjectPropertyEdit panel) {
		return this;
	}
	
	public void setValue(boolean value) {
		this.setSelectedItem(value?"true":"false");
	}
	
	@Override
	public Boolean getValue() {
		Object obj = getSelectedItem();
		if ("true".equals(obj)) {
			return true;
		}
		return false;
	}
}

