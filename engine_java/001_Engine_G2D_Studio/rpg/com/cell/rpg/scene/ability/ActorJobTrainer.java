package com.cell.rpg.scene.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;

@Property({"[单位能力] 职业训练师", "可以训练遗忘职业技能"})
public class ActorJobTrainer  extends AbstractAbility implements IActorAbility
{
	private static final long serialVersionUID = 1L;

	/** 职业类型 */
	@Property("职业类型")
	public	String		job_type = "n/a";
	
	/** NPC闲话  */
	@Property("NPC闲话")
	public	String		npc_talk;
	
	@Override
	public String getTalk() {
		return npc_talk;
	}
	
	@Override
	public boolean isMultiField() {
		return true;
	}
}