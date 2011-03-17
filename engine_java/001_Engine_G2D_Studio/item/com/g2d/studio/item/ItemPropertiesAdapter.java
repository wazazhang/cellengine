package com.g2d.studio.item;

import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.item.ItemPropertyTemplate.ArgTemplate;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.editor.property.TextCellEdit;

public class ItemPropertiesAdapter
{
	public static class ValueRangeAdapter implements CellEditAdapter<Object>
	{
		@Override
		public Class<Object> getType() {
			return Object.class;
		}

		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field) {
			if (ArgTemplate.class.isAssignableFrom(field.getType())) {
				try{
					ArgTemplate<?> range = (ArgTemplate<?>)fieldValue;
					TextCellEdit edit = new TextCellEdit();
					edit.setText(range.getArgCreateMin() + " - " + range.getArgCreateMax());
					return edit;
				}catch(Exception err){}
			}
			return null;
		}
		@Override
		public Object getCellValue(Object editObject, PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue) {
			if (ArgTemplate.class.isAssignableFrom(field.getType())) {
				if (fieldEdit instanceof TextCellEdit) {
					TextCellEdit edit = ((TextCellEdit)fieldEdit);
					try{
						ArgTemplate<?> range = (ArgTemplate<?>)fieldSrcValue;
						range.fromString(edit.getValue());
						return range;
					}catch(Exception err){
						JOptionPane.showMessageDialog(edit, 
								"格式错误！\n"
								+ err.getClass().getName() + "\n"
								+ err.getMessage(), 
								"格式错误！",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			return null;
		}

		@Override
		public boolean fieldChanged(Object editObject, Object fieldValue, Field field) {
			return false;
		}
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field, DefaultTableCellRenderer src) {
			return null;
		}
		@Override
		public String getCellText(Object editObject, Field field, Object fieldSrcValue) {
			return null;
		}
	}

}
