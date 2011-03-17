package com.cell.util;

import java.util.Collection;
import java.util.Map;

public class PairEntry<K, V> extends Pair<K, V>
{
	private static final long serialVersionUID = 1L;
	
	public PairEntry(K k, V v) {
		super(k, v);
	}
	
	public PairEntry() {}

	/**
	 * @return the key
	 */
	synchronized public K getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	synchronized public void setKey(K key) {
		this.key = key;
	}

	/**
	 * @param key the key to set
	 */
	synchronized public V set(K key, V value) {
		V old = this.value;
		this.key = key;
		this.value = value;
		return old;
	}

	/**
	 * @return the value
	 */
	synchronized public V getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	synchronized public V setValue(V value) {
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
	public static<K, V> void putAll(Map<K, V> map, Collection<PairEntry<K, V>> pairs) {
		for (PairEntry<K, V> pair : pairs) {
			map.put(pair.getKey(), pair.getValue());
		}
	}
}
