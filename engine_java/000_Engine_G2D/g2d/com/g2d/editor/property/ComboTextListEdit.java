package com.g2d.editor.property;

import java.awt.Component;
import java.util.Collection;

import javax.swing.JComboBox;

/**
 * 将枚举值列举在ComboBox中
 * @author WAZA
 * @param <T>
 */
public class ComboTextListEdit extends JComboBox implements PropertyCellEdit<String>
{
	private static final long serialVersionUID = 1L;
	
	public ComboTextListEdit(Collection<String> texts, String default_value) {
		super(texts.toArray(new String[texts.size()]));
		if (default_value!=null) {
			setSelectedItem(default_value);
		}
	}
	
	public ComboTextListEdit(Collection<String> texts) {
		this(texts, null);
	}
	
	public Component getComponent(ObjectPropertyEdit panel) {
		return this;
	}
	
	public void setValue(String value) {
		this.setSelectedItem(value);
	}
	
	@Override
	public String getValue() {
		Object obj = getSelectedItem();
		if (obj != null) {
			return obj.toString();
		}
		return null;
	}
}

