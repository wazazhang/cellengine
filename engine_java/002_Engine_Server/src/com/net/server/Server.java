package com.net.server;

import java.io.IOException;
import java.util.Iterator;

import com.net.ExternalizableFactory;
import com.net.MessageHeader;

public interface Server 
{
	 
	public void 					open(int port, ServerListener listener) throws IOException ;
	
//	public void 					close() throws IOException;
	
	public void 					dispose() throws IOException;
	
	
	public void						broadcast(MessageHeader message);
	
	public ExternalizableFactory	getMessageFactory();
//	
//	public long 					getSentMessageCount() ;
//	
//	public long 					getReceivedMessageCount ();
//	
//	public long 					getSentBytes();
//	
//	public long 					getReceivedBytes();
//	
//	
//	public long						getHeartBeatSent();
//	
//	public long						getHeartBeatReceived();

	
	public int 						getSessionCount();
	
	public boolean 					hasSession(ClientSession session);
	
	public ClientSession 			getSession(long sessionID);
	
	public Iterator<ClientSession> 	getSessions();
	
	public ChannelManager 			getChannelManager();

	
	
}
