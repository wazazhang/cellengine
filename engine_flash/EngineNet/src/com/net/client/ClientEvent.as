package com.net.client
{
	import flash.events.Event;
	import com.cell.net.io.MutualMessage;
	
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
		private var request		: MutualMessage;
		private var message		: MutualMessage;
		private var channel_id	: int;
		private var reason		: String;
		
		public function ClientEvent(
			evt 		: String, 
			client 		: Client,
			channel_id	: int = 0,
			request		: MutualMessage = null,
			message		: MutualMessage = null,
			reason		: String = null
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
		
		public function getRequest() : MutualMessage {
			return request;
		}
		
		public function getResponse() : MutualMessage {
			return message;
		}
		
		public function getNotify() : MutualMessage {
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