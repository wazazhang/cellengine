package com.g2d.studio.sound;

import java.awt.Component;

import javax.swing.JLabel;

import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.fileobj.FileObjectSelectDialog;
import com.g2d.studio.swing.G2DListSelectDialog;

public class SoundSelectDialog extends FileObjectSelectDialog<SoundFile> implements PropertyCellEdit<String>
{
	private static final long serialVersionUID = 1L;
	
	JLabel label = new JLabel();

	public SoundSelectDialog(Component owner, String def)
	{
		super(owner, 
				Studio.getInstance().getSoundManager().getList(), 
				Studio.getInstance().getSoundManager().getSound(def));
		super.setTitle("选择一个声音");
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		if (getSelectedObject()!=null) {
			label.setIcon(getSelectedObject().getListIcon(false));
			label.setText(getSelectedObject().getListName());
		} else {
			label.setText("");
		}
		return label;
	}

	@Override
	public String getValue() {
		return label.getText();
	}
}
