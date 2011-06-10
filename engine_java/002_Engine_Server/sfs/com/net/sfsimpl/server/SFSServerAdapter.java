package com.net.sfsimpl.server;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.mina.core.buffer.IoBuffer;

import com.cell.exception.NotImplementedException;
import com.net.CompressingMessage;
import com.net.ExternalizableFactory;
import com.net.ExternalizableMessage;
import com.net.MessageHeader;
import com.net.Protocol;

import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ChannelManager;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.server.Server;
import com.net.server.ServerListener;

import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.ISFSEventListener;
import com.smartfoxserver.v2.core.ISFSEventParam;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.SFSRoomRemoveMode;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.Zone;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSCreateRoomException;
import com.smartfoxserver.v2.extensions.SFSExtension;
import com.smartfoxserver.v2.util.ClientDisconnectionReason;

public class SFSServerAdapter implements Server
{
	public static int PACKAGE_DEFAULT_SIZE = 4096;

//	--------------------------------------------------------------------------------------
	
	final SFSExtension 					extension;
	
	final private RoomChannelManager 	channel_manager;
	
	final private ConcurrentHashMap<Integer, SFSSession> 
										sessions = new ConcurrentHashMap<Integer, SFSSession>();
	
	final private Zone 					current_zone;

	final private ExternalizableFactory	ext_factory;
	
	final private ISFSEventListener		event_listener;

	
	private ServerListener				server_listener;
	
//	--------------------------------------------------------------------------------------
	
	public SFSServerAdapter(
			SFSExtension extension, 
			ExternalizableFactory codec) 
	{
		this.extension 			= extension;
		this.channel_manager 	= new RoomChannelManager();
		this.current_zone 		= extension.getParentZone();
		this.ext_factory 		= codec;
		this.event_listener		= new ServerEventListener();

//		addEventListener(SFSEventType.BUDDY_ADD, this);
//		addEventListener(SFSEventType.BUDDY_BLOCK, this);
//		addEventListener(SFSEventType.BUDDY_LIST_INIT, this);
//		addEventListener(SFSEventType.BUDDY_MESSAGE, this);
//		addEventListener(SFSEventType.BUDDY_ONLINE_STATE_UPDATE, this);
//		addEventListener(SFSEventType.BUDDY_REMOVE, this);
//		addEventListener(SFSEventType.BUDDY_VARIABLES_UPDATE, this);
//
//		addEventListener(SFSEventType.GAME_INVITATION_FAILURE, this);
//		addEventListener(SFSEventType.GAME_INVITATION_SUCCESS, this);
//		addEventListener(SFSEventType.PLAYER_TO_SPECTATOR, this);
//		addEventListener(SFSEventType.PRIVATE_MESSAGE, this);
//		addEventListener(SFSEventType.PUBLIC_MESSAGE, this);
//		addEventListener(SFSEventType.ROOM_ADDED, this);
//		addEventListener(SFSEventType.ROOM_REMOVED, this);
//		addEventListener(SFSEventType.ROOM_VARIABLES_UPDATE, this);
//		addEventListener(SFSEventType.SERVER_READY, this);
//		addEventListener(SFSEventType.SPECTATOR_TO_PLAYER, this);

		this.extension.addEventListener(SFSEventType.USER_DISCONNECT, 			event_listener);
		this.extension.addEventListener(SFSEventType.USER_JOIN_ROOM, 			event_listener);
		this.extension.addEventListener(SFSEventType.USER_JOIN_ZONE, 			event_listener);
		this.extension.addEventListener(SFSEventType.USER_LEAVE_ROOM, 			event_listener);
		this.extension.addEventListener(SFSEventType.USER_LOGIN, 				event_listener);
		this.extension.addEventListener(SFSEventType.USER_LOGOUT, 				event_listener);
		this.extension.addEventListener(SFSEventType.USER_RECONNECTION_SUCCESS, event_listener);
		this.extension.addEventListener(SFSEventType.USER_RECONNECTION_TRY, 	event_listener);
		this.extension.addEventListener(SFSEventType.USER_VARIABLES_UPDATE, 	event_listener);

		trace(getClass().getSimpleName() + " ready, " +
				"current zone is [" + current_zone.getName() + "]");
	}


	public void handleClientRequest(String requestId, User sender, ISFSObject params) 
	{
		SFSSession session = (SFSSession)sender.getProperty(ClientSession.class);
		if (session != null)
		{
			try {
				SFSProtocol protocol = decode(params);
				if (protocol != null) {
					session.getListener().receivedMessage(session, protocol, protocol.getMessage());
				} else {
					trace("ERROR" +
							" : handleClientRequest" +
							" : " + params.toString());
					disconnect(sender);
				}
			} catch (Throwable e) {
				trace("ERROR" +
						" : handleClientRequest" +
						" : " + params.toString() + 
						" : " + e.getMessage() + 
						" : " + e);
				disconnect(sender);
			}
		} else {
			trace("ERROR" +
					" : handleClientRequest" +
					" : session not found, as " + params.toString());
			disconnect(sender);
		}
	}
	private class ServerEventListener implements ISFSEventListener
	{
		@Override
		public void handleServerEvent(ISFSEvent event) throws Exception
		{
			System.out.println("handleServerEvent:" + event.toString());
			switch (event.getType()) {
			case USER_LOGIN:
			case USER_JOIN_ZONE:
			{
				User user = (User)event.getParameter(SFSEventParam.USER);
				synchronized (sessions) {
					SFSSession session = (SFSSession)user.getProperty(ClientSession.class);
					if (session == null) {
						session = new SFSSession(user, SFSServerAdapter.this);
						ClientSessionListener listener = server_listener.connected(session);
						session.setListener(listener);
						user.setProperty(ClientSession.class, session);
						sessions.put(user.getId(), session);
					}
				}
				break;
			}
			case USER_LOGOUT:
			{				
				User user = (User)event.getParameter(SFSEventParam.USER);
				user.disconnect(ClientDisconnectionReason.IDLE);
				break;
			}
			case USER_DISCONNECT: 
			{
				User user = (User)event.getParameter(SFSEventParam.USER);
				synchronized (sessions) {
					SFSSession session = (SFSSession)user.getProperty(ClientSession.class);
					if (session != null) {
						session = sessions.remove(user.getId());
						user.removeProperty(ClientSession.class);
						session.getListener().disconnected(session);
					}
				}
				break;
			}
			default:
				break;
			}
			
		}
	}

//	----------------------------------------------------------------------------------------------------------

	@Override
	public ExternalizableFactory getMessageFactory() {
		return ext_factory;
	}

	public void trace(Object ... args) {
		extension.trace(args);
	}
	
//	----------------------------------------------------------------------------------------------------------

	
	private SFSProtocol decode(ISFSObject in) throws Throwable
	{
		SFSProtocol p = new SFSProtocol();
		
		p.DynamicReceiveTime		= System.currentTimeMillis();

		p.Protocol 					= in.getByte("Protocol");		// 1
		p.PacketNumber				= in.getInt	("PacketNumber");	// 4
		
		switch (p.Protocol) {
		case Protocol.PROTOCOL_CHANNEL_JOIN_S2C:
		case Protocol.PROTOCOL_CHANNEL_LEAVE_S2C:
		case Protocol.PROTOCOL_CHANNEL_MESSAGE:
			p.ChannelID 			= in.getInt("ChannelID");			// 4
			break;
		}
		
		// 解出包包含的二进制消息
		int message_type 			= in.getInt("message_type");
		if (message_type != 0) {
			p.Message = ext_factory.createMessage(message_type);	// ext 4
			ExternalizableMessage ext = (ExternalizableMessage)p.Message;
			ext.readExternal(new NetDataInputImpl(in.getByteArray("message"), ext_factory));
		}

		return p;
	}
	
	private ISFSObject encode(SFSProtocol p) throws Throwable
	{
		p.DynamicSendTime = System.currentTimeMillis();
		
		ISFSObject out = SFSObject.newInstance();
		{
			out.putByte		("Protocol", 			p.Protocol);			// 1
			out.putInt		("PacketNumber",		p.PacketNumber);		// 4
			
			switch (p.Protocol) {
			case Protocol.PROTOCOL_CHANNEL_JOIN_S2C:
			case Protocol.PROTOCOL_CHANNEL_LEAVE_S2C:
			case Protocol.PROTOCOL_CHANNEL_MESSAGE:
				out.putInt	("ChannelID",		 	p.ChannelID);			// 4
				break;
			}

			if (p.Message instanceof ExternalizableMessage) {
				out.putInt("message_type", ext_factory.getType(p.Message));	// ext 4
				NetDataOutputImpl net_out = new NetDataOutputImpl(PACKAGE_DEFAULT_SIZE, ext_factory);
				((ExternalizableMessage)p.Message).writeExternal(net_out);
				byte[] data = net_out.buffer.shrink().flip().array();
				out.putByteArray("message", data);
			} else {
				out.putInt("message_type", 0);	// ext 4
			}
		}

		p.DynamicSendTime = System.currentTimeMillis();
		return out;
	}
	
	void disconnect(User user) {
		this.extension.getApi().disconnectUser(user, 
				ClientDisconnectionReason.KICK);
	}
	
	void send(
			User 			user, 
			MessageHeader 	message,
			byte			protocol, 
			int				channel_id, 
			int				packnumber)
	{
		SFSProtocol p = new SFSProtocol();
		p.Message 			= message;
		p.Protocol			= protocol;
		p.ChannelID			= channel_id;
		p.PacketNumber		= packnumber;
		
		try {
			extension.send("msg", encode(p), user);
		} catch (Throwable e) {
			trace(e);
		}
	}

	@Override
	public void broadcast(MessageHeader message) 
	{
		SFSProtocol p = new SFSProtocol();
		p.Message 			= message;
//		p.SessionID 		= 0;
		p.Protocol			= Protocol.PROTOCOL_SESSION_MESSAGE;
		p.ChannelID			= 0;
//		p.ChannelSessionID	= 0;
		p.PacketNumber		= 0;
		
		try {
			ArrayList<User> users = new ArrayList<User>(sessions.size());
			synchronized (sessions) {
				for (SFSSession s : sessions.values()) {
					users.add(s.user);
				}
			}
			extension.send("msg", encode(p), users);
		} catch (Throwable e) {
			trace(e);
		}
	}

//	----------------------------------------------------------------------------------------------------------

	@Override
	public ClientSession getSession(long sessionID) {
		User user = extension.getApi().getUserById((int)sessionID);
		if (user != null) {
			ClientSession session = (ClientSession)user.getProperty(ClientSession.class);
			return session;
		}
		return null;
	}
	
	@Override
	public int getSessionCount() {
		return sessions.size();
	}
	
	@Override
	public Iterator<ClientSession> getSessions() {
		ArrayList<ClientSession> users = new ArrayList<ClientSession>(sessions.values());
		return users.iterator();
	}
	
	@Override
	public boolean hasSession(ClientSession session) {
		return sessions.containsValue(session);
	}

//	----------------------------------------------------------------------------------------------------------

	@Override
	public ChannelManager getChannelManager() 
	{
		return channel_manager;
	}
	
	class RoomChannelManager implements ChannelManager
	{
		ConcurrentHashMap<Integer, SFSChannel> channels = new ConcurrentHashMap<Integer, SFSChannel>();
		
		@Override
		public Channel createChannel(int id, ChannelListener listener) {
			synchronized (channels) {
				if (!channels.containsKey(id)) {
					SFSChannel channel = new SFSChannel(id, SFSServerAdapter.this, listener);
					channels.put(id, channel);
					return channel;
				}
			}
			return null;
		}

		@Override
		public Channel getChannel(int id) {
			return channels.get(id);
		}

		@Override
		public Iterator<Channel> getChannels() {
			ArrayList<Channel> ret = new ArrayList<Channel>(channels.values());
			return ret.iterator();
		}

		@Override
		public Channel removeChannel(int id) {
			SFSChannel channel = null;
			synchronized (channels) {
				channel = channels.remove(id);
			}
			channel.leaveAll();
			return channel;
		}
		
	}

//	----------------------------------------------------------------------------------------------------------
	
//	----------------------------------------------------------------------------------------------------------

	@Override
	public void open(int port, ServerListener listener) throws IOException {
		this.server_listener = listener;
		this.server_listener.init(this);
	}
	
	@Override
	public void dispose() throws IOException {
		server_listener.destory();
	}
	
	
}
