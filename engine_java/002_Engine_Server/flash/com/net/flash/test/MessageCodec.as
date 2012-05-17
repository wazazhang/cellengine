package com.net.flash.test
{
	import com.cell.net.io.MessageFactory;
	import com.cell.net.io.MutualMessage;
	import com.cell.net.io.NetDataInput;
	import com.cell.net.io.NetDataOutput;
	import com.cell.net.io.NetDataTypes;
	import com.cell.util.Map;

	import flash.utils.getQualifiedClassName;	
	import com.net.flash.test.Messages.*;

	/**
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class MessageCodec implements MessageFactory
	{
	
		public function getVersion() : String{
			return "1337227113546";
		}
	
		public function	getType(msg : Object) : int 
		{
			var cname : String = getQualifiedClassName(msg);
			
			if (cname == "com.net.flash.test.Messages::Data") return 1;
			if (cname == "com.net.flash.test.Messages::Echo2Request") return 2;
			if (cname == "com.net.flash.test.Messages::Echo2Response") return 3;
			if (cname == "com.net.flash.test.Messages::EchoNotify") return 4;
			if (cname == "com.net.flash.test.Messages::EchoRequest") return 5;
			if (cname == "com.net.flash.test.Messages::EchoResponse") return 6;
			if (cname == "com.net.flash.test.Messages::StateInBattle") return 7;
			if (cname == "com.net.flash.test.Messages::TargetState") return 8;

			return 0;
		}
		
		public function	createMessage(type : int) : MutualMessage
		{
			switch(type)
			{
			case 1 : return new com.net.flash.test.Messages.Data;
			case 2 : return new com.net.flash.test.Messages.Echo2Request;
			case 3 : return new com.net.flash.test.Messages.Echo2Response;
			case 4 : return new com.net.flash.test.Messages.EchoNotify;
			case 5 : return new com.net.flash.test.Messages.EchoRequest;
			case 6 : return new com.net.flash.test.Messages.EchoResponse;
			case 7 : return new com.net.flash.test.Messages.StateInBattle;
			case 8 : return new com.net.flash.test.Messages.TargetState;

			}
			return null;
		}
		
		public function	readExternal(msg : MutualMessage,  input : NetDataInput) : void  
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
		if (msg is com.net.flash.test.Messages.StateInBattle) {
			r_StateInBattle_7(com.net.flash.test.Messages.StateInBattle(msg), input); return;
		}
		if (msg is com.net.flash.test.Messages.TargetState) {
			r_TargetState_8(com.net.flash.test.Messages.TargetState(msg), input); return;
		}

		}
		
		public function	writeExternal(msg : MutualMessage, output : NetDataOutput) : void  
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
		if (msg is com.net.flash.test.Messages.StateInBattle) {
			w_StateInBattle_7(com.net.flash.test.Messages.StateInBattle(msg), output); return;
		}
		if (msg is com.net.flash.test.Messages.TargetState) {
			w_TargetState_8(com.net.flash.test.Messages.TargetState(msg), output); return;
		}

		}
		
//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.Data
//	----------------------------------------------------------------------------------------------------
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
		msg.b_d5 = input.readAnyArray(NetDataTypes.TYPE_FLOAT);
		Unsupported type : msg.enum_ts com.net.flash.test.Messages$TargetState
		Unsupported type : msg.enum_sb com.net.flash.test.Messages$StateInBattle
		msg.enums_ts = input.readMutualArray();
		msg.enums_sb = input.readMutualArray();
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
		output.writeAnyArray(msg.b_d5, NetDataTypes.TYPE_FLOAT);
		Unsupported type : msg.enum_ts com.net.flash.test.Messages$TargetState
		Unsupported type : msg.enum_sb com.net.flash.test.Messages$StateInBattle
		output.writeMutualArray(msg.enums_ts);
		output.writeMutualArray(msg.enums_sb);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.Echo2Request
//	----------------------------------------------------------------------------------------------------
	private function r_Echo2Request_2(msg : com.net.flash.test.Messages.Echo2Request, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_Echo2Request_2(msg : com.net.flash.test.Messages.Echo2Request, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.Echo2Response
//	----------------------------------------------------------------------------------------------------
	private function r_Echo2Response_3(msg : com.net.flash.test.Messages.Echo2Response, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_Echo2Response_3(msg : com.net.flash.test.Messages.Echo2Response, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.EchoNotify
//	----------------------------------------------------------------------------------------------------
	private function r_EchoNotify_4(msg : com.net.flash.test.Messages.EchoNotify, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_EchoNotify_4(msg : com.net.flash.test.Messages.EchoNotify, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.EchoRequest
//	----------------------------------------------------------------------------------------------------
	private function r_EchoRequest_5(msg : com.net.flash.test.Messages.EchoRequest, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
		msg.data = input.readMutual() as com.net.flash.test.Messages.Data;
		msg.datas = input.readMutualArray();
		msg.datas2 = input.readAnyArray(NetDataTypes.TYPE_MUTUAL);
	}
	private function w_EchoRequest_5(msg : com.net.flash.test.Messages.EchoRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
		output.writeMutual(msg.data);
		output.writeMutualArray(msg.datas);
		output.writeAnyArray(msg.datas2, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.EchoResponse
//	----------------------------------------------------------------------------------------------------
	private function r_EchoResponse_6(msg : com.net.flash.test.Messages.EchoResponse, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
		msg.data = input.readMutual() as com.net.flash.test.Messages.Data;
		msg.datas = input.readMutualArray();
	}
	private function w_EchoResponse_6(msg : com.net.flash.test.Messages.EchoResponse, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
		output.writeMutual(msg.data);
		output.writeMutualArray(msg.datas);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.StateInBattle
//	----------------------------------------------------------------------------------------------------
	private function r_StateInBattle_7(msg : com.net.flash.test.Messages.StateInBattle, input : NetDataInput) : void {
	}
	private function w_StateInBattle_7(msg : com.net.flash.test.Messages.StateInBattle, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.TargetState
//	----------------------------------------------------------------------------------------------------
	private function r_TargetState_8(msg : com.net.flash.test.Messages.TargetState, input : NetDataInput) : void {
	}
	private function w_TargetState_8(msg : com.net.flash.test.Messages.TargetState, output : NetDataOutput) : void {
	}



	}

}