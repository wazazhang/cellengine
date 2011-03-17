package com.cell.rpg.formula;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;

import com.g2d.annotation.Property;

@Property("数学-函数")
public class MathMethod extends StaticMethod
{
//	------------------------------------------------------------------------------------------------
	
	public MathMethod() {}
	
	public MathMethod(Method method, AbstractValue[] parameters) {
		super(method, parameters);
	}

//	------------------------------------------------------------------------------------------------
//	Edit Mode
	
	static private LinkedHashMap<MethodInfo, Method> methods;

	public static LinkedHashMap<MethodInfo, Method> getStaticMethods() {
		if (methods == null) {
			methods = new LinkedHashMap<MethodInfo, Method>();
			for (Method method : Math.class.getMethods()) {
				if ((method.getModifiers() & Modifier.STATIC) != 0) {
					if (method.getReturnType().equals(Double.class) || 
						method.getReturnType().equals(double.class)) {
						if (validateMethod(method)) {
							methods.put(new MethodInfo(method), method);
//							System.out.println("MathMethod - " + method);
						}
					}
				}
			}
		}
		return methods;
	}
//	
}
