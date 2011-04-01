package com.net;

import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;

public interface NetDataOutput extends DataOutput
{
	public void writeBooleanArray(boolean[] bools) throws IOException;
	
	public void writeCharArray(char[] chars) throws IOException;
	
	public void writeByteArray(byte[] bytes) throws IOException;
	
	public void writeShortArray(short[] shorts) throws IOException;
	
	public void writeIntArray(int[] ints) throws IOException;
	
	public void writeLongArray(long[] longs) throws IOException;
	
	public void writeFloatArray(float[] floats) throws IOException;
	
	public void writeDoubleArray(double[] doubles) throws IOException;

	public void writeUTFArray(String[] data) throws IOException;
	
	
	
	public void writeObject(Object data) throws IOException;
	public void writeObjectArray(Object[] data) throws IOException;
	
	
	public<T extends ExternalizableMessage> void writeExternal(T data) throws IOException;
	public<T extends ExternalizableMessage> void writeExternalArray(T[] data) throws IOException;
	

	public void writeAny(Object data, Class<?> component_type) throws IOException;
	public void writeAnyArray(Object data) throws IOException;

	
	public ExternalizableFactory getFactory();
	
	
}
