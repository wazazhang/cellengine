package com.net.client
{
	import com.cell.util.Reference;
	
	class ClientRequest extends Reference
	{
		var request 		: Message;
		var package_num		: int;
		var listener 		: ClientResponseListener;
		var drop_timeout	: int;
		var sent_time		: Date;
		
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
		
		function getPacketNumber()
		{
			return this.package_num;
		}
		
		function send (client : Client) 
		{
			client.getSession().sendRequest(package_num, this.request);
			this.sent_time = new Date();
		}
		
		function messageResponsed(client : Client, protocol : Protocol) 
		{
			this.set(protocol.getMessage());
			this.listener.response(client, request, protocol.getMessage());
		}
		
		function timeout(client : Client) {
			this.listener.timeout(client, request);
		}
		
		function isDrop() : Boolean {
			return (new Date().time - this.sent_time.time) > drop_timeout;
		}
	}
}