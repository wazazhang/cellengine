package com.net.minaimpl;

import com.cell.net.io.MessageHeader;


final public class NetPackageProtocol 
{
	final public static int PACKAGE_DEFAULT_SIZE 		= 2*1024;// 30k
	
	final public static String CHAR_SET 				= "UTF-8";
	
	//----------------------------------------------------------------------------------------------------------------		

	final public static short PROTOCOL_HART_BEAT			= 0x3000;
	
	final public static short PROTOCOL_SYSTEM_MESSAGE		= 0x3001;

	final public static short PROTOCOL_SESSION_MESSAGE		= 0x3030;
	
	final public static short PROTOCOL_CHANNEL_JOIN_S2C		= 0x3050;

	final public static short PROTOCOL_CHANNEL_LEAVE_S2C	= 0x3051;
	
	final public static short PROTOCOL_CHANNEL_MESSAGE		= 0x3052;
	
	//----------------------------------------------------------------------------------------------------------------		
	
	final static public int FixedHeaderSize 	= 4+4+2+2+8 +4+2+2 +4+2;
	
	//----------------------------------------------------------------------------------------------------------------		
	/*******************************************************/
	// fixed protocol head
	static byte[]			MagicStart	= new byte[]{2,0,0,6,}; // [4] 
	protected int 			Flags;								// [4] 包携带的标志，可以自定义
	protected short  		Dummy;								// [2] 预留
	protected int			Size;								// [2]
	protected long 			SesseionID;							// [8] 可以同时包含连接的IP地址和端口号以及所在的服务器端的频段号
	
	protected int 			PacketID 	= 0;					// [4] // 拆包合包用
	protected short 		TotalPieces = 1;					// [2] // 拆包合包用
	protected short 		PieceIndex 	= 0;					// [2] // 拆包合包用

	protected int			ChannelID;							// [4] 频道ID，仅PROTOCOL_CHANNEL_*类型的消息有效
	protected short 		Protocol; 							// [2] 消息类型	

	protected MessageHeader Message; 							// [?]

	/*******************************************************/
	// message info
	
	//----------------------------------------------------------------------------------------------------------------		
	
	public static void setMagicStart(byte[] start) {
		if (start.length==MagicStart.length) {
			MagicStart = start;
		}
	}
	
	static long HartBeatInterval = 10000;
	
	public static void setHartBeatInterval(long interval) {
		HartBeatInterval = interval;
	}
	
	//----------------------------------------------------------------------------------------------------------------		
	
	NetPackageProtocol() {}
	
	public int getChannelID() {
		return ChannelID;
	}
	public int getMessageSize() {
		return Size - FixedHeaderSize;
	}
	public MessageHeader getMessage() {
		return Message;
	}
	public short getProtocol() {
		return Protocol;
	}
	
	public String toString() {
		return "NetPackageProtocol" +
				" Size=" + Size + 
				" Protocol=0x" + Integer.toString(Protocol, 16) + 
				" Message=\"" + Message + "\"" +
				"";
	}
	
	//----------------------------------------------------------------------------------------------------------------		

	final public static NetPackageProtocol createHartBeat() {
		NetPackageProtocol ret = new NetPackageProtocol();
		ret.Protocol 		= PROTOCOL_HART_BEAT;
		return ret;
	}
	
	final public static NetPackageProtocol createChannelJoin(int channelID) {
		NetPackageProtocol ret = new NetPackageProtocol();
		ret.Protocol 		= PROTOCOL_CHANNEL_JOIN_S2C;
		ret.ChannelID		= channelID;
		ret.Message			= null;
		return ret;
	}
	
	final public static NetPackageProtocol createChannelLeave(int channelID) {
		NetPackageProtocol ret = new NetPackageProtocol();
		ret.Protocol 		= PROTOCOL_CHANNEL_LEAVE_S2C;
		ret.ChannelID		= channelID;
		ret.Message			= null;
		return ret;
	}
	
	final public static NetPackageProtocol createChannelMessage(int channelID, MessageHeader message) {
		NetPackageProtocol ret = new NetPackageProtocol();
		ret.Protocol 		= PROTOCOL_CHANNEL_MESSAGE;
		ret.ChannelID		= channelID;
		ret.Message			= message;
		return ret;
	}
	
	final public static NetPackageProtocol createSessionMessage(MessageHeader message) {
		NetPackageProtocol ret = new NetPackageProtocol();
		ret.Protocol = PROTOCOL_SESSION_MESSAGE;
		ret.Message			= message;
		return ret;
	}
	
	final public static NetPackageProtocol createSystemMessage(MessageHeader message) {
		NetPackageProtocol ret = new NetPackageProtocol();
		ret.Protocol = PROTOCOL_SYSTEM_MESSAGE;
		ret.Message			= message;
		return ret;
	}
	
	//----------------------------------------------------------------------------------------------------------------		

	//----------------------------------------------------------------------------------------------------------------		

}
