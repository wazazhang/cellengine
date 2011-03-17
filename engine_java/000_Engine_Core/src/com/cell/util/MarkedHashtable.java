package com.cell.util;

import java.util.Hashtable;

public class MarkedHashtable extends Hashtable<String, Object> 
{
	private static final long serialVersionUID = 1L;

//	------------------------------------------------------------------------------------

	public MarkedHashtable() {
		super();
	}
	
	public MarkedHashtable(int size) {
		super(size);
	}
	
//	------------------------------------------------------------------------------------

//	------------------------------------------------------------------------------------
	
	public <T> T get(String key, Class<T> type)
	{
		Object t = super.get(key);
		if (type.isInstance(t)) {
			return type.cast(t);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getObject(String key)
	{
		try {
			T t = (T)get(key);
			return t;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getObject(String key, T default_value)
	{
		try {
			T t = (T)get(key);
			if (t != null) {
				return t;
			} else {
				return default_value;
			}
		} catch (Throwable e) {}
		return default_value;
	}
	
	
//	------------------------------------------------------------------------------------
	
	public String getString(String key) {
		return getObject(key);
	}
	
	public byte getByte(String key) {
		return getObject(key);
	}

	public boolean getBoolean(String key) {
		return getObject(key);
	}

	public char getChar(String key) {
		return getObject(key);
	}
	
	public short getShort(String key) {
		return getObject(key);
	}

	public int getInt(String key) {
		return getObject(key);
	}

	public long getLong(String key) {
		return getObject(key);
	}
	
	public float getFloat(String key) {
		return getObject(key);
	}
	
	public double getDouble(String key) {
		return getObject(key);
	}
//	------------------------------------------------------------------------------------
	

	public String getString(String key, String d) {
		return getObject(key, d);
	}
	
	public byte getByte(String key, byte d) {
		return getObject(key, d);
	}

	public boolean getBoolean(String key, boolean d) {
		return getObject(key, d);
	}

	public char getChar(String key, char d) {
		return getObject(key, d);
	}
	
	public short getShort(String key, short d) {
		return getObject(key, d);
	}

	public int getInt(String key, int d) {
		return getObject(key, d);
	}

	public long getLong(String key, long d) {
		return getObject(key, d);
	}
	
	public float getFloat(String key, float d) {
		return getObject(key, d);
	}
	
	public double getDouble(String key, double d) {
		return getObject(key, d);
	}
	
//	------------------------------------------------------------------------------------
	
	public void putString(String key, String value) {
		put(key, value);
	}

	public void putByte(String key, byte value) {
		put(key, value);
	}

	public void putBoolean(String key, boolean value) {
		put(key, value);
	}

	public void putShort(String key, short value) {
		put(key, value);
	}

	public void putInt(String key, int value) {
		put(key, value);
	}

	public void putLong(String key, long value) {
		put(key, value);
	}

	public void putFloat(String key, float value) {
		put(key, value);
	}
	
	public void putDouble(String key, double value) {
		put(key, value);
	}
}
