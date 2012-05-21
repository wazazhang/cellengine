package com.net.server;

import com.cell.net.io.MessageHeader;
import com.net.Protocol;
import com.net.server.ClientSession;

public interface ServerMessageHandler 
{
	public void onReceived(ClientSession session, Protocol protocol, MessageHeader request);
}
