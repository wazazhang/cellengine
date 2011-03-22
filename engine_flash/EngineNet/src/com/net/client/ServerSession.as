package com.net.client
{
	import flash.events.IEventDispatcher;
	
	public interface ServerSession
	{

		/** 获取Session的对端地址 */
		function		getRemoteAddress() : String;
		

		function		isConnected() : Boolean;
		
		function 		disconnect() : void;
		
		function 		send(message : Message): void;
		
		function 		sendRequest(pnum: int, message : Message) : void;
		
		function 		connect(
							host : String, 
							port : int,  
							timeout : int, 
							listener : ServerSessionListener) : void;
		
		
		
		
		
		function  		getSentMessageCount(): int ;
		
		function  		getReceivedMessageCount () : int;
		
		function  		getSentBytes(): int;
		
		function  		getReceivedBytes(): int;
		
		function 		getHeartBeatSent(): int;
		
		function 		getHeartBeatReceived(): int;
		
	}
}