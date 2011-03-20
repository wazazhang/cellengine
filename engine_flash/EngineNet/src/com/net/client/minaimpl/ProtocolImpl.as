package com.net.client.minaimpl
{
	import com.net.client.Message;
	import com.net.client.Protocol;

	public class ProtocolImpl extends Protocol
	{
		public static const TRANSMISSION_TYPE_UNKNOW			: int		= 0x00;
		/** 标识为 {@link Serializable} 方式序列化 */
		public static const TRANSMISSION_TYPE_SERIALIZABLE		: int		= 0x01;
		/** 标识为 {@link ExternalizableMessage} 方式序列化，即以纯手工序列化/反序列化 */
		public static const TRANSMISSION_TYPE_EXTERNALIZABLE	: int		= 0x02;
		/** 标识为 {@link CompressingMessage} 方式序列化，压缩包 */
		public static const TRANSMISSION_TYPE_COMPRESSING		: int		= 0x10;

		
		var buffer_size : int;
		
		function ProtocolImpl(size : int)
		{
			this.buffer_size = size;
		}
		
		
		function setProtocol(protocol : int) : void {
			this.protocol = protocol;
		}
		function setSessionID(s1 : int, s2 : int) : void {
			this.session_id = s1;
		}
		function setPacketNumber(packet_number : int) : void {
			this.packet_number = packet_number;
		}
		function setChannelID(channel_id : int) : void {
			this.channel_id = channel_id;
		}
		function setChannelSessionID(s1 : int, s2 : int) : void {
			this.channel_session_id = s1;
		}
		function setSentTime(sent_time : Date) : void {
			this.sent_time = sent_time;
		}
		function setReceivedTime(received_time : Date) : void {
			this.received_time = received_time;
		}
		function setMessage(message : Message) : void {
			this.message = message;
		}
	}
}