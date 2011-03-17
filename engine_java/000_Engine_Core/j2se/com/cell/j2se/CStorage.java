package com.cell.j2se;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.IStorage;
import com.cell.IStorage.IReadListener;


public class CStorage implements IStorage
{
	File rms_file;

	public CStorage(String gamename)
	{
		try
		{
			String usrHome = System.getProperty("user.home");
			File home_dir = new File(usrHome);
			
			if (home_dir.exists())
			{
				rms_file = new File(usrHome + Separator + ".cstorage" + Separator + gamename);
			}
			else
			{
				String base = File.separator;
				
				File[] roots = File.listRoots();
				
				for(int i=roots.length-1; i>=0; i--){
					if (roots[i].canWrite()){
						base = roots[i].getPath();
						break;
					}
				}
				
				rms_file = new File(base + Separator + ".cstorage" + Separator + gamename);
			}
			
//			if (!rms_file.exists()) {
//				rms_file.mkdirs();
//			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			rms_file = new File("./");
		}
		
		if (CObject.verbos)
			System.out.println(
					"CStorage init\n" + 
					"\t[" + rms_file.getPath()+ "]");
		
				
	}
	

	String getFilePath(String name, int id)
	{
		String path = rms_file.getPath() + Separator  + name + Separator + id + ".rms";
		return path;
	}
	
	String getFileDir(String name)
	{
		String path = rms_file.getPath() + Separator  + name ;
		return path;
	}
	
	String getRootFilePath(String name)
	{
		String path = rms_file.getPath() + Separator  + name;
		return path;
	}
	
	
	int getFileCount(String name)
	{
		try {
			String path = rms_file.getPath() + Separator + name + Separator + "count";
			java.io.File file = new java.io.File(path);
			if (file.exists()) {
				FileInputStream fs = new FileInputStream(file);
				try {
					int count = fs.read();
					return count;
				} finally {
					fs.close();
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return 0;
	}
	
	int setFileCount(String name, int count)
	{
		try {
			String path = rms_file.getPath() + Separator + name;
			java.io.File file = new java.io.File(path);
			if (!file.exists()) file.mkdir();
		} catch (Exception err) {
			err.printStackTrace();
		}
		
		try {
			String path = rms_file.getPath() + Separator + name + Separator + "count";
			java.io.File file = new java.io.File(path);
			if (!file.exists()) file.createNewFile();
			FileOutputStream fs = new FileOutputStream(file);
			try {
				fs.write(count);
			} finally {
				fs.close();
			}
			return count;
		} catch (Exception err) {
			err.printStackTrace();
		}

		return -1;
	}

	
	
	public byte[] load(String name, int id)
	{
		byte[] SaveData = null;
		try {
			java.io.File file = new java.io.File(getFilePath(name, id));
			if (file.exists()) {
				FileInputStream fs = new FileInputStream(file);
				try {
					int dataSize = fs.available();
					int count = 0, i;
					SaveData = new byte[dataSize];
					while (true) {
						i = fs.read(SaveData, count, dataSize - count);
						if (i <= 0)
							break;
						count += i;
					}
				} finally {
					fs.close();
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return SaveData;
	}

	public int save(String name,int id, byte[] datas) 
	{
		try 
		{
			java.io.File file = new java.io.File(getFileDir(name));
			if (!file.exists()){
				file.mkdirs();
			}
			
			file = new java.io.File(getFilePath(name, id));

			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fs = new FileOutputStream(file);
			try {
				fs.write(datas);
			} finally {
				fs.close();
			}

			int count = getFileCount(name);
			if (count < id + 1)
				setFileCount(name, id + 1);

			return FILE_OK;
			
		} catch (Exception err) {
			err.printStackTrace();
		}

		return FILE_FAILE;
	
	}
	
	public int delete(String name,int id) 
	{
		try 
		{
			java.io.File file = new java.io.File(getFilePath(name, id));

			if (file.exists()) {
				file.delete();
				return FILE_OK;
			}

		} catch (Exception err) {
			err.printStackTrace();
		}
		
		return FILE_FAILE;
	}
	
	
	
	@Override
	public int root_delete(String name) {
		try {
			java.io.File file = new java.io.File(getRootFilePath(name));
			if (file.exists()) {
				file.delete();
				return FILE_OK;
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return FILE_FAILE;
	}
	
	@Override
	public byte[] root_load(String name) {
		byte[] SaveData = null;
		try {
			java.io.File file = new java.io.File(getRootFilePath(name));
			if (file.exists()) {
				FileInputStream fs = new FileInputStream(file);
				try {
					SaveData = CIO.readStream(fs);
				} finally {
					fs.close();
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return SaveData;
	}
	
	@Override
	public int root_save(String name, byte[] datas) {
		try {
			java.io.File file = new java.io.File(getRootFilePath(name));
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fs = new FileOutputStream(file);
			try {
				fs.write(datas);
			} finally {
				fs.close();
			}
			return FILE_OK;
		} catch (Exception err) {
			err.printStackTrace();
		}

		return FILE_FAILE;

	}
	
	@Override
	public boolean root_exist(String name) {
		java.io.File file = new java.io.File(getRootFilePath(name));
		return file.exists();
	}
	
	@Override
	public String root_path(String name) {
		return rms_file.getPath() + Separator  + name;
	}
	
	public int getIdCount(String name) 
	{
		try {
			int count = getFileCount(name);
			return count;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return -1;
	}

	// String url = "http://gametiler.01www.com/game/serverlist.txt";
	public byte[] syncReadBytesFromURL(String url, int timeOut)
	{
		URL Url = null;
		try {
			Url = new URL(url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return null;
		}

		URLConnection c = null;
		InputStream is = null;
		try 
		{
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
			}
			else if (len == 0) {
				return new byte[0];
			}
			
		} catch (IOException err){
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

	
	public boolean beginReadBytesFromURL(final String url,final IReadListener listener, final int timeOut)
	{
		try{
			Thread t = new Thread(
					new Runnable(){
						public void run(){
							byte data[] = syncReadBytesFromURL(url, timeOut);
							if(listener!=null){
								listener.notifyReadAction(
										data!=null?IReadListener.ACTION_COMPLETE:IReadListener.ACTION_ERROR, 
										url, 
										data
										);
							}
						}
					}
			);
			t.start();
			return true;
		}catch(Exception err){
			err.printStackTrace();
			return false;
		}	
	}
		
}
