package com.net.client;

import com.cell.net.io.MessageHeader;
import com.net.Protocol;


public interface ServerSessionListener
{
	
	public void connected(ServerSession session);
	
	public void disconnected(ServerSession session, boolean graceful, String reason);

	public void onError(ServerSession session, Throwable err);
	
    public void joinedChannel(ClientChannel channel) ;
	
    public void leftChannel(ClientChannel channel);
 
    
    

	public void sentMessage(ServerSession session, Protocol protocol, MessageHeader message);

	
	
	public void receivedMessage(ServerSession session, Protocol protocol, MessageHeader message);
	
	
    public void receivedChannelMessage(ClientChannel channel, Protocol protocol, MessageHeader message);
        

}
