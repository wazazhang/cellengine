package com.g2d.studio.old.swing;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JComboBox;

import com.cell.rpg.xls.XLSFile;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.old.Studio;

public class XLSFileListComboBox extends JComboBox implements PropertyCellEdit<XLSFile>
{
	private static final long serialVersionUID = 1L;

	ObjectPropertyPanel panel;
	
	public XLSFileListComboBox() 
	{
		super(new Vector<XLSFile>(Studio.getInstance().getXLSFiles()));
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
	
	public XLSFile getValue() {
		Object item = getSelectedItem();
		if (item != null) {
			XLSFile xls = (XLSFile)item;
			return xls;
		}
		return null;
	}
}
