package com.g2d.studio.quest;

import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DList;

public class QuestList extends G2DList<QuestNode>
{
	/**
	 * @param not_transient 是否排除所有临时任务
	 */
	public QuestList(boolean not_transient) {
		super(
		not_transient ?
		Studio.getInstance().getQuestManager().getDependedQuests() : 
		Studio.getInstance().getQuestManager().getQuests()		
		);
	}
}
