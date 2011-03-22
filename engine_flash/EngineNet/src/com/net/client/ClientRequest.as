package com.net.client
{
	import com.cell.util.Reference;
	
	internal class ClientRequest extends Reference
	{
		internal var request 		: Message;
		internal var package_num		: int;
		internal var listener 		: ClientResponseListener;
		internal var drop_timeout	: int;
		internal var sent_time		: Date;
		
		function ClientRequest(
			message 		: Message, 
			drop_timeout 	: int, 
			package_num		: int,
			listener 		: ClientResponseListener)
		{
			this.request 		= message;
			this.package_num 	= package_num;
			this.listener 		= listener;
			this.drop_timeout	= drop_timeout;
		}
		
		internal function getPacketNumber() : int
		{
			return this.package_num;
		}
		
		internal function send (client : Client) : void
		{
			client.getSession().sendRequest(package_num, this.request);
			this.sent_time = new Date();
		}
		
		internal function messageResponsed(client : Client, protocol : Protocol) : void 
		{
			this.set(protocol.getMessage());
			this.listener.response(client, request, protocol.getMessage());
		}
		
		internal function timeout(client : Client) : void
		{
			this.listener.timeout(client, request);
		}
		
		internal function isDrop() : Boolean {
			return (new Date().time - this.sent_time.time) > drop_timeout;
		}
	}
}