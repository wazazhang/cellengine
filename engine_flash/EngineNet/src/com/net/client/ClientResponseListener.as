package com.net.client
{
	import mx.events.Request;

	public interface ClientResponseListener
	{
		/**
		 * 发送数据后，此方法监听消息反馈。
		 */ 
		function response(client : Client, request : Request, response : Message) : void;
		
	}
}