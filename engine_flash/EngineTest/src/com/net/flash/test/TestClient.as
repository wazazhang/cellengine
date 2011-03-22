package com.net.flash.test
{
	import com.net.client.Client;
	import com.net.client.minaimpl.ServerSessionImpl;

	public class TestClient extends Client
	{
		public function TestClient()
		{
			super(new ServerSessionImpl(new MessageCodec()));
		}
	}
}