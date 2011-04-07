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

//	-----------------------------------------------------------------------------------------------------------
//	


	@Override
	public void writeAnyArray(Object array) throws IOException {
		if (array != null) {
			int count = Array.getLength(array);
			if (count > 0) {
				Class<?> component_type = array.getClass().getComponentType();
				if (component_type.isArray()) {
					writeInt(-count); 	// 表示成员还是个数组
					for (int i = 0; i < count; i++) {
						writeAnyArray(Array.get(array, i));
					}
				} else {
					writeInt(count);	// 表示成员是个通常对象
					byte component_data_type = NetDataTypes.getArrayCompomentType(component_type, factory);
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

//	-----------------------------------------------------------------------------------------------------------
//	
	
	
	public void writeExternal(ExternalizableMessage data) throws IOException {
		if (data != null) {
			int msg_type = factory.getType(data.getClass());
			buffer.putInt(msg_type);
			if (msg_type != 0) {
				data.writeExternal(this);
			}
		} else {
			buffer.putInt(0);
		}
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
	
	
}
