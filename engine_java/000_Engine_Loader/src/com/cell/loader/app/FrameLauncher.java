package com.cell.loader.app;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import com.cell.classloader.jcl.JarClassLoader;
import com.cell.loader.LoadTask;

/**
 * 
[main]
update_path			=http://game.lordol.com/lordol_xc_test/update.val
ignore_list			=loader.jar,lordol_res.jar,lordol_ressk.jar,lordol_j2se_ui_sk.jar
l_main				=orc.g2d.Main
l_args				=xxx,xxx,xxx
l_font				=宋体
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

public class FrameLauncher extends LoaderFrame
{
	private static final long serialVersionUID = 1L;
	
	// update.ini
	String 				l_main;
	String				l_decode;
	ArrayList<String>	l_args = new ArrayList<String>();
	
	String				dk;
	Object				main_obj;
	
	public FrameLauncher(String update_ini_file)
	{
		super(update_ini_file);
	}
	
	@Override
	protected void onTaskInit(Map<String, String> update_ini)
	{
		this.dk 		= LoadTask.getVK(getClass().getResourceAsStream("vk.enc"));		
		this.l_main		= update_ini.get("l_main");
		this.l_decode	= update_ini.get("l_decode");
		
		try{
			String args = update_ini.get("l_args");
			if (args!=null) {
				for (String n : args.split(",")) {
					l_args.add(n.trim());
				}
			}
		} catch (Exception err) {}
	}
	
	@Override
	protected void onTaskOver(Vector<byte[]> datas) throws Exception 
	{
		datas.clear();
		
		for (String ignore : getIgnoreJarFiles().values()) {
			if (getLocalJarFiles().containsKey(ignore)) {
				getLocalJarFiles().remove(ignore);
				System.out.println("ignore a classpath jar ! \"" + ignore + "\"");
			}
		}

		datas.addAll(getLocalJarFiles().values());
		
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
//		JarClassLoader.loadNatives(jar_class_loader, l_natives);
		
		Class<?> mainclass = jar_class_loader.findClass(l_main);
		
		if (mainclass != null)
		{
			System.out.println("launch main game !");
			
			// 运行主类的main方法
			JarClassLoader.callMain(mainclass, l_args.toArray(new String[l_args.size()]));
			
			main_obj = mainclass;
			
			this.setVisible(false);
			this.dispose();
		}
	}

	public static void main(String[] args) {
		if (args != null && args.length > 0) {
			new FrameLauncher(args[0]);
		} else {
			if (System.getProperty("com.cell.loader.app.config")!=null) {
				System.out.println("get system property : \"com.cell.loader.app.config\"" );
				new FrameLauncher(System.getProperty("com.cell.loader.app.config"));
			} else {
				new FrameLauncher("./update.ini");
			}
		}
	}
	
}
