package com.cell.rpg.item;

import java.util.Set;

import com.g2d.editor.property.PropertyEditor;

public interface ItemPropertyManager {

	/**
	 * 得到所有属性类型
	 * @return
	 */
	public Set<Class<?>> 				getAllTypes();
	
	/**
	 * 得到扩展的属性类型适配器
	 * @return
	 */
	public Set<PropertyEditor<?>>		getAllAdapters();
}
