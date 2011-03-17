package com.cell.rpg.xls;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;

import jxl.Sheet;
import jxl.Workbook;

import com.cell.CIO;

public class XLSColumns extends LinkedHashMap<String, String> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public XLSColumns() {}
	
	public Collection<String> getColumns() {
		return super.keySet();
	}
	
	public String getDesc(String column_name) {
		String c = super.get(column_name);
		return c;
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	
	/**
	 * 返回以 xls 的 row[c0][c1] 的集合
	 * @param xls_file
	 * @return
	 */
	public static XLSColumns getXLSColumns(String path)
	{
		InputStream is = CIO.loadStream(path);
		return getXLSColumns(is);
	}
	
	/**
	 * 返回以 xls 的 row[c0][c1] 的集合
	 * @param xls_file
	 * @return
	 */
	public static XLSColumns getXLSColumns(InputStream is)
	{
		try {
			Workbook	rwb		= Workbook.getWorkbook(is);
			try {
				Sheet 		rs 		= rwb.getSheet(0);
				int column_count 	= rs.getColumns();
				XLSColumns ret = new XLSColumns();
				for (int c = 1; c < column_count; c++) {
					String cdesc	= rs.getCell(c, 0).getContents();
					String cname	= rs.getCell(c, 1).getContents();
					ret.put(cname, cdesc);
				}
				return ret;
			} finally {
				rwb.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
