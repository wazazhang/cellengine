package com.net.minaimpl;

import java.io.DataInput;
import java.io.Externalizable;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;

import com.cell.CUtil;
import com.cell.exception.NotImplementedException;
import com.cell.io.ExternalizableUtil;
import com.net.ExternalizableFactory;
import com.net.ExternalizableMessage;
import com.net.NetDataInput;
import com.net.NetDataTypes;

public class NetDataInputImpl extends NetDataInput
{	
	final IoBuffer buffer ;
	final ExternalizableFactory factory;

	public NetDataInputImpl(IoBuffer buffer, ExternalizableFactory factory) {
		this.buffer = buffer;
		this.factory = factory;
	}
	
	@Override
	public ExternalizableFactory getFactory() {
		return factory;
	}
	
	public int skipBytes(int n) throws IOException {
		buffer.skip(n);
		return n;
	}
	
	
	public <T> T readObject(Class<T> type) throws IOException {
		int size = buffer.getInt();
		if (size > 0) {
			try {
				Object obj = buffer.getObject();
				if (obj != null) {
					return type.cast(obj);
				}
			} catch (Exception e) {
				throw new IOException(e);
			}
		}
		return null;
	}
	

	
	public String readUTF() throws IOException {
		int size = buffer.getShort();
		if (size > 0) {		
			byte[] data = new byte[size];
			buffer.get(data, 0, size);
			return new String(data, CUtil.getEncoding());
		}
		return null;
	}
	
	static public String readUTF(IoBuffer buffer) throws IOException {
		int size = buffer.getShort();
		if (size > 0) {		
			byte[] data = new byte[size];
			buffer.get(data, 0, size);
			return new String(data, CUtil.getEncoding());
		}
		return null;
	}
	
//	-----------------------------------------------------------------------------------------------
	
	
	public boolean readBoolean() throws IOException {
		return buffer.get()!=0;
	}
	
	public byte readByte() throws IOException {
		return buffer.get();
	}
	
	public char readChar() throws IOException {
		return buffer.getChar();
	}
	
	public double readDouble() throws IOException {
		return buffer.getDouble();
	}
	
	public float readFloat() throws IOException {
		return buffer.getFloat();
	}
	
	public void readFully(byte[] b, int off, int len) throws IOException {
		buffer.get(b, off, len);
	}
	
	public void readFully(byte[] b) throws IOException {
		buffer.get(b);
	}
	
	public int readInt() throws IOException {
		return buffer.getInt();
	}
	
	public String readLine() throws IOException {
		throw new NotImplementedException();
	}
	
	public long readLong() throws IOException {
		return buffer.getLong();
	}
	
	public short readShort() throws IOException {
		return buffer.getShort();
	}
	
	public int readUnsignedByte() throws IOException {
		return buffer.getUnsigned();
	}
	
	public int readUnsignedShort() throws IOException {
		return buffer.getUnsignedShort();
	}

//	--------------------------------------------------------------------------------------------------------------
	
//	--------------------------------------------------------------------------------------------------------------

	
	
}
