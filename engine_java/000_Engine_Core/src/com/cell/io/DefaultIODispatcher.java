package com.cell.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.cell.CIO;
import com.cell.exception.NotImplementedException;


public class DefaultIODispatcher implements IODispatcher
{
	private static long loaded_bytes = 0;
	
	private int url_loading_time_out = 20000; // ms
	private int url_loading_retry_count = 5;
	
	@Override
	public IOCacheService getCache() {
		return null;
	}
	
	/**
	 * 覆盖获得JAR包内容
	 * @param path
	 * @return
	 */
	protected InputStream getJarResource(String path) 
	{
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
	}
	
	/**
	 * 覆盖获得JAR包内容
	 * @param path
	 * @return
	 * @throws FileNotFoundException 
	 */
	protected InputStream getFileResource(String path) throws FileNotFoundException
	{
		File file = new File(path);
		if (file.exists()) {
			return new FileInputStream(file);
		}
		return null;
	}

	/**
	 * 覆盖获得 http:// 协议资源内容
	 * @param path
	 * @param timeout
	 * @return
	 */
	protected RemoteInputStream getHttpResource(String path) throws IOException {
		try {
			return getUrlResource(new URL(path));
		} catch (MalformedURLException err) {
			return null;
		}
	}

	/**
	 * 覆盖获得 ftp:// 协议资源内容
	 * @param path
	 * @param timeout
	 * @return
	 */
	protected RemoteInputStream getFtpResource(String path) throws IOException {
		try {
			return getUrlResource(new URL(path));
		} catch (MalformedURLException err) {
			return null;
		}
	}

	/**
	 * 覆盖获得 res:// 协议资源内容
	 * @param path
	 * @param timeout
	 * @return
	 */
	protected RemoteInputStream getResResource(String path) throws IOException
	{
		return null;
	}
	
	
	protected RemoteInputStream getUrlResource(URL url) throws IOException 
	{
		URLConnection conn = url.openConnection();
		conn.setConnectTimeout(url_loading_time_out);
		conn.setReadTimeout(url_loading_time_out);
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.connect();
		long last_modify_time = conn.getLastModified();
		IOCacheService cache_service =  CIO.getAppBridge().getIO().getCache();
		if (cache_service != null) {
			RemoteInputStream cache = cache_service.findCache(url, last_modify_time);
			if (cache != null) {
				return cache;
			}
		}
		return new URLConnectionInputStream(url, conn);
	}

//	-----------------------------------------------------------------------------------------------------
	
	@Override
	public InputStream getInputStream(String path) 
	{
		path = path.trim();
		try
		{
			if (path.startsWith("/")) 
			{
				return getJarResource(path);
			} 
			else if (path.startsWith("res://"))
			{
				return getResResource(path);
			} 
			else if (path.startsWith("ftp://")) 
			{
				return getFtpResource(path);
			}
			else if (path.startsWith("http://"))
			{
				return getHttpResource(path);
			} 
			else if (path.startsWith("file:///"))
			{
				return getFileResource(path.substring(8));
			} 
			else
			{
				return getFileResource(path);
			}
		}
		catch(Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
//	@Override
//	public byte[] loadData(String path)
//	{
//		path = path.trim();
//		byte[] data = null;
//		try
//		{
//			// load from jar
//			if (path.startsWith("/")) {
//				return CIO.readStream(getJarResource(path));
//			}
//			// user define resource
//			else if (path.startsWith("res://")) {
//				return loadRemoteData(path);
//			}
//			// load from http
//			else if (path.startsWith("ftp://")) {
//				return loadFTPData(path);
//			}
//			// load from http
//			else if (path.startsWith("http://")) {
//				try {
//					return loadURLData(new URL(path));
//				} catch (MalformedURLException err) {
//				}
//			}
//			// load from file
//			else if (path.startsWith("file:///"))
//			{
//				File file = new File(path.substring(8));
//				if (file.exists()) {
//					return CIO.readStream(new FileInputStream(file));
//				}
//			} 
//			// load from file
//			else {
//				File file = new File(path);
//				if (file.exists()) {
//					return CIO.readStream(new FileInputStream(file));
//				}
//			}
//		} catch(Throwable err) {
//			System.err.println(path);
//			err.printStackTrace();
//		}
//		return data;
//	
//	}

	/**
	 * 获得已从CIO读取的字节数
	 * @return
	 */
	public long getLoadedBytes() {
		return loaded_bytes;
	}

	/**读取数据的超时时间*/
	public void setLoadingTimeOut(int loadingTimeOut) {
		url_loading_time_out = loadingTimeOut;
	}
	
	/**读取数据的超时时间*/
	public int getLoadingTimeOut() {
		return url_loading_time_out;
	}

	/**读取数据的重复次数*/
	public void setLoadRetryCount(int loadRetryCount) {
		url_loading_retry_count = Math.max(1, loadRetryCount);
	}
	
	/**读取数据的重复次数*/
	public int getLoadRetryCount() {
		return url_loading_retry_count;
	}
	
	
//	------------------------------------------------------------------------------------------------------------------------
	

	abstract static public class RemoteInputStream extends InputStream
	{
		protected InputStream 			src;
		private ByteArrayOutputStream	cache_stream;
		private boolean 				is_end = false;
		
		public RemoteInputStream() {
			if (CIO.getAppBridge().getIO().getCache() != null) {
				this.cache_stream = new ByteArrayOutputStream(CIO.DEFAULT_READ_BLOCK_SIZE);
			}
		}
		
		public ByteArrayOutputStream getCacheStream() {
			return cache_stream;
		}
		
		public boolean isEnd() {
			return is_end;
		}
		
		@Override
		public int available() throws IOException {
			return src.available();
		}
		
		@Override
		public int read() throws IOException {
			int count = src.read();
			if (count >= 0) {
				loaded_bytes ++;
				if (cache_stream != null) {
					cache_stream.write(count);
				}
			} else {
				is_end = true;
			}
			return count;
		}
		@Override
		public int read(byte[] b) throws IOException {
			int count = src.read(b);
			if (count > 0) {
				loaded_bytes += count;
				if (cache_stream != null) {
					cache_stream.write(b, 0, count);
				}
			} else {
				is_end = true;
			}
			return count;
		}
		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			int count = src.read(b, off, len);
			if (count > 0) {
				loaded_bytes += count;
				if (cache_stream != null) {
					cache_stream.write(b, off, count);
				}
			} else {
				is_end = true;
			}
			return count;
		}
		
		@Override
		public long skip(long n) throws IOException {
			throw new NotImplementedException();
		}
		
		@Override
		public void close() throws IOException {
			src.close();
		}
		
		@Override
		public void mark(int readlimit) {}
		@Override
		public boolean markSupported() {
			return false;
		}
	}

//	------------------------------------------------------------------------------------------------------------------------

	static public class RemoteResInputStream extends RemoteInputStream
	{
		public RemoteResInputStream(InputStream res) throws IOException {
			this.src = res;
		}
	}

//	-----------------------------------------------------------------------------------------------------------------

	static public class RemoteUrlInputStream extends RemoteInputStream
	{
		final protected URL				url;
		final protected long 			last_modify_time;
		
		public RemoteUrlInputStream(URL url, InputStream is, long last_modify_time) throws IOException {
			super.src = is;
			this.url = url;
			this.last_modify_time = last_modify_time;
		}
		
		@Override
		public void close() throws IOException {
			try {
				super.close();
			} finally {
				if (isEnd() && getCacheStream() != null) {
					CIO.getAppBridge().getIO().getCache().writeCache(url, last_modify_time, getCacheStream());
				}
			}
		}
	}

	
//	------------------------------------------------------------------------------------------------------------------------

	static public class URLConnectionInputStream extends RemoteInputStream implements LengthInputStream
	{
		final protected URL				url;
		final protected long 			last_modify_time;
		final protected int				length;
		
		public URLConnectionInputStream(URL url, URLConnection c) throws IOException {
			this.url				= url;
			this.length 			= c.getContentLength();
			this.last_modify_time	= c.getLastModified();
			this.src 				= c.getInputStream();
		}
		
		@Override
		public int getLength() {
			return length;
		}
		
		@Override
		public void close() throws IOException {
			try {
				super.close();
			} finally {
				if (isEnd() && getCacheStream() != null) {
					CIO.getAppBridge().getIO().getCache().writeCache(url, last_modify_time, getCacheStream());
				}
			}
		}
	}
}
