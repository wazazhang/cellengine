package com.cell.rpg.scene;


import com.cell.rpg.instance.zones.ability.InstanceZoneUnitVisible;
import com.cell.rpg.quest.ability.QuestAccepter;
import com.cell.rpg.quest.ability.QuestPublisher;
import com.cell.rpg.scene.ability.RegionMapBlock;
import com.cell.rpg.scene.ability.RegionPlayerEnter;
import com.cell.rpg.scene.ability.RegionSpawnNPC;
import com.cell.rpg.scene.ability.RegionSpawnTrezuro;



public class Region extends SceneUnit
{
	public int 		width;
	public int 		height;
	public int 		color;
	public float 	alpha;
	
	public Region(String id, int x, int y, int width, int height) 
	{
		super(id);
		this.x		= x;
		this.y		= y;
		this.width	= width;
		this.height	= height;
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[]{
				RegionPlayerEnter.class,
				RegionSpawnNPC.class,
				RegionSpawnTrezuro.class,
				RegionMapBlock.class,
				QuestAccepter.class,
				QuestPublisher.class,
				InstanceZoneUnitVisible.class,
			};
	}
	
//	@Override
//	public Class<com.cell.rpg.scene.script.entity.Region> getTriggerObjectType() {
//		return com.cell.rpg.scene.script.entity.Region.class;
//	}

}
