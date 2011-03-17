package com.cell.rpg.scene.script;

import java.io.File;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.CUtil;
import com.cell.io.CFile;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.scene.SceneTrigger;
import com.cell.rpg.scene.SceneTriggerScriptable;
import com.cell.rpg.scene.TriggerGenerator;
import com.cell.rpg.scene.Triggers;
import com.cell.rpg.scene.TriggersPackage;
import com.cell.rpg.scene.script.anno.EventMethod;
import com.cell.rpg.scene.script.anno.EventParam;
import com.cell.rpg.scene.script.anno.EventType;
import com.cell.rpg.scene.script.trigger.Event;
import com.cell.rpg.struct.ScriptCode;
import com.g2d.studio.Studio;

public abstract class SceneScriptManager 
{
	/**
	 * 根据触发者类型，获取该触发者支持的事件类型
	 * @param trigger_type
	 * @return
	 */
	public Set<Class<? extends Event>> getEvents(Class<? extends Scriptable> trigger_type)
	{
		Set<Class<? extends Event>> ret = new HashSet<Class<? extends Event>>();
		for (Class<? extends Event> evt : Studio.getInstance().getSceneScriptManager().getEvents()) {
			if (asTriggeredObjectType(evt, trigger_type)) {
				ret.add(evt);
			}
		}
		return ret;
	}

	/**
	 * 得到指定类型事件的方法
	 * @param event_type
	 * @return
	 */
	public Collection<Method> getEventMethods(Class<? extends Event> event_type) {
		ArrayList<Method> ret = new ArrayList<Method>();
		for (Method mt : event_type.getMethods()) {
			EventMethod em = mt.getAnnotation(EventMethod.class);
			if (em != null) {
				ret.add(mt);
			}
		}
		return ret;
	}
	
	/**
	 * 检查该事件类型是否支持该触发者类型
	 * @param event_type	事件类型
	 * @param trigger_type	触发者类型
	 * @return
	 */
	public boolean asTriggeredObjectType(Class<? extends Event> event_type, Class<? extends Scriptable> trigger_type) {
		EventType et = event_type.getAnnotation(EventType.class);
		for (Class<? extends Scriptable> st : et.trigger_type()) {
			if (trigger_type.isAssignableFrom(st) || 
				st.isAssignableFrom(trigger_type)) {
				return true;
			}
		}
		return false;
	}

//	-----------------------------------------------------------------------------------------------------------------------
	

	/**
	 * 得到所有事件类型。
	 * @return
	 */
	abstract public Collection<Class<? extends Event>> getEvents();
	
	/**
	 * 创建脚本模板（编辑器用）
	 * @param event_type
	 * @return
	 */
	abstract public String createTemplateScript(
			TriggersPackage pak,
			Class<? extends Event> event_type,
			SceneTriggerScriptable sts);

	/**
	 * 读取脚本（编辑器用）
	 * @param pak
	 * @param root
	 * @param sts
	 * @return
	 */
	abstract public String 	loadTriggers(
			TriggersPackage pak, 
			String root, 
			SceneTriggerScriptable sts) ;
	
	/**
	 * 存储脚本（编辑器用）
	 * @param pak
	 * @param root
	 * @param sts
	 * @param script
	 */
	abstract public void 	saveTriggers(
			TriggersPackage pak,
			String root, 
			SceneTriggerScriptable sts,
			String script) ;

//	-----------------------------------------------------------------------------------------------------------------------
	
	public boolean checkScriptCode(ScriptCode code, Map<String, Object> vars) throws Exception {
		throw new Exception("没有编译器");
	}
}
