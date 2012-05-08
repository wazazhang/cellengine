package com.cell.rpg.quest;

import com.cell.rpg.NamedObject;
import com.cell.rpg.RPGObject;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.anno.PropertyAdapter;
import com.cell.rpg.anno.PropertyType;
import com.cell.rpg.formula.AbstractValue;
import com.cell.rpg.formula.Value;
import com.cell.rpg.quest.formula.TriggerUnitMethod;
import com.cell.rpg.quest.formula.TriggerUnitProperty;
import com.cell.rpg.struct.BooleanCondition;
import com.cell.rpg.struct.Comparison;
import com.cell.rpg.struct.InstanceZoneScriptCode;
import com.g2d.annotation.Property;

/**
 * 用于任务的状态存储<br>
 * 如果 isResult() == true，则代表该标志是一个结果<br>
 * 如果 isResult() == false，则代表该标志是一个条件<br>
 * 例如：
 * 玩家完成一个任务后或触发一段剧情后，获得若干QuestItem，
 * QuestItem存储到当前玩家的状态，当玩家触发了另一个任务时，
 * 如果该任务的接受条件包含玩家身上的QuestItem，
 * 那么触发的新任务就被激活，激活的任务可能消耗掉玩家身上的道具，
 * 也可能给予玩家另外的QuestItem。<br>
 * QuestItem也可以理解为任务标志或者任务令牌。<br>
 * 如果QuestItem是一个条件，则一组QuestItem为或的条件<br>
 * 如果QuestItem是一个奖励，则由玩家选择一项<br>
 * @author WAZA
 */
public class QuestItem extends RPGObject implements NamedObject
{	
//	--------------------------------------------------------------------------------------
	
	final private Integer	type;

	final private boolean	is_result;
	
	public String 			name;
	
//	--------------------------------------------------------------------------------------
	
	public QuestItem(Integer type, String name, boolean isresult) {
		super(type.toString());
		this.type 		= type;
		this.is_result	= isresult;
		this.name		= name;
	}
	
	/**
	 * 可以理解为标志的唯一id
	 * @return
	 */
	public int getType() {
		return type;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * 是否是一个结果，就是QuestAward里的值，
	 * 否则就是一个条件
	 * @return
	 */
	public boolean isResult() {
		return is_result;
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		if (!is_result) {
			return new Class<?>[]{
					TagItem.class,
					TagQuest.class,
					TagQuestItem.class,
					TagQuestStateKillMonsterComparison.class,
					TagValueComparison.class,
					TagEveryUnitComparison.class,
					TagEveryUnitMethodComparison.class,
					TagOneMoreUnitComparison.class,
					TagOneMoreUnitMethodComparison.class,
					TagUnitGroupCountComparison.class,
					TagTeamPlayerCountComparison.class,
					
					TagInstanceZoneValueComparison.class,
					
					TagBattleWin.class,
				};
		} else {
			return new Class<?>[]{
					AwardItem.class,
					DropItem.class,
					AwardTeleport.class,
					AwardBattle.class,
					AwardAddUnitProperty.class,
					AwardSetUnitProperty.class,
					
					AwardSummonNPC.class,
					DropQuestNPC.class,
					DropQuestServant.class,
					
					DropQuest.class,
					AwardInstanceZoneValueSet.class,
					
					CallUnitMethod.class,
				};
		}
	}
	
//	--------------------------------------------------------------------------------------
	
//	--------------------------------------------------------------------------------------
	
	public static abstract class QuestItemAbility extends AbstractAbility {
		private static final long serialVersionUID = 1L;
	}

//	--------------------------------------------------------------------------------------

	public static abstract class Tag extends QuestItemAbility
	{
		private static final long serialVersionUID = 1L;
		
		@Property("该标志的布尔条件(默认true)")
		public BooleanCondition boolean_condition = BooleanCondition.TRUE;
		
		@Override
		public boolean isMultiField() 
		{
			return true;
		}
		
		public boolean getBooleanConditionValue() 
		{
			if (boolean_condition == BooleanCondition.FALSE)
				return false;

			return true;
		}
		
		public abstract String getFailedString();
	}
	
	/**
	 * [条件] 物品
	 * @author WAZA
	 */
	@Property("[条件] 依赖的物品")
	final public static class TagItem extends Tag
	{
		private static final long serialVersionUID = 1L;
		
		@Property("物品-类型")
		public int				titem_index			= -1;
		
		@Property("比较器")
		public Comparison 		comparison			= Comparison.EQUAL_OR_GREATER_THAN;
		
		@Property("物品-数量")
		public AbstractValue	titem_count			= new Value(1);
		
		@Property("道具品质满足多少")
		public AbstractValue	titem_quality		= new Value(0);
		
		@Property("物品-是否消耗掉")
		public boolean			is_expense			= true;
		
		@Override
		public String getFailedString() 
		{
			return "依赖的物品不满足";
		}
	}
	
	/**
	 * [条件] 依赖已完成的任务
	 * @author WAZA
	 */
	@Property("[条件] 依赖已完成的任务")
	final public static class TagQuest extends Tag
	{
		private static final long serialVersionUID = 1L;

		@Property("已完成的任务ID")
		public int				quest_id	= -1;
		
		@Override
		public String getFailedString() 
		{
			return "依赖的任务没有完成";
		}		
	}
	
	/**
	 * [条件] 依赖的任务奖励
	 * @author WAZA
	 */
	@Property("[条件] 依赖已完成的任务奖励")
	final public static class TagQuestItem extends Tag
	{
		private static final long serialVersionUID = 1L;
		@Property("已完成的任务ID")
		public int				quest_id			= -1;
		
		@Property("依赖的任务奖励ID")
		public int				quest_item_index	= -1;
		
		@Override
		public String getFailedString() 
		{
			return "没有依赖的任务给予的奖励";
		}		
	}
	
	/**
	 * [条件] 依赖的任务状态
	 * @author WAZA
	 */
	@Property({"[条件] 依赖当前的任务状态(杀死敌人数)", "(不能用作接受条件)"})
	final public static class TagQuestStateKillMonsterComparison extends Tag
	{
		private static final long serialVersionUID = 1L;
		@Property("杀死的单位模板ID")
		public Integer				kill_unit_id	= -1;

		@Property("比较器")
		public Comparison 			comparison		= Comparison.EQUAL;
		
		@Property("杀死的单位数量")
		public AbstractValue		kill_count		= new Value(1);
		
		@Override
		public String getFailedString() 
		{
			return "杀死的敌人的数量还没有达到要求";
		}		
	}

	/**
	 * [条件] 比较条件
	 * @author WAZA
	 */
	@Property("[条件] 比较条件")
	public static class TagValueComparison extends Tag
	{
		private static final long serialVersionUID = 1L;
		@Property("原值")
		public AbstractValue		src_value	= new Value(1);
		
		@Property("比较器")
		public Comparison 			comparison	= Comparison.EQUAL;
		
		@Property("目标值")
		public AbstractValue		dst_value	= new Value(1);
		
		@Override
		public String getFailedString() 
		{
			return "比较条件不满足";
		}		
	}

//	--------------------------------------------------------------------------------------
//	unit group 
	
	@Property("[条件] 每个单位属性")
	public static class TagEveryUnitComparison extends Tag
	{
		private static final long serialVersionUID = 1L;
		@Property("原单位属性")
		public TriggerUnitProperty	src_value	= new TriggerUnitProperty();
		
		@Property("比较器")
		public Comparison 			comparison	= Comparison.EQUAL;
		
		@Property("目标值")
		public AbstractValue		dst_value	= new Value(1);
		
		@Override
		public String getFailedString()
		{
			if (boolean_condition == BooleanCondition.TRUE)
			{
				return "每个"+src_value.toString()+"需要是"+comparison.toString()+dst_value.toString(); // TODO;
			}
			else
			{
				return "每个"+src_value.toString()+"不能是"+comparison.toString()+dst_value.toString(); // TODO;
			}
		}		
	}

	@Property("[条件] 每个单位函数")
	public static class TagEveryUnitMethodComparison extends Tag
	{
		private static final long serialVersionUID = 1L;
		@Property("原单位函数")
		public TriggerUnitMethod	src_value	= new TriggerUnitMethod();
		
		@Property("比较器")
		public Comparison 			comparison	= Comparison.EQUAL;
		
		@Property("目标值")
		public AbstractValue		dst_value	= new Value(1);
		
		@Override
		public String getFailedString()
		{
			if (boolean_condition == BooleanCondition.TRUE)
			{
				return "每个"+src_value.toString()+"需要是"+comparison.toString()+dst_value.toString(); // TODO;
			}
			else
			{
				return "每个"+src_value.toString()+"不能是"+comparison.toString()+dst_value.toString(); // TODO;
			}
		}		
	}
	
	@Property("[条件] 至少一个单位属性")
	public static class TagOneMoreUnitComparison extends Tag
	{
		private static final long serialVersionUID = 1L;
		@Property("原单位属性")
		public TriggerUnitProperty	src_value	= new TriggerUnitProperty();
		
		@Property("比较器")
		public Comparison 			comparison	= Comparison.EQUAL;
		
		@Property("目标值")
		public AbstractValue		dst_value	= new Value(1);
		
		@Override
		public String getFailedString()
		{
			if (boolean_condition == BooleanCondition.TRUE)
			{
				return "至少一个"+src_value.toString()+"需要是"+comparison.toString()+dst_value.toString(); // TODO;
			}
			else
			{
				return "至少一个"+src_value.toString()+"不能是"+comparison.toString()+dst_value.toString(); // TODO;
			}
		}		
	}
	
	@Property("[条件] 至少一个单位函数")
	public static class TagOneMoreUnitMethodComparison extends Tag
	{
		private static final long serialVersionUID = 1L;
		@Property("原单位函数")
		public TriggerUnitMethod	src_value	= new TriggerUnitMethod();
		
		@Property("比较器")
		public Comparison 			comparison	= Comparison.EQUAL;
		
		@Property("目标值")
		public AbstractValue		dst_value	= new Value(1);

		@Override
		public String getFailedString()
		{
			if (boolean_condition == BooleanCondition.TRUE)
			{
				return "至少一个"+src_value.toString()+"需要是"+comparison.toString()+dst_value.toString(); // TODO;
			}
			else
			{
				return "至少一个"+src_value.toString()+"不能是"+comparison.toString()+dst_value.toString(); // TODO;
			}
		}
	}
	
	@Property("[条件] 单位组数量")
	public static class TagUnitGroupCountComparison extends Tag
	{
		private static final long serialVersionUID = 1L;
		
		@Property("单位类型")
		public TriggerUnitType 		group_unit_type	= TriggerUnitType.PET_GROUP;
		
		@Property("比较器")
		public Comparison 			comparison		= Comparison.EQUAL;
		
		@Property("目标值")
		public AbstractValue		dst_value		= new Value(1);
		
		@Override
		public String getFailedString() 
		{
			if (boolean_condition == BooleanCondition.TRUE)
			{
				return group_unit_type.toString()+"需要是"+comparison.toString()+dst_value.toString(); // TODO;
			}
			else
			{
				return group_unit_type.toString()+"不能是"+comparison.toString()+dst_value.toString(); // TODO;
			}
		}		
	}
	
	@Property({"[条件] 组队人数", "0代表没组队"})
	public static class TagTeamPlayerCountComparison extends Tag
	{
		private static final long serialVersionUID = 1L;
		@Property("比较器")
		public Comparison 			comparison		= Comparison.EQUAL;
		
		@Property("目标值")
		public AbstractValue		dst_value		= new Value(1);
		
		@Override
		public String getFailedString() 
		{
			if (boolean_condition == BooleanCondition.TRUE)
			{
				return "组队人数"+"需要是"+comparison.toString()+dst_value.toString(); // TODO;
			}
			else
			{
				return "组队人数"+"不能是"+comparison.toString()+dst_value.toString(); // TODO;
			}
		}
	}
	
	
	@Property({"[副本任务条件] 副本变量条件脚本"})
	public static class TagInstanceZoneValueComparison extends Tag
	{
		private static final long serialVersionUID = 1L;

		@Property("脚本")
		public InstanceZoneScriptCode		dst_value		= new InstanceZoneScriptCode();
		
		@Override
		public String getFailedString() 
		{
			return "副本条件"+dst_value.toString()+"不满足"; // TODO
		}		
	}
	

	@Property("[条件] 判断战斗结果,该条件增加一个单位")
	final public static class TagBattleWin extends QuestItemAbility
	{
		private static final long serialVersionUID = 1L;
		
		@Property("和指定单位类型战斗")
		public Integer			unit_id			= -1;
		
		@Property("该单位是否为友军")
		public boolean			is_friend;
		
		@Property("附加参数")
		public String			arg				= "";

		@Override
		public boolean isMultiField() 
		{
			return true;
		}
	}

//	--------------------------------------------------------------------------------------
//	--------------------------------------------------------------------------------------
	
	public static abstract class Result extends QuestItemAbility
	{
		private static final long serialVersionUID = 1L;
		@Override
		public boolean isMultiField() {
			return true;
		}
	}
//	----------------------------------------------------------------------------

	/**
	 * [奖励] 物品
	 * @author WAZA
	 */
	@Property("[结果] 奖励物品")
	final public static class AwardItem extends Result
	{
		private static final long serialVersionUID = 1L;
//		@Property("单位类型")
//		public TriggerUnitType trigger_unit_type	= TriggerUnitType.PLAYER;
		@Property("物品-类型")
		public int				titem_index			= -1;
		@Property("物品-数量")
		public AbstractValue	titem_count			= new Value(1);
		@Property("物品-得到道具后，自动使用掉")
		public boolean			titem_auto_use		= false;
	}

	@Property("[结果] 强制丢弃物品")
	final public static class DropItem extends Result
	{
		private static final long serialVersionUID = 1L;
		@Property("物品-类型")
		public int				titem_index			= -1;
		@Property("物品-数量")
		public AbstractValue	titem_count			= new Value(1);
	}
//	----------------------------------------------------------------------------

	/**
	 * [奖励] 传送到某场景
	 * @author WAZA
	 */
	@Property("[结果] 传送到某场景")
	final public static class AwardTeleport extends Result
	{
		private static final long serialVersionUID = 1L;
		@Property("场景ID")
		public String			scene_id;
		
		@Property("场景内特定单位名字")
		public String 			scene_object_id;
	}
	
	/**
	 * [奖励] 开始战斗
	 * @author WAZA
	 */
	@Property("[结果] 开始战斗")
	final public static class AwardBattle extends Result
	{
		private static final long serialVersionUID = 1L;
		
		@Property("和指定单位类型战斗")
		public Integer			unit_id			= -1;
		
		@Property("该单位是否为友军")
		public boolean			is_firend;
		
		@Property("附加参数")
		public String			arg				= "";
	}
	
//	----------------------------------------------------------------------------

	@Property("[结果] 增加单位属性")
	final public static class AwardAddUnitProperty extends Result
	{
		private static final long serialVersionUID = 1L;
		@Property("原单位属性")
		public TriggerUnitProperty	src_value	= new TriggerUnitProperty();

		@Property("增量")
		public AbstractValue		increment	= new Value(1);
	}
	
	@Property("[结果] 设置单位属性")
	final public static class AwardSetUnitProperty extends Result
	{
		private static final long serialVersionUID = 1L;
		@Property("原单位属性")
		public TriggerUnitProperty	src_value	= new TriggerUnitProperty();

		@Property("目标值")
		public AbstractValue		dst_value	= new Value(1);
	}

	@Property("[结果] 调用单位函数")
	final public static class CallUnitMethod extends Result
	{
		private static final long serialVersionUID = 1L;
		
		@Property("原单位函数")
		public TriggerUnitMethod	src_value	= new TriggerUnitMethod();
	}
	
//	----------------------------------------------------------------------------
//	Quest PET
	
//	@Property({"[结果] 获得触发的NPC", "即当前场景中的NPC被带走"})
//	final public static class AwardTriggerNPC extends Result
//	{
//	}
	
	@Property({"[结果] 加入NPC到队伍", "产生一个NPC并带走"})
	final public static class AwardSummonNPC extends Result
	{
		private static final long serialVersionUID = 1L;
		@Property("NPC-类型")
		@PropertyAdapter(PropertyType.UNIT_ID)
		public int				tunit_index			= -1;
	}
	
	@Property({"[结果] 踢出NPC从队伍", "用于完成任务时，丢弃掉任务获得的NPC"})
	final public static class DropQuestNPC extends Result
	{
		private static final long serialVersionUID = 1L;
		@Property("NPC-类型")
		@PropertyAdapter(PropertyType.UNIT_ID)
		public int				tunit_index			= -1;
	}
	
	@Property({"[结果] 踢出指定类型战魂", "用于完成任务时，丢弃掉任务获得的战魂"})
	final public static class DropQuestServant extends Result
	{
		private static final long serialVersionUID = 1L;
		@Property("战魂-类型")
		@PropertyAdapter(PropertyType.UNIT_ID)
		public Integer			unit_id				= -1;
	}	
	
	@Property({"[结果] 清理任务状态", "用于完成任务时，清理掉之前完成的任务状态"})
	final public static class DropQuest extends Result
	{
		private static final long serialVersionUID = 1L;
		@Property("任务ID")
		@PropertyAdapter(PropertyType.QUEST_ID)
		public Integer			quest_id				= -1;
	}	
//	----------------------------------------------------------------------------

	
	@Property({"[副本任务结果] 副本变量设置脚本"})
	public static class AwardInstanceZoneValueSet extends Result
	{
		private static final long serialVersionUID = 1L;

		@Property("脚本")
		public InstanceZoneScriptCode		dst_value		= new InstanceZoneScriptCode();
	}

}
