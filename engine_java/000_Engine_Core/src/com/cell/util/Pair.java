package com.cell.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class Pair<K, V> implements Serializable, Map.Entry<K, V>
{
	private static final long serialVersionUID = 1L;
	
	K key;
	V value;
	
	public Pair(K k, V v) {
		key = k;
		value = v;
	}
	
	public Pair() {}

	/**
	 * @return the key
	 */
	public K getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(K key) {
		this.key = key;
	}

	/**
	 * @param key the key to set
	 */
	public V set(K key, V value) {
		V old = this.value;
		this.key = key;
		this.value = value;
		return old;
	}

	/**
	 * @return the value
	 */
	public V getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public V setValue(V value) {
		V old = this.value;
		this.value = value;
		return old;
	}
	
	
	/**
	 * 将以一组Pair添加到Map里
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param pairs
	 */
	public static<K, V> void putAll(Map<K, V> map, Collection<Pair<K, V>> pairs) {
		for (Pair<K, V> pair : pairs) {
			map.put(pair.getKey(), pair.getValue());
		}
	}
}
