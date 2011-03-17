package com.cell.rpg.scene;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import com.cell.CIO;
import com.cell.io.CFile;
import com.cell.rpg.scene.script.Scriptable;
import com.cell.rpg.scene.script.anno.EventType;
import com.cell.rpg.scene.script.trigger.Event;

/**
 * 自定义脚本触发器
 * @author WAZA
 */
public class SceneTriggerScriptable extends SceneTrigger implements Serializable
{
	private static final long serialVersionUID = 1L;
		
	public SceneTriggerScriptable(Triggers parent, String name) throws Exception {
		super(parent, name);
	}
}
