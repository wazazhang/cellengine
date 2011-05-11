package com.net.client.sfsimpl
{
	import com.smartfoxserver.v2.*;
	import com.net.client.*;
		
	public class SFSSessionImpl implements com.net.client.ServerSession
	{
		private var sfs : SmartFox;

		public function SFSSessionImpl()
		{
			sfs = new SmartFox();
		}
		
		
		/** 获取Session的对端地址 */
		public function	getRemoteAddress() : String
		{
			return sfs.currentIp+":"+sfs.currentPort;
		}
		
		
		public function	isConnected() : Boolean
		{
			return sfs.isConnected;
		}
		
		public function disconnect() : void
		{
			sfs.disconnect();
		}
		
		public function connect(
			host 		: String, 
			port 		: int, 
			listener 	: ServerSessionListener) : Boolean
		{
			sfs.connect(host, port);
			
			return true;
		}
		

		public function send(message : Message): Boolean
		{
			
			
			return false;
		}
		
		public function sendRequest(pnum: int, message : Message) : Boolean
		{
			return false;
		}

	}
}
