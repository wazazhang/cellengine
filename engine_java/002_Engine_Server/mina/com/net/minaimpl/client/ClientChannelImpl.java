package com.net.minaimpl.client;

import com.cell.net.io.MessageHeader;
import com.net.client.ClientChannel;
import com.net.client.ServerSession;

class ClientChannelImpl implements ClientChannel 
{
	final private ServerSessionImpl Session;
	final private int ID;

	public ClientChannelImpl(ServerSessionImpl session, int id) {
		Session = session;
		ID = id;
	}

	public int getID() {
		return ID;
	}

	public void send(MessageHeader message) {
		Session.sendChannel(message, this);
	}

	public ServerSession getSession() {
		return Session;
	}
}
