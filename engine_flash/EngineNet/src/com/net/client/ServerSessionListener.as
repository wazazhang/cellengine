package com.net.client
{
	public interface ServerSessionListener
	{
		
		function connected		( session : ServerSession) : void;
		
		function disconnected	( session : ServerSession, reason : String) : void;
		
		function sentMessage	( session : ServerSession, protocol : Protocol) : void;
		
		function receivedMessage( session : ServerSession, protocol : Protocol) : void;
		
		
		function joinedChannel	(channel_id : Protocol, session : ServerSession)  : void;
		
		function leftChannel	(channel_id : Protocol, session : ServerSession) : void;

	}
}