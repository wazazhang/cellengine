package com.net.client
{
	import com.cell.util.Reference;
	
	internal class ClientRequest extends Reference
	{
		internal var request 		: Message;
		internal var package_num	: int;
		internal var drop_timeout	: int;
		
		internal var response 		: Function;
		internal var timeout 		: Function;
		
		internal var sent_time		: Date;
		
		function ClientRequest(
			message 		: Message, 
			package_num		: int,
			drop_timeout 	: int, 
			response 		: Function,
			timeout 		: Function
		){
			this.request 		= message;
			this.package_num 	= package_num;
			this.drop_timeout	= drop_timeout;
			
			this.response 		= response;
			this.timeout 		= timeout;
		}
		
		internal function getPacketNumber() : int
		{
			return this.package_num;
		}
		
		internal function send(client : Client) : void
		{
			client.getSession().sendRequest(package_num, this.request);
			this.sent_time = new Date();
		}
		
		internal function messageTimeout(client : Client) : void
		{
			client.dispatchEvent(new ClientEvent(
				ClientEvent.REQUEST_TIMEOUT, 
				client, 
				-1,
				request,
				null));
		}
		
		internal function isDrop() : Boolean {
			return (new Date().time - this.sent_time.time) > drop_timeout;
		}
	}
}