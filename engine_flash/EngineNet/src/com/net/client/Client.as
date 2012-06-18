package com.net.client
{
	import avmplus.getQualifiedClassName;
	
	import com.cell.net.io.MutualMessage;
	import com.cell.util.Arrays;
	import com.cell.util.Map;
	import com.cell.util.Reference;
	import com.cell.util.Util;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.TimerEvent;
	import flash.utils.Dictionary;
	import flash.utils.Timer;
	
	//告诉系统，需要注册哪里事件  
	[Event(name=ClientEvent.CONNECTED, 			type="com.net.client.ClientEvent")]  
	[Event(name=ClientEvent.DISCONNECTED,		type="com.net.client.ClientEvent")]  
	
	[Event(name=ClientEvent.JOINED_CHANNEL,		type="com.net.client.ClientEvent")]  
	[Event(name=ClientEvent.LEFT_CHANNEL,		type="com.net.client.ClientEvent")]  
	
	[Event(name=ClientEvent.SENT_MESSAGE,		type="com.net.client.ClientEvent")]  
	[Event(name=ClientEvent.SEND_ERROR,			type="com.net.client.ClientEvent")]  
	
	[Event(name=ClientEvent.MESSAGE_NOTIFY,		type="com.net.client.ClientEvent")]  
	[Event(name=ClientEvent.MESSAGE_RESPONSE,	type="com.net.client.ClientEvent")]  
	[Event(name=ClientEvent.REQUEST_TIMEOUT,	type="com.net.client.ClientEvent")]  
	
	public class Client extends EventDispatcher
	{
		private var package_index 			: int = 1;
		
		/**key is package num, value is ClientResponseListener*/
		private var request_listeners		: Map = new Map();
		
		/**key is message type, value is ClientNotifyListener*/
		private var notify_listeners		: Map = new Map();
		
		// 将场景中所有单位的进入缓冲到此，为了在切换屏幕不丢失数据
		private var unhandled_notifies		: Vector.<Protocol> = new Vector.<Protocol>();
		
		private var session				: ServerSession;
		
		private var check_timeout			: Timer;
		

		
		public function Client(session : ServerSession)
		{
			this.session = session;
			this.check_timeout = new Timer(1000);
			this.check_timeout.addEventListener(TimerEvent.TIMER, checkRequest);
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
			timeout		: int = 20000) : Boolean
		{
			check_timeout.start();
			return session.connect(host, port, timeout, new ClientServerListener(this));
		}
		
		/**
		 * 断开链接
		 */
		public function disconnect() : void
		{
			check_timeout.stop();
			clearRequests();
			session.disconnect();
		}
		
		/**
		 * 直接发送，不监听回馈 
		 * @param msg
		 */
		public function send(msg : MutualMessage) : Boolean
		{
			if (isConnected()) {
				return session.send(msg);
			} else {
				dispatchEvent(new ClientEvent(ClientEvent.SEND_ERROR, this));
				return false;
			}
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
			message 			: MutualMessage, 
			response_listener 	: Function = null, 
			timeout_listener	: Function = null,
			timeout 			: int = 30000) : Reference
		{
			var fresponse : Array;
			var ftimeout : Array;
			
			if (response_listener != null) {
				fresponse = new Array();
				fresponse.push(response_listener);
			}
			if (timeout_listener != null) {	
				ftimeout = new Array();
				ftimeout.push(timeout_listener);
			}
			
			return sendRequestImpl(message, fresponse, ftimeout, timeout);
		}
		
		public function sendRequestImpl(
			message 			: MutualMessage, 
			response_listener 	: Array = null, 
			timeout_listener	: Array = null,
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
				dispatchEvent(new ClientEvent(ClientEvent.SEND_ERROR, this));
				return null;
			}
		}
		
		/**
		 * 强制移除所有等待中的请求
		 * @param type
		 */
		public function clearRequests() : void
		{
			for (var pnum : Object in request_listeners) { 
				((ClientRequest)(request_listeners[pnum])).onTimeout(this);
				delete request_listeners[pnum];
			}
		}
		
		/**
		 * 添加一个用于主动监听服务器端的消息的监听器
		 * @param listener
		 */
		public function addNotifyListener(cls:Class, listener:Function) : void
		{
			var list : Map = this.notify_listeners[cls];
			var call : Boolean = false;
			if (list == null) 
			{
				list = new Map();
				this.notify_listeners.put(cls, list);
				list.put(listener, listener);
				call = true;
			}
			else
			{
				if (list.size()==0) {
					call = true;
				}
				list.put(listener, listener);
			}
			if (call) {
				// 有新的监听器加入时，并且之前没有任何监听器
				for each (var p:Protocol in unhandled_notifies) {
					if (p.getMessage() is cls) {
						var e : ClientEvent = new ClientEvent(ClientEvent.MESSAGE_NOTIFY, 
							this, p, null, null);
						listener.call(null, e);
					}
				}
			}
		}
		
		/**
		 * 删除一个用于主动监听服务器端的消息的监听器
		 * @param listener
		 */
		public function removeNotifyListener(cls:Class, listener:Function) : void
		{
			var list : Map = this.notify_listeners[cls];
			if (list != null) {
				list.remove(listener);
			}
		}
		
		private function notifyMessage(p:Protocol) : void
		{
			var e : ClientEvent = new ClientEvent(ClientEvent.MESSAGE_NOTIFY, this, p, null, null);
			var cls : Class = Util.getClass(p.getMessage());
			var list : Map = this.notify_listeners[cls];
			if (list != null) {
				for each (var l : Function in list) {
					l.call(null, e);
				}
			}
		}
		
		/**
		 * 清除所有用于主动监听服务器端的消息的监听器
		 * @param message_type
		 */
		public function clearNotifyListeners() : void
		{
			for each (var listener : Map in notify_listeners) { 
				delete notify_listeners[listener];
			}
		}
		
		public function cleanUnhandledMessage() : void
		{
			unhandled_notifies = new Vector.<Protocol>();
		}
		
		private function checkRequest(e:Event) : void
		{
			for (var pnum : Object in request_listeners) { 
				var cq : ClientRequest = ((ClientRequest)(request_listeners[pnum]));
				if (cq.isTimeout()) {
					cq.onTimeout(this);
					delete request_listeners[pnum];
				}
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
		
		final function connected(session : ServerSession) : void
		{
			trace("connected : " + session);
			dispatchEvent(new ClientEvent(ClientEvent.CONNECTED, this, 
				null, null, null));
		}
		
		final function disconnected(session : ServerSession, reason:String) : void
		{
			trace("disconnected : " + session);
			dispatchEvent(new ClientEvent(ClientEvent.DISCONNECTED, this, 
				null, null, reason));
			check_timeout.stop();
			clearRequests();
		}
		
		final function sentMessage(session : ServerSession, protocol : Protocol) : void
		{
			//trace("sentMessage : " + protocol);
			dispatchEvent(new ClientEvent(ClientEvent.SENT_MESSAGE, this, 
				protocol, protocol.getMessage(), null));
		}
		
		final function receivedMessage(session : ServerSession, protocol : Protocol) : void
		{
			//trace("receivedMessage : " + protocol);	
			if (protocol.getPacketNumber() != 0) {
				var request : ClientRequest = request_listeners[protocol.getPacketNumber()];
				if (request != null) {
					delete request_listeners[protocol.getPacketNumber()];
					request.onResponse(this, protocol);
					dispatchEvent(new ClientEvent(ClientEvent.MESSAGE_RESPONSE, this, 
						protocol, request.getRequest(), null));
				}
			}
			else {				
				dispatchEvent(new ClientEvent(ClientEvent.MESSAGE_NOTIFY, this, 
					protocol, null, null));
				
				notifyMessage(protocol);
			}
		}

		final function joinedChannel(channel_id : Protocol, session : ServerSession)  : void
		{
			trace("joinedChannel : " + channel_id);
			dispatchEvent(new ClientEvent(ClientEvent.JOINED_CHANNEL, this, 
				channel_id, null, null));
		}
		
		final function leftChannel(channel_id : Protocol, session : ServerSession) : void
		{
			trace("leftChannel : " + channel_id);
			dispatchEvent(new ClientEvent(ClientEvent.LEFT_CHANNEL, this, 
				channel_id, null, null));
		}

		
	}
	
}