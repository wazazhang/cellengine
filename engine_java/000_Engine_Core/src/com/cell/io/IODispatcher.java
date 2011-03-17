package com.cell.io;

import java.io.InputStream;

/**
 * 获取字符串对应的资源，可能是网络资源，也可能是本地资源或文件。比如: http:// ftp:// file:///
 * @author WAZA
 *
 */
public interface IODispatcher 
{
	public InputStream getInputStream(String path);
	
	public IOCacheService getCache();
	
//	/**
//	 * 一次性同步读取所有资源
//	 * @param path
//	 * @return
//	 */
//	public byte[] loadData(String path);
	

	/** 获得已从CIO读取的字节数 */
	public long getLoadedBytes();

	/** 读取数据的超时时间 */
	public void setLoadingTimeOut(int loadingTimeOut);

	/** 读取数据的超时时间 */
	public int getLoadingTimeOut();

	/** 读取数据的重复次数 */
	public void setLoadRetryCount(int loadRetryCount);

	/** 读取数据的重复次数 */
	public int getLoadRetryCount();
}
