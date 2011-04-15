package com.cell.util.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.io.CFile;
import com.cell.util.Pair;
import com.cell.util.StringFilters;

public class ZipUtil 
{
	static public ByteArrayOutputStream packFiles(ArrayList<File> files, long time)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zip_out = new ZipOutputStream(baos);
		try{
			for (File file : files) {
				byte[] data = CFile.readData(file);
				if (data != null) {
					ZipEntry entry = new ZipEntry(file.getName());
					entry.setTime(time);
					try{
						zip_out.putNextEntry(entry);
						zip_out.write(data);
					} catch(Exception err){
						err.printStackTrace();
					}
				}
			}
		} finally {
			try {
				zip_out.close();
			} catch (IOException e) {}
		}
		return baos;
	}
	
	static public ByteArrayOutputStream packFile(File file, long time)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zip_out = new ZipOutputStream(baos);
		try{
			byte[] data = CFile.readData(file);
			if (data != null) {
				ZipEntry entry = new ZipEntry(file.getName());
				entry.setTime(time);
				try{
					zip_out.putNextEntry(entry);
					zip_out.write(data);
				} catch(Exception err){
					err.printStackTrace();
				}
			}
		} finally {
			try {
				zip_out.close();
			} catch (IOException e) {}
		}
		return baos;
	}

	static public ByteArrayOutputStream packStreams(ArrayList<Pair<InputStream, String>> files, long time)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zip_out = new ZipOutputStream(baos);
		try{
			for (Pair<InputStream, String> file : files) {
				byte[] data = CIO.readStream(file.getKey());
				if (data != null) {
					ZipEntry entry = new ZipEntry(file.getValue());
					entry.setTime(time);
					try{
						zip_out.putNextEntry(entry);
						zip_out.write(data);
					} catch(Exception err){
						err.printStackTrace();
					}
				}
			}
		} finally {
			try {
				zip_out.close();
			} catch (IOException e) {}
		}
		return baos;
	}
	
	static public ByteArrayOutputStream packStream(InputStream is, String name, long time)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zip_out = new ZipOutputStream(baos);
		try{
			byte[] data = CIO.readStream(is);
			if (data != null) {
				ZipEntry entry = new ZipEntry(name);
				entry.setTime(time);
				try{
					zip_out.putNextEntry(entry);
					zip_out.write(data);
				} catch(Exception err){
					err.printStackTrace();
				}
			}
		} finally {
			try {
				zip_out.close();
			} catch (IOException e) {}
		}
		return baos;
	}
	
	/**
	 * 该方法将关闭刘
	 * @param input
	 * @return
	 */
	static public Map<String, ByteArrayInputStream> unPackFile(InputStream input)
	{
		HashMap<String, ByteArrayInputStream> inputs = new HashMap<String, ByteArrayInputStream>();
		
		ZipInputStream zip_in = new ZipInputStream(input);
		
		try{
			while (true) {
				try {
					ZipEntry entry =  zip_in.getNextEntry();
					if (entry != null) {
						ByteArrayInputStream bais = new ByteArrayInputStream(readBytes(zip_in));
						inputs.put(entry.getName(), bais);
					} else {
						break;
					}
				} catch (Throwable ex) {
					ex.printStackTrace();
					break;
				}
			}
		}finally{
			try {
				zip_in.close();
			} catch (IOException e) {}
		}
		
		return inputs;
	}
	
	static public byte[] readBytes(ZipInputStream is) throws IOException {
		ByteArrayOutputStream data = new ByteArrayOutputStream(8192);
		byte[] buffer = new byte[8192];
		int size;
		while (is.available() > 0) {
			size = is.read(buffer);
			if (size > 0) {
				data.write(buffer, 0, size);
			} else {
				break;
			}
		}
		return data.toByteArray();
	}
	
	static public void main(String[] args)
	{
		try
		{
			HashMap<String, String> commands = new HashMap<String, String>(args.length);
			for (String cmd : args) {
				if (cmd.startsWith("-")) {
					commands.put(cmd.trim(), cmd.trim());
				}
			}
			
			if (args[0].equals("A")) 
			{
				File src = new File(args[args.length-2]).getCanonicalFile();
				File out = new File(args[args.length-3]).getCanonicalFile();
//				Pattern parttern = Pattern.compile(args[args.length-1]);
				StringFilters pattern = new StringFilters(args[args.length-1]);
				zipFiles(src, out, pattern, commands.containsKey("-verbos"));
			} 
			else if (args[0].equals("E")) 
			{
				printUsage();
			}
			else
			{
				printUsage();
			}
		} 
		catch (Exception err) {
			err.printStackTrace();
			printUsage();
		}
	}
	
	static private void printUsage()
	{
		String usage = 
			"A [-开关] <输出文件> <输入文件> [文件匹配正则表达式]\n" +
			"	[开关] -R - 连同子文件夹\n" +
			"	[文件匹配正则表达式]\n" + 
			StringFilters.usage("	");
		System.out.println(usage);
	}
	
	/**
	 * 将src所有符合标准的文件都压缩到out
	 * @param src
	 * @param out
	 * @param parttern
	 * @throws Exception
	 */
	static public void zipFiles(File src, File out, StringFilters pattern, boolean verbos) throws Exception
	{
		src = src.getCanonicalFile();
		out = out.getCanonicalFile();
		
		if (!src.isHidden()) {
			String root = "";
			if (src.isDirectory()) {
				root = src.getCanonicalPath();
			} else if (src.isFile()) {
				root = src.getParentFile().getCanonicalPath();
			}
			LinkedHashMap<String, byte[]> entrys = new LinkedHashMap<String, byte[]>();
			zipFiles(src, pattern, root, entrys, verbos);
			if (!entrys.isEmpty()) {				
				out.createNewFile();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ZipOutputStream zos = new ZipOutputStream(baos);
				for (Entry<String, byte[]> e : entrys.entrySet()) {
					ZipEntry ze = new ZipEntry(e.getKey());
					ze.setTime(0);
					zos.putNextEntry(ze);
					zos.write(e.getValue());
				}
				zos.close();
				CFile.writeData(out, baos.toByteArray());
			}
		}
	}
	
	static public void zipFiles(File src, StringFilters pattern, String root, LinkedHashMap<String, byte[]> entrys, boolean verbos) throws Exception
	{
		if (!src.isHidden()) {
			if (pattern.accept(src.getName())) {
				if (src.isFile()) {
					pushFileEntry(src, root, entrys, verbos);
				} else if (src.isDirectory()) {
					for (File sub : src.listFiles()) {
						zipFiles(sub, pattern, root, entrys, verbos);
					}
				}
			}
		}
	}
	
	static private void pushFileEntry(File src, String root, LinkedHashMap<String, byte[]> entrys, boolean verbos) throws Exception
	{
		String name = src.getCanonicalPath();
		name = CUtil.replaceString(name, root, "", 1);
		name = name.replaceAll("\\\\", "/");
		while (name.startsWith("/")) {
			name = name.substring(1);
		}
		byte[] data = CFile.readData(src);
		entrys.put(name, data);
		if (verbos) {
			System.out.println("put : " + CUtil.snapStringRightSize(data.length + "(bytes)", 22, ' ') + " "+  name);
		}
	}
	
	/**
	 * @param is, 该方法不会关闭流
	 * @return
	 */
	static public TreeMap<String, byte[]> unzipAll(InputStream is) {
		try {
			TreeMap<String, byte[]> ret = new TreeMap<String, byte[]>();
			ZipInputStream zip_in = new ZipInputStream(is);
			for (ZipEntry e = zip_in.getNextEntry(); e != null; e = zip_in.getNextEntry()) {
				byte[] v = ZipUtil.readBytes(zip_in);
				ret.put(e.getName(), v);
			}
			return ret;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	
}
