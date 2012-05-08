package com.cell.rpg.scene.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;


@Property("[单位能力] 标志字符串")
public class ActorFlag  extends AbstractAbility implements IActorAbility
{
	private static final long serialVersionUID = 1L;

	@Property("一个标志字符串")
	public	String		flag;
	
	@Override
	public String getTalk() {
		return null;
	}
	
	@Override
	public boolean isMultiField() {
		return true;
	}
	
}

