package com.cell.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.cell.CObject;


public class NIODeserialize extends IInput
{
	
	public static String getString(ByteBuffer buffer, String charSet) {
		{
	        int stringLen = buffer.getInt();
	        if (stringLen>=0){
		        byte []stringData = new byte[stringLen];
		        buffer.get(stringData);
		        try{
		        	return new String(stringData, charSet);
		        }catch(UnsupportedEncodingException err){
		        	err.printStackTrace();
		        }
		    }else{
	        	System.err.println("Deserialize.getString(): NegativeArraySize: "+stringLen);
	        }
	        return null;
        }
	}
	
	public static String getString(ByteBuffer buffer) {
		return getString(buffer, CObject.getEncoding());
	}
	
	public static byte getByte(ByteBuffer buffer) {
		 {
			return (byte)buffer.get(); 
		}
	}
	
	
	
	public static short getUnsignedByte(ByteBuffer buffer) {
		{
			return buffer.getShort();
        }
	}
	
	public static boolean getBoolean(ByteBuffer buffer) {
		 {
			return buffer.get() != 0;
		}
	}
	
	public static short getShort(ByteBuffer buffer) {
		{
			return buffer.getShort();
        }
	}
	
	public static int getUnsignedShort(ByteBuffer buffer) {
		{
			return buffer.getInt();
        }		
	}
	
	public static int getInt(ByteBuffer buffer) {
		{
			return buffer.getInt();
        }
	}
	
	public static long getUnsignedInt(ByteBuffer buffer) {
		{
			return buffer.getLong();
        }		
	}
	
	public static long getLong(ByteBuffer buffer) {
		{
			return buffer.getLong();
        }	
	}
	public static double getDouble(ByteBuffer istream) throws IOException {
		return Double.longBitsToDouble(getLong(istream));
	}

	public static float getFloat(ByteBuffer istream) throws IOException {
		return Float.intBitsToFloat(getInt(istream));
	}
	//////////////////////////////////////////////////////////////////////////////////////
	// get array
	
	public static String[] getStrings(ByteBuffer buffer, String charSet) {
		{
			int count = (int)NIODeserialize.getUnsignedInt(buffer);
			if (count > 0){
				String[] values = new String[count];
				for ( int i=0; i<count; ++i )
					values[i] = getString(buffer, charSet);
				return values;
			}
			return null;
		}
	}
	
	public static String[] getStrings(ByteBuffer buffer) {
		return getStrings(buffer, CObject.getEncoding());
	}
	
	public static boolean[] getBooleans(ByteBuffer buffer) 
	{
		{
			int count = (int)NIODeserialize.getUnsignedInt(buffer);
			if (count > 0)
			{
				boolean[] values = new boolean[count];
				for ( int i=0; i<count; ++i )
					values[i] = NIODeserialize.getBoolean(buffer);
				
				return values;
			}
			return null;
		}
	}	
	
	public static byte[] getBytes(ByteBuffer buffer) 
	{
		{
			int count = (int)NIODeserialize.getUnsignedInt(buffer);
			if (count > 0)
			{
				byte[] values = new byte[count];
				for ( int i=0; i<count; ++i )
					values[i] = NIODeserialize.getByte(buffer);
				return values;
			}
			return null;
		}
	}
	
	public static short[] getUnsignedBytes(ByteBuffer buffer) 
	{
		{
			int count = (int)NIODeserialize.getUnsignedInt(buffer);
			if (count > 0)
			{
				short[] values = new short[count];
				for ( int i=0; i<count; ++i )
					values[i] = NIODeserialize.getUnsignedByte(buffer);
				
				return values;
			}
			return null;
		}
	}
	
	public static short[] getShorts(ByteBuffer buffer) 
	{
		{
			int count = (int)NIODeserialize.getUnsignedInt(buffer);
			if (count > 0)
			{
				short[] values = new short[count];
				for ( int i=0; i<count; ++i )
					values[i] = NIODeserialize.getShort(buffer);
				
				return values;
			}
			return null;
		}
	}
	
	public static int[] getUnsignedShorts(ByteBuffer buffer) 
	{
		{
			int count = (int)NIODeserialize.getUnsignedInt(buffer);
			if (count > 0)
			{
				int[] values = new int[count];
				for ( int i=0; i<count; ++i )
					values[i] = NIODeserialize.getUnsignedShort(buffer);
				
				return values;
			}
			return null;
		}
	}	
	
	public static int[] getInts(ByteBuffer buffer) 
	{
		{
			int count = (int)NIODeserialize.getUnsignedInt(buffer);
			if (count > 0)
			{
				int[] values = new int[count];
				for ( int i=0; i<count; ++i )
					values[i] = NIODeserialize.getInt(buffer);
				
				return values;
			}
			return null;
		}
	}
	
	public static long[] getUnsignedInts(ByteBuffer buffer) 
	{
		{
			int count = (int)NIODeserialize.getUnsignedInt(buffer);
			if (count > 0)
			{
				long[] values = new long[count];
				for ( int i=0; i<count; ++i )
					values[i] = NIODeserialize.getUnsignedInt(buffer);
				
				return values;
			}
			return null;
		}
	}
	
	public static long[] getLongs(ByteBuffer buffer) 
	{
		{
			int count = (int)NIODeserialize.getUnsignedInt(buffer);
			if (count > 0)
			{
				long[] values = new long[count];
				for ( int i=0; i<count; ++i )
					values[i] = NIODeserialize.getLong(buffer);
				
				return values;
			}
			return null;
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------------------	
	

//	---------------------------------------------------------------------------------------------------------------------------------
	
	ByteBuffer istream;
	
	public NIODeserialize(ByteBuffer is) 
	{
		istream = is;
	}
	protected NIODeserialize() {}
	
	
	public boolean getBoolean() throws IOException {
		return getBoolean(istream);
	}



	public byte getByte() throws IOException {
		return getByte(istream);
	}


	public int getInt() throws IOException {
		return getInt(istream);
	}



	public long getLong() throws IOException {
		return getLong(istream);
	}



	public short getShort() throws IOException {
		return getShort(istream);
	}



	public String getString() throws IOException {
		return getString(istream);
	}

	public String getString(String charset) throws IOException {
		return getString(istream, charset);
	}
	



	
	public short getUByte() throws IOException {
		return getUnsignedByte(istream);
	}



	public long getUInt() throws IOException {
		return getUnsignedInt(istream);
	}



	public int getUShort() throws IOException {
		return getUnsignedShort(istream);
	}
	
	public double getDouble() throws IOException {
		return getDouble(istream);
	}

	public float getFloat() throws IOException {
		return getFloat(istream);
	}
	
	
}