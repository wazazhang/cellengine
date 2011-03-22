package com.net.minaimpl;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;

import com.cell.CUtil;
import com.cell.exception.NotImplementedException;
import com.cell.io.ExternalizableUtil;
import com.net.ExternalizableFactory;
import com.net.ExternalizableMessage;
import com.net.NetDataOutput;

public class NetDataOutputImpl implements NetDataOutput
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

//	-----------------------------------------------------------------------------------------------------------
//	
	synchronized
	public void writeExternal(ExternalizableMessage data) throws IOException {
		if (data != null) {
			buffer.putInt(1);
			data.writeExternal(this);
		} else {
			buffer.putInt(0);
		}
	}
	
	synchronized
	public <T extends ExternalizableMessage> void writeExternalArray(T[] data) throws IOException {
		if (data != null) {
			buffer.putInt(data.length);
			for (T d : data) {
				writeExternal(d);
			}
		} else {
			buffer.putInt(0);
		}
	}
	
	synchronized
	public void writeObject(Object data) throws IOException {
		if (data != null) {
			buffer.putInt(1);
			buffer.putObject(data);
		} else {
			buffer.putInt(0);
		}
	}

	@Override
	public void writeObjectArray(Object[] data) throws IOException {
		if (data != null) {
			buffer.putInt(data.length);
			for (Object d : data) {
				writeObject(d);
			}
		} else {
			buffer.putInt(0);
		}
	}
	
	synchronized
	public void writeUTF(String s) throws IOException {
		if (s != null) {			
			byte[] data = s.getBytes(CUtil.getEncoding());
			buffer.putShort((short)data.length);
			buffer.put(data);
		} else {
			buffer.putInt(0);
		}
	}

	@Override
	public void writeUTFArray(String[] data) throws IOException {
		if (data != null) {
			buffer.putInt(data.length);
			for (String d : data) {
				writeUTF(d);
			}
		} else {
			buffer.putInt(0);
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------
//	
	
	synchronized
	public void write(byte[] b, int off, int len) throws IOException {
		buffer.put(b, off, len);
	}
	synchronized
	public void write(byte[] b) throws IOException {
		buffer.put(b);
	}
	synchronized
	public void write(int b) throws IOException {
		buffer.put((byte)(b));
	}
	synchronized
	public void writeBoolean(boolean v) throws IOException {
		buffer.put(v?(byte)1:0);
	}
	synchronized
	public void writeByte(int v) throws IOException {
		buffer.put((byte)v);
	}
	synchronized
	public void writeBytes(String s) throws IOException {
		throw new NotImplementedException();
	}
	synchronized
	public void writeChar(int v) throws IOException {
		buffer.putChar((char)v);
	}
	synchronized
	public void writeChars(String s) throws IOException {
		throw new NotImplementedException();
	}
	synchronized
	public void writeDouble(double v) throws IOException {
		buffer.putDouble(v);
	}
	synchronized
	public void writeFloat(float v) throws IOException {
		buffer.putFloat(v);
	}
	synchronized
	public void writeInt(int v) throws IOException {
		buffer.putInt(v);
	}
	synchronized
	public void writeLong(long v) throws IOException {
		buffer.putLong(v);
	}
	synchronized
	public void writeShort(int v) throws IOException {
		buffer.putShort((short)v);
	}
	

//	-----------------------------------------------------------------------------------------------------------
//	
	synchronized
	public void writeBooleanArray(boolean[] bools) throws IOException {
		ExternalizableUtil.writeBooleanArray(this, bools);
	}
	synchronized
	public void writeCharArray(char[] chars) throws IOException {
		ExternalizableUtil.writeCharArray(this, chars);
	}
	synchronized
	public void writeByteArray(byte[] bytes) throws IOException {
		ExternalizableUtil.writeByteArray(this, bytes);
	}
	synchronized
	public void writeShortArray(short[] shorts) throws IOException {
		ExternalizableUtil.writeShortArray(this, shorts);
	}
	synchronized
	public void writeIntArray(int[] ints) throws IOException {
		ExternalizableUtil.writeIntArray(this, ints);
	}
	synchronized
	public void writeLongArray(long[] longs) throws IOException {
		ExternalizableUtil.writeLongArray(this, longs);
	}
	synchronized
	public void writeFloatArray(float[] floats) throws IOException {
		ExternalizableUtil.writeFloatArray(this, floats);
	}
	synchronized
	public void writeDoubleArray(double[] doubles) throws IOException {
		ExternalizableUtil.writeDoubleArray(this, doubles);
	}
	
	
	
}
