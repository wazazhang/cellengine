package com.net.client.test
{
	public class MessageFactory implements com.net.client.MessageFactory
	{
		public function Messages()
		{
		}
		
		/**获得此消息体的类型ID*/
		public function	getType(message:Message) : int 
		{
			return 0;
		}
		
		/**通过类型ID获得消息体*/
		public function	createMessage(type:int) : Message
		{
			return 0;
			
		}
		
		/**读取消息*/
		public function	readExternal(msg : Message,  input : IDataInput) : void  
		{
			return 0;
			
		}
		
		/**写入消息*/
		public function	writeExternal(msg : Message, output : IDataOutput) : void  
		{
			return 0;
			
		}
		
	}
}