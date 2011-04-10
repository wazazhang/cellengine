package com.net.minaimpl;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;

import com.cell.CUtil;
import com.cell.exception.NotImplementedException;
import com.cell.io.ExternalizableUtil;
import com.cell.reflect.Parser;
import com.net.ExternalizableFactory;
import com.net.ExternalizableMessage;
import com.net.NetDataOutput;
import com.net.NetDataTypes;

public class NetDataOutputImpl extends NetDataOutput
{	
	final IoBuffer buffer ;
	final ExternalizableFactory factory;
	
	public NetDataOutputImpl(IoBuffer buffer, ExternalizableFactory factory) {
		this.buffer = buffer;
		this.factory = factory;
	}
	@Override
	public ExternalizableFactory getFactory() {
		return factory;
	}

	
	
	
	public void writeObject(Object data) throws IOException {
		if (data != null) {
			buffer.putInt(1);
			buffer.putObject(data);
		} else {
			buffer.putInt(0);
		}
	}

	
	public void writeUTF(String s) throws IOException {
		if (s != null) {			
			byte[] data = s.getBytes(CUtil.getEncoding());
			buffer.putShort((short) data.length);
			if (data.length > 0) {
				buffer.put(data);
			}
		} else {
			buffer.putShort((short)0);
		}
	}

	static public void writeUTF(String s, IoBuffer buffer) throws IOException {
		if (s != null) {			
			byte[] data = s.getBytes(CUtil.getEncoding());
			buffer.putShort((short)data.length);
			if (data.length > 0) {
				buffer.put(data);
			}
		} else {
			buffer.putShort((short)0);
		}
	}

	
//	-----------------------------------------------------------------------------------------------------------
//	
	
	
	public void write(byte[] b, int off, int len) throws IOException {
		buffer.put(b, off, len);
	}
	
	public void write(byte[] b) throws IOException {
		buffer.put(b);
	}
	
	public void write(int b) throws IOException {
		buffer.put((byte)(b));
	}
	
	public void writeBoolean(boolean v) throws IOException {
		buffer.put(v?(byte)1:0);
	}
	
	public void writeByte(int v) throws IOException {
		buffer.put((byte)v);
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
	

//	-----------------------------------------------------------------------------------------------------------
//	

	public static void main(String[] args) throws Exception {
		Boolean[][][] tt = new Boolean[][][]{
				{new Boolean[2],},
				{new Boolean[3],},
		};
		System.out.println(tt.getClass().getCanonicalName());
//		boolean b = (Boolean)true;
//		Array.newInstance(componentType, dimensions);
		System.out.println(tt.getClass().newInstance());
		
	}

	
}
