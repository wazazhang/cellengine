package com.net;

import java.io.DataInput;
import java.io.IOException;

public interface NetDataInput extends DataInput
{
	public boolean[] 	readBooleanArray() throws IOException;
	
	public char[] 		readCharArray() throws IOException;
	
	public byte[] 		readByteArray() throws IOException;
	
	public short[] 		readShortArray() throws IOException;
	
	public int[] 		readIntArray() throws IOException;
	
	public long[] 		readLongArray() throws IOException;
	
	public float[] 		readFloatArray() throws IOException;
	
	public double[] 	readDoubleArray() throws IOException;

	public String[] 	readUTFArray() throws IOException;

	
	
	public<T extends ExternalizableMessage> 
			T[] 		readExternalArray(Class<T> type) throws IOException;
	
	public <T extends ExternalizableMessage> 
			T 			readExternal(Class<T> type) throws IOException;

	public<T> T			readObject(Class<T> type) throws IOException;

	public<T> T[]		readObjectArray(Class<T> type) throws IOException;
	
	public ExternalizableFactory getFactory();
	
}

