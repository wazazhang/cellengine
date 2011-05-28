package com.net.minaimpl.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.net.MessageHeader;
import com.net.Protocol;
import com.net.minaimpl.SystemMessages;
import com.net.minaimpl.SystemMessages.*;
import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ClientSession;
import com.net.server.Server;

public class ChannelImpl implements Channel
{
	final ChannelListener 		Listener;
	
	final int				ID;
	
	final AbstractServer		server;
	
	final ConcurrentHashMap<ClientSession, ClientSessionImpl>
								sessions = new ConcurrentHashMap<ClientSession, ClientSessionImpl>();
	
	final ChannelManagerImpl	channel_manager;;
	
	ChannelImpl(ChannelListener listener, int id, AbstractServer server, ChannelManagerImpl cm) {
		this.Listener			= listener;
		this.ID 				= id;
		this.server 			= server;
		this.channel_manager	= cm;
	}
	
	@Override
	public void dispose() {
		channel_manager.removeChannel(getID());
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
		if (session instanceof ClientSessionImpl) {
			ClientSessionImpl impl = (ClientSessionImpl)session;
			ClientSession old = sessions.putIfAbsent(session, impl);
			if (old == null) {
				server.write(impl.Session, null, Protocol.PROTOCOL_CHANNEL_JOIN_S2C, getID(), impl.getID(), 0);
				Listener.sessionJoined(this, session);
				return true;
			}
		}
		return false;
	}
	
	public boolean leave(ClientSession session) {
		ClientSessionImpl old = sessions.remove(session);
		if (old != null) {
			server.write(old.Session, null, Protocol.PROTOCOL_CHANNEL_LEAVE_S2C, getID(), old.getID(), 0);
			Listener.sessionLeaved(this, session);
			return true;
		}
		return false;
	}
	
	public int leaveAll() {
		int count = 0;
		for (Iterator<ClientSessionImpl> it = sessions.values().iterator(); it.hasNext(); ) {
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
		long sender_id = (sender != null ? sender.getID() : 0);
		int  count = 0;
		for (Iterator<ClientSessionImpl> it = sessions.values().iterator(); it.hasNext(); ) {
			ClientSessionImpl session = it.next();
			server.write(session.Session, message, Protocol.PROTOCOL_CHANNEL_MESSAGE, ID, sender_id, packnum);
		}
		return count;
	}
	
//	int broadcast(ClientSession sender, byte protocol)
//	{
//		long sender_id = (sender != null ? sender.getID() : 0);
//		int  count = 0;
//		for (Iterator<ClientSessionImpl> it = sessions.values().iterator(); it.hasNext(); ) {
//			ClientSessionImpl session = it.next();
//			server.write(session.Session, null, protocol, getID(), sender_id, 0);
//		}
//		return count;
//	}
	
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
