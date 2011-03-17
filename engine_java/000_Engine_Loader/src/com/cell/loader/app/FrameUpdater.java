package com.cell.loader.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
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
l_app				=game.exe
l_app_working_dir	=.
l_envp				=envp
l_font				=宋体

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

public class FrameUpdater extends LoaderFrame
{
	private static final long serialVersionUID = 1L;
	
	String l_app;
	String l_app_working_dir;
	String l_envp[];
	
	
	public FrameUpdater(String update_ini_file)
	{
		super(update_ini_file);
	}
	
	@Override
	protected void onTaskInit(Map<String, String> update_ini)
	{
		this.l_app				= update_ini.get("l_app");
		this.l_app_working_dir	= update_ini.get("l_app_working_dir");	
		if (l_app_working_dir == null) {
			l_app_working_dir = ".";
		}
		try{
			String envp 		= update_ini.get("l_envp");
			if (envp != null) {
				l_envp = envp.split(",");
			}
		} catch (Exception err) {}
	
	}
	
	@Override
	protected void onTaskOver(Vector<byte[]> datas) throws Exception 
	{
		if (this.l_app != null) {
			System.out.println(l_app);
			Process process = Runtime.getRuntime().exec(l_app, l_envp, new File(l_app_working_dir));
			System.out.println(process);
			this.setVisible(false);
//			process.waitFor();
//			System.out.println(process.exitValue());
		}
		new Thread(){
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.exit(1);
			}
		}.start();
	}

	public static void main(String[] args) {
		if (args != null && args.length > 0) {
			new FrameUpdater(args[0]);
		} else {
			if (System.getProperty("com.cell.loader.app.config")!=null) {
				System.out.println("get system property : \"com.cell.loader.app.config\"" );
				new FrameUpdater(System.getProperty("com.cell.loader.app.config"));
			} else {
				new FrameUpdater("./update.ini");
			}
		}
	}
	
}
