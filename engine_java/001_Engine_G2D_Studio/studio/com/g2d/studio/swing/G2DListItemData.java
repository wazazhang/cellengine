package com.g2d.studio.swing;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JList;

public class G2DListItemData<T> implements G2DListItem
{
	final private T 		data;
	final private String 	name;
	
	public G2DListItemData(T data) {
		this(data, data + "");
	}

	public G2DListItemData(T data, String name) {
		this.data = data;
		this.name = name;
	}
	
	
	@Override
	public Component getListComponent(JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		return null;
	}
	
	@Override
	public ImageIcon getListIcon(boolean update) {
		return null;
	}
	
	@Override
	public String getListName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getListName();
	}

	public T getData() {
		return data;
	}
	
}
