package com.cell.rpg.xls;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

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
		this(file, cls, new ObjectClassFactory<V>(cls));
	}
	
	public XLSTable(InputStream is, Class<V> cls) throws Exception
	{
		this(is, cls, new ObjectClassFactory<V>(cls));
	}

	public XLSTable(String file, Class<V> cls, TableFactory<V> factory) throws Exception
	{
		System.out.println("Read XLSTable : " + file);
		InputStream is = CIO.loadStream(file);
		init(is, cls, factory);
	}
	
	public XLSTable(InputStream is, Class<V> cls, TableFactory<V> factory) throws Exception
	{
		init(is, cls, factory);
	}
	
	public XLSTable(byte[] data, Class<V> cls, TableFactory<V> factory) throws Exception
	{
		init(new ByteArrayInputStream(data), cls, factory);
	}
	
	public XLSTable(byte[] data, Class<V> cls) throws Exception
	{
		init(new ByteArrayInputStream(data), cls, new ObjectClassFactory<V>(cls));
	}
	
	final protected void init(InputStream is, Class<V> cls, TableFactory<V> factory) throws Exception
	{
	    System.out.println("Init XLSTable : table class : " + cls.getSimpleName());
	    
		SQLColumn[]	sql_columns		= SQLTableManager.getSQLColumns(cls);
		Workbook	work_book		= Workbook.getWorkbook(is);
		try {
	
			for (Sheet rs : work_book.getSheets()) 
			{
			    int			row_start		= 1;
			    int			column_start	= 1;
			    int			row_count		= rs.getRows();
			    int			column_count	= rs.getColumns();
			    
				for (int r = row_start+1; r < row_count; r++)
				{
					try
					{
						HashMap<String, String> row = new HashMap<String, String>(column_count);
						
						String primary_key = rs.getCell(column_start, r).getContents().trim();
						if (primary_key.length()<=0) {
							 System.out.println("\ttable eof at row " + r + " sheet " + rs.getName());
							break;
						}
						
						for (int c = column_start; c < column_count; c++) {
							String k = rs.getCell(c, row_start).getContents().trim();
							String v = rs.getCell(c, r).getContents().trim();
							row.put(k, v);
						}
						
						V instance = factory.createInstance();
						for (SQLColumn sql_column : sql_columns) 
						{
							if (row.containsKey(sql_column.getName())) 
							{
								String 		text	= row.get(sql_column.getName());
								
								Class<?> 	type 	= sql_column.getLeafField().getType();
									
								if (SQLStructCLOB.class.isAssignableFrom(type)) 
								{
									sql_column.setObject(instance, text);
								} 
								else 
								{
									Object 		value = Parser.stringToObject(text, type);
									if ((value == null) && (type == Timestamp.class) ) {
										value = Timestamp.valueOf(text);
									}
									if (value != null) {
										sql_column.setObject(instance, value);
	//									System.out.println(sql_column.name + "=" + value);
									}
									else {
										throw new NullPointerException(
													"format error at" +
													" column [" + sql_column.getName() + " = \""+ text +"\"]" +
													" row [" + r + "]" +
													" sheet [" + rs.getName()+"]");
									}
								}
							}
						}
						values.add(instance);
					}
					catch (Exception e) {
						System.err.println("read error at row [" + r + "] sheet [" + rs.getName()+"]");
						throw e;
					}
				}
			
			}
		} finally {
			work_book.close();
		}

	}
	
	public ArrayList<V> getValues() 
	{
		return values;
	}
	
	
	
	
	public static interface TableFactory<V extends SQLTableRow<?>> {
		public V createInstance();
	}
	
	public static class ObjectClassFactory<V extends SQLTableRow<?>> implements TableFactory<V> 
	{
		final public Class<V> type;
		
		public ObjectClassFactory(Class<V> type) {
			this.type = type;
		}
		
		@Override
		public V createInstance() {
			try {
				return type.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}


