package com.cell.sql;

import java.sql.Connection;


/**
 * 数据库表和实体的关系,
 * 没有缓冲，该类的方法将实时的和SQL表进行通讯
 * @author WAZA
 * @param <K> PrimaryKey 类型
 * @param <R> Row 类型
 */
public abstract class SQLColumnMapping<K, R extends SQLTableRow<K>> extends SQLColumnAdapter<K, R>
{
	public SQLColumnMapping(Class<R> cls, String tableName)
	{
		super(cls, tableName);
	}
	
	/**
	 * 获得指定键对应的行
	 * @param conn
	 * @param primaryKey
	 * @return
	 * @throws Exception
	 */
	synchronized public R get(Connection conn, K primaryKey) throws Exception {
		return select(primaryKey, conn);
	}

	/**
	 * 填入一行数据，如果已有该行，则替代原有数据，否则插入一行新数据
	 * @param conn
	 * @param obj
	 * @return 被替代行的原始数据
	 * @throws Exception
	 */
	synchronized public R put(Connection conn, R obj) throws Exception {
		return putFields(conn, obj, table_columns);
	}
	
	/**
	 * 填入一行数据，如果已有该行，则替代原有数据，否则插入一行新数据
	 * @param conn
	 * @param obj
	 * @return 被替代行的原始数据
	 * @throws Exception
	 */
	synchronized public R putFields(Connection conn, R obj, SQLColumn... fields) throws Exception {
		R row = get(conn, obj.getPrimaryKey());
		if (row != null) {
			updateWithDB(obj, conn, fields);
		} else {
			insertWithDB(obj, conn);
		}
		return row;
	}
	
	/**
	 * 删除一行数据
	 * @param conn
	 * @param primaryKey
	 * @return
	 * @throws Exception
	 */
	synchronized public R remove(Connection conn, K primaryKey) throws Exception {
		R row = get(conn, primaryKey);
		if (row != null) {
			deleteWithDB(primaryKey, conn);
		}
		return row;
	}

}
