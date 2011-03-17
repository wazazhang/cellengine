package com.cell.service;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

public class MainWarpper implements WrapperListener
{
	Logger log = Logger.getLogger(MainWarpper.class.getName());
	
	final static void callMethod(Class<?> clz, String methodName, String[] args) throws Exception
	{
		Class<?>[] arg = new Class<?>[1];
		arg[0] = args.getClass();
		
		Method method = clz.getMethod(methodName, arg);
		Object[] inArg = new Object[1];
		inArg[0] = args;
		method.invoke(null, inArg);
	}
	
	@Override
	public Integer start(String[] arg0) {
		log.info("start : " + arg0);
		for (int i = 0; i < arg0.length; ++i) {
			System.out.println("arg[" + i + "] = " + arg0[i]);
		}
		try {
			String		class_name	= arg0[0];
			Class<?>	main_class	= Class.forName(class_name);
			String[]	main_args	= new String[arg0.length-1];
			System.arraycopy(arg0, 1, main_args, 0, main_args.length);
			callMethod(main_class, "main", main_args);
		} catch (Throwable e) {
			log.log(Level.WARNING, e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return null;
	}
	
	@Override
	public int stop(int arg0) {
		log.info("stop : " + arg0);
		return arg0;
	}
	
	@Override
	public void controlEvent(int arg0) {
		log.info("controlEvent : " + arg0);
	}

	public static void main(String[] args)
	{
		WrapperManager.start(new MainWarpper(), args);
	}

}
