
package com.cell.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.cell.CObject;




public class NIOSerialize extends IOutput
{
	public static void putString(ByteBuffer buffer, String string, String charset) {
//		synchronized (buffer) {
			try {
				byte[] stringBytes = string.getBytes(charset);
				buffer.putInt(stringBytes.length);
				buffer.put(stringBytes);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
//		}
	}
	public static void putString(ByteBuffer buffer, String string) {
		putString(buffer, string, CObject.getEncoding());
	}
	
	
	public static void putByte(ByteBuffer buffer, byte value) {
//		synchronized (buffer) { 
			buffer.put(value);
//		}
	}
	
	public static void putUnsignedByte(ByteBuffer buffer, short value) {
//		synchronized (buffer) { 
			buffer.putShort(value);
//		}
	}
	
	public static void putBoolean(ByteBuffer buffer, boolean value) {
//		synchronized (buffer) { 
			buffer.put((byte)(value? 1 : 0));
//		}
	}
	
	public static void putShort(ByteBuffer buffer, short value) {
//		synchronized (buffer) { 
			buffer.putShort(value);
//		}
	}
	
	public static void putUnsignedShort(ByteBuffer buffer, int value) {
//		synchronized (buffer) { 
			buffer.putInt(value);
//		}
	}
	
	public static void putInt(ByteBuffer buffer, int value) {
//		synchronized (buffer) { 
			buffer.putInt(value);
//		}
	}
	
	public static void putUnsignedInt(ByteBuffer buffer, long value) {
//		synchronized (buffer) { 
			buffer.putLong(value);
//		}
	}
	
	public static void putLong(ByteBuffer buffer, long value) {
//		synchronized (buffer) { 
			buffer.putLong(value);
//		}
	}
	public static void putFloat(ByteBuffer ostream, float value) throws IOException
	{
		putInt(ostream, Float.floatToIntBits(value));
	}
	
	public static void putDouble(ByteBuffer ostream, double value) throws IOException
	{
		putDouble(ostream, Double.doubleToLongBits(value));
	}
	/////////////////////////////////////////////////////////////////////////////////////////////
	// put array
	
	public static void putStrings(ByteBuffer buffer, String[] value, String charset) {
//		synchronized (buffer) { 
			if (value != null) {
				NIOSerialize.putUnsignedInt(buffer, value.length);
				for (int i = 0; i < value.length; ++i)
					NIOSerialize.putString(buffer, value[i], charset);
			} else {
				NIOSerialize.putUnsignedInt(buffer, 0);
			}
//		}
	}
	
	public static void putStrings(ByteBuffer buffer, String[] value) {
		putStrings(buffer, value, CObject.getEncoding());
	}
	
	public static void putBooleans(ByteBuffer buffer, boolean[] value) 
	{
//		synchronized (buffer) { 
			if (value != null)
			{
				NIOSerialize.putUnsignedInt(buffer, value.length);
				for ( int i=0; i<value.length; ++i )
					NIOSerialize.putBoolean(buffer, value[i]);
			}
			else
			{
				NIOSerialize.putUnsignedInt(buffer, 0);
			}
//		}
	}	
	
	public static void putBytes(ByteBuffer buffer, byte[] value) 
	{
//		synchronized (buffer) { 
		if (value != null)
		{
			NIOSerialize.putUnsignedInt(buffer, value.length);
			for ( int i=0; i<value.length; ++i )
				NIOSerialize.putByte(buffer, value[i]);
		}
		else
		{
			NIOSerialize.putUnsignedInt(buffer, 0);
		}
//		}
	}	
	
	public static void putUnsignedBytes(ByteBuffer buffer, short[] value) 
	{
//		synchronized (buffer) { 
		if (value != null)
		{
			NIOSerialize.putUnsignedInt(buffer, value.length);
			for ( int i=0; i<value.length; ++i )
				NIOSerialize.putUnsignedByte(buffer, value[i]);
		}
		else
		{
			NIOSerialize.putUnsignedInt(buffer, 0);
		}
//		}
	}
	
	public static void putShorts(ByteBuffer buffer, short[] value) 
	{
//		synchronized (buffer) { 
		if (value != null)
		{
			NIOSerialize.putUnsignedInt(buffer, value.length);
			for ( int i=0; i<value.length; ++i )
				NIOSerialize.putShort(buffer, value[i]);
		}
		else
		{
			NIOSerialize.putUnsignedInt(buffer, 0);
		}		
//		}
	}	
	
	public static void putUnsignedShorts(ByteBuffer buffer, int[] value) 
	{
//		synchronized (buffer) { 
		if (value != null)
		{
			NIOSerialize.putUnsignedInt(buffer, value.length);
			for ( int i=0; i<value.length; ++i )
				NIOSerialize.putUnsignedShort(buffer, value[i]);
		}
		else
		{
			NIOSerialize.putUnsignedInt(buffer, 0);
		}		
//		}
	}
	
	public static void putInts(ByteBuffer buffer, int[] value) 
	{
//		synchronized (buffer) { 
		if (value != null)
		{
			NIOSerialize.putUnsignedInt(buffer, value.length);
			for ( int i=0; i<value.length; ++i )
				NIOSerialize.putInt(buffer, value[i]);
		}
		else
		{
			NIOSerialize.putUnsignedInt(buffer, 0);
		}		
//		}
	}	
	
	public static void putUnsignedInts(ByteBuffer buffer, long[] value) 
	{
//		synchronized (buffer) { 
		if (value != null)
		{
			NIOSerialize.putUnsignedInt(buffer, value.length);
			for ( int i=0; i<value.length; ++i )
				NIOSerialize.putUnsignedInt(buffer, value[i]);
		}
		else
		{
			NIOSerialize.putUnsignedInt(buffer, 0);
		}		
//		}
	}
	
	public static void putLongs(ByteBuffer buffer, long[] value) 
	{
//		synchronized (buffer) { 
		if (value != null)
		{
			NIOSerialize.putUnsignedInt(buffer, value.length);
			for ( int i=0; i<value.length; ++i )
				NIOSerialize.putLong(buffer, value[i]);
		}
		else
		{
			NIOSerialize.putUnsignedInt(buffer, 0);
		}	
//		}
	}

	
	
//	-------------------------------------------------------------------------------------------------------------------
	
	

	ByteBuffer ostream;
	
	public NIOSerialize(ByteBuffer os)
	{
		ostream = os;
	}
	protected NIOSerialize() {}

	public void putBoolean(boolean value) throws IOException {
		putBoolean(ostream, value);
	}


	public void putByte(byte value) throws IOException {
		putByte(ostream, value);
	}


	public void putInt(int value) throws IOException {
		putInt(ostream, value);
	}


	public void putLong(long value) throws IOException {
		putLong(ostream, value);
	}

	public void putShort(short value) throws IOException {
		putShort(ostream, value);
	}


	public void putString(String string, String charset) throws IOException {
		putString(ostream, string, charset);
	}

	public void putString(String string) throws IOException {
		putString(ostream, string);
	}



	

	public void putUByte(short value) throws IOException {
		putUnsignedByte(ostream, value);
	}


	public void putUInt(long value) throws IOException {
		putUnsignedInt(ostream, value);
	}

	

	public void putUShort(int value) throws IOException {
		putUnsignedShort(ostream, value);
	}


	
	public void putFloat(float value) throws IOException
	{
		putFloat(ostream, value);
	}
	
	public void putDouble(double value) throws IOException
	{
		putDouble(ostream, value);
	}
	
	
	
	
	
	
	
}
