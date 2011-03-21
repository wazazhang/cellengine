package com.net.flash.test
{
	import com.net.client.ServerSession;
	import com.net.client.minaimpl.ServerSessionImpl;
	
	public class NetService
	{
		var session : ServerSession = new ServerSessionImpl(new MessageCodec());
		
		public function NetService()
		{
		}
	}
}