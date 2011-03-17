package com.cell.rpg.scene;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import com.cell.CUtil;
import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.scene.script.trigger.Event;
import com.g2d.annotation.Property;

public class TriggerGenerator implements Serializable, Comparator<String>, Abilities
{
	private static final long serialVersionUID = 1L;
	
	private TreeMap<String, TriggerInstance> binded_triggers = new TreeMap<String, TriggerInstance>();
	
	final protected Object writeReplace() throws ObjectStreamException {
		if (binded_triggers != null) {
			TreeMap<String, TriggerInstance> new_list = new TreeMap<String, TriggerInstance>();
			for (TriggerInstance e : binded_triggers.values()) {
				new_list.put(e.trigger_name, e);
			}
			binded_triggers = new_list;
		}
		return this;
	}
	final protected Object readResolve() throws ObjectStreamException {
		if (binded_triggers == null) {
			binded_triggers = new TreeMap<String, TriggerInstance>();
		}
		return this;
	}


	public boolean bindTrigger(SceneTrigger st){
		if (binded_triggers.containsKey(st.getName())) {
			return false;
		} else {
			binded_triggers.put(st.getName(), new TriggerInstance(st.getName()));
			return true;
		}
	}

	public boolean unbindTrigger(SceneTrigger st) {
		if (binded_triggers.containsKey(st.getName())) {
			binded_triggers.remove(st.getName());
			return true;
		} else {
			return false;
		}
	}

	public TriggerInstance getTriggerInstance(String name) {
		return binded_triggers.get(name);
	}

	public ArrayList<SceneTrigger> getTriggers(Triggers triggers) {
		ArrayList<SceneTrigger> ret = new ArrayList<SceneTrigger>(binded_triggers.size());
		for (String name : new ArrayList<String>(binded_triggers.keySet())) {
			SceneTrigger st = triggers.getTrigger(name);
			if (st != null) {
				ret.add(st);
			} else {
				binded_triggers.remove(name);
			}
		}
		return ret;
	}

	public ArrayList<TriggerInstance> getBindedTriggers() {
		return new ArrayList<TriggerInstance>(binded_triggers.values());
	}
	
	public ArrayList<String> getTriggerNames() {
		return new ArrayList<String>(binded_triggers.keySet());
	}

	public int getTriggerCount() {
		return binded_triggers.size();
	}
	
	@Override
	public int compare(String o1, String o2) {
		return CUtil.getStringCompare().compare(o2, o1);
	}

	@Override
	public void addAbility(AbstractAbility element) {
		if (element instanceof TriggerInstance) {
			TriggerInstance e = (TriggerInstance)element;
			if (e.trigger_name!=null &&
				!binded_triggers.containsKey(e.trigger_name)) {
				binded_triggers.put(e.trigger_name, e);
			}
		}
	}

	@Override
	public void removeAbility(AbstractAbility element) {
		if (element instanceof TriggerInstance) {
			TriggerInstance e = (TriggerInstance)element;
			if (binded_triggers.containsKey(e.trigger_name)) {
				binded_triggers.remove(e.trigger_name);
			}
		}
	}
	@Override
	public void clearAbilities() {
		binded_triggers.clear();
	}
	
	@Override
	public AbstractAbility[] getAbilities() {
		return binded_triggers.values().toArray(new AbstractAbility[binded_triggers.size()]);
	}

	@Override
	public int getAbilitiesCount() {
		return binded_triggers.size();
	}

	@Override
	public <T> ArrayList<T> getAbilities(Class<T> type) {
		if (TriggerInstance.class.isAssignableFrom(type)) {
			ArrayList<T> ret = new ArrayList<T>(binded_triggers.size());
			for (TriggerInstance e : binded_triggers.values()) {
				ret.add(type.cast(e));
			}
			return ret;
		}
		return new ArrayList<T>(0);
	}
	
	@Override
	public <T> T getAbility(Class<T> type) {
		if (TriggerInstance.class.isAssignableFrom(type)) {
			for (TriggerInstance e : binded_triggers.values()) {
				return type.cast(e);
			}
		}
		return null;
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class[]{
				TriggerInstance.class,
		};
	}
	
	@Property("[触发器]")
	public static class TriggerInstance extends AbstractAbility implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
		@Property("触发器名")
		public String trigger_name = null;
		
		@Property("触发器默认开启")
		public boolean enable = true;
		
		public TriggerInstance() {}
		
		public TriggerInstance(String trigger_name) {
			this.trigger_name = trigger_name;
		}
		
		@Override
		public boolean isMultiField() {
			return true;
		}
		
		@Override
		public String toString() {
			return super.toString() + " - " + trigger_name;
		}
	}
	
	
}
