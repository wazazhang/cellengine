package com.cell.net.io;

import java.io.DataInput;
import java.io.IOException;

public interface NetDataInput extends DataInput
{
	public ExternalizableFactory getFactory();
	
//	-----------------------------------------------------------------------------------------------
	
	public boolean[] readBooleanArray() throws IOException ;
	
	public char[] readCharArray() throws IOException ;
	
	public byte[] readByteArray() throws IOException ;
	
	public short[] readShortArray() throws IOException ;
	
	public int[] readIntArray() throws IOException ;
	
	public long[] readLongArray() throws IOException ;
	
	public float[] readFloatArray() throws IOException ;
	
	public double[] readDoubleArray() throws IOException;

	public String[] readUTFArray() throws IOException;

	
	public <T> T 	readExternal(Class<T> cls) throws IOException ;
	public <T> T[] 	readExternalArray(Class<T> compomentType) throws IOException;

	
	public <T> T 	readMutual(Class<T> cls) throws IOException ;
	public <T> T[] 	readMutualArray(Class<T> compomentType) throws IOException;

	
	public <T> T 	readObject(Class<T> type) throws IOException;
	public <T> T[] 	readObjectArray(Class<T> compomentType) throws IOException ;
	
	
	public Object readAnyArray(Class<?> type, byte component_data_type) throws IOException;
	

//	-----------------------------------------------------------------------------------------------

//	-----------------------------------------------------------------------------------------------
//
//	public void readFully(String key, byte b[]) throws IOException;
//
//	public void readFully(String key, byte b[], int off, int len) throws IOException;
//
//	public boolean readBoolean(String key) throws IOException;
//
//	public byte readByte(String key) throws IOException;
//
//	public int readUnsignedByte(String key) throws IOException;
//
//	public short readShort(String key) throws IOException;
//
//	public int readUnsignedShort(String key) throws IOException;
//
//	public char readChar(String key) throws IOException;
//
//	public int readInt(String key) throws IOException;
//
//	public long readLong(String key) throws IOException;
//
//	public float readFloat(String key) throws IOException;
//
//	public double readDouble(String key) throws IOException;
//
//	public String readLine(String key) throws IOException;
//
//	public String readUTF(String key) throws IOException;
//
////	-----------------------------------------------------------------------------------------------
//	
//	public boolean[] readBooleanArray(String key) throws IOException ;
//	
//	public char[] readCharArray(String key) throws IOException ;
//	
//	public byte[] readByteArray(String key) throws IOException ;
//	
//	public short[] readShortArray(String key) throws IOException ;
//	
//	public int[] readIntArray(String key) throws IOException ;
//	
//	public long[] readLongArray(String key) throws IOException ;
//	
//	public float[] readFloatArray(String key) throws IOException ;
//	
//	public double[] readDoubleArray(String key) throws IOException;
//
//	public String[] readUTFArray(String key) throws IOException;
//
//	public <T extends com.net.ExternalizableMessage> T readExternal(String key, Class<T> cls) throws IOException ;
//
//	public <T extends ExternalizableMessage> T[] readExternalArray(String key, Class<T> type) throws IOException;
//
//	public <T> T[] readObjectArray(String key, Class<T> type) throws IOException ;
//
//	public Object readAnyArray(String key, Class<?> type, byte component_data_type) throws IOException;
//	
//	public<T> T readObject(String key, Class<T> type) throws IOException;
//
//
////	-----------------------------------------------------------------------------------------------

}

