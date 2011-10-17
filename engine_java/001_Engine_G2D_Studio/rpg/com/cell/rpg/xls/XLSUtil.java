package com.cell.rpg.xls;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;

import com.cell.reflect.Parser;
import com.cell.sql.SQLColumn;
import com.cell.sql.SQLStructCLOB;
import com.cell.sql.SQLTableManager;
import com.cell.sql.SQLTableRow;

public class XLSUtil {

	public static <K, V extends SQLTableRow<?>> Map<K, V> readTable(
			Workbook work_book, 
			String sheet,
		    int	row_start,
		    int	column_start,
		    int primary_column,
			SQLColumn[] sql_columns, 
			Class<K> ktype, 
			Class<V> vtype, 
			XLSRowFactory<V> factory) throws Exception
	{
		LinkedHashMap<K, V> ret = new LinkedHashMap<K, V>();

//		for (Sheet rs : work_book.getSheets()) 
		{
			Sheet rs = work_book.getSheet(sheet);

			int row_count = rs.getRows();
			int column_count = rs.getColumns();
		    
			for (int r = row_start + 1; r < row_count; r++)
			{
				try
				{
					String primary_key = rs.getCell(primary_column, r).getContents().trim();
					if (primary_key.length()<=0) {
						System.out.println("\ttable eof at row " + r + " sheet " + rs.getName());
						break;
					}

					HashMap<String, String> row = new HashMap<String, String>(column_count);
					for (int c = column_start; c < column_count; c++) {
						String k = rs.getCell(c, row_start).getContents().trim();
						String v = rs.getCell(c, r).getContents().trim();
						row.put(k, v);
					}
					
					V instance = factory.createInstance();
					
					for (SQLColumn sql_column : sql_columns) 
					{
						String text = row.get(sql_column.getName());
						if (text == null)
						{
							System.out.println(rs.getName() + " - column[" + sql_column.getName() + "] not found in xls");
						}
						else
						{
							Class<?> type = sql_column.getLeafField().getType();
							
							String conv = factory.convertField(rs, sql_column, text);
							
							if (conv != null) {
								text = conv;
							}
							try {
								if (SQLStructCLOB.class.isAssignableFrom(type)) 
								{
									sql_column.setObject(instance, text);
								} 
								else 
								{
									Object value = Parser.stringToObject(text, type);
									if ((value == null) && (type == Timestamp.class) ) {
										value = Timestamp.valueOf(text);
									}
//									else if (type.isPrimitive() && !type.equals(boolean.class)) {
//										sql_column.setObject(instance, Parser.castNumber(value, type));
//									}
									else if (value != null) {
										sql_column.setObject(instance, value);
//										System.out.println(sql_column.getName() + "=" + value);
									}
									else {
										throw new NullPointerException(
													"format error at" +
													" column [" + sql_column.getName() + " = \""+ row.get(sql_column.getName()) +"\"]" +
													" row [" + r + "]" +
													" sheet [" + rs.getName()+"]");
									}
								}
							} catch (Exception e) {
								System.err.println(
										"error at" +
										" column [" + sql_column.getName() + " = \""+ row.get(sql_column.getName()) +"\"]" +
										" row [" + r + "]" +
										" sheet [" + rs.getName()+"]");
								throw e;
							}
						}
					}
					ret.put(Parser.cast(instance.getPrimaryKey(), ktype), instance);
//					System.out.println(instance.getPrimaryKey() + " - " + instance);
				}
				catch (Exception e) {
					System.err.println("read error at row [" + r + "] sheet [" + rs.getName()+"]");
					throw e;
				}
			}
		}
		
		return ret;
	}
	
	public static Map<String, String> readMap(
			Workbook work_book,
			String sheet, 
			int rowStart,
			int columK, int columV) throws Exception
 {
		Map<String, String> ret = new LinkedHashMap<String, String>();
		Sheet rs = work_book.getSheet(sheet);
		int row_start = rowStart;
		int row_count = rs.getRows();
		for (int r = row_start; r < row_count; r++) {
			String key = rs.getCell(columK, r).getContents().trim();
			String value = rs.getCell(columV, r).getContents().trim();
			if (!key.isEmpty()) {
				ret.put(key, value);
			}
		}
		return ret;
	}
}
