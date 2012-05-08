package com.cell.rpg.template.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;


@Property("[单位能力] 出售物品")
public class UnitItemSell extends AbstractAbility
{
	private static final long serialVersionUID = 1L;

	/** 商品列表ID */
	@Property("商品列表ID")
	public int shopitem_list_id = -1;
	
	@Override
	final public boolean isMultiField() {
		return true;
	}
}
