
package com.cell.io;

import java.io.IOException;
import java.io.InputStream;

import com.cell.CObject;


/**
 * LITTLE_ENDIAN
 * @author WAZA
 *
 */
public class LittleIODeserialize extends IInput
{
//	private static byte[] lock_read = new byte[]{};
	
	public static final int STRING_MAX_LEN = 1024 * 100 + 1;
	
	public static String getString(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
        {
	        int stringLen = getUnsignedShort(istream);
	       
	        if (stringLen>=0 && stringLen<STRING_MAX_LEN) {
		        byte[] stringData = new byte[stringLen];
		        istream.read(stringData);
		        try{
		        	return new String(stringData,CObject.getEncoding());
		        }catch(Exception err){
		        	err.printStackTrace();
		        }
		    }
	        else{
	        	throw new IOException("Deserialize.getString(): negative size or too long : "+stringLen);
	        }
	        return "";
        }
	}
	
	public static String getString(InputStream istream, String charset) throws IOException
	{
//		synchronized (lock_read)
        {
	        int stringLen = getUnsignedShort(istream);
	       
	        if (stringLen>=0 && stringLen<STRING_MAX_LEN) {
		        byte[] stringData = new byte[stringLen];
		        istream.read(stringData);
		        try{
		        	return new String(stringData, charset);
		        }catch(Exception err){
		        	err.printStackTrace();
		        }
		    }
	        else{
	        	throw new IOException("Deserialize.getString(): negative size or too long : "+stringLen);
	        }
	        
	        return "";
        }
	}
	
	
	public static byte getByte(InputStream istream) throws IOException
	{
//		synchronized (lock_read) 
		{
			return (byte)istream.read();
		}
	}
	
	public static short getUnsignedByte(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
		{
			return (short)(istream.read() & 0x00FF);
        }
	}
	
	public static boolean getBoolean(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
		{
			return (istream.read() != 0);
		}
	}
	
	public static short getShort(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
        {
			int b0 = istream.read();
			int b1 = istream.read();
			return (short)(b0 | (b1 << 8));
        }
	}
	
	public static int getUnsignedShort(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
        {
			int b0 = istream.read();
			int b1 = istream.read();
			return (int)(b0 | (b1 << 8));
        }		
	}
	
	public static int getInt(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
        {
			int b0 = istream.read();
			int b1 = istream.read();
			int b2 = istream.read();
			int b3 = istream.read();
	        
			return (int)(b0 | (b1 << 8) | (b2 << 16) | (b3 << 24));
        }
	}
	
	public static long getUnsignedInt(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
        {
			long b0 = istream.read();
			long b1 = istream.read();
			long b2 = istream.read();
			long b3 = istream.read();
	        
			return (long)(b0 | (b1 << 8) | (b2 << 16) | (b3 << 24));
        }		
	}
	
	public static long getLong(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
        {
			long b0 = istream.read();
			long b1 = istream.read();
			long b2 = istream.read();
			long b3 = istream.read();
			long b4 = istream.read();
			long b5 = istream.read();
			long b6 = istream.read();
			long b7 = istream.read();
			
			return (long)(b0 | (b1 << 8) | (b2 << 16) | (b3 << 24) | (b4 << 32) | (b5 << 40) | (b6 << 48) | (b7 << 56));
        }
	}
	public static double getDouble(InputStream istream) throws IOException {
		return Double.longBitsToDouble(getLong(istream));
	}

	public static float getFloat(InputStream istream) throws IOException {
		return Float.intBitsToFloat(getInt(istream));
	}
	//////////////////////////////////////////////////////////////////////////////////////
	// get array
	
	public static String[] getStrings(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
		{
			int count = (int)LittleIODeserialize.getUnsignedInt(istream);
			String[] values = new String[count];
			for ( int i=0; i<count; ++i ) 
				values[i] = LittleIODeserialize.getString(istream);
			return values;
		}
	}
	
	public static String[] getStrings(InputStream istream, String charset) throws IOException
	{
//		synchronized (lock_read)
		{
			int count = (int)LittleIODeserialize.getUnsignedInt(istream);
			String[] values = new String[count];
			for ( int i=0; i<count; ++i )
				values[i] = LittleIODeserialize.getString(istream, charset);
			return values;
		}
	}
	
	public static boolean[] getBooleans(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
		{
			int count = (int)LittleIODeserialize.getUnsignedInt(istream);
			boolean[] values = new boolean[count];
			for ( int i=0; i<count; ++i )
				values[i] = LittleIODeserialize.getBoolean(istream);
			return values;
		}
	}	
	
	public static byte[] getBytes(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
		{
			int count = (int)LittleIODeserialize.getUnsignedInt(istream);
			byte[] values = new byte[count];
			for ( int i=0; i<count; ++i )
				values[i] = LittleIODeserialize.getByte(istream);
			return values;
		}
	}
	
	public static short[] getUnsignedBytes(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
		{
			int count = (int)LittleIODeserialize.getUnsignedInt(istream);
			short[] values = new short[count];
			for ( int i=0; i<count; ++i )
				values[i] = LittleIODeserialize.getUnsignedByte(istream);
			return values;
		}
	}
	
	public static short[] getShorts(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
		{
			int count = (int)LittleIODeserialize.getUnsignedInt(istream);
			short[] values = new short[count];
			for ( int i=0; i<count; ++i )
				values[i] = LittleIODeserialize.getShort(istream);
			return values;
		}
	}
	
	public static int[] getUnsignedShorts(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
		{
			int count = (int)LittleIODeserialize.getUnsignedInt(istream);
			int[] values = new int[count];
			for ( int i=0; i<count; ++i )
				values[i] = LittleIODeserialize.getUnsignedShort(istream);
			return values;
		}
	}	
	
	public static int[] getInts(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
		{
			int count = (int)LittleIODeserialize.getUnsignedInt(istream);
			int[] values = new int[count];
			for ( int i=0; i<count; ++i )
				values[i] = LittleIODeserialize.getInt(istream);
			return values;
		}
	}
	
	public static long[] getUnsignedInts(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
		{
			int count = (int)LittleIODeserialize.getUnsignedInt(istream);
			long[] values = new long[count];
			for ( int i=0; i<count; ++i )
				values[i] = LittleIODeserialize.getUnsignedInt(istream);
			return values;
		}
	}
	
	public static long[] getLongs(InputStream istream) throws IOException
	{
//		synchronized (lock_read)
		{
			int count = (int)LittleIODeserialize.getUnsignedInt(istream);
			long[] values = new long[count];
			for ( int i=0; i<count; ++i )
				values[i] = LittleIODeserialize.getLong(istream);
			return values;
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------
	
	InputStream istream;
	
	public LittleIODeserialize(InputStream is) 
	{
		istream = is;
	}
	protected LittleIODeserialize() {}
	
	
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
