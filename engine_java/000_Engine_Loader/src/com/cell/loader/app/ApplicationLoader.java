package com.cell.loader.app;

import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


import com.cell.classloader.jcl.CC;
import com.cell.classloader.jcl.JarClassLoader;
import com.cell.loader.LoadTask;
import com.cell.loader.MD5;
import com.cell.loader.PaintTask;
import com.cell.loader.LoadTask.LoadTaskListener;


/**
 * 
[main]
update_path			=http://game.lordol.com/lordol_xc_test/update.val
ignore_list			=loader.jar,lordol_res.jar,lordol_ressk.jar,lordol_j2se_ui_sk.jar
l_main				=orc.g2d.Main
l_args				=xxx,xxx,xxx
l_font				=宋体
l_natives			=OpenGL32.dll,OpenAL32.dll,warp_openal.dll
l_decode			=true

[image]
img_bg				=bg.png
img_loading_f		=loading_f.png
img_loading_s 		=loading_s.png
img_loading_b 		=loading_b.png

[text]
l_text_loading		=更新文件中...
l_text_initializing	=初始化中...
l_text_error		=更新错误!
l_text_check		=检查更新中...

[net]
load_retry_count	=5
load_timeout		=10000
*/

public class ApplicationLoader extends JFrame implements WindowListener, LoadTask.LoadTaskListener
{
	private static final long serialVersionUID = 1L;
	
	// update.ini
	String 			update_path;
	String			ignore_list;
	String 			l_main;
	String			l_font;
	String			l_decode;
	ArrayList<String>	l_args = new ArrayList<String>();
	ArrayList<String>	l_natives = new ArrayList<String>();
	
	String			img_bg;
	String			img_loading_f;
	String			img_loading_s;
	String			img_loading_b;
	
	String 			l_text_loading;
	String 			l_text_initializing;
	String 			l_text_error;
	String 			l_text_check;

	int				load_retry_count	=5;
	int				load_timeout		=10000;
	
	String			dk;
	
	// other
	Object 			main_obj;
	
	PaintTask 		paint_task;
	LoadTask 		load_task;

	Canvas 			paint_canvas;
	
	Hashtable<String, byte[]>	local_jar_files 	= new Hashtable<String, byte[]>();
	
	Hashtable<String, String>	ignore_jar_files	= new Hashtable<String, String>();
	
	
	public ApplicationLoader(String update_ini_file)
	{
		this.addWindowListener(this);
		this.setSize(640, 480);
		this.setLocation(
				Toolkit.getDefaultToolkit().getScreenSize().width/2 - getWidth()/2,
				Toolkit.getDefaultToolkit().getScreenSize().height/2 - getHeight()/2);

		dk = LoadTask.getVK(getClass().getResourceAsStream("vk.enc"));
		
		paint_canvas = new Canvas(){
			@Override
			public void update(Graphics g) {
				paint(g);
			}
			public void paint(Graphics g){
				if (main_obj == null) {
					if (paint_task != null) {
						Image buffer = paint_task.repaint(((Graphics2D)g).getDeviceConfiguration());
						if (buffer != null) {
							g.drawImage(buffer, 0, 0, getWidth(), getHeight(), null);
						}
					}
				}
			}
		};
		this.add(paint_canvas);
		this.setTitle("check for update");

		try
		{
			paint_task = new PaintTask(paint_canvas);
			paint_task.start();
			
			// 读入update.ini,得到update.val位置,并且确定主类和配置参数
			Hashtable<String, String> update_ini = new Hashtable<String, String>();
			{
				System.out.println("loading : " + update_ini_file);
				FileInputStream fis = new FileInputStream(update_ini_file);
				byte[] data = new byte[fis.available()];
				fis.read(data);
				fis.close();
				String text = new String(data, "UTF-8");
				String[] lines = text.split("\n");
				for (String line : lines) {
					try{
						String[] kv = line.split("=");
						update_ini.put(kv[0].trim(), kv[1].trim());
						System.out.println("\t" + kv[0].trim() + "\t=" + kv[1].trim());
					}catch (Exception e) {
					}
				}
				
				// 初始化参数
				{
					//[main]
					update_path			= update_ini.get("update_path");
					ignore_list			= update_ini.get("ignore_list");
					l_main				= update_ini.get("l_main");
					l_font				= update_ini.get("l_font");
					l_decode			= update_ini.get("l_decode");
					
					//[image]
					img_bg				= update_ini.get("img_bg");
					img_loading_f		= update_ini.get("img_loading_f");
					img_loading_s 		= update_ini.get("img_loading_s");
					img_loading_b 		= update_ini.get("img_loading_b");
					
					//[text]
					l_text_loading		= update_ini.get("l_text_loading");
					l_text_initializing	= update_ini.get("l_text_initializing");
					l_text_error		= update_ini.get("l_text_error");
					l_text_check		= update_ini.get("l_text_check");
					
					//[net]
					try{
					load_retry_count	= Integer.parseInt(update_ini.get("load_retry_count"));
					load_timeout		= Integer.parseInt(update_ini.get("load_timeout"));
					}catch (Exception e) {
					load_retry_count	= 5;
					load_timeout		= 10000;
					}
					
					
					try{
						String args = update_ini.get("l_args");
						if (args!=null) {
							for (String n : args.split(",")) {
								l_args.add(n.trim());
							}
						}
					} catch (Exception err) {}
					
					try{
						String natives = update_ini.get("l_natives");
						if (natives!=null) {
							for (String n : natives.split(",")) {
								l_natives.add(n.trim());
							}
						}
					} catch (Exception err) {}
					
					LoadTask.LoadRetryTime 	= load_retry_count;
					LoadTask.LoadTimeOut	= load_timeout;
					this.setTitle(l_text_check);
				}
				
				// 初始化忽略列表
				if (ignore_list!=null)
				{
					String[] ignores = ignore_list.split(",");
					for (String ignore : ignores) {
						while (ignore.startsWith(".")) {
							ignore = ignore.substring(1);
						}
						while (ignore.contains("\\")) {
							ignore = ignore.replace('\\', '/');
						}
						ignore_jar_files.put(ignore.trim(), ignore.trim());
					}
				}
				
				// 初始化图片
				try
				{
					System.out.println("loading images !");
					
					Image	bg 			= ImageIO.read(new File(img_bg));
					Image	loading_f	= ImageIO.read(new File(img_loading_f));
					Image	loading_s 	= ImageIO.read(new File(img_loading_s));
					Image	loading_b 	= ImageIO.read(new File(img_loading_b));
					Font	font 		= new Font(l_font, Font.PLAIN, 14);
					paint_task.setPaintUnit(bg, loading_f, loading_s, loading_b, font);
					paint_canvas.repaint();
					
					int frameWidth	= bg.getWidth(null)  + (getInsets().left+getInsets().right);
					int frameHeight	= bg.getHeight(null) + (getInsets().top+getInsets().bottom);
					this.setSize(frameWidth, frameHeight);
					this.setLocation(
							Toolkit.getDefaultToolkit().getScreenSize().width/2 - getWidth()/2,
							Toolkit.getDefaultToolkit().getScreenSize().height/2 - getHeight()/2);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
				this.setVisible(true);
			}
			
//			Thread.sleep(10000);
			
			// 读取远程的update.val
			Hashtable<String, String> update_val = new Hashtable<String, String>();
			{
				// 得到远程文件列表  md5 : file_name
				String root_remote = "";
				try
				{
					root_remote	= update_path.substring(0, update_path.lastIndexOf("/"));
					System.out.println("root_remote = " + root_remote);
					
					System.out.println("loading : " + update_path);
					URLConnection update_path_c = LoadTask.openURL(new URL(update_path));
					byte[] data = LoadTask.loadURL(update_path_c);
					String text = new String(data, "UTF-8");
					String[] lines = text.split("\n");
					for (String line : lines) {
						try{
							String[] kv = line.split(":");
							String jar_name = kv[1].trim();
							update_val.put(jar_name, kv[0].trim());
							System.out.println(line);
						}catch (Exception e) {
						}
					}
				}
				catch (Throwable err) {
					err.printStackTrace();
				}
				
				// 判断当前目录是否存在远程文件,并读入这些文件,计算出md5值
				{
					File root_dir = new File(".");
					
					System.out.println("root_dir = " + root_dir.getPath());
					
					for (File file : root_dir.listFiles())
					{
						// 判断是否为jar文件
						if (file.getName().endsWith(".jar"))
						{
							System.out.print("get local jar ! \"" + file.getName() + "\" ");
							
							try
							{
								FileInputStream fis = new FileInputStream(file);
								byte[] data = new byte[fis.available()];
								fis.read(data);
								fis.close();
								
								if (update_val.isEmpty()) 
								{
									local_jar_files.put(file.getName(), data);
								}
								else if (update_val.containsKey(file.getName()))
								{
									String remote_md5 = update_val.get(file.getName());
									String local_md5 = MD5.getMD5(data);
									// 比较差异文件
									if (remote_md5.equals(local_md5)) {
										local_jar_files.put(file.getName(), data); // keep local file
										update_val.remove(file.getName()); // remove remote file
										System.out.print("validate ok!");
									}else{
										System.out.print("validate changed, prepare to update !" +
												"\n\t" + remote_md5 + 
												"\n\t" + local_md5);
									}
								}
							}
							catch (Exception e) {
								e.printStackTrace();
							}
							System.out.println();
						}
					}
				}
				
				// 下载新的文件
				if (!update_val.isEmpty())
				{
					String[] remote_files = new String[update_val.size()];
					remote_files = update_val.keySet().toArray(remote_files);
					
					for (int i=0; i<remote_files.length; ++i) 
					{
						System.out.print("redist : " + remote_files[i]);
						remote_files[i] = root_remote + "/" + remote_files[i];
						System.out.println("\t -> " + remote_files[i]);
					}
					
					load_task = new LoadTask(this, remote_files);
				}
				else
				{
					loadAllComplete(new Vector<byte[]>(local_jar_files.size())) ;
				}
				
			}
		}
		catch(Throwable err)
		{
			this.setVisible(true);
			err.printStackTrace();
			paint_task.setState(l_text_error + " : " + err.getMessage());
		}
		
	}
	
	public void loadBegin(URL[] files) {
		paint_task.setState(l_text_loading);
	}
	
	public void loadJarBegin(URL file, int current, int total) {
		paint_task.setState(l_text_loading+"(" + (current) + "/" + total + ")");
		paint_task.reset();
	}
	public void loadJarStart(URL file, int current, int total, int length) {
		paint_task.setMax(length);
	}
	public void loadJarProgress(URL file, int current, int total, int actual) {
		paint_task.acc(actual);
	}
	public void loadJarComplete(URL file, int current, int total, byte[] data) 
	{
		// 将下载的新文件保存到本地
		String file_name = file.getFile();
		if (file_name.contains("/")) {
			file_name = file_name.substring(file_name.lastIndexOf("/")+1);
		}
		try
		{
			String suffix = file_name;
			while (suffix.contains("\\")) {
				suffix = suffix.replace('\\', '/');
			}
			for (String ig : ignore_jar_files.keySet()) {
				if (suffix.endsWith(ig)) {
					return;
				}
			}
			File local_file = new File(file_name);
			if (!local_file.getParentFile().exists()) {
				local_file.getParentFile().mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(local_file);
			fos.write(data);
			fos.flush();
			fos.close();
			System.out.println("downloaded and save to local file ! " + file_name);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			paint_task.setState(l_text_error + " : " + e.getMessage());
		}
		finally 
		{
			local_jar_files.put(file_name, data);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadAllComplete(Vector<byte[]> datas) 
	{
		System.out.println("loadCompleted !");
		
		paint_task.setState(l_text_initializing);
		paint_task.exit();
		
		try
		{
			datas.clear();
			
			for (String ignore : ignore_jar_files.values()) {
				if (local_jar_files.containsKey(ignore)) {
					local_jar_files.remove(ignore);
					System.out.println("ignore a classpath jar ! \"" + ignore + "\"");
				}
			}
			
			datas.addAll(local_jar_files.values());
			boolean decode = true;
			try {
				if (l_decode != null) {
					decode = Boolean.parseBoolean(l_decode);
				}
			} catch (Exception err) {
				decode = true;
			}
			ClassLoader		old_class_loader	= Thread.currentThread().getContextClassLoader();
			JarClassLoader	jar_class_loader	= JarClassLoader.createJarClassLoader(
					old_class_loader, datas, dk, true, decode);
			Thread.currentThread().setContextClassLoader(jar_class_loader);
			System.out.println("Class loader changed : " + 
					old_class_loader.getClass().getName() + " -> " + 
					jar_class_loader.getClass().getName());
//			JarClassLoader.loadNatives(jar_class_loader, l_natives);
			
			Class mainclass = jar_class_loader.findClass(l_main);
			
			if (mainclass != null)
			{
				System.out.println("launch main game !");
				
				// 运行主类的main方法
				JarClassLoader.callMain(mainclass, l_args.toArray(new String[l_args.size()]));
				
				main_obj = mainclass;
				
				paint_task = null;
				
				this.setVisible(false);
				this.dispose();
			}

		}
		catch (Throwable e)
		{
			e.printStackTrace();
			paint_task.setState(l_text_error + " : " + e.getMessage());
		}
		
		this.validate();
	}
	
	public void loadError(Throwable cause)
	{
		System.out.println("loadError !");
		paint_task.setState(l_text_error + " : " + cause.getMessage());
	}
	
	

	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {System.exit(1);}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}


	public static void main(String[] args) {
		if (args!=null && args.length>0) {
			new ApplicationLoader(args[0]);
		}else{
			new ApplicationLoader("./update.ini");
		}
	}
	
}
