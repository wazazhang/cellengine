package com.cell.j2se;

import java.applet.Applet;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import com.cell.CObject;
import com.cell.IAppBridge;
import com.cell.gfx.IGfxBridge;
import com.cell.gfx.IImage;
import com.cell.io.DefaultIODispatcher;
import com.cell.io.IODispatcher;

public class CAppBridge implements IAppBridge, IGfxBridge
{
	public static void init()
	{
		CObject.initSystem(new CStorage("_default"), new CAppBridge());
	}
	
	private Hashtable<String, String>	Propertys = new Hashtable<String, String>();
	
	private ClassLoader					m_ClassLoader;
	
	private Class<?>					m_RootClass;
	
	private Applet						m_applet;
	
	private IODispatcher				m_IO;
	
	public CAppBridge()
	{
		this(CAppBridge.class.getClassLoader(), CAppBridge.class);
	}
	
	public CAppBridge(ClassLoader classloader, Class<?> rootclass)
	{
		this(classloader, rootclass, null);
	}
	
	public CAppBridge(ClassLoader classloader, Class<?> rootclass, Applet applet)
	{
		if (classloader!=null) {
			m_ClassLoader = classloader;
		}else{
			m_ClassLoader = this.getClass().getClassLoader();
		}
		
		if (rootclass!=null) {
			m_RootClass = rootclass;
		}else{
			m_RootClass = this.getClass();
		}
		
		m_applet = applet;
		
		m_IO = createIO();
		if (CObject.verbos)
		System.out.println("CAppBridge init\n" +
				"\t[" + m_ClassLoader.getClass().getCanonicalName() + "]\n" +
				"\t[" + m_RootClass.getCanonicalName() + "]" +
				"\t[" + m_IO.getClass().getCanonicalName() + "]"
				);
	}
	
	//
	
	//
	public Thread createTempThread() {
		return new Thread("Game-Thread");
	}
	public Thread createTempThread(Runnable run) {
		return new Thread(run,"Game-Thread");
	}
	public Thread createServiceThread() {
		return new Thread("Game-Thread");
	}
	public Thread createServiceThread(Runnable run) {
		return new Thread(run,"Game-Thread");
	}

	public ClassLoader getClassLoader() {
		return m_ClassLoader;
	}
	
	/**
	 * 子类可覆盖
	 * @return
	 */
	protected IODispatcher createIO() {
		return new CIODispatcher();
	}
	
	@Override
	public IODispatcher getIO() {
		return m_IO;
	}

	public String getAppProperty(String key) {
		return Propertys.get(key);
	}
	
	public String setAppProperty(String key, String value) {
		return Propertys.put(key, value);
	}

	
	
//	public void addAppStateListener(IAppStateListener listener) {
//		StateListeners.add(listener);
//	}
//	public void removeAppStateListener(IAppStateListener listener) {
//		StateListeners.remove(listener);
//	}
//	public Collection<IAppStateListener> getAppListeners() {
//		return StateListeners;
//	}
	
	
	public IImage createImage(InputStream is) {
		return new CImage(is);
	}

	public IImage createImage(int w, int h) {
		CImage img = new CImage();
		img.createBuffer(w, h);
		return img;
	}
	
	public IImage createImage(int argb, int w, int h) {
		BufferedImage buf = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		int[] rgb = new int[w*h];
		for(int i=0;i<rgb.length;i++){
			rgb[i] = argb;
		}
		buf.setRGB(0, 0, w, h, rgb, 0, w);
		return new CImage(buf);
	}
	
	public IImage createImage(String filename) {
		return new CImage(filename);
	}

	//
	public void 	setClipboardText(String str){
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection text = new StringSelection(str);
        clipboard.setContents(text,null);
	}
	
	public String 	getClipboardText(){
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(this);
        DataFlavor flavor = DataFlavor.stringFlavor;
        if(contents.isDataFlavorSupported(flavor)){
            try{
                return (String)contents.getTransferData(flavor);
            }catch(Exception ee){ee.printStackTrace();}    
        }
        return "";
	}
	

	public String getMacAddr() {
		try {
			return InetAddress.getLocalHost().toString();
		} catch (java.io.IOException e) {
			System.err.println("IOException " + e.getMessage());
			return System.currentTimeMillis() + "";
		}
	}

	public int getPing(String host, int bufferSize)
	{
		int splitIndex = host.indexOf(":");
		if(splitIndex>=0)
		{
			String[] splits = host.split(":");
			host = splits[0];
		}
		
		int ping = -1;
		
		try {
			long startTime = System.currentTimeMillis();
			InetAddress address = InetAddress.getByName(host);
			if (address.isReachable(2000)){
				ping = (int)(System.currentTimeMillis() - startTime);
			}else{
				ping = -1;
			}
		} catch (UnknownHostException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}  
		
		return ping;
	}
	
	public void openBrowser(String url) 
	{
		try 
		{
//			if (m_applet!=null) 
//			{
//				m_applet.getAppletContext().showDocument(new URL(url), "_top");
//			}
//			else 
//			{
//				if (isWindowsPlatform()) {
//					openWinURL(url);
//				} else if (isMacPlatform()) {
//					openMacURL(url);
//				}
//			}
			Desktop.getDesktop().browse(new URI(url));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
//	--------------------------------------------------------------------------------------------------
	
    private static final String WIN_ID = "Windows";
    private static final String MAC_ID = "Mac";
    
    public static boolean isWindowsPlatform() {
        String os = System.getProperty("os.name");
        if ( os != null && os.startsWith(WIN_ID))
            return true;
        else
            return false;
    }
    
    public static boolean isMacPlatform() {
        String os = System.getProperty("os.name");
        if ( os != null && os.startsWith(MAC_ID))
            return true;
        else
            return false;
    }
    
    public static void openWinURL(String url)
    {
    	try{
	    	String cmd = "rundll32 url.dll,FileProtocolHandler " + url;
	    	Runtime.getRuntime().exec(cmd);
	    }catch(Exception err){
	    	err.printStackTrace();
	    }
    }

    @SuppressWarnings("unchecked")
	public static void openMacURL(String url) {
        try{
            Class MRJFileUtils = Class.forName("com.apple.mrj.MRJFileUtils");
            Method openMethod = MRJFileUtils.getDeclaredMethod("openURL", new Class[] {String.class});
            //String dst = formatString(url);
            openMethod.invoke(MRJFileUtils,new Object[]{url});
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

//	--------------------------------------------------------------------------------------------------

    public static String formatString(String str) {
        String retString="";
        String protocol = "";
        String host = "";
        String path = "";
        
        try {
            java.net.URL url = new java.net.URL(str);
            protocol = url.getProtocol();
            host = url.getHost();
            path = url.getPath();
        } catch (Exception ex) {
            path = str;
        }
        
        for(int i = 0; i < path.length(); i++) {
            if(path.charAt(i) == ' ') {
                retString += "%20";
            } else if(path.charAt(i) == '.') {
                retString += "%2E";
            } else {
                retString += path.substring(i, i + 1);
            }
        }
        
        if (!protocol.equals("")) {
            retString = protocol + "://" + host + retString;
        } else {
            retString = host + retString;
        }
        
        return retString ;
    } 

	protected class CIODispatcher extends DefaultIODispatcher
	{
		@Override
		protected InputStream getJarResource(String file) {
			InputStream is = null;
			if (is == null) {
				is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
			}
			if (is == null) {
				is = m_ClassLoader.getResourceAsStream(file);
			}
			if (is == null) {
				is = m_RootClass.getResourceAsStream(file);
			}
			if (is == null) {
				is = m_RootClass.getClassLoader().getResourceAsStream(file);
			}
			return is;
		}
	}
	
    
    
    

}
