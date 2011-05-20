package com.net.flash.test
{
	import com.net.client.Client;
	import com.net.client.sfsimpl.SFSSessionImpl;

	public class TestClientSFS extends Client
	{
		public function TestClientSFS()
		{
			super(new SFSSessionImpl(new MessageCodec(), "EngineTest"));
		}
	}
}