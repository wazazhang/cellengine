package com.cell.sql.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

import com.cell.sql.SQLDriverManager;
import com.cell.util.concurrent.ThreadPool;


public class SQLPool
{
	public static enum PropertyKey
	{
		THREADPOOL_NAME				("threadpool.name"),
		THREADPOOL_MIN_THREAD_COUNT	("threadpool.minimum-thread-count"),
		THREADPOOL_MAX_THREAD_COUNT	("threadpool.maximum-thread-count"),

		PROXOOL_ALIAS				("proxool.alias"),
		PROXOOL_MAX_CONNECTION		("proxool.maximum-connection-count"),
		PROXOOL_MIN_CONNECTION		("proxool.minimum-connection-count"),
		 
		DB_USER						("user"),
		DB_PASSWORD					("password"),
		;
	
		final public String key;
		 private PropertyKey(String k) {
			key = k;
		}
		 
		 @Override
		public String toString() {
			return key;
		}
	}
	
//	Properties info = new Properties();
//	info.setProperty("proxool.maximum-connection-count", "20");
//	info.setProperty("proxool.house-keeping-test-sql", "select CURRENT_DATE");
//	info.setProperty("user", "sa");
//	info.setProperty("password", "");
//	String alias = "test";
//	String driverClass = "org.hsqldb.jdbcDriver";
//	String driverUrl = "jdbc:hsqldb:test";
//	String url = "proxool." + alias + ":" + driverClass + ":" + driverUrl;
//	connection = DriverManager.getConnection(url, info);
		
	ThreadPool	thread_pool;
	
	Properties 	info = new Properties();
	String 		url;

	/**
	 * @param dirver 驱动名 eg:<br>
	 * 		com.mysql.jdbc.Driver
	 * 
	 * @param url 数据库jdbc形式地址 eg: <br>
	 * 		jdbc:mysql://server-win2k3:3306/eatworld<br>
	 * 
	 * @param info 参数 eg:<br>
	 * 
	 * 		info.setProperty("threadpool.name", 					"2");<br>
	 * 		info.setProperty("threadpool.scheduled-thread-count", 	"2");<br>
	 * 		info.setProperty("threadpool.minimum-thread-count", 	"2");<br>
	 * 		info.setProperty("threadpool.maximum-thread-count", 	"4");<br>
	 * 
	 * 		info.setProperty("proxool.alias", 						"sql_proxy");<br>
	 * 		info.setProperty("proxool.maximum-connection-count", 	"30");<br>
	 * 		info.setProperty("proxool.minimum-connection-count", 	"5");<br>
	 * 
	 * 		info.setProperty("user", 								"root");<br>
	 * 		info.setProperty("password", 							"root");<br>
	 * 
	 * @throws Exception
	 */
	public SQLPool(String dirver, String url, Properties info) throws Exception
	{
		init(dirver, url, info);
	}
	
	/**
	 * 
	 * @param dirver
	 * @param url
	 * @param user
	 * @param password
	 * @throws Exception
	 */
	public SQLPool(String dirver, String url, String user, String password) throws Exception
	{
		info.setProperty(PropertyKey.PROXOOL_ALIAS.toString(), 					"sqlpool");
		info.setProperty(PropertyKey.PROXOOL_MIN_CONNECTION.toString(), 		"10");
		info.setProperty(PropertyKey.PROXOOL_MAX_CONNECTION.toString(), 		"30");

		info.setProperty(PropertyKey.THREADPOOL_NAME.toString(), 				"sqlpool");
		info.setProperty(PropertyKey.THREADPOOL_MIN_THREAD_COUNT.toString(), 	"10");
		info.setProperty(PropertyKey.THREADPOOL_MAX_THREAD_COUNT.toString(), 	"30");
		
		info.setProperty(PropertyKey.DB_USER.toString(), 						user);
		info.setProperty(PropertyKey.DB_PASSWORD.toString(),					password);
		
		init(dirver, url, info);
	}
	/**
	 * @param dirver
	 * @param url
	 * @param user
	 * @param password
	 * @param proxool_alias
	 * @param proxool_min_connection
	 * @param proxool_max_connection
	 * @throws Exception
	 */
	public SQLPool(String dirver, String url, 
			String user, 
			String password, 
			String proxool_alias,
			int proxool_min_connection,
			int proxool_max_connection
	) throws Exception
	{
		this(dirver, url, user, password, 
				proxool_alias, proxool_min_connection, proxool_max_connection,
				proxool_alias, proxool_min_connection, proxool_max_connection, 
				null);
	}
	
	/**
	 * @param dirver
	 * @param url
	 * @param user
	 * @param password
	 * @param proxool_alias
	 * @param proxool_min_connection
	 * @param proxool_max_connection
	 * @param threadpool_name
	 * @param threadpool_min_thread_count 推荐等于 proxool_min_connection
	 * @param threadpool_max_thread_count 推荐等于 proxool_max_connection
	 * @throws Exception
	 */
	public SQLPool(String dirver, String url, 
			String user, 
			String password, 
			String proxool_alias,
			int proxool_min_connection,
			int proxool_max_connection,
			String threadpool_name,
			int threadpool_min_thread_count,
			int threadpool_max_thread_count, 
			Properties append_properties
	) throws Exception
	{
		info.setProperty(PropertyKey.PROXOOL_ALIAS.toString(), 					proxool_alias);
		info.setProperty(PropertyKey.PROXOOL_MIN_CONNECTION.toString(), 		proxool_min_connection+"");
		info.setProperty(PropertyKey.PROXOOL_MAX_CONNECTION.toString(), 		proxool_max_connection+"");
		
		info.setProperty(PropertyKey.THREADPOOL_NAME.toString(), 				threadpool_name);
		info.setProperty(PropertyKey.THREADPOOL_MIN_THREAD_COUNT.toString(), 	threadpool_min_thread_count+"");
		info.setProperty(PropertyKey.THREADPOOL_MAX_THREAD_COUNT.toString(), 	threadpool_max_thread_count+"");
		
		info.setProperty(PropertyKey.DB_USER.toString(), 						user);
		info.setProperty(PropertyKey.DB_PASSWORD.toString(),					password);
		
		if (append_properties != null) {
			info.putAll(append_properties);
		}
		
		init(dirver, url, info);
		
	}
	
	private void init(String dirver, String url, Properties info) throws Exception
	{
		SQLDriverManager.init();
		
		Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
		info.setProperty("proxool.house-keeping-test-sql", "select CURRENT_DATE");
		
		String alias 	= info.getProperty(PropertyKey.PROXOOL_ALIAS.toString(), 				"sqlpool");
		String tp_name	= info.getProperty(PropertyKey.THREADPOOL_NAME.toString(), 				"sqlpool");
		String tp_itc	= info.getProperty(PropertyKey.THREADPOOL_MIN_THREAD_COUNT.toString(),	"10");
		String tp_xtc	= info.getProperty(PropertyKey.THREADPOOL_MAX_THREAD_COUNT.toString(), 	"30");
		
		this.url		= "proxool." + alias + ":" + dirver + ":" + url;
		this.info		= info;
		
		thread_pool = new ThreadPool(tp_name, 0, Integer.parseInt(tp_itc), Integer.parseInt(tp_xtc));
		
		for (Object key : info.keySet()) {
			if (key.equals(PropertyKey.DB_PASSWORD.toString()) || key.equals(PropertyKey.DB_USER.toString())) {
			}else {
				System.out.println(key + " = " + info.get(key));
			}
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------
	
	protected ReentrantLock 	lock 	= new ReentrantLock();
	
	/**
	 * 从池中获得一个空闲链接，该链接调用close后会返回到池中
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() {
		lock.lock();
		try {
			Connection conn = DriverManager.getConnection(url, info);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			lock.unlock();
		}
	}

	public Connection getConnection(String url) {
		lock.lock();
		try {
			Connection conn = DriverManager.getConnection(url, info);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			lock.unlock();
		}
	}

	public Properties getInfo() {
		return new Properties(info);
	}
	
//	-----------------------------------------------------------------------------------------------------------------------
//	task manager policy

//	int					hight_water_mark	= 10000;
//	int					low_water_mark		= 1000;
//	
//	/**
//	 * @param low 低水位标，超过低水位标后
//	 * @param hight 高水位标，超过高水位标后
//	 */
//	public void setWaterMark(int low, int hight) {
//		hight_water_mark = hight;
//		low_water_mark = low;
//	}
	
//	-----------------------------------------------------------------------------------------------------------------------
//	task executer
//	
//	public void executeTask(SQLTask r) 
//	{
////		int taskcount = thread_pool.getExecutor().getQueue().size();
////		
////		// 检查是否超出高水位标
////		if (taskcount > hight_water_mark)
////		{
////			thread_pool.getExecutor().remove(r);
////		}
////		// 检查是否超出低水位标
////		else if (taskcount > low_water_mark)
////		{
////			thread_pool.getExecutor().remove(r);
////		}
//
//		r.commited(this);
//		thread_pool.executeTask(r);
//	}
//	
//	public long getCommitedCount() {
//		return thread_pool.getExecutor().getTaskCount();
//	}
//	
//	public ThreadPoolExecutor getExecutor() {
//		return thread_pool.getExecutor();
//	}
//	
//	public void shutdown(){
//		thread_pool.shutdown();
//	}
//	
//	public ScheduledThreadPoolExecutor getScheduledExecutor() {
//		return thread_pool.getScheduledExecutor();
//	}
//	
//	-----------------------------------------------------------------------------------------------------------------------
//	
//	public ScheduledFuture<?> schedule(SQLTask r, long delay){
//		r.commited(this);
//		return thread_pool.schedule(r, delay);
//    }
//
//	public ScheduledFuture<?> scheduleAtFixedRate(SQLTask r, long initial, long delay) {
//		r.commited(this);
//		return thread_pool.scheduleAtFixedRate(r, initial, delay);
//    }
//	
//	-----------------------------------------------------------------------------------------------------------------------

}
