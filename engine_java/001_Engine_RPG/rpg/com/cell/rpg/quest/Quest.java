package com.cell.rpg.quest;

import com.cell.rpg.NamedObject;
import com.cell.rpg.RPGObject;
import com.g2d.annotation.Property;

/**
 * 一个任务的全部描述
 * @author WAZA
 */
public class Quest extends RPGObject implements NamedObject
{
//	----------------------------------------------------------------------------------------------------------------
//	base
	
	/** 任务id */
	final private Integer 		int_id;
	
	/** 任务名字 */
	public String				name;
	
	/**任务标志*/
	@Property("任务标志")
	public QuestFlags			quest_flags			= new QuestFlags();

	/**满足条件时自动接收*/
	@Property("满足条件时自动接收")
	public boolean				auto_accept			= false;
	
	/**满足条件时自动提交*/
	@Property("满足条件时自动提交")
	public boolean				auto_commit			= false;

//	----------------------------------------------------------------------------------------------------------------
//	quest

	/** 任务内容, 该字段由文件存储，注意反序列化后恢复*/
	transient
	private String				discussion			= "";

	/**任务产生事件*/
	public QuestGenerator		quest_generator		= new QuestGenerator();
	
//	-----------------------------------------------------------------------------------
//	quest tag
	
	/** 任务接受条件 */
	public QuestCondition		accept_condition	= new QuestCondition();
	/**任务接受奖励*/
	public QuestAward			accept_award		= new QuestAward();

	/** 任务完成条件 */
	public QuestCondition		complete_condition	= new QuestCondition();
	/** 任务完成奖励 */
	public QuestAward			complete_award		= new QuestAward();

	/** 任务放弃动作，仅一个 */
	public QuestActionSingle	discard_action		= new QuestActionSingle();

//	----------------------------------------------------------------------------------------------------------------
//	extend

	/** 任务图标 */ 
	@Property("任务图标")
	public String				icon_index;
	
	/** 任务等级 */ 
	@Property("任务等级")
	public Integer				quest_level;
	
	/** "组队任务", "可以主动共享给其他玩家" */ 
	@Property({"组队任务", "可以主动共享给其他玩家"})
	public boolean				is_team_quest		= false;

	/** "组队任务", "可以主动共享给其他玩家" */ 
	@Property({"组队事件任务", "队长接取后，队员也将执行剧情脚本"})
	public boolean				is_team_event_quest	= false;
	
	/** "玩家任务", "接了某些任务后就不能够结成组队战斗关系"*/ 
	@Property({"玩家任务", "接了某些任务后就不能够结成组队战斗关系"})
	public boolean				is_player_quest		= false;
	
	/** "单人任务", "接了某些任务后战魂就不能出战" */ 
	@Property({"单人任务", "接了某些任务后战魂就不能出战"})
	public boolean				is_single_quest		= false;
	
	/** "临时任务","任务做完后删除状态，该类型任务不能作为任务前置条件" */ 
	@Property({"临时任务","任务做完后删除状态，该类型任务不能作为任务前置条件"})
	public boolean				is_transient_quest	= false;
	
	@Property({"隐藏任务", "接了/完成任务时不会提示玩家，也不会出现在玩家的已接/完成任务列表中"})
	public boolean				is_hidden_quest		= false;
	
	@Property({"主动可接受任务", "在可接受的情况下会出现在玩家的可接受任务列表中"})
	public boolean				is_positive_acceptable_quest = false;
	
	@Property({"非NPC绑定任务", "即不是由NPC产生的任务"})
	public boolean				is_not_binded_npc = false;

//	----------------------------------------------------------------------------------------------------------------
	public Quest(Integer id, String name) {
		super(id.toString());
		this.int_id = id;
		this.name	= name;
	}

	public int getIntID() {
		return int_id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{};
	}
	
	@Override
	public void onReadComplete(RPGObject object) 
	{
		if (accept_condition == null) {
			accept_condition = new QuestCondition();
		}
		if (accept_award == null) {
			accept_award = new QuestAward();
		}
		if (complete_condition == null) {
			complete_condition = new QuestCondition();
		}
		if (complete_award == null) {
			complete_award = new QuestAward();
		}
		if (discard_action == null) {
			discard_action = new QuestActionSingle();
		}
		if (quest_flags == null) {
			quest_flags = new QuestFlags();
		}
	}
	
	@Override
	public void onWriteBefore(RPGObject object) {}
	
//	----------------------------------------------------------------------------------------------------------------
	
	/** 任务内容, 该字段由文件存储，注意反序列化后恢复*/
	public void setDiscussion(String text) {
		this.discussion = text;
	}
	
	/** 任务内容, 该字段由文件存储，注意反序列化后恢复*/
	public String getDiscussion() {
		return discussion;
	}

//	----------------------------------------------------------------------------------------------------------------

}
