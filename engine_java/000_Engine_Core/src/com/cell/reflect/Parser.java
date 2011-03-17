package com.cell.reflect;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import com.cell.io.TextDeserialize;
import com.cell.util.StringUtil;

public class Parser 
{
	final public static String PERFIX_RADIX_16 = "0x";
	
	private static HashMap<Class<?>, IObjectStringParser> s_parser_map_;
	
	private static void init()
	{
		s_parser_map_ = new HashMap<Class<?>, IObjectStringParser>();
		
		s_parser_map_.put(String.class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return str;
			}
		});		
		
		s_parser_map_.put(Byte.class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				long full = str.startsWith(PERFIX_RADIX_16)? 
						Long.parseLong(str.substring(PERFIX_RADIX_16.length()), 16) : Long.parseLong(str);

				return new Byte((byte)(full & 0xff));
			}
		});
		s_parser_map_.put(byte.class, s_parser_map_.get(Byte.class));
		
		s_parser_map_.put(Short.class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				long full = str.startsWith(PERFIX_RADIX_16)? 
						Long.parseLong(str.substring(PERFIX_RADIX_16.length()), 16) : Long.parseLong(str);

				return new Short((short)(full & 0xffff));
			}
		});
		s_parser_map_.put(short.class, s_parser_map_.get(Short.class));
		
		s_parser_map_.put(Integer.class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				long full = str.startsWith(PERFIX_RADIX_16)? 
						Long.parseLong(str.substring(PERFIX_RADIX_16.length()), 16) : Long.parseLong(str);

				return new Integer((int)(full & 0xffffffff));
			}
		});
		s_parser_map_.put(int.class, s_parser_map_.get(Integer.class));	
		
		s_parser_map_.put(Long.class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				long full = str.startsWith(PERFIX_RADIX_16)? 
						Long.parseLong(str.substring(PERFIX_RADIX_16.length()), 16) : Long.parseLong(str);

				return new Long(full);
			}
		});
		s_parser_map_.put(long.class, s_parser_map_.get(Long.class));		
		
		s_parser_map_.put(Float.class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return new Float(str);
			}
		});
		s_parser_map_.put(float.class, s_parser_map_.get(Float.class));
		
		s_parser_map_.put(Double.class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return new Double(str);
			}
		});	
		s_parser_map_.put(double.class, s_parser_map_.get(Double.class));
		
		s_parser_map_.put(Character.class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return new Character(str.charAt(0));
			}
		});	
		s_parser_map_.put(char.class, s_parser_map_.get(Character.class));	
		
		s_parser_map_.put(Boolean.class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return new Boolean(str);
			}
		});	
		s_parser_map_.put(boolean.class, s_parser_map_.get(Boolean.class));
		
		s_parser_map_.put(Double[].class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return StringUtil.getDoubleObjArray(str, ",");
			}
		});		
	
		s_parser_map_.put(double[].class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return StringUtil.getDoubleArray(str, ",");
			}
		});
		
		s_parser_map_.put(Float[].class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return StringUtil.getFloatObjArray(str, ",");
			}
		});		
	
		s_parser_map_.put(float[].class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return StringUtil.getFloatArray(str, ",");
			}
		});		
		
		s_parser_map_.put(Long[].class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return StringUtil.getLongObjArray(str, ",");
			}
		});		
	
		s_parser_map_.put(long[].class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return StringUtil.getLongArray(str, ",");
			}
		});		
		
		s_parser_map_.put(Integer[].class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return StringUtil.getIntegerObjArray(str, ",");
			}
		});		
	
		s_parser_map_.put(int[].class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return StringUtil.getIntegerArray(str, ",");
			}
		});
		
		s_parser_map_.put(Short[].class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return StringUtil.getShortObjArray(str, ",");
			}
		});		
	
		s_parser_map_.put(short[].class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return StringUtil.getShortArray(str, ",");
			}
		});
		
		s_parser_map_.put(Byte[].class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return StringUtil.getByteObjArray(str, ",");
			}
		});		
	
		s_parser_map_.put(byte[].class, new IObjectStringParser() 
		{
			@Override
			public Object parseFrom(String str) 
			{
				return StringUtil.getByteArray(str, ",");
			}
		});		
	};
	
	public static void registerObjectStringParser(Class type, IObjectStringParser parser)
	{
		s_parser_map_.put(type, parser);
	}

	
	@SuppressWarnings("unchecked")
	public static <T> T stringToObject(String str, Class<T> return_type) 
	{	
		try 
		{
			if (s_parser_map_ == null)
				Parser.init();
			
			IObjectStringParser parser = s_parser_map_.get(return_type);
			
			if (parser != null)
				return (T)parser.parseFrom(str);			

			if (return_type.isEnum()) {
				Class<? extends Enum> ec = (Class<? extends Enum>)return_type;
				return return_type.cast(Enum.valueOf(ec, str.trim()));
			}

//			if (return_type.isArray()) {
//				T type = return_type.newInstance();
//				Arrays.copyOf(type, newLength, newType);
//			}
//			
//			if (Collection.class.isAssignableFrom(return_type)) {
//				Collection ret = (Collection)return_type.newInstance();
//				String[] ls = str.split(",");
//				for (String l : ls) {
//					ret.add(stringToObject(l, return_type));
//				}
//			}
			
		} catch (Exception e) {
		}

		return null;
	}

	public static String objectToString(Object obj) 
	{
		return obj.toString();
	}
	
	public static <T> T castNumber(Object obj, Class<T> return_type)
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
}
