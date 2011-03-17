package com.cell.rpg.formula;

import java.lang.reflect.Method;

/**
 * 将静态函数包装成变量
 * @author WAZA
 *
 */
public abstract class StaticMethod extends AbstractMethod
{
//	------------------------------------------------------------------------------------------------------------------------------
	
	public StaticMethod() {}

	public StaticMethod(Method method, AbstractValue[] parameters) 
	{
		super(method, parameters);
	}
	
}
