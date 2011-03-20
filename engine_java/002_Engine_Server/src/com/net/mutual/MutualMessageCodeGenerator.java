package com.net.mutual;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Map.Entry;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.reflect.Fields;
import com.cell.reflect.Parser;
import com.net.ExternalizableFactory;
import com.net.ExternalizableMessage;
import com.net.MessageHeader;
import com.net.NetDataInput;
import com.net.NetDataOutput;


public interface MutualMessageCodeGenerator
{
	public String genMutualMessageCodec(Map<Integer, Class<?>> regist_types);

	
	public static class MutualMessageCodeGeneratorJava implements MutualMessageCodeGenerator
	{
		String template = CIO.readAllText("com/net/mutual/MutualMessageCodecJava.txt");
		
		public MutualMessageCodeGeneratorJava(String template) {
			if (template != null) {
				this.template = template;
			}
		}
		
		public String genMutualMessageCodec(Map<Integer, Class<?>> regist_types)
		{
			StringBuilder read_external		= new StringBuilder();
			StringBuilder write_external	= new StringBuilder();
			StringBuilder classes			= new StringBuilder();

			for (Entry<Integer, Class<?>> e : regist_types.entrySet()) 
			{
				String c_name = e.getValue().getCanonicalName();
				String s_name = e.getValue().getSimpleName();
				read_external.append(
				"		if (msg.getClass().equals(" + c_name + ".class)) {\n" +
				"			read_" + s_name + "((" + c_name + ")msg, in);\n" +
				"			return;\n" +
				"		}\n");
				write_external.append(
				"		if (msg.getClass().equals(" + c_name + ".class)) {\n" +
				"			write_" + s_name + "((" + c_name + ")msg, out);\n" +
				"			return;\n" +
				"		}\n");
				genMethod(e.getValue(), classes);
			}
			
			String ret = this.template;
			ret = CUtil.replaceString(ret, "//readExternal",  read_external.toString());
			ret = CUtil.replaceString(ret, "//writeExternal", write_external.toString());
			ret = CUtil.replaceString(ret, "//classes",       classes.toString());
			return ret;
		}
		
		/**
		 * 产生一个方法，用于读写消息。
		 * @param msg
		 * @param sb
		 */
		public void genMethod(Class<?> msg, StringBuilder sb)
		{
			String c_name = msg.getCanonicalName();
			String s_name = msg.getSimpleName();
			String m_name = c_name.replace('.', '_');
			
			StringBuilder read = new StringBuilder();
			StringBuilder write = new StringBuilder();
			for (Field f : msg.getFields()) {
				int modifiers = f.getModifiers();
				if (!Modifier.isStatic(modifiers)) {
					genField(msg, f, read, write);
				}
			}
			
			sb.append("//	----------------------------------------------------------------------------------------------------\n");
			sb.append("//	" + c_name + "\n");
			sb.append("//	----------------------------------------------------------------------------------------------------\n");
			sb.append("	void " + m_name + "(){}\n");
			sb.append("	public void read_" + s_name + "(" + c_name + " msg, NetDataInput in) throws IOException {\n");
			sb.append(read);
			sb.append("	}\n");
			sb.append("	public void write_" + s_name + "(" + c_name + " msg, NetDataOutput out) throws IOException {\n");
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
		public void genField(Class<?> msg, Field f, StringBuilder read, StringBuilder write)
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
			else if (f_type.isArray()) {
				read.append("		" + f_name + " = in.readExternalArray(" + 
						f_type.getComponentType().getCanonicalName() + ".class);\n");
				write.append("		out.writeExternalArray(" + f_name + ");\n");
			} 
			// Error -----------------------------------------------
			else {
				read.append("		Unsupported type : " + f_name + " " + f_type.getName() + "\n");
				write.append("		Unsupported type : " + f_name + " " + f_type.getName() + "\n");
			}
		}
	}
}
