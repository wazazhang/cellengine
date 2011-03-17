package com.g2d.studio.old.scene;

import javax.swing.JOptionPane;

import com.g2d.display.ui.Menu;
import com.g2d.display.ui.Menu.MenuItem;
import com.g2d.editor.DisplayObjectEditor;

public class UnitMenu extends Menu
{
	protected SceneUnitTag<?> unit;
	protected FormSceneViewer view;
	MenuItemButton item_rename		= new MenuItemButton("重命名");
	MenuItemButton item_properties	= new MenuItemButton("属性");
	MenuItemButton item_delete		= new MenuItemButton("删除");
	
	public UnitMenu(FormSceneViewer view, SceneUnitTag<?> unit, Object ... objects) 
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
			String new_name = JOptionPane.showInputDialog("input name", unit.getSceneUnit().getID());
			if (new_name!=null) {
				if (!unit.getSceneUnit().setID(view.scene.getWorld(), new_name)){
					JOptionPane.showMessageDialog(view, "bad name or duplicate !");
				}
				view.refreshAll();
			}
		}
		else if (item == item_properties) {
			DisplayObjectEditor<?> editor = unit.getSceneUnit().getEditorForm();
			editor.setCenter();
			editor.setAlwaysOnTop(true);
			editor.setVisible(true);
		} 
		else if (item == item_delete) {
			view.removeUnit(unit);
		}
	}
}
