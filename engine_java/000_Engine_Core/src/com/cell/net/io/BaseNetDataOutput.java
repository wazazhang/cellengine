package com.cell.net.io;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import com.cell.io.ExternalizableUtil;

public abstract class BaseNetDataOutput implements NetDataOutput
{
	final protected ExternalizableFactory factory;
	
	public BaseNetDataOutput(ExternalizableFactory factory) 
	{
		this.factory = factory;
	}
	
	@Override
	final public ExternalizableFactory getFactory() {
		return factory;
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

	public void writeExternalArray(Object array) throws IOException {
		if (array != null) {
			int len = Array.getLength(array);
			writeInt(len);
			for (int i=0; i<len; i++) {
				writeExternal((ExternalizableMessage)Array.get(array, i));
			}
		} else {
			writeInt(0);
		}
	}
	
	public void writeMutualArray(Object array) throws IOException {
		if (array != null) {
			int len = Array.getLength(array);
			writeInt(len);
			for (int i=0; i<len; i++) {
				writeMutual((MutualMessage)Array.get(array, i));
			}
		} else {
			writeInt(0);
		}
	}
	
	public void writeObjectArray(Object array) throws IOException {
		if (array != null) {
			int len = Array.getLength(array);
			writeInt(len);
			for (int i=0; i<len; i++) {
				writeObject(Array.get(array, i));
			}
		} else {
			writeInt(0);
		}
	}
	

	
//	-----------------------------------------------------------------------------------------------------------
//	
	
	
	public void writeExternal(ExternalizableMessage data) throws IOException {
		if (data != null) {
			int msg_type = getFactory().getMessageType(data.getClass());
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
			int msg_type = getFactory().getMessageType(data.getClass());
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
	
	protected void writeAny(byte component_data_type, Object obj) throws IOException 
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
		case NetDataTypes.TYPE_DATE:
			writeDate((Date)obj);
			break;
			
		case NetDataTypes.TYPE_OBJECT: 
			writeObject(obj);
			break;
		
		default:
		}
	}

	
	protected void writeAnyMutual(byte component_data_type, Object obj) throws IOException 
	{
		switch (component_data_type) {
//		case NetDataTypes.TYPE_EXTERNALIZABLE:
//			writeExternal((ExternalizableMessage)obj);
//			break;
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
		case NetDataTypes.TYPE_DATE: 
			writeDate((Date)obj);
			break;
			
//		case NetDataTypes.TYPE_OBJECT: 
//			writeObject(obj);
//			break;
		default:
		}
	}

	
	
	
	@Override
	public void writeCollection(Collection<?> array, byte compNetType) throws IOException {
		if (array != null) {
			int count = array.size();
			writeInt(count);
			if (count > 0) {
				for (Object o : array) {
					writeAnyMutual(compNetType, o);
				}
			}
		} else {
			writeInt(0);
		}
	}
	

	@SuppressWarnings("rawtypes")
	@Override
	public void writeMap(Map<?,?> map, byte keyNetType, byte valueNetType) throws IOException 
	{
		if (map != null) {
			int count = map.size();
			writeInt(count);
			if (count > 0) {
				for (Entry e : map.entrySet()) {
					Object key = e.getKey();
					Object value = e.getValue();
					writeAnyMutual(keyNetType,   key);
					writeAnyMutual(valueNetType, value);
				}
			}
		} else {
			writeInt(0);
		}
	}
	

	@Override
	public void writeDate(Date date) throws IOException 
	{
		if (date == null) {
			writeByte(0);
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			writeByte(1);
			
			writeShort(cal.get(Calendar.YEAR));
			writeByte(cal.get(Calendar.MONTH));
			writeByte(cal.get(Calendar.DATE));
			
			writeByte(cal.get(Calendar.HOUR_OF_DAY));
			writeByte(cal.get(Calendar.MINUTE));
			writeByte(cal.get(Calendar.SECOND));
			
			writeShort(cal.get(Calendar.MILLISECOND));
			
			cal = null;
		}
	}
	
	
	
}
