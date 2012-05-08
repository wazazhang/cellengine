package com.cell.rpg.formula;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;

import com.g2d.annotation.Property;

@Property("系统-函数")
public class SystemMethod extends StaticMethod
{
	public SystemMethod() {}
	
	public SystemMethod(Method method, AbstractValue[] parameters) {
		super(method, parameters);
	}

//	------------------------------------------------------------------------------------------------------------
//	Edit Mode
	static private LinkedHashMap<MethodInfo, Method> methods;
	
	static public LinkedHashMap<MethodInfo, Method> getStaticMethods() 
	{
		if (methods == null) {
			methods = new LinkedHashMap<MethodInfo, Method>();
			for (Method method : SystemMethod.class.getMethods()) {
				if ((method.getModifiers() & Modifier.STATIC) != 0) {
					if (validateMethod(method)) {
						methods.put(new MethodInfo(method), method);
//						System.out.println("SystemMethod - " + method);
					}
				}
			}
		}
		return methods;
	}
	
//	------------------------------------------------------------------------------------------------------------
//	Static System Method
	
	public static Long getGameTime()
	{
		return system_method_adapter.getGameTime();
	}
	
	public static Integer getTotalPlayer()
	{
		return system_method_adapter.getTotalPlayer();
	}

//	------------------------------------------------------------------------------------------------------------
//	System Method Adapter
	
	public static interface ISystemMethodAdapter
	{
		/**
		 * 得到当前时间
		 * @return
		 */
		public Long		getGameTime();
		
		/**
		 * 得到活动玩家数
		 * @return
		 */
		public Integer	getTotalPlayer();
	}
	
	private static ISystemMethodAdapter system_method_adapter;

	public static ISystemMethodAdapter getSystemMethodAdapter() {
		return system_method_adapter;
	}
	
	public static void setSystemMethodAdapter(ISystemMethodAdapter systemMethodAdapter) {
		system_method_adapter = systemMethodAdapter;
	}
	
}

