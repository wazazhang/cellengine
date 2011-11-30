
package com.cell.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import com.cell.CObject;



public class TextDeserialize extends IInput
{

	private static String getNext(Reader in) throws IOException {
		 {
			StringBuilder sb = new StringBuilder();
			while (true) {
				int r = in.read();
				if (r == -1)
					break;
				if (r == ',')
					break;
				sb.append((char) r);
			}
			return sb.toString();
		}
	}
	
	public static String getString(Reader in) throws IOException {
		int stringLen = (int)getUnsignedInt(in);
		char[] chars = new char[stringLen];
		in.read(chars);
		if (in.read() != ','){
			//System.err.println("bat ending for : " + in);
		}
		return new String(chars);
	}
	
	public static String getBytesString(Reader in) throws IOException {
		String next = getNext(in);
		if (next.endsWith("byte")) {
			next = next.replace("byte", "");
		}
		int stringLen = Integer.parseInt(next);
		String ret = "";
		if (stringLen>0){
			while (true) {
				int ch = in.read();
				if (ch!=-1) {
					ret += (char)ch;
					byte[] data = ret.getBytes(CObject.ENCODING);
					if (stringLen <= data.length) {
						break;
					}
				}else{
					throw new IOException("out of index !");
				}
			}
		}
		if (in.read() != ',') {
			//System.err.println("bat ending for : " + in);
		}
		return ret;
	}
	
	public static byte getByte(Reader in) throws IOException
	{
		return Byte.parseByte(getNext(in));
	}
	
	public static short getUnsignedByte(Reader in) throws IOException
	{
		return Short.parseShort(getNext(in));
	}
	
	public static boolean getBoolean(Reader in) throws IOException
	{
		String read = getNext(in);
		try{
			return Boolean.parseBoolean(read);
		}catch(NumberFormatException err1){
			return Byte.parseByte(read)!=0;
		}
	}
	
	public static short getShort(Reader in) throws IOException
	{
		return Short.parseShort(getNext(in));
	}
	
	public static int getUnsignedShort(Reader in) throws IOException
	{
		String read = getNext(in);
		try{
			return Integer.parseInt(read);
		}catch(NumberFormatException err1){
			return Integer.parseInt(read, 16);
		}
	}
	
	public static int getInt(Reader in) throws IOException
	{
		return Integer.parseInt(getNext(in));
	}
	
	public static long getUnsignedInt(Reader in) throws IOException
	{
		return Long.parseLong(getNext(in));
	}
	
	public static long getLong(Reader in) throws IOException
	{
		return Long.parseLong(getNext(in));
	}
	public static double getDouble(Reader istream) throws IOException {
		return Double.parseDouble(getNext(istream));
	}

	public static float getFloat(Reader istream) throws IOException {
		return Float.parseFloat(getNext(istream));
	}
	//////////////////////////////////////////////////////////////////////////////////////
	// get array
	
	public static String[] getStrings(Reader in) throws IOException
	{
		int count = (int)getUnsignedInt(in);
		if (count > 0)
		{
			String[] values = new String[count];
			for ( int i=0; i<count; ++i )
				values[i] = getString(in);
			
			return values;
		}
		
		return null;
	}
	
	public static String[] getBytesStrings(Reader in) throws IOException
	{
		int count = (int)getUnsignedInt(in);
		if (count > 0)
		{
			String[] values = new String[count];
			for ( int i=0; i<count; ++i )
				values[i] = getBytesString(in);
			
			return values;
		}
		
		return null;
	}
	
	public static boolean[] getBooleans(Reader in) throws IOException
	{
		int count = (int)getUnsignedInt(in);
		if (count > 0)
		{
			boolean[] values = new boolean[count];
			for ( int i=0; i<count; ++i )
				values[i] = getBoolean(in);
			
			return values;
		}
		
		return null;
	}	
	
	public static byte[] getBytes(Reader in) throws IOException
	{
		int count = (int)getUnsignedInt(in);
		if (count > 0)
		{
			byte[] values = new byte[count];
			for ( int i=0; i<count; ++i )
				values[i] = getByte(in);
			
			return values;
		}
		
		return null;
	}
	
	public static short[] getUnsignedBytes(Reader in) throws IOException
	{
		int count = (int)getUnsignedInt(in);
		if (count > 0)
		{
			short[] values = new short[count];
			for ( int i=0; i<count; ++i )
				values[i] = getUnsignedByte(in);
			
			return values;
		}
		
		return null;
	}
	
	public static short[] getShorts(Reader in) throws IOException
	{
		int count = (int)getUnsignedInt(in);
		if (count > 0)
		{
			short[] values = new short[count];
			for ( int i=0; i<count; ++i )
				values[i] = getShort(in);
			
			return values;
		}
		
		return null;
	}
	
	public static int[] getUnsignedShorts(Reader in) throws IOException
	{
		int count = (int)getUnsignedInt(in);
		if (count > 0)
		{
			int[] values = new int[count];
			for ( int i=0; i<count; ++i )
				values[i] = getUnsignedShort(in);
			
			return values;
		}
		
		return null;
	}	
	
	public static int[] getInts(Reader in) throws IOException
	{
		int count = (int)getUnsignedInt(in);
		if (count > 0)
		{
			int[] values = new int[count];
			for ( int i=0; i<count; ++i )
				values[i] = getInt(in);
			
			return values;
		}
		
		return null;
	}
	
	public static long[] getUnsignedInts(Reader in) throws IOException
	{
		int count = (int)getUnsignedInt(in);
		if (count > 0)
		{
			long[] values = new long[count];
			for ( int i=0; i<count; ++i )
				values[i] = getUnsignedInt(in);
			
			return values;
		}
		
		return null;
	}
	
	public static long[] getLongs(Reader in) throws IOException
	{
		int count = (int)getUnsignedInt(in);
		if (count > 0)
		{
			long[] values = new long[count];
			for ( int i=0; i<count; ++i )
				values[i] = getLong(in);
			
			return values;
		}
		
		return null;
	}
	
	public static float[] getFloats(Reader in) throws IOException
	{
		int count = (int)getUnsignedInt(in);
		if (count > 0)
		{
			float[] values = new float[count];
			for ( int i=0; i<count; ++i )
				values[i] = getFloat(in);
			
			return values;
		}
		
		return null;
	}
//	---------------------------------------------------------------------------------------------------------------------------	
	

	
//	---------------------------------------------------------------------------------------------------------------------------------
	
	Reader istream;
	
	public TextDeserialize(Reader is) 
	{
		istream = is;
	}
	protected TextDeserialize() {}
	
	
	public boolean getBoolean() throws IOException {
		return getBoolean(istream);
	}



	public byte getByte() throws IOException {
		return getByte(istream);
	}

	public char getChar() throws IOException {
		return getString().charAt(0);
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
		return getString(istream);
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
	
	public short getUnsignedByte() throws IOException
	{
		return getUnsignedByte(istream);
	}
	
	public int getUnsignedShort() throws IOException
	{
		return getUnsignedShort(istream);
	}
}
