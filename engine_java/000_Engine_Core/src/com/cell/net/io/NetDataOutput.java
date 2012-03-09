package com.cell.net.io;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public interface NetDataOutput extends DataOutput
{
	public ExternalizableFactory getFactory();

//	-----------------------------------------------------------------------------------------------

	public void writeBooleanArray(boolean[] bools) throws IOException;
	
	public void writeCharArray(char[] chars) throws IOException ;
	
	public void writeByteArray(byte[] bytes) throws IOException ;
	
	public void writeShortArray(short[] shorts) throws IOException ;
	
	public void writeIntArray(int[] ints) throws IOException;
	
	public void writeLongArray(long[] longs) throws IOException ;
	
	public void writeFloatArray(float[] floats) throws IOException;
	
	public void writeDoubleArray(double[] doubles) throws IOException ;
	
	public void writeUTFArray(String[] data) throws IOException ;

	
	public void writeObject(Object data) throws IOException;
	public void writeObjectArray(Object array) throws IOException;
	

	public void writeExternal(ExternalizableMessage data) throws IOException;
	public void writeExternalArray(Object array) throws IOException ;


	public void writeMutual(MutualMessage data) throws IOException;
	public void writeMutualArray(Object array) throws IOException ;
 
	public void writeAnyArray(Object array, byte component_data_type) throws IOException;
		
	
	public void writeCollection(Collection<?> array, byte compNetType) throws IOException ;

	public void writeMap(Map<?,?> map, byte keyNetType, byte valueNetType) throws IOException ;
	
//	-----------------------------------------------------------------------------------------------

	public void writeDate(Date date) throws IOException;
	
//// -----------------------------------------------------------------------------------------------
//
//	void write(String key, byte b[]) throws IOException;
//
//	void write(String key, byte b[], int off, int len) throws IOException;
//
//	void writeBoolean(String key, boolean v) throws IOException;
//
//	void writeByte(String key, int v) throws IOException;
//
//	void writeShort(String key, int v) throws IOException;
//
//	void writeChar(String key, int v) throws IOException;
//
//	void writeInt(String key, int v) throws IOException;
//
//	void writeLong(String key, long v) throws IOException;
//
//	void writeFloat(String key, float v) throws IOException;
//
//	void writeDouble(String key, double v) throws IOException;
//
//	void writeBytes(String key, String s) throws IOException;
//
//	void writeChars(String key, String s) throws IOException;
//
//	void writeUTF(String key, String s) throws IOException;
//
//	// -----------------------------------------------------------------------------------------------
//
//	public void writeBooleanArray(String key, boolean[] bools) throws IOException;
//
//	public void writeCharArray(String key, char[] chars) throws IOException;
//
//	public void writeByteArray(String key, byte[] bytes) throws IOException;
//
//	public void writeShortArray(String key, short[] shorts) throws IOException;
//
//	public void writeIntArray(String key, int[] ints) throws IOException;
//
//	public void writeLongArray(String key, long[] longs) throws IOException;
//
//	public void writeFloatArray(String key, float[] floats) throws IOException;
//
//	public void writeDoubleArray(String key, double[] doubles) throws IOException;
//
//	public void writeUTFArray(String key, String[] data) throws IOException;
//
//	public <T extends ExternalizableMessage> void writeExternalArray(String key, T[] data)
//			throws IOException;
//
//	public void writeObjectArray(String key, Object[] data) throws IOException;
//
//	public void writeAnyArray(String key, Object array, byte component_data_type)
//			throws IOException;
//
//	public void writeExternal(String key, ExternalizableMessage data) throws IOException;
//
//	public void writeObject(String key, Object data) throws IOException;
//
//	// -----------------------------------------------------------------------------------------------

}
