package com.cell.io;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;


public abstract class IInput 
{

	public abstract String 		getString() 				throws IOException;
	public abstract String 		getString(String charset) 	throws IOException ;
	public abstract byte 		getByte() 					throws IOException;
	public abstract short 		getUByte() 					throws IOException;
	public abstract boolean		getBoolean() 				throws IOException;
	public abstract short		getShort() 					throws IOException;
	public abstract int			getUShort() 				throws IOException;
	public abstract int 		getInt() 					throws IOException;
	public abstract long 		getUInt() 					throws IOException;
	public abstract long 		getLong() 					throws IOException;
	public abstract float 		getFloat() 					throws IOException;
	public abstract double 		getDouble() 				throws IOException;
	
//	---------------------------------------------------------------------------------------------------------------------------	
	
	Hashtable<Integer, ISerializable> objects = new Hashtable<Integer, ISerializable>();
	
	synchronized final public Object getJObject() throws IOException
	{
		JSerializableRef object = getObject();
		return object.object;
	}
	
	
	@SuppressWarnings("unchecked")
	synchronized final public <T extends ISerializable> T getObject() throws IOException
	{
		int id = this.getInt();
		
		if (!objects.containsKey(id)) 
		{
			String clsname = this.getString();

			try
			{
				T obj = (T)Class.forName(clsname).newInstance();
				obj.deserialize(this);
				objects.put(id, obj);

				return obj;
			}
			catch (Throwable e) {
				System.err.println("serialize \"" + clsname + "\" error !");
				e.printStackTrace();
			}
			
			return null;
		}
		else 
		{
			return (T)objects.get(id);
		}
		
	}
	
	final public <T extends ISerializable> Vector<T> getObjects() throws IOException
	{
		int count = this.getInt();
		Vector<T> ret = new Vector<T>(count);
		for (int i=0; i<count; i++) {
			T obj = this.getObject();
			ret.add(obj);
		}
		return ret;
	}

	final public Vector<Object> getJObjects() throws IOException
	{
		int count = this.getInt();
		Vector<Object> ret = new Vector<Object>(count);
		for (int i=0; i<count; i++) {
			Object obj = this.getJObject();
			ret.add(obj);
		}
		return ret;
	}
	//////////////////////////////////////////////////////////////////////////////////////
	// get array
	
	public final String[] 	getStrings() 				throws IOException
	{
		String[] ret = new String[this.getInt()];
		for (int i=0; i<ret.length; i++) {
			ret[i] = this.getString();
		}
		return ret;
	}
	
	public final String[] 	getStrings(String charset) 	throws IOException 
	{
		String[] ret = new String[this.getInt()];
		for (int i=0; i<ret.length; i++) {
			ret[i] = this.getString(charset);
		}
		return ret;
	}
	
	public final boolean[] 	getBooleans() 				throws IOException
	{
		boolean[] ret = new boolean[this.getInt()];
		for (int i=0; i<ret.length; i++) {
			ret[i] = this.getBoolean();
		}
		return ret;
	}
	
	public final byte[] 	getBytes() 					throws IOException
	{
		byte[] ret = new byte[this.getInt()];
		for (int i=0; i<ret.length; i++) {
			ret[i] = this.getByte();
		}
		return ret;
	}
	
	public final short[] 	getUBytes() 				throws IOException
	{
		short[] ret = new short[this.getInt()];
		for (int i=0; i<ret.length; i++) {
			ret[i] = this.getUByte();
		}
		return ret;
	}
	
	public final short[] 	getShorts() 				throws IOException
	{
		short[] ret = new short[this.getInt()];
		for (int i=0; i<ret.length; i++) {
			ret[i] = this.getShort();
		}
		return ret;
	}
	
	public final int[] 		getUShorts()				throws IOException
	{
		int[] ret = new int[this.getInt()];
		for (int i=0; i<ret.length; i++) {
			ret[i] = this.getUShort();
		}
		return ret;
	}
	
	public final int[] 		getInts() 					throws IOException
	{
		int[] ret = new int[this.getInt()];
		for (int i=0; i<ret.length; i++) {
			ret[i] = this.getInt();
		}
		return ret;
	}
	
	
	public final long[] 	getUInts()					throws IOException
	{
		long[] ret = new long[this.getInt()];
		for (int i=0; i<ret.length; i++) {
			ret[i] = this.getUInt();
		}
		return ret;
	}
	
	public final long[] 	getLongs() 					throws IOException
	{
		long[] ret = new long[this.getInt()];
		for (int i=0; i<ret.length; i++) {
			ret[i] = this.getLong();
		}
		return ret;
	}
	
	
}
