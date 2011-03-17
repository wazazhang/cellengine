package com.cell.rpg.quest;

public class QuestActionSingle extends QuestCondition
{
	public int addQuestItem(QuestItem item) {
		items.clear();
		items.put(item.getType(), item);
		return item.getType();
	}

}
