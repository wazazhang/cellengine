package com.cell.rpg.template.ability;

import java.io.ObjectStreamException;

import com.cell.rpg.ability.AbilitiesList;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.anno.PropertyAdapter;
import com.cell.rpg.anno.PropertyType;
import com.cell.rpg.scene.ability.RegionSpawnNPC.NPCSpawn;
import com.g2d.annotation.Property;


@SuppressWarnings("deprecation")
@Property("[单位能力] 战斗队伍")
public class UnitBattleTeam extends AbstractAbility
{
	private static final long serialVersionUID = 1L;

	@Property("绑定的单位")
	public SpawnTypes	spawn_types = new SpawnTypes();
	
	@Property("队伍人数是否会因玩家发生变化")
	public boolean is_variable_team = false;

	
	
	public UnitBattleTeam() {
		this.spawn_types = new SpawnTypes();
	}
	
	@Override
	public boolean isMultiField() {
		return false;
	}
	
	protected Object writeReplace() throws ObjectStreamException {
		return this;
	}
	
	protected Object readResolve() throws ObjectStreamException {
		return this;
	}


	public static class SpawnTypes extends AbilitiesList
	{
		private static final long serialVersionUID = 1L;
		public SpawnTypes() {
//			super(TeamNode.class);
		}
//		public SpawnTypes(AbilitiesVector vector) {
//			super(TeamNode.class);
//			if (vector != null) {
//				for (AbstractAbility a : vector.getAbilities()) {
//					this.addAbility(a);
//				}
//			}
//		}
		public java.lang.Class<?>[] getSubAbilityTypes() {
			return new Class[]{TeamNode.class};
		}
		public String toString() {
			return getAbilitiesCount() + "个单位";
		}
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
		
		@Property("出现机率100%")
		public	Float		happen_percent = 100.0f;
	
		protected Object readResolve() throws ObjectStreamException {
			if (happen_percent == null) {
				happen_percent = 100.0f;
			}
			return this;
		}

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
