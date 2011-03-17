package com.cell.rpg.quest.formula;

import com.cell.rpg.formula.AbstractValue;
import com.g2d.annotation.Property;

@Property("任务-状态")
public class QuestStateProperty extends AbstractValue
{
	@Property("任务ID")
	public int					quest_id;
	
	@Property("任务状态")
	public QuestStateField 		trigger_unit_field;
	
//	----------------------------------------------------------------------------------------
	
	public QuestStateProperty(int quest_id, QuestStateField field) {
		this.trigger_unit_field = field;
	}
	public QuestStateProperty() {}

	@Override
	public String toString() {
		return "任务" + quest_id + trigger_unit_field;
	}

//	----------------------------------------------------------------------------------------
	
	public static enum QuestStateField 
	{
		ACCEPT_TIME_MS			("接收时间"),
		COMMIT_TIME_MS			("完成时间"),
		COMPLETE_COUNT			("完成次数"),
		;

		final private String text;
		private QuestStateField(String text) {
			this.text = text;
		}
		@Override
		public String toString() {
			return text;
		}
	}
}
