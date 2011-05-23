package com.net.client
{
	import com.cell.util.Reference;
	
	import flash.events.EventDispatcher;
	import flash.utils.Dictionary;
	
	//告诉系统，需要注册哪里事件  
	[Event(name=ClientEvent.CONNECTED, 		type="com.net.client.ClientEvent")]  
	[Event(name=ClientEvent.DISCONNECTED,	type="com.net.client.ClientEvent")]  
	public class Client extends EventDispatcher implements ServerSessionListener
	{
		private var package_index 			: int = 1;
		
		/**key is package num, value is ClientResponseListener*/
		private var request_listeners		: Dictionary = new Dictionary();
		
		/**key is message type, value is ClientNotifyListener*/
		private var notify_listeners		: Dictionary = new Dictionary();

		private var session					: ServerSession;
		
		public function Client(session : ServerSession)
		{
			this.session = session;
		}
		
		public function getSession() : ServerSession 
		{
			return session;
		}
		
		public function isConnected() : Boolean
		{
			return session.isConnected();
		}
		
		public function connect(
			host 		: String, 
			port 		: int,
			timeout		: int = 60000) : Boolean
		{
			return getSession().connect(host, port, this);
		}
		
		/**
		 * 断开链接
		 */
		public function disconnect() : void
		{
			getSession().disconnect();
		}
		
		/**
		 * 直接发送，不监听回馈 
		 * @param msg
		 */
		public function send(msg : Message) : Boolean
		{
			return getSession().send(msg);
		}
		
		/**
		 * 发送并监听返回
		 * @param message 			发送的消息
		 * @param response_listener	消息接收监听器
		 * @param timeout_listener	消息超时监听器
		 * @param timeout			消息发送后的超时时间
		 * @return
		 */
		public function sendRequest(
			message 			: Message, 
			response_listener 	: Function, 
			timeout_listener	: Function = null,
			timeout 			: int = 10000) : Reference
		{
			if (isConnected()) {
				var request : ClientRequest  = new ClientRequest(
					message, 
					package_index++, 
					timeout,
					response_listener, 
					timeout_listener
				);
				request_listeners[request.getPacketNumber()]= request;
				request.send(this);
				return request;
			} else {
				return null;
			}
		}
		
		/**
		 * 强制移除所有等待中的请求
		 * @param type
		 */
		public function clearRequests() : void
		{
			for each (var pnum : Object in request_listeners) { 
				delete request_listeners[pnum];
			}
		}
		
		/**
		 * 添加一个用于主动监听服务器端的消息的监听器
		 * @param listener
		 */
		public function addNotifyListener(listener : Function) : void
		{
			this.notify_listeners[listener] = listener;
			this.addEventListener(ClientEvent.MESSAGE_NOTIFY, listener);
		}
		
		/**
		 * 删除一个用于主动监听服务器端的消息的监听器
		 * @param listener
		 */
		public function removeNotifyListener(listener : Function) : void
		{
			this.removeEventListener(ClientEvent.MESSAGE_NOTIFY, listener);
			delete notify_listeners[listener];
		}
		
		/**
		 * 清除所有用于主动监听服务器端的消息的监听器
		 * @param message_type
		 */
		public function clearNotifyListeners() : void
		{
			for each (var listener : Function in notify_listeners) { 
				this.removeEventListener(ClientEvent.MESSAGE_NOTIFY, listener);
				delete notify_listeners[listener];
			}
		}
		
		
//	----------------------------------------------------------------------------------------------------------------------------
//		
//		protected function onConnected(session : ServerSession) : void {}
//		
//		protected function onDisconnected(ServerSession session, boolean graceful, String reason) {}
//		
//		protected function onJoinedChannel(ClientChannel channel) {}
//		
//		protected function onLeftChannel(ClientChannel channel) {}
//		
//		protected function onReceivedMessage(ServerSession session, MessageHeader message){}
//		
//		protected function onSentMessage(ServerSession session, MessageHeader message){}
//		
//	----------------------------------------------------------------------------------------------------------------------------
		
		final public function connected(session : ServerSession) : void
		{
			trace("connected : " + session);
			dispatchEvent(new ClientEvent(ClientEvent.CONNECTED, this, 
				0, null, null, null));
		}
		
		final public function disconnected(session : ServerSession, reason:String) : void
		{
			trace("disconnected : " + session);
			dispatchEvent(new ClientEvent(ClientEvent.DISCONNECTED, this, 
				0, null, null, reason));
		}
		
		final public function sentMessage(session : ServerSession, protocol : Protocol) : void
		{
			//trace("sentMessage : " + protocol);
			dispatchEvent(new ClientEvent(ClientEvent.SENT_MESSAGE, this, 
				protocol.getChannelID(), protocol.getMessage(), null, null));
		}
		
		final public function receivedMessage(session : ServerSession, protocol : Protocol) : void
		{
			//trace("receivedMessage : " + protocol);	
			var request : ClientRequest = request_listeners[protocol.getPacketNumber()];
			if (request != null) {			
				delete request_listeners[protocol.getPacketNumber()];
				var event : ClientEvent = new ClientEvent(ClientEvent.MESSAGE_RESPONSE, this, 
					protocol.getChannelID(), request.request, protocol.getMessage(), null);
				request.set(protocol.getMessage());
				request.response.call(request.response, event);
			} else {
				dispatchEvent(new ClientEvent(ClientEvent.MESSAGE_NOTIFY, this, 
					protocol.getChannelID(), null, protocol.getMessage(), null));
			}
		}

		final public function joinedChannel(channel_id : int, session : ServerSession)  : void
		{
			trace("joinedChannel : " + channel_id);
			dispatchEvent(new ClientEvent(ClientEvent.JOINED_CHANNEL, this, 
				channel_id, null, null, null));
		}
		
		final public function leftChannel(channel_id : int, session : ServerSession) : void
		{
			trace("leftChannel : " + channel_id);
			dispatchEvent(new ClientEvent(ClientEvent.LEFT_CHANNEL, this, 
				channel_id, null, null, null));
		}

		
	}
	
}