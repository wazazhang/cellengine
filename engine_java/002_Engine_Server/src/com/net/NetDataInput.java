package com.net;

import java.io.DataInput;
import java.io.IOException;
import java.lang.reflect.Array;

import com.cell.CUtil;
import com.cell.io.ExternalizableUtil;

public abstract class NetDataInput implements DataInput
{
	
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
	

	public <T extends com.net.ExternalizableMessage> T readExternal(Class<T> cls) throws IOException {
		int type = readInt();
		if (type != 0) {
			try {
				T data = cls.newInstance();
				data.readExternal(this);
				return data;
			} catch (Exception e) {
				throw new IOException(e);
			}
		}
		return null;
	}
	

	public <T extends ExternalizableMessage> T[] readExternalArray(Class<T> type) throws IOException {
		T[] ret = CUtil.newArray(type, readInt());
		for (int i = 0; i < ret.length; i++) {
			ret[i] = readExternal(type);
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


	public Object readAnyArray(Class<?> type) throws IOException {
		int count = readInt();
		if (count == 0) {
			return null;
		}
		Class<?> component_type = type.getComponentType();
		if (count < 0) { // 表示成员还是个数组
			count = -count;
			Object array = Array.newInstance(component_type, count);
			for (int i = 0; i < count; i++) {
				Array.set(array, i, readAnyArray(component_type));
			}
			return array;
		} else if (count > 0) { // 表示成员是个通常对象
			Object array = Array.newInstance(component_type, count);
			byte component_data_type = readByte();
			for (int i = 0; i < count; i++) {
				Array.set(array, i, readAny(component_data_type, component_type));
			}
			return array;
		}
		return null;
	}
	
	private Object readAny(byte component_data_type, Class<?> component_type) throws IOException {
		switch (component_data_type) {
		case NetDataTypes.TYPE_EXTERNALIZABLE:
			return readAnyExternal(component_type);
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
		case NetDataTypes.TYPE_OBJECT:
			return readObject(component_type);
		default:
			return null;
		}
	}
	
	private ExternalizableMessage readAnyExternal(Class<?> cls) throws IOException {
		int type = readInt();
		if (type != 0) {
			try {
				ExternalizableMessage data = (ExternalizableMessage)cls.newInstance();
				data.readExternal(this);
				return data;
			} catch (Exception e) {
				throw new IOException(e);
			}
		}
		return null;
	}

//	-----------------------------------------------------------------------------------------------

	
	
	
	
	abstract public<T> T readObject(Class<T> type) throws IOException;

	abstract public ExternalizableFactory getFactory();
	
}

