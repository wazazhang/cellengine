package com.cell.net.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.security.MD5;
import com.sun.org.apache.xerces.internal.util.HTTPInputSource;

public class HttpQuery implements Runnable
{	
	final public String 		url;
	final HttpQueryListener 	listener;

	public boolean				interaction;
	public String				charset	= "UTF-8";
	public String 				result;
	public int 					timeout = 20000;
	
	public HttpQuery(String url, int timeout)
	{
		this(url, timeout, CIO.ENCODING, null, true);
	}

	public HttpQuery(String url, int timeout, String charset)
	{
		this(url, timeout, charset, null, true);
	}

	public HttpQuery(String url, int timeout, String charset, HttpQueryListener listener)
	{
		this(url, timeout, charset, listener, true);
	}

	public HttpQuery(String url, int timeout, String charset, HttpQueryListener listener, boolean allow_user_interaction)
	{
		this.url					= url;
		this.timeout				= timeout;
		this.listener				= listener;
		this.charset				= charset;
		this.interaction 			= allow_user_interaction;
	}
	
	public void run() 
	{
		try
        {
            URL Url = new URL(url);
            
            URLConnection c = Url.openConnection();
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.setDoInput(true);
			
        	HttpURLConnection uc = null;
			if (c instanceof HttpURLConnection) {
				uc = ((HttpURLConnection)c);
			}
			
			c.connect();
			try {
//				System.out.println(c.getContent());
				Thread.yield();
	            InputStream is = c.getInputStream();
				try {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					while (true) {
						int data = is.read();
						if (data != -1) {
							baos.write(data);
						} else {
							break;
						}
					}
					result = CIO.stringDecode(baos.toByteArray(), charset);
				} finally {
					is.close();
				}
			} finally {
				if (uc != null) {
					uc.disconnect();
				}
			}
            if (listener!=null) {
            	listener.response(url, result);
            }
        }
		catch(Throwable err)
		{
			err.printStackTrace();
			listener.timeout(url);
		}
	}


	
	public static String blockQuery(String url, int timeout)
	{
		HttpQuery query = new HttpQuery(url, timeout);
		query.run();
		return query.result;
	}
	
	public static String blockQuery(String url, int timeout, String charset)
	{
		HttpQuery query = new HttpQuery(url, timeout, charset);
		query.run();
		return query.result;
	}
	
	
	public static void query(String url, HttpQueryListener listener)
	{
		query(url, 20000, listener);
	}

	public static void query(String url, int timeout, HttpQueryListener listener)
	{
		new Thread(new HttpQuery(url, timeout, CIO.ENCODING, listener)).start();
	}

	public static void query(String url, int timeout, HttpQueryListener listener, String charset)
	{
		new Thread(new HttpQuery(url, timeout, charset, listener)).start();
	}
	
	public static void main(String[] args) 
	{
//		{
//			String q1 = "http://download.java.net/media/joal/webstart/joal.jnlp";
//			query(q1, 10000, new HttpQueryListener() {
//				public void response(String url, String result) {
//					System.out.println(result);
//				}
//				public void timeout(String url) {
//					System.out.println("timeout from : " + url);
//				}
//			});
//		}
		{
			String q2 = "http://download.java.net/media/gluegen/webstart/gluegen-rt.jnlp";
			query(q2, 10000, new HttpQueryListener() {
				public void response(String url, String result) {
					System.out.println(result);
				}
				public void timeout(String url) {
					System.out.println("timeout from : " + url);
				}
			});
		}
	}
	
}
