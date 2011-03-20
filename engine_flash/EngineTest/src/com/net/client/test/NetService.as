package com.net.client.test
{
	import com.net.client.ServerSession;
	import com.net.client.minaimpl.ServerSessionImpl;
	
	public class NetService
	{
		var session : ServerSession = new ServerSessionImpl(new Messages());
		
		public function NetService()
		{
		}
	}
}