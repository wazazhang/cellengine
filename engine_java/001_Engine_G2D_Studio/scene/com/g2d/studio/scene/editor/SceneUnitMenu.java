package com.g2d.studio.scene.editor;

import javax.swing.JOptionPane;

import com.g2d.display.ui.Menu;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.studio.scene.units.SceneUnitTag;

public class SceneUnitMenu extends Menu
{
	protected SceneUnitTag<?>	unit;
	protected SceneEditor		view;
	
	MenuItemButton item_rename		= new MenuItemButton("重命名");
	MenuItemButton item_properties	= new MenuItemButton("属性");
	MenuItemButton item_delete		= new MenuItemButton("删除");
	
	public SceneUnitMenu(SceneEditor view, SceneUnitTag<?> unit, Object ... objects) 
	{
		super(100, objects);
		
		this.unit = unit;
		this.view = view;
		
		addMenuItem(item_rename);
		addMenuItem(item_properties);
		addMenuItem(item_delete);
	}
	
	@Override
	protected void onClickMenuItem(MenuItem item) {
		if (item == item_rename) {
			String new_name = JOptionPane.showInputDialog("input name", unit.getGameUnit().getID());
			if (new_name!=null) {
				if (!unit.getGameUnit().setID(view.getGameScene().getWorld(), new_name)){
					JOptionPane.showMessageDialog(view, "bad name or duplicate !");
				} else {
					unit.getUnit().name = unit.getGameUnit().getID()+"";
				}
				view.refreshAll();
			}
		}
		else if (item == item_properties) {
			DisplayObjectEditor<?> editor = unit.getEditorForm();
			editor.setCenter();
			editor.setAlwaysOnTop(true);
			editor.setVisible(true);
		} 
		else if (item == item_delete) {
			view.removeUnit(unit);
		}
	}
}
