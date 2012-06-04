package com.g2d.editor.property;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.JButton;


public abstract class PopupCellEdit<T> extends JButton 
implements ActionListener, 
PropertyCellEdit<T>
{
	private static final long serialVersionUID = 1;

	protected static final String EDIT = "edit";

	protected Field current_field;
	
	protected T	current_value;
	
	protected ObjectPropertyEdit sender;

	public PopupCellEdit() 
	{
		this.setActionCommand(EDIT);
		this.addActionListener(this);
		this.setBorderPainted(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (EDIT.equals(e.getActionCommand())) {
			onOpenEditor(current_value);
		} 
	}

	abstract public void onOpenEditor(T	value);

	public Component getComponent(ObjectPropertyEdit panel) {
		sender = panel;
		return this;
	}
	
	public T getValue() {
		return current_value;
	}

	public void setValue(Field field, T value, ObjectPropertyEdit comp) {
		current_field = field;
		current_value = value;
		sender = comp;
	}
	
	


	
}
