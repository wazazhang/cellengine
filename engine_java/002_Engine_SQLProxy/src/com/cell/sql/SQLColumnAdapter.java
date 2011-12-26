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
	final static protected Logger	log = LoggerFactory.getLogger("DB");
//	---------------------------------------------------------------------------------------------------------------------------------------------------------
	final public	Class<R> 		table_class;
	final public	SQLTable		table_type;
	final public	String			table_name;
	final public	SQLColumn[]		table_columns;
	final private 	HashMap<String, SQLColumn>	table_columns_map;

//	---------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public SQLColumnAdapter(Class<R> cls, String table_name)
	{
		this.table_class	= cls;
		this.table_type		= cls.getAnnotation(SQLTable.class);
		this.table_name		= table_name;
		this.table_columns	= getSQLColumns(cls);
		this.table_columns_map = new HashMap<String, SQLColumn>();
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
				// NOTE 为什么有些地方就不能用序号获取result.getObject??
				
				int index = table_columns[i].getIndex();
				Object obj = result.getObject(index);
				table_columns[i].fromSqlData(row, obj);		
//				table_columns[i].setObject(row, result.getObject(table_columns[i].getIndex()));				
//				String name = table_columns[i].getName();
//				Object obj = result.getObject(name);
//				table_columns[i].setObject(row, obj);
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
		for (int i = 0; i < table_columns.length; i++)
		{
			try {
				String name = table_columns[i].getName();
				Object obj = null;
				try {
					obj = result.getObject(name);
				} catch (Exception err) {}
				if (obj != null) {
					table_columns[i].fromSqlData(row, obj);
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
		StringBuffer sb = new StringBuffer("INSERT INTO ");
		sb.append(table_name);
		sb.append("(\n");
		
		for (int i=0; i<columns.length; i++){
			SQLColumn c = columns[i];
			sb.append("\t"); 
			sb.append(c.getName());
			sb.append(getSplitChar(i, columns.length));
		}
		sb.append(") VALUES (\n");
		for (int i=0; i<columns.length; i++){
			sb.append("\t?"); 
			sb.append(getSplitChar(i, columns.length));
		}
		sb.append(");");

		PreparedStatement statement = conn.prepareStatement(sb.toString());
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
		StringBuffer sb = new StringBuffer("UPDATE ");
		sb.append(table_name);
		sb.append(" SET\n");
		
		for (int i=0; i<columns.length; i++){
			SQLColumn c = columns[i];
			sb.append("\t"); 
			sb.append(c.getName()); 
			sb.append("=?"); 
			sb.append(getSplitChar(i, columns.length));
		}
		
		sb.append("WHERE "); 
		sb.append(table_type.primary_key_name()); 
		sb.append("='");
		sb.append(row.getPrimaryKey()); 
		sb.append("';");
		
		PreparedStatement statement = conn.prepareStatement(sb.toString());
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
		StringBuffer sb = new StringBuffer("DELETE FROM ");
		sb.append(table_name);
		sb.append(" WHERE ");
		sb.append(table_type.primary_key_name()); 
		sb.append("='"); 
		sb.append(primary_key);
		sb.append("';");
		
		Statement statement = conn.createStatement();
		try{
			return statement.executeUpdate(sb.toString());
		}finally{
			statement.close();
		}
	}
	
	final public CustomResultSet query(Connection conn, String str_query) throws Exception {
		Statement statement = conn.createStatement();
		try {
			ResultSet rs = statement.executeQuery(str_query);
			try {
				return new CustomResultSet(rs);
			} finally {
				rs.close();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			statement.close();
		}
	}

	
	/**
	 * 查询所有字段
	 * @param conn
	 * @param str_query
	 * @return
	 * @throws Exception
	 */
	final public ArrayList<R> queryFully(Connection conn, String str_query) throws Exception
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
			StringBuffer sb = new StringBuffer("SELECT * FROM ");
			sb.append(table_name);
			sb.append(" WHERE ");
			sb.append(field_name);
			sb.append("='");
			sb.append(field_value);
			sb.append("';");
			
			ResultSet result = statement.executeQuery(sb.toString());
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

	final public ArrayList<R> selectFully(Connection conn, String sql) throws Exception
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
	
	/**
	 * 从表中读出所有对象
	 * @param <T>
	 * @param tableClass
	 * @param statement
	 * @return
	 * @throws SQLException
	 */
	final public ArrayList<R> selectAll(Connection conn) throws Exception
	{
		return selectFully(conn, "SELECT * FROM " + table_name + " ;");
	}
	
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
			String sql = "SELECT * FROM " + table_name + " LIMIT " + index + "," + block_size + ";";
			try {
				ArrayList<R> rows = selectFully(conn, sql);
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
	public static interface ReplaceListener<R>
	{
		public void replace(R row);
	}
	
	public void replaceAll(Connection conn, int block_size, ReplaceListener<R> listener) throws Exception 
	{
		log.info("replacing [" + table_name + "] ...");
		long ttime = System.currentTimeMillis();
		int count = 0;
		for (Iterator<R> it = selectAll(conn, block_size); it.hasNext(); ) {
			long btime = System.currentTimeMillis();
			R row = it.next();
			count ++;
			if (count % block_size == 0) {
				log.info("replacing [" + table_name + "]" +
						" : block size = " + count+
						" : last id = " + row.getPrimaryKey() +
						" : use time = " + (System.currentTimeMillis() - btime) + "(ms)");
			}
			listener.replace(row);
		}
		log.info("replaced  [" + table_name + "]" +
				" : total size = " + count+ 
				" : use time = " + (System.currentTimeMillis() - ttime) + "(ms)");
	}

	
	
//	---------------------------------------------------------------------------------------------------------------------------------------------------------
	
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
	
	final public boolean validateTable(Connection conn,
			boolean auto_create_struct,
			boolean sort_field,
			boolean create_comment) throws SQLException
	{
		// 验证表结构
		log.info("validating table struct [" + table_name + "] ...");
		
		ValidateResult result = validateTable(conn);
		
		if (result != ValidateResult.OK)
		{
			log.warn("validate error [" + table_name + "] : " + result);
			
			if (result == ValidateResult.ERROR_NOT_EXIST && auto_create_struct)
			{
				log.info("creating table struct [" + table_name + "] ...");
				
				Statement statement = conn.createStatement();
				
				String create = getCreateTableSQL(sort_field, create_comment);
				System.out.println(create);
				statement.execute(create);
				statement.close();
				
				result = validateTable(conn);
			}
			else
			{
				throw new SQLException("table struct [" + table_name + "] not validate !");
			}
		}

		return result == ValidateResult.OK;
	}
	
	/**
	 * 检查指定数据库里的表结构是否符合java的结构
	 * @param tableClass
	 * @param statement
	 * @return
	 */
	final public ValidateResult validateTable(Connection conn) throws SQLException
	{
		try {
			SQMTypeManager.getTypeComparer().validateTable(
					table_class,
					table_type, 
					table_name, 
					table_columns);
		} catch (SQLException err) {
			log.error(err.getMessage(), err);
			return ValidateResult.ERROR_DRIVER_LIMITED;
		}
		
		ValidateResult vresult = validateAndAutoFix(conn);
		
		if (vresult == ValidateResult.OK)
		{
			String sql = "SELECT * FROM " + table_name + " LIMIT 0;";
			
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			ResultSetMetaData metadata = result.getMetaData();
			result.close();
			statement.close();
			
			// last validate
			for (SQLColumn c : table_columns)
			{
				int rc = c.getIndex();
				
				String rname = metadata.getColumnName(rc);
				String tname = c.getName();
				
				// 检测名字是否正确
				if (!rname.equalsIgnoreCase(tname)) 
				{
					System.err.println(
							"Table '" + table_name + "' " +
							"field name ["+rname+","+tname+"] not equal! " +
							"at column " + rc 
							);
					
					return ValidateResult.ERROR_NAME;
				}
				
				// 检查类型是否正确
				if (!c.getAnno().type().typeEquals(metadata.getColumnType(rc))) 
				{
					String	rtypename	= metadata.getColumnTypeName(rc);
					int		rtype		= metadata.getColumnType(rc);
					String	ttypename	= c.getAnno().type().getDirverTypeName();
					int		ttype		= c.getAnno().type().getJdbcType();
					
					System.err.println(
							"Table '" + table_name + "' " +
							"field type ["+rtypename+"("+rtype+")"+","+ttypename+"("+ttype+")"+"] not equal! " +
							"at column " + rc 
							);
					
					return ValidateResult.ERROR_TYPE;
				}
			
				
				
			}
			
			return ValidateResult.OK;
		}
		
		return vresult;
	}
	
	final ValidateResult validateAndAutoFix(Connection conn) throws SQLException
	{
		String sql = "SELECT * FROM " + table_name + " LIMIT 0;";

		Statement statement = conn.createStatement();
		
		while(true)
		{
			ResultSet result = null;
			
			try{
				result = statement.executeQuery(sql);
			}catch (Exception e) {
				return ValidateResult.ERROR_NOT_EXIST;
			}
			
			ResultSetMetaData metadata = result.getMetaData();
			result.close();
			
			ArrayList<SQLColumn> not_exists = new ArrayList<SQLColumn>();
			
			for (SQLColumn c : table_columns)
			{
				int rc = indexOfSQLColumn(metadata, c);
				
				if (rc > 0) {
					c.setIndex(rc);
				}
				else if (rc == -1) {
					not_exists.add(c);
				} 
				else if (rc == -2)
				{
					String	rtypename	= metadata.getColumnTypeName(c.getIndex());
					int		rtype		= metadata.getColumnType(c.getIndex());
					String	ttypename	= c.getAnno().type().getDirverTypeName();
					int		ttype		= c.getAnno().type().getJdbcType();
					
					String reson = (
							"Table '" + table_name + "' " +
							"field : " + c.getName() + " : type ["+rtypename+"("+rtype+")"+","+ttypename+"("+ttype+")"+"] not equal! " +
							"at column " + c.getIndex() 
							);

					throw new SQLException(reson);
				}
			}
			
			if (!not_exists.isEmpty())
			{
				for (SQLColumn c : not_exists)
				{
					String add_sql = getAlterTableAddColumnSQL(c);
					System.out.println(add_sql);
					statement.executeUpdate(add_sql);
				}
			}
			else
			{
				break;
			}
		}
		statement.close();
		
		CUtil.sort(table_columns, table_columns[0]);
		
		return ValidateResult.OK;
		
	}


	public void syncTableColumns(Connection conn) throws SQLException
	{
		ValidateResult vresult = validateTable(conn);
		
		if (vresult == ValidateResult.OK)
		{
			Statement statement = conn.createStatement();
			
			String sql = "SELECT * FROM " + table_name + " LIMIT 0;";
			try {
				ResultSet 			result			= statement.executeQuery(sql);
				ResultSetMetaData	metadata		= result.getMetaData();
				result.close();
				for (SQLColumn c : table_columns) {
					String rname = metadata.getColumnTypeName(c.getIndex()).toLowerCase();
					String tname = c.getAnno().type().getDirverTypeName().toLowerCase();
					if (rname.equals(tname)) {
						String change_sql = getAlterTableChangeColumnSQL(c);
						System.out.println(change_sql);
						statement.executeUpdate(change_sql);
					}
				}
			} finally {
				statement.close();
			}
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------

	final private static String getSplitChar(int i, int len) {
		return ((i != len - 1) ? ",\n" : "\n");
	}
	
	/**
	 * @param metadata
	 * @param c
	 * @return 
	 * >=1 : ok<br>
	 * -1 : not exist<br>
	 * -2 : type not equal<br>
	 * @throws SQLException
	 */
	static int indexOfSQLColumn(ResultSetMetaData metadata, SQLColumn c) throws SQLException
	{
		String	tname	= c.getName();
		SQLType	ttype	= c.getAnno().type();
		
		for (int rc = 1; rc <= metadata.getColumnCount(); rc++) {
			// 检测名字是否正确
			if (tname.equalsIgnoreCase(metadata.getColumnName(rc))) {
				c.setIndex(rc);
				// 检查字段类型是否正确
				if (ttype.typeEquals(metadata.getColumnType(rc))) {
					return rc;
				}else{
					return -2;
				}
			}
		}
		return -1;
	}
	
	
//	---------------------------------------------------------------------------------------------------------------------------------
//	alter table 'name' change 'columna' 'columnb' longblob
//
//	---------------------------------------------------------------------------------------------------------------------------------

	static public SQLColumn[] getSQLColumns(Class<? extends SQLFieldGroup> tableClass)
	{
		SQLColumn[] columns = null;
		
		try 
		{
			ArrayList<SQLColumn>	full_columns_list 	= new ArrayList<SQLColumn>();
			Stack<Field> 			fields_stack		= new Stack<Field>();

			getSQLColumns(
					tableClass, 
					full_columns_list, 
					fields_stack);
			
			columns = full_columns_list.toArray(new SQLColumn[full_columns_list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return columns;
	}

	/**
	 * 递归该类（包括子字段），找到所有SQLField字段
	 * @param gclass
	 * @param full_columns_list
	 * @param fields_stack
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	static void getSQLColumns(
			Class<?>				gclass,
			ArrayList<SQLColumn> 	full_columns_list, 
			Stack<Field> 			fields_stack
			) throws IllegalArgumentException, IllegalAccessException
	{
		ArrayList<Field> fields = Fields.getDeclaredAndSuperFields(gclass);
		
		for (Field field : fields)
		{
			fields_stack.push(field);
			
			SQLField sql_field_anno = field.getAnnotation(SQLField.class);
			
			if (sql_field_anno != null)
			{
				full_columns_list.add(new SQLColumn(sql_field_anno, fields_stack));
			}
			else
			{			
				// 得到SQLFieldGroup子复合类型
				SQLGroupField sql_group_field_anno = field.getAnnotation(SQLGroupField.class);
				
				if (sql_group_field_anno != null)
				{
					// 得到SQLFieldGroup子复合类型
					try
					{
						field.getType().asSubclass(SQLFieldGroup.class);
						// 递归
						getSQLColumns(field.getType(), full_columns_list, fields_stack);
					}
					catch (Exception e) 
					{
//						// 得到SQLField字段
//						SQLField anno = field.getAnnotation(SQLField.class);
//						if (anno != null)
//							full_columns_list.add(new SQLColumn(anno, fields_stack));

						// 必须是annotation SQLGroupField的且是SQLFieldGroup子类的才可以继续处理内部表结构
						e.printStackTrace();
					}
				}
			}
			
			fields_stack.pop();
		}
	}
	

//	---------------------------------------------------------------------------------------------------------------------------------
	
	public String getAlterTableChangeColumnSQL(SQLColumn c)
	{
		StringBuilder add_sql = new StringBuilder();
		add_sql.append("ALTER TABLE `" + this.table_name + "` CHANGE ");
		add_sql.append("`" + c.getName() + "` ");
		add_sql.append("`" + c.getName() + "` ");
		add_sql.append(c.getAnno().type() + " ");
		add_sql.append(c.getConstraint());
		String comment = c.getAllComment();
		if (comment != null && comment.length() > 0) {
			add_sql.append(" COMMENT '" + comment + "'");
		}
		add_sql.append(";");
		return add_sql.toString();
	}
	
	public String getAlterTableAddColumnSQL(SQLColumn c) 
	{
		StringBuilder add_sql = new StringBuilder();
		add_sql.append("ALTER TABLE `" + this.table_name + "` ADD COLUMN ");
		add_sql.append("`" + c.getName() + "` ");
		add_sql.append(c.getAnno().type() + " ");
		add_sql.append(c.getConstraint());
		String comment = c.getAllComment();
		if (comment != null && comment.length() > 0) {
			add_sql.append(" COMMENT '" + comment + "'");
		}
		add_sql.append(";");
		return add_sql.toString();
	}

//	--------------------------------------------------------------------------------------------------------
	
	public static class CreateTableColumnSorter implements Comparator<SQLColumn> 
	{
		final SQLTable		table_type;
		final boolean		sort_by_name;
		
		public CreateTableColumnSorter(SQLTable table_type, boolean sort_by_name) {
			this.table_type		= table_type;
			this.sort_by_name	= sort_by_name;
		}
		
		public int compare(SQLColumn a, SQLColumn b)
		{
			if (a.getName().equals(table_type.primary_key_name())) return -1;
			if (b.getName().equals(table_type.primary_key_name())) return 1;
			int priority = b.getAnno().index_priority() - a.getAnno().index_priority();
			if (priority != 0) {
				return priority;
			}
			if (sort_by_name) {
				return a.getName().compareToIgnoreCase(b.getName());
			}
			return 0;
		}
	}
	
	
	/**
	 * 获得该类型对应SQL的创建语句
	 * @param sorter			是否对列进行排序
	 * @param create_comment	是否产生注释信息
	 * @return 
	 * @throws SQLException
	 */
	public String getCreateTableSQL(
			Comparator<SQLColumn> sorter,
			boolean create_comment)
	{
		SQLColumn[] columnss = new SQLColumn[this.table_columns.length];
		System.arraycopy(this.table_columns, 0, columnss, 0, columnss.length);
		Arrays.sort(columnss, sorter);
		
		String sql = "CREATE TABLE `" + this.table_name + "` (\n";
		int name_max_len = 1;
		for (int i = 0; i < columnss.length; i++) {
			name_max_len = Math.max(columnss[i].getName().length()+4, name_max_len);
		}
		
		for (int i = 0; i < columnss.length; i++)
		{
			SQLColumn column = columnss[i];
			
			sql += "\t" + CUtil.snapStringRightSize(
					"`"+ column.getName() + "`", name_max_len, ' ') + 
					" " + column.getAnno().type();
			
				String constraint = column.getConstraint();
				if (constraint != null && constraint.length() > 0) {
					sql += " " + constraint;
				}
				if (create_comment) {
					String comment = column.getAllComment();
					if (comment != null && comment.length() > 0) {
						sql += " COMMENT '" + comment + "'";
					}
				}
				sql += ",\n";
		}
		
		sql += "\tPRIMARY KEY (" + this.table_type.primary_key_name() + ")";
		if (!this.table_type.constraint().trim().isEmpty()) {
			sql += ", " + this.table_type.constraint() + "\n";
		} else {
			sql += "\n";
		}
		sql += ")";
		
		if (!this.table_type.properties().trim().isEmpty()) {
			sql += " " + this.table_type.properties();
		}
		if (create_comment && !this.table_type.comment().trim().isEmpty()) {
			sql += " COMMENT='" + this.table_type.comment() + "'";
		}
		sql += ";";
		return sql;
	
	}
	
	/**
	 * 获得该类型对应SQL的创建语句
	 * @param table
	 * @param sort_fields		是否对列进行默认排序
	 * @param create_comment	是否产生注释信息
	 * @return 
	 * @throws SQLException
	 */
	public String getCreateTableSQL(
			boolean sort_fields,
			boolean create_comment)
	{
		return getCreateTableSQL(new CreateTableColumnSorter(table_type, sort_fields), create_comment);
	}

	/**
	 * 获得该类型对应SQL的创建语句
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public String getCreateTableSQL()
	{
		return getCreateTableSQL(true, true);
	}
	
}
