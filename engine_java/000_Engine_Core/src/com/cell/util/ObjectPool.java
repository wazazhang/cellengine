package com.cell.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class ObjectPool<T>
{
	private int 		core_pool_size;		// 该对象池的属性参数对象
	private Queue<T> 	pool;				// 用于存放对象的池

	public ObjectPool(int core_pool_size) {
		this.core_pool_size = core_pool_size;
		pool = new ConcurrentLinkedQueue<T>();
	}

	abstract protected T createObject();

	public int getSize() {
		return pool.size();
	}
	
	/**
	 * 从池中取出对象，如果池为空，则创建新对象
	 * @return
	 */
	synchronized public T getObject() {
		if (pool.isEmpty()) {
			return createObject();
		} else {
			while(pool.size() > core_pool_size) {
				pool.poll();
			}
			// 如果当前池中有可用的对象，就直接从池中取出对象
			return pool.poll();
		}
	}

	/**
	 * 将不用的对象返回到池中
	 * @param object
	 */
	synchronized public void returnObject(T object) {
		pool.add(object);
	}

}
