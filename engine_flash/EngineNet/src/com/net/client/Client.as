package com.net.client
{
	import com.cell.util.Reference;
	
	import flash.events.EventDispatcher;
	import flash.utils.Dictionary;

	public class Client implements ServerSessionListener
	{
		private var package_index 			: int = 0;
		
		/**key is package num, value is ClientResponseListener*/
		private var request_listeners		: Dictionary = new Dictionary();
		
		/**key is message type, value is ClientNotifyListener*/
		private var notify_listeners		: Dictionary = new Dictionary();

		
		function getSession() : ServerSession 
		{
			return null;
		}
		
		/**
		 * 断开链接
		 */
		public function close() : void
		{
			getSession().disconnect();
		}
		
		/**
		 * 直接发送，不监听回馈 
		 * @param msg
		 */
		public function send(msg : Message) 
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

		public function connected(session : ServerSession) : void
		{
			trace("connected : " + session);
		}
		
		public function disconnected(session : ServerSession, reason:String) : void
		{
			trace("disconnected : " + session);
		}
		
		public function sentMessage(session : ServerSession, protocol : Protocol) : void
		{
			trace("sentMessage : " + protocol);
		}
		
		public function receivedMessage(session : ServerSession, protocol : Protocol) : void
		{
			trace("receivedMessage : " + protocol);	
			doReceivedMessage(protocol);
		}
		
		
		public function joinedChannel(channel_id : int, session : ServerSession)  : void
		{
			trace("joinedChannel : " + channel_id);
		}
		
		public function leftChannel(channel_id : int, session : ServerSession) : void
		{
			trace("leftChannel : " + channel_id);
		}

		
	}
	
}