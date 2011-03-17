package com.g2d.studio.old.scene;

import java.awt.Color;
import java.awt.Shape;
import java.util.ArrayList;

import com.cell.rpg.entity.Unit;
import com.g2d.display.ui.Menu;

public interface SceneUnitTag<T extends Unit>
{
	public com.g2d.game.rpg.Unit	getSceneUnit();
	public FormSceneViewer			getViewer();
	
	public T			getUnit();
	
//	---------------------------------------------------------------------------------------
//	io
	public void 		onRead(T unit);
	public T 			onWrite();
	
	public void			onReadComplete(ArrayList<Unit> all);
	public void			onWriteComplete(ArrayList<Unit> all);
	
//	---------------------------------------------------------------------------------------
//	edit
	public Menu 		getEditMenu();
	
	
//	---------------------------------------------------------------------------------------
//	snapshot
	public Color 		getSnapColor();
	public Shape 		getSnapShape();

//	---------------------------------------------------------------------------------------
}
