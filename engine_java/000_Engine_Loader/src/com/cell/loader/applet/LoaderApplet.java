package com.cell.loader.applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JApplet;

import sun.plugin2.applet.JNLP2ClassLoader;


import com.cell.classloader.jcl.CC;
import com.cell.classloader.jcl.JarClassLoader;
import com.cell.loader.LoadTask;
import com.cell.loader.PaintTask;
import com.cell.loader.LoadTask.LoadTaskListener;
import com.cell.loader.applet.AppletLauncher.AppletStubAdapter;



/***
 * 
 * @author WAZA

	<PARAM name="l_jars"				value="lordol.jar,lordolres.jar">
	<PARAM name="l_applet"				value="lord.LordApplet">
	<PARAM name="l_font"				value="System">
	
	<PARAM name="img_bg"				value="bg.png">
	<PARAM name="img_loading_f"			value="loading_f.png">
	<PARAM name="img_loading_s" 		value="loading_s.png">
	<PARAM name="img_loading_b" 		value="loading_b.png">
	
	<PARAM name="l_text_loading"		value="loading...">
	<PARAM name="l_text_initializing"	value="initializing...">
	<PARAM name="l_text_error"			value="loading error, please refresh browser!">
	
	<PARAM name="load_retry_count"		value="5">
	<PARAM name="load_timeout"			value="10000">
 */
public abstract class LoaderApplet extends JApplet implements LoadTaskListener
{
	private static final long serialVersionUID = 1L;
	
	// param
	private String 			l_jars;
	private String			l_font;
	
	private String			img_bg;
	private String			img_loading_f;
	private String			img_loading_s;
	private String			img_loading_b;
	
	private String 			l_text_loading;
	private String 			l_text_initializing;
	private String 			l_text_error;
	
	private int				load_retry_count	=5;
	private int				load_timeout		=10000;

	// other
	private URL 			root_dir;
		
	private PaintTask 		paint_task;
	private LoadTask 		load_task;
	private PaintCanvas		paint_canvas;
	
	final protected String getParameter(String name, String default_value) {
		String ret = super.getParameter(name);
		if (ret==null) {
			ret = default_value;
		}
		return ret;
	}
	
	final public void init()
	{
		try
		{
			this.setLayout(new BorderLayout());
			
			this.paint_canvas	= new PaintCanvas();
			this.add(paint_canvas);
			
			this.paint_task		= new PaintTask(paint_canvas);
			
			this.root_dir		= new URL(getCodeBase().toString());
			
			// init applet parameter
			{
				l_jars				= getParameter("l_jars");
				l_font				= getParameter("l_font");

				img_bg				= getParameter("img_bg", 				"bg.png");
				img_loading_f		= getParameter("img_loading_f",			"loading_f.png");
				img_loading_s 		= getParameter("img_loading_s",			"loading_s.png");
				img_loading_b 		= getParameter("img_loading_b", 		"loading_b.png");
				
				l_text_loading		= getParameter("l_text_loading",		"loading...");
				l_text_initializing	= getParameter("l_text_initializing",	"initializing...");
				l_text_error		= getParameter("l_text_error",			"error");
				
				try {
				load_retry_count	= Integer.parseInt(getParameter("load_retry_count",		"5"));
				load_timeout		= Integer.parseInt(getParameter("load_timeout",			"10000"));
				} catch (Exception e) {
				load_retry_count	=5;
				load_timeout		=10000;
				}

				LoadTask.LoadRetryTime 	= load_retry_count;
				LoadTask.LoadTimeOut	= load_timeout;
				
				onTaskInit();
			}
			
			// make paint task
			{
				Thread imageloadtask = new Thread(){
					@Override
					public void run()
					{
						try
						{
							Image	bg 			= getImage(root_dir, img_bg);
							Image 	loading_f 	= getImage(root_dir, img_loading_f);
							Image 	loading_s 	= getImage(root_dir, img_loading_s);
							Image 	loading_b 	= getImage(root_dir, img_loading_b);
							Font 	font 		= new Font(l_font, Font.PLAIN, 14);

							paint_task.setPaintUnit(bg, loading_f, loading_s, loading_b, font);
							
						}catch (Exception e) {
							e.printStackTrace();
						}
						
						System.out.println("imageloadtask exited!");
					}
				};
				imageloadtask.start();
				
				paint_task.start();
			}
			
			// make load task
			{
				String[]	jarfiles 	= l_jars.split(",");
				for (int i=0; i<jarfiles.length; ++i) 
				{
					jarfiles[i] = jarfiles[i].trim();
					
					if (!jarfiles[i].startsWith("http://"))
					{
						System.out.print("redist " + jarfiles[i]);
						jarfiles[i] = getCodeBase().toString() + jarfiles[i];
						System.out.println("\t -> " + jarfiles[i]);
					}
				}
				
				load_task = new LoadTask(this, jarfiles);
			}
			
		}
		catch(Throwable err)
		{
			err.printStackTrace();
			paint_task.setState(l_text_error + " : " + err.getMessage());
		}
	}
	
	
	final public void loadBegin(URL[] files) {
		paint_task.setState(l_text_loading);
	}
	final public void loadJarBegin(URL file, int current, int total) {
		paint_task.setState(l_text_loading+"(" + (current) + "/" + total + ")");
		paint_task.reset();
	}
	final public void loadJarStart(URL file, int current, int total, int length) {
		paint_task.setMax(length);
	}
	final public void loadJarProgress(URL file, int current, int total, int actual) {
		paint_task.acc(actual);
	}
	final public void loadJarComplete(URL file, int current, int total, byte[] data) {
//		paint_task.reset();
	}
	final public void loadAllComplete(Vector<byte[]> datas) 
	{
		System.out.println("loadCompleted !");

		paint_task.setState(l_text_initializing);
		paint_task.exit();
		
		this.validate();
		try {
			this.remove(paint_canvas);
			onTaskOver(datas);
			paint_task = null;
		} catch (Throwable e) {
			e.printStackTrace();
			paint_task.setState(l_text_error + " : " + e.getMessage());
		}
		this.validate();
	
	}
	final public void loadError(Throwable cause)
	{
		System.out.println("loadError !");
		paint_task.setState(l_text_error + " : " + cause.getMessage());
	}
	
	abstract protected void onTaskOver(Vector<byte[]> datas) throws Exception;

	abstract protected void onTaskInit();
	
	private class PaintCanvas extends Canvas
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void update(Graphics g) {
			this.paint(g);
		}
		
		public void paint(Graphics g)
		{
			if (paint_task != null) {
				Image buffer = paint_task.repaint(((Graphics2D)g).getDeviceConfiguration());
				if (buffer != null) {
					g.drawImage(buffer, 0, 0, getWidth(), getHeight(), null);
				}
			}
		}
	}
	

	
	
}
