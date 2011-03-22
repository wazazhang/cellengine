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
		private var package_index 			: int = 0;
		
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
			port 		: int) : void
		{
			getSession().connect(host, port, this);
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
		public function send(msg : Message) : void
		{
			getSession().send(msg);
		}
		
		/**
		 * 发送并监听返回
		 * @param <T>
		 * @param message
		 * @param timeout
		 * @param listeners
		 * @return
		 */
		public function sendRequest(message : Message, listener : ClientResponseListener, timeout : int = 10000) : Reference
		{
			var request : ClientRequest  = new ClientRequest(message, timeout, package_index++, listener);
			request_listeners[request.getPacketNumber()]= request;
			request.send(this);
			return request;
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
		public function addNotifyListener(listener : ClientNotifyListener) : void
		{
			this.notify_listeners[listener] = listener;
		}
		
		/**
		 * 删除一个用于主动监听服务器端的消息的监听器
		 * @param listener
		 */
		public function removeNotifyListener(listener : ClientNotifyListener) : void
		{
			delete notify_listeners[listener];
		}
		
		/**
		 * 清除所有用于主动监听服务器端的消息的监听器
		 * @param message_type
		 */
		public function clearNotifyListeners() : void
		{
			for each (var listener : Object in notify_listeners) { 
				delete notify_listeners[listener];
			}
		}
		
		/**
		 * 立刻清理所有未响应的请求和囤积的未知消息
		 */
		public function cleanRequestAndNotify() : void
		{
			clearRequests();
			clearNotifyListeners();
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
		
		private function doReceivedMessage(protocol : Protocol) : void
		{
			var request : ClientRequest = request_listeners[protocol.getPacketNumber()];
			if (request != null) {
				request.messageResponsed(this, protocol);
			} else {
				for each (var listener : ClientNotifyListener in notify_listeners) { 
					listener.notify(this, protocol.getMessage());
				}
			}
		}
		
//	----------------------------------------------------------------------------------------------------------------------------

		final public function connected(session : ServerSession) : void
		{
			trace("connected : " + session);
			if (dispatchEvent(new ClientEvent(ClientEvent.CONNECTED, this))) {
				trace("can not dispatchEvent");
			}
		}
		
		final public function disconnected(session : ServerSession, reason:String) : void
		{
			trace("disconnected : " + session);
			if (dispatchEvent(new ClientEvent(ClientEvent.DISCONNECTED, this))) {
				trace("can not dispatchEvent");
			}
		}
		
		final public function sentMessage(session : ServerSession, protocol : Protocol) : void
		{
			trace("sentMessage : " + protocol);
			dispatchEvent(new ClientEvent(ClientEvent.SENT_MESSAGE, this, protocol.getMessage()));
		}
		
		final public function receivedMessage(session : ServerSession, protocol : Protocol) : void
		{
			trace("receivedMessage : " + protocol);	
			doReceivedMessage(protocol);
			dispatchEvent(new ClientEvent(ClientEvent.RECEIVED_MESSAGE, this, protocol.getMessage()));
		}

		final public function joinedChannel(channel_id : int, session : ServerSession)  : void
		{
			trace("joinedChannel : " + channel_id);
			dispatchEvent(new ClientEvent(ClientEvent.JOINED_CHANNEL, this, null, channel_id));
		}
		
		final public function leftChannel(channel_id : int, session : ServerSession) : void
		{
			trace("leftChannel : " + channel_id);
			dispatchEvent(new ClientEvent(ClientEvent.LEFT_CHANNEL, this, null, channel_id));
		}

		
	}
	
}