package com.net.client
{
	public interface ClientNotifyListener
	{
		/**
		 * 主动监听来自于服务器端的消息。
		 */ 
		function notify(client : Client, message : Message) : void;
	}
}