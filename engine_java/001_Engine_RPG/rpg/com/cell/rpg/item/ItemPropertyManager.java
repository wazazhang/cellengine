package com.cell.rpg.item;

import java.util.Set;

import com.cell.rpg.item.anno.ItemType;
import com.g2d.editor.property.PropertyEditor;

public abstract class ItemPropertyManager
{
	/**
	 * 得到所有属性类型
	 * @return
	 */
	public abstract Set<Class<?>> 				getAllTypes();
	
	/**
	 * 得到扩展的属性类型适配器
	 * @return
	 */
	public abstract Set<PropertyEditor<?>>		getAllAdapters();
	
	
	////////////////////////////////////////////////////////////////
	public static Integer getType(Class<?> cls) 
	{
		ItemType type = cls.getAnnotation(ItemType.class);
		if (type != null) {
			return type.value();
		} else {
			return null;
		}
	}
	
}
