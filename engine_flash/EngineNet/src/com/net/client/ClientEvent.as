package com.net.client
{
	import flash.events.Event;

	public class ClientEvent extends Event
	{
		public static const CONNECTED			:String = "onConnected"; 
			
		public static const DISCONNECTED		:String = "onDisconnected"; 
			
		public static const JOINED_CHANNEL		:String = "onJoinChannel"; 
			
		public static const LEFT_CHANNEL		:String = "onLeftChannel"; 
			
		public static const SENT_MESSAGE		:String = "onSentMessage"; 
		
		public static const MESSAGE_NOTIFY		:String = "onMessageNotify"; 
		
		public static const MESSAGE_RESPONSE	:String = "onMessageResponse"; 
		
		public static const REQUEST_TIMEOUT		:String = "onRequestTimeout"; 
		
		private var client 		: Client;
		private var request		: Message;
		private var message		: Message;
		private var channel_id	: int;
		private var reason		: String;
		
		public function ClientEvent(
			evt 		: String, 
			client 		: Client,
			channel_id	: int,
			request		: Message,
			message		: Message,
			reason		: String
		) 
		{
			super(evt);
			this.client 	= client;
			this.message	= message;
			this.request	= request;
			this.reason		= reason;
		}
		
		override public function clone() : Event {  
			return new ClientEvent(this.type, this.client, this.channel_id, this.request, this.message, this.reason);  
		}
		
		public function getRequest() : Message {
			return request;
		}
		
		public function getResponse() : Message {
			return message;
		}
		
		public function getNotify() : Message {
			return message;
		}
	
		public function getChannelID() : int {
			return channel_id;
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