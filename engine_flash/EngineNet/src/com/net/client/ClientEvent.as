package com.net.client
{
	import com.cell.net.io.MutualMessage;
	
	import flash.events.Event;
	
	public class ClientEvent extends Event
	{
		public static const CONNECTED			:String = "onConnected"; 
			
		public static const DISCONNECTED		:String = "onDisconnected"; 
			
		public static const JOINED_CHANNEL		:String = "onJoinChannel"; 
			
		public static const LEFT_CHANNEL		:String = "onLeftChannel"; 
			
		public static const SENT_MESSAGE		:String = "onSentMessage"; 
		
		public static const SEND_ERROR			:String = "onSendError"; 
		
		public static const MESSAGE_NOTIFY		:String = "onMessageNotify"; 
		
		public static const MESSAGE_RESPONSE	:String = "onMessageResponse"; 
		
		public static const REQUEST_TIMEOUT		:String = "onRequestTimeout"; 
		
		
		private var client 		: Client;
		private var protocol	: Protocol;
		private var request		: MutualMessage;
		private var reason		: String;
		
		
		public function ClientEvent(
			evt 		: String, 
			client 		: Client,
			protocol	: Protocol = null,
			request		: MutualMessage = null,
			reason		: String = null
		) 
		{
			super(evt);
			this.client 	= client;
			this.protocol	= protocol;
			this.request	= request;
			this.reason		= reason;
		}
		
		override public function clone() : Event {  
			return new ClientEvent(this.type, this.client, this.protocol, this.request, this.reason);  
		}
		
		public function getRequest() : MutualMessage {
			return request;
		}
		
		public function getResponse() : MutualMessage {
			return protocol.getMessage();
		}
		
		public function getNotify() : MutualMessage {
			return protocol.getMessage();
		}
	
		public function getChannelID() : int {
			return protocol.getChannelID();
		}
		
		public function getProtocol() : Protocol {
			return protocol;
		}
		
		public function getClient() : Client {
			return this.client;
		}
		
		public function getReason() : String
		{
			return this.reason;
		}
		
	}
}