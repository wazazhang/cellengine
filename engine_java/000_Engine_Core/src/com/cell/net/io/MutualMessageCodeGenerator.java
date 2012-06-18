package com.cell.net.io;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;


public abstract class MutualMessageCodeGenerator
{
	final protected ExternalizableFactory factory;
	public MutualMessageCodeGenerator(ExternalizableFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * 自动生成代码文件，或编解码规则文件。
	 * @param regist_types
	 * @return
	 */
	abstract public String genMutualMessageCodec();
	
	
	public String getVersion() {
		return new Date().toString();
	}
	
	public static boolean isAllStaticField(Class<?> msg)
	{
		for (Field f : msg.getFields()) {
			int modifiers = f.getModifiers();
			if (!Modifier.isStatic(modifiers)) {
				return false;
			}
		}
		return true;
	}
	

	public static String tb(int count){
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<count; i++) {
			sb.append('\t');
		}
		return sb.toString();
	}
	

}
