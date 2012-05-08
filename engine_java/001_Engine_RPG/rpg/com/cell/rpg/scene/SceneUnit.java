package com.cell.rpg.scene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

import com.cell.CUtil;
import com.cell.rpg.RPGObject;
//import com.cell.rpg.scene.script.trigger.Event;
import com.cell.rpg.struct.QuestStateDisplayOR;
import com.g2d.annotation.Property;

public abstract class SceneUnit extends RPGObject
{
	/** scene graph 结构的视图 */
	public String 						name	= "no name";
	public float						x;
	public float						y;
	public float						z;

	@Property("任务依赖显示条件 (覆盖TUnit)")
	public QuestStateDisplayOR 			quest_display = null;
	
//	private TriggerGenerator			binded_triggers = new TriggerGenerator();
	
	public SceneUnit(String id) {
		super(id);
	}

	@Override
	protected void init_transient() {
		super.init_transient();
//		if (binded_triggers == null) {
//			binded_triggers = new TriggerGenerator();
//		}
	}

	public String getName() {
		return name;
	}
	
	public String getTriggerObjectName(){
		return name;
	}

//	------------------------------------------------------------------------------------------------------------------
	
//	abstract public Class<? extends com.cell.rpg.scene.script.entity.SceneUnit>	getTriggerObjectType();
//
//	public TriggerGenerator getBindedTriggers() {
//		return binded_triggers;
//	}
	
//	------------------------------------------------------------------------------------------------------------------
	
}
