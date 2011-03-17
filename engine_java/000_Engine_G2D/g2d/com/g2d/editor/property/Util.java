package com.g2d.editor.property;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.g2d.annotation.Property;

public class Util 
{

	/**
	 * 得到该类在编辑器里的名字
	 * @param cls
	 * @see Property
	 * @return
	 */
	public static String getEditName(Class<?> cls) {
		Property property = cls.getAnnotation(Property.class);
		if (property != null) {
			return property.value()[0];
		} else {
			return cls.getSimpleName();
		}
	}

	/**
	 * 得到所有标注为{@link Property}的字段
	 * cls.getDeclaredFields()
	 * @see Property
	 * @return
	 */
	public static Field[] getEditFields(Class<?> cls) {
		Field[] edit_fields = null;
			ArrayList<Field> fields = new ArrayList<Field>();
			for (Field field : cls.getDeclaredFields()) {
				Property pro = field.getAnnotation(Property.class);
				if (pro != null) {
					fields.add(field);
				}
			}
			edit_fields = fields.toArray(new Field[fields.size()]);
		
		return edit_fields;
	}

	/**
	 * 得到字段标注为{@link Property}名字
	 * @param field
	 * @see Property
	 * @return
	 */
	public static String getEditFieldName(Field field) {
		Property property = field.getAnnotation(Property.class);
		if (property != null) {
			return property.value()[0];
		} else {
			return field.getName();
		}
	}
	
	
	/**
	 * 得到所有标注为{@link Property}的字段
	 * cls.getFields()
	 * @see Property
	 * @return
	 */
	public static Field[] getPublicFields(Class<?> cls) {
		Field[] edit_fields = null;
			ArrayList<Field> fields = new ArrayList<Field>();
			for (Field field : cls.getFields()) {
				Property pro = field.getAnnotation(Property.class);
				if (pro != null) {
					fields.add(field);
				}
			}
			edit_fields = fields.toArray(new Field[fields.size()]);
		
		return edit_fields;
	}
}
