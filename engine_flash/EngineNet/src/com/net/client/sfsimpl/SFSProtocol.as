package com.net.client.sfsimpl
{
	import com.net.client.Message;
	import com.net.client.Protocol;

	public class SFSProtocol extends Protocol
	{
		public function SFSProtocol()
		{
		}
		
		function setProtocol(protocol : int) : void {
			this.protocol = protocol;
		}
		function setSessionID(s1 : int) : void {
			this.session_id = s1;
		}
		function setPacketNumber(packet_number : int) : void {
			this.packet_number = packet_number;
		}
		function setChannelID(channel_id : int) : void {
			this.channel_id = channel_id;
		}
		function setChannelSessionID(s1 : int) : void {
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