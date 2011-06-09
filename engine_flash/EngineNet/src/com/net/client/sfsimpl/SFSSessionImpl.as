package com.net.client.sfsimpl
{
	import com.net.client.*;
	import com.smartfoxserver.v2.*;
	import com.smartfoxserver.v2.core.SFSEvent;
	import com.smartfoxserver.v2.entities.data.ISFSObject;
	import com.smartfoxserver.v2.entities.data.SFSObject;
	import com.smartfoxserver.v2.requests.ExtensionRequest;
	import com.smartfoxserver.v2.requests.JoinRoomRequest;
	import com.smartfoxserver.v2.requests.LoginRequest;
	import com.smartfoxserver.v2.requests.ObjectMessageRequest;
	
	import flash.events.Event;
	import flash.utils.ByteArray;
		
	public class SFSSessionImpl implements com.net.client.ServerSession
	{
		private var sfs 			: SmartFox;
		private var user_listener 	: ServerSessionListener;
		private var ext_factory		: MessageFactory;
		
		private var sfs_zone		: String = "XML_NAME";
		private var sfs_user		: String = "";
		private var sfs_pswd		: String = "";
		
		public function SFSSessionImpl(factory : MessageFactory)
		{
			this.ext_factory	= factory;
			
			// Create an instance of the SmartFox class
			sfs = new SmartFox();
			// Turn on the debug feature
			sfs.debug = false;
			// Add SFS2X event listeners
			sfs.addEventListener(SFSEvent.CONNECTION, onConnection)
			sfs.addEventListener(SFSEvent.CONNECTION_LOST, onConnectionLost)
			//sfs.addEventListener(SFSEvent.CONFIG_LOAD_SUCCESS, onConfigLoadSuccess)
			//sfs.addEventListener(SFSEvent.CONFIG_LOAD_FAILURE, onConfigLoadFailure)
			
			sfs.addEventListener(SFSEvent.LOGIN, onLogin);
			sfs.addEventListener(SFSEvent.LOGIN_ERROR, onLoginError);
			
			sfs.addEventListener(SFSEvent.ROOM_JOIN, onRoomJoin);
			sfs.addEventListener(SFSEvent.ROOM_JOIN_ERROR, onRoomJoinError);
			
			sfs.addEventListener(SFSEvent.EXTENSION_RESPONSE, onExtensionResponse);
//			sfs.addEventListener(SFSEvent.OBJECT_MESSAGE, onObjectMessage);
			dTrace("SmartFox API: "+ sfs.version)
		}
		
//		------------------------------------------------------------------------------------
		
		public function setZone(zone:String) : SFSSessionImpl
		{
			this.sfs_zone = zone;
			return this;
		}
		
		public function setUser(user:String) : SFSSessionImpl
		{
			this.sfs_user = user;
			return this;
		}
		
		public function setPassword(pswd:String) : SFSSessionImpl
		{
			this.sfs_pswd = pswd;
			return this;
		}
		
//		------------------------------------------------------------------------------------
		
		public function getMessageFactory() : MessageFactory 
		{
			return ext_factory;
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
			trace("connecting : " + host+":"+port);
			this.user_listener = listener;
			sfs.connect(host, port);
			return sfs.isConnected;
		}
		

		public function send(message : Message): Boolean
		{
			var p : SFSProtocol = new SFSProtocol();
//			p.setSessionID(0);
			p.setProtocol(ProtocolType.PROTOCOL_SESSION_MESSAGE);
			p.setChannelID(null);
//			p.setChannelSessionID(0);
			p.setMessage(message);
			p.setPacketNumber(0);
			var params  : ISFSObject = encode(p);
			if (params != null) {
				sfs.send(new ExtensionRequest("msg", params));
				return false;
			}
			return true;
		}
		
		public function sendRequest(pnum: int, message : Message) : Boolean
		{
			var p : SFSProtocol = new SFSProtocol();
//			p.setSessionID(0);
			p.setProtocol(ProtocolType.PROTOCOL_SESSION_MESSAGE);
			p.setChannelID(null);
//			p.setChannelSessionID(0);
			p.setMessage(message);
			p.setPacketNumber(pnum);
			var params  : ISFSObject = encode(p);
			if (params != null) {
				sfs.send(new ExtensionRequest("msg", params));
				return false;
			}
			return true;
		}
		
//		---------------------------------------------------------------------------------------------------------
//		
//		---------------------------------------------------------------------------------------------------------
		
		function encode(p : SFSProtocol) : ISFSObject
		{
			var out : SFSObject = new SFSObject();
			{
				out.putByte		("Protocol", 			p.getProtocol());			// 1
				out.putInt		("PacketNumber",		p.getPacketNumber());		// 4
				
				switch (p.getProtocol()) {
				case ProtocolType.PROTOCOL_CHANNEL_JOIN_S2C:
				case ProtocolType.PROTOCOL_CHANNEL_LEAVE_S2C:
				case ProtocolType.PROTOCOL_CHANNEL_MESSAGE:
					out.putInt	("ChannelID",		 	p.getChannelID());			// 4
					break;
				}
				if (p.getMessage() != null) {
					out.putInt	("message_type", ext_factory.getType(p.getMessage()));// 4
					var net_out : NetDataOutput = new NetDataOutput(ext_factory);
					ext_factory.writeExternal(p.getMessage(), net_out);
					net_out.position = 0;
					out.putByteArray("message", net_out);
				} else {
					out.putInt	("message_type", 0);//  4
				}
			}
			sentMessage(p);
			return out;
		}
		
		function decode(obj : ISFSObject) : SFSProtocol
		{
			var p : SFSProtocol = new SFSProtocol();

			p.setProtocol 				(obj.getByte	("Protocol"));	// 1
			p.setPacketNumber			(obj.getInt		("PacketNumber"));	// 4
			
			switch (p.getProtocol()) {
			case ProtocolType.PROTOCOL_CHANNEL_JOIN_S2C:
			case ProtocolType.PROTOCOL_CHANNEL_LEAVE_S2C:
			case ProtocolType.PROTOCOL_CHANNEL_MESSAGE:
				p.setChannelID 			(obj.getInt		("ChannelID"));			// 4
				break;
			}
			
			// 解出包包含的二进制消息
			var message_type : int		= obj.getInt	("message_type");
			if (message_type != 0) {
				var data : ByteArray 	= obj.getByteArray("message");
				var msg : Message = ext_factory.createMessage(message_type);
				var net_in : NetDataInput = new NetDataInput(ext_factory);
				net_in.writeBytes(data);
				net_in.position = 0;
				ext_factory.readExternal(msg, net_in);
				p.setMessage			(msg);
			}
			recivedMessage(p);
			return p;
		}
		
		function recivedMessage(decoded : Protocol) : void
		{
			// 判断是否是联盟消息，协议消息等
			switch (decoded.getProtocol()) {
				case ProtocolType.PROTOCOL_CHANNEL_JOIN_S2C:{
					this.user_listener.joinedChannel(decoded.getChannelID(), this);
					break;
				}
				case ProtocolType.PROTOCOL_CHANNEL_LEAVE_S2C:{
					this.user_listener.leftChannel(decoded.getChannelID(), this);
					break;
				}
				case ProtocolType.PROTOCOL_CHANNEL_MESSAGE:{
					this.user_listener.receivedMessage(this, decoded);
					break;
				}
				case ProtocolType.PROTOCOL_SESSION_MESSAGE:{
					this.user_listener.receivedMessage(this, decoded);
					break;
				}
				default:{
					if (decoded.getMessage() != null) {
						this.user_listener.receivedMessage(this, decoded);
					}
				}
			}
		}
		
		function sentMessage(decoded : Protocol) : void
		{
			this.user_listener.sentMessage(this, decoded);
		}
		
//		---------------------------------------------------------------------------------------------------------
//		
//		---------------------------------------------------------------------------------------------------------
				
		private function onConnection(evt:SFSEvent):void
		{
			if (evt.params.success)
			{
				dTrace("Connection Success!")
				sfs.send( new LoginRequest(sfs_user, sfs_pswd, sfs_zone) );
			}
			else
			{
				dTrace("Connection Failure: " + evt.params.errorMessage)
				user_listener.disconnected(this, evt.params.errorMessage);
			}
		}
		
		private function onConnectionLost(evt:SFSEvent):void
		{
			dTrace("Connection was lost. Reason: " + evt.params.reason)
			
			user_listener.disconnected(this, evt.params.reason);
		}
		
//		---------------------------------------------------------------------------------------------------------
//		
//		---------------------------------------------------------------------------------------------------------
				
		private function onLogin(evt:SFSEvent):void
		{
			dTrace("SFS Login success: " + evt.params.user.name);
			user_listener.connected(this);
		}
		
		private function onLoginError(evt:SFSEvent):void
		{
			dTrace("SFS Login failed: " + evt.params.errorMessage);
			sfs.disconnect();
			user_listener.disconnected(this, evt.params.errorMessage);
		}
		
//		---------------------------------------------------------------------------------------------------------
//		
//		---------------------------------------------------------------------------------------------------------
		
		private function onRoomJoin(evt:SFSEvent):void
		{
			dTrace("onRoomJoin success: " + evt.params.toString());
			
		}
		
		private function onRoomJoinError(evt:SFSEvent):void
		{
			dTrace("onRoomJoin failed: " + evt.params.errorMessage);
			
		}
		
//		---------------------------------------------------------------------------------------------------------
//		
//		---------------------------------------------------------------------------------------------------------
		
		private function onExtensionResponse(evt:SFSEvent):void
		{
			var msg : ISFSObject = evt.params.params as SFSObject;
	
			decode(msg);
		}
		
		private function onObjectMessage(evt:SFSEvent):void
		{
			var msg : ISFSObject = evt.params.params as SFSObject;
			
			decode(msg);
			
		}
		
//		---------------------------------------------------------------------------------------------------------
//		
//		---------------------------------------------------------------------------------------------------------
		
		private function dTrace(msg:String):void
		{
			trace("--> " + msg + "\n");
		}
		
	}
}
