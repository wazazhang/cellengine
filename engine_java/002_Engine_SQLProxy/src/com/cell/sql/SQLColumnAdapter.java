package com.cell.sql;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CUtil;
import com.cell.CUtil.ICompare;
import com.cell.CUtil.StringCompare;
import com.cell.reflect.Fields;
import com.cell.sql.SQLTypeComparer.ValidateResult;
import com.cell.sql.anno.SQLField;
import com.cell.sql.anno.SQLGroupField;
import com.cell.sql.anno.SQLTable;



/**
 * 数据库表和实体的关系
 * @author WAZA
 * @param <K> PrimaryKey 类型
 * @param <R> Row 类型
 */
public abstract class SQLColumnAdapter<K, R extends SQLTableRow<K>>
{
	final public Logger log;
	
//	---------------------------------------------------------------------------------------------------------------------------------------------------------
	
	final public	Class<R> 		table_class;
	final public	SQLTable		table_type;
	final public	String			table_name;
	final public	SQLColumn[]		table_columns;
	final public	Field			table_key_field;
	final private 	HashMap<String, SQLColumn>	table_columns_map;
	
//	---------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public SQLColumnAdapter(Class<R> cls, String table_name) throws Exception
	{
		this.log = LoggerFactory.getLogger("DB["+table_name+"]");
		this.table_class		= cls;
		this.table_type			= cls.getAnnotation(SQLTable.class);
		this.table_name			= table_name;
		this.table_columns		= SQLColumn.getSQLColumns(cls);		
		this.table_columns_map	= new HashMap<String, SQLColumn>();
		this.table_key_field	= cls.getField(table_type.primary_key_name());
		for (SQLColumn c : table_columns) {
			table_columns_map.put(c.getName(), c);
		}
	}
	
	/**
	 * 创建行实体
	 * @return
	 * @throws Exception
	 */
	abstract public R newRow() throws Exception ;

//	---------------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	final public SQLColumn[] getColumns(String ... columns_name) throws SQLException
	{
		SQLColumn[] columns = new SQLColumn[columns_name.length];
		for (int i=columns.length-1; i>=0; --i) {
			columns[i] = getColumn(columns_name[i]);
			if (columns[i] == null) {
				throw new SQLException("Can not find column by name : " + columns_name[i]);
			}
		}
		return columns;
	}
	
	final public SQLColumn getColumn(String column_name)
	{
		return table_columns_map.get(column_name);
	}
	
	final public SQLColumn getColumn(Field field)
	{
		for (SQLColumn c : table_columns) {
			if (c.getLeafField().equals(field)) {
				return c;
			}
		}
		return null;
	}

//	---------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 从结果集中读入数据
	 * @param result
	 * @throws SQLException
	 */
	final public R fromResult(R row, ResultSet result) throws Exception
	{
		for (int i = 0; i < table_columns.length; i++)
		{
			try {
				Object obj = result.getObject(table_columns[i].getName());
				table_columns[i].fromSqlData(row, obj);		
			} catch (Exception err) {
				log.error("[" + table_name + "] read column error !\n" +
						"\t    id = " + row.getPrimaryKey() +
						"\t cause = " + err.getMessage() +
						"", err);
			}
		}
		return row;
	}
	
	/**
	 * 从结果集中读入数据
	 * @param result
	 * @throws SQLException
	 */
	final public R fromResultByName(R row, ResultSet result) throws Exception
	{
		ResultSetMetaData meta = result.getMetaData();
		for (int i = meta.getColumnCount(); i >= 1; --i)
		{
			String name = meta.getColumnName(i);
			SQLColumn column = table_columns_map.get(name);
			try {
				Object obj = null;
				try {
					obj = result.getObject(name);
				} catch (Exception err) {}
				if (obj != null) {
					column.fromSqlData(row, obj);
				}
			} catch (Exception err) {
				log.error("[" + table_name + "] read column error !\n" +
						"\t    id = " + row.getPrimaryKey() +
						"\t cause = " + err.getMessage() +
						"", err);
			}
		}
		return row;
	}
	
	/**
	 * 执行插入指令，将一个新行插入到DB。
	 * @param conn
	 * @throws Exception
	 */
	final public int insertWithDB(R row, Connection conn) throws Exception
	{
		return insertWithDB(row, conn, table_columns);
	}
	
	/**
	 * 执行插入指令，将一个新行插入到DB。
	 * @param conn
	 * @throws Exception
	 */
	final public int insertWithDB(R row, Connection conn, SQLColumn[] columns) throws Exception
	{
		PreparedStatement statement = conn.prepareStatement(
				SQMTypeManager.getTypeComparer().createInsertSQL(table_name, columns));
		try {
			for (int i=0; i<columns.length; i++){
				SQLColumn c = columns[i];
				statement.setObject(i+1, c.toSqlData(row), c.getAnno().type().getJdbcType());
			}
			return statement.executeUpdate();
		} finally {
			statement.close();
		}
	}

	
	
	/**
	 * 执行更新指令，将更新指定的行。
	 * @param conn
	 * @throws Exception
	 */
	final public int updateWithDB(R row, Connection conn) throws Exception
	{
		return updateWithDB(row, conn, table_columns);
	}
	
	/**
	 * 执行更新指令，将更新指定的行。
	 * @param conn
	 * @throws Exception
	 */
	final public int updateWithDB(R row, Connection conn, SQLColumn[] columns) throws Exception
	{
		PreparedStatement statement = conn.prepareStatement(
				SQMTypeManager.getTypeComparer().createUpdateSQL(
						table_name, 
						columns, 
						table_type.primary_key_name(),
						row.getPrimaryKey()));
		try{
			for (int i=0; i<columns.length; i++){
				SQLColumn c = columns[i];
				statement.setObject(i+1, 
						c.toSqlData(row), 
						c.getAnno().type().getJdbcType());
			}
			return statement.executeUpdate();
		}finally{
			statement.close();
		}
	}
	
	/**
	 * 删除oid对应的列。
	 * @param conn
	 * @throws Exception
	 */
	final public int deleteWithDB(K primary_key, Connection conn) throws Exception
	{
		
		Statement statement = conn.createStatement();
		try{
			return statement.executeUpdate(
					SQMTypeManager.getTypeComparer().createDeleteSQL(
							table_name, 
							table_type.primary_key_name(),
							primary_key));
		}finally{
			statement.close();
		}
	}
	
//	final public CustomResultSet query(Connection conn, String str_query) throws Exception {
//		Statement statement = conn.createStatement();
//		try {
//			ResultSet rs = statement.executeQuery(str_query);
//			try {
//				return new CustomResultSet(rs);
//			} finally {
//				rs.close();
//			}
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			statement.close();
//		}
//	}

	
	/**
	 * 查询所有字段
	 * @param conn
	 * @param str_query
	 * @return
	 * @throws Exception
	 */
	final public ArrayList<R> queryRows(Connection conn, String str_query) throws Exception
	{
		Statement statement = conn.createStatement();
		try {
			ArrayList<R> ret = new ArrayList<R>();
			ResultSet rs = statement.executeQuery(str_query);
			try {
				while (rs.next()) {
					ret.add(fromResult(newRow(), rs));
				}
			} finally {
				rs.close();
			}
			return ret;
		} catch (Exception e) {
			throw e;
		} finally {
			statement.close();
		}
	}
	
	final public R select(K primary_key, Connection conn) throws Exception
	{
		return select(table_type.primary_key_name(), primary_key.toString(), conn);
	}
	
	final public R select(String field_name, String field_value, Connection conn) throws Exception
	{
		R row = newRow();
		
		Statement statement = conn.createStatement();
		try {
			ResultSet result = statement.executeQuery(SQMTypeManager.getTypeComparer().createSelectRowSQL(
					table_name, field_name, field_value));
			try {
				if (result.next()) {
					return fromResult(row, result);
				}
			} finally {
				result.close();
			}
		} catch (Exception e) {
			throw e;
		} finally{
			statement.close();
		}

		return null;
	}

	final public ArrayList<R> select(Connection conn, String sql) throws Exception
	{
		ArrayList<R> 	ret 		= new ArrayList<R>();
		Statement		statement 	= conn.createStatement();
		ResultSet 		result 		= statement.executeQuery(sql);
		try {
			while (result.next()) {
				try {
					ret.add(fromResultByName(newRow(), result));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				result.close();
			} catch (Exception err) {}
			try {
				statement.close();
			} catch (Exception err) {}
		}
		return ret;
		
	}

	final public ArrayList<R> selectRows(Connection conn, String sql) throws Exception
	{
		ArrayList<R> 	ret 		= new ArrayList<R>();
		Statement		statement 	= conn.createStatement();
		ResultSet 		result 		= statement.executeQuery(sql);
		try {
			while (result.next()) {
				try {
					ret.add(fromResult(newRow(), result));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				result.close();
			} catch (Exception err) {}
			try {
				statement.close();
			} catch (Exception err) {}
		}
		return ret;
		
	}
	
//	/**
//	 * 从表中读出所有对象
//	 * @param <T>
//	 * @param tableClass
//	 * @param statement
//	 * @return
//	 * @throws SQLException
//	 */
//	final public ArrayList<R> selectAll(Connection conn) throws Exception
//	{
//		return selectFully(conn, "SELECT * FROM " + table_name + " ;");
//	}
	
	/**
	 * 分块读取，要保证外部对该Iterator原子操作
	 * @param conn
	 * @param block_size
	 * @return
	 */
	final public Iterator<R> selectAll(Connection conn, int block_size)
	{
		return new SelectIterator(conn, block_size);
	}
	
	private class SelectIterator implements Iterator<R>
	{
		final private Connection	conn;
		final private int 			block_size;
		final private LinkedList<R>	readed = new LinkedList<R>();
		
		private int 				index = 0;
		
		public SelectIterator(Connection conn, int block_size) {
			this.block_size = block_size;
			this.conn		= conn;
			request();
		}
		
		@Override
		public boolean hasNext() {
			return !readed.isEmpty();
		}
		
		@Override
		public R next() {
			R ret = readed.remove();
			if (readed.isEmpty()) {
				request();
			}
			return ret;
		}
		
		synchronized private void request()
		{
			String sql = SQMTypeManager.getTypeComparer().createSelectAllLimitSQL(table_name, index, block_size);
			try {
				ArrayList<R> rows = selectRows(conn, sql);
				if (rows != null && !rows.isEmpty()) {
					index += rows.size();
					readed.addAll(rows);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	

//	---------------------------------------------------------------------------------------------------------------------------------------------------------
//	public static interface ReplaceListener<R>
//	{
//		public void replace(R row);
//	}
//	
//	public void replaceAll(Connection conn, int block_size, ReplaceListener<R> listener) throws Exception 
//	{
//		log.info("replacing [" + table_name + "] ...");
//		long ttime = System.currentTimeMillis();
//		int count = 0;
//		for (Iterator<R> it = selectAll(conn, block_size); it.hasNext(); ) {
//			long btime = System.currentTimeMillis();
//			R row = it.next();
//			count ++;
//			if (count % block_size == 0) {
//				log.info("replacing [" + table_name + "]" +
//						" : block size = " + count+
//						" : last id = " + row.getPrimaryKey() +
//						" : use time = " + (System.currentTimeMillis() - btime) + "(ms)");
//			}
//			listener.replace(row);
//		}
//		log.info("replaced  [" + table_name + "]" +
//				" : total size = " + count+ 
//				" : use time = " + (System.currentTimeMillis() - ttime) + "(ms)");
//	}

	
	
}
