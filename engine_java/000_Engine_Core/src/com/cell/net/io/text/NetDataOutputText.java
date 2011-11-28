package com.cell.net.io.text;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import com.cell.CUtil;
import com.cell.exception.NotImplementedException;
import com.cell.io.ExternalizableUtil;
import com.cell.io.TextSerialize;
import com.cell.net.io.ExternalizableFactory;
import com.cell.net.io.ExternalizableMessage;
import com.cell.net.io.MutualMessage;
import com.cell.net.io.NetDataOutput;
import com.cell.net.io.NetDataTypes;
import com.cell.reflect.Parser;

public class NetDataOutputText implements NetDataOutput
{	
	final TextSerialize buffer ;
	final ExternalizableFactory factory;
	
	public NetDataOutputText(TextSerialize buffer, ExternalizableFactory factory) {
		this.buffer = buffer;
		this.factory = factory;
	}
	
	@Override
	public ExternalizableFactory getFactory() {
		return factory;
	}

	
	public void writeObject(Object data) throws IOException {
		throw new NotImplementedException("Not Support Method");
	}

	
	public void writeUTF(String s) throws IOException {
		
	}

	
//	-----------------------------------------------------------------------------------------------------------
//	
	
	
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
	


	public void writeBooleanArray(boolean[] bools) throws IOException {
		ExternalizableUtil.writeBooleanArray(this, bools);
	}
	
	public void writeCharArray(char[] chars) throws IOException {
		ExternalizableUtil.writeCharArray(this, chars);
	}
	
	public void writeByteArray(byte[] bytes) throws IOException {
		ExternalizableUtil.writeByteArray(this, bytes);
	}
	
	public void writeShortArray(short[] shorts) throws IOException {
		ExternalizableUtil.writeShortArray(this, shorts);
	}
	
	public void writeIntArray(int[] ints) throws IOException {
		ExternalizableUtil.writeIntArray(this, ints);
	}
	
	public void writeLongArray(long[] longs) throws IOException {
		ExternalizableUtil.writeLongArray(this, longs);
	}
	
	public void writeFloatArray(float[] floats) throws IOException {
		ExternalizableUtil.writeFloatArray(this, floats);
	}
	
	public void writeDoubleArray(double[] doubles) throws IOException {
		ExternalizableUtil.writeDoubleArray(this, doubles);
	}
	
	public void writeUTFArray(String[] data) throws IOException {
		if (data != null) {
			writeInt(data.length);
			for (String d : data) {
				writeUTF(d);
			}
		} else {
			writeInt(0);
		}
	}

	public <T extends ExternalizableMessage> void writeExternalArray(T[] data) throws IOException {
		if (data != null) {
			writeInt(data.length);
			for (T d : data) {
				writeExternal(d);
			}
		} else {
			writeInt(0);
		}
	}
	
	public <T extends MutualMessage> void writeMutualArray(T[] data) throws IOException {
		if (data != null) {
			writeInt(data.length);
			for (T d : data) {
				writeMutual(d);
			}
		} else {
			writeInt(0);
		}
	}
	
	public void writeObjectArray(Object[] data) throws IOException {
		if (data != null) {
			writeInt(data.length);
			for (Object d : data) {
				writeObject(d);
			}
		} else {
			writeInt(0);
		}
	}
	

	
//	-----------------------------------------------------------------------------------------------------------
//	
	
	
	public void writeExternal(ExternalizableMessage data) throws IOException {
		if (data != null) {
			int msg_type = getFactory().getType(data.getClass());
			writeInt(msg_type);
			if (msg_type != 0) {
				data.writeExternal(this);
			}
		} else {
			writeInt(0);
		}
	}
	
	public void writeMutual(MutualMessage data) throws IOException {
		if (data != null) {
			int msg_type = getFactory().getType(data.getClass());
			writeInt(msg_type);
			if (msg_type != 0) {
				this.factory.getMutualCodec().writeMutual(data, this);
			}
		} else {
			writeInt(0);
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------
//	

	public void writeAnyArray(Object array, byte component_data_type) throws IOException {
		if (array != null) {
			int count = Array.getLength(array);
			if (count > 0) {
				Class<?> component_type = array.getClass().getComponentType();
				if (component_type.isArray()) {
					writeInt(-count); 	// 表示成员还是个数组
					for (int i = 0; i < count; i++) {
						writeAnyArray(Array.get(array, i), component_data_type);
					}
				} else {
					writeInt(count);	// 表示成员是个通常对象
					for (int i = 0; i < count; i++) {
						writeAny(component_data_type, Array.get(array, i));
					}
				}
			} else {
				writeInt(0);
			}
		} else {
			writeInt(0);
		}
	}
	
	private void writeAny(byte component_data_type, Object obj) throws IOException 
	{
		switch (component_data_type) {
		case NetDataTypes.TYPE_EXTERNALIZABLE:
			writeExternal((ExternalizableMessage)obj);
			break;
		case NetDataTypes.TYPE_MUTUAL:
			writeMutual((MutualMessage)obj);
			break;
		case NetDataTypes.TYPE_BOOLEAN:
			writeBoolean((Boolean)obj);
			break;
		case NetDataTypes.TYPE_BYTE:
			writeByte((Byte)obj);
			break;
		case NetDataTypes.TYPE_CHAR: 
			writeChar((Character)obj);
			break;
		case NetDataTypes.TYPE_SHORT: 
			writeShort((Short)obj);
			break;
		case NetDataTypes.TYPE_INT: 
			writeInt((Integer)obj);
			break;
		case NetDataTypes.TYPE_LONG: 
			writeLong((Long)obj);
			break;
		case NetDataTypes.TYPE_FLOAT: 
			writeFloat((Float)obj);
			break;
		case NetDataTypes.TYPE_DOUBLE: 
			writeDouble((Double)obj);
			break;
		case NetDataTypes.TYPE_STRING: 
			writeUTF((String)obj);
			break;
		case NetDataTypes.TYPE_OBJECT: 
			writeObject(obj);
			break;
		default:
		}
	}

	
	
////	-----------------------------------------------------------------------------------------------------------
////	
////	-----------------------------------------------------------------------------------------------------------
//
//	
//	@Override
//	public void write(String key, byte[] b, int off, int len)
//			throws IOException {
//		write(b, off, len);
//	}
//
//	@Override
//	public void write(String key, byte[] b) throws IOException {
//		write(b);
//	}
//
//	@Override
//	public void writeAnyArray(String key, Object array, byte componentDataType)
//			throws IOException {
//		writeAnyArray(array, componentDataType);
//	}
//
//	@Override
//	public void writeBoolean(String key, boolean v) throws IOException {
//		writeBoolean(v);
//	}
//
//	@Override
//	public void writeBooleanArray(String key, boolean[] bools)
//			throws IOException {
//		writeBooleanArray(bools);
//	}
//
//	@Override
//	public void writeByte(String key, int v) throws IOException {
//		writeByte(v);
//	}
//
//	@Override
//	public void writeByteArray(String key, byte[] bytes) throws IOException {
//		writeByteArray(bytes);
//	}
//
//	@Override
//	public void writeBytes(String key, String s) throws IOException {
//		writeBytes(s);
//	}
//
//	@Override
//	public void writeChar(String key, int v) throws IOException {
//		writeChar(v);
//	}
//
//	@Override
//	public void writeCharArray(String key, char[] chars) throws IOException {
//		writeCharArray(chars);
//	}
//
//	@Override
//	public void writeChars(String key, String s) throws IOException {
//		writeChars(s);
//	}
//
//	@Override
//	public void writeDouble(String key, double v) throws IOException {
//		writeDouble(v);
//	}
//
//	@Override
//	public void writeDoubleArray(String key, double[] doubles)
//			throws IOException {
//		writeDoubleArray(doubles);
//	}
//
//	@Override
//	public void writeExternal(String key, ExternalizableMessage data)
//			throws IOException {
//		writeExternal(data);
//	}
//
//	@Override
//	public <T extends ExternalizableMessage> void writeExternalArray(
//			String key, T[] data) throws IOException {
//		writeExternalArray(data);
//	}
//
//	@Override
//	public void writeFloat(String key, float v) throws IOException {
//		writeFloat(v);
//	}
//
//	@Override
//	public void writeFloatArray(String key, float[] floats) throws IOException {
//		writeFloatArray(floats);
//	}
//
//	@Override
//	public void writeInt(String key, int v) throws IOException {
//		writeInt(v);
//	}
//
//	@Override
//	public void writeIntArray(String key, int[] ints) throws IOException {
//		writeIntArray(ints);
//	}
//
//	@Override
//	public void writeLong(String key, long v) throws IOException {
//		writeLong(v);
//	}
//
//	@Override
//	public void writeLongArray(String key, long[] longs) throws IOException {
//		writeLongArray(longs);
//	}
//
//	@Override
//	public void writeObject(String key, Object data) throws IOException {
//		writeObject(data);
//	}
//
//	@Override
//	public void writeObjectArray(String key, Object[] data) throws IOException {
//		writeObjectArray(data);
//	}
//
//	@Override
//	public void writeShort(String key, int v) throws IOException {
//		writeShort(v);
//	}
//
//	@Override
//	public void writeShortArray(String key, short[] shorts) throws IOException {
//		writeShortArray(shorts);
//	}
//
//	@Override
//	public void writeUTF(String key, String s) throws IOException {
//		writeUTF(s);
//	}
//
//	@Override
//	public void writeUTFArray(String key, String[] data) throws IOException {
//		writeUTFArray(data);
//	}
//
////	-----------------------------------------------------------------------------------------------------------
////	
////	-----------------------------------------------------------------------------------------------------------

	

	
}
