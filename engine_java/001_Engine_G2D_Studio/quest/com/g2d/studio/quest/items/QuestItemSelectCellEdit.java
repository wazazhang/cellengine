package com.g2d.studio.quest.items;

import java.awt.Component;
import java.awt.Window;

import javax.swing.JLabel;

import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.quest.QuestNode;
import com.g2d.studio.swing.G2DListSelectDialog;

public class QuestItemSelectCellEdit extends G2DListSelectDialog<QuestItemNode> implements PropertyCellEdit<Integer>
{
	JLabel cell_edit_component = new JLabel();
	
	public QuestItemSelectCellEdit(Window owner, int quest_id) {
		super(owner, new QuestNode.QuestItemList(quest_id), null);
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		QuestItemNode node = getSelectedObject();
		cell_edit_component.setText(node+"");
		return cell_edit_component;
	}
	
	@Override
	public Integer getValue() {
		QuestItemNode node = getSelectedObject();
		if (node != null) {
			return node.getIntID();
		}
		return -1;
	}
	
	
}
