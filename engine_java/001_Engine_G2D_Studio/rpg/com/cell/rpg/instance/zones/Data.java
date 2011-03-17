package com.cell.rpg.instance.zones;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.cell.CUtil;
import com.cell.util.Pair;

/**
 * 副本进度，进度指的是，当玩家在此副本中游戏时，杀死了BOSS，触发了事件，副本中的数据将会改变。
 * @author WAZA
 */
public class Data implements Serializable, Iterable<Entry<String, Object>>
{
	private static final long serialVersionUID = 1L;
	
	private LinkedHashMap<String, Object> values = new LinkedHashMap<String, Object>();
	
	public Object put(String key, Object value) {
		return values.put(key, value);
	}
	
	public Object get(String key) {
		return values.get(key);
	}

	public boolean containsKey(String key) {
		return values.containsKey(key);
	}
	
	public Object remove(String key) {
		return values.remove(key);
	}
	
	public Iterator<Entry<String, Object>> iterator() {
		return values.entrySet().iterator();
	}
	
	public Set<Entry<String, Object>> entrySet() {
		return values.entrySet();
	}
	
	public Set<String> keySet() {
		return values.keySet();
	}
	
	public int size() {
		return values.size();
	}
	
	public HashMap<String, Object> asMap() {
		return new HashMap<String, Object>(values);
	}
}
