package com.cell.rpg.io;

import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.Vector;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.persistance.PersistanceManager;
import com.cell.rpg.RPGObject;



/**
 * 在构造时将所有对象读入，运行时动态你的添加删除对象，最后调用saveAll方法将所有对象存储到文件
 * @author WAZA
 * @param <T>
 */
public class RPGObjectMap<T extends RPGObject> extends Hashtable<String, T> //implements ZipStreamFilter
{
//	final public Class<T>	type;
//	final public File		zip_dir;
//	final public File		zip_info;
//
//	public RPGObjectMap(Class<T> type, File zip_dir) 
//	{
//		this.type 		= type;
//		this.zip_dir	= zip_dir;
//		this.zip_info	= new File(zip_dir, type.getSimpleName().toLowerCase()+".list");
//
//		loadAll();
//	}
//	
////	-----------------------------------------------------------------------------------------------------------------------
//
//	
//	synchronized public void loadAll()
//	{
//		if (zip_dir.exists()) 
//		{
//			String[] entrys = CIO.readAllLine(zip_info.getPath(), "UTF-8");
//			
//			for (String entry : entrys) {
//				File f = new File(zip_dir, entry.trim());
//				if (f.exists()) {
//					T t = readNode(f.getPath(), type);
//					if (t!=null) {
//						put(t.id, t);
//					}
//				}
//			}
//		}
//	}
//	
//	synchronized public void saveAll()
//	{
//		if (!zip_dir.exists()) {
//			zip_dir.mkdirs();
//		}
//		
//		Vector<String> keys = new Vector<String>(keySet());
//		CUtil.sort(keys, CUtil.getStringCompare());
//		StringBuffer info = new StringBuffer();
//		for (String k : keys) {
//			T v = get(k);
//			if (writeNode(v, new File(zip_dir, v.getEntryName()))) {
//				info.append(v.getEntryName()+"\n");
//			}
//		}
//		
//		com.cell.io.CFile.writeText(zip_info, info.toString(), "UTF-8");
//	}


//	------------------------------------------------------------------------------------------------------------------------------

	private static final long serialVersionUID = 1L;
	private static PersistanceManager	persistance_manager;
	
	public static void setPersistanceManagerDriver(String driver_name) throws Exception {
		Class<?> cls = Class.forName(driver_name);
		persistance_manager = (PersistanceManager)cls.newInstance();
	}

//	------------------------------------------------------------------------------------------------------------------------------

	public static<T extends RPGObject> T readNode(InputStream is, String xml_file, Class<T> type) {
		try{
			String 				xml 	= CIO.stringDecode(CIO.readStream(is), CObject.ENCODING);
			StringReader 		reader 	= new StringReader(xml);
			ObjectInputStream 	ois 	= persistance_manager.createReadStream(reader);
			try{
				T ret = type.cast(ois.readObject());
				if (ret != null) {
					ret.onReadComplete(ret);
					Vector<RPGSerializationListener> rlisteners = ret.getRPGSerializationListeners();
					if (rlisteners != null) {
						for (RPGSerializationListener l : rlisteners) {
							l.onReadComplete(ret);
						}
					}
				}
				return ret;
			}finally{
				ois.close();
			}
		} catch(Throwable ex) {
			System.err.println("read node error : " + type + " : " + xml_file);
			ex.printStackTrace();
		}
		return null;
	}
	
	public static<T extends RPGObject> T readNode(String xml_file, Class<T> type) {
		return readNode(CIO.getInputStream(xml_file), xml_file, type);
	}
	
	public static<T extends RPGObject> String writeNode(String xml_file, T node) {
		try {
			StringWriter 		writer 	= new StringWriter(1024);
			ObjectOutputStream 	oos 	= persistance_manager.createWriteStream(writer);
			try{
				Vector<RPGSerializationListener> wlisteners = node.getRPGSerializationListeners();
				if (wlisteners!=null) {
					for (int i=wlisteners.size()-1; i>=0; --i) {
						wlisteners.get(i).onWriteBefore(node);
					}
				}
				node.onWriteBefore(node);
				oos.writeObject(node);
			}finally{
				oos.close();
			}
			String xml = writer.toString();
			return xml;
		} catch(Throwable ex) {
			System.err.println("write node error : " + node + " : " + xml_file);
			ex.printStackTrace();
		}
		return null;
	}
}
