package com.cell.rpg.xls;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;

import com.cell.CIO;
import com.cell.reflect.Parser;
import com.cell.sql.SQLColumn;
import com.cell.sql.SQLStructCLOB;
import com.cell.sql.SQLTableManager;
import com.cell.sql.SQLTableRow;

public class XLSTable<V extends SQLTableRow<?>>
{	
	ArrayList<V> values = new ArrayList<V>();
	
	public XLSTable(String file, Class<V> cls) throws Exception
	{
		this(file, cls, new XLSRowFactory.ObjectClassFactory<V>(cls));
	}
	
	public XLSTable(InputStream is, Class<V> cls) throws Exception
	{
		this(is, cls, new XLSRowFactory.ObjectClassFactory<V>(cls));
	}

	public XLSTable(String file, Class<V> cls, XLSRowFactory<V> factory) throws Exception
	{
		System.out.println("Read XLSTable : " + file);
		InputStream is = CIO.loadStream(file);
		init(is, cls, factory);
	}
	
	public XLSTable(InputStream is, Class<V> cls, XLSRowFactory<V> factory) throws Exception
	{
		init(is, cls, factory);
	}
	
	public XLSTable(byte[] data, Class<V> cls, XLSRowFactory<V> factory) throws Exception
	{
		init(new ByteArrayInputStream(data), cls, factory);
	}
	
	public XLSTable(byte[] data, Class<V> cls) throws Exception
	{
		init(new ByteArrayInputStream(data), cls, new XLSRowFactory.ObjectClassFactory<V>(cls));
	}
	
	final protected void init(InputStream is, Class<V> cls, XLSRowFactory<V> factory) throws Exception
	{
	    System.out.println("Init XLSTable : table class : " + cls.getSimpleName());
	    
		SQLColumn[]	sql_columns		= SQLTableManager.getSQLColumns(cls);
		Workbook	work_book		= Workbook.getWorkbook(is);
		try {
			for (String rs : work_book.getSheetNames()) {
				Map<?, V> map = XLSUtil.readTable(
						work_book, rs, 1, 1, 1, 
						sql_columns, Object.class, cls, factory);
				values.addAll(map.values());
			}
		} finally {
			work_book.close();
		}
	}
	
	public ArrayList<V> getValues() 
	{
		return values;
	}
	
	public<K> Map<K, V> getTableMap(Class<K> kt)
	{
		Map<K, V>ret = new LinkedHashMap<K, V>();
		for (V v : getValues()) {
			ret.put(kt.cast(v.getPrimaryKey()), v);
		}
		return ret;
	}
	

	


}


