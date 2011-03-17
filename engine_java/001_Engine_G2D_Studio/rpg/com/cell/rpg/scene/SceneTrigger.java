package com.cell.rpg.scene;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import com.cell.CUtil;
import com.cell.rpg.scene.script.trigger.Event;

/**
 * 触发器
 * @author WAZA
 */
abstract public class SceneTrigger implements Serializable, Comparator<Class<?>>
{
	private static final long serialVersionUID = 1L;
	
	private String 	name;
	
	private String 	tree_path;
	
//	private boolean	enable = true;
	
	public String	comment = "comment here ....";
	
//	------------------------------------------------------------------------------------------------------
	
	transient private TreeSet<Class<? extends Event>> event_types;
	
	private ArrayList<String> event_names = new ArrayList<String>();
	
//	------------------------------------------------------------------------------------------------------
	
	public SceneTrigger(Triggers parent, String name) throws Exception
	{
		if (!setName(parent, name) ) {
			throw new Exception("duplicate name in : " + name);
		}
	}
	
	protected Object writeReplace() throws ObjectStreamException {
		return this;
	}
	protected Object readResolve() throws ObjectStreamException {
		if (event_names == null) {
			event_names = new ArrayList<String>();
		}
		return this;
	}

//	public void setEnable(boolean enable) {
//		this.enable = enable;
//	}
//	
//	public boolean isEnable() {
//		return this.enable;
//	}
	
	
	public String getName() {
		return name;
	}

	public boolean setName(Triggers parent, String name) 
	{
		for (SceneTrigger st : parent.getTriggers()) {
			if (st != this && st.getName().equals(name)) {
				return false;
			}
		}
		this.name = name;
		return true;
	}

	public String getTreePath() {
		return tree_path;
	}

	public void setTreePath(String treePath) {
		tree_path = treePath;
	}

	public boolean addTriggerEvent(Class<? extends Event> event) {
		if (!event_names.contains(event.getName())) {		
			event_types = null;
			event_names.add(event.getName());
			return true;
		}
		return false;
	}
	
	public boolean removeTriggerEvent(Class<? extends Event> event) {
		event_types = null;
		return event_names.remove(event.getName());
	}
	
	@SuppressWarnings("unchecked")
	public SortedSet<Class<? extends Event>> getEventTypes() {
		if (event_types == null) {
			event_types = new TreeSet<Class<? extends Event>>(this);
			for (String tn : event_names) {
				try {
					event_types.add((Class<? extends Event>)Class.forName(tn));
				} catch(Exception err) {
					err.printStackTrace();
				}
			}
		}
		return event_types;
	}

	@Override
	public int compare(Class<?> o1, Class<?> o2) {
		return CUtil.getStringCompare().compare(o1.getName(), o2.getName());
	}
}
