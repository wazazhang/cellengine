package com.net.client
{

	public interface MessageFactory
	{
		
		/**获得此消息体的类型ID*/
		function	getType(message:Message) : int;
		
		/**通过类型ID获得消息体*/
		function	createMessage(type:int) : Message;

		/**读取消息*/
		function	readExternal(msg : Message,  input : NetDataInput) : void;
		
		/**写入消息*/
		function	writeExternal(msg : Message, output : NetDataOutput) : void;
		
	}
	
//	class IO
//	{
//		
//		
//
//		public void writeBooleanArray(boolean[] bools) throws IOException;
//		
//		public void writeCharArray(char[] chars) throws IOException;
//		
//		public void writeByteArray(byte[] bytes) throws IOException;
//		
//		public void writeShortArray(short[] shorts) throws IOException;
//		
//		public void writeIntArray(int[] ints) throws IOException;
//		
//		public void writeLongArray(long[] longs) throws IOException;
//		
//		public void writeFloatArray(float[] floats) throws IOException;
//		
//		public void writeDoubleArray(double[] doubles) throws IOException;
//		
//		public void writeUTFArray(String[] data) throws IOException;
//	
//		
//		public boolean[] 	readBooleanArray() throws IOException;
//		
//		public char[] 		readCharArray() throws IOException;
//		
//		public byte[] 		readByteArray() throws IOException;
//		
//		public short[] 		readShortArray() throws IOException;
//		
//		public int[] 		readIntArray() throws IOException;
//		
//		public long[] 		readLongArray() throws IOException;
//		
//		public float[] 		readFloatArray() throws IOException;
//		
//		public double[] 	readDoubleArray() throws IOException;
//		
//		public String[] 	readUTFArray() throws IOException;
//				
//	}
	
}