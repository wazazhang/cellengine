package com.cell.reflect;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import com.cell.CUtil;
import com.cell.io.TextDeserialize;
import com.cell.util.StringUtil;

public class Parser 
{
	final public static String PERFIX_RADIX_16 = "0x";
	
	private static HashMap<Class<?>, IObjectStringParser> s_parser_map_ = new HashMap<Class<?>, IObjectStringParser>();
	private static SimpleParser s_simple_parser = new SimpleParser();
	
	public static void registerObjectStringParser(Class<?> type, IObjectStringParser parser)
	{
		s_parser_map_.put(type, parser);
	}

	
	@SuppressWarnings("unchecked")
	public static <T> T stringToObject(String str, Class<T> return_type) 
	{	
		try 
		{
			IObjectStringParser parser = s_parser_map_.get(return_type);
			if (parser != null) {
				return (T)parser.parseFrom(str, return_type);
			}	
			if (return_type.isEnum()) {
				Class<? extends Enum> ec = (Class<? extends Enum>)return_type;
				return return_type.cast(Enum.valueOf(ec, str.trim()));
			}

			Object obj = s_simple_parser.parseFrom(str, return_type);
			if (obj != null) {
				if (return_type.equals(boolean.class)) {
					return (T)obj;
				} else if (return_type.isPrimitive() ) {
					return Parser.castNumber(obj, return_type);
				} else {
					return return_type.cast(obj);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String objectToString(Object obj) {
		if (obj != null) {
			return objectToString(obj, obj.getClass());
		}
		return null;
	}
	public static String objectToString(Object obj, Class<?> type) 
	{
		if (obj != null) {
			IObjectStringParser parser = s_parser_map_.get(type);
			if (parser != null) {
				return parser.toString(obj);
			}
			String str = s_simple_parser.toString(obj);
			if (str != null) {
				return str;
			}			
			return obj.toString();
		}
		return null;
	}
	
	public static <T> T cast(Object obj, Class<T> return_type)
	{
		T ret = castNumber(obj, return_type);
		
		if ( (ret == null) && (obj instanceof String) )
			ret = stringToObject((String)obj, return_type);
			
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static final <T> T castNumber(Object obj, Class<T> return_type)
	{
		// 基础类型
		if (obj instanceof Number) {
			Number num = (Number)obj;
			if (return_type.equals(Byte.class) || return_type.equals(byte.class)) {
				return (T)(new Byte(num.byteValue()));
			} 
			if (return_type.equals(Short.class) || return_type.equals(short.class)) {
				return (T)(new Short(num.shortValue()));
			} 
			if (return_type.equals(Integer.class) || return_type.equals(int.class)) {
				return (T)(new Integer(num.intValue()));
			} 
			if (return_type.equals(Long.class) || return_type.equals(long.class)) {
				return (T)(new Long(num.longValue()));
			} 
			if (return_type.equals(Float.class) || return_type.equals(float.class)) {
				return (T)(new Float(num.floatValue()));
			} 
			if (return_type.equals(Double.class) || return_type.equals(double.class)) {
				return (T)(new Double(num.doubleValue()));
			}
		}
		return null;
	}

	public static boolean isNumber(Class<?> cls)
	{
		// 基础类型
		if (Number.class.isAssignableFrom(cls)) {
			return true;
		}
		if (cls.equals(byte.class)) {
			return true;
		} 
		if (cls.equals(short.class)) {
			return true;
		} 
		if (cls.equals(int.class)) {
			return true;
		} 
		if (cls.equals(long.class)) {
			return true;
		} 
		if (cls.equals(float.class)) {
			return true;
		} 
		if (cls.equals(double.class)) {
			return true;
		}
		return false;
	}
	
	public static Class<?> classForName(String name) throws ClassNotFoundException {
		return classForName(name, null);
	}
	
	public static Class<?> classForName(String name, ClassLoader loader) throws ClassNotFoundException {
		if (boolean.class.getName().equals(name)) {
			return boolean.class;
		}
		if (byte.class.getName().equals(name)) {
			return byte.class;
		} 
		if (short.class.getName().equals(name)) {
			return short.class;
		} 
		if (int.class.getName().equals(name)) {
			return int.class;
		} 
		if (long.class.getName().equals(name)) {
			return long.class;
		} 
		if (float.class.getName().equals(name)) {
			return float.class;
		} 
		if (double.class.getName().equals(name)) {
			return double.class;
		}
		if (loader!=null) {
			return Class.forName(name, true, loader);
		} else {
			return Class.forName(name);
		}
	}
	
	
	
//	--------------------------------------------------------------------------------------------------------------------
	
	public static byte parseByte(String str) 
	{
		long full = str.startsWith(PERFIX_RADIX_16)? 
				Long.parseLong(str.substring(PERFIX_RADIX_16.length()), 16) : Long.parseLong(str);
		return (byte)(full & 0xff);
	}
	
	public static short parseShort(String str) 
	{
		long full = str.startsWith(PERFIX_RADIX_16)? 
				Long.parseLong(str.substring(PERFIX_RADIX_16.length()), 16) : Long.parseLong(str);
		return (short)(full & 0xffff);
	}
	
	public static int parseInteger(String str) 
	{
		long full = str.startsWith(PERFIX_RADIX_16)? 
				Long.parseLong(str.substring(PERFIX_RADIX_16.length()), 16) : Long.parseLong(str);
		return (int)(full & 0xffffffff);
	}
	
	public static long parseLong(String str) 
	{
		long full = str.startsWith(PERFIX_RADIX_16)? 
				Long.parseLong(str.substring(PERFIX_RADIX_16.length()), 16) : Long.parseLong(str);
		return full;
	}
	
	public static float parseFloat(String str) 
	{
		if (str.endsWith("%")) {
			str = str.substring(0, str.length()-1);
			float ret = Float.parseFloat(str);
			ret = ret / 100;
			return ret;
		} else {
			return Float.parseFloat(str);
		}
	}
	
	public static double parseDouble(String str) 
	{
		return Double.parseDouble(str);
	}
	
	public static char parseCharacter(String str)
	{
		return str.charAt(0);
	}
	
	public static boolean parseBoolean(String str)
	{
		if (str.equals("1"))
			return true;				
		return Boolean.parseBoolean(str);
	}
	
	public static class SimpleParser implements IObjectStringParser
	{
		@Override
		public Object parseFrom(String str, Class<?> type) 
		{
			if (type.equals(String.class)) {
				return str;
			}
			// ---------------------------------------------------------------------------
			// byte
			if (type.equals(Byte.class)) 
			{
				return new Byte(parseByte(str));
			}
			if (type.equals(byte.class)) 
			{
				return parseByte(str);
			}

			// ---------------------------------------------------------------------------
			// short
			if (type.equals(Short.class)) 
			{
				return new Short(parseShort(str));
			}
			if (type.equals(short.class)) 
			{
				return parseShort(str);
			}

			// ---------------------------------------------------------------------------
			// int
			if (type.equals(Integer.class)) 
			{
				return new Integer(parseInteger(str));
			}
			if (type.equals(int.class)) 
			{
				return parseInteger(str);
			}

			// ---------------------------------------------------------------------------
			// long
			if (type.equals(Long.class)) 
			{
				return new Long(parseLong(str));
			}
			if (type.equals(long.class)) 
			{
				return parseLong(str);
			}
			// ---------------------------------------------------------------------------
			// float
			if (type.equals(Float.class)) 
			{
				return new Float(parseFloat(str));
			}
			if (type.equals(float.class)) 
			{
				return parseFloat(str);
			}
			// ---------------------------------------------------------------------------
			// double
			if (type.equals(Double.class)) 
			{
				return new Double(parseDouble(str));
			}
			if (type.equals(double.class)) 
			{
				return parseDouble(str);
			}
			// ---------------------------------------------------------------------------
			// char
			if (type.equals(Character.class)) 
			{
				return new Character(parseCharacter(str));
			}
			if (type.equals(type.equals(char.class))) 
			{
				return parseCharacter(str);
			}
			// ---------------------------------------------------------------------------
			// boolean
			if (type.equals(Boolean.class)) 
			{	
				return new Boolean(parseBoolean(str));
			}
			if (type.equals(boolean.class)) 
			{	
				return parseBoolean(str);
			}
			
			// ---------------------------------------------------------------------------

			if (type.isArray() ) 
			{
				if (str.isEmpty()) {
					return Array.newInstance(type.getComponentType(), 0);
				}
				
				if (type.equals(Double[].class)) 
				{
					return StringUtil.getDoubleObjArray(str, ",");
				}
				if (type.equals(double[].class)) 
				{
					return StringUtil.getDoubleArray(str, ",");
				}
				
				if (type.equals(Float[].class)) 
				{
					return StringUtil.getFloatObjArray(str, ",");
				}
				if (type.equals(float[].class)) 
				{
					return StringUtil.getFloatArray(str, ",");
				}
				
				if (type.equals(Long[].class)) 
				{
					return StringUtil.getLongObjArray(str, ",");
				}
				if (type.equals(long[].class)) 
				{
					return StringUtil.getLongArray(str, ",");
				}
				
				if (type.equals(Integer[].class)) 
				{
					return StringUtil.getIntegerObjArray(str, ",");
				}
				if (type.equals(int[].class)) 
				{
					return StringUtil.getIntegerArray(str, ",");
				}
				

				if (type.equals(Short[].class)) 
				{
					return StringUtil.getShortObjArray(str, ",");
				}
				if (type.equals(short[].class)) 
				{
					return StringUtil.getShortArray(str, ",");
				}
				
				if (type.equals(Byte[].class)) 
				{
					return StringUtil.getByteObjArray(str, ",");
				}
				if (type.equals(byte[].class)) 
				{
					return StringUtil.getByteArray(str, ",");
				}
			}
			
			
			
			return null;
		}
	
		
		@Override
		public String toString(Object obj) {
			if (obj.getClass().isArray()) {
				return CUtil.arrayObjectToString(obj, ",", "");
			}
			return obj.toString();
		}
	}


}
