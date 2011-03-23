package com.net.client;


import com.net.MessageHeader;

public interface ClientChannel {

	public String	getID();
	
	public void		send(MessageHeader message);
	
	public ServerSession getSession();
}
