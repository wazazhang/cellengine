
package com.cell;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.io.LengthInputStream;

//import javax.microedition.lcdui.Image;
//import javax.microedition.rms.RecordStore;

/**
 * util IO function.</br> 
 * @author WAZA
 * @since 2006-11-28 
 * @version 1.0
 */
public class CIO extends CObject
{		
	public static int DEFAULT_READ_BLOCK_SIZE = 4096;

//	------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * <pre>
	 * <b>得到流用于异步动态读取</b>
	 * 获取字符串对应的资源，可能是网络资源，也可能是本地资源或文件。
	 * 比如: http:// ftp:// file:///
	 * </pre>
	 * @param path
	 * @return
	 */
	public static InputStream getInputStream(String path)
	{
		InputStream is = getAppBridge().getIO().getInputStream(path);
		if (is == null) {
			System.err.println("getInputStream : resource not found : " + path);
			return null;
		}
		return is;
	}
	
	/**
	 * <pre>
	 * <b>一次性同步读取所有资源</b>
	 * 获取字符串对应的资源，可能是网络资源，也可能是本地资源或文件。
	 * 比如: http:// ftp:// file:///
	 * </pre>
	 * @param path
	 * @return
	 */
	public static byte[] loadData(String path)
	{
		return loadData(path, null, DEFAULT_READ_BLOCK_SIZE);
	}
	
	/**
	 * <pre>
	 * <b>一次性同步读取所有资源</b>
	 * 获取字符串对应的资源，可能是网络资源，也可能是本地资源或文件。
	 * 比如: http:// ftp:// file:///
	 * </pre>
	 * @param path
	 * @param percent
	 * @param block_size
	 * @return
	 */
	public static byte[] loadData(String path, AtomicReference<Float> percent, int block_size)
	{
		for (int i = Math.max(1, getLoadRetryCount()); i > 0; --i) 
		{
			InputStream is = getInputStream(path);
			if (is == null) {
				return null;
			}
			try {
				return readStream(is, percent, block_size);
			} catch (SocketTimeoutException err) {
				System.err.println("timeout retry load data [" + is.getClass().getSimpleName() + "] : " + path);
			}  catch (IOException err) {
				err.printStackTrace();
				System.err.println("retry load data [" + is.getClass().getSimpleName() + "] : " + path);
			} finally {
				try {
					is.close();
				} catch (IOException e) {}
			}
		}
		return null;
	}

	
	public static ByteArrayInputStream loadStream(String path) {
		byte[] data = loadData(path);
		if (data != null) {
			return new ByteArrayInputStream(data);
		}
		return null;
	}

//	------------------------------------------------------------------------------------------------------------------------
	
//	------------------------------------------------------------------------------------------------------------------------

//	------------------------------------------------------------------------------------------------------------------------
	
	public static String stringDecode(byte[] data, String encoding) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		try {
			CharsetDecoder decoder = Charset.forName(encoding).newDecoder();  
			CharBuffer cb = decoder.decode(bb);
			try {
				char[] ret = new char[cb.remaining()];
				cb.get(ret);
				return new String(ret);
			} finally {
				cb.clear();
			}
		} catch (Exception err) {
			return null;
		} finally {
			bb.clear();
		}
	}
	
	public static byte[] stringEncode(String src, String encoding) {
		CharBuffer cb = CharBuffer.wrap(src);
		try {
			CharsetEncoder encoder = Charset.forName(encoding).newEncoder();  
			ByteBuffer bb = encoder.encode(cb);
			try {
				byte[] ret = new byte[bb.remaining()];
				bb.get(ret);
				return ret;
			} finally {
				bb.clear();
			}
		} catch (Exception err) {
			return null;
		} finally {
			cb.clear();
		}
	}
	
	public static String readAllText(String file)
	{
		return readAllText(file, CObject.getEncoding());
	}
	
	public static String readAllText(String file, String encoding)
	{
		InputStream is = getInputStream(file);
		if (is != null) {
			return readAllText(is, encoding);
		} else {
			System.err.println("file not found : "+file);
		}
		return null;
	}
	
	public static String[] readAllLine(String file)
	{
		return readAllLine(file, CObject.getEncoding());
	}
	
	public static String[] readAllLine(String file, String encoding)
	{
		InputStream is = getInputStream(file);
		if (is != null) {
			return readAllLine(is, encoding);
		} else {
			System.err.println(file);
			return new String[] { "" };
		}
	}

	public static String readAllText(InputStream is)
	{
		return readAllText(is, CObject.getEncoding());
	}
	
	public static String readAllText(InputStream is, String encoding)
	{
		try {
			InputStreamReader isr = new InputStreamReader(is, encoding);
			StringBuilder sb = new StringBuilder();
			char[] buff = new char[DEFAULT_READ_BLOCK_SIZE/2];
			while (true) {
				int readed = isr.read(buff);
				if (readed >= 0) {
					sb.append(buff, 0, readed);
				} else {
					break;
				}
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		byte[] data = CIO.readStream(is);
//		if (data != null) {
//			return stringDecode(data, encoding);
//		}
		return null;
	}

	public static String[] readAllLine(InputStream is) {
		return readAllLine(is, CObject.getEncoding());
	}
	
	public static String[] readAllLine(InputStream is, String encoding)
	{
		try {
			String src = readAllText(is, encoding);
			String[] ret = CUtil.splitString(src, "\n");
			for (int i = ret.length - 1; i >= 0; i--) {
				int ld = ret[i].lastIndexOf('\r');
				if (ld >= 0) {
					ret[i] = ret[i].substring(0, ld);
				}
			}
			return ret;
		} catch (Exception err) {
			err.printStackTrace();
			return new String[] { "" };
		}
	}
	
//	------------------------------------------------------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	public static<T> T cloneObject(T src)
	{
		if (src == null) {
			return src;
		}
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(src);
			oos.flush();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			T ret = (T)ois.readObject();
			
			ois.close();
			oos.close();
			
			ois = null;
			oos = null;
			bais = null;
			baos = null;
			
			return ret;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] objectToBin(Object src)
	{
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(DEFAULT_READ_BLOCK_SIZE);
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(src);
			oos.flush();
			oos.close();
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object binToObject(byte[] data)
	{
		try {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			Object ret = ois.readObject();
			ois.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getPathDir(String file_path)
	{
		file_path		= file_path.replace('\\', '/');
		String dir 		= file_path.substring(0, file_path.lastIndexOf("/")+1);
		return dir;
	}
	
	public static String getPathName(String file_path)
	{
		file_path		= file_path.replace('\\', '/');
		String name		= file_path.substring(file_path.lastIndexOf("/")+1);
		return name;
	}

//	-----------------------------------------------------------------------------------------------------------------

	/**
	 * 读取一个流的所有数据到字节序。<br>
	 * 只要InputStream里有数据，
	 * 该方法都将阻塞，直到 read < 0，所以该方法不适合读取动态流。<br>
	 * <b><font color=ff0000>该方法将自动关闭流。</font></b>
	 * @param is
	 * @return 
	 */
	public static byte[] readStream(InputStream is) {
		try {
			return readStream(is, null, DEFAULT_READ_BLOCK_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {}
		}
		return null;
	}

	/**
	 * 只要InputStream里有数据，
	 * 该方法都将阻塞，直到 read < 0，
	 * 所以该方法不适合读取动态流。
	 * <b><font color=ff0000>该方法不会自动关闭流。</font></b>
	 * @param is
	 * @param 预计的进度 0~1
	 * @return 
	 */
	public static byte[] readStream(InputStream is, AtomicReference<Float> percent) throws IOException
	{
		return readStream(is, percent, DEFAULT_READ_BLOCK_SIZE);
	}

	/**
	 * 只要InputStream里有数据，
	 * 该方法都将阻塞，直到 read < 0，
	 * 所以该方法不适合读取动态流。
	 * <b><font color=ff0000>该方法不会自动关闭流。</font></b>
	 * @param is
	 * @param percent 预计的进度 0~1
	 * @param block_size 每次读取的块大小
	 * @return 
	 */
	public static byte[] readStream(InputStream is, AtomicReference<Float> percent, int block_size) throws IOException
	{
		if (is != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(block_size);
			int count 		= 0;
			int available	= is.available();
			if (is instanceof LengthInputStream) {
				available	= ((LengthInputStream)is).getLength();
			} else {
				available	= is.available();
			}
			byte[] readed 	= new byte[block_size];
			while (true) {
				int read_bytes 	= is.read(readed);
				if (read_bytes >= 0) {
					baos.write(readed, 0, read_bytes);
					count += read_bytes;
					if (percent != null) {
						if (available < count) {
							available = count + is.available();
						}
						percent.set(count / (float) available);
					}
				} else {
					break;
				}
			}
			return baos.toByteArray();
		}
		return null;
	}

	/**
	 * 只要InputStream里有数据，
	 * 该方法都将阻塞，直到 available <= 0，
	 * <b><font color=ff0000>该方法不会自动关闭流。</font></b>
	 * @param is
	 * @return 
	 */
	public static byte[] readAvailable(InputStream is) throws IOException
	{
		if (is != null) {
			int available	= is.available();
			ByteArrayOutputStream baos = new ByteArrayOutputStream(available);
			byte[] readed 	= new byte[available];
			while (available > 0) {
				int read_bytes 	= is.read(readed);
				if (read_bytes >= 0) {
					baos.write(readed, 0, read_bytes);
					available = is.available();
				} else {
					break;
				}
			}
			return baos.toByteArray();
		}
		return null;
	}
	
//	------------------------------------------------------------------------------------------------------------------------



	/**
	 * 获得已从CIO读取的字节数
	 * @return
	 */
	public static long getLoadedBytes() {
		return getAppBridge().getIO().getLoadedBytes();
	}

	/**读取数据的超时时间*/
	public static void setLoadingTimeOut(int loadingTimeOut) {
		getAppBridge().getIO().setLoadingTimeOut(loadingTimeOut);
	}
	
	/**读取数据的超时时间*/
	public static int getLoadingTimeOut() {
		return getAppBridge().getIO().getLoadingTimeOut();
	}

	/**读取数据的重复次数*/
	public static void setLoadRetryCount(int loadRetryCount) {
		getAppBridge().getIO().setLoadRetryCount(loadRetryCount);
	}
	/**读取数据的重复次数*/
	public static int getLoadRetryCount() {
		return getAppBridge().getIO().getLoadRetryCount();
	}

}