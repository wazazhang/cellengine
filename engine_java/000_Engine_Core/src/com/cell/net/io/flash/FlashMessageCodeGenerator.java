package com.cell.net.io.flash;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
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

	public String genMutualMessageCodec(ExternalizableFactory factory)
	{
		StringBuilder read_external		= new StringBuilder();
		StringBuilder write_external	= new StringBuilder();
		StringBuilder classes			= new StringBuilder();
		StringBuilder get_type			= new StringBuilder();
		StringBuilder new_msg			= new StringBuilder();
		
		for (Entry<Integer, Class<?>> e : factory.getRegistTypes().entrySet()) 
		{
			String c_name = e.getValue().getCanonicalName();
			String s_name = e.getValue().getSimpleName();
			int    s_type = e.getKey();
			String m_name = s_name + "_" + s_type;
			
			read_external.append(
			"		if (msg is " + c_name + ") {\n" +
			"			r_" + m_name + "(" + c_name + "(msg), input); return;\n" +
			"		}\n");
			write_external.append(
			"		if (msg is " + c_name + ") {\n" +
			"			w_" + m_name + "(" + c_name + "(msg), output); return;\n" +
			"		}\n");
			genCodecMethod(factory, e.getValue(), s_type, classes);
			get_type.append(
			"			if (msg is " + c_name + ") return " + s_type + ";\n");
			new_msg.append(
			"			case " + s_type + " : return new " + c_name + ";\n");
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
		sb.append("	function new_" + m_name + "() : " + c_name + " {return new " + c_name + "();}\n");
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
		else if (f_type.equals(long.class)) {
			read.append("		" + f_name + " = input.readLong();\n");
			write.append("		output.writeLong(" + f_name + ");\n");
		}
		else if (f_type.equals(long[].class)) {
			read.append("		" + f_name + " = input.readLongArray();\n");
			write.append("		output.writeLongArray(" + f_name + ");\n");
		}
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
		else if (MutualMessage.class.isAssignableFrom(f_type)) {
			read.append("		" + f_name + " = input.readExternal() as " + f_type.getCanonicalName() + ";\n");
			write.append("		output.writeExternal(" + f_name + ");\n");
		}
		else if (f_type.isArray()) {
			if (f_type.getComponentType().isArray()) {
				String leaf_type = NetDataTypes.toTypeName(NetDataTypes.getArrayCompomentType(f_type, factory));
				read.append("		" + f_name + " = input.readAnyArray(" +
						"NetDataTypes." + leaf_type + ");\n");
				write.append("		output.writeAnyArray(" + f_name + ", " +
						"NetDataTypes." + leaf_type + ");\n");
			} else {
				read.append("		" + f_name + " = input.readExternalArray();\n");
				write.append("		output.writeExternalArray(" + f_name + ");\n");
			}
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
		String 	s_name 		= msg.getSimpleName();
		String 	o_package 	= c_name.substring(0, c_name.length() - s_name.length() - 1);
		String 	s_comment	= c_comment != null ? CUtil.arrayToString(c_comment.value(),",","<br>") : "";
		
		StringBuilder d_fields 		= new StringBuilder();
		StringBuilder d_init_args	= new StringBuilder();		
		StringBuilder d_init_fields = new StringBuilder();	
		StringBuilder d_init_commet	= new StringBuilder();	
		
		ArrayList<Field> fields = new ArrayList<Field>();
		for (Field f : msg.getFields()) {
			fields.add(f);
		}
		int i = 0;
		d_init_commet.append("		/**\n");
		for (Field f : fields) {
			int modifiers = f.getModifiers();
			String	f_type_comment 	= f.getType().getCanonicalName();
			Comment f_comment 		= f.getAnnotation(Comment.class);
			String	f_leaf_name 	= NetDataTypes.toTypeName(
					NetDataTypes.getArrayCompomentType(f.getType(), factory));
			String	f_cline 		= "Java type is : " +
					"<font color=#0000ff>" + f_type_comment + "</font>";
			if (f_comment != null) {
			d_fields.append(
					"		/** " + CUtil.arrayToString(f_comment.value(),",","") + "<br>\n" +
					"		  * " + f_cline + "*/\n");
			} else {
			d_fields.append(
					"		/** " + f_cline + "*/\n");
			}
			d_fields.append(
					"		[JavaType(name=\""+f_type_comment+"\", leaf_type=NetDataTypes."+f_leaf_name +")]\n");
			if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
				Object value = null;
				try {
					value = f.get(null);
				} catch (Exception e) {}
				d_fields.append(
						"		static public const " + genMsgField(factory, f) + " = " + value + ";");
			} else {
				d_fields.append(
						"		public var " + genMsgField(factory, f) + ";");
				d_init_commet.append(
						"		 * @param " + f.getName() + " as <font color=#0000ff>" + f_type_comment + "</font>");
				d_init_args.append(
						"			" + genMsgField(factory, f) + " = " + genMsgFieldValue(f));
				d_init_fields.append(
						"			this." + f.getName() + " = " + f.getName()+";");
				if (i < fields.size() - 1) {
					d_init_args.append(",\n");
					d_init_fields.append("\n");
					d_init_commet.append("\n");
				}
			}
			if (i < fields.size() - 1) {
				d_fields.append("\n");
			}
			i++;
		}
		d_init_commet.append("		 */");
		
		
		String ret = this.message_template;
		ret = CUtil.replaceString(ret, "//package", 		o_package);
		ret = CUtil.replaceString(ret, "//import", 			message_import);
		ret = CUtil.replaceString(ret, "//classComment", 	s_comment);
		ret = CUtil.replaceString(ret, "//classType", 		msg_type+"");
		ret = CUtil.replaceString(ret, "//className", 		s_name);
		ret = CUtil.replaceString(ret, "//classFullName", 	c_name);
		ret = CUtil.replaceString(ret, "//initComment",		d_init_commet.toString());
		ret = CUtil.replaceString(ret, "//initArgs",		d_init_args.toString());
		ret = CUtil.replaceString(ret, "//initFields",		d_init_fields.toString());
		ret = CUtil.replaceString(ret, "//fields",			d_fields.toString());
		return ret;
	}
	
	
	protected String genMsgField(ExternalizableFactory factory, Field f) 
	{
		Class<?> 	f_type 		= f.getType();
	
		// boolean -----------------------------------------------
		if (f_type.equals(boolean.class)) {
			return f.getName() + " :  Boolean";
		}
		// byte -----------------------------------------------
		else if (f_type.equals(byte.class)) {
			return f.getName() + " :  int";
		}
		// short -----------------------------------------------
		else if (f_type.equals(short.class)) {
			return f.getName() + " :  int";
		}
		// char -----------------------------------------------
		else if (f_type.equals(char.class)) {
			return f.getName() + " :  int";
		}
		// int -----------------------------------------------
		else if (f_type.equals(int.class)) {
			return f.getName() + " :  int";
		}
		// long -----------------------------------------------
		else if (f_type.equals(long.class)) {
			return f.getName() + " : Number";
		}
		// float -----------------------------------------------
		else if (f_type.equals(float.class)) {
			return f.getName() + " :  Number";
		}
		// double -----------------------------------------------
		else if (f_type.equals(double.class)) {
			return f.getName() + " : Number";
		}	
		// String -----------------------------------------------
		else if (f_type.equals(String.class)) {
			return f.getName() + " :  String";
		}
		// ExternalizableMessage -----------------------------------------------
//		else if (ExternalizableMessage.class.isAssignableFrom(f_type)) {
//			return f.getName() + " :  " + f_type.getCanonicalName()+"";
//		} 
		else if (MutualMessage.class.isAssignableFrom(f_type)) {
			return f.getName() + " :  " + f_type.getCanonicalName()+"";
		} 
		else if (f_type.isArray()) {
			return f.getName() + " :  Array";
		} 
		// Error -----------------------------------------------
		else {
			return "		Unsupported type : " + f.getName() + " " + f_type.getName();
		}
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
