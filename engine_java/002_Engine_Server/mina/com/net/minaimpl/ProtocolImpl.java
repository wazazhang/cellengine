package com.net.minaimpl;

import java.io.Serializable;

import com.cell.net.io.ExternalizableMessage;
import com.cell.net.io.MessageHeader;

public class ProtocolImpl implements com.net.Protocol
{	
	final public static byte	TRANSMISSION_TYPE_UNKNOW			= 0x00;
	/** 标识为 {@link Serializable} 方式序列化 */
	final public static byte	TRANSMISSION_TYPE_SERIALIZABLE		= 0x01;
	/** 标识为 {@link ExternalizableMessage} 方式序列化，即以纯手工序列化/反序列化 */
	final public static byte	TRANSMISSION_TYPE_EXTERNALIZABLE	= 0x02;
	/** 标识为 {@link MutualMessage} 方式序列化 */
	final public static byte	TRANSMISSION_TYPE_MUTUAL			= 0x04;
	/** 标识为 {@link CompressingMessage} 方式序列化，压缩包 */
	final public static byte	TRANSMISSION_TYPE_COMPRESSING		= 0x10;
	

//	-------------------------------------------------------------------------------
	
	/** Session/Server 之间的消息 */
	final public static byte	PROTOCOL_SYSTEM_NOTIFY				= 0x20;
	
	public final static int 	SYSTEM_NOTIFY_SERVER_FULL 			= 0x21;

//	-------------------------------------------------------------------------------
	
	/**消息类型*/
	public byte 				Protocol;		
	
//	/**该链接对于服务器的唯一ID*/
//	public long 				SessionID;
	
	/**匹配Request和Response的值，如果为0，则代表为Notify*/
	public int					PacketNumber		= 0;

	public int					SystemMessage		= 0;
	
	/** 标识为 {@link Serializable} 方式序列化 */
	public byte					transmission_flag	= 0;
	
//	-------------------------------------------------------------------------------
	
	/**频道ID<br>
	 * 仅PROTOCOL_CHANNEL_*类型的消息有效*/
	public int					ChannelID;
	
//	/**频道发送者的SessionID<br>
//	 * 仅PROTOCOL_CHANNEL_*类型的消息有效*/
//	public long					ChannelSessionID;

//	-------------------------------------------------------------------------------
//	/**发送时间*/
	transient long		DynamicSendTime;
	
	/**接收时间*/
	transient long		DynamicReceiveTime;

	public MessageHeader		message;
	
//	-------------------------------------------------------------------------------

	@Override
	public byte getProtocol() {
		return Protocol;
	}

//	@Override
//	public long getSessionID() {
//		return SessionID;
//	}
	
	@Override
	public int getPacketNumber() {
		return PacketNumber;
	}
	
	public int getSystemNotify() {
		return SystemMessage;
	}
	
	@Override
	public int getChannelID() {
		return ChannelID;
	}

//	@Override
//	public long getChannelSessionID() {
//		return ChannelSessionID;
//	}

	@Override
	public long getSentTime() {
		return DynamicSendTime;
	}
	
	@Override
	public long getReceivedTime() {
		return DynamicReceiveTime;
	}
	
	@Override
	public MessageHeader getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return "[0x"+Integer.toHexString(Protocol)+"] : " + message;
	}
}
