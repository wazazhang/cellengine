package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import com.g2d.awt.util.AbstractOptionDialog;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.swing.G2DList;

public class ObjectSelect<T extends ObjectNode<?>> extends AbstractOptionDialog<T>
{
	private static final long 
			serialVersionUID = 1L;
	
	private static final HashMap<Class<?>, Object> 
			last_selected_map = new HashMap<Class<?>, Object>();
	
	protected Vector<T>		list_data = new Vector<T>();
	protected G2DList<T>	list;
	protected JLabel		cell_edit_component = new JLabel();

	public ObjectSelect(Component owner, Class<T> type, int wcount, Object default_value, ObjectSelectFilter<T> filter) 
	{
		super(owner);
		super.setTitle(type.getCanonicalName());
		
		this.list_data = getObjects(type, filter);
		this.list = new G2DList<T>();
		this.list.setListData(list_data);
		this.list.setLayoutOrientation(JList.HORIZONTAL_WRAP);		
		this.list.setVisibleRowCount(0);

		super.add(new JScrollPane(list), BorderLayout.CENTER);
		
		if (default_value == null) {
			default_value = last_selected_map.get(type);
		}
		
		if (type.isInstance(default_value)) {
			this.setSelectedItem(type.cast(default_value));
		}
		else if (default_value instanceof String) {
			this.setSelected((String)default_value);
		}
		else if (default_value instanceof Integer) {
			this.setSelected((Integer)default_value);
		}
		else if (int.class.isInstance(default_value)) {
			this.setSelected((Integer)default_value);
		}
	}
	
	@Override
	protected boolean checkOK() {
		return true;
	}

	public void setSelectedItem(T obj) {
		list.setSelectedValue(obj, true);
	}
	
	protected void setSelected(String obj) {
		if (obj != null) {
			for (T t : list_data) {
				if (t.getID().equals(obj)) {
					this.list.setSelectedValue(t, true);
				}
			}
		}
	}
	protected void setSelected(Integer obj) {
		if (obj != null) {
			for (T t : list_data) {
				if (t.getIntID() == obj) {
					this.list.setSelectedValue(t, true);
				}
			}
		}
	}
	
	public G2DList<T> getList() {
		return list;
	}
	
	@Override
	protected T getUserObject(ActionEvent e) {
		T obj = list.getSelectedItem();
		if (obj != null) {		
			cell_edit_component.setText(obj.getName());
			cell_edit_component.setIcon(obj.getIcon(false));
			last_selected_map.put(obj.getClass(), obj);
		}
		return obj;
	}

	public Component getComponent(ObjectPropertyEdit panel) {
		return cell_edit_component;
	}
	
	
	@Override
	public T showDialog() {
		new Thread(){
			public void run() {
				try {
					Thread.sleep(100);
					int index = list.getSelectedIndex();
					list.scrollRectToVisible(list.getCellBounds(index, index));
				} catch (Exception err) {}
			}
		}.start();
		return super.showDialog();
	}
	
	public static<T extends ObjectNode<?>> Vector<T> getObjects(Class<T> object_type, ObjectSelectFilter<T> filter)
	{
		Vector<T> src = Studio.getInstance().getObjectManager().getObjects(object_type);
		if (filter == null) {
			return src;
		}
		Vector<T> ret = new Vector<T>(src.size());
		for (T d : src) {
			if (filter.accept(d)) {
				ret.add(d);
			}
		}
		return ret;
	}
}
