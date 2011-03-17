package com.g2d.editor.property;

import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author WAZA
 *
 * @param <T> Edit Object Type
 */
public interface CellEditAdapter<T>
{
	public abstract Class<T> getType();

	public Component getCellRender(
			ObjectPropertyEdit owner,
			Object edit_object, 
			Object field_value, 
			Field field, 
			DefaultTableCellRenderer src);
	
	public PropertyCellEdit<?> getCellEdit(
			ObjectPropertyEdit owner,
			Object edit_object, 
			Object field_value, 
			Field field) ;

	public Object getCellValue(
			Object edit_object, 
			PropertyCellEdit<?> field_edit, 
			Field field, 
			Object field_src_value);
	
	public boolean fieldChanged(
			Object edit_object,
			Object field_value, 
			Field field);
	
	public String getCellText(
			Object edit_object, 
			Field field, 
			Object field_src_value);

}

