package com.cell.util.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.cell.CIO;


final public class ZipNodeManager
{
//	---------------------------------------------------------------------------------------------------------------
	
	public static <T extends ZipNode> void saveAll(
			OutputStream outputstream,
			Collection<T> list, 
			ZipStreamFilter filter) throws Exception
	{
		ZipOutputStream zip_out = new ZipOutputStream(outputstream);
		try {
//			zip_out.setMethod(ZipOutputStream.STORED);
			ZipEntry entry = new ZipEntry(".info/info.properties");
			entry.setTime(0);
			zip_out.putNextEntry(entry);
			String info = 
				"filter = " + filter.getClass().getName() + "\n" + 
				"count  = " + list.size() + "\n";
			zip_out.write(info.getBytes());
			
			for (T object : list) {
				save(zip_out, object, filter);
			}
		} catch(Throwable ex) {
			ex.printStackTrace();
		} finally{
			try {
				zip_out.close();
			} catch (IOException e) {}
		}
	}

	
	public static <T extends ZipNode> Vector<T> loadAll(
			InputStream inputstream,
			Class<T> type, 
			ZipStreamFilter filter) throws Exception
	{
		Vector<T> ret = new Vector<T>(20);
		ZipInputStream zip_in = new ZipInputStream(inputstream);
		try{
			ZipEntry entry =  zip_in.getNextEntry();
			try{
				String info = new String(ZipUtil.readBytes(zip_in));
//				System.out.println(info);
			}catch(Exception err){}
			
			while ((entry = zip_in.getNextEntry()) != null) {
				T object = load(zip_in, entry, type, filter);
				if (object != null) {
					ret.add(object);
				}
			}
		}finally{
			try {
				zip_in.close();
			} catch (IOException e) {}
		}
		return ret;
	}

//	---------------------------------------------------------------------------------------------------------------
	
	static public <T extends ZipNode> T load(
			ZipInputStream zip_in,
			ZipEntry entry, 
			Class<T> type, 
			ZipStreamFilter filter)
	{
		T ret = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(ZipUtil.readBytes(zip_in));
			ret = type.cast(filter.readNode(bais, type));
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return ret;
	}
	
	

	static ZipEntry save(
			ZipOutputStream zip_out, 
			ZipNode node, 
			ZipStreamFilter filter)
	{
		ZipEntry entry = new ZipEntry(node.getEntryName());
		try{
			entry.setTime(0);
			zip_out.putNextEntry(entry);
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
			filter.writeNode(baos, node);
			zip_out.write(baos.toByteArray());
		} catch(Exception err){
			err.printStackTrace();
		}
		return entry;
	}
}
