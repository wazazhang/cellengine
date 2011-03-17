package com.g2d.studio.cpj;

import java.awt.Component;

import javax.swing.JLabel;

import com.cell.rpg.display.Node;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;

public class CPJResourceSelectCellEdit<T extends Node> extends JLabel implements PropertyCellEdit<T>
{
	public CPJResourceSelectCellEdit(Class<T> type) {
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		return this;
	}
	
	@Override
	public T getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
