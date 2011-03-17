package com.cell.rpg.formula;

import com.g2d.annotation.Property;

/**
 * @author WAZA
 *
 */
@Property("值-编辑器对象ID")
public class ObjectIDValue extends AbstractValue
{
	@Property("ID类型")
	public ObjectType	object_type	= ObjectType.TUNIT_ID;
	
	@Property("ID")
	public int			object_id	= 0;

	public ObjectIDValue() {}
	
	@Override
	public String toString() {
		return object_id + "(" + object_type + ")";
	}
	
	
	public static enum ObjectType
	{
		TUNIT_ID					("单位ID"),
		TTEMPLATE_ITEM_ID			("道具模板ID"),
		TSKILL_ID					("技能ID"),
		TAVATAR_ID					("AvatarID"),
		TEFFECT_ID					("效果ID"),
		
		QUEST_ID					("任务ID"),
//		QUEST_ITEM_ID				("任务条件/奖励ID"),
		
		SCENE_ID					("场景ID"),
		
		ITEM_PROPERTY_SAVED_TYPE	("道具属性类型"),
		;
		
		final String text;
		private ObjectType(String value) {
			this.text = value;
		}
		
		@Override
		public String toString() {
			return text;
		}
	}
}
