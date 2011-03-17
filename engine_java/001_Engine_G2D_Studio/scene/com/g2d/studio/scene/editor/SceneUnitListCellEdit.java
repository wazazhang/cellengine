package com.g2d.studio.scene.editor;

import java.awt.Component;

import javax.swing.JComboBox;

import com.cell.rpg.scene.Scene;
import com.cell.rpg.scene.SceneUnit;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.scene.units.SceneUnitTag;

public class SceneUnitListCellEdit extends JComboBox implements PropertyCellEdit<String>
{
	private static final long serialVersionUID = 1L;

	ObjectPropertyEdit panel;
	
	public SceneUnitListCellEdit(Scene scene, Class<? extends SceneUnit> cls) 
	{
		for (SceneUnit unit : scene.scene_units) {
			if (cls.isInstance(unit)) {
				super.addItem(unit.getName());
			}
		}
	}
	
	public Component getComponent(ObjectPropertyEdit panel) {
		this.panel = panel;
		return this;
	}
	
	public String getValue() {
		Object item = getSelectedItem();
		return item + "";
	}
}
