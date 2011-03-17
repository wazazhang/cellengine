package com.cell.io;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import com.cell.io.DefaultIODispatcher.RemoteInputStream;


/**
 * 用于缓冲下载的资源
 * @author WAZA
 */
public interface IOCacheService
{
	/**
	 * @param url
	 * @param modification_time
	 * @param baos
	 */
	public void writeCache(URL url, long last_modify_time, ByteArrayOutputStream baos);

	/**
	 * @param url
	 * @param modification_time
	 * @return
	 */
	public RemoteInputStream findCache(URL url, long last_modify_time);
}
