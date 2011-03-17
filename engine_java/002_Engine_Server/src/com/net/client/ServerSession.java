package com.net.client;

import java.io.IOException;

import com.net.AbstractSession;
import com.net.MessageHeader;

public interface ServerSession extends AbstractSession
{
	public void		dispose();
	
	public boolean	send(MessageHeader message);
	
	public boolean	sendRequest(int pnum, MessageHeader message);
	
	public boolean	connect(String host, int port, long timeout, ServerSessionListener listener) throws IOException;
	
	public boolean	connect(String host, int port, ServerSessionListener listener) throws IOException;
	
	public long		getHeartBeatSent();
	
	public long		getHeartBeatReceived();
	
	
	
}
