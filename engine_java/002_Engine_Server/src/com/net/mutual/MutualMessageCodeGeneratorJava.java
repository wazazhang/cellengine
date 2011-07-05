package com.net.mutual;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Map.Entry;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.io.CFile;
import com.cell.reflect.Fields;
import com.cell.reflect.Parser;
import com.cell.security.MD5;
import com.net.ExternalizableFactory;
import com.net.ExternalizableMessage;
import com.net.MessageHeader;
import com.net.NetDataInput;
import com.net.NetDataOutput;
import com.net.NetDataTypes;


public class MutualMessageCodeGeneratorJava extends MutualMessageCodeGenerator
{
	private String template 		= CIO.readAllText("/com/net/mutual/MutualMessageCodecJava.txt");
	
	private String code_package		= "com.net.mutual";
	private String code_import		= "";
	private String code_class_name	= "MutualMessageCodecJava";
	
	public MutualMessageCodeGeneratorJava(String template) 
	{
		if (template != null) {
			this.template = template;
		}
	}
	
	public MutualMessageCodeGeneratorJava(
			String template,
			String code_package, 
			String code_import, 
			String code_class_name) 
	{
		if (template != null) {
			this.template = template;
		}
		this.code_package		= code_package;
		this.code_import		= code_import;
		this.code_class_name	= code_class_name;
	}
	
	public MutualMessageCodeGeneratorJava(
			String code_package, 
			String code_import, 
			String code_class_name) 
	{
		this.code_package		= code_package;
		this.code_import		= code_import;
		this.code_class_name	= code_class_name;
	}
	
	public String genMutualMessageCodec(ExternalizableFactory factory)
	{
		StringBuilder read_external		= new StringBuilder();
		StringBuilder write_external	= new StringBuilder();
		StringBuilder classes			= new StringBuilder();

		for (Entry<Integer, Class<?>> e : factory.getRegistTypes().entrySet()) 
		{
			String c_name = e.getValue().getCanonicalName();
			String s_name = e.getValue().getSimpleName();
			read_external.append(
			"		if (msg.getClass().equals(" + c_name + ".class)) {\n" +
			"			_r((" + c_name + ")msg, in); return;\n" +
			"		}\n");
			write_external.append(
			"		if (msg.getClass().equals(" + c_name + ".class)) {\n" +
			"			_w((" + c_name + ")msg, out); return;\n" +
			"		}\n");
			genMethod(e.getValue(), classes, factory);
		}
		
		String ret = this.template;
		ret = CUtil.replaceString(ret, "//package",			code_package);
		ret = CUtil.replaceString(ret, "//import",			code_import);
		ret = CUtil.replaceString(ret, "//className",		code_class_name);
		
		ret = CUtil.replaceString(ret, "//version",			getVersion());
		
		ret = CUtil.replaceString(ret, "//readExternal",	read_external.toString());
		ret = CUtil.replaceString(ret, "//writeExternal",	write_external.toString());
		ret = CUtil.replaceString(ret, "//classes",			classes.toString());
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
				genField(msg, f, read, write, factory);
			}
		}
		
		sb.append("//	----------------------------------------------------------------------------------------------------\n");
		sb.append("//	" + c_name + "\n");
		sb.append("//	----------------------------------------------------------------------------------------------------\n");
		sb.append("	public " + c_name + " new_" + m_name + "(){return new " + c_name + "();}\n");
		sb.append("	private void _r(" + c_name + " msg, NetDataInput in) throws IOException {\n");
		sb.append(read);
		sb.append("	}\n");
		sb.append("	private void _w(" + c_name + " msg, NetDataOutput out) throws IOException {\n");
		sb.append(write);
		sb.append("	}\n\n");
	}
	
	/**
	 * 产生一行代码，用于读写一个消息内的字段
	 * @param msg
	 * @param f
	 * @param read
	 * @param write
	 */
	protected void genField(Class<?> msg, Field f, StringBuilder read, StringBuilder write, ExternalizableFactory factory)
	{
		Class<?> 	f_type 		= f.getType();
		String 		f_name 		= "msg." + f.getName();
	
		// boolean -----------------------------------------------
		if (f_type.equals(boolean.class)) {
			read.append("		" + f_name + " = in.readBoolean();\n");
			write.append("		out.writeBoolean(" + f_name + ");\n");
		}
		else if (f_type.equals(boolean[].class)) {
			read.append("		" + f_name + " = in.readBooleanArray();\n");
			write.append("		out.writeBooleanArray(" + f_name + ");\n");
		}
		// byte -----------------------------------------------
		else if (f_type.equals(byte.class)) {
			read.append("		" + f_name + " = in.readByte();\n");
			write.append("		out.writeByte(" + f_name + ");\n");
		}
		else if (f_type.equals(byte[].class)) {
			read.append("		" + f_name + " = in.readByteArray();\n");
			write.append("		out.writeByteArray(" + f_name + ");\n");
		}
		// short -----------------------------------------------
		else if (f_type.equals(short.class)) {
			read.append("		" + f_name + " = in.readShort();\n");
			write.append("		out.writeShort(" + f_name + ");\n");
		}
		else if (f_type.equals(short[].class)) {
			read.append("		" + f_name + " = in.readShortArray();\n");
			write.append("		out.writeShortArray(" + f_name + ");\n");
		}
		// char -----------------------------------------------
		else if (f_type.equals(char.class)) {
			read.append("		" + f_name + " = in.readChar();\n");
			write.append("		out.writeChar(" + f_name + ");\n");
		}
		else if (f_type.equals(char[].class)) {
			read.append("		" + f_name + " = in.readCharArray();\n");
			write.append("		out.writeCharArray(" + f_name + ");\n");
		}
		// int -----------------------------------------------
		else if (f_type.equals(int.class)) {
			read.append("		" + f_name + " = in.readInt();\n");
			write.append("		out.writeInt(" + f_name + ");\n");
		}
		else if (f_type.equals(int[].class)) {
			read.append("		" + f_name + " = in.readIntArray();\n");
			write.append("		out.writeIntArray(" + f_name + ");\n");
		}
		// long -----------------------------------------------
		else if (f_type.equals(long.class)) {
			read.append("		" + f_name + " = in.readLong();\n");
			write.append("		out.writeLong(" + f_name + ");\n");
		}
		else if (f_type.equals(long[].class)) {
			read.append("		" + f_name + " = in.readLongArray();\n");
			write.append("		out.writeLongArray(" + f_name + ");\n");
		}
		// float -----------------------------------------------
		else if (f_type.equals(float.class)) {
			read.append("		" + f_name + " = in.readFloat();\n");
			write.append("		out.writeFloat(" + f_name + ");\n");
		}
		else if (f_type.equals(float[].class)) {
			read.append("		" + f_name + " = in.readFloatArray();\n");
			write.append("		out.writeFloatArray(" + f_name + ");\n");
		}
		// double -----------------------------------------------
		else if (f_type.equals(double.class)) {
			read.append("		" + f_name + " = in.readDouble();\n");
			write.append("		out.writeDouble(" + f_name + ");\n");
		}	
		else if (f_type.equals(double[].class)) {
			read.append("		" + f_name + " = in.readDoubleArray();\n");
			write.append("		out.writeDoubleArray(" + f_name + ");\n");
		}
		// String -----------------------------------------------
		else if (f_type.equals(String.class)) {
			read.append("		" + f_name + " = in.readUTF();\n");
			write.append("		out.writeUTF(" + f_name + ");\n");
		}	
		else if (f_type.equals(String[].class)) {
			read.append("		" + f_name + " = in.readUTFArray();\n");
			write.append("		out.writeUTFArray(" + f_name + ");\n");
		}	
		// ExternalizableMessage -----------------------------------------------
		else if (ExternalizableMessage.class.isAssignableFrom(f_type)) {
			read.append("		" + f_name + " = in.readExternal(" + 
					f_type.getCanonicalName() + ".class);\n");
			write.append("		out.writeExternal(" + f_name + ");\n");
		} 
		else if (MutualMessage.class.isAssignableFrom(f_type)) {
			read.append("		" + f_name + " = in.readMutual(" + 
					f_type.getCanonicalName() + ".class);\n");
			write.append("		out.writeMutual(" + f_name + ");\n");
		} 
		else if (f_type.isArray()) {
			if (f_type.getComponentType().isArray()) {
				String leaf_type = NetDataTypes.toTypeName(NetDataTypes.getArrayCompomentType(f_type, factory));
				read.append("		" + f_name + " = (" + f_type.getCanonicalName() + ")in.readAnyArray(" + 
						f_type.getCanonicalName() + ".class, " +
						"NetDataTypes." + leaf_type + ");\n");
				write.append("		out.writeAnyArray(" + f_name + ", " +
						"NetDataTypes." + leaf_type + ");\n");
			} else {
				Class<?> comp_type = f_type.getComponentType();
				if (ExternalizableMessage.class.isAssignableFrom(comp_type)) {
					read.append("		" + f_name + " = (" + f_type.getCanonicalName() + ")in.readExternalArray(" + 
							comp_type.getCanonicalName() + ".class);\n");
					write.append("		out.writeExternalArray(" + f_name + ");\n");
				} 
				else if (MutualMessage.class.isAssignableFrom(comp_type)) {
					read.append("		" + f_name + " = (" + f_type.getCanonicalName() + ")in.readMutualArray(" + 
							comp_type.getCanonicalName() + ".class);\n");
					write.append("		out.writeMutualArray(" + f_name + ");\n");
				} 
			}
		} 
		// Error -----------------------------------------------
		else {
			read.append("		Unsupported type : " + f_name + " " + f_type.getName() + "\n");
			write.append("		Unsupported type : " + f_name + " " + f_type.getName() + "\n");
		}
	}
	
	public void genCodeFile(ExternalizableFactory factory, File as_src_root) throws IOException
	{
		File output = new File(as_src_root, 
				CUtil.replaceString(code_package+"." + code_class_name, ".", File.separator)+".java");
		CFile.writeText(output, genMutualMessageCodec(factory));
		System.out.println("genCodeFileJava : " + output.getCanonicalPath());
	}
}
