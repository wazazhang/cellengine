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

abstract public class ServerExtenstion extends SFSExtension implements Server
{
	public static int PACKAGE_DEFAULT_SIZE = 4096;

//	--------------------------------------------------------------------------------------
	private ServerListener	server_listener;
	
	private RoomChannelManager channel_manager;
	
	private UserEventListener user_listener;
	
	private ConcurrentHashMap<Integer, SFSSession> sessions = new ConcurrentHashMap<Integer, SFSSession>();
	
	private Zone current_zone;
	
	private ExternalizableFactory ext_factory;
	
//	--------------------------------------------------------------------------------------
	
	@Override
	public void init() 
	{
		channel_manager = new RoomChannelManager();
		
		user_listener = new UserEventListener();
		
		current_zone = getParentZone();
		
		trace(getClass().getSimpleName() + " ready, current zone is [" + current_zone.getName() + "]");
	
		ext_factory = createFactory();
		
		try {
			server_listener = createListener();
		} catch (Exception e) {
			e.printStackTrace();
			server_listener = new ServerListener() {
				@Override
				public ClientSessionListener connected(
						ClientSession session) {
					trace("no session listener !");
					return null;
				}
			};
		}
	
	
	}
	
	

//	------------------------------------------------------------------------------------
	
	abstract protected ServerListener 			createListener() throws Exception;
	
	abstract protected ExternalizableFactory 	createFactory();
	
//	------------------------------------------------------------------------------------
	
	class UserEventListener implements ISFSEventListener
	{
		public UserEventListener() 
		{
//			addEventListener(SFSEventType.BUDDY_ADD, this);
//			addEventListener(SFSEventType.BUDDY_BLOCK, this);
//			addEventListener(SFSEventType.BUDDY_LIST_INIT, this);
//			addEventListener(SFSEventType.BUDDY_MESSAGE, this);
//			addEventListener(SFSEventType.BUDDY_ONLINE_STATE_UPDATE, this);
//			addEventListener(SFSEventType.BUDDY_REMOVE, this);
//			addEventListener(SFSEventType.BUDDY_VARIABLES_UPDATE, this);
//
//			addEventListener(SFSEventType.GAME_INVITATION_FAILURE, this);
//			addEventListener(SFSEventType.GAME_INVITATION_SUCCESS, this);
//			addEventListener(SFSEventType.PLAYER_TO_SPECTATOR, this);
//			addEventListener(SFSEventType.PRIVATE_MESSAGE, this);
//			addEventListener(SFSEventType.PUBLIC_MESSAGE, this);
//			addEventListener(SFSEventType.ROOM_ADDED, this);
//			addEventListener(SFSEventType.ROOM_REMOVED, this);
//			addEventListener(SFSEventType.ROOM_VARIABLES_UPDATE, this);
//			addEventListener(SFSEventType.SERVER_READY, this);
//			addEventListener(SFSEventType.SPECTATOR_TO_PLAYER, this);

			addEventListener(SFSEventType.USER_DISCONNECT, this);
			addEventListener(SFSEventType.USER_JOIN_ROOM, this);
			addEventListener(SFSEventType.USER_JOIN_ZONE, this);
			addEventListener(SFSEventType.USER_LEAVE_ROOM, this);
			addEventListener(SFSEventType.USER_LOGIN, this);
			addEventListener(SFSEventType.USER_LOGOUT, this);
			addEventListener(SFSEventType.USER_RECONNECTION_SUCCESS, this);
			addEventListener(SFSEventType.USER_RECONNECTION_TRY, this);
			addEventListener(SFSEventType.USER_VARIABLES_UPDATE, this);

		}
		
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
					if (!sessions.containsKey(user)) {
						SFSSession session = new SFSSession(user, ServerExtenstion.this);
						ClientSessionListener listener = server_listener.connected(session);
						session.setListener(listener);
						user.setProperty(ClientSession.class, session);
						sessions.put(user.getId(), session);
					}
				}
				break;
			}
			case USER_LOGOUT:
			case USER_DISCONNECT: 
			{
				User user = (User)event.getParameter(SFSEventParam.USER);
				synchronized (sessions) {
					SFSSession session = sessions.remove(user.getId());
					if (session != null) {
						user.removeProperty(ClientSession.class);
						session.getListener().disconnected(session);
					}
				}
			}
			default:
				break;
			}
			
		}
	}

	@Override
	public void handleClientRequest(String requestId, User sender, ISFSObject params) 
	{
//		trace("handleC/lientRequest : " + params.toString());
		SFSSession session = (SFSSession)sender.getProperty(ClientSession.class);
		if (session != null)
		{
			try {
				SFSProtocol protocol = decode(params);
				if (protocol != null) {
					session.getListener().receivedMessage(session, protocol, protocol.getMessage());
				} else {
					trace("ERROR : handleClientRequest : " + params.toString());
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

//	----------------------------------------------------------------------------------------------------------

	private SFSProtocol decode(ISFSObject in) throws Throwable
	{
		SFSProtocol p = new SFSProtocol();
		
		p.DynamicReceiveTime		= System.currentTimeMillis();

		p.Protocol 					= in.getByte("Protocol");		// 1
		p.SessionID 				= in.getInt	("SessionID");		// 4
		p.PacketNumber				= in.getInt	("PacketNumber");	// 4
		
		switch (p.Protocol) {
		case Protocol.PROTOCOL_CHANNEL_JOIN_S2C:
		case Protocol.PROTOCOL_CHANNEL_LEAVE_S2C:
		case Protocol.PROTOCOL_CHANNEL_MESSAGE:
			p.ChannelID 			= in.getInt("ChannelID");			// 4
			p.ChannelSessionID 		= in.getInt("ChannelSessionID");	// 4
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
			out.putInt		("SessionID", 			p.SessionID);			// 8
			out.putInt		("PacketNumber",		p.PacketNumber);		// 4
			
			switch (p.Protocol) {
			case Protocol.PROTOCOL_CHANNEL_JOIN_S2C:
			case Protocol.PROTOCOL_CHANNEL_LEAVE_S2C:
			case Protocol.PROTOCOL_CHANNEL_MESSAGE:
				out.putInt	("ChannelID",		 	p.ChannelID);			// 4
				out.putInt	("ChannelSessionID",	p.ChannelSessionID);	// 8
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
	
	void send(
			SFSSession 		session, 
			MessageHeader 	message,
			byte			protocol, 
			int				channel_id, 
			long			channel_sender_id,
			int				packnumber)
	{
		SFSProtocol p = new SFSProtocol();
		p.Message 			= message;
		p.SessionID 		= (int)session.getID();
		p.Protocol			= protocol;
		p.ChannelID			= channel_id;
		p.ChannelSessionID	= p.SessionID;
		p.PacketNumber		= packnumber;
		
		try {
			this.send("msg", encode(p), session.user);
//			this.getApi().sendExtensionResponse("msg", encode(p), session.user, null, false);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void broadcast(MessageHeader message) 
	{
		SFSProtocol p = new SFSProtocol();
		p.Message 			= message;
		p.SessionID 		= 0;
		p.Protocol			= Protocol.PROTOCOL_SESSION_MESSAGE;
		p.ChannelID			= 0;
		p.ChannelSessionID	= 0;
		p.PacketNumber		= 0;
		
		try {
			ArrayList<User> users = new ArrayList<User>(sessions.size());
			synchronized (sessions) {
				for (SFSSession s : sessions.values()) {
					users.add(s.user);
				}
			}
			this.send("msg", encode(p), users);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

//	----------------------------------------------------------------------------------------------------------

	@Override
	public ClientSession getSession(long sessionID) {
		User user = getApi().getUserById((int)sessionID);
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
					try {
						CreateRoomSettings settings = new CreateRoomSettings();
						settings.setName("r_" + id);
						settings.setMaxUsers(10000);
						settings.setMaxVariablesAllowed(1);
						settings.setAutoRemoveMode(SFSRoomRemoveMode.DEFAULT);
//						Parameters:
//						zone - the Zone in which the Room is going to be created
//						settings - the Room settings
//						owner - the Room owner, when null it indicates that the Room is owned by the Server itself
//						joinIt - if true the Room will be joined by the owner right after creation
//						roomToLeave - a previous Room to leave after the join, or null
//						fireClientEvent - fire a client side Event
//						fireServerEvent - fire a server side Event
//						Returns:
//						the Room
//						Throws:
//						SFSCreateRoomException
//						See Also:
//						CreateRoomSettings
						Room room = getApi().createRoom(current_zone,
				                settings,
				               	null,
				                false,
				                null,
				                false,
				                true);
//						Room room = getApi().createRoom(current_zone, settings, null);
						SFSChannel channel = new SFSChannel(room);
//						room.setProperty(SFSChannel.class, channel);
						channels.put(id, channel);
						return channel;
					} catch (SFSCreateRoomException e) {
						e.printStackTrace();
					}
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
			synchronized (channels) {
				SFSChannel channel = channels.remove(id);
				channel.room.getZone().removeRoom(channel.room.getName());
			}
			return null;
		}
		
	}

//	----------------------------------------------------------------------------------------------------------
	
//	----------------------------------------------------------------------------------------------------------

	@Deprecated
	@Override
	public void open(int port, ServerListener listener) throws IOException {
		throw new NotImplementedException("not support on sfs system !");
	}
	

	@Deprecated
	@Override
	public void dispose() throws IOException {
		throw new NotImplementedException("not support on sfs system !");
	}
	
	
}
