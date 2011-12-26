package com.cell.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import com.cell.CUtil;


public class Fields
{
	// 得到所有的field(包括父类) @see Class.getDeclaredFields()
	public static ArrayList<Field> getDeclaredAndSuperFields(Class<?> gclass)
	{
		ArrayList<Field> ret = new ArrayList<Field>();
		
		Field[] d_fields = gclass.getDeclaredFields();
		for (Field field : d_fields) {
			int modifiers = field.getModifiers();
			if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
			} else {
				ret.add(field);
			}
		}

		Class<?> super_class = gclass.getSuperclass();
		if (super_class!=null) {
			ret.addAll(getDeclaredAndSuperFields(super_class));
		}
		
		return ret;
	}
	
	public static ArrayList<Field> getSuperAndDeclaredFields(Class<?> gclass)
	{
		ArrayList<Field> ret = new ArrayList<Field>();

		Class<?> super_class = gclass.getSuperclass();
		if (super_class!=null) {
			ret.addAll(getSuperAndDeclaredFields(super_class));
		}
		
		Field[] d_fields = gclass.getDeclaredFields();
		for (Field field : d_fields) {
			int modifiers = field.getModifiers();
			if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
			} else {
				ret.add(field);
			}
		}

		return ret;
	}
	
	/**
	 * 将src的内容完全复制给dst，通常用于将父类的内容传递给子类，
	 * dst必须是src的子类或者同类<br>
	 * <font color="#ff0000"> 注意：只能复制public或者protected字段的值 </font>
	 * @param src 
	 * @param dst
	 */
	public static <T extends FieldGroup> void setFields(T src, T dst)
	{
		try{
			Class<?> src_type = src.getClass();
			if (!src_type.isInstance(dst)) {
				throw new ClassCastException("\"" + src.getClass() + "\" can not cast \"" + dst.getClass() + "\"");
			}
			setFields(src_type, src, dst);
//			System.out.println("-------------------------------------------------------------------------------------");
		}catch(Throwable ex) {
			ex.printStackTrace();
		}
	}
	
	private static void setFields(Class<?> src_type, FieldGroup src, FieldGroup dst)
	{		
		for (Field field : src_type.getDeclaredFields()) {
			int modifiers = field.getModifiers();
			if ((modifiers&Modifier.STATIC)!=0 || 
				(modifiers&Modifier.FINAL)!=0 || 
				(modifiers&Modifier.PRIVATE)!=0 ) {
				continue;
			}
			try{
				Object fv = src.getField(field);
				dst.setField(field, CUtil.cloneObject(fv));
//				System.out.println(src_type + " : " + field.getName() + " : " + fv);
			}catch(Exception err){
				System.err.println(src_type + " : " + field.getName() + " : " + err.getMessage());
				err.printStackTrace();
			}
		}
		Class<?> super_type = src_type.getSuperclass();
		if (super_type!=null) {
			setFields(super_type, src, dst);
		}
		
	}
}
