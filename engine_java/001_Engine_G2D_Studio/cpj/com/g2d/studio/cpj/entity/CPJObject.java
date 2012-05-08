package com.g2d.studio.cpj.entity;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JList;

import com.cell.gameedit.SetObject;
import com.g2d.awt.util.*;

import com.g2d.display.DisplayObject;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.studio.swing.G2DTreeNode;

public abstract class CPJObject <T extends SetObject> extends G2DTreeNode<CPJObject<?>> implements G2DListItem, Comparable<CPJObject<?>>
{
	private static final long serialVersionUID = 1L;
	final public CPJFile			parent;
	final public String				name;
	final public CPJResourceType 	res_type;

	private T					set_object;
	protected BufferedImage		snapshoot;
	
	
	public CPJObject(CPJFile parent, String name, Class<T> type, CPJResourceType res_type) {
		this.parent 	= parent;
		this.name		= name;
		this.res_type	= res_type;
		//this.set_object	= parent.getSetResource().getSetObject(type, name);
	}
	
	@Override
	public int compareTo(CPJObject<?> o) {
		return this.name.compareTo(o.name);
	}
	
//	abstract public DisplayObject createDisplayObject();

	public<O extends CPJObject<?>> CPJIndex<O> getCPJIndex(CPJResourceType type)
	{
		return new CPJIndex<O>(type, parent.getName(), name);
	}
	
	public CPJFile getCPJFile() {
		return parent;
	}
	
	public BufferedImage getSnapShoot(){
		return snapshoot;
	}
	
	public T getSetObject() {
		return set_object;
	}

	public void setSetObject(T obj) {
		this.set_object = obj;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	final public ImageIcon createIcon() {
		if (getSnapShoot()!=null) {
			return Tools.createIcon(getSnapShoot());
		}
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

}
