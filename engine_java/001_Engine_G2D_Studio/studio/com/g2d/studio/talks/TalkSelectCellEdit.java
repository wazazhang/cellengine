package com.g2d.studio.talks;

import java.awt.Component;
import java.awt.Window;

import javax.swing.JLabel;

import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;

public class TalkSelectCellEdit extends TalkSelectDialog implements PropertyCellEdit<String>
{
	private static final long serialVersionUID = 1L;
	
	JLabel	edit_label 	= new JLabel();
	
	public TalkSelectCellEdit(Window owner, TalkFile dv) {
		super(owner, dv);
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		TalkFile talk = getSelectedObject();
		if (talk!=null) {
			edit_label.setText(talk.talk_file_name);	
			edit_label.setIcon(talk.getListIcon(false));
		} else {
			edit_label.setText("");	
			edit_label.setIcon(null);
		}
		return edit_label;
	}

	@Override
	public String getValue() {
		TalkFile talk = getSelectedObject();
		if (talk!=null) {
			return talk.talk_file_name;
		}
		return null;
	}
	
	
}
