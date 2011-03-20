package com.net.client
{
	
	public interface ServerSession
	{

		/** 获取Session的对端地址 */
		function		getRemoteAddress() : String;
		

		
		function		isConnected() : Boolean;
		
		function 		disconnect( force : Boolean) : Boolean;
		
		function 		send( message : Message):Boolean;
		
		function 		sendRequest( pnum: int, message : Message):Boolean;
		
		function 		connect( host : String,  port : int,  timeout : int,  listener : ServerSessionListener) :Boolean;
		
		
		
		
		
		function  		getSentMessageCount(): int ;
		
		function  		getReceivedMessageCount () : int;
		
		function  		getSentBytes(): int;
		
		function  		getReceivedBytes(): int;
		
		function 		getHeartBeatSent(): int;
		
		function 		getHeartBeatReceived(): int;
		
	}
}