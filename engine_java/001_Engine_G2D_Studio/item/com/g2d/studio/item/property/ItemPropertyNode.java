package com.g2d.studio.item.property;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JList;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.item.ItemPropertyTemplate;
import com.g2d.studio.swing.G2DListItem;

public class ItemPropertyNode implements G2DListItem
{
	final Class<?> type;
	
	public ItemPropertyNode(Class<?> type) {
		this.type = type;
	}
	
	@Override
	public Component getListComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		return null;
	}
	@Override
	public ImageIcon getListIcon(boolean update) {
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemPropertyNode) {
			return type.equals(((ItemPropertyNode) obj).type);
		}
		return false;
	}
	
	@Override
	public String getListName() {
		return AbstractAbility.getEditName(type);
	}
	
	public ItemPropertyTemplate getItemPropertyTemplate() {
		try {
			return (ItemPropertyTemplate)type.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

