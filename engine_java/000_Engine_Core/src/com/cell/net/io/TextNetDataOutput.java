package com.cell.net.io;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Calendar;
import java.util.Date;

import com.cell.CUtil;
import com.cell.exception.NotImplementedException;
import com.cell.io.ExternalizableUtil;
import com.cell.io.TextSerialize;
import com.cell.reflect.Parser;

public class TextNetDataOutput extends BaseNetDataOutput
{	
	final TextSerialize buffer ;
	
	public TextNetDataOutput(TextSerialize buffer, ExternalizableFactory factory) {
		super(factory);
		this.buffer = buffer;
	}
	
	
	public void writeObject(Object data) throws IOException {
		throw new NotImplementedException("Not Support Method");
	}

	
	public void writeUTF(String s) throws IOException {
		buffer.putString(s);
	}

	public void write(byte[] b, int off, int len) throws IOException {
		throw new NotImplementedException("Not Support Method");
	}
	
	public void write(byte[] b) throws IOException {
		throw new NotImplementedException("Not Support Method");
	}
	
	public void write(int b) throws IOException {
		buffer.putByte((byte)b);
	}
	
	public void writeBoolean(boolean v) throws IOException {
		buffer.putBoolean(v);
	}
	
	public void writeByte(int v) throws IOException {
		buffer.putByte((byte)v);
	}
	
	public void writeBytes(String s) throws IOException {
		throw new NotImplementedException();
	}
	
	public void writeChar(int v) throws IOException {
		buffer.putChar((char)v);
	}
	
	public void writeChars(String s) throws IOException {
		throw new NotImplementedException();
	}
	
	public void writeDouble(double v) throws IOException {
		buffer.putDouble(v);
	}
	
	public void writeFloat(float v) throws IOException {
		buffer.putFloat(v);
	}
	
	public void writeInt(int v) throws IOException {
		buffer.putInt(v);
	}
	
	public void writeLong(long v) throws IOException {
		buffer.putLong(v);
	}
	
	public void writeShort(int v) throws IOException {
		buffer.putShort((short)v);
	}
	
	@Override
	public void writeDate(Date date) throws IOException 
	{
		if (date == null)
		{
			writeDouble(0);
		}
		else 
		{
			writeDouble(date.getTime());
		}
	}
	
	@Override
	public void writeEnum(Object eo) throws IOException
	{
		throw new NotImplementedException("Not Support Method");
	}
}
