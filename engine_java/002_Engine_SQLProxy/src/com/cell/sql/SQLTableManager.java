package com.cell.sql;

import java.util.concurrent.ConcurrentHashMap;


/**
 * loadAll 读入所有数据，在运行时，只写入数据，所有Table的PrimaryKey由该类管理。
 * 相当于在内存里保存了所有行数据，写入和读取都很快。
 * @author WAZA
 * @param <K> PrimaryKey 类型
 * @param <R> Row 类型
 */
public abstract class SQLTableManager<K, R extends SQLTableRow<K>> extends SQLColumnManager<K, R>
{
	public SQLTableManager(Class<R> cls, String tableName) throws Exception
	{
		super(cls, tableName, new ConcurrentSQLColumnMap<K, R>(1024));
	}
	
	public SQLTableManager(Class<R> cls, String tableName, int base_size) throws Exception
	{
		super(cls, tableName, new ConcurrentSQLColumnMap<K, R>(base_size));
	}
	
	static class ConcurrentSQLColumnMap<K, R> extends ConcurrentHashMap<K, R> implements SQLColumnMap<K, R>
	{
		private static final long serialVersionUID = 1L;

		public ConcurrentSQLColumnMap(int base_size) {
			super(base_size);
		}
	}
}
