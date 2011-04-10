package com.net;

import java.lang.reflect.Array;




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
	
	public static String toTypeName(byte type) {
		switch (type) {
		case NetDataTypes.TYPE_EXTERNALIZABLE:
			return "TYPE_EXTERNALIZABLE";
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
		case NetDataTypes.TYPE_OBJECT:
			return "TYPE_OBJECT";
		default:
			return null;
		}
	}
	
	public static byte getArrayCompomentType(Class<?> type, ExternalizableFactory factory) 
	{
		if (type.isArray()) {
			return getArrayCompomentType(type.getComponentType(), factory);
		}
		else if (type.equals(boolean.class)) {
			return TYPE_BOOLEAN;
		}
		else if (type.equals(byte.class)) {
			return TYPE_BYTE;
		}
		else if (type.equals(char.class)) {
			return TYPE_CHAR;
		}
		else if (type.equals(short.class)) {
			return TYPE_SHORT;
		}
		else if (type.equals(int.class)) {
			return TYPE_INT;
		}
		else if (type.equals(long.class)) {
			return TYPE_LONG;
		}
		else if (type.equals(float.class)) {
			return TYPE_FLOAT;
		}
		else if (type.equals(double.class)) {
			return TYPE_DOUBLE;
		}
		else if (type.equals(String.class)) {
			return TYPE_STRING;
		}
		else if (ExternalizableMessage.class.isAssignableFrom(type)) {
			return TYPE_EXTERNALIZABLE;
		}
		else {
			return TYPE_OBJECT;
		}
	}
}
