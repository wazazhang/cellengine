package com.cell.rpg.instance.zones.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;

@Property("[副本怪物]")
public class InstanceZoneMonster extends AbstractAbility 
{
	private static final long serialVersionUID = 1L;

	@Property("阵营")
	public int force = 0;

	@Override
	final public boolean isMultiField() {
		return false;
	}
}
