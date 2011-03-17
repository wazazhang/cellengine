package com.g2d.studio.old.swing;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JComboBox;

import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSRow;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.old.Studio;

public class XLSRowListComboBox extends JComboBox implements PropertyCellEdit<XLSRow>
{
	private static final long serialVersionUID = 1L;

	ObjectPropertyPanel panel;
	
	public XLSRowListComboBox(XLSFile xls_file) 
	{
		super(new Vector<XLSRow>(Studio.getInstance().getXLSPrimaryRows(xls_file, XLSRow.class)));
		this.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				panel.fireEditingStopped();
			}
		});
	}
	
	public void beginEdit(ObjectPropertyPanel panel) {
		this.panel = panel;
	}
	
	public Component getComponent() {
		return this;
	}
	
	public XLSRow getValue() {
		Object item = getSelectedItem();
		if (item != null) {
			XLSRow row = (XLSRow)item;
			return row;
		}
		return null;
	}
}
