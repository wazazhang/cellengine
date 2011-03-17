package com.g2d.studio.gameedit.entity;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JList;

import com.cell.rpg.RPGObject;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.studio.swing.G2DTreeNode;

public abstract class ObjectNode<T extends RPGObject> extends G2DTreeNode<ObjectNode<?>> implements G2DListItem
{
	transient protected ObjectViewer<?>		edit_component;
	
	public ObjectNode() {}

	public abstract String getID();
	
	public abstract T getData();

	public abstract int getIntID() ;

	
//	----------------------------------------------------------------------------------------
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj.getClass().equals(getClass())) {
			return ((ObjectNode<?>)obj).getIntID() == getIntID();
		}
		return false;
	}
	
	@Override
	protected ImageIcon createIcon() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}
	
	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	
	public ObjectViewer<?> getEditComponent(){
		return edit_component;
	}
	
	@Override
	public Component getListComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		return null;
	}
	@Override
	public ImageIcon getListIcon(boolean update) {
		return getIcon(update);
	}
	@Override
	public String getListName() {
		return getName();
	}

	public void onOpenEdit() {
		
	}
	public void onSave() {
		
	}
}
