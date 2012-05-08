package com.cell.rpg.quest.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;

public class QuestTrigger extends AbstractAbility
{
	private static final long serialVersionUID = 1L;
	@Property("任务ID")
	public Integer quest_id	= -1;
	
	@Override
	public boolean isMultiField() {
		return true;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
