package com.cell.sql;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;

import com.cell.sql.anno.SQLTable;

public interface SQLTypeComparer
{
	/**
	 * 检查该驱动下 jdbc_type 是否和 SQLType 兼容
	 * @param type
	 * @param jdbcType ResultSetMetaData.getColumnType()
	 * @return
	 * @see ResultSetMetaData
	 */
	public boolean typeEquals(SQLType type, int jdbc_type);
	
	/**
	 * 得到该驱动下，对应的类型字符
	 * @param type
	 * @return
	 */
	public String getDirverTypeString(SQLType type);
	
	/**
	 * 该驱动下Java类型到SQL类型的映射
	 * @param type
	 * @param java_object
	 * @return
	 * @throws Exception
	 */
	public Object toSQLObject(SQLType type, Class<?> type_clazz, Object java_object) throws Exception;
	
	/**
	 * 该驱动下SQL类型到Java类型的映射
	 * @param type
	 * @param sql_object
	 * @return
	 * @throws Exception
	 */
	public Object toJavaObject(SQLType type, Class<?> type_clazz, Object sql_object) throws Exception;
	
	
	/**
	 * 获得XML对象序列化驱动
	 * @param writer
	 * @return
	 */
	public ObjectOutputStream	getXMLOutputStream(Writer writer) throws IOException;
	
	
	/**
	 * 获得XML对象序列化驱动
	 * @param reader
	 * @return
	 */
	public ObjectInputStream	getXMLInputStream(Reader reader) throws IOException;
	
//	/**
//	 * 表结构认证
//	 * @param table_class
//	 * @param table_type
//	 * @param table_name
//	 * @param table_columns
//	 * @return
//	 */
//	public void validateTable(
//			Class<?> table_class, 
//			SQLTable table_type,
//			String table_name,
//			SQLColumn[] table_columns) throws SQLException;
	
	
	public String createInsertSQL(String table_name, SQLColumn[] columns);
	
	public String createUpdateSQL(String table_name, SQLColumn[] columns, String primary_key_name, Object primary_key_value);
	
	public String createDeleteSQL(String table_name, String primary_key_name, Object primary_key_value);
	
	public String createSelectRowSQL(String table_name, String field_name, Object field_value);
	
	public String createSelectAllLimitSQL(String table_name, int startIndex, int count);
	
//	----------------------------------------------------------------------------------------------------------------------

	public static enum ValidateResult
	{
		OK,
		ERROR,
		ERROR_NOT_EXIST,
		ERROR_LENGTH,
		ERROR_NAME,
		ERROR_TYPE,
		ERROR_DRIVER_LIMITED,
	}
	
	public boolean validateTable(Connection conn, SQLColumnAdapter<?, ?> table,
			boolean auto_create_struct,
			boolean sort_field,
			boolean create_comment) throws SQLException;

	/**
	 * 获得该类型对应SQL的创建语句
	 * @param table
	 * @param sort_fields		是否对列进行默认排序
	 * @param create_comment	是否产生注释信息
	 * @return 
	 * @throws SQLException
	 */
	public String getCreateTableSQL(
			SQLColumnAdapter<?, ?> table,
			boolean sort_fields, 
			boolean create_comment);
}
