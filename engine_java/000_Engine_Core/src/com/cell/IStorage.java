package com.cell;

public interface IStorage 
{
	final public static String Separator = System.getProperty("file.separator");
	
	final static public int FILE_FAILE	= -1;
	final static public int FILE_OK		=  1;
	
	
	final static public int MODE_R 		= 1;
	final static public int MODE_W		= 2;
	final static public int MODE_RW		= 3;
	final static public int MODE_CREATE = 0;
	
	public int		save(String name, int id, byte[] datas);
	public int		delete(String name, int id);
	public byte[]	load(String name, int id);
	public int		getIdCount(String name);
	
	public int		root_save	(String name, byte[] datas);
	public int		root_delete	(String name);
	public byte[]	root_load	(String name);
	public boolean	root_exist	(String name);
	public String	root_path	(String name);
	
	
	public byte[] syncReadBytesFromURL(String url, int timeOut);
	
	public boolean beginReadBytesFromURL(String url, IReadListener listener, int timeOut);
	
	public interface IReadListener
	{
		final public static int ACTION_COMPLETE 	= 1;
		final public static int ACTION_ERROR 		= -1;
		public void notifyReadAction(int type, String srcURL, byte[] data);
	}
	
}
