package com.net.sfsimpl.server;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import com.net.MessageHeader;
import com.net.Protocol;
import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ClientSession;
import com.net.server.Server;

public class SFSChannel implements Channel
{
	final ChannelListener Listener;
	final int ID;
	final SFSServerAdapter server;
	final ConcurrentHashMap<ClientSession, SFSSession> sessions = new ConcurrentHashMap<ClientSession, SFSSession>();
	
	SFSChannel(int id, SFSServerAdapter server, ChannelListener listener) {
		this.Listener			= listener;
		this.ID 				= id;
		this.server 			= server;
	}
	
	@Override
	public void dispose() {
		server.getChannelManager().removeChannel(getID());
	}
	
	public int getID() {
		return ID;
	}
	
	public Iterator<ClientSession> getSessions() {
		return sessions.keySet().iterator();
	}
	
	public int getSessionCount(){
		return sessions.size();
	}
	
	public boolean hasSessions() {
		return sessions.isEmpty();
	}
	
	public boolean hasSession(ClientSession session){
		return sessions.contains(session);
	}
	
	public boolean join(ClientSession session) {
		if (session instanceof SFSSession) {
			SFSSession impl = (SFSSession)session;
			ClientSession old = sessions.putIfAbsent(session, impl);
			if (old == null) {
				server.send(
						impl, 
						null,
						Protocol.PROTOCOL_CHANNEL_JOIN_S2C, 
						getID(), 
						0);
				Listener.sessionJoined(this, session);
				return true;
			}
		}
		return false;
	}
	
	public boolean leave(ClientSession session) {
		SFSSession old = sessions.remove(session);
		if (old != null) {
			server.send(
					old, 
					null,
					Protocol.PROTOCOL_CHANNEL_LEAVE_S2C, 
					getID(), 
					0);
			Listener.sessionLeaved(this, session);
			return true;
		}
		return false;
	}
	
	public int leaveAll() {
		int count = 0;
		for (Iterator<SFSSession> it = sessions.values().iterator(); it.hasNext(); ) {
			ClientSession session = it.next();
			try {
				if (leave(session)) {
					count ++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	int broadcast(ClientSession sender, MessageHeader message, int packnum)
	{
		int  count = 0;
		for (Iterator<SFSSession> it = sessions.values().iterator(); it.hasNext(); ) {
			SFSSession session = it.next();
			server.send(session,
					message,
					Protocol.PROTOCOL_CHANNEL_MESSAGE,
					ID,
					packnum);
		}
		return count;
	}

	public int send(MessageHeader message) {
		return broadcast(null, message, 0);
	}
	
	public int send(ClientSession sender, MessageHeader message) {
		return broadcast(sender, message, 0);
	}
	
	public int sendResponse(ClientSession sender, Protocol request, MessageHeader response) {
		return broadcast(sender, response, request.getPacketNumber());
	}
	
	public ChannelListener getChannelListener() {
		return Listener;
	}
	
	public Server getServer() {
		return server;
	}
}
