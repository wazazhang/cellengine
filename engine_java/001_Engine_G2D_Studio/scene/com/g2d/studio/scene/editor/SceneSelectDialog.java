package com.g2d.studio.scene.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.g2d.awt.util.AbstractOptionDialog;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;

public class SceneSelectDialog extends AbstractOptionDialog<SceneNode> implements PropertyCellEdit<Integer>
{
	static int last_selected_scene_id;
	
	JLabel cell_edit_comp = new JLabel();
	
	G2DList<SceneItem> scene_panel = new G2DList<SceneItem>();
	
	public SceneSelectDialog(Component comp) {
		this(comp, last_selected_scene_id);
	}
	
	public SceneSelectDialog(Component comp, Integer default_scene) {
		this(comp, default_scene, false);
	}
	
	public SceneSelectDialog(Component comp, boolean only_zone) {
		this(comp, last_selected_scene_id, only_zone);
	}
	
	public SceneSelectDialog(Component comp, Integer default_scene, boolean only_zone) {
		super(comp);
		super.setTitle("选择一个场景");
		super.setSize(700, 400);
		Vector<SceneItem> items = new Vector<SceneItem>();
		SceneItem selected = null;
		for (SceneNode sn : Studio.getInstance().getSceneManager().getAllScenes()) {
			if (!only_zone || sn.getData().is_instance_zone) {
				SceneItem item = new SceneItem(sn);
				items.add(item);
				if (default_scene != null && 
					default_scene.equals(item.node.getIntID())) {
					selected = item;
				}
			}
		}
		scene_panel.setListData(items);
		scene_panel.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		scene_panel.setVisibleRowCount(0);
		if (selected != null){
			scene_panel.setSelectedValue(selected, true);
		}
		this.add(new JScrollPane(scene_panel), BorderLayout.CENTER);
	}
	
	@Override
	protected boolean checkOK() {
		return true;
	}
	
	@Override
	protected SceneNode getUserObject(ActionEvent e) {
		SceneItem item = scene_panel.getSelectedItem();
		if (item != null) {
			return item.node;
		}
		return null;
	}
	
//	@Override
//	protected Object[] getUserObjects()
//	{
//		Object[] objs = new Object[1];				
//		objs[0] = getUserObject();				
//		return objs;
//	}	
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		SceneNode node = getSelectedObject();
		if (node != null) {
			cell_edit_comp.setText(node.getListName());
		}
		return cell_edit_comp;
	}
	
	@Override
	public Integer getValue() {
		SceneNode node = getSelectedObject();
		if (node != null) {
			return node.getIntID();
		}
		return null;
	}

	
	
	class SceneItem extends JLabel implements G2DListItem
	{
		SceneNode node;
		
		public SceneItem(SceneNode node) {
			this.node = node;
			this.setIcon(node.getIcon(false));
			this.setText(node.getListName());
		}
		
		@Override
		public String getListName() {
			return node.getListName();
		}
		
		@Override
		public Component getListComponent(JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			return null;
		}
	
		@Override
		public ImageIcon getListIcon(boolean update) {
			return node.getListIcon(update);
		}
	}
}
