package com.cell.rpg.scene.script;

public interface Scriptable 
{
	/**
	 * @param key
	 * @param value
	 * @return old value
	 */
	public Object put(String key, Object value);
	
	/**
	 * @param key
	 * @return
	 */
	public Object get(String key);
}
