package com.net;

import java.lang.reflect.Array;



public class NetDataTypes
{
	public final static int TYPE_BOOLEAN	= -1;
	public final static int TYPE_BYTE		= -2;
	public final static int TYPE_SHORT		= -3;
	public final static int TYPE_CHAR		= -4;
	public final static int TYPE_INT		= -5;
	public final static int TYPE_LONG		= -6;
	public final static int TYPE_FLOAT		= -7;
	public final static int TYPE_DOUBLE		= -8;
	public final static int TYPE_OBJECT		= -9;
	
	public static int getArrayCompomentType(Class<?> type, ExternalizableFactory factory) 
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
		else if (ExternalizableMessage.class.isAssignableFrom(type)) {
			return factory.getType(type);
		}
		else {
			return TYPE_OBJECT;
		}
	}
}
