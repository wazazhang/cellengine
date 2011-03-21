package com.net.client
{
	public interface ServerSessionListener
	{
		
		function connected( session : ServerSession) : void;
		
		function disconnected( session : ServerSession, reason:String) : void;
		
		function sentMessage( session : ServerSession, protocol : Protocol,  message:Message) : void;
		
		function receivedMessage( session : ServerSession, protocol : Protocol,  message:Message) : void;
		
	}
}