package com.net;

import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;

import com.cell.io.ExternalizableUtil;

public abstract class NetDataOutput implements DataOutput
{

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
					byte component_data_type = NetDataTypes.getArrayCompomentType(component_type, getFactory());
					writeByte(component_data_type);
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
	
	

	abstract public void writeObject(Object data) throws IOException;
		
	abstract public ExternalizableFactory getFactory();
	
	
}
