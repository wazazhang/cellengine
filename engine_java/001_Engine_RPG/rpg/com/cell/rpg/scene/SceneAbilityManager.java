package com.cell.rpg.scene;

import java.util.Set;

import com.g2d.editor.property.PropertyEditor;

public abstract class SceneAbilityManager
{
//	-------------------------------------------------------------------------
	static private SceneAbilityManager manager;
	static public void setManager(SceneAbilityManager m){
		manager = m;
	}
	static public SceneAbilityManager getManager() {
		return manager;
	}
//	-------------------------------------------------------------------------
	
	abstract public Set<Class<?>> 			getAllTypes();
	
	abstract public Set<PropertyEditor<?>>	getEditAdapters();
}
