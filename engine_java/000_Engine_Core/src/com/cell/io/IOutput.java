package com.cell.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


public abstract class IOutput 
{
	public abstract void putString(String string) 					throws IOException;
	public abstract void putString(String string, String charset) 	throws IOException;
	public abstract void putByte(byte value) 						throws IOException;
	public abstract void putUByte(short value) 						throws IOException;
	public abstract void putBoolean(boolean value) 					throws IOException;
	public abstract void putShort(short value) 						throws IOException;
	public abstract void putUShort(int value) 						throws IOException;
	public abstract void putInt(int value) 							throws IOException;
	public abstract void putUInt(long value) 						throws IOException;
	public abstract void putLong(long value) 						throws IOException;
	public abstract void putFloat(float value) 						throws IOException;
	public abstract void putDouble(double value) 					throws IOException;
	/////////////////////////////////////////////////////////////////////////////////////////////

	
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	Hashtable<ISerializable, Integer> objects = new Hashtable<ISerializable, Integer>();
	
	protected IOutput(){}
	
	synchronized final public int putJObject(Object obj) throws IOException
	{
		JSerializableRef object = new JSerializableRef(obj);
		return putObject(object);
	}
	
	
	@SuppressWarnings("unused")
	synchronized final public <T extends ISerializable> int putObject(T obj) throws IOException
	{
		int id = 0;
		
		// 如果对象包里没有该对象，则插入新条目并生成id
		if (!objects.containsKey(obj)) 
		{
			// 生成新的id，并存储
			id = objects.size();
			this.putInt(id);
			
			// 记录id 
			objects.put(obj, id);
			
			// 存储类名，用于反射，如果该对象没有默认无参数构造函数，则会有异常
			String clsname = obj.getClass().getName();
			this.putString(clsname);
			
			try{
				// check Instantiation
				ISerializable ser = (ISerializable)Class.forName(clsname).newInstance();
				obj.serialize(this);
			}
			catch (Throwable e) {
				System.err.println("serialize \"" + clsname + "\" error !");
				e.printStackTrace();
			}
		}
		// 如果对象包里已经有该对象，则只需要存储id
		else 
		{
			id = objects.get(obj);
			this.putInt(id);
		}
		
		return id;
	}
	
	public final<T extends ISerializable> void putObjects(T[] objs) throws IOException
	{
		synchronized (objs) {
			this.putInt(objs.length);
			for (int i=0; i<objs.length; i++) {
				this.putObject(objs[i]);
			}
		}
	}
	
	public final<T extends ISerializable> void putObjects(Vector<T> objs) throws IOException
	{
		synchronized (objs) {
			int count = objs.size();
			this.putInt(count);
			for (int i=0; i<count; i++) {
				this.putObject(objs.elementAt(i));
			}
		}
	}
	
	public final void putJObjects(Object[] objs) throws IOException
	{
		synchronized (objs) {
			this.putInt(objs.length);
			for (int i=0; i<objs.length; i++) {
				this.putJObject(objs[i]);
			}
		}
	}
	
	public final void putJObjects(Vector<?> objs) throws IOException
	{
		synchronized (objs) {
			int count = objs.size();
			this.putInt(count);
			for (int i=0; i<count; i++) {
				this.putJObject(objs.elementAt(i));
			}
		}
	}
	
	
	
	public final void putStrings(String[] value) 				throws IOException
	{
		this.putInt(value.length);
		for (int i=0; i<value.length; i++) {
			this.putString(value[i]);
		}
	}
	
	public final void putStrings(String[] value, String charset) throws IOException
	{
		this.putInt(value.length);
		for (int i=0; i<value.length; i++) {
			this.putString(value[i], charset);
		}
	}
	
	public final void putBooleans(boolean[] value) 				throws IOException
	{
		this.putInt(value.length);
		for (int i=0; i<value.length; i++) {
			this.putBoolean(value[i]);
		}
	}
	
	public final void putBytes(byte[] value)					throws IOException
	{
		this.putInt(value.length);
		for (int i=0; i<value.length; i++) {
			this.putByte(value[i]);
		}
	}
	
	public final void putUBytes(short[] value) 					throws IOException
	{
		this.putInt(value.length);
		for (int i=0; i<value.length; i++) {
			this.putUByte(value[i]);
		}
	}
	
	public final void putShorts(short[] value) 					throws IOException
	{
		this.putInt(value.length);
		for (int i=0; i<value.length; i++) {
			this.putShort(value[i]);
		}
	}
	
	public final void putUShorts(int[] value) 					throws IOException
	{
		this.putInt(value.length);
		for (int i=0; i<value.length; i++) {
			this.putUShort(value[i]);
		}
	}
	
	public final void putInts(int[] value) 						throws IOException
	{
		this.putInt(value.length);
		for (int i=0; i<value.length; i++) {
			this.putInt(value[i]);
		}
	}
	
	public final void putUInts(long[] value) 					throws IOException
	{
		this.putInt(value.length);
		for (int i=0; i<value.length; i++) {
			this.putUInt(value[i]);
		}
	}
	
	public final void putLongs(long[] value) 					throws IOException
	{
		this.putInt(value.length);
		for (int i=0; i<value.length; i++) {
			this.putLong(value[i]);
		}
	}
	
	
	
//	-----------------------------------------------------------------------------------------------------------------------------
//	test
	
	
	static class E1 implements ISerializable
	{
		
		static class E3 implements ISerializable
		{
			String text = "e3";
			
			E3(){}
			
			public void deserialize(IInput arg0) throws IOException {
				text = arg0.getString();
			}
			public void serialize(IOutput arg0) throws IOException {
				arg0.putString(text);
			}
			
			@Override
			public String toString() {
				return text + " " + hashCode();
			}
		}

		static class E2 implements ISerializable
		{
			int id = 2;
			
			public void deserialize(IInput arg0) throws IOException {
				id = arg0.getInt();
			}
			public void serialize(IOutput arg0) throws IOException {
				arg0.putInt(id);
			}
			
			@Override
			public String toString() {
				return id + " " + hashCode();
			}
		}
		
		E2 e2	= new E2();
		E3 e3	= new E3();
//		E2 e2m	= e2;
//		E3 e3m	= e3;
		E2 e2m	= new E2();
		E3 e3m	= new E3();
		
		
		public void deserialize(IInput arg0) throws IOException {
			e2 = arg0.getObject();
			e3 = arg0.getObject();
			e2m = arg0.getObject();
			e3m = arg0.getObject();
		}
		
		public void serialize(IOutput arg0) throws IOException {
			arg0.putObject(e2);
			arg0.putObject(e3);
			arg0.putObject(e2m);
			arg0.putObject(e3m);
		}
		
		@Override
		public String toString() {
			return "E1\n" +
					"[" + e2 + "," + e3 + "]\n" + 
					"[" + e2m + "," + e3m + "]";
		}
		
	}

	public static void main(String[] args)
	{
		try
		{
			
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			BigIOSerialize out = new BigIOSerialize(output);
			
			E1 src = new E1();
			out.putObject(src);
			System.out.println(src);
			
			ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
			BigIODeserialize in = new BigIODeserialize(input);
			
			E1 dst = in.getObject();
			System.out.println(dst);
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
