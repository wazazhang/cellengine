package com.cell.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;





public class StringUtil
{	
	public static Double[] getDoubleObjArray(String str, String delimiter)
	{
		if ( (str==null) || str.isEmpty() )
			return null;

		String[] strs = str.split(delimiter);

		Double[] ret = new Double[strs.length];
		int i = 0;
		for ( String s : strs )
			ret[i++] = Double.parseDouble(s);

		return ret;
	}	
	
	public static double[] getDoubleArray(String str, String delimiter)
	{
		if ( (str==null) || str.isEmpty() )
			return null;

		String[] strs = str.split(delimiter);

		double[] ret = new double[strs.length];
		int i = 0;
		for ( String s : strs )
			ret[i++] = Double.parseDouble(s);

		return ret;
	}	
	
	public static Float[] getFloatObjArray(String str, String delimiter)
	{
		if ( (str==null) || str.isEmpty() )
			return null;

		String[] strs = str.split(delimiter);

		Float[] ret = new Float[strs.length];
		int i = 0;
		for ( String s : strs )
			ret[i++] = Float.parseFloat(s);

		return ret;
	}	
	
	public static float[] getFloatArray(String str, String delimiter)
	{
		if ( (str==null) || str.isEmpty() )
			return null;

		String[] strs = str.split(delimiter);

		float[] ret = new float[strs.length];
		int i = 0;
		for ( String s : strs )
			ret[i++] = Float.parseFloat(s);

		return ret;
	}	
	
	public static Long[] getLongObjArray(String str, String delimiter)
	{
		if ( (str==null) || str.isEmpty() )
			return null;

		String[] strs = str.split(delimiter);

		Long[] ret = new Long[strs.length];
		int i = 0;
		for ( String s : strs )
			ret[i++] = Long.parseLong(s);

		return ret;
	}	
	
	public static long[] getLongArray(String str, String delimiter)
	{
		if ( (str==null) || str.isEmpty() )
			return null;

		String[] strs = str.split(delimiter);

		long[] ret = new long[strs.length];
		int i = 0;
		for ( String s : strs )
			ret[i++] = Long.parseLong(s);

		return ret;
	}
	
	public static Integer[] getIntegerObjArray(String str, String delimiter)
	{
		if ( (str==null) || str.isEmpty() )
			return null;

		String[] strs = str.split(delimiter);

		Integer[] ret = new Integer[strs.length];
		int i = 0;
		for ( String s : strs )
			ret[i++] = Integer.parseInt(s);

		return ret;
	}	
	
	public static int[] getIntegerArray(String str, String delimiter)
	{
		if ( (str==null) || str.isEmpty() )
			return null;

		String[] strs = str.split(delimiter);

		int[] ret = new int[strs.length];
		int i = 0;
		for ( String s : strs )
			ret[i++] = Integer.parseInt(s);

		return ret;
	}
	
	public static Short[] getShortObjArray(String str, String delimiter)
	{
		if ( (str==null) || str.isEmpty() )
			return null;

		String[] strs = str.split(delimiter);

		Short[] ret = new Short[strs.length];
		int i = 0;
		for ( String s : strs )
			ret[i++] = Short.parseShort(s);

		return ret;
	}	
	
	public static short[] getShortArray(String str, String delimiter)
	{
		if ( (str==null) || str.isEmpty() )
			return null;

		String[] strs = str.split(delimiter);

		short[] ret = new short[strs.length];
		int i = 0;
		for ( String s : strs )
			ret[i++] = Short.parseShort(s);

		return ret;
	}
	
	public static Byte[] getByteObjArray(String str, String delimiter)
	{
		if ( (str==null) || str.isEmpty() )
			return null;

		String[] strs = str.split(delimiter);

		Byte[] ret = new Byte[strs.length];
		int i = 0;
		for ( String s : strs )
			ret[i++] = Byte.parseByte(s);

		return ret;
	}	
	
	public static byte[] getByteArray(String str, String delimiter)
	{
		if ( (str==null) || str.isEmpty() )
			return null;

		String[] strs = str.split(delimiter);

		byte[] ret = new byte[strs.length];
		int i = 0;
		for ( String s : strs )
			ret[i++] = Byte.parseByte(s);

		return ret;
	}	
	
	
}; // class


