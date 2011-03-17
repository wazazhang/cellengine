package com.g2d.studio.old.swing;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.old.scene.FormSceneViewer;
import com.g2d.studio.old.scene.SceneUnitTag;

public class SceneUnitListComboBox extends JComboBox implements PropertyCellEdit<String>
{
	private static final long serialVersionUID = 1L;

	ObjectPropertyPanel panel;
	
	public SceneUnitListComboBox(FormSceneViewer scene_viewer, Class<? extends SceneUnitTag<?>> cls) 
	{
		for (SceneUnitTag<?> unit : scene_viewer.getViewObject().getScene().getWorld().getChildsSubClass(cls)) {
			super.addItem(unit);
		}
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
	
	public String getValue() {
		Object item = getSelectedItem();
		if (item != null) {
			return item.toString();
		}
		return null;
	}
}
