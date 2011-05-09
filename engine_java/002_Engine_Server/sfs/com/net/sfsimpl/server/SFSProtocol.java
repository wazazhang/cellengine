package com.net.sfsimpl.server;

import java.io.Serializable;

import com.net.ExternalizableMessage;
import com.net.MessageHeader;


final public class SFSProtocol  implements com.net.Protocol
{	
//	-------------------------------------------------------------------------------
	
	/**消息类型*/
	byte 				Protocol;		
	
	/**该链接对于服务器的唯一ID*/
	long 				SessionID;
	
	/**匹配Request和Response的值，如果为0，则代表为Notify*/
	int					PacketNumber		= 0;
	
	/** 标识为 {@link Serializable} 方式序列化 */
	byte				transmission_flag	= 0;
	
//	-------------------------------------------------------------------------------
	
	/**频道ID<br>
	 * 仅PROTOCOL_CHANNEL_*类型的消息有效*/
	int					ChannelID;
	
	/**频道发送者的SessionID<br>
	 * 仅PROTOCOL_CHANNEL_*类型的消息有效*/
	long				ChannelSesseionID;

//	-------------------------------------------------------------------------------
//	/**发送时间*/
	transient long		DynamicSendTime;
	
	/**接收时间*/
	transient long		DynamicReceiveTime;

	public MessageHeader message;
	
//	-------------------------------------------------------------------------------

	public SFSProtocol() {
		// TODO Auto-generated constructor stub
	}

//	-------------------------------------------------------------------------------

	@Override
	public byte getProtocol() {
		return Protocol;
	}

	@Override
	public long getSessionID() {
		return SessionID;
	}
	
	@Override
	public int getPacketNumber() {
		return PacketNumber;
	}

	@Override
	public int getChannelID() {
		return ChannelID;
	}

	@Override
	public long getChannelSesseionID() {
		return ChannelSesseionID;
	}

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
