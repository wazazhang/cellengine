package com.net.client.minaimpl
{
	import com.cell.net.io.MutualMessage;
	import com.net.client.Protocol;

	public class ProtocolImpl implements Protocol
	{
		public static const TRANSMISSION_TYPE_UNKNOW			: int		= 0x00;
		/** 标识为 {@link Serializable} 方式序列化 */
		public static const TRANSMISSION_TYPE_SERIALIZABLE		: int		= 0x01;
		/** 标识为 {@link ExternalizableMessage} 方式序列化，即以纯手工序列化/反序列化 */
		public static const TRANSMISSION_TYPE_EXTERNALIZABLE	: int		= 0x02;
		/** 标识为 {@link MutualMessage} 方式序列化 */
		public static const TRANSMISSION_TYPE_MUTUAL			: int		= 0x04;
		/** 标识为 {@link CompressingMessage} 方式序列化，压缩包 */
		public static const TRANSMISSION_TYPE_COMPRESSING		: int		= 0x10;

		
		protected var		protocol 			: int ;
		protected var		packet_number 		: int;
		protected var 		channel_id 			: int;
		protected var 		sent_time 			: Date;
		protected var 		received_time 		: Date;
		protected var 		message 			: MutualMessage;
		
		var buffer_size : int;
		
		function ProtocolImpl(size : int)
		{
			this.buffer_size = size;
		}
		
		
		function setProtocol(protocol : int) : void {
			this.protocol = protocol;
		}
		function setPacketNumber(packet_number : int) : void {
			this.packet_number = packet_number;
		}
		function setChannelID(channel_id : int) : void {
			this.channel_id = channel_id;
		}
		function setSentTime(sent_time : Date) : void {
			this.sent_time = sent_time;
		}
		function setReceivedTime(received_time : Date) : void {
			this.received_time = received_time;
		}
		function setMessage(message : MutualMessage) : void {
			this.message = message;
		}
		
		
		/**消息类型*/
		public function		getProtocol() : int {
			return this.protocol;
		}
		
		/**匹配Request和Response的值，如果为0，则代表为Notify*/
		public function		getPacketNumber() : int {
			return this.packet_number;
		}
		
		/**频道ID<br>
		 * 仅PROTOCOL_CHANNEL_*类型的消息有效*/
		public function 		getChannelID() : int {
			return this.channel_id;
		}
		
		/**发送时间*/
		public function 		getSentTime(): Date {
			return this.sent_time;
		}
		
		/**接收时间*/
		public function 		getReceivedTime():Date {
			return this.received_time;
		}
		
		/**包含的消息*/
		public function 		getMessage() : MutualMessage {
			return this.message;
		}
	}
}