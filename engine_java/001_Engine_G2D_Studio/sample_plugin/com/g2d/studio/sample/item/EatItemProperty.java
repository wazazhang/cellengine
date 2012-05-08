package com.g2d.studio.sample.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.cell.CUtil;
import com.cell.location.StringConfig;
import com.cell.reflect.Parser;
import com.cell.rpg.item.ItemPropertyTemplate;
import com.cell.rpg.item.ItemPropertyTypes;
import com.cell.rpg.item.anno.ItemPersistenceField;
import com.cell.rpg.item.anno.ItemType;
import com.cell.util.EnumManager.ValueEnum;
import com.g2d.annotation.Property;


public abstract class EatItemProperty extends ItemPropertyTemplate
{
	private static final long serialVersionUID = 1L;

	public static enum TargetType
	{
		ALL,
		PLAYER,
		SERVANT,
		MONSTER,
	}
	
	public static enum TargetTeam
	{
		ALL,
		ENEMY,
		PARTNER,
		SELF_ONLY,
		PARTNER_ONLY,
	}
	
	public static enum TargetState
	{
		ALL,
		ALIVE,
		DEAD,
	}
	
	public static enum StateInBattle implements ValueEnum<Short>
	{
		Normal 			((short)0),
		Cannt_Comannd 	((short)1),
		Cannt_Physics	((short)2),
		Cannt_Magic		((short)3),
		Cannt_Action	((short)4),
		Cannt_UseItem	((short)5),
		Cannt_Escape	((short)6),
		Cannt_AttackByPhysics	((short)7),
		Cannt_AttackByMagic		((short)8),
		;
		
		final short value ;
		private StateInBattle(short value)
		{
			this.value = value;
		}
		
		@Override
		public Short getValue() 
		{
			return value;
		}
		
	}
	
	public static enum CostResult
	{
		SUCCESS,
		
		FAILED_CANNT_COST,
		
		FAILED_CANNT_COST_BATTLE,
		
		FAILED_MANA_NOT_ENOUGH,
		
		FAILED_HP_NOT_ENOUGH,
		
		FAILED_EP_NOT_ENOUGH,
		
		FAILED_ITEM_NOT_ENOUGH,
		
		FAILED_NO_ACTOR,
		
		FAILED_ELEMENT_NOT_ENOUGH,
		
		FAILED_MONEY_NOT_ENOUGH,
		
		FAILED_LEVEL_NOT_MEET,
		
		FAILED_TALENT_NOT_MEET,
	}
	

	@Property("无")
	@ItemType(0)
	public static class NULL extends EatItemProperty
	{
		private static final long serialVersionUID = 1L;
		
		@Override
		public boolean isMultiField() 
		{
			return false;
		}
	}
	

	/** 回复生命绝对值 */
	@Property("(000001) 回复生命值（地图&战斗）")
	@ItemType(1)
	public static class HP_ADD_ABS extends EatItemProperty
	{
		private static final long serialVersionUID = 1L;
		@Property("下限")
		public int low;
		@Property("上限")
		public int high;
		@Property("对象类型")
		public TargetType target_type = TargetType.ALL;
		@Property("对象阵营")
		public TargetTeam target_team = TargetTeam.PARTNER;
		@Property("对象状态")
		public TargetState target_state = TargetState.ALIVE;
		
		@Property("依赖上个属性的结果")
		public boolean is_depend = false;

	}

	/** 回复法力绝对值 */
	@Property("(000002) 回复法力值（地图&战斗）")
	@ItemType(2)
	public static class MANA_ADD_ABS extends EatItemProperty
	{
		private static final long serialVersionUID = 1L;
		@Property("下限")
		public int low;
		@Property("上限")
		public int high;
		@Property("对象类型")
		public TargetType target_type = TargetType.ALL;
		@Property("对象阵营")
		public TargetTeam target_team = TargetTeam.PARTNER;
		@Property("对象状态")
		public TargetState target_state = TargetState.ALIVE;

		@Property("随机对象")
		public boolean is_random_unit = false;
		
		@Property("依赖上个属性的结果")
		public boolean is_depend = false;

	}

	@Property("(000003) 回复生命万分比（地图&战斗）")
	@ItemType(3)
	public static class HP_ADD_PCT extends EatItemProperty
	{
		private static final long serialVersionUID = 1L;
		@Property("下限")
		public int low;
		@Property("上限")
		public int high;
		@Property("对象类型")
		public TargetType target_type = TargetType.ALL;
		@Property("对象阵营")
		public TargetTeam target_team = TargetTeam.PARTNER;
		@Property("对象状态")
		public TargetState target_state = TargetState.ALIVE;
		
		@Property("随机对象")
		public boolean is_random_unit = false;
		
		@Property("依赖上个属性的结果")
		public boolean is_depend = false;
		
	}
	
	@Property("(000004) 回复法力万分比（地图&战斗）")
	@ItemType(4)
	public static class MANA_ADD_PCT extends EatItemProperty 
	{}
	/** 生命最大绝对值 */
	@Property("(000005) (装备)生命最大绝对值")
	@ItemType(5)
	public static class HP_MAX_ABS extends EatItemProperty
	{}

	@Property("(000006) (装备)生命最大万分比")
	@ItemType(6)
	public static class HP_MAX_PCT extends EatItemProperty
	{}

	
	
}; // class EatItemProperty



