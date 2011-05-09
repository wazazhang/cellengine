package com.net.sfsimpl.server;

import java.io.DataInput;
import java.io.Externalizable;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import com.cell.CUtil;
import com.cell.exception.NotImplementedException;
import com.cell.io.ExternalizableUtil;
import com.net.ExternalizableFactory;
import com.net.ExternalizableMessage;
import com.net.NetDataInput;
import com.net.NetDataTypes;

public class NetDataInputImpl implements NetDataInput
{	
	final ByteBuffer buffer ;
	final ExternalizableFactory factory;

	public NetDataInputImpl(ByteBuffer buffer, ExternalizableFactory factory) {
		this.buffer = buffer;
		this.factory = factory;
	}
	
	@Override
	public ExternalizableFactory getFactory() {
		return factory;
	}
	
	@Override
	public int skipBytes(int n) throws IOException {
		buffer.position(buffer.position() + n);
		return n;
	}
	
	@Deprecated
	public <T> T readObject(Class<T> type) throws IOException {
		throw new NotImplementedException("not support on sfs system !");
	}
	
	@Override
	public String readUTF() throws IOException {
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
		byte[] ub = new byte[1];
		buffer.get(ub);
		int ret = 0x00ff & ub[0];
		return ret;
	}
	
	/**
	 * big edian
	 */
	public int readUnsignedShort() throws IOException {
		byte[] ub = new byte[2];
		buffer.get(ub);
		int ret =
			((0x00ff & ub[0])<<8) | 
			((0x00ff & ub[1]));
		return ret;
	}

//	public static void main(String[] args)
//	{
//		byte[] ub = new byte[]{(byte)0xaf, (byte)0xff};
//		int ret =
//			((0x00ff & ub[0])<<8) | 
//			((0x00ff & ub[1]));
//		System.out.println(Integer.toString(ret, 16));
//	}
	
//	--------------------------------------------------------------------------------------------------------------
	
//	--------------------------------------------------------------------------------------------------------------

	

	
	public boolean[] readBooleanArray() throws IOException {
		return ExternalizableUtil.readBooleanArray(this);
	}
	
	public char[] readCharArray() throws IOException {
		return ExternalizableUtil.readCharArray(this);
	}
	
	public byte[] readByteArray() throws IOException {
		return ExternalizableUtil.readByteArray(this);
	}
	
	public short[] readShortArray() throws IOException {
		return ExternalizableUtil.readShortArray(this);
	}
	
	public int[] readIntArray() throws IOException {
		return ExternalizableUtil.readIntArray(this);
	}
	
	public long[] readLongArray() throws IOException {
		return ExternalizableUtil.readLongArray(this);
	}
	
	public float[] readFloatArray() throws IOException {
		return ExternalizableUtil.readFloatArray(this);
	}
	
	public double[] readDoubleArray() throws IOException {
		return ExternalizableUtil.readDoubleArray(this);
	}

	public String[] readUTFArray() throws IOException {
		String[] ret = new String[readInt()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = readUTF();
		}
		return ret;
	}
	

	public <T extends com.net.ExternalizableMessage> T readExternal(Class<T> cls) throws IOException {
		int type = readInt();
		if (type != 0) {
			try {
				T data = cls.newInstance();
				data.readExternal(this);
				return data;
			} catch (Exception e) {
				throw new IOException(e);
			}
		}
		return null;
	}
	

	public <T extends ExternalizableMessage> T[] readExternalArray(Class<T> type) throws IOException {
		T[] ret = CUtil.newArray(type, readInt());
		for (int i = 0; i < ret.length; i++) {
			ret[i] = readExternal(type);
		}
		return ret;
	}

	public <T> T[] readObjectArray(Class<T> type) throws IOException {
		T[] ret = CUtil.newArray(type, readInt());
		for (int i = 0; i < ret.length; i++) {
			ret[i] = readObject(type);
		}
		return ret;
	}


//	-----------------------------------------------------------------------------------------------


	public Object readAnyArray(Class<?> type, byte component_data_type) throws IOException {
		int count = readInt();
		if (count == 0) {
			return null;
		}
		Class<?> component_type = type.getComponentType();
		if (count < 0) { // 表示成员还是个数组
			count = -count;
			Object array = Array.newInstance(component_type, count);
			for (int i = 0; i < count; i++) {
				Array.set(array, i, readAnyArray(component_type, component_data_type));
			}
			return array;
		} else if (count > 0) { // 表示成员是个通常对象
			Object array = Array.newInstance(component_type, count);
			for (int i = 0; i < count; i++) {
				Array.set(array, i, readAny(component_data_type, component_type));
			}
			return array;
		}
		return null;
	}
	
	private Object readAny(byte component_data_type, Class<?> component_type) throws IOException {
		switch (component_data_type) {
		case NetDataTypes.TYPE_EXTERNALIZABLE:
			return readAnyExternal(component_type);
		case NetDataTypes.TYPE_BOOLEAN:
			return readBoolean();
		case NetDataTypes.TYPE_BYTE:
			return readByte();
		case NetDataTypes.TYPE_CHAR:
			return readChar();
		case NetDataTypes.TYPE_SHORT:
			return readShort();
		case NetDataTypes.TYPE_INT:
			return readInt();
		case NetDataTypes.TYPE_LONG:
			return readLong();
		case NetDataTypes.TYPE_FLOAT:
			return readFloat();
		case NetDataTypes.TYPE_DOUBLE:
			return readDouble();
		case NetDataTypes.TYPE_STRING: 
			return readUTF();
		case NetDataTypes.TYPE_OBJECT:
			return readObject(component_type);
		default:
			return null;
		}
	}
	
	private ExternalizableMessage readAnyExternal(Class<?> cls) throws IOException {
		int type = readInt();
		if (type != 0) {
			try {
				ExternalizableMessage data = (ExternalizableMessage)cls.newInstance();
				data.readExternal(this);
				return data;
			} catch (Exception e) {
				throw new IOException(e);
			}
		}
		return null;
	}

	

}
