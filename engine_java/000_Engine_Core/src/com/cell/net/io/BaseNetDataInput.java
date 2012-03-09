package com.cell.net.io;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.cell.CUtil;
import com.cell.exception.NotImplementedException;
import com.cell.io.ExternalizableUtil;
import com.cell.io.TextDeserialize;

public abstract class BaseNetDataInput implements NetDataInput
{	
	final protected ExternalizableFactory factory;

	public BaseNetDataInput(ExternalizableFactory factory) {
		this.factory = factory;
	}

	@Override
	final public ExternalizableFactory getFactory() {
		return factory;
	}
	
//	-----------------------------------------------------------------------------------------------
	
	public boolean[] readBooleanArray() throws IOException {
		return ExternalizableUtil.readBooleanArray(this);
	}
	
	public char[] readCharArray() throws IOException {
		return ExternalizableUtil.readCharArray(this);
	}
	
	public byte[] readByteArray() throws IOException {
		return ExternalizableUtil.readByteArray(this);
	}
	
	public short[] readShortArray() throws IOException {
		return ExternalizableUtil.readShortArray(this);
	}
	
	public int[] readIntArray() throws IOException {
		return ExternalizableUtil.readIntArray(this);
	}
	
	public long[] readLongArray() throws IOException {
		return ExternalizableUtil.readLongArray(this);
	}
	
	public float[] readFloatArray() throws IOException {
		return ExternalizableUtil.readFloatArray(this);
	}
	
	public double[] readDoubleArray() throws IOException {
		return ExternalizableUtil.readDoubleArray(this);
	}

	public String[] readUTFArray() throws IOException {
		String[] ret = new String[readInt()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = readUTF();
		}
		return ret;
	}
	

	public <T> T readExternal(Class<T> cls) throws IOException {
		int type = readInt();
		if (type != 0) {
			try {
				T data = cls.newInstance();
				((ExternalizableMessage)data).readExternal(this);
				return data;
			} catch (Exception e) {
				throw new IOException(cls.getCanonicalName(), e);
			}
		}
		return null;
	}
	

	public <T> T[] readExternalArray(Class<T> type) throws IOException {
		T[] ret = CUtil.newArray(type, readInt());
		for (int i = 0; i < ret.length; i++) {
			ret[i] = readExternal(type);
		}
		return ret;
	}

	
	public <T> T readMutual(Class<T> cls) throws IOException {
		int type = readInt();
		if (type != 0) {
			try {
				T data = cls.newInstance();
				this.factory.getMutualCodec().readMutual((MutualMessage)data, this);
				return data;
			} catch (Exception e) {
				throw new IOException(cls.getCanonicalName(), e);
			}
		}
		return null;
	}
	

	public <T> T[] readMutualArray(Class<T> type) throws IOException {
		T[] ret = CUtil.newArray(type, readInt());
		for (int i = 0; i < ret.length; i++) {
			ret[i] = readMutual(type);
		}
		return ret;
	}
	
	
	
	public <T> T[] readObjectArray(Class<T> type) throws IOException {
		T[] ret = CUtil.newArray(type, readInt());
		for (int i = 0; i < ret.length; i++) {
			ret[i] = readObject(type);
		}
		return ret;
	}


//	-----------------------------------------------------------------------------------------------


	public Object readAnyArray(Class<?> type, byte component_data_type) throws IOException {
		int count = readInt();
		if (count == 0) {
			return null;
		}
		Class<?> component_type = type.getComponentType();
		if (count < 0) { // 表示成员还是个数组
			count = -count;
			Object array = Array.newInstance(component_type, count);
			for (int i = 0; i < count; i++) {
				Array.set(array, i, readAnyArray(component_type, component_data_type));
			}
			return array;
		} else if (count > 0) { // 表示成员是个通常对象
			Object array = Array.newInstance(component_type, count);
			for (int i = 0; i < count; i++) {
				Array.set(array, i, readAny(component_data_type, component_type));
			}
			return array;
		}
		return null;
	}

	protected ExternalizableMessage readAnyExternal(Class<?> cls) throws IOException {
		int type = readInt();
		if (type != 0) {
			try {
				ExternalizableMessage data = (ExternalizableMessage)cls.newInstance();
				data.readExternal(this);
				return data;
			} catch (Exception e) {
				throw new IOException(cls.getCanonicalName(), e);
			}
		}
		return null;
	}
	

	protected MutualMessage readAnyMutual(Class<?> cls) throws IOException {
		int type = readInt();
		if (type != 0) {
			try {
				MutualMessage data = (MutualMessage)cls.newInstance();
				this.factory.getMutualCodec().readMutual(data, this);
				return data;
			} catch (Exception e) {
				throw new IOException(cls.getCanonicalName(), e);
			}
		}
		return null;
	}

	protected Object readAny(byte component_data_type, Class<?> component_type) throws IOException {
		switch (component_data_type) {
		case NetDataTypes.TYPE_EXTERNALIZABLE:
			return readAnyExternal(component_type);	
		case NetDataTypes.TYPE_MUTUAL:
			return readAnyMutual(component_type);
		case NetDataTypes.TYPE_BOOLEAN:
			return readBoolean();
		case NetDataTypes.TYPE_BYTE:
			return readByte();
		case NetDataTypes.TYPE_CHAR:
			return readChar();
		case NetDataTypes.TYPE_SHORT:
			return readShort();
		case NetDataTypes.TYPE_INT:
			return readInt();
		case NetDataTypes.TYPE_LONG:
			return readLong();
		case NetDataTypes.TYPE_FLOAT:
			return readFloat();
		case NetDataTypes.TYPE_DOUBLE:
			return readDouble();
			
		case NetDataTypes.TYPE_STRING: 
			return readUTF();
		case NetDataTypes.TYPE_DATE: 
			return readDate(component_type);
			
		case NetDataTypes.TYPE_OBJECT:
			return readObject(component_type);
		default:
			return null;
		}
	}
	
	protected Object readAnyMutual(byte component_data_type) throws IOException {
		switch (component_data_type) {
//		case NetDataTypes.TYPE_EXTERNALIZABLE:
//			return readAnyExternal(component_type);	
		case NetDataTypes.TYPE_MUTUAL:
			int mutualType = readInt();
			if (mutualType == 0) {
				return null;
			}
			try {
				MutualMessage data = (MutualMessage)(factory.createMessage(mutualType));
				factory.getMutualCodec().readMutual(data, this);
				return data;
			} catch (Exception e) {
				throw new IOException("crate message : " + mutualType, e);
			}
		case NetDataTypes.TYPE_BOOLEAN:
			return readBoolean();
		case NetDataTypes.TYPE_BYTE:
			return readByte();
		case NetDataTypes.TYPE_CHAR:
			return readChar();
		case NetDataTypes.TYPE_SHORT:
			return readShort();
		case NetDataTypes.TYPE_INT:
			return readInt();
		case NetDataTypes.TYPE_LONG:
			return readLong();
		case NetDataTypes.TYPE_FLOAT:
			return readFloat();
		case NetDataTypes.TYPE_DOUBLE:
			return readDouble();
		case NetDataTypes.TYPE_STRING: 
			return readUTF();
		case NetDataTypes.TYPE_DATE: 
			return readDate(java.sql.Date.class);
			
//		case NetDataTypes.TYPE_OBJECT:
//			return readObject(component_type);
		default:
			return null;
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<?> readCollection(Class<?> collectionType, byte compNetType) throws IOException {
		int size = readInt();
		try {
			Collection ret = (Collection) collectionType.newInstance();
			if (size > 0) {
				for (int i = 0; i < size; i++) {
					Object data = readAnyMutual(compNetType);
					ret.add(data);
				}
			}
			return ret;
		} catch (InstantiationException e) {
			throw new IOException(e);
		} catch (IllegalAccessException e) {
			throw new IOException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<?,?> readMap(Class<?> mapType, byte keyNetType, byte valueNetType) throws IOException {
		int size = readInt();
		try {
			Map ret = (Map)mapType.newInstance();
			if (size > 0) {
				for (int i=0; i<size; i++) {
					Object key   = readAnyMutual(keyNetType);
					Object value = readAnyMutual(valueNetType);
					ret.put(key, value);
				}
			}
			return ret;
		} catch (InstantiationException e) {
			throw new IOException(e);
		} catch (IllegalAccessException e) {
			throw new IOException(e);
		}
	}
	
	@Override
	public <T> T readDate(Class<T> cls) throws IOException 
	{
		try 
		{
			byte size = readByte();
			if (size > 0) 
			{
				short YY = readShort();
				byte MM = readByte();
				byte DD = readByte();
				
				byte hh = readByte();
				byte mm = readByte();
				byte ss = readByte();
				
				short ms = readShort();

				Date ret = (Date)cls.newInstance();
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.MILLISECOND, ms);
				cal.set(YY, MM, DD, hh, mm, ss);
				ret.setTime(cal.getTimeInMillis());
				
				return (T)ret;
			}
		} catch (IOException e1) {
			throw e1;
		} catch (Exception e2) {
			throw new IOException(e2);
		}
		return null;
	}
}
