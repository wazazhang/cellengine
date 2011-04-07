package com.net.flash.test
{
	import com.net.client.MessageFactory;
	import com.net.client.Message;
	import com.net.client.NetDataInput;
	import com.net.client.NetDataOutput;
	
	import com.net.flash.test.Messages.*;

	/**
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class MessageCodec implements MessageFactory
	{
		public function	getType(msg : Message) : int 
		{
			if (msg is com.net.flash.test.Messages.Data) return 1;
			if (msg is com.net.flash.test.Messages.Echo2Request) return 2;
			if (msg is com.net.flash.test.Messages.Echo2Response) return 3;
			if (msg is com.net.flash.test.Messages.EchoNotify) return 4;
			if (msg is com.net.flash.test.Messages.EchoRequest) return 5;
			if (msg is com.net.flash.test.Messages.EchoResponse) return 6;

			return 0;
		}
		
		public function	createMessage(type : int) : Message
		{
			switch(type)
			{
			case 1 : return new com.net.flash.test.Messages.Data;
			case 2 : return new com.net.flash.test.Messages.Echo2Request;
			case 3 : return new com.net.flash.test.Messages.Echo2Response;
			case 4 : return new com.net.flash.test.Messages.EchoNotify;
			case 5 : return new com.net.flash.test.Messages.EchoRequest;
			case 6 : return new com.net.flash.test.Messages.EchoResponse;

			}
			return null;
		}
		
		public function	readExternal(msg : Message,  input : NetDataInput) : void  
		{
		if (msg is com.net.flash.test.Messages.Data) {
			r_Data_1(com.net.flash.test.Messages.Data(msg), input); return;
		}
		if (msg is com.net.flash.test.Messages.Echo2Request) {
			r_Echo2Request_2(com.net.flash.test.Messages.Echo2Request(msg), input); return;
		}
		if (msg is com.net.flash.test.Messages.Echo2Response) {
			r_Echo2Response_3(com.net.flash.test.Messages.Echo2Response(msg), input); return;
		}
		if (msg is com.net.flash.test.Messages.EchoNotify) {
			r_EchoNotify_4(com.net.flash.test.Messages.EchoNotify(msg), input); return;
		}
		if (msg is com.net.flash.test.Messages.EchoRequest) {
			r_EchoRequest_5(com.net.flash.test.Messages.EchoRequest(msg), input); return;
		}
		if (msg is com.net.flash.test.Messages.EchoResponse) {
			r_EchoResponse_6(com.net.flash.test.Messages.EchoResponse(msg), input); return;
		}

		}
		
		public function	writeExternal(msg : Message, output : NetDataOutput) : void  
		{
		if (msg is com.net.flash.test.Messages.Data) {
			w_Data_1(com.net.flash.test.Messages.Data(msg), output); return;
		}
		if (msg is com.net.flash.test.Messages.Echo2Request) {
			w_Echo2Request_2(com.net.flash.test.Messages.Echo2Request(msg), output); return;
		}
		if (msg is com.net.flash.test.Messages.Echo2Response) {
			w_Echo2Response_3(com.net.flash.test.Messages.Echo2Response(msg), output); return;
		}
		if (msg is com.net.flash.test.Messages.EchoNotify) {
			w_EchoNotify_4(com.net.flash.test.Messages.EchoNotify(msg), output); return;
		}
		if (msg is com.net.flash.test.Messages.EchoRequest) {
			w_EchoRequest_5(com.net.flash.test.Messages.EchoRequest(msg), output); return;
		}
		if (msg is com.net.flash.test.Messages.EchoResponse) {
			w_EchoResponse_6(com.net.flash.test.Messages.EchoResponse(msg), output); return;
		}

		}
		
//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.Data
//	----------------------------------------------------------------------------------------------------
	function new_Data_1() : com.net.flash.test.Messages.Data {return new com.net.flash.test.Messages.Data();}
	private function r_Data_1(msg : com.net.flash.test.Messages.Data, input : NetDataInput) : void {
		msg.message2 = input.readJavaUTF();
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
	private function w_Data_1(msg : com.net.flash.test.Messages.Data, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message2);
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
	function new_Echo2Request_2() : com.net.flash.test.Messages.Echo2Request {return new com.net.flash.test.Messages.Echo2Request();}
	private function r_Echo2Request_2(msg : com.net.flash.test.Messages.Echo2Request, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_Echo2Request_2(msg : com.net.flash.test.Messages.Echo2Request, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.Echo2Response
//	----------------------------------------------------------------------------------------------------
	function new_Echo2Response_3() : com.net.flash.test.Messages.Echo2Response {return new com.net.flash.test.Messages.Echo2Response();}
	private function r_Echo2Response_3(msg : com.net.flash.test.Messages.Echo2Response, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_Echo2Response_3(msg : com.net.flash.test.Messages.Echo2Response, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.EchoNotify
//	----------------------------------------------------------------------------------------------------
	function new_EchoNotify_4() : com.net.flash.test.Messages.EchoNotify {return new com.net.flash.test.Messages.EchoNotify();}
	private function r_EchoNotify_4(msg : com.net.flash.test.Messages.EchoNotify, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_EchoNotify_4(msg : com.net.flash.test.Messages.EchoNotify, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.EchoRequest
//	----------------------------------------------------------------------------------------------------
	function new_EchoRequest_5() : com.net.flash.test.Messages.EchoRequest {return new com.net.flash.test.Messages.EchoRequest();}
	private function r_EchoRequest_5(msg : com.net.flash.test.Messages.EchoRequest, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
		msg.data = input.readExternal() as com.net.flash.test.Messages.Data;
		msg.datas = input.readExternalArray();
		msg.datas2 = input.readExternalArray();
	}
	private function w_EchoRequest_5(msg : com.net.flash.test.Messages.EchoRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
		output.writeExternal(msg.data);
		output.writeExternalArray(msg.datas);
		output.writeExternalArray(msg.datas2);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.EchoResponse
//	----------------------------------------------------------------------------------------------------
	function new_EchoResponse_6() : com.net.flash.test.Messages.EchoResponse {return new com.net.flash.test.Messages.EchoResponse();}
	private function r_EchoResponse_6(msg : com.net.flash.test.Messages.EchoResponse, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
		msg.data = input.readExternal() as com.net.flash.test.Messages.Data;
		msg.datas = input.readExternalArray();
	}
	private function w_EchoResponse_6(msg : com.net.flash.test.Messages.EchoResponse, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
		output.writeExternal(msg.data);
		output.writeExternalArray(msg.datas);
	}



	}

}