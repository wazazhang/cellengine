package com.cell.rpg.template.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.anno.PropertyAdapter;
import com.cell.rpg.anno.PropertyType;
import com.g2d.annotation.Property;

@Property("[单位能力] AVATAR")
public class UnitAvatar extends AbstractAbility
{
	private static final long serialVersionUID = 1L;

	/** AVATAR ID */
	@Property("AVATAR ID")
	@PropertyAdapter(PropertyType.AVATAR_ID)
	public int avatar_id = -1;
	
	@Override
	final public boolean isMultiField() {
		return true;
	}
}