package com.g2d.studio.item;

import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.template.TItem;
import com.cell.util.Pair;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListSelectDialog;

@SuppressWarnings("serial")
public class XLSItemSelectCellEdit extends G2DListSelectDialog<XLSItem> implements PropertyCellEdit<Integer>
{
	JLabel cell_edit_component = new JLabel();
	
	public XLSItemSelectCellEdit(Component owner, XLSItem dv) 
	{
		super(owner, new ItemTemplateList(), dv);
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		XLSItem node = getSelectedObject();
		cell_edit_component.setText(node+"");
		return cell_edit_component;
	}
	
	public XLSItem setValue(int id) {
		XLSItem node = Studio.getInstance().getObjectManager().getObject(XLSItem.class, id);
		if (node != null) {
			getList().setSelectedValue(node, true);
		}
		return node;
	}
	
	@Override
	public Integer getValue() {
		XLSItem node = getSelectedObject();
		if (node != null) {
			return node.getIntID();
		}
		return -1;
	}
	
	
	static public class XLSItemSelectCellEdit2 extends G2DListSelectDialog<XLSItem> implements PropertyCellEdit<Pair<Integer, String>>
	{
		JLabel cell_edit_component = new JLabel();
		
		public XLSItemSelectCellEdit2(Component owner, XLSItem dv) 
		{
			super(owner, new ItemTemplateList(), dv);
		}
		
		@Override
		public Component getComponent(ObjectPropertyEdit panel) {
			XLSItem node = getSelectedObject();
			cell_edit_component.setText(node+"");
			return cell_edit_component;
		}
		
		public XLSItem setValue(Pair<Integer, String> value) {
			int id = (value==null)? 0 : value.getKey();
			
			XLSItem node = Studio.getInstance().getObjectManager().getObject(XLSItem.class, id);
			if (node != null) {
				getList().setSelectedValue(node, true);
			}
			return node;
		}
		
		@Override
		public Pair<Integer, String> getValue() {
			XLSItem node = getSelectedObject();
			if (node != null) {
				return new Pair<Integer, String>(node.getIntID(), node.getName());
			}
			return null;
		}		
	};
	
	
	static class ItemTemplateList extends G2DList<XLSItem>
	{
		public ItemTemplateList() 
		{
			super(Studio.getInstance().getObjectManager().getObjects(XLSItem.class));
			super.setLayoutOrientation(JList.VERTICAL);
		}
	}
	
	
	public static class XLSItemSelectAdapter implements CellEditAdapter<Object>
	{
		@Override
		public Class<Object> getType() 
		{
			return Object.class;
		}
		
		private Pair<Integer, String> pair_type_ = new Pair<Integer, String>();
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field) 
		{
			if (field.getName().equals("item_template_id")) 
			{	
				if (field.getType()== pair_type_.getClass())
				{
					Pair<Integer, String> value = (Pair<Integer, String>)fieldValue;
					
					XLSItemSelectCellEdit2 edit 
					= new XLSItemSelectCellEdit2(owner.getComponent(), 
							Studio.getInstance().getObjectManager().getObject(XLSItem.class, (value==null)? 0 : value.getKey()));
					edit.setValue((Pair<Integer, String>)fieldValue);
					edit.showDialog();
					return edit;					
				}
				else
				{
					XLSItemSelectCellEdit edit 
					= new XLSItemSelectCellEdit(owner.getComponent(), 
							Studio.getInstance().getObjectManager().getObject(XLSItem.class, (Integer)fieldValue));
					edit.setValue((Integer)fieldValue);
					edit.showDialog();
					return edit;
				}
			}

			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
										Object fieldValue, Field field, DefaultTableCellRenderer src) 
		{
			if (field.getName().equals("item_template_id") && fieldValue!=null)
			{
				int id;
				
				if (field.getType()== pair_type_.getClass())
				{
					Pair<Integer, String> value = (Pair<Integer, String>)fieldValue;
					id = (value==null)? 0 : value.getKey();					
				}
				else
				{
					id = (Integer)fieldValue;
				}
				
				XLSItem node = Studio.getInstance().getObjectManager().getObject(XLSItem.class, id);
				if (node != null) 
				{
					src.setText(node.toString());
					src.setIcon(node.getIcon(false));
				}

				return src;
			}

			return null;
		}

		@Override
		public boolean fieldChanged(Object editObject, Object fieldValue, Field field) 
		{
			return false;
		}

		@Override
		public String getCellText(Object editObject, Field field, Object fieldSrcValue)
		{
			return null;
		}

		@Override
		public Object getCellValue(Object editObject, PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue) 
		{
			return null;
		}
	}	

}
