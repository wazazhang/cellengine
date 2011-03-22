package com.net.client
{
	import flash.events.Event;

	public class ClientEvent extends Event
	{
		public static const CONNECTED			:String = "onConnected"; 
			
		public static const DISCONNECTED		:String = "onDisconnected"; 
			
		public static const JOINED_CHANNEL		:String = "onJoinChannel"; 
			
		public static const LEFT_CHANNEL		:String = "onLeftChannel"; 
			
		public static const RECEIVED_MESSAGE	:String = "onReceivedMessage"; 
			
		public static const SENT_MESSAGE		:String = "onSentMessage"; 
		
		private var client 		: Client;
		private var message		: Message;
		private var channel_id	: int;
		
		public function ClientEvent(
			evt 		: String, 
			client 		: Client,
			message		: Message = null,
			channel_id	: int = -1) 
		{
			super(evt);
			this.client 	= client;
			this.message	= message;
		}
		
		override public function clone() : Event {  
			return new ClientEvent(this.type, this.client, this.message, this.channel_id);  
		}
		
		
		public function getMessage() : Message
		{
			return message;
		}
	
		public function getChannelID() : int
		{
			return channel_id;
		}
		
		public function getClient() : Client
		{
			return this.client;
		}
		
	}
}