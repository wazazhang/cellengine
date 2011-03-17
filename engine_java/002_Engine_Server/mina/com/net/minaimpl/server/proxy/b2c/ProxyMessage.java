package com.net.minaimpl.server.proxy.b2c;

public class ProxyMessage
{
	/** 当前Session加入频道的事件 */
	final public static byte	EVENT_SESSION_CONNECT		= 0x01;
	/** 当前Session离开频道的事件 */
	final public static byte	EVENT_SESSION_DISCONNECT	= 0x02;

	public int		ChannelID 			;
	public long		ChannelSesseionID	;
	public long		SessionID 			;
	public short	Protocol 			;
	public int		PacketNumber		;
	public byte		TransmissionType	;
	public byte		message_type		;
	public byte[]	message_body		;
}
