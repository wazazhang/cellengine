package com.net.client
{
	final public class ProtocolType
	{
		/** Session/Server 之间的消息 */
		static public const PROTOCOL_SESSION_MESSAGE	 	: int	= 0x30;
		/** Session/Channel 之间的消息 */
		static public const PROTOCOL_CHANNEL_MESSAGE 		: int	= 0x52;
		/** 当前Session加入频道的事件 */
		static public const PROTOCOL_CHANNEL_JOIN_S2C		: int	= 0x50;
		/** 当前Session离开频道的事件 */
		static public const PROTOCOL_CHANNEL_LEAVE_S2C 		: int	= 0x51;
		
		/** Session/Server 之间的消息 */
		static public const PROTOCOL_SYSTEM_NOTIFY 			: int	= 0x20;
		
		static public const SYSTEM_NOTIFY_SERVER_FULL		: int	= 0x21;
	}
}