package com.g2d.studio.sample.scene;

import java.util.LinkedHashSet;
import java.util.Set;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.anno.PropertyAdapter;
import com.cell.rpg.anno.PropertyType;
import com.cell.rpg.scene.SceneAbilityManager;
import com.g2d.annotation.Property;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.PropertyEditor;

public class EatSceneAbilityManager extends SceneAbilityManager
{
	private Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
	
	public EatSceneAbilityManager() {
		classes.add(InhibitItem.class);
		classes.add(InteractiveCondition.class);
		classes.add(SceneElements.class);
		classes.add(RebirthPlace.class);
		classes.add(PlayerCondition.class);
	}

//	-------------------------------------------------------------------------------------------------------
	
	@Override
	public Set<Class<?>> getAllTypes() {
		return classes;
	}
	
	@Override
	public Set<PropertyEditor<?>> getEditAdapters() {
		return null;
	}
	
//	-------------------------------------------------------------------------------------------------------
	
	@Property("禁止使用的道具ID")
	public static class InhibitItem extends AbstractAbility
	{
		private static final long serialVersionUID = 1L;

		@Property("禁止使用的道具ID")
		@PropertyAdapter(PropertyType.ITEM_ID)
		public int inhibit_item_id = -1;
		
		@Override
		public boolean isMultiField() {
			return true;
		}
	}
	
	@Property("交互条件限制")
	public static class InteractiveCondition extends AbstractAbility
	{
		private static final long serialVersionUID = 1L;

		@Property("禁止组队")
		public boolean inhibit_create_team 	= false;
		
		@Property("禁止GPS")
		public boolean inhibit_gps 			= false;
		@Property("禁止MiniMap")
		public boolean inhibit_mini_map		= false;
		
		@Property("禁止PVP")
		public boolean inhibit_pvp 			= false;
		@Property("禁止使用所有道具")
		public boolean inhibit_use_item 	= false;
		
		@Override
		public boolean isMultiField() {
			return false;
		}
	}
	
	@Property("场景五行")
	public static class SceneElements extends AbstractAbility
	{
		private static final long serialVersionUID = 1L;
		
		@Property("金")
		public int e_j	 	= 0;
		@Property("木")
		public int e_m	 	= 0;
		@Property("水")
		public int e_s	 	= 0;
		@Property("火")
		public int e_h	 	= 0;
		@Property("土")
		public int e_t	 	= 0;
		
		@Override
		public boolean isMultiField() {
			return false;
		}
		
	}
	
	@Property("复活位置")
	public static class RebirthPlace extends AbstractAbility
	{
		private static final long serialVersionUID = 1L;
		
		@Property("死亡后，在该场景复活")
		@PropertyAdapter(PropertyType.SCENE_ID)
		public int scene_id	 	= 0;

		@Override
		public boolean isMultiField() {
			return false;
		}
		
	}
	
//	-------------------------------------------------------------------------------------------------------
	

	@Property("[副本] 玩家进入条件限制")
	public static class PlayerCondition extends AbstractAbility
	{
		private static final long serialVersionUID = 1L;
		
		@Property("需要玩家职业")
		public int requied_job	 	= -1;
		@Property("需要玩家等级")
		public int requied_level 	= 0;
		
		@Override
		public boolean isMultiField() {
			return false;
		}
		
	}
	
	
}
