package com.cell.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.cell.CUtil;


/**
 * 管理数据库表的抽象结构
 * SQLColumnMap为获取当前SQL表和内存的缓冲区
 * @author WAZA
 * @param <K> PrimaryKey 类型
 * @param <R> Row 类型
 */
public abstract class SQLColumnManager<K, R extends SQLTableRow<K>> extends SQLColumnAdapter<K, R>
{
//	---------------------------------------------------------------------------------------------------------------------------------------------------------

	final SQLColumnMap<K, R> 		data_map;
	final ReentrantReadWriteLock	data_lock		= new ReentrantReadWriteLock();
	
	protected Lock 					data_readLock 	= data_lock.readLock();
	protected Lock 					data_writeLock 	= data_lock.writeLock();

//	---------------------------------------------------------------------------------------------------------------------------------------------------------

	public SQLColumnManager(Class<R> cls, String tableName, SQLColumnMap<K, R> data_map) throws Exception
	{
		super(cls, tableName);
		this.data_map		= data_map;
	}
	
	public R newRow() throws Exception {
		return table_class.newInstance();
	}

	/**
	 * 获得SQL表和内存的缓冲区
	 * @return
	 */
	protected SQLColumnMap<K, R> getDataMap() {
		return data_map;
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------------------------------
	
//	---------------------------------------------------------------------------------------------------------------------------------------------------------

//	public void loadAll(Connection conn, int block_size) throws Exception
//	{
//		// 读入表数据
//		try
//		{
//			if (data_map.size() > 0) {
//				throw new Exception("already loaded [" + table_name + "] data  !");
//			}
//			
//			long starttime = System.currentTimeMillis();
//			log.info("loading [" + table_name + "] ...");
//			
//			SQMTypeManager.ValidateResult vresult = validateAndAutoFix(conn);
//			
//			if (vresult == SQMTypeManager.ValidateResult.OK)
//			{
//				data_writeLock.lock();
//				try {
//					int index = 0;
//					while (true) {
//						long btime = System.currentTimeMillis();
//						String sql = "SELECT * FROM " + table_name + " LIMIT " + index + "," + block_size + ";";
//						ArrayList<R> rows = selectFully(conn, sql);
//						if (rows != null && !rows.isEmpty()) {
//							index += rows.size();
//							for (R row : rows) {
//								data_map.put(row.getPrimaryKey(), row);
//							}
//							log.info("loading [" + table_name + "]" +
//									" : block size = " + rows.size()+
//									" : last id = " + rows.get(rows.size()-1).getPrimaryKey() +
//									" : use time = " + (System.currentTimeMillis() - btime) + "(ms)");
//						} else {
//							break;
//						}
//					}
////					ArrayList<R> rows = selectAll(conn);
////					for (R row : rows) {
////						data_map.put(row.getPrimaryKey(), row);
////					}
//				} finally {
//					data_writeLock.unlock();
//				}
//				log.info("loaded  [" + table_name + "]" +
//						" : total size = " + size() + 
//						" : use time = " + (System.currentTimeMillis() - starttime) + "(ms)");
//			}
//			else
//			{
//				throw new SQLException("validate error !");
//			}
//		} 
//		catch (Exception e) {
//			log.error("load table [" + table_name + "] faild!\n" + e.getMessage(), e);
//			throw e;
//		}
//	}
	
	public void close(Connection conn)
	{
		data_writeLock.lock();
		try {
			for (R row : data_map.values()) {
				try {
					updateWithDB(row, conn, table_columns);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		} finally {
			data_writeLock.unlock();
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------------------------------
//	write operator
	
	/** 
	 * 向数据库插入一行数据，如果该行已经存在于数据库则将会引起SQLExcption
	 * @param conn
	 * @param row
	 * @return 
	 * @throws Exception
	 */
	public boolean insert(Connection conn, R row) throws Exception
	{
		data_writeLock.lock();
		try {
			insertWithDB(row, conn);
			data_map.put(row.getPrimaryKey(), row);
			return true;
		} finally {
			data_writeLock.unlock();
		}
	}

	/**
	 * 从数据库删除一行数据
	 * @param conn
	 * @param primary_key
	 * @return 被删除的数据
	 * @throws Exception
	 */
	public R remove(Connection conn, K primary_key) throws Exception
	{
		data_writeLock.lock();
		try {
			R row = data_map.get(primary_key);
			if (row != null) {
				deleteWithDB(primary_key, conn);
				data_map.remove(primary_key);
			}
			return row;
		} finally {
			data_writeLock.unlock();
		}
	}
	
	/**
	 * 向数据库存储指定key值的指定的字段
	 * @param conn
	 * @param primary_key
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	public R updateFields(Connection conn, K primary_key, SQLColumn ... fields) throws Exception
	{
		data_writeLock.lock();
		try {
			R row = data_map.get(primary_key);
			if (row != null) {
				updateWithDB(row, conn, fields);
			}
			return row;
		} finally {
			data_writeLock.unlock();
		}
	}

	/**
	 * 向数据库存储对象指定的字段
	 * @param conn
	 * @param obj
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	public R updateFields(Connection conn, R obj, SQLColumn ... fields) throws Exception
	{
		data_writeLock.lock();
		try {
			R row = data_map.get(obj.getPrimaryKey());
			if (row != null) {
				updateWithDB(obj, conn, fields);
				data_map.put(obj.getPrimaryKey(), obj);
				return row;
			}
			return null;
		} finally {
			data_writeLock.unlock();
		}
	}

	/**
	 * 向数据库存储对象根据key值
	 * @param conn
	 * @param primary_key
	 * @return
	 * @throws Exception
	 */
	public R update(Connection conn, K primary_key) throws Exception
	{
		return updateFields(conn, primary_key, table_columns);
	}

	/**
	 * 向数据库存储对象
	 * @param conn
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public R update(Connection conn, R obj) throws Exception
	{
		return updateFields(conn, obj, table_columns);
	}

	/**
	 * 执行插入或者更新操作，如果该行已经存在于数据库，则执行更新语句，否则执行插入语句。
	 * @param conn
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public R put(Connection conn, R obj) throws Exception
	{
		data_writeLock.lock();
		try {
			R row = data_map.get(obj.getPrimaryKey());
			if (row != null) {
				updateWithDB(obj, conn, table_columns);
				data_map.put(obj.getPrimaryKey(), obj);
				return row;
			} else {
				insertWithDB(obj, conn);
				data_map.put(obj.getPrimaryKey(), obj);
				return null;
			}
		} finally {
			data_writeLock.unlock();
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------------------------------
//	read only

	/**
	 * 得到所有对象的迭代子
	 * @return
	 */
	public Iterator<R> iterator()
	{
		data_readLock.lock();
		try {
			return data_map.values().iterator();
		} finally {
			data_readLock.unlock();
		}
	}
	
	/**
	 * 根据key值获得对象
	 * @param primary_key
	 * @return
	 */
	public R get(K primary_key)
	{
		data_readLock.lock();
		try {
			R row = data_map.get(primary_key);
			return row;
		} finally {
			data_readLock.unlock();
		}
	}
	
	/**
	 * 根据key值获得对象，如果目标不存在，则将新值插入
	 * @param primary_key
	 * @param new_value
	 * @return 如果原值不存在，则返回新值
	 */
	public R getIfAbsent(Connection conn, K primary_key, R new_value) throws Exception
	{
		data_writeLock.lock();
		try {
			R row = data_map.get(primary_key);
			if (row != null) {
				return row;
			} else if (new_value!=null) {
				if (primary_key.equals(new_value.getPrimaryKey())) {
					insertWithDB(new_value, conn);
					data_map.put(primary_key, new_value);
				}
				return new_value;
			} else {
				return null;
			}
		} finally {
			data_writeLock.unlock();
		}
	}
	
	/**
	 * 查找指定的对象
	 * @param finder 重载finder的equals(Object obj)方法获得对象
	 * @return
	 * @see Object.equals(Object obj);
	 */
	public R find(Object finder)
	{
		data_readLock.lock();
		try {
			for (R row : data_map.values()) {
				if (finder.equals(row)){
					return row;
				}
			}
			return null;
		} finally {
			data_readLock.unlock();
		}
	}
	
	/**
	 * 获取所有对象
	 * 在不清楚对象特性之前最好不要随意使用
	 * @return
	 */
	public Collection<R> getAll()
	{
		data_readLock.lock();
		try {
			return data_map.values();
		} finally {
			data_readLock.unlock();
		}		
	}
	
	/**
	 * 返回数据库行的数量
	 * @return
	 */
	public int size(){
		data_readLock.lock();
		try {
			return data_map.size();
		} finally {
			data_readLock.unlock();
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------------------------------
}
