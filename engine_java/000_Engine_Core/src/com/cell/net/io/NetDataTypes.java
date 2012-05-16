package com.cell.net.io;

import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.Date;





public class NetDataTypes
{
	public final static byte TYPE_BOOLEAN		= -1;
	public final static byte TYPE_BYTE			= -2;
	public final static byte TYPE_SHORT			= -3;
	public final static byte TYPE_CHAR			= -4;
	public final static byte TYPE_INT			= -5;
	public final static byte TYPE_LONG			= -6;
	public final static byte TYPE_FLOAT			= -7;
	public final static byte TYPE_DOUBLE		= -8;
	
	public final static byte TYPE_STRING			= -9;
	public final static byte TYPE_OBJECT			= -10;
	public final static byte TYPE_EXTERNALIZABLE	= -11;
	public final static byte TYPE_MUTUAL			= -12;
	
	public final static byte TYPE_DATE			= -13;
	public final static byte TYPE_ENUM			= -14;
	
	public static String toTypeName(byte type) {
		switch (type) {
		case NetDataTypes.TYPE_EXTERNALIZABLE:
			return "TYPE_EXTERNALIZABLE";
		case NetDataTypes.TYPE_MUTUAL:
			return "TYPE_MUTUAL";
		case NetDataTypes.TYPE_BOOLEAN:
			return "TYPE_BOOLEAN";
		case NetDataTypes.TYPE_BYTE:
			return "TYPE_BYTE";
		case NetDataTypes.TYPE_CHAR:
			return "TYPE_CHAR";
		case NetDataTypes.TYPE_SHORT:
			return "TYPE_SHORT";
		case NetDataTypes.TYPE_INT:
			return "TYPE_INT";
		case NetDataTypes.TYPE_LONG:
			return "TYPE_LONG";
		case NetDataTypes.TYPE_FLOAT:
			return "TYPE_FLOAT";
		case NetDataTypes.TYPE_DOUBLE:
			return "TYPE_DOUBLE";
			
		case NetDataTypes.TYPE_STRING:
			return "TYPE_STRING";
			
		case NetDataTypes.TYPE_DATE:
			return "TYPE_DATE";
		case NetDataTypes.TYPE_ENUM:
			return "TYPE_ENUM";
			
		case NetDataTypes.TYPE_OBJECT:
			return "TYPE_OBJECT";
			
			
		default:
			return null;
		}
	}
	
	@SuppressWarnings({ "rawtypes"})
	public static byte getNetType(Class<?> type, ExternalizableFactory factory) 
	{
		if (type.equals(boolean.class) || type.equals(Boolean.class)) {
			return TYPE_BOOLEAN;
		}
		else if (type.equals(byte.class) || type.equals(Byte.class)) {
			return TYPE_BYTE;
		}
		else if (type.equals(char.class) || type.equals(Character.class)) {
			return TYPE_CHAR;
		}
		else if (type.equals(short.class) || type.equals(Short.class)) {
			return TYPE_SHORT;
		}
		else if (type.equals(int.class) || type.equals(Integer.class)) {
			return TYPE_INT;
		}
		else if (type.equals(long.class) || type.equals(Long.class)) {
			return TYPE_LONG;
		}
		else if (type.equals(float.class) || type.equals(Float.class)) {
			return TYPE_FLOAT;
		}
		else if (type.equals(double.class) || type.equals(Double.class)) {
			return TYPE_DOUBLE;
		}
		//
		else if (type.equals(String.class)) {
			return TYPE_STRING;
		}
		else if (Date.class.isAssignableFrom(type)) {
			return TYPE_DATE;
		}
		else if (type.isEnum()) {
			return TYPE_ENUM;
		}
		
		else if (ExternalizableMessage.class.isAssignableFrom(type)) {
			return TYPE_EXTERNALIZABLE;
		}
		else if (MutualMessage.class.isAssignableFrom(type)) {
			return TYPE_MUTUAL;
		}
		else {
			return TYPE_OBJECT;
		}
	}
	
	@SuppressWarnings({ "rawtypes"})
	public static byte getCollectionCompomentType(Collection array, ExternalizableFactory factory) 
	{
		for (Object o : array) {
			if ( o != null) {
				return getArrayCompomentType(o.getClass(), factory);
			}
		}
		return 0;
	}
	
	public static byte getArrayCompomentType(Class<?> type, ExternalizableFactory factory) 
	{
		if (type.isArray()) {
			return getArrayCompomentType(type.getComponentType(), factory);
		} else {
			 return getNetType(type, factory);
		}
	}

}
