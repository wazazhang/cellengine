package com.g2d.studio.quest;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;

public class QuestSelectCellEditComboBox extends JComboBox implements PropertyCellEdit<Integer>
{
	JLabel cell_edit_component = new JLabel();
	
	public QuestSelectCellEditComboBox() {
		this(0);
	}
	
	public QuestSelectCellEditComboBox(Integer quest_id) {
		super(Studio.getInstance().getQuestManager().getQuests().toArray(
		new QuestNode[Studio.getInstance().getQuestManager().getQuests().size()]));
		super.setRenderer(new CellRender());
		QuestNode node = Studio.getInstance().getQuestManager().getQuest(quest_id);
		if (node != null) {
			this.setSelectedItem(node);
		}
	}
	
	public void setValue(Integer quest_id) {
		QuestNode node = Studio.getInstance().getQuestManager().getQuest(quest_id);
		if (node != null) {
			this.setSelectedItem(node);
		}
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		QuestNode node = (QuestNode)getSelectedItem();
		cell_edit_component.setText(node+"");
		return cell_edit_component;
	}
	
	@Override
	public Integer getValue() {
		QuestNode node = (QuestNode)getSelectedItem();
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
			QuestNode node = (QuestNode)value;
			if (node != null) {
				super.setIcon(node.getIcon(false));
				super.setText(node.toString());
			}
			return this;
		}
	}
}
