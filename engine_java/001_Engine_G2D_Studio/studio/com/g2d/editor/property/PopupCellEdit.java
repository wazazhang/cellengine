package com.g2d.editor.property;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public abstract class PopupCellEdit<T> extends JButton implements ActionListener, PropertyCellEdit<T>
{
	private static final long serialVersionUID = 1;

	protected static final String EDIT = "edit";

	protected T	current_value;
	
	protected ObjectPropertyEdit	sender;

	public PopupCellEdit() 
	{
		this.setActionCommand(EDIT);
		this.addActionListener(this);
		this.setBorderPainted(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (EDIT.equals(e.getActionCommand())) {
			onOpenEditor();
		} 
	}

	abstract public void onOpenEditor();

	public Component getComponent(ObjectPropertyEdit panel) {
		sender = panel;
		return this;
	}
	
	public T getValue() {
		return current_value;
	}

	public void setValue(T value, ObjectPropertyEdit comp) {
		current_value = value;
		sender = comp;
	}
	
	


	
}
