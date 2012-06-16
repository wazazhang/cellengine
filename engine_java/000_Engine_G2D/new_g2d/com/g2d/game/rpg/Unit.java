package com.g2d.game.rpg;

import com.cell.CMath;
import com.g2d.BufferedImage;
import com.g2d.display.Sprite;

public abstract class Unit extends Sprite
{
//	------------------------------------------------------------------------------------------------------------------------------------

	private transient SceneMap	owner_world;
	private transient Scene 	owner_scene;

	private Object				identify;

//	/** 是否只在camera范围内可视 */
//	public boolean		is_visible_camera_only	= true;
	
//	------------------------------------------------------------------------------------------------------------------------------------

	public boolean setID(SceneMap world, Object new_id) {
		if (new_id == null || world.containsUnitWithID(new_id)) {
			//throw new IllegalArgumentException("can not set unit name '" + name + "' !");
			return false;
		} else {
			if (this.identify != null && world==owner_world) {
				world.tryChangeUnitID(this, new_id);
			}
			this.identify = new_id;
			return true;
		}
	}
	
	public Object getID(){
		return identify;
	}
	
	public boolean isInCamera()
	{
		if (getOwnerScene() != null) 
		{
			if (CMath.intersectRect2(
					(float)getOwnerScene().camera_x, 
					(float)getOwnerScene().camera_y, 
					getOwnerScene().local_bounds.width, 
					getOwnerScene().local_bounds.height,
					(float)x + local_bounds.x,
					(float)y + local_bounds.y, 
					local_bounds.width, 
					local_bounds.height)) {
				return true;
			}
		}
		return false;
	}
	
	void setOwnerWorld(SceneMap owner_world) {
		this.owner_world = owner_world;
	}

	void setOwnerScene(Scene owner_scene) {
		this.owner_scene = owner_scene;
	}

	public SceneMap getOwnerWorld() {
		return owner_world;
	}

	public Scene getOwnerScene() {
		return owner_scene;
	}

	@Override
	public void update() {
		super.update();
		if (is_visible_camera_only() && getOwnerScene() != null && !isInCamera()) {
			this.visible = false;
		} else {
			this.visible = true;
		}
	}
	
	protected boolean is_visible_camera_only() {
		return true;
	}

}
