package com.g2d.studio.rpg;

import java.awt.Component;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;

import com.cell.CUtil;
import com.cell.rpg.formula.anno.MethodSynthetic;
import com.cell.rpg.formula.anno.ObjectMethod;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;

public class MethodListCellEdit extends JComboBox implements PropertyCellEdit<Method>
{
	public MethodListCellEdit(Collection<Method> methods) {
		super(CUtil.sort(methods.toArray(new Method[methods.size()]), new Sorter()));
		setRenderer(new CellRender());
	}
	
	public MethodListCellEdit(Map<String, Method> methods, String method_name) {
		this(methods.values());
		Method mt = methods.get(method_name);
		if (mt!=null) {
			this.setSelectedItem(mt);
		}
	}
	
	@Override
	public Method getValue() {
		try{
			Method method = (Method)getSelectedItem();
			if (method==null) {
				method = (Method)getModel().getElementAt(0);
			}
			return method;
		} catch (Exception err){
			return null;
		}
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		return this;
	}
	
	static class Sorter implements CUtil.ICompare<Method, Method>
	{
		@Override
		public int compare(Method a, Method b) {
			return CUtil.getStringCompare().compare(a.getName(), b.getName());
		}
	}
	
	static class CellRender extends DefaultListCellRenderer
	{
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			Method method = (Method)value;
			if (method != null) {
				MethodSynthetic synthetic = method.getAnnotation(MethodSynthetic.class);
				String text = "";
				if (synthetic != null) {
					text += "[复合]";
				}
				text += method.getReturnType().getSimpleName() + " " + method.getName() + "(";
				Class<?>[] params = method.getParameterTypes();
				for (int i=0; i<params.length; i++) {
					text += params[i].getSimpleName();
					if (i!=params.length-1) {
						text += ",";
					}
				}
				text += ")";
				ObjectMethod comment = method.getAnnotation(ObjectMethod.class);
				if (comment != null) {
					text += " - " + comment.value();
				}
				super.setText(text);
			}
			return this;
		}
	}
	
	
	
	
}
