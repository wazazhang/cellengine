package com.net.client.test
{
	import com.net.client.Message;
	
	
	
	public class Data extends Message
	{
		public String		message2;
		public boolean 		d0;
		public byte 		d1;
		public short 		d2;
		public char 		d3;
		public int	 		d4;
		public long	 		d5;
		public double 		d6;
		
		public String[]		a_message2;
		public boolean[]	a_d0;
		public byte[] 		a_d1;
		public short[] 		a_d2;
		public char[] 		a_d3;
		public int[]	 	a_d4;
		public long[]	 	a_d5;
		public double[] 	a_d6;
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
}