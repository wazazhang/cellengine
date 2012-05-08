package com.g2d.studio.cell.gameedit;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.io.BigIOSerialize;
import com.cell.io.CFile;
import com.cell.util.Pair;
import com.g2d.awt.util.Tools;
import com.g2d.studio.StudioConfig;

public class BuildProcess 
{
	final private File dir;
	final private File g2d_root;
	
	public BuildProcess(File dir, File g2d_root) throws IOException
	{
		this.dir = dir.getCanonicalFile();
		this.g2d_root = g2d_root.getCanonicalFile();
	}

	/**
	 * 得到当前工作目录
	 * @return
	 */
	public File getDir() {
		return dir;
	}
	
	/**
	 * 得到G2D工程目录
	 * @return
	 */
	public File getG2DRoot() {
		return g2d_root;
	}
	
	/**
	 * 执行操作系统指令
	 * @param cmd
	 * @return
	 */
	public Object exec(String cmd) {
		return exec(cmd, 0);
	}
	
	/**
	 * 执行操作系统指令，并等待超时
	 * @param cmd
	 * @param timeout
	 * @return
	 */
	public Object exec(String cmd, int timeout) {
		try {	
			Process p = Runtime.getRuntime().exec(cmd, CUtil.getEnv(), dir);
			WaitProcessTask task = new WaitProcessTask(p, Math.max(timeout, 60000));
			task.start();
			try {
				p.waitFor();
				Thread.yield();
			} finally {
				synchronized (task) {
					task.notifyAll();
				}
			}
			byte[] out = CIO.readStream(p.getInputStream());
			return (new String(out));
		} catch (Exception err) {
			err.printStackTrace();
			return null;
		}
	}
	
	
	
	/**
	 * 删除文件或整个目录
	 * @param child_file
	 */
	public BuildProcess deleteIfExists(String child_file) 
	{
		try {
			File file = new File(dir, child_file).getCanonicalFile();
			deleteIfExists(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	private void deleteIfExists(File file) 
	{
		if (file != null && file.exists()) {
			if (file.isDirectory()) {
				for (File sub : file.listFiles()) {
					deleteIfExists(sub);
				}
			}
			file.delete();
		}
	}
	
	
//	----------------------------------------------------------------------------------------------------------------------

	

	/**
	 * 工作目录中。
	 * 获得该目录下所有子文件信息
	 * @param root			文件夹
	 * @param regex			文件名
	 * @param entry_prefix	导出子节点文件名前缀 : entry_prefix + filename
	 * @return Map<String, byte[]>
	 * @throws Exception
	 */
	public LinkedHashMap<String, byte[]> getEntrys(String root, String regex, String entry_prefix)
	{
		LinkedHashMap<String, byte[]> packs = new LinkedHashMap<String, byte[]>();
		getEntrys(root, regex, entry_prefix, packs);
		return packs;
	}
	
	/**
	 * 工作目录中。
	 * 获得该目录下所有子文件信息
	 * @param root			文件夹
	 * @param regex			文件名
	 * @param entry_prefix	导出子节点文件名前缀 : entry_prefix + filename
	 * @param Map<String, byte[]>
	 * @throws Exception
	 */
	public BuildProcess getEntrys(String root, String regex, String entry_prefix, Map<String, byte[]> packs)
	{
		File 	root_dir	= new File(dir, root);
		Pattern	pattern		= Pattern.compile(regex);
		if (root_dir.exists() && root_dir.isDirectory()) {
			for (File file : root_dir.listFiles()) {
				if (file.isFile() && pattern.matcher(file.getName()).find()) {
					packs.put(entry_prefix + file.getName(), CFile.readData(file));
				}
			}
		}
		return this;
	}
	
	
	/**
	 * 将所有entry打包成zip格式
	 * @param entrys
	 * @param out
	 * @return
	 */
	public File pakEntrys(Map<String, byte[]> entrys, String out) 
	{
		try 
		{
			File					output	= new File(dir, out).getCanonicalFile();
			ByteArrayOutputStream	baos	= new ByteArrayOutputStream();
			ZipOutputStream			zip_out	= new ZipOutputStream(baos);
			try {
				for (Entry<String, byte[]> pak : entrys.entrySet()) {
					ZipEntry entry = new ZipEntry(pak.getKey());
					try {
						entry.setTime(0);
						zip_out.putNextEntry(entry);
						zip_out.write(pak.getValue());
					} catch(Exception err){
						err.printStackTrace();
					}
				}
			} finally {
				try {
					zip_out.close();
				} catch (IOException e) {}
				try {
					baos.close();
				} catch (IOException e) {}
			}
			CFile.writeData(output, baos.toByteArray());
			return output;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 工作目录中。
	 * 将该目录下符合的文件都打包，自定义子文件的前缀
	 * @param root
	 * @param regex
	 * @param out
	 * @param prefix 子文件前缀
	 * @return
	 */
	public File pakFiles(String root, String regex, String out, String prefix)
	{
		try {
			LinkedHashMap<String, byte[]> entrys = getEntrys(root, regex, prefix);
			if (!entrys.isEmpty()) {
				return pakEntrys(entrys, out);
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
//	------------------------------------------------------------------------------------------------------------
	
	

	/**
	 * 工作目录中。
	 * 导出一份缩略图
	 * @param cpj_file_name
	 */
	public BuildProcess saveImageThumb(String src_file, String dst_file, float scale) {
		try {
			File jpg = new File(dir, src_file);
			if (jpg.exists()) {
				BufferedImage src = Tools.readImage(jpg.getPath());
				BufferedImage tag = new BufferedImage(
						(int)(src.getWidth() *scale), 
						(int)(src.getHeight()*scale), 
						BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(
						src, 0, 0, 
						tag.getWidth(),
						tag.getHeight(), null);
				File thumb_file = new File(dir, dst_file);
				Tools.writeImage(thumb_file.getPath(), "jpg", tag);
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return this;
	}

	/**
	 * 转换图片成MASK信息
	 * @param packs
	 * @return
	 */
	public BuildProcess convertImageMaskEntrys(Map<String, byte[]> packs)
	{
		for (String k : packs.keySet())
		{
			byte[] data = packs.get(k);
			try {
				BufferedImage bi = Tools.readImage(new ByteArrayInputStream(data));
				if (bi != null) {
					data = Tools.encodeImageMask(bi);
				}
			} catch (Throwable tx) {
				tx.printStackTrace();
			} finally {
				packs.put(k, data);
			}
		}
		return this;
	}

	/**
	 * 工作目录中。
	 * 获得该目录下所有子文件信息，并转换成ImageMask
	 * @param root			文件夹
	 * @param regex			文件名
	 * @param entry_prefix	导出子节点文件名前缀 : entry_prefix + filename
	 * @param Map<String, byte[]>
	 * @throws Exception
	 */
	public BuildProcess getImageMaskEntrys(String root, String regex, String entry_prefix, Map<String, byte[]> packs)
	{
		File 	root_dir	= new File(dir, root);
		Pattern	pattern		= Pattern.compile(regex);
		if (root_dir.exists() && root_dir.isDirectory()) {
			for (File file : root_dir.listFiles()) {
				if (file.isFile() && pattern.matcher(file.getName()).find()) {
					try {
						byte[] data = CFile.readData(file);
						BufferedImage bi = Tools.readImage(new ByteArrayInputStream(data));
						if (bi != null) {
							data = Tools.encodeImageMask(bi);
							packs.put(entry_prefix + file.getName(), data);
						}
					} catch (Throwable tx) {
						tx.printStackTrace();
					}
				}
			}
		}
		return this;
	}
	
	/**
	 * 工作目录中。
	 * 将该目录下符合的文件都打包，自定义子文件的前缀
	 * @param root
	 * @param regex
	 * @param out
	 * @param prefix 子文件前缀
	 * @return
	 */
	public File pakImageMaskFiles(String root, String regex, String out, String prefix)
	{
		try {
			LinkedHashMap<String, byte[]> entrys = getEntrys(root, regex, prefix);
			if (!entrys.isEmpty()) {
				convertImageMaskEntrys(entrys);
				return pakEntrys(entrys, out);
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
}
