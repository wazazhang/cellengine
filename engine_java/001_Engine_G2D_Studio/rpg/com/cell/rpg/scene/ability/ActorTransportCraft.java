package com.cell.rpg.scene.ability;

import com.cell.rpg.scene.EUnitType;
import com.g2d.annotation.Property;

@Property("[单位能力] 飞行管理员")
public class ActorTransportCraft extends ActorTransport implements IActorAbility
{
	private static final long serialVersionUID = 1L;
	/** NPC闲话  */
	@Property("NPC闲话")
	public	String		npc_talk;
	
	@Property("目标类型")
	public EUnitType	target_type = EUnitType.IMMUTABLE;
	
	@Override
	public String getTalk() {
		return npc_talk;
	}
	
	@Override
	public boolean isMultiField() {
		return true;
	}
	
}
