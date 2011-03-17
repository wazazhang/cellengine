package com.cell.sql;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;





public class CustomColumnSet implements Serializable
{
	private static final long serialVersionUID = 1L;


	private final HashMap<String, Integer> column_index_map_ = new HashMap<String, Integer>();

	private final String[] columns_;

	
	public CustomColumnSet(ResultSet rs) throws SQLException
	{
		java.sql.ResultSetMetaData rsmd = rs.getMetaData();

		int column_count = rsmd.getColumnCount();
		columns_ = new String[column_count];

		for ( int i=1; i<=column_count; ++i )
		{
			String col_name = rsmd.getColumnName(i);
			column_index_map_.put(col_name, i);
			columns_[i-1] = col_name;
		}
	}

	/**
	 * 获取SQL结果集的列名，根据JDBC规范，序号从1开始
	 * @param index 从1开始的序号
	 * @return
	 */
	public String getColumnName(int index)
	{
		if ( (index <= 0) || (index > columns_.length) )
			return null;

		return columns_[index-1];
	}

	/**
	 * 获取SQL结果集中列名对应的列序号，根据JDBC规范，序号从1开始
	 * @param col_name 列名
	 * @return
	 */
	public int getColumnIndex(String col_name)
	{
		Integer obj = column_index_map_.get(col_name);
		if (obj != null)
			return obj;

		return -1;
	}

	/**
	 * 获取列名集合的java数组，注意，java数组的序号从0开始
	 * @return
	 */
	public String[] getColumns()
	{
		return this.columns_;
	}
}





