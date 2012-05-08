package com.cell.rpg.scene.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;



@Property("[单位能力] NPC闲话")
public class ActorTalk extends AbstractAbility  implements IActorAbility
{
	private static final long serialVersionUID = 1L;
	
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

