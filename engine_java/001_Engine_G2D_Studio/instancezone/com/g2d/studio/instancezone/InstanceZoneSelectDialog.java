package com.g2d.studio.instancezone;

import java.awt.Component;

import javax.swing.JLabel;

import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectSelectDialog;
import com.g2d.studio.quest.QuestList;
import com.g2d.studio.quest.QuestNode;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListSelectDialog;

@SuppressWarnings("serial")
public class InstanceZoneSelectDialog extends G2DListSelectDialog<InstanceZoneNode> implements PropertyCellEdit<Integer>
{
	JLabel cell_edit_component = new JLabel();
	
	/**
	 * @param not_transient 是否排除所有临时任务
	 */
	public InstanceZoneSelectDialog(Component owner, Integer dv) {
		super(owner, 
				Studio.getInstance().getInstanceZoneManager().getNodes(), 
				Studio.getInstance().getInstanceZoneManager().getNode(dv));
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		InstanceZoneNode node = getSelectedObject();
		cell_edit_component.setText(node+"");
		return cell_edit_component;
	}
	
	@Override
	public Integer getValue() {
		InstanceZoneNode node = getSelectedObject();
		if (node != null) {
			return node.getIntID();
		}
		return -1;
	}
}

