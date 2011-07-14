package com.net.client;


import com.cell.net.io.MessageHeader;

public interface ClientChannel {

	public int	getID();
	
	public void		send(MessageHeader message);
	
	public ServerSession getSession();
}
