package com.cell.sql;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;




public class CustomRow implements Serializable
{		
	private static final long serialVersionUID = 1L;
	

	private final Object[] values_;


	public CustomRow(ResultSet rs, final String[] column_names) throws SQLException 
	{
		values_ = new Object[column_names.length];

		for ( int i=1; i<=column_names.length; ++i )
		{
			values_[i-1] = rs.getObject(i);
		}
	}

	/**
	 * 获得每一列的值的java数组，以sql结果集中的顺序，注意，java数组的序号从0开始
	 * @return
	 */
	public Object[] getValues()
	{
		return this.values_;
	}

	/**
	 * 获取SQL结果集的列值，根据JDBC规范，序号从1开始
	 * @param index 从1开始的序号
	 * @return
	 */		
	public Object getValue(int index)
	{
		if ( (index <= 0) || (index > values_.length) )
			return null;

		return values_[index-1];		
	}
}


