package com.net.client.sfsimpl
{
	import com.smartfoxserver.v2.*;
	import com.net.client.*;
		
	public class SFSSessionImpl implements com.net.client.ServerSession
	{
		public function SFSSessionImpl()
		{
			
		}
		
		
		/** 获取Session的对端地址 */
		public function	getRemoteAddress() : String
		{
			return "";
		}
		
		
		public function	isConnected() : Boolean
		{
			
			return false;
		}
		
		public function disconnect() : void
		{
			
		}
		
		public function send(message : Message): Boolean
		{
			return false;
			
		}
		
		public function sendRequest(pnum: int, message : Message) : Boolean
		{
			return false;
			
		}
		
		public function connect(
			host 		: String, 
			port 		: int, 
			listener 	: ServerSessionListener) : Boolean
		{
			return false;
			
		}
		
		
		public function getSentMessageCount(): int 
		{
			return 1;
			
		}
		
		public function getReceivedMessageCount () : int
		{
			return 1;
			
		}
		
		public function getSentBytes(): int
		{
			return 1;
			
		}
		
		public function getReceivedBytes(): int
		{
			return 1;
			
		}
		
		public function getHeartBeatSent(): int
		{
			return 1;
			
		}
		
		public function getHeartBeatReceived(): int
		{
			return 1;
			
		}

	}
}
