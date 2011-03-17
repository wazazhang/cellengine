package com.cell.util;

import java.util.EnumSet;
import java.util.HashMap;

public class EnumManager
{
	public static interface ValueEnum <K>
	{
		public K getValue();
	}
	
	@SuppressWarnings("unchecked")
	static HashMap<Class, HashMap> Types = new HashMap<Class, HashMap>();
	
	@SuppressWarnings("unchecked")
	public static <K, V extends ValueEnum<K>> V getEnum(Class cls, K k) 
	{
		HashMap<K, V> map = Types.get(cls);
		if (map == null) {
			map = new HashMap<K, V>();
			for (Object s : EnumSet.allOf(cls)) {
				V sv = (V)s;
				map.put(sv.getValue(), sv);
			}
		}
		
		V v = map.get(k);
		
		if (v==null) {
			System.err.println(cls.getName() + " can not create from value \"" + k + "\"");
		}
		
		return v;
	}
	
	@SuppressWarnings("unchecked")
	public static <V extends Enum<V>> V toEnum(Class<V> cls, Object k) 
	{
		HashMap map = Types.get(cls);
		
		if (map == null) {
			map = new HashMap();
			for (Object e : EnumSet.allOf(cls)) {
				ValueEnum<?> sv = (ValueEnum<?>)e;
				map.put(sv.getValue(), sv);
//				System.out.println(sv.getValue() + " - " + sv);
			}
		}
		
		V v = (V)map.get(k);
		
		if (v==null) {
			System.err.println(cls.getName() + " can not create from value \"" + k + "\"");
		}
		
		return v;
	}
	
	@SuppressWarnings("unchecked")
	public static int getEnumCount(Class cls){
		HashMap map = Types.get(cls);
		return map.size();
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
}
