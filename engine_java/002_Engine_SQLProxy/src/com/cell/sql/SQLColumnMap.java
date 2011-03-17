/**
 * 
 */
package com.cell.sql;

import java.util.Collection;

/**
 * 抽象了对数据库表读写的原子操作
 * @author WAZA
 * @param <K>
 * @param <V>
 * @see SQLColumnManager
 */
public interface SQLColumnMap<K, V>
{
	public Collection<V>	values();

	public V 				put(K key, V value);

	public V 				get(Object key);

	public V 				remove(Object key);

	public int	 			size();
}