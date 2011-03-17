package com.cell.loader.applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.Component;
import java.awt.EventQueue;
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
import com.cell.loader.LoadTask.LoadTaskListener;



/***
 * 
 * @author WAZA

	<PARAM name="l_jars"				value="lordol.jar,lordolres.jar">
	<PARAM name="l_applet"				value="lord.LordApplet">
	<PARAM name="l_font"				value="System">
	<PARAM name="l_decode"				value="true">
		
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
public class AppletLauncher extends LoaderApplet
{
	private static final long serialVersionUID = 1L;
	
	String 			l_applet;
	boolean			l_decode;
	Object 			applet_obj;
	JApplet 		applet_game = null;

	String			dk;
	Vector<byte[]> 	loaded_datas;
	ClassLoader		old_class_loader;
	
	@Override
	protected void onTaskInit()
	{
		l_applet	= getParameter("l_applet");
		l_decode	= "true".equalsIgnoreCase(getParameter("l_decode"));
		if (l_decode) {
			dk = LoadTask.getVK(getClass().getResourceAsStream("/com/cell/loader/vk.enc"));
		}
		old_class_loader = Thread.currentThread().getContextClassLoader();
		System.out.println(Thread.currentThread());
	}
	
	@Override
	protected void onTaskOver(Vector<byte[]> datas) throws Exception 
	{
		loaded_datas = datas;
//		EventQueue.invokeLater(new Runnable() {
//            public void run() {
//            	try {
//            		launchApplet(datas);
//				} catch (Exception e) {
//					e.printStackTrace();
//					System.exit(1);
//				}
//            }
//        });
//		this.repaint();
		launchApplet(datas);
	}
//	@Override
//	public void paint(Graphics g) {
//		super.paint(g);
//		if (loaded_datas != null) {
//			Vector<byte[]> datas = loaded_datas;
//			loaded_datas = null;
//			try {
//				launchApplet(datas);
//			} catch (Exception err) {
//				err.printStackTrace();
//			}
//		}
//	}
	
	private void launchApplet(Vector<byte[]> datas) throws Exception
	{
		JarClassLoader		jar_class_loader	= JarClassLoader.createJarClassLoader(
				old_class_loader, datas, dk, true, l_decode);
		Thread.currentThread().setContextClassLoader(jar_class_loader);
		System.out.println("Class loader changed : " + 
				old_class_loader.getClass().getName() + " -> " + 
				jar_class_loader.getClass().getName());
//		JarClassLoader.loadNatives(jar_class_loader, l_natives);
		Class<?> mainclass = jar_class_loader.findClass(l_applet);
		applet_obj = mainclass.newInstance();
		JApplet game = null;
		
		if (applet_obj instanceof JApplet)
		{
			game = (JApplet)applet_obj;
			
			game.setStub(new AppletStubAdapter());
			
			game.init();
		
			System.out.println("game applet initrilized !");
			
			this.resize(game.getSize());
			this.setEnabled(true);
			this.setFocusable(true);
			this.add(game);
			this.repaint();
			
			applet_game = game;
			
			System.out.println("applet added !");
			
			game.repaint();
		
			this.repaint();		
			this.setVisible(true);
		}
	}
	
	class AppletStubAdapter implements AppletStub
	{
		public void appletResize(int width, int height) {
			AppletLauncher.this.resize(width, height);
		}
		public AppletContext getAppletContext() {
			return AppletLauncher.this.getAppletContext();
		}
		public URL getCodeBase() {
			return AppletLauncher.this.getCodeBase();
		}
		public URL getDocumentBase() {
			return AppletLauncher.this.getDocumentBase();
		}
		public String getParameter(String name) {
			return AppletLauncher.this.getParameter(name);
		}
		public boolean isActive() {
			return AppletLauncher.this.isActive();
		}
	}
}
