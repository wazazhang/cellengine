package com.net;

public interface Protocol
{
//	----------------------------------------------------------------------------------------------------------------		

	/** Session/Server 之间的消息 */
	final public static byte	PROTOCOL_SESSION_MESSAGE			= 0x30;
	/** Session/Channel 之间的消息 */
	final public static byte	PROTOCOL_CHANNEL_MESSAGE			= 0x52;
	/** 当前Session加入频道的事件 */
	final public static byte	PROTOCOL_CHANNEL_JOIN_S2C			= 0x50;
	/** 当前Session离开频道的事件 */
	final public static byte	PROTOCOL_CHANNEL_LEAVE_S2C			= 0x51;

//	----------------------------------------------------------------------------------------------------------------		

	/**消息类型*/
	public byte 	getProtocol();
	
//	/**该链接对于服务器的唯一ID*/
//	public long 	getSessionID();
	
	/**匹配Request和Response的值，如果为0，则代表为Notify*/
	public int		getPacketNumber();
	

	/**频道ID<br>
	 * 仅PROTOCOL_CHANNEL_*类型的消息有效*/
	public int		getChannelID();
	
//	/**频道发送者的SessionID<br>
//	 * 仅PROTOCOL_CHANNEL_*类型的消息有效*/
//	public long		getChannelSessionID();
	
//	----------------------------------------------------------------------------------------------------------------		
//	dynamic fields
	
	/**发送时间*/
	public long		getSentTime();
	
	/**接收时间*/
	public long		getReceivedTime();
//
//	----------------------------------------------------------------------------------------------------------------		
	
	public MessageHeader getMessage();
	
	
//	----------------------------------------------------------------------------------------------------------------		
	
	
	
//	protocol fields
//
//	/**消息类型*/
//	transient public byte 		Protocol;
//	
//	/**该链接对于服务器的唯一ID*/
//	transient public long 		SessionID;
//	
//	/**匹配Request和Response的值，如果为0，则代表为Notify*/
//	transient public int		PacketNumber	= 0;
//	
//
//	/**频道ID<br>
//	 * 仅PROTOCOL_CHANNEL_*类型的消息有效*/
//	transient public int		ChannelID;
//	
//	/**频道发送者的SessionID<br>
//	 * 仅PROTOCOL_CHANNEL_*类型的消息有效*/
//	transient public long		ChannelSesseionID;
//	
//	
//----------------------------------------------------------------------------------------------------------------		
//	dynamic fields
//
//	/**发送时间*/
//	transient public long		DynamicSendTime;
//	
//	/**接收时间*/
//	transient public long		DynamicReceiveTime;
//	
//----------------------------------------------------------------------------------------------------------------		

}
