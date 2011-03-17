package com.cell.sql.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SQLTask implements Runnable
{
//	ResultSetMetaData 	meta_data;
	SQLPool		 		pool;
	long 				commit_time;
	
//	protected String	sql;
//	public SQLTask(String sql) {
//		this.sql = sql;
//	}
	
	public SQLTask() {}
	
	final void commited(SQLPool pool) {
		commit_time = System.currentTimeMillis();
		this.pool = pool;
	}

	final public boolean isCommited() {
		return pool != null;
	}

	final public long getCommitTime() {
		return commit_time;
	}

	final public void run() 
	{
		if (pool!=null)
		{
			Connection conn = pool.getConnection();
			
			try {
				execute(conn);
			} catch (Throwable e) {
				errorOccured(e);
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
					}
				}
			}
		}
		else
		{
			try {
				throw new Exception("task not commit !");
			} catch (Throwable e) {
				e.printStackTrace();
			} 
		}
	}
	
	/**
	 * 子类执行的sql指令<br>
	 * do not close Connection, it will be auto close !
	 * @param conn
	 * @return  <code>true</code> if the first result is a <code>ResultSet</code> 
     *         object; <code>false</code> if it is an update count or there are 
     *         no results
	 * @throws SQLException
	 * @see {@link Statement}
	 */
	abstract public void execute(Connection conn) throws Exception ;
	
	/**
	 * 可覆盖该方法获取错误信息
	 * @param e
	 */
	public void errorOccured(Throwable e){
		e.printStackTrace();
	}
	
	
	
	
	
	
	
}
