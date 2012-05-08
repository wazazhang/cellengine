package com.cell.rpg.quest.ability;

import com.g2d.annotation.Property;

@Property("[任务能力] 任务接收者")
public class QuestAccepter extends QuestTrigger
{
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return super.toString() + " ? ";
	}
	
}
