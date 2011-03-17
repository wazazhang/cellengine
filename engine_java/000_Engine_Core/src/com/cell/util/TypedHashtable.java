package com.cell.util;

import java.util.Hashtable;

public class TypedHashtable extends Hashtable<Class<?>, Object> 
{
	private static final long serialVersionUID = 1L;

	
	synchronized public <T> T get(Class<T> key) {
		return key.cast(super.get(key));
	}
	
	synchronized public <T> T put(Class<T> key, T value) {
		Object obj = super.put(key, value);
		if (obj != null) {
			return key.cast(obj);
		}
		return null;
	}
	
}
