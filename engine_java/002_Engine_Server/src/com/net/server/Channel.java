package com.net.server;

import java.util.Iterator;
import java.util.Set;

import com.net.MessageHeader;
import com.net.Protocol;

public interface Channel 
{
	public int 						getID();
	
	public Iterator<ClientSession> 	getSessions();
	
	public int 						getSessionCount();
	
	public boolean 					hasSessions();
	
	public boolean 					hasSession(ClientSession session);
	
	public boolean 					join(ClientSession session);
		
	public boolean 					leave(ClientSession session);
			
	public int 						leaveAll();
	
	public int 						send(MessageHeader message);
	
	public int 						send(ClientSession sender, MessageHeader message);
	
	public int 						sendResponse(ClientSession sender, Protocol request, MessageHeader response);
		
	public Server					getServer();
	
	public ChannelListener 			getChannelListener();
	
	public void						dispose();
	
}
