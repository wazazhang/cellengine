package com.cell.net.io
{

	public interface MessageFactory
	{
		
		function	getVersion() : String;
		
		/**获得此消息体的类型ID*/
		function	getType(message:MutualMessage) : int;
		
		/**通过类型ID获得消息体*/
		function	createMessage(type:int) : MutualMessage;

		/**读取消息*/
		function	readExternal(msg : MutualMessage,  input : NetDataInput) : void;
		
		/**写入消息*/
		function	writeExternal(msg : MutualMessage, output : NetDataOutput) : void;
		
	}

}