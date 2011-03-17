package com.g2d.studio.cpj;

import com.cell.util.EnumManager.ValueEnum;
import com.g2d.studio.cpj.entity.CPJObject;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.cpj.entity.CPJWorld;

public enum CPJResourceType implements ValueEnum<Class<? extends CPJObject<?>>>
{
	ACTOR(CPJSprite.class),
	AVATAR(CPJSprite.class),
	EFFECT(CPJSprite.class),
	WORLD(CPJWorld.class),
	;
	
	final public Class<? extends CPJObject<?>> res_type;
	
	private CPJResourceType(Class<? extends CPJObject<?>> type) {
		this.res_type = type;
	}
	
	@Override
	public Class<? extends CPJObject<?>> getValue() {
		return res_type;
	}
	
}

