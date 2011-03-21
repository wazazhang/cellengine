package com.net.flash.test
{
	import com.net.client.MessageFactory;
	import com.net.client.Message;
	import com.net.client.NetDataInput;
	import com.net.client.NetDataOutput;
	
	import com.net.flash.test.Messages.*;

	public class MessageCodec implements MessageFactory
	{
		public function	getType(message : Message) : int 
		{
//getType
		}
		
		public function	createMessage(type : int) : Message
		{
//createMessage
		}
		
		public function	readExternal(msg : Message,  input : NetDataInput) : void  
		{
		if (msg is com.net.flash.test.Messages.Data) {
			read_Data(com.net.flash.test.Messages.Data(msg), input);
			return;
		}
		if (msg is com.net.flash.test.Messages.Echo2Request) {
			read_Echo2Request(com.net.flash.test.Messages.Echo2Request(msg), input);
			return;
		}
		if (msg is com.net.flash.test.Messages.Echo2Response) {
			read_Echo2Response(com.net.flash.test.Messages.Echo2Response(msg), input);
			return;
		}
		if (msg is com.net.flash.test.Messages.EchoRequest) {
			read_EchoRequest(com.net.flash.test.Messages.EchoRequest(msg), input);
			return;
		}
		if (msg is com.net.flash.test.Messages.EchoResponse) {
			read_EchoResponse(com.net.flash.test.Messages.EchoResponse(msg), input);
			return;
		}

		}
		
		public function	writeExternal(msg : Message, output : NetDataOutput) : void  
		{
		if (msg is com.net.flash.test.Messages.Data) {
			write_Data(com.net.flash.test.Messages.Data(msg), output);
			return;
		}
		if (msg is com.net.flash.test.Messages.Echo2Request) {
			write_Echo2Request(com.net.flash.test.Messages.Echo2Request(msg), output);
			return;
		}
		if (msg is com.net.flash.test.Messages.Echo2Response) {
			write_Echo2Response(com.net.flash.test.Messages.Echo2Response(msg), output);
			return;
		}
		if (msg is com.net.flash.test.Messages.EchoRequest) {
			write_EchoRequest(com.net.flash.test.Messages.EchoRequest(msg), output);
			return;
		}
		if (msg is com.net.flash.test.Messages.EchoResponse) {
			write_EchoResponse(com.net.flash.test.Messages.EchoResponse(msg), output);
			return;
		}

		}
		
//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.Data
//	----------------------------------------------------------------------------------------------------
	function com_net_flash_test_Messages_Data() : void {}
	public function read_Data(msg : com.net.flash.test.Messages.Data, input : NetDataInput) : void {
		msg.message2 = input.readUTF();
		msg.d0 = input.readBoolean();
		msg.d1 = input.readByte();
		msg.d2 = input.readShort();
		msg.d3 = input.readChar();
		msg.d4 = input.readInt();
		msg.d5 = input.readFloat();
		msg.a_message2 = input.readUTFArray();
		msg.a_d0 = input.readBooleanArray();
		msg.a_d1 = input.readByteArray();
		msg.a_d2 = input.readShortArray();
		msg.a_d3 = input.readCharArray();
		msg.a_d4 = input.readIntArray();
		msg.a_d5 = input.readFloatArray();
	}
	public function write_Data(msg : com.net.flash.test.Messages.Data, output : NetDataOutput) : void {
		output.writeUTF(msg.message2);
		output.writeBoolean(msg.d0);
		output.writeByte(msg.d1);
		output.writeShort(msg.d2);
		output.writeChar(msg.d3);
		output.writeInt(msg.d4);
		output.writeFloat(msg.d5);
		output.writeUTFArray(msg.a_message2);
		output.writeBooleanArray(msg.a_d0);
		output.writeByteArray(msg.a_d1);
		output.writeShortArray(msg.a_d2);
		output.writeCharArray(msg.a_d3);
		output.writeIntArray(msg.a_d4);
		output.writeFloatArray(msg.a_d5);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.Echo2Request
//	----------------------------------------------------------------------------------------------------
	function com_net_flash_test_Messages_Echo2Request() : void {}
	public function read_Echo2Request(msg : com.net.flash.test.Messages.Echo2Request, input : NetDataInput) : void {
		msg.message = input.readUTF();
	}
	public function write_Echo2Request(msg : com.net.flash.test.Messages.Echo2Request, output : NetDataOutput) : void {
		output.writeUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.Echo2Response
//	----------------------------------------------------------------------------------------------------
	function com_net_flash_test_Messages_Echo2Response() : void {}
	public function read_Echo2Response(msg : com.net.flash.test.Messages.Echo2Response, input : NetDataInput) : void {
		msg.message = input.readUTF();
	}
	public function write_Echo2Response(msg : com.net.flash.test.Messages.Echo2Response, output : NetDataOutput) : void {
		output.writeUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.EchoRequest
//	----------------------------------------------------------------------------------------------------
	function com_net_flash_test_Messages_EchoRequest() : void {}
	public function read_EchoRequest(msg : com.net.flash.test.Messages.EchoRequest, input : NetDataInput) : void {
		msg.message = input.readUTF();
		msg.data = input.readExternal(1) as com.net.flash.test.Messages.Data;
		msg.datas = input.readExternalArray(1);
	}
	public function write_EchoRequest(msg : com.net.flash.test.Messages.EchoRequest, output : NetDataOutput) : void {
		output.writeUTF(msg.message);
		output.writeExternal(msg.data);
		output.writeExternalArray(msg.datas);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.EchoResponse
//	----------------------------------------------------------------------------------------------------
	function com_net_flash_test_Messages_EchoResponse() : void {}
	public function read_EchoResponse(msg : com.net.flash.test.Messages.EchoResponse, input : NetDataInput) : void {
		msg.message = input.readUTF();
		msg.data = input.readExternal(1) as com.net.flash.test.Messages.Data;
		msg.datas = input.readExternalArray(1);
	}
	public function write_EchoResponse(msg : com.net.flash.test.Messages.EchoResponse, output : NetDataOutput) : void {
		output.writeUTF(msg.message);
		output.writeExternal(msg.data);
		output.writeExternalArray(msg.datas);
	}



	}

}