package com.cell.rpg.scene.ability;

import java.io.Serializable;

import com.cell.rpg.ability.AbilitiesList;
import com.cell.rpg.ability.AbilitiesVector;
import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;

/**
 * @author WAZA
 */
@Property("[区域能力] 采集点产生区域")
public class RegionSpawnTrezuro extends AbstractAbility 
{
	private static final long serialVersionUID = 1L;
	
	@Property("产生的单位最大数量")
	public int spawn_unit_count;
	
	@Property("采集点刷新时间")
	public int spawn_interval = 10000;
	
	@Property("产生的单位")
	public CollectionTypes spawn_types = new CollectionTypes();

	@Override
	public boolean isMultiField() {
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + " : " + " : max=" + spawn_unit_count + " : inerval=" + spawn_interval  + " : types=" + spawn_types;
	}
	
	public static class CollectionTypes extends AbilitiesList implements Serializable
	{
		private static final long serialVersionUID = 1L;
		@Override
		public Class<?>[] getSubAbilityTypes() {
			return new Class<?>[]{CollectionSpawn.class};
		}
	}
	

	/**
	 * [区域能力] 采集点产生区域单位类型 
	 * 该对象放置在 {@link RegionSpawnTrezuro}
	 * @author WAZA
	 * @see RegionSpawnTrezuro
	 */
	@Property("[区域能力] 采集点产生区域单位类型")
	public static class CollectionSpawn extends AbstractAbility 
	{
		private static final long serialVersionUID = 1L;
		
		/** 产生百分比，即该区域在生成NPC时，生成到该类型的几率 */
		@Property("产生百分比，即该区域在生成采集点时，生成到该类型的几率")
		public float 		spawn_percent	= 100;
		
		/** 对应采集点对象名 */
		@Property("对应采集点对象名")
		public	String		template_unit_id;
		
		@Override
		public boolean isMultiField() {
			return true;
		}
		
		@Override
		public String toString() {
			return super.toString() + " : " + template_unit_id + "(" + ((int)(spawn_percent)) + "%)";
		}
	}
}
