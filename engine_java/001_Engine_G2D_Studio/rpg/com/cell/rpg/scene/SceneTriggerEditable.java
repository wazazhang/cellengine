package com.cell.rpg.scene;

import java.io.Serializable;

import com.cell.rpg.scene.script.Scriptable;
import com.cell.rpg.scene.script.anno.EventType;
import com.cell.rpg.scene.script.trigger.Event;

/**
 * 编辑器发器
 * @author WAZA
 */
public class SceneTriggerEditable extends SceneTrigger implements Serializable
{
	private static final long serialVersionUID = 1L;

	public SceneTriggerEditable(Triggers parent, String name) throws Exception {
		super(parent, name);
	}
}
