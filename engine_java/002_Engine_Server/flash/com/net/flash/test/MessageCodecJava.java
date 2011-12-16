package com.net.flash.test;

import java.io.IOException;
import com.cell.net.io.*;


/**
 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
 */
public class MessageCodecJava implements MutualMessageCodec
{
	public String getVersion() {
		return "1323421055223";
	}

	public void readMutual(MutualMessage msg, NetDataInput in) throws IOException 
	{
		if (msg.getClass().equals(com.net.flash.test.Messages.Data.class)) {
			_r((com.net.flash.test.Messages.Data)msg, in); return;
		}
		if (msg.getClass().equals(com.net.flash.test.Messages.Echo2Request.class)) {
			_r((com.net.flash.test.Messages.Echo2Request)msg, in); return;
		}
		if (msg.getClass().equals(com.net.flash.test.Messages.Echo2Response.class)) {
			_r((com.net.flash.test.Messages.Echo2Response)msg, in); return;
		}
		if (msg.getClass().equals(com.net.flash.test.Messages.EchoNotify.class)) {
			_r((com.net.flash.test.Messages.EchoNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.net.flash.test.Messages.EchoRequest.class)) {
			_r((com.net.flash.test.Messages.EchoRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.net.flash.test.Messages.EchoResponse.class)) {
			_r((com.net.flash.test.Messages.EchoResponse)msg, in); return;
		}

	}

	public void writeMutual(MutualMessage msg, NetDataOutput out) throws IOException 
	{
		if (msg.getClass().equals(com.net.flash.test.Messages.Data.class)) {
			_w((com.net.flash.test.Messages.Data)msg, out); return;
		}
		if (msg.getClass().equals(com.net.flash.test.Messages.Echo2Request.class)) {
			_w((com.net.flash.test.Messages.Echo2Request)msg, out); return;
		}
		if (msg.getClass().equals(com.net.flash.test.Messages.Echo2Response.class)) {
			_w((com.net.flash.test.Messages.Echo2Response)msg, out); return;
		}
		if (msg.getClass().equals(com.net.flash.test.Messages.EchoNotify.class)) {
			_w((com.net.flash.test.Messages.EchoNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.net.flash.test.Messages.EchoRequest.class)) {
			_w((com.net.flash.test.Messages.EchoRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.net.flash.test.Messages.EchoResponse.class)) {
			_w((com.net.flash.test.Messages.EchoResponse)msg, out); return;
		}

	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.Data
//	----------------------------------------------------------------------------------------------------
	public static com.net.flash.test.Messages.Data new_com_net_flash_test_Messages_Data(){return new com.net.flash.test.Messages.Data();}
	private void _r(com.net.flash.test.Messages.Data msg, NetDataInput in) throws IOException {
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
		msg.b_d5 = (float[][])in.readAnyArray(float[][].class, NetDataTypes.TYPE_FLOAT);
	}
	private void _w(com.net.flash.test.Messages.Data msg, NetDataOutput out) throws IOException {
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
		out.writeAnyArray(msg.b_d5, NetDataTypes.TYPE_FLOAT);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.Echo2Request
//	----------------------------------------------------------------------------------------------------
	public static com.net.flash.test.Messages.Echo2Request new_com_net_flash_test_Messages_Echo2Request(){return new com.net.flash.test.Messages.Echo2Request();}
	private void _r(com.net.flash.test.Messages.Echo2Request msg, NetDataInput in) throws IOException {
		msg.message = in.readUTF();
	}
	private void _w(com.net.flash.test.Messages.Echo2Request msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.Echo2Response
//	----------------------------------------------------------------------------------------------------
	public static com.net.flash.test.Messages.Echo2Response new_com_net_flash_test_Messages_Echo2Response(){return new com.net.flash.test.Messages.Echo2Response();}
	private void _r(com.net.flash.test.Messages.Echo2Response msg, NetDataInput in) throws IOException {
		msg.message = in.readUTF();
	}
	private void _w(com.net.flash.test.Messages.Echo2Response msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.EchoNotify
//	----------------------------------------------------------------------------------------------------
	public static com.net.flash.test.Messages.EchoNotify new_com_net_flash_test_Messages_EchoNotify(){return new com.net.flash.test.Messages.EchoNotify();}
	private void _r(com.net.flash.test.Messages.EchoNotify msg, NetDataInput in) throws IOException {
		msg.message = in.readUTF();
	}
	private void _w(com.net.flash.test.Messages.EchoNotify msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.EchoRequest
//	----------------------------------------------------------------------------------------------------
	public static com.net.flash.test.Messages.EchoRequest new_com_net_flash_test_Messages_EchoRequest(){return new com.net.flash.test.Messages.EchoRequest();}
	private void _r(com.net.flash.test.Messages.EchoRequest msg, NetDataInput in) throws IOException {
		msg.message = in.readUTF();
		msg.data = in.readMutual(com.net.flash.test.Messages.Data.class);
		msg.datas = (com.net.flash.test.Messages.Data[])in.readMutualArray(com.net.flash.test.Messages.Data.class);
		msg.datas2 = (com.net.flash.test.Messages.Data[][][])in.readAnyArray(com.net.flash.test.Messages.Data[][][].class, NetDataTypes.TYPE_MUTUAL);
	}
	private void _w(com.net.flash.test.Messages.EchoRequest msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.message);
		out.writeMutual(msg.data);
		out.writeMutualArray(msg.datas);
		out.writeAnyArray(msg.datas2, NetDataTypes.TYPE_MUTUAL);
	}

//	----------------------------------------------------------------------------------------------------
//	com.net.flash.test.Messages.EchoResponse
//	----------------------------------------------------------------------------------------------------
	public static com.net.flash.test.Messages.EchoResponse new_com_net_flash_test_Messages_EchoResponse(){return new com.net.flash.test.Messages.EchoResponse();}
	private void _r(com.net.flash.test.Messages.EchoResponse msg, NetDataInput in) throws IOException {
		msg.message = in.readUTF();
		msg.data = in.readMutual(com.net.flash.test.Messages.Data.class);
		msg.datas = (com.net.flash.test.Messages.Data[])in.readMutualArray(com.net.flash.test.Messages.Data.class);
	}
	private void _w(com.net.flash.test.Messages.EchoResponse msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.message);
		out.writeMutual(msg.data);
		out.writeMutualArray(msg.datas);
	}


}
