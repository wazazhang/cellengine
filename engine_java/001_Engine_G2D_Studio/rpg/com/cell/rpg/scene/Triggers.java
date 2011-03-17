package com.cell.rpg.scene;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import com.cell.CUtil;

public class Triggers implements Serializable
{
	private static final long serialVersionUID = 1L;

	private LinkedHashMap<String, SceneTrigger> triggers = new LinkedHashMap<String, SceneTrigger>();
		
	public Triggers() {}

	public boolean addTrigger(SceneTrigger st) {
		if (!triggers.containsKey(st.getName())) {
			triggers.put(st.getName(), st);
			return true;
		}
		return false;
	}
	
//	----------------------------------------------------------------------------------------------

	protected Object writeReplace() throws ObjectStreamException {
		return this;
	}
	
	protected Object readResolve() throws ObjectStreamException {
		return this;
	}
	
//	----------------------------------------------------------------------------------------------
	
	public SceneTrigger getTrigger(String name) {
		return triggers.get(name);
	}
	
	public SceneTrigger removeTrigger(String name) {
		return triggers.remove(name);
	}
	
	public ArrayList<SceneTrigger> getTriggers(){
		return new ArrayList<SceneTrigger>(triggers.values());
	}
	
	public int getTriggerCount(){
		return triggers.size();
	}
//	----------------------------------------------------------------------------------------------

	public void saveList(ArrayList<String> nameList) {
		LinkedHashMap<String, SceneTrigger> new_list = new LinkedHashMap<String, SceneTrigger>(triggers.size());
		for (String name : nameList) {
			SceneTrigger st = getTrigger(name);
			if (st != null) {
				new_list.put(st.getName(), st);
			}
		}
		triggers = new_list;
	}

	
}
