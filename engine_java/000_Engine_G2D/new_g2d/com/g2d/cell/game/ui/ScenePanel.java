package com.g2d.cell.game.ui;

import com.g2d.cell.game.Scene;
import com.g2d.display.ui.Container;
import com.g2d.display.ui.Panel;
import com.g2d.display.ui.ScrollBar;

public class ScenePanel extends Container
{
	Scene 			scene;
	ScrollBar		vScroll;
	ScrollBar		hScroll;
	
	public ScenePanel()
	{
		vScroll = ScrollBar.createVScroll(Panel.DEFAULT_SCROLL_BAR_SIZE);
		hScroll = ScrollBar.createHScroll(Panel.DEFAULT_SCROLL_BAR_SIZE);
		this.addChild(vScroll);
		this.addChild(hScroll);
	}
	
	public ScenePanel(Scene scene) {
		this();
		this.setScene(scene);
	}
	
	public void setScene(Scene scene) {
		if (this.scene != null) {
			this.removeChild(scene, true);
		}
		this.scene = scene;
		this.addChild(scene);
		this.setSize(scene.getWidth(), scene.getHeight());
	}
	
	public Scene getScene() {
		return scene;
	}
	
	@Override
	public void update() 
	{
		int view_x		= layout.BorderSize;
		int view_y		= layout.BorderSize;
		int view_w		= getWidth() -(layout.BorderSize<<1);
		int view_h		= getHeight()-(layout.BorderSize<<1);
		int camera_w	= view_w -vScroll.getWidth();
		int camera_h	= view_h -hScroll.getHeight();
		int world_w		= scene.getWorld().getWidth();
		int world_h		= scene.getWorld().getHeight();
		double camera_x	= hScroll.getValue();
		double camera_y	= vScroll.getValue();
		
		vScroll.setSize(vScroll.size, view_h - hScroll.size);
		hScroll.setSize(view_w - vScroll.size, hScroll.size);
		vScroll.setLocation(view_x + view_w - vScroll.size, view_y);
		hScroll.setLocation(view_x, view_y + view_h - hScroll.size);

		hScroll.setMax(world_w);
		vScroll.setMax(world_h);
		hScroll.setValue(camera_x, camera_w);
		vScroll.setValue(camera_y, camera_h);
		
		scene.setLocation(view_x, view_y);
		scene.setSize(camera_w, camera_h);
		scene.locateCamera(camera_x, camera_y);

		super.update();
	}
	
	public void locationCameraCenter(double x, double y) {
		hScroll.setValue(x-scene.getCameraWidth()/2);
		vScroll.setValue(y-scene.getCameraHeight()/2);
	}
	
	public void locationCamera(double x, double y) {
		hScroll.setValue(x);
		vScroll.setValue(y);
	}
	
	public boolean isCatchedScene() {
		return scene!= null && getChildAtPos(getMouseX(), getMouseY()) == scene;
	}
}
