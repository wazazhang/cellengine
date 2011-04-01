package com.cell.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOError;
import java.io.IOException;
import java.io.ObjectOutput;

public class ExternalizableUtil
{
	public static void writeBooleanArray(DataOutput out, boolean[] bools) throws IOException
	{
		if (bools != null) {
			out.writeInt(bools.length);
			for (int i=0; i<bools.length; i++) {
				out.writeBoolean(bools[i]);
			}
		} else {
			out.writeInt(0);
		}
	}
	
	public static void writeCharArray(DataOutput out, char[] chars) throws IOException
	{
		if (chars != null) {
			out.writeInt(chars.length);
			for (int i=0; i<chars.length; i++) {
				out.writeChar(chars[i]);
			}
		} else {
			out.writeInt(0);
		}
	}
	
	public static void writeByteArray(DataOutput out, byte[] bytes) throws IOException
	{
		if (bytes != null) {
			out.writeInt(bytes.length);
			out.write(bytes);
		} else {
			out.writeInt(0);
		}
	}
	
	public static void writeShortArray(DataOutput out, short[] shorts) throws IOException
	{
		if (shorts != null) {
			out.writeInt(shorts.length);
			for (int i=0; i<shorts.length; i++) {
				out.writeShort(shorts[i]);
			}
		} else {
			out.writeInt(0);
		}
	}
	
	public static void writeIntArray(DataOutput out, int[] ints) throws IOException
	{
		if (ints != null) {
			out.writeInt(ints.length);
			for (int i=0; i<ints.length; i++) {
				out.writeInt(ints[i]);
			}
		} else {
			out.writeInt(0);
		}
	}
	
	public static void writeLongArray(DataOutput out, long[] longs) throws IOException
	{
		if (longs != null) {
			out.writeInt(longs.length);
			for (int i=0; i<longs.length; i++) {
				out.writeLong(longs[i]);
			}
		} else {
			out.writeInt(0);
		}
		
	}
	
	public static void writeFloatArray(DataOutput out, float[] floats) throws IOException
	{
		if (floats != null) {
			out.writeInt(floats.length);
			for (int i=0; i<floats.length; i++) {
				out.writeFloat(floats[i]);
			}
		} else {
			out.writeInt(0);
		}
		
	}
	
	public static void writeDoubleArray(DataOutput out, double[] doubles) throws IOException
	{
		if (doubles != null) {
			out.writeInt(doubles.length);
			for (int i=0; i<doubles.length; i++) {
				out.writeDouble(doubles[i]);
			}
		} else {
			out.writeInt(0);
		}
		
	}
	
	
//	-----------------------------------------------------------------------------------------------------------------
	
	public static boolean[] readBooleanArray(DataInput in) throws IOException
	{
		boolean[] ret = new boolean[in.readInt()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = in.readBoolean();
		}
		return ret;
	}
	
	public static char[] readCharArray(DataInput in) throws IOException
	{
		char[] ret = new char[in.readInt()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = in.readChar();
		}
		return ret;
	}
	
	public static byte[] readByteArray(DataInput in) throws IOException
	{
		byte[] ret = new byte[in.readInt()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = in.readByte();
		}
		return ret;
	}
	
	public static short[] readShortArray(DataInput in) throws IOException
	{
		short[] ret = new short[in.readInt()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = in.readShort();
		}
		return ret;
	}
	
	public static int[] readIntArray(DataInput in) throws IOException
	{
		int[] ret = new int[in.readInt()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = in.readInt();
		}
		return ret;
	}
	
	public static long[] readLongArray(DataInput in) throws IOException
	{
		long[] ret = new long[in.readInt()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = in.readLong();
		}
		return ret;
	}
	
	public static float[] readFloatArray(DataInput in) throws IOException
	{
		float[] ret = new float[in.readInt()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = in.readFloat();
		}
		return ret;
	}
	
	public static double[] readDoubleArray(DataInput in) throws IOException
	{
		double[] ret = new double[in.readInt()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = in.readDouble();
		}
		return ret;
	}
	
	
	
}
