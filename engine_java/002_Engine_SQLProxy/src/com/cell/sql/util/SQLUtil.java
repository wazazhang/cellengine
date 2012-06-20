package com.cell.sql.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.cell.CUtil;
import com.cell.sql.SQLColumn;
import com.cell.sql.SQLFieldGroup;
import com.cell.sql.SQLStructCLOB;
import com.cell.sql.SQLDriverManager;

public class SQLUtil
{
	final static public byte[] ZERO_DATA = new byte[0];
	final static public String ZERO_TEXT = "";
	
	/**
	 * 将一个struct编码成Blob
	 * @param struct
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static byte[] blobToBin(Serializable struct) throws Exception
	{
		if (struct == null) {
			return ZERO_DATA;
		}
		ByteArrayOutputStream	baos	= new ByteArrayOutputStream(1024);
		ObjectOutputStream		oos		= new ObjectOutputStream(baos);
		try{
			oos.writeObject(struct);
			return baos.toByteArray();
		}finally{
			oos.close();
		}
	}
	
	/**
	 * 将一个Blob解码成struct
	 * @param <T>
	 * @param blob
	 * @return
	 * @throws Exception
	 */
	public static Serializable binToBlob(byte[] data) throws Exception
	{
		if (data == null || data.length == 0) {
			return null;
		}
		ByteArrayInputStream	bais	= new ByteArrayInputStream(data);
		ObjectInputStream		ois		= new ObjectInputStream(bais);
		try{
			Serializable ret = (Serializable)ois.readObject();
			return ret;
		} finally {
			ois.close();
		}
	}
	

//	---------------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * 将一个struct编码成Blob
	 * @param struct
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static String clobToString(SQLStructCLOB struct) throws Exception
	{
		if (struct == null) {
			return ZERO_TEXT;
		}
		return struct.encode();
	}
	
	/**
	 * 将一个Blob解码成struct
	 * @param <T>
	 * @param blob
	 * @return
	 * @throws Exception
	 */
	public static SQLStructCLOB stringToClob(String data, Class<?> clazz) throws Exception
	{
		if (data == null || data.length() == 0) {
			return null;
		}
		SQLStructCLOB ret = (SQLStructCLOB) clazz.newInstance();
		ret.decode(data);
		return ret;
	}

	
//	---------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 将一个struct编码成String
	 * @param struct
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static String xmlToString(Serializable struct) throws Exception
	{
		if (struct == null) {
			return ZERO_TEXT;
		}
		StringWriter 		sw	= new StringWriter(1024);
		ObjectOutputStream	oos	= SQLDriverManager.getDriver().getXMLOutputStream(sw);
		try{
			oos.writeObject(struct);
			oos.flush();
			return sw.toString();
		}finally{
			oos.close();
		}
	}
	
	/**
	 * 将一个String解码成struct
	 * @param <T>
	 * @param blob
	 * @return
	 * @throws Exception
	 */
	public static Serializable stringToXML(String data) throws Exception
	{
		if (data == null || data.length() == 0) {
			return null;
		}
		StringReader		sr	= new StringReader(data);
		ObjectInputStream	ois	= SQLDriverManager.getDriver().getXMLInputStream(sr);
		try{
			Serializable ret = (Serializable)ois.readObject();
			return ret;
		}finally{
			ois.close();
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 将src所有SQLField字段的值赋给dst
	 * @param <T>
	 * @param src
	 * @param dst
	 * @throws Exception
	 */
	public static <T extends SQLFieldGroup> void setSQLFields(T src, T dst) throws Exception
	{
		setSQLFields(SQLColumn.getSQLColumns(src.getClass()), src, dst);
	}
	
	/**
	 * 将src所有SQLField字段的值赋给dst
	 * @param <T>
	 * @param sql_columns
	 * @param src
	 * @param dst
	 * @throws Exception
	 */
	public static <T extends SQLFieldGroup> void setSQLFields(
			SQLColumn[] sql_columns,
			T src,
			T dst) throws Exception
	{
		for (SQLColumn c : sql_columns) {
			c.setLeafField(dst, c.getLeafField(src));
		}
	}
	
	/**
	 * 得到该类所有签名的字段
	 * @param clazz
	 * @return
	 */
	public static SQLColumn[] getSQLColumns(Class<? extends SQLFieldGroup> clazz)
	{
		return SQLColumn.getSQLColumns(clazz);
	}

	public static HashMap<String, SQLColumn> getSQLColumnsMap(Class<? extends SQLFieldGroup> clazz)
	{
		SQLColumn[] columns = SQLColumn.getSQLColumns(clazz);
		
		HashMap<String, SQLColumn> ret = new HashMap<String, SQLColumn>(columns.length);
		
		for (SQLColumn c : columns) {
			ret.put(c.getName(), c);
		}
		
		return ret;
	}

//	-------------------------------------------------------------------------------------------------
	
	public static PrintStream printTable(Class<? extends SQLFieldGroup> clazz, Collection<? extends SQLFieldGroup> list, PrintStream out) 
	{
		SQLColumn[] columns = getSQLColumns(clazz);
		
		ArrayList<String> hd_line = new ArrayList<String>();
		for (int c=0; c<columns.length; c++) {
			String name = columns[c].getName();
			hd_line.add(name);
		}

		ArrayList<List<String>> lines = new ArrayList<List<String>>();
		for (SQLFieldGroup o : list) {
			ArrayList<String> line = new ArrayList<String>();
			for (int c=0; c<columns.length; c++) {
				try {
					Object v = columns[c].getLeafField(o);
					if (columns[c].getLeafField().getType().isArray()) {
						line.add(CUtil.arrayObjectToString(v, ",", ""));
					} else {
						line.add(v+"");
					}
				} catch (Exception e) {
					e.printStackTrace();
					line.add("e???");
				}
			}
			lines.add(line);
		}
		
		return CUtil.printTable(hd_line, lines, out);
	}
	

	
}
