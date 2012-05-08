package com.cell.rpg.scene.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;

@Property("[单位数据] NPC行动开始点")
public class ActorPathStart extends AbstractAbility
{
	private static final long serialVersionUID = 1L;
	
	/** 对应point对象名 */
	@Property("对应point名字")
	public	String		point_name;
	
	@Override
	public boolean isMultiField() {
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + " : " + point_name;
	}
}
