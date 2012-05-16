package com.cell.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.EnumSet;
import java.util.HashMap;

@SuppressWarnings({"unchecked", "rawtypes"})
public class EnumManager
{
	
	public static interface ValueEnum <K>
	{
		public K getValue();
	}
	
//////////////////////////////////////////////////////////////////////////
	
	static HashMap<Class, HashMap> Types = new HashMap<Class, HashMap>();
	
	static private HashMap getEnumMap(Class cls)
	{
		HashMap map = Types.get(cls);
		if (map == null) {
			map = new HashMap();
			for (Object e : EnumSet.allOf(cls)) {
				ValueEnum<?> sv = (ValueEnum<?>)e;
				map.put(sv.getValue(), sv);
			}
		}
		return map;
	}
	
	
	public static <K, V extends ValueEnum<K>> V getEnum(Class cls, K k) 
	{
		HashMap<K, V> map = getEnumMap(cls);		
		V v = map.get(k);
		if (v==null) {
			System.err.println(cls.getName() + " can not create from value \"" + k + "\"");
		}
		return v;
	}
	
	public static Object toEnum(Class cls, Object k) 
	{
		HashMap map = getEnumMap(cls);
		Object v = map.get(k);
		if (v==null) {
			System.err.println(
					cls.getName() + 
					" can not create from value \"" + k + "\"");
		}
		return v;
	}
	
	public static int getEnumCount(Class cls){
		HashMap map = Types.get(cls);
		return map.size();
	}
	
	
	public static Class getValueEnumGenreicType(Class enumType)
	{
		if (ValueEnum.class.isAssignableFrom(enumType)) {
			Type[] types = enumType.getGenericInterfaces();
			for (Type ic : types) {
				ParameterizedType pt = (ParameterizedType)ic;
				Type[] atp = pt.getActualTypeArguments();
				if (atp.length > 0) {
					return (Class)atp[0];
				}
			}
		}
		return null;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static<T> T valueOf(Class<T> cls, String name)
	{
		T[] cts = cls.getEnumConstants();
		for (T t : cts) {
			if (cts.toString().equals(name)) {
				return t;
			}
		}
		return null;
	}
}
