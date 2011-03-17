package com.cell.loader.applet;

import netscape.javascript.*;
import java.util.Vector;
/***
 * 
 * @author WAZA

	<PARAM name="l_jars"				value="lordol.jar,lordolres.jar">
	<PARAM name="l_method"				value="run_applet()">
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
public class AppletCache extends LoaderApplet
{
	private static final long serialVersionUID = 1L;
	
	String l_method;
	
	@Override
	protected void onTaskInit()
	{
		l_method	= getParameter("l_method");
	}
	
	@Override
	protected void onTaskOver(Vector<byte[]> datas) throws Exception 
	{
		// 获取JavaScript窗口句柄，引用当前文档窗口
		JSObject win 		= JSObject.getWindow(this); 
		// 访问JavaScript对象
		JSObject doc 		= (JSObject) win.getMember("document"); 
		System.out.println("---------call window method---------");
		System.out.println(l_method);
		System.out.println("---------call window method---------");
		
		win.eval(l_method);

		System.out.println("close current applet!");
//		Runtime.getRuntime().exit(0);
	}

}
