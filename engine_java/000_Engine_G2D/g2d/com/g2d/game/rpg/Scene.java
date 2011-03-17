package com.g2d.game.rpg;

import java.awt.Graphics2D;

import com.g2d.Version;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.InteractiveObject;

public class Scene extends InteractiveObject 
{
	private static final long serialVersionUID = Version.VersionG2D;
	
//	---------------------------------------------------------------------------------------------------------------------------
//	
	protected SceneMap			world;
	
	protected double 			camera_x;
	protected double 			camera_y;
	
//	---------------------------------------------------------------------------------------------------------------------------

	public Scene() {
		this.setSize(640, 480);
	}
	
	public Scene(SceneMap world) {
		this.setSize(640, 480);
		this.world = world;
		super.addChild(world);
	}
	
	/**
	 * 请勿将世界精灵添加到该层。
	 * @see getWorld().addChild(child);
	 */
	@Override @Deprecated
	public boolean addChild(DisplayObject child) {
		if (child instanceof SceneMap && world==null){
			this.world = (SceneMap)child;
			return super.addChild(child);
		}
		throw new IllegalStateException("can not add a custom child in " + getClass().getName() + " !");
	}
	
	/**
	 * 请勿将世界精灵添加到该层。
	 * @see getWorld().removeChild(child)
	 */
	@Override @Deprecated
	public boolean removeChild(DisplayObject child) {
		throw new IllegalStateException("can not remove a custom child in " + getClass().getName() + " !");
	}

	public void setWorld(SceneMap world) {
		addChild(world);
	}
	
	public SceneMap getWorld() {
		return world;
	}
	
//	-----------------------------------------------------------------------------------------------------------
//	camera refer
	
	public double getCameraX() {
		return camera_x;
	}
	public double getCameraY() {
		return camera_y;
	}
	public int getCameraWidth() {
		return super.getWidth();
	}
	public int getCameraHeight() {
		return super.getHeight();
	}
	public void setCameraSize(int w, int h) {
		setSize(w, h);
	}
	
	public void locateCamera(double x, double y) 
	{
		if (camera_x != x || camera_y != y)		
		{
			camera_x = x ;
			camera_y = y ;
			
			if (camera_x < world.local_bounds.x) {
				camera_x = world.local_bounds.x;
			}
			if (camera_x > world.local_bounds.width - local_bounds.width) {
				camera_x = world.local_bounds.width - local_bounds.width;
			}
			if (camera_y < world.local_bounds.y) {
				camera_y = world.local_bounds.y;
			}
			if (camera_y > world.local_bounds.height - local_bounds.height) {
				camera_y = world.local_bounds.height - local_bounds.height;
			}

			world.x = -camera_x;
			world.y = -camera_y;
			
			onCameraChanged(camera_x, camera_y, local_bounds.width, local_bounds.height);
		}
	}
	
	public void locateCameraCenter(double x, double y) 
	{
		locateCamera(x - getWidth()/2, y - getHeight()/2);
	}
	
	public void setSize(int w, int h)
	{
		int pw = local_bounds.width;
		int ph = local_bounds.height;
		
		if (world != null) 
		{
			if (w > world.local_bounds.width) {
				w = world.local_bounds.width;
			}
			if (h > world.local_bounds.height) {
				h = world.local_bounds.height;
			}
		}
		
		super.setSize(w, h);
		
		if (pw != local_bounds.width || ph != local_bounds.height) {
			onCameraChanged(camera_x, camera_y, local_bounds.width, local_bounds.height);
		}
	}

	protected void onCameraChanged(double cx, double cy, double cw, double ch){}
	
//	-----------------------------------------------------------------------------------------------------------
	
	public void added(DisplayObjectContainer parent) {}
	
	public void removed(DisplayObjectContainer parent) {}

	public void update() {}
	
	public void render(Graphics2D g)
	{
		g.clip(local_bounds);
	}

//	-----------------------------------------------------------------------------------------------------------
	
}
