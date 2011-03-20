package com.net.client.test
{
	import com.net.client.MessageFactory;

	public class FlashMessageCodec implements MessageFactory
	{
		public function FlashMessageCodec()
		{
		}
		
		/**获得此消息体的类型ID*/
		public function	getType(message:Message) : int 
		{
//getType
		}
		
		/**通过类型ID获得消息体*/
		public function	createMessage(type : int) : Message
		{
//createMessage
		}
		
		/**读取消息*/
		public function	readExternal(msg : Message,  input : IDataInput) : void  
		{
		if (msg.getClass().equals(com.net.flash.server.test.Messages.Data.class)) {
			read_Data((com.net.flash.server.test.Messages.Data)msg, in);
			return;
		}
		if (msg.getClass().equals(com.net.flash.server.test.Messages.Echo2Request.class)) {
			read_Echo2Request((com.net.flash.server.test.Messages.Echo2Request)msg, in);
			return;
		}
		if (msg.getClass().equals(com.net.flash.server.test.Messages.Echo2Response.class)) {
			read_Echo2Response((com.net.flash.server.test.Messages.Echo2Response)msg, in);
			return;
		}
		if (msg.getClass().equals(com.net.flash.server.test.Messages.EchoRequest.class)) {
			read_EchoRequest((com.net.flash.server.test.Messages.EchoRequest)msg, in);
			return;
		}
		if (msg.getClass().equals(com.net.flash.server.test.Messages.EchoResponse.class)) {
			read_EchoResponse((com.net.flash.server.test.Messages.EchoResponse)msg, in);
			return;
		}

		}
		
		/**写入消息*/
		public function	writeExternal(msg : Message, output : IDataOutput) : void  
		{
		if (msg.getClass().equals(com.net.flash.server.test.Messages.Data.class)) {
			write_Data((com.net.flash.server.test.Messages.Data)msg, out);
			return;
		}
		if (msg.getClass().equals(com.net.flash.server.test.Messages.Echo2Request.class)) {
			write_Echo2Request((com.net.flash.server.test.Messages.Echo2Request)msg, out);
			return;
		}
		if (msg.getClass().equals(com.net.flash.server.test.Messages.Echo2Response.class)) {
			write_Echo2Response((com.net.flash.server.test.Messages.Echo2Response)msg, out);
			return;
		}
		if (msg.getClass().equals(com.net.flash.server.test.Messages.EchoRequest.class)) {
			write_EchoRequest((com.net.flash.server.test.Messages.EchoRequest)msg, out);
			return;
		}
		if (msg.getClass().equals(com.net.flash.server.test.Messages.EchoResponse.class)) {
			write_EchoResponse((com.net.flash.server.test.Messages.EchoResponse)msg, out);
			return;
		}

		}
		
//	----------------------------------------------------------------------------------------------------
//	com.net.flash.server.test.Messages.Data
//	----------------------------------------------------------------------------------------------------
	void com_net_flash_server_test_Messages_Data(){}
	public void read_Data(com.net.flash.server.test.Messages.Data msg, NetDataInput in) throws IOException {
		msg.message2 = in.readUTF();
		msg.d0 = in.readBoolean();
		msg.d1 = in.readByte();
		msg.d2 = in.readShort();
		msg.d3 = in.readChar();
		msg.d4 = in.readInt();
		msg.d5 = in.readFloat();
		msg.a_message2 = in.readUTFArray();
		msg.a_d0 = in.readBooleanArray();
		msg.a_d1 = in.readByteArray();
		msg.a_d2 = in.readShortArray();
		msg.a_d3 = in.readCharArray();
		msg.a_d4 = in.readIntArray();
		msg.a_d5 = in.readFloatArray();
	}
	public void write_Data(com.net.flash.server.test.Messages.Data msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.message2);
		out.writeBoolean(msg.d0);
		out.writeByte(msg.d1);
		out.writeShort(msg.d2);
		out.writeChar(msg.d3);
		out.writeInt(msg.d4);
		out.writeFloat(msg.d5);
		out.writeUTFArray(msg.a_message2);
		out.writeBooleanArray(msg.a_d0);
		out.writeByteArray(msg.a_d1);
		out.writeShortArray(msg.a_d2);
		out.writeCharArray(msg.a_d3);
		out.writeIntArray(msg.a_d4);
		out.writeFloatArray(msg.a_d5);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.server.test.Messages.Echo2Request
//	----------------------------------------------------------------------------------------------------
	void com_net_flash_server_test_Messages_Echo2Request(){}
	public void read_Echo2Request(com.net.flash.server.test.Messages.Echo2Request msg, NetDataInput in) throws IOException {
		msg.message = in.readUTF();
	}
	public void write_Echo2Request(com.net.flash.server.test.Messages.Echo2Request msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.server.test.Messages.Echo2Response
//	----------------------------------------------------------------------------------------------------
	void com_net_flash_server_test_Messages_Echo2Response(){}
	public void read_Echo2Response(com.net.flash.server.test.Messages.Echo2Response msg, NetDataInput in) throws IOException {
		msg.message = in.readUTF();
	}
	public void write_Echo2Response(com.net.flash.server.test.Messages.Echo2Response msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.server.test.Messages.EchoRequest
//	----------------------------------------------------------------------------------------------------
	void com_net_flash_server_test_Messages_EchoRequest(){}
	public void read_EchoRequest(com.net.flash.server.test.Messages.EchoRequest msg, NetDataInput in) throws IOException {
		msg.message = in.readUTF();
		msg.data = in.readExternal(com.net.flash.server.test.Messages.Data.class);
		msg.datas = in.readExternalArray(com.net.flash.server.test.Messages.Data.class);
	}
	public void write_EchoRequest(com.net.flash.server.test.Messages.EchoRequest msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.message);
		out.writeExternal(msg.data);
		out.writeExternalArray(msg.datas);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.server.test.Messages.EchoResponse
//	----------------------------------------------------------------------------------------------------
	void com_net_flash_server_test_Messages_EchoResponse(){}
	public void read_EchoResponse(com.net.flash.server.test.Messages.EchoResponse msg, NetDataInput in) throws IOException {
		msg.message = in.readUTF();
		msg.data = in.readExternal(com.net.flash.server.test.Messages.Data.class);
		msg.datas = in.readExternalArray(com.net.flash.server.test.Messages.Data.class);
	}
	public void write_EchoResponse(com.net.flash.server.test.Messages.EchoResponse msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.message);
		out.writeExternal(msg.data);
		out.writeExternalArray(msg.datas);
	}



	}

}