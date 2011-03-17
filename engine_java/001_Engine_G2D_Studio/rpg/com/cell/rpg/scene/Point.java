package com.cell.rpg.scene;

import java.util.ArrayList;

import com.cell.rpg.instance.zones.ability.InstanceZoneUnitVisible;

public class Point extends SceneUnit
{	
	public int 		color;
	public float 	alpha;

	public ArrayList<String> next_ids = new ArrayList<String>();
	
	public Point(String id, int x, int y) 
	{
		super(id);
		this.x = x;
		this.y = y;
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[]{
				InstanceZoneUnitVisible.class,};
	}
	
	@Override
	public Class<com.cell.rpg.scene.script.entity.Point> getTriggerObjectType() {
		return com.cell.rpg.scene.script.entity.Point.class;
	}
	
}
