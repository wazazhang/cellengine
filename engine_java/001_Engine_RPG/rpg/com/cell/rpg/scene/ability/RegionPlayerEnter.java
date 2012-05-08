package com.cell.rpg.scene.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;

@Property("[区域能力] 场景出生区域")
public class RegionPlayerEnter extends AbstractAbility {

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
