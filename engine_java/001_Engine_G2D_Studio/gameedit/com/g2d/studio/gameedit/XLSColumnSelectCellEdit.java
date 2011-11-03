package com.g2d.studio.gameedit;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;

import com.cell.CUtil;
import com.cell.xls.XLSColumns;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;

public class XLSColumnSelectCellEdit extends JComboBox implements PropertyCellEdit<String>
{
	XLSColumns columns;
	
	public XLSColumnSelectCellEdit(XLSColumns columns) {
		super(CUtil.sort(columns.getColumns().toArray(new String[columns.size()]), CUtil.getStringCompare()));
		super.setRenderer(new CellRender());
		this.columns = columns;
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		return this;
	}
	
	@Override
	public String getValue() {
		Object ret = getSelectedItem();
		if (ret==null) {
			return null;
		} else {
			return ret.toString();
		}
	}

	class CellRender extends DefaultListCellRenderer
	{
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus)
		{
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			
			String column = (String)value;
			
			String desc = column + " - " + columns.getDesc(column);
	
			this.setText(desc);
			
			return this;
		}
	}
}
