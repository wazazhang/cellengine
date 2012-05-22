package com.net.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cell.net.io.ExternalizableFactory;
import com.cell.net.io.MessageHeader;

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
	
	public Iterator<ClientSession> 	getSessionsIt();
	
	public List<ClientSession> 		getSessions();
	
	public ChannelManager 			getChannelManager();

	
	public<T extends MessageHeader> 
	void addMessageHandler(Class<T> cls, ServerMessageHandler<T> handler);

	public<T extends MessageHeader> 
	void removeMessageHandler(Class<T> cls, ServerMessageHandler<T> handler);

}
