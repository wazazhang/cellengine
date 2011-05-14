package com.net.client
{

	public interface MessageFactory
	{
		
		function	getVersion() : String;
		
		/**获得此消息体的类型ID*/
		function	getType(message:Message) : int;
		
		/**通过类型ID获得消息体*/
		function	createMessage(type:int) : Message;

		/**读取消息*/
		function	readExternal(msg : Message,  input : NetDataInput) : void;
		
		/**写入消息*/
		function	writeExternal(msg : Message, output : NetDataOutput) : void;
		
	}

}