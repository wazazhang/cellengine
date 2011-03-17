package com.cell.rpg.scene.ability;

import java.util.ArrayList;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;

@Property({"[单位能力] 技能训练师", "可以训练遗忘技能"})
public class ActorSkillTrainer extends AbstractAbility implements IActorAbility
{
	private static final long serialVersionUID = 1L;
	
	/** NPC闲话  */
	@Property("NPC闲话")
	public	String		npc_talk;
	
	@Property("可以训练(遗忘)的技能")
	public ArrayList<Integer> skills_id = new ArrayList<Integer>();
	
	@Override
	public String getTalk() {
		return npc_talk;
	}
	
	@Override
	public boolean isMultiField() {
		return true;
	}
}
