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

	abstract public<T> T readObject(Class<T> type) throws IOException;

	abstract public <T extends ExternalizableMessage> T readExternal(Class<T> type) throws IOException;

	abstract public Object readAnyArray(Class<?> type) throws IOException ;

	abstract public ExternalizableFactory getFactory();
	
}

