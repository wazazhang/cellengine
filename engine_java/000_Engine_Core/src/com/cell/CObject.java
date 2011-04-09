package com.cell;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import com.cell.io.DefaultIODispatcher;
import com.cell.io.IODispatcher;

/**
 * @author WAZA
 * @since 2006-11-28
 * @version 1.0
 */
public class CObject {
	static class NullStorage implements IStorage {

		public NullStorage() {}

		@Override
		public int root_delete(String name) {
			return 0;
		}
		@Override
		public int root_save(String name, byte[] datas) {
			return 0;
		}
		@Override
		public boolean root_exist(String name) {
			return false;
		}
		@Override
		public byte[] root_load(String name) {
			return null;
		}
		@Override
		public String root_path(String name) {
			return null;
		}
		public byte[] load(String name, int id) {
			return null;
		}
		public int save(String name, int id, byte[] datas) {
			return FILE_FAILE;
		}
		public int delete(String name, int id) {
			return FILE_FAILE;
		}
		public int getIdCount(String name) {
			return 0;
		}
		public byte[] syncReadBytesFromURL(String url, int timeOut) {
			URL Url = null;
			try {
				Url = new URL(url);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
				return null;
			}
			URLConnection c = null;
			InputStream is = null;
			try {
				c = Url.openConnection();
				c.setConnectTimeout(timeOut);
				c.setReadTimeout(timeOut);
				c.connect();
				is = c.getInputStream();
				int len = (int) c.getContentLength();
				if (len > 0) {
					int actual = 0;
					int bytesread = 0;
					byte[] data = new byte[len];
					while ((bytesread != len) && (actual != -1)) {
						actual = is.read(data, bytesread, len - bytesread);
						bytesread += actual;
					}
					is.close();
					return data;
				} else if (len == 0) {
					return new byte[0];
				}
			} catch (IOException err) {
				err.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return null;
		}

		public boolean beginReadBytesFromURL(final String url,
				final IReadListener listener, final int timeOut) {
			try {
				Thread t = new Thread(new LoadTask(url, listener, timeOut));
				t.start();
				return true;
			} catch (Exception err) {
				err.printStackTrace();
				return false;
			}
		}

		class LoadTask implements Runnable {
			final String url;
			final IReadListener listener;
			final int timeOut;

			public LoadTask(final String url, final IReadListener listener,
					final int timeOut) {
				this.url = url;
				this.listener = listener;
				this.timeOut = timeOut;
			}

			public void run() {
				byte data[] = syncReadBytesFromURL(url, timeOut);
				if (listener != null) {
					if (data != null) {
						listener.notifyReadAction(
								IReadListener.ACTION_COMPLETE, url, data);
					} else {
						listener.notifyReadAction(IReadListener.ACTION_ERROR,
								url, data);
					}
				}
			}
		}
	}

	static class NullAppBridge implements IAppBridge
	{
		final Hashtable<String, String> Propertys = new Hashtable<String, String>();
		final ClassLoader m_ClassLoader;
		final Class<?> m_RootClass;
		final DefaultIODispatcher io = new DefaultIODispatcher() {
			protected InputStream getJarResource(String file) {
				InputStream is = m_ClassLoader.getResourceAsStream(file);
				if (is == null) {
					is = m_RootClass.getResourceAsStream(file);
				}
				if (is == null) {
					is = m_RootClass.getClassLoader().getResourceAsStream(file);
				}
				if (is == null) {
					is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
				}
				return is;
			}
		};
		public NullAppBridge() {
			m_ClassLoader = CObject.class.getClass().getClassLoader();
			m_RootClass = CObject.class.getClass();
		}

		public Thread createTempThread() {
			return new Thread("Temp-Thread");
		}

		public Thread createTempThread(Runnable run) {
			return new Thread(run, "Temp-Thread");
		}

		public Thread createServiceThread() {
			return new Thread("Service-Thread");
		}

		public Thread createServiceThread(Runnable run) {
			return new Thread(run, "Service-Thread");
		}

		public ClassLoader getClassLoader() {
			return m_ClassLoader;
		}
		public IODispatcher getIO() {
			return io;
		}
		public String getAppProperty(String key) {
			return Propertys.get(key);
		}
		public String setAppProperty(String key, String value) {
			return Propertys.put(key, value);
		}
		public void setClipboardText(String str) {
			Clipboard clipboard = Toolkit.getDefaultToolkit()
					.getSystemClipboard();
			StringSelection text = new StringSelection(str);
			clipboard.setContents(text, null);
		}
		public String getClipboardText() {
			Clipboard clipboard = Toolkit.getDefaultToolkit()
					.getSystemClipboard();
			Transferable contents = clipboard.getContents(this);
			DataFlavor flavor = DataFlavor.stringFlavor;
			if (contents.isDataFlavorSupported(flavor)) {
				try {
					return (String) contents.getTransferData(flavor);
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
			return "";
		}
		public void openBrowser(String url) {
			try {
				Desktop.getDesktop().browse(new URI(url));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		public String getMacAddr() {
			try {
				return InetAddress.getLocalHost().toString();
			} catch (java.io.IOException e) {
				System.err.println("IOException " + e.getMessage());
				return System.currentTimeMillis() + "";
			}
		}
		public int getPing(String host, int bufferSize) {
			return -1;
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------

	static public String ProductVersion = "0.0.0";
	static public String ENCODING = "UTF-8";

	static IStorage Storage = new NullStorage();
	static IAppBridge AppBridge = new NullAppBridge();
	static Random Random = new Random();
	static Locale CurLocale = Locale.getDefault();

	static private String DateFormat = "YYYY-MM-DD hh:mm:ss";
	static private String[] DateFormats = new String[] { "YYYY", "MM", "DD",
			"W", "hh", "mm", "ss", };
	static private Date CurDate = new Date(System.currentTimeMillis());
	static private Calendar CurCalendar = Calendar.getInstance();

	// -------------------------------------------------------------------------------------------------------------------------

	public static boolean verbos = false;
	
	static public void initSystem(IStorage file, IAppBridge appBridge) {
		initSystem(file, appBridge, Locale.getDefault());
	}

	static public void initSystem(IStorage file, IAppBridge appBridge,
			Locale local) {
		setStorage(file);
		setAppBridge(appBridge);
		setCurLocale(local);
		CurDate = new Date(System.currentTimeMillis());
		CurCalendar = Calendar.getInstance(local);
		//		
		// if (getAppBridge() instanceof IGfxBridge) {
		// AScreen.setGfxAdapter((IGfxBridge)getAppBridge());
		// }
		//		
		TimeZone.getAvailableIDs();

		try {
			CurCalendar.setTime(CurDate);
		} catch (Exception err) {
			err.printStackTrace();
		}
		if (verbos) {
			System.out.println("CObject : System initialized !\n"
					+ "\tIStorage   = " + getStorage().getClass().getName() + "\n"
					+ "\tIAppBridge = " + getAppBridge().getClass().getName()
					+ "\n" +
					// "\tIGfxBridge = " + AScreen.getGfxAdapter() + "\n" +
					"\tLocale     = " + getCurLocale() + "\n" + "");
		}
	}
	
	public static String getEncoding() {
		return ENCODING;
	}

	// ------------------------------------------------------

	/**
	 * debug console print, System.out.print();</br>
	 * 
	 * @param str
	 */
	static public void print(String str) {
		System.out.print(str);
	}

	/**
	 * debug console println, System.out.println();</br>
	 * 
	 * @param str
	 */
	static public void println(String str) {
		System.out.println(str);
	}

	/**
	 * @param fmt
	 *            default is "YYYY-MM-DD hh:mm:ss"
	 */
	static public void setTimeFormat(String fmt) {
		DateFormat = fmt;

		DateFormats[0] = CUtil.getSameCharBlock(fmt, 0, 'Y');
		DateFormats[1] = CUtil.getSameCharBlock(fmt, 0, 'M');
		DateFormats[2] = CUtil.getSameCharBlock(fmt, 0, 'D');
		DateFormats[3] = CUtil.getSameCharBlock(fmt, 0, 'W');
		DateFormats[4] = CUtil.getSameCharBlock(fmt, 0, 'h');
		DateFormats[5] = CUtil.getSameCharBlock(fmt, 0, 'm');
		DateFormats[6] = CUtil.getSameCharBlock(fmt, 0, 's');

	}

	static public String getTimeFormat() {
		return DateFormat;
	}

	static public Calendar getCalendar(long timeInMillis) {
		CurDate.setTime(timeInMillis);
		CurCalendar.setTime(CurDate);
		return CurCalendar;
	}

	static public String timeToString(long timeInMillis) {
		String ret = DateFormat.toString();

		CurDate.setTime(timeInMillis);
		CurCalendar.setTime(CurDate);

		int[] dsts = new int[] { CurCalendar.get(Calendar.YEAR),
				CurCalendar.get(Calendar.MONTH) + 1,
				CurCalendar.get(Calendar.DAY_OF_MONTH),
				CurCalendar.get(Calendar.DAY_OF_WEEK),
				CurCalendar.get(Calendar.HOUR_OF_DAY),
				CurCalendar.get(Calendar.MINUTE),
				CurCalendar.get(Calendar.SECOND), };

		for (int i = 0; i < DateFormats.length; i++) {
			if (DateFormats[i] != null && DateFormats[i].length() > 0) {
				ret = CUtil.replaceString(ret, DateFormats[i], CUtil
						.intToSting(dsts[i], 10, DateFormats[i].length(), "0"));
			}
		}

		return ret;
	}

	static public String timesliceToStringHour(long timesliceInMillis) {

		long time = timesliceInMillis;

		int ss = (int) (time / 1000 % 60);
		int mm = (int) (time / 1000 / 60 % 60);
		int hh = (int) (time / 1000 / 60 / 60);

		String ret = hh + ":" + CUtil.intToSting(mm, 10, 2, "0") + ":"
				+ CUtil.intToSting(ss, 10, 2, "0");

		return ret;
	}

	public static void setStorage(IStorage storage) {
		Storage = storage;
	}

	public static IStorage getStorage() {
		return Storage;
	}

	public static void setAppBridge(IAppBridge appBridge) {
		AppBridge = appBridge;
	}

	public static IAppBridge getAppBridge() {
		return AppBridge;
	}

	public static void setRandom(Random random) {
		Random = random;
	}

	public static Random getRandom() {
		return Random;
	}

	public static void setCurLocale(Locale curLocale) {
		CurLocale = curLocale;
	}

	public static Locale getCurLocale() {
		return CurLocale;
	}

	// --------------------------------------------------------------------------------------------------------------------------------------

	public static <T> void storageSave(String key, T data) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(data);
			getStorage().root_save(key, baos.toByteArray());
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
			}
		}
	}

	public static <T> T storageLoad(String key, Class<T> type) {
		byte[] data = getStorage().root_load(key);
		if (data != null) {
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			try {
				ObjectInputStream ois = new ObjectInputStream(bais);
				Object o = ois.readObject();
				if (type.isInstance(o)) {
					return type.cast(o);
				}
			} catch (Exception err) {
				err.printStackTrace();
			} finally {
				try {
					bais.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	public static <T> T storageLoad(String key, Class<T> type, T default_value) {
		T ret = storageLoad(key, type);
		if (ret == null) {
			return default_value;
		}
		return ret;
	}

	// --------------------------------------------------------------------------------------------------------------------------------------

}
