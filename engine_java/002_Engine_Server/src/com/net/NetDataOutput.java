package com.net;

import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;

import com.cell.io.ExternalizableUtil;

public abstract class NetDataOutput implements DataOutput
{

	public void writeBooleanArray(boolean[] bools) throws IOException {
		ExternalizableUtil.writeBooleanArray(this, bools);
	}
	
	public void writeCharArray(char[] chars) throws IOException {
		ExternalizableUtil.writeCharArray(this, chars);
	}
	
	public void writeByteArray(byte[] bytes) throws IOException {
		ExternalizableUtil.writeByteArray(this, bytes);
	}
	
	public void writeShortArray(short[] shorts) throws IOException {
		ExternalizableUtil.writeShortArray(this, shorts);
	}
	
	public void writeIntArray(int[] ints) throws IOException {
		ExternalizableUtil.writeIntArray(this, ints);
	}
	
	public void writeLongArray(long[] longs) throws IOException {
		ExternalizableUtil.writeLongArray(this, longs);
	}
	
	public void writeFloatArray(float[] floats) throws IOException {
		ExternalizableUtil.writeFloatArray(this, floats);
	}
	
	public void writeDoubleArray(double[] doubles) throws IOException {
		ExternalizableUtil.writeDoubleArray(this, doubles);
	}
	
	public void writeUTFArray(String[] data) throws IOException {
		if (data != null) {
			writeInt(data.length);
			for (String d : data) {
				writeUTF(d);
			}
		} else {
			writeInt(0);
		}
	}

	public <T extends ExternalizableMessage> void writeExternalArray(T[] data) throws IOException {
		if (data != null) {
			writeInt(data.length);
			for (T d : data) {
				writeExternal(d);
			}
		} else {
			writeInt(0);
		}
	}
 
	public void writeObjectArray(Object[] data) throws IOException {
		if (data != null) {
			writeInt(data.length);
			for (Object d : data) {
				writeObject(d);
			}
		} else {
			writeInt(0);
		}
	}
	

	abstract public void writeObject(Object data) throws IOException;
	
	abstract public<T extends ExternalizableMessage> void writeExternal(T data) throws IOException;

	abstract public void writeAnyArray(Object array) throws IOException ;
	
	abstract public ExternalizableFactory getFactory();
	
	
}
