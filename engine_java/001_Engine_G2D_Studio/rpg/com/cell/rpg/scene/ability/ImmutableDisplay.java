package com.cell.rpg.scene.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;

@Property("[不可破坏能力] 可显示/隐藏")
public class ImmutableDisplay  extends AbstractAbility
{
	private static final long serialVersionUID = 1L;
		
	@Override
	public boolean isMultiField() {
		return false;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
