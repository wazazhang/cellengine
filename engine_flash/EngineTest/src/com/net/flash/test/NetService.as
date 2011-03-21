package com.net.flash.test
{
	import com.net.client.ServerSession;
	import com.net.client.ServerSessionListener;
	import com.net.client.minaimpl.ServerSessionImpl;
	
	public class NetService
	{
		var session : ServerSession = new ServerSessionImpl(new MessageCodec());
		
		public function NetService()
		{
		}
		
		public function connect(host : String , port : int, timeout : int = 10000) : Boolean
		{
			return session.connect(host, port, timeout, new ServerListener(this));
		}
		
		
	}
	
	class ServerListener implements ServerSessionListener
	{
		var c : NetService;
		
		function ServerListener(c : NetService) 
		{
			this.c = c;
		}
			
		
		public function connected( session : ServerSession) : void
		{
		
		}
		
		public function disconnected( session : ServerSession,  graceful : Boolean,  reason:String) : void
		{
		
		}
		
		public function sentMessage( session : ServerSession,  protocol : Protocol,  message:Message) : void
		{
		
		}
		
		public function receivedMessage( session : ServerSession,  protocol : Protocol,  message:Message) : void
		{
		
		}

	}
}