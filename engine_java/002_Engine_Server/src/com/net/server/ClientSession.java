package com.net.server;

import com.cell.net.io.MessageHeader;
import com.net.AbstractSession;
import com.net.Protocol;


public interface ClientSession extends AbstractSession
{
	public String 					getName();

	public Server					getServer();
	
	public ClientSessionListener	getListener();
	
	public boolean					send(MessageHeader message);
	
	public boolean 					sendResponse(Protocol request, MessageHeader response);
	
	
}
