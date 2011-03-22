package com.net.client
{

	public interface ClientResponseListener
	{
		/**
		 * 发送数据后，此方法监听消息反馈。
		 */ 
		function response(client : Client, 
						  request : Message, 
						  response : Message) : void;
		/**
		 * 一个请求长时间没有反馈
		 */
		function timeout(client : Client, 
						 request : Message) : void;
	}
}