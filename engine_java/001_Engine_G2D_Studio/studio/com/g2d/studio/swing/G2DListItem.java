package com.g2d.studio.swing;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JList;

public abstract interface G2DListItem 
{
	abstract public ImageIcon	getListIcon(boolean update); 

	abstract public String		getListName();
	
	abstract public Component	getListComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus);
}
