package com.cell.rpg.scene;


import com.cell.rpg.instance.zones.ability.InstanceZoneMonster;
import com.cell.rpg.instance.zones.ability.InstanceZoneUnitKillAction;
import com.cell.rpg.instance.zones.ability.InstanceZoneUnitVisible;
import com.cell.rpg.quest.ability.QuestAccepter;
import com.cell.rpg.quest.ability.QuestPublisher;
import com.cell.rpg.scene.ability.ActorAuctioneer;
import com.cell.rpg.scene.ability.ActorBank;
import com.cell.rpg.scene.ability.ActorDropItem;
import com.cell.rpg.scene.ability.ActorFlag;
import com.cell.rpg.scene.ability.ActorJobTrainer;
import com.cell.rpg.scene.ability.ActorPathStart;
import com.cell.rpg.scene.ability.ActorPostOffice;
import com.cell.rpg.scene.ability.ActorQuestDropItem;
import com.cell.rpg.scene.ability.ActorSellItem;
import com.cell.rpg.scene.ability.ActorSkillTrainer;
import com.cell.rpg.scene.ability.ActorTalk;
import com.cell.rpg.scene.ability.ActorTransportCraft;
import com.g2d.annotation.Property;



public class Actor extends SceneSprite
{
	final public String	template_unit_id;
	
	/** NPC闲话  */
	@Property("NPC闲话")
	public	String		npc_talk;
	
	@Property("警戒范围")
	public int			look_range		= 300;
	
	public Actor(String id, String template_unit_id) 
	{	
		super(id);
		this.template_unit_id = template_unit_id;
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[] {
				// quests
				QuestAccepter.class,
				QuestPublisher.class,
				// flags
				ActorTalk.class,
				ActorSellItem.class,
				ActorBank.class,
				ActorAuctioneer.class,
				ActorPostOffice.class,
				ActorJobTrainer.class,
				ActorSkillTrainer.class,
				ActorTransportCraft.class,
				// other
				ActorDropItem.class,
				ActorPathStart.class,
				ActorQuestDropItem.class,
				ActorFlag.class,
				// instance zone
				InstanceZoneUnitVisible.class,
				InstanceZoneUnitKillAction.class,
				InstanceZoneMonster.class,
			};
	}
	
	@Override
	public Class<com.cell.rpg.scene.script.entity.Actor> getTriggerObjectType() {
		return com.cell.rpg.scene.script.entity.Actor.class;
	}

}
