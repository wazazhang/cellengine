package com.g2d.studio.quest.items;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;

public class QuestItemSelectCellEditComboBox extends JComboBox implements PropertyCellEdit<Integer>
{
	JLabel cell_edit_component = new JLabel();
	
	public QuestItemSelectCellEditComboBox(int quest_id) {
		super(Studio.getInstance().getQuestManager().getQuest(quest_id).getQuestItemManager().getAllNodes());
		super.setRenderer(new CellRender());
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		QuestItemNode node = (QuestItemNode)getSelectedItem();
		cell_edit_component.setText(node+"");
		return cell_edit_component;
	}
	
	@Override
	public Integer getValue() {
		QuestItemNode node = (QuestItemNode)getSelectedItem();
		if (node != null) {
			return node.getIntID();
		}
		return -1;
	}
	
	class CellRender extends DefaultListCellRenderer
	{
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			QuestItemNode node = (QuestItemNode)value;
			if (node != null) {
				super.setIcon(node.getIcon(false));
				super.setText(node.toString());
			}
			return this;
		}
	}
}
