package com.net.client
{
	import com.cell.net.io.MessageFactory;
	import com.cell.net.io.MutualMessage;
	
	import flash.events.IEventDispatcher;
	
	public interface ServerSession
	{

		/** 获取Session的对端地址 */
		function		getRemoteAddress() : String;
		

		function		isConnected() : Boolean;
		
		function 		disconnect() : void;
		
		function 		send(message : MutualMessage): Boolean;
		
		function 		sendRequest(pnum: int, message : MutualMessage) : Boolean;
		
		function 		connect(
							host 		: String, 
							port 		: int, 
							timeout		: int,
							listener 	: ServerSessionListener) : Boolean;
		
		function		getMessageFactory() : MessageFactory;
		
//		function  		getSentMessageCount(): int ;
//		
//		function  		getReceivedMessageCount () : int;
//		
//		function  		getSentBytes(): int;
//		
//		function  		getReceivedBytes(): int;
//		
//		function 		getHeartBeatSent(): int;
//		
//		function 		getHeartBeatReceived(): int;
		
	}
}