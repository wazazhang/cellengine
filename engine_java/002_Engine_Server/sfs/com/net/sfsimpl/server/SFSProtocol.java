package com.net.sfsimpl.server;

import java.io.Serializable;

import com.cell.net.io.ExternalizableMessage;
import com.cell.net.io.MessageHeader;


final public class SFSProtocol  implements com.net.Protocol
{	
//	-------------------------------------------------------------------------------
	
	/**消息类型*/
	byte 				Protocol;		
	
//	/**该链接对于服务器的唯一ID*/
//	int 				SessionID;
	
	/**匹配Request和Response的值，如果为0，则代表为Notify*/
	int					PacketNumber		= 0;
		
//	-------------------------------------------------------------------------------
	
	/**频道ID<br>
	 * 仅PROTOCOL_CHANNEL_*类型的消息有效*/
	int					ChannelID;
	
//	/**频道发送者的SessionID<br>
//	 * 仅PROTOCOL_CHANNEL_*类型的消息有效*/
//	int					ChannelSessionID;

	
	MessageHeader		Message;
	
	
//	-------------------------------------------------------------------------------
//	/**发送时间*/
	transient long		DynamicSendTime;
	
	/**接收时间*/
	transient long		DynamicReceiveTime;

	
//	-------------------------------------------------------------------------------

	public SFSProtocol() {
		// TODO Auto-generated constructor stub
	}

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
		return Message;
	}
	
	@Override
	public String toString() {
		return "[0x"+Integer.toHexString(Protocol)+"] : " + Message;
	}
}
