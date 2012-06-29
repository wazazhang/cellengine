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
	import com.net.flash.test.Messages.Data;
	import com.net.flash.test.Messages.Echo2Request;
	import com.net.flash.test.Messages.Echo2Response;
	import com.net.flash.test.Messages.EchoNotify;
	import com.net.flash.test.Messages.EchoRequest;
	import com.net.flash.test.Messages.EchoResponse;
	import com.net.flash.test.Messages.StateInBattle;
	import com.net.flash.test.Messages.TargetState;


	/**
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class MessageCodec implements MessageFactory
	{
	
		public function getVersion() : String{
			return "1340962225875";
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

			throw new Error("Object is not a MutualMessage : " + msg);
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

			}
			throw new Error("Can not create message : " + type);
		}
		
		public function	readExternal(msg : MutualMessage,  input : NetDataInput) : void  
		{
			var type : int = getType(msg);
			switch(type)
			{
			case 1 : r_1(com.net.flash.test.Messages.Data(msg), input); return;
			case 2 : r_2(com.net.flash.test.Messages.Echo2Request(msg), input); return;
			case 3 : r_3(com.net.flash.test.Messages.Echo2Response(msg), input); return;
			case 4 : r_4(com.net.flash.test.Messages.EchoNotify(msg), input); return;
			case 5 : r_5(com.net.flash.test.Messages.EchoRequest(msg), input); return;
			case 6 : r_6(com.net.flash.test.Messages.EchoResponse(msg), input); return;

			}
			throw new Error("Can not decode message : " + type + " : " + msg);
		}
		
		public function	writeExternal(msg : MutualMessage, output : NetDataOutput) : void  
		{
			var type : int = getType(msg);
			switch(type)
			{
			case 1 : w_1(com.net.flash.test.Messages.Data(msg), output); return;
			case 2 : w_2(com.net.flash.test.Messages.Echo2Request(msg), output); return;
			case 3 : w_3(com.net.flash.test.Messages.Echo2Response(msg), output); return;
			case 4 : w_4(com.net.flash.test.Messages.EchoNotify(msg), output); return;
			case 5 : w_5(com.net.flash.test.Messages.EchoRequest(msg), output); return;
			case 6 : w_6(com.net.flash.test.Messages.EchoResponse(msg), output); return;

			}
			throw new Error("Can not encode message : " + type + " : " + msg);
		}
		
//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.Data
//	----------------------------------------------------------------------------------------------------
	private function r_1(msg : com.net.flash.test.Messages.Data, input : NetDataInput) : void {
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
		msg.enum_ts = input.readEnum();
		msg.enum_sb = input.readEnum();
		msg.enums_ts = input.readMutualArray();
		msg.enums_sb = input.readMutualArray();
	}
	private function w_1(msg : com.net.flash.test.Messages.Data, output : NetDataOutput) : void {
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
		output.writeEnum(msg.enum_ts);
		output.writeEnum(msg.enum_sb);
		output.writeMutualArray(msg.enums_ts);
		output.writeMutualArray(msg.enums_sb);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.Echo2Request
//	----------------------------------------------------------------------------------------------------
	private function r_2(msg : com.net.flash.test.Messages.Echo2Request, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_2(msg : com.net.flash.test.Messages.Echo2Request, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.Echo2Response
//	----------------------------------------------------------------------------------------------------
	private function r_3(msg : com.net.flash.test.Messages.Echo2Response, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_3(msg : com.net.flash.test.Messages.Echo2Response, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.EchoNotify
//	----------------------------------------------------------------------------------------------------
	private function r_4(msg : com.net.flash.test.Messages.EchoNotify, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_4(msg : com.net.flash.test.Messages.EchoNotify, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.EchoRequest
//	----------------------------------------------------------------------------------------------------
	private function r_5(msg : com.net.flash.test.Messages.EchoRequest, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
		msg.data = input.readMutual() as com.net.flash.test.Messages.Data;
		msg.datas = input.readMutualArray();
		msg.datas2 = input.readAnyArray(NetDataTypes.TYPE_MUTUAL);
	}
	private function w_5(msg : com.net.flash.test.Messages.EchoRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
		output.writeMutual(msg.data);
		output.writeMutualArray(msg.datas);
		output.writeAnyArray(msg.datas2, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.EchoResponse
//	----------------------------------------------------------------------------------------------------
	private function r_6(msg : com.net.flash.test.Messages.EchoResponse, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
		msg.data = input.readMutual() as com.net.flash.test.Messages.Data;
		msg.datas = input.readMutualArray();
	}
	private function w_6(msg : com.net.flash.test.Messages.EchoResponse, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
		output.writeMutual(msg.data);
		output.writeMutualArray(msg.datas);
	}



	}

}