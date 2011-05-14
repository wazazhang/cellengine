package com.net.client
{
	public interface Protocol
	{
		/**消息类型*/
		function		getProtocol() : int ;
		
		/**该链接对于服务器的唯一ID*/
		function  		getSessionID() : Number;
		
		/**匹配Request和Response的值，如果为0，则代表为Notify*/
		function		getPacketNumber() : int ;
		
		/**频道ID<br>
		 * 仅PROTOCOL_CHANNEL_*类型的消息有效*/
		function 		getChannelID() : int ;
		
		/**频道发送者的SessionID<br>
		 * 仅PROTOCOL_CHANNEL_*类型的消息有效*/
		function 		getChannelSessionID() : Number;
		
		/**发送时间*/
		function 		getSentTime(): Date ;
		
		/**接收时间*/
		function 		getReceivedTime():Date ;
		
		/**包含的消息*/
		function 		getMessage() : Message ;
		
	}
	
	
	
}
