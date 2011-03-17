package com.cell.util;

import java.util.concurrent.ConcurrentHashMap;

public class ObjectPoolManager 
{
	static ConcurrentHashMap<Class<?>, ObjectPool<?>> pools = new ConcurrentHashMap<Class<?>, ObjectPool<?>>();
	
	@SuppressWarnings("unchecked")
	public static<T extends Object> ObjectPool<T> getPool(Class<T> cls)
	{
		return (ObjectPool<T>)pools.get(cls);
	}
	
	public static<T extends Object> ObjectPool<?> registObjectPool(Class<T> cls, ObjectPool<T> pool)
	{
		System.out.println("regist object pool : " + cls.getName() + " : " + pool.getClass().getName() + " size=" + pool.getSize());
		return pools.put(cls, pool);
	}
}
