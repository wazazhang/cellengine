package com.net.sfsimpl.server;

import java.util.Iterator;

import com.net.MessageHeader;
import com.net.Protocol;
import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ClientSession;
import com.net.server.Server;
import com.smartfoxserver.v2.entities.Room;

public class SFSChannel implements Channel
{
	final Room room;
	
	public SFSChannel(Room room) {
		this.room = room;
	}

	@Override
	public void dispose() {}

	@Override
	public ChannelListener getChannelListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Server getServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSessionCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<ClientSession> getSessions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasSession(ClientSession session) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasSessions() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean join(ClientSession session) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean leave(ClientSession session) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int leaveAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int send(ClientSession sender, MessageHeader message) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int send(MessageHeader message) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int sendResponse(ClientSession sender, Protocol request,
			MessageHeader response) {
		// TODO Auto-generated method stub
		return 0;
	}

}
