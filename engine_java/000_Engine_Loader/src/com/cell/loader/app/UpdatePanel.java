package com.cell.loader.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JPanel;

import com.cell.loader.LoadTask;
import com.cell.loader.MD5;


/**
 * file list<br>
 * md5 : size : path	<br>
aa58ea7fad8ee62a6dfc0e873abec53a : 652        : .\avatar\item_000100\icon_020100.png	<br>
54f4575f894775e32a35f5787c28f149 : 26809      : .\avatar\item_000100\output\actor.properties	<br>
1807c436ad4aa77de2579cd429c5b16f : 40584      : .\avatar\item_000100\output\item.properties	<br>
612ef854d496ad36292bf7f9d588428e : 9425       : .\avatar\item_000100\output\item_000100.png	<br>
caa6dabede83435d597bb220c0fe97b2 : 733        : .\avatar\item_000101\icon_000101.png	<br>
a43da58335f20affae6052411dd6b55a : 25011      : .\avatar\item_000101\output\item.properties	<br>
c95cd030ffbcc34968f6167bd6697049 : 23737      : .\avatar\item_000101\output\item_000101.png	<br>
eb909da48f90f2a4da711292d6e78ca6 : 722        : .\avatar\item_000102\icon_000102.png	<br>
f3d511974941211adf45b9af7b9c974f : 24604      : .\avatar\item_000102\output\item.properties	<br>
751a158842c6cfb9fa674bbda14f483b : 6949       : .\avatar\item_000102\output\item_000102.png	<br>
e9b82febaac8e64a2963f16e3de61f26 : 340        : .\avatar\item_000103\icon_000103.png	<br>
2e3e9775c8fb8ea0d63937eefcae65ff : 547        : .\avatar\item_000103\icon_020103.png	<br>
fec02be0d29b155679271230114b9867 : 36507      : .\avatar\item_000103\output\item.properties	<br>
c35f33c56d2934b98803197a828995ce : 2307       : .\avatar\item_000103\output\item_000103.png	<br>
 * @author WAZA
 */
public class UpdatePanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	public String 	info_on_checking 		= "检查文件";
	public String	info_on_downloading 	= "下载文件";
	public Image	info_background			= null;
	
//	--------------------------------------------------------------------------
	final private String	remote_root;
	final private String	remote_file;
	final private File		local_root;
	final private File		local_file;
	private UpdateListener	update_over;
	private UpdateTask		task;
//	--------------------------------------------------------------------------
	
	public UpdatePanel(
			String remote_val_file,
			String local_file, 
			String remote_root, 
			String local_root,
			UpdateListener update_over)
	{
		this.remote_root	= remote_root;
		this.remote_file	= remote_val_file;
		
		this.local_root		= new File(local_root);
		this.local_file		= new File(local_file);
		
		this.update_over	= update_over;
		
		this.task			= new UpdateTask();
		
		new Thread(task).start();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		task.paint((Graphics2D)g);
	}
	
//	--------------------------------------------------------------------------------------------------------
	
	private HashMap<String, UpdateValFile> getValList(byte[] data) throws Exception
	{
		if (data != null) {
			String text = new String(data, "UTF-8");
			String[] lines = text.split("\n");
			HashMap<String, UpdateValFile> ret = new HashMap<String, UpdateValFile>(lines.length);
			for (String line : lines) {
				try {
					String[] kv = line.split(":", 3);
					UpdateValFile vf = new UpdateValFile(kv[0].trim(), Integer.parseInt(kv[1].trim()), kv[2].trim());
					ret.put(vf.path, vf);
				} catch (Exception e) {
				}
			}
			return ret;
		} else {
			return new HashMap<String, UpdateValFile>(0);
		}
	}
	
	private boolean checkLocalFile(UpdateValFile remote_val) {
		File real_local_file = new File(local_root, remote_val.path);
		if (!real_local_file.exists()) {
			return false;
		}
		if (real_local_file.length() != remote_val.size) {
			return false;
		}
		byte[] data = LoadTask.load(real_local_file);
		if (data == null) {
			return false;
		}
		if (data.length != remote_val.size) {
			return false;
		}
		if (!remote_val.md5.equals(MD5.getMD5(data))) {
			return false;
		}
		return true;
	}

//	--------------------------------------------------------------------------------------------------------
	
	private class UpdateTask implements Runnable, LoadTask.LoadListener
	{
		ArrayList<UpdateValFile>	update_list = new ArrayList<UpdateValFile>();

		private float 				task_percent = 0f;
		
		private String				state = "";
		
		private byte[] 				remote_data;
		private byte[]				local_data;
		
		@Override
		public void loading(String path, float percent) {
			if (path.equals(remote_file)) {
				task_percent = percent;
			}
			repaint();
		}
		
		@Override
		public void run()
		{
			getRemoteFile();
			
			checkLocalFiles();
			
			downloadFiles();
			
			System.out.println("cache local file : " + remote_file + " -> " + local_file);
			LoadTask.save(local_file, remote_data);
			update_over.updateOver(UpdatePanel.this);
		}
		
		public void paint(Graphics2D g2d) 
		{
			if (info_background != null) {
				g2d.drawImage(info_background, 0, 0, null);
			}
			Rectangle rect = new Rectangle(10, getHeight()-14, getWidth()-20, 5);
			g2d.setColor(Color.GRAY);
			g2d.fill(rect);		
			rect.width = (int)(task_percent * (float)rect.width);
			g2d.setColor(Color.WHITE);
			g2d.fill(rect);
			if (state != null) {
				g2d.setColor(Color.GRAY);
				g2d.drawString(state, rect.x, rect.y-g2d.getFont().getSize());
				g2d.setColor(Color.BLACK);
				g2d.drawString(state, rect.x-1, rect.y-g2d.getFont().getSize()-1);
			}
		}
		
		private void getRemoteFile() 
		{
			try 
			{
				this.state = info_on_checking;
				System.out.println("read remote file : " + remote_file);
				this.remote_data = LoadTask.load(remote_file, this);
				if (remote_file.endsWith(".zip")) {
					ByteArrayOutputStream unzip_data = new ByteArrayOutputStream();
					byte[] buffer = new byte[8192];
					ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(remote_data));
					for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
						int size = 0;
						while (zip.available() > 0) {
							size = zip.read(buffer);
							if (size > 0) {
								unzip_data.write(buffer, 0, size);
							}
						}
						System.out.println("unzip remote file : " + entry.getName());
					}
					this.remote_data = unzip_data.toByteArray();
				}
				System.out.println("read local file : " + local_file.getAbsolutePath());
				this.local_data  = LoadTask.load(local_file);
			}
			catch (Throwable err) {
				err.printStackTrace();
				this.state = info_on_checking + " : " + err.getMessage();
			}
		}
		
		private void checkLocalFiles()
		{
			try 
			{
				HashMap<String, UpdateValFile> remote_list	= getValList(remote_data);
				HashMap<String, UpdateValFile> local_list	= getValList(local_data);
				int max = remote_list.size();
				int index = 0;
				for (String file : remote_list.keySet()) {
					this.state = info_on_checking + " : " + index + "/" + max + " : " + file;
					UpdateValFile remote_val	= remote_list.get(file);
					UpdateValFile local_val		= local_list.get(file);
					try {
						if (local_val != null && local_val.equals(remote_val)) {
							continue;
						}
						if (checkLocalFile(remote_val)) {
							System.out.println("local file ok : " + remote_val);
							continue;
						}
						update_list.add(remote_val);
						System.out.println("append update file : " + remote_val);
					} catch (Throwable t) {
						System.err.println("error : " + file);
						t.printStackTrace();
					} finally {
						task_percent = ((float)index++ / (float)max);
						repaint();
					}
				}
			} catch (Throwable err) {
				err.printStackTrace();
				this.state = info_on_checking + " : " + err.getMessage();
			}
			
		}
		
		private void downloadFiles() 
		{
			try
			{
				System.out.println("start download new files " + update_list.size());
				int max = update_list.size();
				int index = 0;
				for (UpdateValFile remote_val : update_list) {
					this.state = info_on_downloading + " : " + index + "/" + max + " : " + remote_val.path;
					try {
						byte[] rdata = LoadTask.load(remote_root + remote_val.path);
						if (rdata != null) {
							File lfile = new File(local_root, remote_val.path);
							if (!lfile.exists()) {
								lfile.getParentFile().mkdirs();
							}
							System.err.println("cache local file : " + remote_val);
							LoadTask.save(lfile, rdata);
						}
					} catch (Throwable t) {
						System.err.println("error : " + remote_val);
						t.printStackTrace();
					} finally {
						task_percent = ((float)index++ / (float)max);
						repaint();
					}
				}
			} catch (Throwable err) {
				err.printStackTrace();
				this.state = info_on_downloading + " : " + err.getMessage();
			}
		}
		
	
	}

//	--------------------------------------------------------------------------------------------------------
	
	static class UpdateValFile
	{
		final String 	md5;
		final int		size;
		final String	path;

		public UpdateValFile(String md5, int size, String path) {
			while (path.contains("\\")) {
				path = path.replaceAll("\\\\", "/");
			}
			while (path.startsWith(".")) {
				path = path.substring(1);
			}
			while (path.startsWith("/")) {
				path = path.substring(1);
			}
			this.md5 = md5;
			this.size = size;
			this.path = "/" + path;
		}
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof UpdateValFile) {
				return md5.equals(((UpdateValFile) obj).md5);
			}
			return false;
		}
		@Override
		public String toString() {
			return md5 + " : " + path + " (" + size + " bytes)";
		}
	}

//	--------------------------------------------------------------------------------------------------------
	
	public static interface UpdateListener
	{
		
		
		public void updateOver(UpdatePanel panel);
	}
	
}

