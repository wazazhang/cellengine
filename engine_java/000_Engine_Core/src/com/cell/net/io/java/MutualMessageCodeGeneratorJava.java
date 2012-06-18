package com.cell.net.io.java;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.io.CFile;
import com.cell.net.io.ExternalizableFactory;
import com.cell.net.io.ExternalizableMessage;
import com.cell.net.io.MessageHeader;
import com.cell.net.io.MutualMessage;
import com.cell.net.io.MutualMessageCodeGenerator;
import com.cell.net.io.NetDataTypes;
import com.cell.reflect.FieldGroup;
import com.cell.reflect.ReflectUtil;
import com.cell.util.EnumManager;
import com.cell.util.EnumManager.ValueEnum;


public class MutualMessageCodeGeneratorJava extends MutualMessageCodeGenerator
{
	private String template 		= CIO.readAllText(
			"/com/cell/net/io/java/MutualMessageCodecJava.txt");
	
	private String code_package		= "com.net.mutual";
	private String code_import		= "";
	private String code_class_name	= "MutualMessageCodecJava";
	
	public MutualMessageCodeGeneratorJava(
			ExternalizableFactory factory, 
			String template) 
	{
		super(factory);
		if (template != null) {
			this.template = template;
		}
	}
	
	public MutualMessageCodeGeneratorJava(
			ExternalizableFactory factory, 
			String template,
			String code_package, 
			String code_import, 
			String code_class_name) 
	{
		super(factory);
		if (template != null) {
			this.template = template;
		}
		this.code_package		= code_package;
		this.code_import		= code_import;
		this.code_class_name	= code_class_name;
	}
	
	public MutualMessageCodeGeneratorJava(
			ExternalizableFactory factory, 
			String code_package, 
			String code_import, 
			String code_class_name) 
	{
		super(factory);
		this.code_package		= code_package;
		this.code_import		= code_import;
		this.code_class_name	= code_class_name;
	}
	
	public String genMutualMessageCodec()
	{
		StringBuilder read_external		= new StringBuilder();
		StringBuilder write_external	= new StringBuilder();
		StringBuilder classesIO			= new StringBuilder();
		StringBuilder classesArray		= new StringBuilder();

		for (Entry<Integer, Class<?>> e : factory.getRegistTypes().entrySet()) 
		{
			Class<?> cls = e.getValue();
			String c_name = cls.getCanonicalName();
			String s_name = cls.getSimpleName();

			classesArray.append("\t\t\t"+c_name+".class, //"+ factory.getMessageType(cls) +"\n");
		
			if (!Modifier.isAbstract(cls.getModifiers()) && !cls.isEnum() && 
					MutualMessage.class.isAssignableFrom(cls)) 
			{
				read_external.append(
						tb(2) + "if (msg.getClass().equals(" + c_name + ".class)) {\n" +
						tb(2) + "	_r((" + c_name + ")msg, in); return;\n" +
						tb(2) + "}\n");
						write_external.append(
						tb(2) + "if (msg.getClass().equals(" + c_name + ".class)) {\n" +
						tb(2) + "	_w((" + c_name + ")msg, out); return;\n" +
						tb(2) + "}\n");
						genMethod(e.getValue(), classesIO, factory);
			}
		}
		
		String ret = this.template;
		ret = CUtil.replaceString(ret, "//package",			code_package);
		ret = CUtil.replaceString(ret, "//import",			code_import);
		ret = CUtil.replaceString(ret, "//className",		code_class_name);
		
		ret = CUtil.replaceString(ret, "//version",			getVersion());
		
		ret = CUtil.replaceString(ret, "//readExternal",	read_external.toString());
		ret = CUtil.replaceString(ret, "//writeExternal",	write_external.toString());
		ret = CUtil.replaceString(ret, "//classesIO",		classesIO.toString());
		ret = CUtil.replaceString(ret, "//classesArray",	classesArray.toString());
		return ret;
	}
	
	
	
	/**
	 * 产生一个方法，用于读写消息。
	 * @param msg
	 * @param sb
	 */
	protected void genMethod(Class<?> msg, StringBuilder sb, ExternalizableFactory factory)
	{
		String c_name = msg.getCanonicalName();
		String s_name = msg.getSimpleName();
		String m_name = c_name.replace('.', '_');
		
		StringBuilder read = new StringBuilder();
		StringBuilder write = new StringBuilder();
		for (Field f : msg.getFields()) {
			int modifiers = f.getModifiers();
			if (!Modifier.isStatic(modifiers)) {
				AtomicReference<String> oread  = new AtomicReference<String>();
				AtomicReference<String> owrite = new AtomicReference<String>();
				genCodecField(msg, f, oread, owrite, factory);
				replaceCodecField(msg, f, oread, owrite, factory);
				read.append(oread.get());
				write.append(owrite.get());
			}
		}
		
		sb.append("//	----------------------------------------------------------------------------------------------------\n");
		sb.append("//	" + c_name + "\n");
		sb.append("//	----------------------------------------------------------------------------------------------------\n");
		//sb.append("	public static " + c_name + " new_" + m_name + "(){return new " + c_name + "();}\n");
		sb.append("	private void _r(" + c_name + " msg, NetDataInput in) throws IOException {\n");
		sb.append(read);
		sb.append("	}\n");
		sb.append("	private void _w(" + c_name + " msg, NetDataOutput out) throws IOException {\n");
		sb.append(write);
		sb.append("	}\n\n");
	}
	
	protected void replaceCodecField(
			Class<?> msg, Field f, 
			AtomicReference<String> read, 
			AtomicReference<String> write, 
			ExternalizableFactory factory)
	{
		Class<?> 	f_type 		= f.getType();
		String 		f_name 		= "msg." + f.getName();
		
		if (!Modifier.isPublic(f.getModifiers()) &&
			FieldGroup.class.isAssignableFrom(msg)) 
		{
			String r = read.get();
			r = CUtil.replaceString(r, 
					f_name + " = ", 
					"Fields.setField(msg, \"" + f.getName() + "\", ");
			r = CUtil.replaceString(r, 
					";\n", 
					");\n");
			read.set(r);
			
			String w = write.get();
			w = CUtil.replaceString(r, 
					f_name, 
					"Fields.getField(msg, \"" + f.getName() + "\")");
			write.set(w);
		}
	}
	
	/**
	 * 产生一行代码，用于读写一个消息内的字段
	 * @param msg
	 * @param f
	 * @param read
	 * @param write
	 */
	protected void genCodecField(
			Class<?> msg, Field f, 
			AtomicReference<String> read, 
			AtomicReference<String> write, 
			ExternalizableFactory factory)
	{
		Class<?> 	f_type 		= f.getType();
		String 		f_name 		= "msg." + f.getName();
	
		// boolean -----------------------------------------------
		if (f_type.equals(boolean.class)) {
			read.set(tb(2) + f_name + " = in.readBoolean();\n");
			write.set(tb(2) + "out.writeBoolean(" + f_name + ");\n");
		}
		else if (f_type.equals(boolean[].class)) {
			read.set(tb(2) + f_name + " = in.readBooleanArray();\n");
			write.set(tb(2) + "out.writeBooleanArray(" + f_name + ");\n");
		}
		// byte -----------------------------------------------
		else if (f_type.equals(byte.class)) {
			read.set(tb(2) + f_name + " = in.readByte();\n");
			write.set(tb(2) + "out.writeByte(" + f_name + ");\n");
		}
		else if (f_type.equals(byte[].class)) {
			read.set(tb(2) + f_name + " = in.readByteArray();\n");
			write.set(tb(2) + "out.writeByteArray(" + f_name + ");\n");
		}
		// short -----------------------------------------------
		else if (f_type.equals(short.class)) {
			read.set(tb(2) + f_name + " = in.readShort();\n");
			write.set(tb(2) + "out.writeShort(" + f_name + ");\n");
		}
		else if (f_type.equals(short[].class)) {
			read.set(tb(2) + f_name + " = in.readShortArray();\n");
			write.set(tb(2) + "out.writeShortArray(" + f_name + ");\n");
		}
		// char -----------------------------------------------
		else if (f_type.equals(char.class)) {
			read.set(tb(2) + f_name + " = in.readChar();\n");
			write.set(tb(2) + "out.writeChar(" + f_name + ");\n");
		}
		else if (f_type.equals(char[].class)) {
			read.set(tb(2) + f_name + " = in.readCharArray();\n");
			write.set(tb(2) + "out.writeCharArray(" + f_name + ");\n");
		}
		// int -----------------------------------------------
		else if (f_type.equals(int.class)) {
			read.set(tb(2) + f_name + " = in.readInt();\n");
			write.set(tb(2) + "out.writeInt(" + f_name + ");\n");
		}
		else if (f_type.equals(int[].class)) {
			read.set(tb(2) + f_name + " = in.readIntArray();\n");
			write.set(tb(2) + "out.writeIntArray(" + f_name + ");\n");
		}
		// long -----------------------------------------------
		else if (f_type.equals(long.class)) {
			read.set(tb(2) + f_name + " = in.readLong();\n");
			write.set(tb(2) + "out.writeLong(" + f_name + ");\n");
		}
		else if (f_type.equals(long[].class)) {
			read.set(tb(2) + f_name + " = in.readLongArray();\n");
			write.set(tb(2) + "out.writeLongArray(" + f_name + ");\n");
		}
		// float -----------------------------------------------
		else if (f_type.equals(float.class)) {
			read.set(tb(2) + f_name + " = in.readFloat();\n");
			write.set(tb(2) + "out.writeFloat(" + f_name + ");\n");
		}
		else if (f_type.equals(float[].class)) {
			read.set(tb(2) + f_name + " = in.readFloatArray();\n");
			write.set(tb(2) + "out.writeFloatArray(" + f_name + ");\n");
		}
		// double -----------------------------------------------
		else if (f_type.equals(double.class)) {
			read.set(tb(2) + f_name + " = in.readDouble();\n");
			write.set(tb(2) + "out.writeDouble(" + f_name + ");\n");
		}	
		else if (f_type.equals(double[].class)) {
			read.set(tb(2) + f_name + " = in.readDoubleArray();\n");
			write.set(tb(2) + "out.writeDoubleArray(" + f_name + ");\n");
		}
		// String -----------------------------------------------
		else if (f_type.equals(String.class)) {
			read.set(tb(2) + f_name + " = in.readUTF();\n");
			write.set(tb(2) + "out.writeUTF(" + f_name + ");\n");
		}	
		else if (f_type.equals(String[].class)) {
			read.set(tb(2) + f_name + " = in.readUTFArray();\n");
			write.set(tb(2) + "out.writeUTFArray(" + f_name + ");\n");
		}	
		// Enum -----------------------------------------------
		else if (f_type.isEnum()) {
			read.set(tb(2) + f_name + " = in.readEnum(" + f_type.getCanonicalName() + ".class);\n");
			write.set(tb(2) + "out.writeEnum(" + f_name + ");\n");
		}
		// Date -----------------------------------------------
		else if (Date.class.isAssignableFrom(f_type)) {
			read.set(tb(2) + f_name + " = in.readDate(" + f_type.getCanonicalName() + ".class);\n");
			write.set(tb(2) + "out.writeDate(" + f_name + ");\n");
		}	
		// ExternalizableMessage -----------------------------------------------
		else if (ExternalizableMessage.class.isAssignableFrom(f_type)) {
			read.set(tb(2) + f_name + " = in.readExternal(" + 
					f_type.getCanonicalName() + ".class);\n");
			write.set(tb(2) + "out.writeExternal(" + f_name + ");\n");
		} 
		else if (MutualMessage.class.isAssignableFrom(f_type)) {
			read.set(tb(2) + f_name + " = in.readMutual(" + 
					f_type.getCanonicalName() + ".class);\n");
			write.set(tb(2) + "out.writeMutual(" + f_name + ");\n");
		} 
		else if (f_type.isArray()) 
		{
			// array[0][1]...[n]
			if (f_type.getComponentType().isArray()) 
			{
				String leaf_type = NetDataTypes.toTypeName(
						NetDataTypes.getArrayCompomentType(f_type, factory));
				read.set(tb(2) + f_name + " = (" + f_type.getCanonicalName() + ")in.readAnyArray(" + 
						f_type.getCanonicalName() + ".class, " +
						"NetDataTypes." + leaf_type + ");\n");
				write.set(tb(2) + "out.writeAnyArray(" + f_name + ", " +
						"NetDataTypes." + leaf_type + ");\n");
			} 
			else
			{
				Class<?> comp_type = f_type.getComponentType();
				// 
				if (ExternalizableMessage.class.isAssignableFrom(comp_type))
				{
					read.set(tb(2) + f_name + " = (" + f_type.getCanonicalName() + ")in.readExternalArray(" + 
							comp_type.getCanonicalName() + ".class);\n");
					write.set(tb(2) + "out.writeExternalArray(" + f_name + ");\n");
				} 
				else if (MutualMessage.class.isAssignableFrom(comp_type))
				{
					read.set(tb(2) + f_name + " = (" + f_type.getCanonicalName() + ")in.readMutualArray(" + 
							comp_type.getCanonicalName() + ".class);\n");
					write.set(tb(2) + "out.writeMutualArray(" + f_name + ");\n");
				} 
				else
				{
					String leaf_type = NetDataTypes.toTypeName(
							NetDataTypes.getNetType(comp_type, factory));
					read.set(tb(2) + f_name + " = (" + f_type.getCanonicalName() + ")in.readAnyArray(" + 
							f_type.getCanonicalName() + ".class, " +
							"NetDataTypes." + leaf_type + ");\n");
					write.set(tb(2) + "out.writeAnyArray(" + f_name + ", " +
							"NetDataTypes." + leaf_type + ");\n");
				}
			}
		} 
		// Collections -----------------------------------------------
		else if (Collection.class.isAssignableFrom(f_type)) 
		{
			Class argType = ReflectUtil.getFieldGenericType(f, 0);
			byte compType = NetDataTypes.getNetType(argType, factory);
			
			read.set(tb(2) + f_name + " = ("+f_type.getCanonicalName()+")in.readCollection(" +
					f_type.getCanonicalName()+".class, " +
					"NetDataTypes." + NetDataTypes.toTypeName(compType) + ");\n");
			write.set(tb(2) + "out.writeCollection(" + f_name + ", " +
					"NetDataTypes." + NetDataTypes.toTypeName(compType) + ");\n");
		}
		else if (Map.class.isAssignableFrom(f_type)) 
		{
			Class keyType 		= ReflectUtil.getFieldGenericType(f, 0);
			byte  keyNetType 	= NetDataTypes.getNetType(keyType, factory);
			Class valueType 	= ReflectUtil.getFieldGenericType(f, 1);
			byte  valueNetType 	= NetDataTypes.getNetType(valueType, factory);
			
			read.set(tb(2) + f_name + " = ("+f_type.getCanonicalName()+")in.readMap("+
			f_type.getCanonicalName()+".class, " +
			"NetDataTypes." + NetDataTypes.toTypeName(keyNetType) + ", " +
			"NetDataTypes." + NetDataTypes.toTypeName(valueNetType) + ");\n");
			
			write.set(tb(2) + "out.writeMap(" + f_name + ", " +
			"NetDataTypes." + NetDataTypes.toTypeName(keyNetType) + ", " +
			"NetDataTypes." + NetDataTypes.toTypeName(valueNetType) + ");\n");
		}
		// Error -----------------------------------------------
		else {
			read.set(tb(2) + f_name + " = in.readObject(" + 
				f_type.getCanonicalName() + ".class);\n");
			write.set(tb(2) + "out.writeObject(" + f_name + ");\n");
		}
	}
	
	public void genCodeFile(File as_src_root) throws IOException
	{
		File output = new File(as_src_root, 
				CUtil.replaceString(code_package+"." + code_class_name, ".", File.separator)+".java");
		CFile.writeText(output, genMutualMessageCodec());
		System.out.println("genCodeFileJava : " + output.getCanonicalPath());
	}
	
	
	public static Class<?>[] findClasses(File srcdir, String package_prefix) throws Exception
	{
		LinkedHashSet<Class<?>> ret = new LinkedHashSet<Class<?>>();
		for (File f : srcdir.listFiles()) {
			if (f.getName().endsWith(".java")) {
				String className = f.getName().substring(0, f.getName().length()-5);
				Class<?> cls = Class.forName(package_prefix + "." + className);
				ret.add(cls);
			}
		}
		return ret.toArray(new Class<?>[ret.size()]);
	}
	
	public static Class<?>[] findClasses(File srcroot) throws Exception
	{
		Map<File, Class<?>> classmap = findClassesMap(srcroot);
		Class<?>[] classes = classmap.values().toArray(new Class<?>[classmap.size()]);
		return classes;
	}
	
	public static Map<File, Class<?>> findClassesMap(File srcroot) throws Exception
	{
		LinkedHashMap<File, Class<?>> ret = new LinkedHashMap<File, Class<?>>();
		for (File f : srcroot.listFiles()) {
			if (f.isDirectory()) {
				findClasses(srcroot, f, ret);
			}
		}
		return ret;
	}
	
	private static void findClasses(File srcroot, File child, Map<File, Class<?>> ret) throws Exception
	{
		String package_prefix = child.getCanonicalPath().substring(srcroot.getCanonicalPath().length());
		package_prefix = package_prefix.replace('\\', '.');
		package_prefix = package_prefix.replace('/', '.');
		while (package_prefix.charAt(0)=='.') {
			package_prefix = package_prefix.substring(1);
		}
		while (package_prefix.charAt(package_prefix.length()-1)=='.') {
			package_prefix = package_prefix.substring(0, package_prefix.length()-1);
		}
		for (File f : child.listFiles()) {
			if (!f.isHidden()) {
				if (f.isDirectory()) {
					findClasses(srcroot, f, ret);
				} else if (f.getName().endsWith(".java")) {
					String className = f.getName().substring(0, f.getName().length()-5);
					Class<?> cls = Class.forName(package_prefix + "." + className);
					ret.put(f, cls);
				}
			}
		}
	}
	
//	public static void main(String[] args) {
//		ArrayList a = new ArrayList();
//		a.add("");
//		ArrayList<Integer> b = new ArrayList<Integer>();
//		
//		b = a;
//		int b0 = b.get(0);
//		System.out.println(b0);
//	}
}
