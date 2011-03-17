package com.cell;

import com.cell.io.IODispatcher;


public interface IAppBridge 
{
	public Thread 			createTempThread();
	public Thread 			createTempThread(Runnable run);
	public Thread 			createServiceThread();
	public Thread 			createServiceThread(Runnable run);
	
	public IODispatcher		getIO();
	
//	public InputStream		getResource(String file);
//	
//	public ResInputStream	getRemoteResource(String file, int timeout) throws IOException;
	
	public void 			setClipboardText(String str);
	public String 			getClipboardText();
	
	public String 			getMacAddr();
	public int 				getPing(String host, int bufferSize);

	public String 			getAppProperty(String key);
	public String			setAppProperty(String key, String value);
	
	public ClassLoader		getClassLoader();

	public void 			openBrowser(String url);
}
