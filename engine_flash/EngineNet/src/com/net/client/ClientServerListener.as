package com.net.client
{
	import com.cell.util.Reference;
	
	import flash.events.EventDispatcher;
	import flash.utils.Dictionary;
	
	class ClientServerListener implements ServerSessionListener
	{
		private var client : Client;
		
		function ClientServerListener(client : Client)
		{
			this.client = client;
		}
		
		final public function connected(session : ServerSession) : void
		{
			client.connected(session);
		}
		
		final public function disconnected(session : ServerSession, reason:String) : void
		{
			client.disconnected(session, reason);
		}
		
		final public function sentMessage(session : ServerSession, protocol : Protocol) : void
		{
			client.sentMessage(session, protocol);
		}
		
		final public function receivedMessage(session : ServerSession, protocol : Protocol) : void
		{
			client.receivedMessage(session, protocol);
		}

		final public function joinedChannel(channel_id : int, session : ServerSession)  : void
		{
			client.joinedChannel(channel_id, session);
		}
		
		final public function leftChannel(channel_id : int, session : ServerSession) : void
		{
			client.leftChannel(channel_id, session);
		}

		
	}
	
}