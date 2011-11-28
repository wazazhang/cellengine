package com.cell.net.io.text;

import java.io.DataInput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import com.cell.CUtil;
import com.cell.exception.NotImplementedException;
import com.cell.io.ExternalizableUtil;
import com.cell.io.TextDeserialize;
import com.cell.net.io.ExternalizableFactory;
import com.cell.net.io.ExternalizableMessage;
import com.cell.net.io.MutualMessage;
import com.cell.net.io.NetDataInput;
import com.cell.net.io.NetDataTypes;

public class NetDataInputText implements NetDataInput
{	
	final TextDeserialize buffer ;
	final ExternalizableFactory factory;

	public NetDataInputText(String text, ExternalizableFactory factory) {
		this.buffer = new TextDeserialize(new StringReader(text));
		this.factory = factory;
	}
	
	@Override
	public ExternalizableFactory getFactory() {
		return factory;
	}
	
	public int skipBytes(int n) throws IOException {
		throw new NotImplementedException("Not Support Method");
	}
	
	
	public <T> T readObject(Class<T> type) throws IOException {
		throw new NotImplementedException("Not Support Method");
	}
	
	public String readUTF() throws IOException {
		return buffer.getString();
	}
	
	
//	-----------------------------------------------------------------------------------------------
	
	
	public boolean readBoolean() throws IOException {
		return buffer.getBoolean();
	}
	
	public byte readByte() throws IOException {
		return buffer.getByte();
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
		throw new NotImplementedException("Not Support Method");
	}
	
	public void readFully(byte[] b) throws IOException {
		throw new NotImplementedException("Not Support Method");
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
		return buffer.getUnsignedByte();
	}
	
	public int readUnsignedShort() throws IOException {
		return buffer.getUnsignedShort();
	}

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
	

	public <T extends com.cell.net.io.ExternalizableMessage> T readExternal(Class<T> cls) throws IOException {
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

	
	public <T extends MutualMessage> T readMutual(Class<T> cls) throws IOException {
		int type = readInt();
		if (type != 0) {
			try {
				T data = cls.newInstance();
				this.factory.getMutualCodec().readMutual(data, this);
				return data;
			} catch (Exception e) {
				throw new IOException(e);
			}
		}
		return null;
	}
	

	public <T extends MutualMessage> T[] readMutualArray(Class<T> type) throws IOException {
		T[] ret = CUtil.newArray(type, readInt());
		for (int i = 0; i < ret.length; i++) {
			ret[i] = readMutual(type);
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
		case NetDataTypes.TYPE_MUTUAL:
			return readAnyMutual(component_type);
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
	

	private MutualMessage readAnyMutual(Class<?> cls) throws IOException {
		int type = readInt();
		if (type != 0) {
			try {
				MutualMessage data = (MutualMessage)cls.newInstance();
				this.factory.getMutualCodec().readMutual(data, this);
				return data;
			} catch (Exception e) {
				throw new IOException(e);
			}
		}
		return null;
	}

//	-----------------------------------------------------------------------------------------------
//	
//	-----------------------------------------------------------------------------------------------
//	
//
//	@Override
//	public Object readAnyArray(String key, Class<?> type, byte componentDataType) throws IOException {
//		return readAnyArray(type, componentDataType);
//	}
//
//	@Override
//	public boolean readBoolean(String key) throws IOException {
//		return readBoolean();
//	}
//
//	@Override
//	public boolean[] readBooleanArray(String key) throws IOException {
//		return readBooleanArray();
//	}
//
//	@Override
//	public byte readByte(String key) throws IOException {
//		return readByte();
//	}
//
//	@Override
//	public byte[] readByteArray(String key) throws IOException {
//		return readByteArray();
//	}
//
//	@Override
//	public char readChar(String key) throws IOException {
//		return readChar();
//	}
//
//	@Override
//	public char[] readCharArray(String key) throws IOException {
//		return readCharArray();
//	}
//
//	@Override
//	public double readDouble(String key) throws IOException {
//		return readDouble();
//	}
//
//	@Override
//	public double[] readDoubleArray(String key) throws IOException {
//		return readDoubleArray();
//	}
//
//	@Override
//	public <T extends ExternalizableMessage> T readExternal(String key,
//			Class<T> cls) throws IOException {
//		return readExternal(cls);
//	}
//
//	@Override
//	public <T extends ExternalizableMessage> T[] readExternalArray(String key,
//			Class<T> type) throws IOException {
//		return readExternalArray(type);
//	}
//
//	@Override
//	public float readFloat(String key) throws IOException {
//		return readFloat();
//	}
//
//	@Override
//	public float[] readFloatArray(String key) throws IOException {
//		return readFloatArray();
//	}
//
//	@Override
//	public void readFully(String key, byte[] b, int off, int len)
//			throws IOException {
//		readFully(b, off, len);
//	}
//
//	@Override
//	public void readFully(String key, byte[] b) throws IOException {
//		readFully(b);
//	}
//
//	@Override
//	public int readInt(String key) throws IOException {
//		return readInt();
//	}
//
//	@Override
//	public int[] readIntArray(String key) throws IOException {
//		return readIntArray();
//	}
//
//	@Override
//	public String readLine(String key) throws IOException {
//		return readLine();
//	}
//
//	@Override
//	public long readLong(String key) throws IOException {
//		return readLong();
//	}
//
//	@Override
//	public long[] readLongArray(String key) throws IOException {
//		return readLongArray();
//	}
//
//	@Override
//	public <T> T readObject(String key, Class<T> type) throws IOException {
//		return readObject(type);
//	}
//
//	@Override
//	public <T> T[] readObjectArray(String key, Class<T> type)
//			throws IOException {
//		return readObjectArray(type);
//	}
//
//	@Override
//	public short readShort(String key) throws IOException {
//		return readShort();
//	}
//
//	@Override
//	public short[] readShortArray(String key) throws IOException {
//		return readShortArray();
//	}
//
//	@Override
//	public int readUnsignedByte(String key) throws IOException {
//		return readUnsignedByte();
//	}
//
//	@Override
//	public int readUnsignedShort(String key) throws IOException {
//		return readUnsignedShort();
//	}
//
//	@Override
//	public String readUTF(String key) throws IOException {
//		return readUTF();
//	}
//
//	@Override
//	public String[] readUTFArray(String key) throws IOException {
//		return readUTFArray();
//	}
//
////	-----------------------------------------------------------------------------------------------
////	
////	-----------------------------------------------------------------------------------------------
	
	

}
