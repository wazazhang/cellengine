package com.net.minaimpl.client;

import com.net.MessageHeader;
import com.net.client.ClientChannel;
import com.net.client.ServerSession;

class ClientChannelImpl implements ClientChannel 
{
	final private ServerSessionImpl Session;
	final private String ID;

	public ClientChannelImpl(ServerSessionImpl session, String id) {
		Session = session;
		ID = id;
	}

	public String getID() {
		return ID;
	}

	public void send(MessageHeader message) {
		Session.sendChannel(message, this);
	}

	public ServerSession getSession() {
		return Session;
	}
}
