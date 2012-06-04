package com.g2d.editor.property;

import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class TextCellEdit extends JTextField implements PropertyCellEdit<String>
{
	private static final long serialVersionUID = 1L;
	
	public TextCellEdit() {
		super.setBorder(new EmptyBorder(1, 1, 1, 1));
	}
	
	public TextCellEdit(Object text) {
		super.setBorder(new EmptyBorder(1, 1, 1, 1));
		super.setText(text+"");
	}
	public String getValue() {
		return super.getText();
	}
	
	public Component getComponent(ObjectPropertyEdit panel) {
		return this;
	}

}
