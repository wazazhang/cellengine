package com.cell.net.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;

import com.cell.CIO;

/**
 * HTTP请求对象
 */
public class HttpRequester 
{
	private String	defaultContentEncoding;
	
	private int		defaultTimeout;
	
	public HttpRequester() 
	{
		this(CIO.ENCODING, CIO.getLoadingTimeOut());
	}

	public HttpRequester(String charset, int timeout)
	{
		this.defaultContentEncoding	= charset;
		this.defaultTimeout			= timeout;
	}

//	-----------------------------------------------------------------------------------------------------
	
	public HttpResponse sendGet(String urlString) throws IOException 
	{
		return this.send(urlString, "GET", null, null);
	}

	/**
	 * 发送GET请求	
	 * @param urlString	URL地址	
	 * @param params 	参数集合
	 * @return 响应对象	
	 * @throws IOException 　　
	 */
	public HttpResponse sendGet(
			String urlString, 
			Map<String, String> params) throws IOException 
	{
		return this.send(urlString, "GET", params, null);
	}

	/**
	 * 　　 * 发送GET请求 　　 * 　　 * @param urlString 　　 * URL地址 　　 * @param params 　　
	 * * 参数集合 　　 * @param propertys 　　 * 请求属性 　　 * @return 响应对象 　　 * @throws
	 * IOException 　　
	 */
	public HttpResponse sendGet(
			String urlString, 
			Map<String, String> params,
			Map<String, String> propertys) throws IOException 
	{
		return this.send(urlString, "GET", params, propertys);
	}

	/**
	 * 　　 * 发送POST请求 　　 * 　　 * @param urlString 　　 * URL地址 　　 * @return 响应对象 　　
	 * * @throws IOException 　　
	 */
	public HttpResponse sendPost(String urlString) throws IOException {
		return this.send(urlString, "POST", null, null);
	}

	/**
	 * 　　 * 发送POST请求 　　 * 　　 * @param urlString 　　 * URL地址 　　 * @param params 　　
	 * * 参数集合 　　 * @return 响应对象 　　 * @throws IOException 　　
	 */
	public HttpResponse sendPost(String urlString, Map<String, String> params)
			throws IOException {
		return this.send(urlString, "POST", params, null);
	}

	/**
	 * 　　 * 发送POST请求 　　 * 　　 * @param urlString 　　 * URL地址 　　 * @param params 　　
	 * * 参数集合 　　 * @param propertys 　　 * 请求属性 　　 * @return 响应对象 　　 * @throws
	 * IOException 　　
	 */
	public HttpResponse sendPost(String urlString, Map<String, String> params,
			Map<String, String>

			propertys) throws IOException {
		return this.send(urlString, "POST", params, propertys);
	}

	/**
	 * 发送HTTP请求
	 * @param urlString 　　
	 * @return 响映对象
	 * @throws
	 * IOException 　　
	 */
	private HttpResponse send(
			String urlString, 
			String method,
			Map<String, String> parameters,
			Map<String, String> propertys) throws IOException 
	{
		// HttpURLConnection为局部变量
		HttpURLConnection urlConnection = null;
		// URL对象
		URL url = null;

		// 如果请求为GET方法，并且参数不为空
		if (method.equalsIgnoreCase("GET") && parameters != null) {
			// 构建并拼接参数字符串
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (String key : parameters.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(parameters.get(key));
				i++;
			}
			// 拼接URL串 + 参数
			urlString += param;
		}
		// NEW一个URL对象，由该对象的openConnection()方法将生成一个URLConnection对象
		url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection();

		// 设置相关属性，具体含义请查阅JDK文档
		urlConnection.setConnectTimeout(defaultTimeout);
		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);
		
		// 赋予请求属性
		if (propertys != null)
			for (String key : propertys.keySet()) {
				urlConnection.addRequestProperty(key, propertys.get(key));
			}

		// 如果请求为POST方法，并且参数不为空
		if (method.equalsIgnoreCase("POST") && parameters != null) {
			StringBuffer param = new StringBuffer();
			for (String key : parameters.keySet()) {
				param.append("&");
				param.append(key).append("=").append(parameters.get(key));
			}
			// 将参数信息发送到HTTP服务器
			// 要注意：一旦使用了urlConnection.getOutputStream().write()方法，

			urlConnection.setRequestMethod("GET");
			urlConnection.getOutputStream().write(param.toString().getBytes());
			urlConnection.getOutputStream().flush();
			urlConnection.getOutputStream().close();
		}

		return this.makeContent(urlString, urlConnection);
	}

	/**
	 * 　　 * 得到响应对象 　　 * 　　 * @param urlConnection 　　 * @return 响应对象 　　 * @throws
	 * IOException 　　
	 */
	private HttpResponse makeContent(String urlString,
			HttpURLConnection urlConnection) throws IOException

	{
		HttpResponse httpResponser = new HttpResponse();
		try {
			// 得到响应流
			InputStream in = urlConnection.getInputStream();
			// 封装成高级对象
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(in));
			// 内容集合(集合项为行内容)
			httpResponser.contentCollection = new Vector<String>();
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				httpResponser.contentCollection.add(line);
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();

			// 得到请求连接的字符集
			String ecod = urlConnection.getContentEncoding();
			if (ecod == null)
				ecod = this.defaultContentEncoding;

			// 将各属性赋值给响应对象
			httpResponser.urlString = urlString;
			httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
			httpResponser.file = urlConnection.getURL().getFile();
			httpResponser.host = urlConnection.getURL().getHost();
			httpResponser.path = urlConnection.getURL().getPath();
			httpResponser.port = urlConnection.getURL().getPort();
			httpResponser.protocol = urlConnection.getURL().getProtocol();
			httpResponser.query = urlConnection.getURL().getQuery();
			httpResponser.ref = urlConnection.getURL().getRef();
			httpResponser.userInfo = urlConnection.getURL().getUserInfo();
			httpResponser.content = new String(temp.toString().getBytes(), ecod);
			httpResponser.contentEncoding = ecod;
			httpResponser.code = urlConnection.getResponseCode();
			httpResponser.message = urlConnection.getResponseMessage();
			httpResponser.contentType = urlConnection.getContentType();
			httpResponser.method = urlConnection.getRequestMethod();
			httpResponser.connectTimeout = urlConnection.getConnectTimeout();
			httpResponser.readTimeout = urlConnection.getReadTimeout();

			return httpResponser;
		} catch (IOException e) {
			throw e;
		} finally {
			// 最终关闭流
			if (urlConnection != null)
				urlConnection.disconnect();
		}
	}

}