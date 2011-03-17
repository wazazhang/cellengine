package com.cell.nio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.cell.CObject;
import com.cell.io.NIODeserialize;
import com.cell.io.NIOSerialize;
import com.cell.nio.anno.Seri;

/**
 * @since 2009-06-02
 * @author WAZA
 * 通过反射机制获得该字段是否参加序列化反序列化
 */
public class SeriObject 
{
	Field[] seri_fields;
	
	public Field[] getSeriFields(){
		if (seri_fields == null) {
			seri_fields = getSeriFields(getClass());
		}
		return seri_fields;
	}
	
	public void deserializeAnno(ByteBuffer in) throws Exception {
		readAnno(this, getSeriFields(), in);
	}
	
	public void serializeAnno(ByteBuffer out) throws Exception {
		writeAnno(this, getSeriFields(), out);
	}
	
	
//	-------------------------------------------------------------------------------------------------------------------------------
//	util
	
	/**缓冲类对应缓冲字段列表*/
	static ConcurrentHashMap<Type, Field[]> SeriFieldMap = new ConcurrentHashMap<Type, Field[]>();

	/**
	 * 获得可序列化字段列表
	 * @return
	 */
	synchronized static Field[] getSeriFields(Class<?> type) 
	{
		Field[] ret = SeriFieldMap.get(type);
		if (ret == null) {
			ArrayList<Field>	sfields	= new ArrayList<Field>(); 
			Field[]				fields 	= type.getFields();
			for (Field field : fields) {
				if (field.getAnnotation(Seri.class)!=null) {
					sfields.add(field);
				}
			}
			ret = sfields.toArray(new Field[sfields.size()]);
			SeriFieldMap.put(type, ret);
		}
		return ret;
	}

	static void putString(ByteBuffer buffer, String string) {
		try {
			byte[] stringBytes = string.getBytes(CObject.getEncoding());
			buffer.putInt(stringBytes.length);
			buffer.put(stringBytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	static String getString(ByteBuffer buffer) {
		 int stringLen = buffer.getInt();
		 if (stringLen>=0){
		        byte []stringData = new byte[stringLen];
		        buffer.get(stringData);
		        try{
		        	return new String(stringData, CObject.getEncoding());
		        }catch(UnsupportedEncodingException err){
		        	err.printStackTrace();
		        }
		    }else{
	        	System.err.println("Deserialize.getString(): NegativeArraySize: "+stringLen);
	        }
	        return null;
	}
	
	public static void readAnno(Object obj, Field[] fields, ByteBuffer is) throws Exception 
	{
		for (Field f : fields) 
		{
			Type cls = f.getType();
			
			// 基础类型
			if (cls.equals(boolean.class) || cls.equals(Boolean.class)) {
				f.setBoolean(obj, is.get()!=0);
			} else if (cls.equals(byte.class) || cls.equals(Byte.class)) {
				f.setByte(obj, is.get());
			} else if (cls.equals(char.class) || cls.equals(Character.class)) {
				f.setChar(obj, is.getChar());
			} else if (cls.equals(short.class) || cls.equals(Short.class)) {
				f.setShort(obj, is.getShort());
			} else if (cls.equals(int.class) || cls.equals(Integer.class)) {
				f.setInt(obj, is.getInt());
			} else if (cls.equals(long.class) || cls.equals(Long.class)) {
				f.setLong(obj, is.getLong());
			} else if (cls.equals(float.class) || cls.equals(Float.class)) {
				f.setFloat(obj, is.getFloat());
			} else if (cls.equals(double.class) || cls.equals(Double.class)) {
				f.setDouble(obj, is.getDouble());
			} 
			// java String
			else if (cls.equals(String.class)) {
				f.set(obj, getString(is));
			}
			// 自定义类型
			else {
				Object sub = f.get(obj);
				if (sub instanceof SeriObject) {
					readAnno(sub, ((SeriObject)sub).getSeriFields(), is);
				} else {
					readAnno(sub, getSeriFields(sub.getClass()), is);
				}
			}
		}
	}
	

	public static void writeAnno(Object obj, Field[] fields, ByteBuffer os) throws Exception 
	{
		for (Field f : fields) 
		{
			Type cls = f.getType();
			
			// 基础类型
			if (cls.equals(boolean.class) || cls.equals(Boolean.class)) {
				os.put((byte)(f.getBoolean(obj)?1:0));
			} else if (cls.equals(byte.class) || cls.equals(Byte.class)) {
				os.put(f.getByte(obj));
			} else if (cls.equals(char.class) || cls.equals(Character.class)) {
				os.putChar(f.getChar(obj));
			} else if (cls.equals(short.class) || cls.equals(Short.class)) {
				os.putShort(f.getShort(obj));
			} else if (cls.equals(int.class) || cls.equals(Integer.class)) {
				os.putInt(f.getInt(obj));
			} else if (cls.equals(long.class) || cls.equals(Long.class)) {
				os.putLong(f.getLong(obj));
			} else if (cls.equals(float.class) || cls.equals(Float.class)) {
				os.putFloat(f.getFloat(obj));
			} else if (cls.equals(double.class) || cls.equals(Double.class)) {
				os.putDouble(f.getDouble(obj));
			} 
			// java String
			else if (cls.equals(String.class)) {
				putString(os, obj.toString());
			}
			// 自定义类型
			else {
				Object sub = f.get(obj);
				if (sub instanceof SeriObject) {
					writeAnno(sub, ((SeriObject)sub).getSeriFields(), os);
				} else {
					writeAnno(sub, getSeriFields(sub.getClass()), os);
				}
			}
		}
	}
	
	
	static class TestHeader extends SeriObject
	{
		@Seri
		public int		data	= 1;
		@Seri
		public byte		data2	= 2;
		
		public TestHeader() {}
		
		public void deserialize(ByteBuffer is) throws IOException {
			data	= is.getInt();
			data2	= is.get();
//			data 	= NIODeserialize.getInt(is);
//			data2	= NIODeserialize.getByte(is);
		}
		
		public void serialize(ByteBuffer os) throws IOException {
			os.putInt(data);
			os.put(data2);
//			NIOSerialize.putInt(os, data);
//			NIOSerialize.putByte(os, data2);
		}
		
	}
	
	public static void main(String[] args) throws Exception
	{
		TestHeader header = new TestHeader();
		long time ;
		
		ByteBuffer 	bb2	= ByteBuffer.allocateDirect(100000);
		ByteBuffer 	bb1	= ByteBuffer.allocateDirect(100000);
		
		
		time = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			header.serializeAnno(bb2);
		}
		bb2.position(0);
		for (int i = 0; i < 10000; i++) {
			header.deserialize(bb2);
		}
		System.out.println("anno\t"+(System.nanoTime() - time));
		
		
		

		time = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			header.serialize(bb1);
		}
		bb1.position(0);
		for (int i = 0; i < 10000; i++) {
			header.deserialize(bb1);
		}
		System.out.println("direct\t" + (System.nanoTime() - time));

		
	}
	
	
}
