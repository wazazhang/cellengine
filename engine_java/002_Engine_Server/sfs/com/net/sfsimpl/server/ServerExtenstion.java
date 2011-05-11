package com.net.sfsimpl.server;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.cell.exception.NotImplementedException;
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
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.ISFSEventListener;
import com.smartfoxserver.v2.core.ISFSEventParam;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.Zone;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.SFSExtension;
import com.smartfoxserver.v2.util.ClientDisconnectionReason;

abstract public class ServerExtenstion extends SFSExtension implements Server, ServerListener
{
	private RoomChannelManager channel_manager;
	
	private UserEventListener user_listener;
	
	private ConcurrentHashMap<Integer, SFSSession> sessions = new ConcurrentHashMap<Integer, SFSSession>();
	
	private Zone current_zone;
	
	private ExternalizableFactory ext_factory;
	
	@Override
	public void init() 
	{
		channel_manager = new RoomChannelManager();
		
		user_listener = new UserEventListener();
		
		current_zone = getParentZone();
		
		trace(getClass().getSimpleName() + " ready, current zone is [" + current_zone.getName() + "]");
	}
	
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
						ClientSessionListener listener = connected(session);
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
						user.setProperty(ClientSession.class, null);
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
		SFSSession session = (SFSSession)sender.getProperty(ClientSession.class);
		if (session != null)
		{
			SFSProtocol protocol = decode(params);
			if (protocol != null) {
				session.getListener().receivedMessage(session, protocol, protocol.getMessage());
			} else {
				trace("ERROR : handleClientRequest : " + params.toString());
			}
		}
	}

//	----------------------------------------------------------------------------------------------------------

	private SFSProtocol decode(ISFSObject in)
	{
		try {
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
				p.ChannelSesseionID 	= in.getInt("ChannelSesseionID");	// 4
				break;
			}
			
			// 解出包包含的二进制消息
			p.message = ext_factory.createMessage(in.getInt("message_type"));	// ext 4
			ExternalizableMessage ext = (ExternalizableMessage)p.message;
			ext.readExternal(new NetDataInputImpl(in.getByteArray("message"), ext_factory));
			
			return p;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private SFSProtocol encode(SFSSession session, MessageHeader hd) 
	{
		
		return null;
	}
	
//	----------------------------------------------------------------------------------------------------------

	@Override
	public ClientSession getSession(long sessionID) {
		User user = getApi().getUserById((int)sessionID);
		if (user != null) {
//			user.setProperty(arg0, arg1)
			return sessions.get(user);
		}
		return null;
	}
	
	@Override
	public int getSessionCount() {
		return 0;
	}
	
	@Override
	public Iterator<ClientSession> getSessions() {
		return null;
	}
	
	@Override
	public boolean hasSession(ClientSession session) {
		return false;
	}

	@Override
	public void broadcast(MessageHeader message) 
	{
		
	}

//	----------------------------------------------------------------------------------------------------------

	@Override
	public ChannelManager getChannelManager() 
	{
		return channel_manager;
	}
	
	class RoomChannelManager implements ChannelManager
	{
		AtomicInteger channel_index = new AtomicInteger();

		@Override
		public Channel createChannel(int id, ChannelListener listener) {

//			getApi().createRoom(current_zone, arg1, null)
			return null;
		}

		@Override
		public Channel getChannel(int id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Iterator<Channel> getChannels() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Channel removeChannel(int id) {
			// TODO Auto-generated method stub
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
