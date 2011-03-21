package com.net.flash.test;

import java.io.File;
import java.io.IOException;

import com.cell.CIO;
import com.cell.io.CFile;
import com.cell.j2se.CAppBridge;
import com.net.ExternalizableMessage;
import com.net.MessageHeader;
import com.net.NetDataInput;
import com.net.NetDataOutput;
import com.net.flash.message.FlashMessage;
import com.net.flash.message.FlashMessageCodeGenerator;
import com.net.flash.message.FlashMessageFactory;
import com.net.mutual.MutualMessageCodeGenerator.MutualMessageCodeGeneratorJava;

public class Messages 
{
	public static class Data extends FlashMessage
	{
		public String		message2;
		public boolean 		d0;
		public byte 		d1;
		public short 		d2;
		public char 		d3;
		public int	 		d4;
//		public long	 		d5;
		public float 		d5;
//		public double 		d6;
		
		public String[]		a_message2;
		public boolean[]	a_d0;
		public byte[] 		a_d1;
		public short[] 		a_d2;
		public char[] 		a_d3;
		public int[]	 	a_d4;
//		public long[]	 	a_d5;
		public float[]	 	a_d5;
//		public double[] 	a_d6;
	}
	
	public static class Echo2Request extends FlashMessage
	{
		public String message;		
		public Echo2Request(String message) {
			this.message = message;
		}
		public Echo2Request() {}
		@Override
		public String toString() {
			return message+"";
		}
	}
	
	public static class Echo2Response extends FlashMessage
	{
		public String message;
		public Echo2Response(String message) {
			this.message = message;
		}
		public Echo2Response() {}
		@Override
		public String toString() {
			return message+"";
		}
	}
	
	public static class EchoRequest extends FlashMessage
	{
		public String message;
		public Data data;
		public Data[] datas;
		public EchoRequest(String message) {
			this.message = message;
		}
		public EchoRequest() {}
		@Override
		public String toString() {
			return message+"";
		}
	}
	
	public static class EchoResponse extends FlashMessage
	{
		public String message;
		public Data data;
		public Data[] datas;
		public EchoResponse(String message) {
			this.message = message;
		}
		public EchoResponse() {}
		@Override
		public String toString() {
			return message+"";
		}
	}

	public static void main(String[] args)
	{
		CAppBridge.init();
		FlashMessageFactory factory = new FlashMessageFactory(null, Messages.class);
		{
			MutualMessageCodeGeneratorJava gen_java = new MutualMessageCodeGeneratorJava(
					CIO.readAllText("/com/net/flash/test/MutualMessageCodecJava.txt"));
			CFile.writeText(new File("./flash/com/net/flash/test/MutualMessageCodecJava.java"), 
					gen_java.genMutualMessageCodec(factory.getRegistTypes())
			);
		}{
			FlashMessageCodeGenerator gen_as = new FlashMessageCodeGenerator();
			CFile.writeText(new File("./flash/com/net/flash/test/MutualMessageCodecAS.as"), 
					gen_as.genMutualMessageCodec(factory.getRegistTypes())
			);
		}

	}
}
