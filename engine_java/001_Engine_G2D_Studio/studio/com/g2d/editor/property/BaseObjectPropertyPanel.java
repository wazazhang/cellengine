package com.g2d.editor.property;

import java.awt.Component;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import com.cell.reflect.Parser;
import com.g2d.Color;
import com.g2d.Font;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.editor.Util;




@SuppressWarnings("serial")
public abstract class BaseObjectPropertyPanel extends JPanel implements ObjectPropertyEdit
{	
	static {
		com.g2d.editor.Util.initParser();
	}
	
	final protected Map<Class<?>, CellEditAdapter<?>>	
							edit_adapters 	= new Hashtable<Class<?>, CellEditAdapter<?>>();
	
	private Set<ObjectPropertyListener> edit_listeners = new LinkedHashSet<ObjectPropertyListener>();

	public BaseObjectPropertyPanel(CellEditAdapter<?> ... adapters)
	{
		for (CellEditAdapter<?> ad : adapters) 
		{
			edit_adapters.put(ad.getClass(), ad);
		}
	}
	
	final public Collection<CellEditAdapter<?>> getAdapters() 
	{
		return edit_adapters.values();
	}
	
	public void addObjectPropertyListener(ObjectPropertyListener lis) {
		edit_listeners.add(lis);
	}

	public void removeObjectPropertyListener(ObjectPropertyListener lis) {
		edit_listeners.remove(lis);
	}

	@Override
	final public Component getComponent() 
	{
		return this;
	}
	
	/**
	 * 用户自定义该字段显示的内容
	 * @param object		被编辑的对象
	 * @param field			被编辑的对象类的字段
	 * @param field_value	被编辑的对象类的字段当前值
	 * @return
	 */
	final protected Component getPropertyCellRender(DefaultTableCellRenderer src, Object object, Field field, Object field_value) 
	{
		try {
			for (CellEditAdapter<?> ad : edit_adapters.values()) {
				if (ad.getType().isInstance(object)) {
					Component ret = ad.getCellRender(this, object, field_value, field, src);
					if (ret != null) {
						return ret;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return src;
	}
	
	/**
	 * 用户自定义该字段显示的内容(下面的信息栏里)
	 * @param object		被编辑的对象
	 * @param field			被编辑的对象类的字段
	 * @param field_value	被编辑的对象类的字段当前值
	 * @return
	 */
	final protected String getPropertyCellText(Object object, Field field, Object field_value) 
	{
		try {
			for (CellEditAdapter<?> ad : edit_adapters.values()) {
				if (ad.getType().isInstance(object)) {
					String ret = ad.getCellText(object, field, field_value);
					if (ret != null) {
						return ret;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String txt = Parser.objectToString(field_value);
		return txt;
	}
	
	/**
	 * 创建一个单元格的编辑器，该编辑器必须实现 PropertyCellEdit<br>
	 * 用于用户自自定义子类的编辑器
	 * @param object		被编辑的对象
	 * @param field			被编辑的对象类的字段
	 * @param field_value	被编辑的对象类的字段当前值
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	final protected PropertyCellEdit<?> getPropertyCellEdit(Object object, Field field, Object field_value)
	{
		// 从适配器里选取
		try {
			for (CellEditAdapter<?> ad : edit_adapters.values()) {
				if (ad.getType().isInstance(object)) {
					PropertyCellEdit<?> edit = ad.getCellEdit(this, object, field_value, field);
					if (edit != null) {
						if (edit instanceof PopupCellEdit<?>) {
							try {
								((PopupCellEdit) edit).setValue(field, field_value, this);
							}catch (Exception e) {
								e.printStackTrace();
							}
						}
						return edit;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (field.getType().isEnum()) 
		{
			ListEnumEdit edit = new ListEnumEdit(field.getType());
			return edit;
		}
		else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
			ComboBooleanEdit edit = new ComboBooleanEdit((Boolean)field_value);
			return edit;
		}
		else if (field.getType().equals(Color.class))
		{
			PopupCellEditColor edit_color = new PopupCellEditColor();
			edit_color.setValue(field, (Color) field_value, this);
			return edit_color;
		}
		else if (field.getType().equals(Font.class))
		{
			PopupCellEditFont edit_font = new PopupCellEditFont();
			edit_font.setValue(field, (Font) field_value, this);
			return edit_font;
		}
		else if (field.getType().equals(UILayout.class))
		{
			PopupCellEditUILayout edit_ui_layout	= new PopupCellEditUILayout();
			edit_ui_layout.setValue(field, (UILayout)field_value, this);
			return edit_ui_layout;
		}
		else if (Parser.isNumber(field.getType())) 
		{
			NumberCellEdit numedit = new NumberCellEdit(field.getType(), field_value);
			return numedit;
		}
		else {
			TextCellEdit text_edit = new TextCellEdit();
			//text_edit.setText(Util.fromObject(field_value));
			String txt = Parser.objectToString(field_value);
			text_edit.setText(txt);
			return text_edit;
		}
	}
	
	/**
	 * 当字段属性编辑器完成编辑后，得到单元格的值<br>
	 * 用于用户自自定义子类的编辑器
	 * @param object		被编辑的对象
	 * @param field			被编辑的对象类的字段
	 * @param edit			字段属性编辑器
	 * @param src_value		被编辑的对象类的字段原值
	 * @return
	 */
	final protected Object getPropertyCellEditValue(Object object, Field field, PropertyCellEdit<?> edit, Object src_value)
	{
		try{
			for (CellEditAdapter<?> ad : edit_adapters.values()) {
				if (ad.getType().isInstance(object)) {
					Object ret = ad.getCellValue(object, edit, field, src_value);
					if (ret!=null){
						return ret;
					}
				}
			}
		}catch(Exception err){
			err.printStackTrace();
		}
		
		Object obj = null;
		if (edit instanceof NumberCellEdit) {
			obj = ((NumberCellEdit)edit).getValue();
		}
		else if (edit instanceof TextCellEdit) {
			//obj = Util.parseObject(((TextCellEdit) edit).getValue(), src_value == null ? field.getType() : src_value.getClass());
			obj = Parser.stringToObject(
					((TextCellEdit) edit).getValue(), 
					src_value == null ? field.getType() : src_value.getClass());
		}
		else {
			obj = edit.getValue();
		}
		return obj;
	}

	/**
	 * 当单月格的值被改变时回调
	 * @param object 当前被改变的对象
	 * @param field 该对象在其所有者中的字段
	 */
	final protected void onFieldChanged(Object object, Field field)
	{
		try{
			for (CellEditAdapter<?> ad : edit_adapters.values()) {
				if (ad.getType().isInstance(object)) {
					if (ad.fieldChanged(
							object, field.get(object), field)){
						return;
					}
				}
			}
			for (ObjectPropertyListener lis : edit_listeners) {
				lis.onFieldChanged(object, field);
			}
		}catch(Exception err){
			err.printStackTrace();
		}finally{
//			try{
//				rows_table.valueChanged(null);
//			}catch(Exception err){}
		}
	}

//	--------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 空编辑器
	 * @author WAZA
	 *
	 */
	public static class NullEditor extends AbstractCellEditor implements TableCellEditor
	{
		private static final long serialVersionUID = 1L;
		@Override
		public boolean isCellEditable(EventObject e) {return false;}
		public Object getCellEditorValue() {return null;}
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){return null;}
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------------
	
}
