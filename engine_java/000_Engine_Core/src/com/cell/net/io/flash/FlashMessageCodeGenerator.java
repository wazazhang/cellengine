package com.cell.net.io.flash;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.io.CFile;
import com.cell.net.io.Comment;
import com.cell.net.io.ExternalizableFactory;
import com.cell.net.io.MutualMessage;
import com.cell.net.io.MutualMessageCodeGenerator;
import com.cell.net.io.NetDataTypes;
import com.cell.reflect.Fields;
import com.cell.reflect.ReflectUtil;
import com.cell.util.StringUtil;

public class FlashMessageCodeGenerator extends MutualMessageCodeGenerator
{
	private String codec_template	= CIO.readAllText(
			"/com/cell/net/io/flash/FlashMessageCodec.txt");
	private String codec_class_name	= "FlashMessageCodec";
	private String codec_package	= "com.net.flash.message";
	private String codec_import		= "";
	
	private String message_template	= CIO.readAllText(
			"/com/cell/net/io/flash/FlashMessage.txt");
	private String message_import	= "";
	
	public FlashMessageCodeGenerator() {
		this(null, null, null, null);
	}
	
	public FlashMessageCodeGenerator(
			String codec_package,
			String codec_class_name,
			String codec_import,
			String message_import) {
		this(null, codec_package, codec_class_name, codec_import, null, message_import);
	}

	public FlashMessageCodeGenerator(
			String codec_template,
			String codec_package,
			String codec_class_name,
			String codec_import,
			String message_template,
			String message_import) 
	{
		if (codec_template != null) {
			this.codec_template = codec_template;
		}
		if (codec_package != null) {
			this.codec_package = codec_package;
		}
		if (codec_class_name != null) {
			this.codec_class_name = codec_class_name;
		}
		if (codec_import != null) {
			this.codec_import = codec_import;
		}
		if (message_template != null) {
			this.message_template = message_template;
		}
		if (message_import != null) {
			this.message_import = message_import;
		}
	}

	protected String toASType(Class<?> 	f_type)
	{
		// boolean -----------------------------------------------
		if (f_type.equals(boolean.class)) {
			return "Boolean";
		}
		else if (f_type.equals(Boolean.class)) {
			return "Boolean";
		}
		// byte -----------------------------------------------
		else if (f_type.equals(byte.class)) {
			return "int";
		}
		else if (f_type.equals(Byte.class)) {
			return "int";
		}
		// short -----------------------------------------------
		else if (f_type.equals(short.class)) {
			return "int";
		}
		else if (f_type.equals(Short.class)) {
			return "int";
		}
		// char -----------------------------------------------
		else if (f_type.equals(char.class)) {
			return "int";
		}
		else if (f_type.equals(Character.class)) {
			return "int";
		}
		// int -----------------------------------------------
		else if (f_type.equals(int.class)) {
			return "int";
		}
		else if (f_type.equals(Integer.class)) {
			return "int";
		}
		// long -----------------------------------------------
		else if (f_type.equals(long.class)) {
			return "Number";
		}
		else if (f_type.equals(Long.class)) {
			return "Number";
		}
		// float -----------------------------------------------
		else if (f_type.equals(float.class)) {
			return "Number";
		}
		else if (f_type.equals(Float.class)) {
			return "Number";
		}
		// double -----------------------------------------------
		else if (f_type.equals(double.class)) {
			return "Number";
		}	
		else if (f_type.equals(Double.class)) {
			return "Number";
		}	
		// String -----------------------------------------------
		else if (f_type.equals(String.class)) {
			return "String";
		}
		// ExternalizableMessage -----------------------------------------------
		else if (MutualMessage.class.isAssignableFrom(f_type)) {
			return "" + f_type.getCanonicalName();
		} 
		else if (f_type.isArray()) {
			return "Array";
		} 
		else if (Collection.class.isAssignableFrom(f_type)) {
			return "Array";
		}
		else if (Map.class.isAssignableFrom(f_type)) {
			return "com.cell.util.Map";
		}
		// Error -----------------------------------------------
		else {
			return "		Unsupported type : " + f_type.getName();
		}
	}
	
	
	public String genMutualMessageCodec(ExternalizableFactory factory)
	{
		StringBuilder read_external		= new StringBuilder();
		StringBuilder write_external	= new StringBuilder();
		StringBuilder classes			= new StringBuilder();
		StringBuilder get_type			= new StringBuilder();
		StringBuilder new_msg			= new StringBuilder();
		
		for (Entry<Integer, Class<?>> e : factory.getRegistTypes().entrySet()) 
		{
			Class<?> cls = e.getValue();
			String c_name = cls.getCanonicalName();
			String s_name = cls.getSimpleName();
			
			int    s_type = e.getKey();
			
			if (!Modifier.isAbstract(cls.getModifiers())) 
			{
				String m_name = s_name + "_" + s_type;
				String q_name = c_name.substring(0, c_name.length() - s_name.length() - 1) + "::" + s_name;
				
				get_type.append(
				"			if (cname == \"" + q_name + "\") return " + s_type + ";\n");
				
				read_external.append(
				"		if (msg is " + c_name + ") {\n" +
				"			r_" + m_name + "(" + c_name + "(msg), input); return;\n" +
				"		}\n");
				write_external.append(
				"		if (msg is " + c_name + ") {\n" +
				"			w_" + m_name + "(" + c_name + "(msg), output); return;\n" +
				"		}\n");
				genCodecMethod(factory, e.getValue(), s_type, classes);
				
				new_msg.append(
				"			case " + s_type + " : return new " + c_name + ";\n");
				
			}
		}
		
		String ret = this.codec_template;
		ret = CUtil.replaceString(ret, "//package", 		codec_package);
		ret = CUtil.replaceString(ret, "//import", 			codec_import);
		ret = CUtil.replaceString(ret, "//className", 		codec_class_name);	
		ret = CUtil.replaceString(ret, "//version",			getVersion());
		ret = CUtil.replaceString(ret, "//getType", 		get_type.toString());
		ret = CUtil.replaceString(ret, "//createMessage", 	new_msg.toString());	
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
	protected void genCodecMethod(ExternalizableFactory factory, Class<?> msg, int msg_type, StringBuilder sb)
	{
		String c_name = msg.getCanonicalName();
		String s_name = msg.getSimpleName();
		String m_name = s_name + "_" + msg_type;
		
		StringBuilder read = new StringBuilder();
		StringBuilder write = new StringBuilder();
		for (Field f : msg.getFields()) {
			int modifiers = f.getModifiers();
			if (!Modifier.isStatic(modifiers)) {
				genCodecField(factory, msg, f, read, write);
			}
		}
		
		sb.append("//	----------------------------------------------------------------------------------------------------\n");
		sb.append("//	" + c_name + "\n");
		sb.append("//	----------------------------------------------------------------------------------------------------\n");
//		sb.append("	public static function new_" + m_name + "() : " + c_name + " {return new " + c_name + "();}\n");
		sb.append("	private function r_" + m_name + "(msg : " + c_name + ", input : NetDataInput) : void {\n");
		sb.append(read);
		sb.append("	}\n");
		sb.append("	private function w_" + m_name + "(msg : " + c_name + ", output : NetDataOutput) : void {\n");
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
	protected void genCodecField(ExternalizableFactory factory, Class<?> msg, Field f, StringBuilder read, StringBuilder write)
	{
		Class<?> 	f_type 		= f.getType();
		String 		f_name 		= "msg." + f.getName();
	
		// boolean -----------------------------------------------
		if (f_type.equals(boolean.class)) {
			read.append("		" + f_name + " = input.readBoolean();\n");
			write.append("		output.writeBoolean(" + f_name + ");\n");
		}
		else if (f_type.equals(boolean[].class)) {
			read.append("		" + f_name + " = input.readBooleanArray();\n");
			write.append("		output.writeBooleanArray(" + f_name + ");\n");
		}
		// byte -----------------------------------------------
		else if (f_type.equals(byte.class)) {
			read.append("		" + f_name + " = input.readByte();\n");
			write.append("		output.writeByte(" + f_name + ");\n");
		}
		else if (f_type.equals(byte[].class)) {
			read.append("		" + f_name + " = input.readByteArray();\n");
			write.append("		output.writeByteArray(" + f_name + ");\n");
		}
		// short -----------------------------------------------
		else if (f_type.equals(short.class)) {
			read.append("		" + f_name + " = input.readShort();\n");
			write.append("		output.writeShort(" + f_name + ");\n");
		}
		else if (f_type.equals(short[].class)) {
			read.append("		" + f_name + " = input.readShortArray();\n");
			write.append("		output.writeShortArray(" + f_name + ");\n");
		}
		// char -----------------------------------------------
		else if (f_type.equals(char.class)) {
			read.append("		" + f_name + " = input.readChar();\n");
			write.append("		output.writeChar(" + f_name + ");\n");
		}
		else if (f_type.equals(char[].class)) {
			read.append("		" + f_name + " = input.readCharArray();\n");
			write.append("		output.writeCharArray(" + f_name + ");\n");
		}
		// int -----------------------------------------------
		else if (f_type.equals(int.class)) {
			read.append("		" + f_name + " = input.readInt();\n");
			write.append("		output.writeInt(" + f_name + ");\n");
		}
		else if (f_type.equals(int[].class)) {
			read.append("		" + f_name + " = input.readIntArray();\n");
			write.append("		output.writeIntArray(" + f_name + ");\n");
		}
		// long -----------------------------------------------
//		else if (f_type.equals(long.class)) {
//			//read.append("		" + f_name + " = input.readLong();\n");
//			//write.append("		output.writeLong(" + f_name + ");\n");
//			read.append("		error: not support long type !\n");
//			write.append("		error: not support long type !\n");
//		}
//		else if (f_type.equals(long[].class)) {
//			//read.append("		" + f_name + " = input.readLongArray();\n");
//			//write.append("		output.writeLongArray(" + f_name + ");\n");
//			read.append("		error: not support long type !\n");
//			write.append("		error: not support long type !\n");
//		}
		// float -----------------------------------------------
		else if (f_type.equals(float.class)) {
			read.append("		" + f_name + " = input.readFloat();\n");
			write.append("		output.writeFloat(" + f_name + ");\n");
		}
		else if (f_type.equals(float[].class)) {
			read.append("		" + f_name + " = input.readFloatArray();\n");
			write.append("		output.writeFloatArray(" + f_name + ");\n");
		}
		// double -----------------------------------------------
		else if (f_type.equals(double.class)) {
			read.append("		" + f_name + " = input.readDouble();\n");
			write.append("		output.writeDouble(" + f_name + ");\n");
		}	
		else if (f_type.equals(double[].class)) {
			read.append("		" + f_name + " = input.readDoubleArray();\n");
			write.append("		output.writeDoubleArray(" + f_name + ");\n");
		}
		// String -----------------------------------------------
		else if (f_type.equals(String.class)) {
			read.append("		" + f_name + " = input.readJavaUTF();\n");
			write.append("		output.writeJavaUTF(" + f_name + ");\n");
		}	
		else if (f_type.equals(String[].class)) {
			read.append("		" + f_name + " = input.readUTFArray();\n");
			write.append("		output.writeUTFArray(" + f_name + ");\n");
		}	
		// ExternalizableMessage -----------------------------------------------
//		else if (ExternalizableMessage.class.isAssignableFrom(f_type)) {
//			read.append("		" + f_name + " = input.readExternal() as " + f_type.getCanonicalName() + ";\n");
//			write.append("		output.writeExternal(" + f_name + ");\n");
//		} 
		else if (MutualMessage.class.isAssignableFrom(f_type)) 
		{
			read.append("		" + f_name + " = input.readMutual() as " + f_type.getCanonicalName() + ";\n");
			write.append("		output.writeMutual(" + f_name + ");\n");
		}
		else if (f_type.isArray())
		{
			if (f_type.getComponentType().isArray()) 
			{
				String leaf_type = NetDataTypes.toTypeName(NetDataTypes.getArrayCompomentType(f_type, factory));
				read.append("		" + f_name + " = input.readAnyArray(" +
						"NetDataTypes." + leaf_type + ");\n");
				write.append("		output.writeAnyArray(" + f_name + ", " +
						"NetDataTypes." + leaf_type + ");\n");
			}
			else 
			{
				read.append("		" + f_name + " = input.readMutualArray();\n");
				write.append("		output.writeMutualArray(" + f_name + ");\n");
			}
		} 
		// Collections -----------------------------------------------
		else if (Collection.class.isAssignableFrom(f_type))
		{
			Class argType = ReflectUtil.getFieldGenericType(f, 0);
			byte compType = NetDataTypes.getNetType(argType, factory);
			
			read.append("		" + f_name + " = input.readCollection(" +
					"NetDataTypes." + NetDataTypes.toTypeName(compType) + ");\n");
			write.append("		output.writeCollection(" + f_name + ", " +
					"NetDataTypes." + NetDataTypes.toTypeName(compType) + ");\n");
		}
		else if (Map.class.isAssignableFrom(f_type)) 
		{
			Class keyType 		= ReflectUtil.getFieldGenericType(f, 0);
			byte  keyNetType 	= NetDataTypes.getNetType(keyType, factory);
			Class valueType 	= ReflectUtil.getFieldGenericType(f, 1);
			byte  valueNetType 	= NetDataTypes.getNetType(valueType, factory);
			
			read.append("		" + f_name + " = input.readMap("+
			"NetDataTypes." + NetDataTypes.toTypeName(keyNetType) + ", " +
			"NetDataTypes." + NetDataTypes.toTypeName(valueNetType) + ");\n");
			
			write.append("		output.writeMap(" + f_name + ", " +
			"NetDataTypes." + NetDataTypes.toTypeName(keyNetType) + ", " +
			"NetDataTypes." + NetDataTypes.toTypeName(valueNetType) + ");\n");
		}
		// Error -----------------------------------------------
		else {
			read.append("		Unsupported type : " + f_name + " " + f_type.getName() + "\n");
			write.append("		Unsupported type : " + f_name + " " + f_type.getName() + "\n");
		}
	}
	
//	------------------------------------------------------------------------------------------------------------------
	
	public TreeMap<Class<?>, String> genMutualMessages(ExternalizableFactory factory)
	{
		TreeMap<Class<?>, String> ret = new TreeMap<Class<?>, String>(factory);
		for (Entry<Integer, Class<?>> e : factory.getRegistTypes().entrySet()) {
			ret.put(e.getValue(), genMsgClass(factory, e.getValue(), e.getKey()));
		}
		return ret;
	}
	
	protected String genMsgClass(ExternalizableFactory factory, Class<?> msg, int msg_type) 
	{
		String	c_name 		= msg.getCanonicalName();
		Comment	c_comment 	= msg.getAnnotation(Comment.class);
		ASClass	c_as_class	= msg.getAnnotation(ASClass.class);
		String	c_dynamic	= (c_as_class != null && c_as_class.dynamic())?"dynamic":"";
		String 	s_name 		= msg.getSimpleName();
		String  ext_name	= "";
		String 	o_package 	= c_name.substring(0, c_name.length() - s_name.length() - 1);
		String 	s_comment	= c_comment != null ? CUtil.arrayToString(c_comment.value(),",","<br>") : "";
		
		if (factory.containsMessageType(msg.getSuperclass())) {
			ext_name = "extends " + msg.getSuperclass().getCanonicalName();
		}
		
		StringBuilder d_fields = new StringBuilder();
		
		for (Field f : msg.getDeclaredFields()) 
		{
			int modifiers = f.getModifiers();
			
			String	f_type_comment 	= f.getType().getCanonicalName();
			Comment f_comment 		= f.getAnnotation(Comment.class);
			
			String	f_cline 		= "Java type is : " +
					"<font color=#0000ff>" + f_type_comment + "</font>";
			
			if (f_comment != null) 
			{
				d_fields.append(
					"		/** " + CUtil.arrayToString(f_comment.value(),",","") + "<br>\n" +
					"		  * " + f_cline + "*/\n");
			}
			
			if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) 
			{
				Object value = null;
				try {
					value = f.get(null);
				} catch (Exception e) {}
				if (!Modifier.isPrivate(modifiers)) {
					d_fields.append(
					"		static public const " + f.getName() + " : " +  toASType(f.getType()) + " = " + value + ";");
				}
			}
			else
			{
				if (f_comment == null) {
					d_fields.append(
						"		/** " + f_cline + "*/\n");
				}
				d_fields.append(
						"		public var " + f.getName() + " : " +  toASType(f.getType()) + ";");
			}
			d_fields.append("\n\n");
		}

		StringBuilder d_functions = new StringBuilder();
		
		for (Method m : msg.getDeclaredMethods()) 
		{
			ASFunction m_function = m.getAnnotation(ASFunction.class);
			if (m_function != null) {
				String tabPrifix = CUtil.stringFill("\t", m_function.tabCount());
				for (String fline : m_function.value()) {
					d_functions.append(tabPrifix + fline + "\n");
				}
				d_functions.append("\n");
			}
		}
		
		StringBuilder d_init_args	= new StringBuilder();		
		StringBuilder d_init_fields = new StringBuilder();	
		StringBuilder d_init_commet	= new StringBuilder();	
		ArrayList<Field> argsFields = Fields.getSuperAndDeclaredFields(msg);
		if (!argsFields.isEmpty()) 
		{
			d_init_args.append("\n");
			d_init_commet.append("		/**\n");
			for (int i = 0; i<argsFields.size(); i++) 
			{
				Field f = argsFields.get(i);
				int modifiers = f.getModifiers();
				if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
				} else {
					String	f_type_comment 	= f.getType().getCanonicalName();
					d_init_commet.append(
							"		 * @param " + f.getName() + " as <font color=#0000ff>" + f_type_comment + "</font>");
					d_init_args.append(
							"			" + f.getName() + " : " + toASType(f.getType()) + " = " + genMsgFieldValue(f));
					d_init_fields.append(
							"			this." + f.getName() + " = " + f.getName()+";");
					if (i != argsFields.size() - 1) {
						d_init_args.append(",\n");
						d_init_fields.append("\n");
						d_init_commet.append("\n");
					}
				}
			}
			d_init_commet.append("		 */");
		}
		
		
		String ret = this.message_template;
		ret = CUtil.replaceString(ret, "//package", 		o_package);
		ret = CUtil.replaceString(ret, "//import", 			message_import);
		ret = CUtil.replaceString(ret, "//classComment", 	s_comment);
		ret = CUtil.replaceString(ret, "//classType", 		msg_type+"");
		ret = CUtil.replaceString(ret, "//dynamic", 		c_dynamic);
		ret = CUtil.replaceString(ret, "//className", 		s_name);
		ret = CUtil.replaceString(ret, "//extendsClass", 	ext_name); 
		ret = CUtil.replaceString(ret, "//classFullName", 	c_name);
		ret = CUtil.replaceString(ret, "//initComment",		d_init_commet.toString());
		ret = CUtil.replaceString(ret, "//initArgs",		d_init_args.toString());
		ret = CUtil.replaceString(ret, "//initFields",		d_init_fields.toString());
		ret = CUtil.replaceString(ret, "//fields",			d_fields.toString());
		ret = CUtil.replaceString(ret, "//asFunctions",		d_functions.toString());
		
		return ret;
	}
	
	protected Object genMsgFieldValue(Field f) 
	{
		Class<?> f_type = f.getType();
		if (f_type.equals(boolean.class)) {
			return false;
		} else if (f_type.isPrimitive()) {
			return 0;
		}
		return null;
	}
	
//	------------------------------------------------------------------------------------------------------------------
	
	public void genCodeFile(ExternalizableFactory factory, File as_src_root) throws IOException
	{
		File codec_output = new File(as_src_root, 
				CUtil.replaceString(codec_package+"." + codec_class_name, ".", File.separator)+".as");
		CFile.writeText(codec_output, genMutualMessageCodec(factory));
		System.out.println("genCodeFileAS :   Codec : " + codec_output.getCanonicalPath());
		for (Entry<Class<?>, String> e : genMutualMessages(factory).entrySet()) {
			File msg_output = new File(as_src_root, 
				CUtil.replaceString(e.getKey().getCanonicalName(), ".", File.separator)+".as");
			CFile.writeText(msg_output, e.getValue());
			System.out.println("genCodeFileAS : Message : " + msg_output.getCanonicalPath());
		}
	}
}
