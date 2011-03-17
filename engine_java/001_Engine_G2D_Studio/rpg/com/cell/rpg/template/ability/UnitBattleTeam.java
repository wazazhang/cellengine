package com.cell.rpg.template.ability;

import com.cell.rpg.ability.AbilitiesVector;
import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;


@SuppressWarnings("deprecation")
@Property("[单位能力] 战斗队伍")
public class UnitBattleTeam extends AbstractAbility
{
	private static final long serialVersionUID = 1L;

	@Property("绑定的单位")
	public AbilitiesVector	spawn_types = new AbilitiesVector(TeamNode.class){
		private static final long serialVersionUID = 1L;
		public String toString() {
			return getAbilitiesCount() + "个单位";
		}
	};

	@Override
	public boolean isMultiField() {
		return false;
	}

	@Property("[单位能力] 战斗队伍中的一个单位")
	public static class TeamNode extends AbstractAbility 
	{
		private static final long serialVersionUID = 1L;
		
		/** 对应单位 */
		@Property("对应单位")
		public	String		template_unit_id;
		
		/** 战斗时处在第几行 */
		@Property("战斗时处在第几行")
		public	int			battle_row;
		
		/** 战斗时处在第几列 */
		@Property("战斗时处在第几列")
		public	int			battle_column;
		
		@Override
		public boolean isMultiField() {
			return true;
		}
		
		@Override
		public String toString() {
			return super.toString() + " :(r" + battle_row + ",c" + battle_column + ")";
		}
	}
	
}
