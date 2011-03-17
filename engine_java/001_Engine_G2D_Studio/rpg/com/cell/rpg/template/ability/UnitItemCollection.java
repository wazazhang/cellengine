package com.cell.rpg.template.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.anno.PropertyAdapter;
import com.cell.rpg.anno.PropertyType;
import com.g2d.annotation.Property;


@Property("[单位能力] 采集物品")
public class UnitItemCollection extends AbstractAbility
{
	private static final long serialVersionUID = 1L;

	/** 掉落物品列表ID */
	@Property("掉落物品列表ID")
	@PropertyAdapter(PropertyType.ITEM_LIST_ID)
	public int item_drop_list_id = -1;
	
	/** 依赖采集者的技能 */
	@Property("依赖的技能")
	@PropertyAdapter(PropertyType.SKILL_ID)
	public int depend_skill_id	= -1;
	
	/** 依赖采集者的技能等级 */
	@Property("依赖的技能等级")
	public int depend_skill_level = 0;

	@Override
	final public boolean isMultiField() {
		return true;
	}
	
}
