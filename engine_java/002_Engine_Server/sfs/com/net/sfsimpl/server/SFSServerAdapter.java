package com.net.sfsimpl.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.buffer.IoBuffer;

import com.cell.net.io.ExternalizableFactory;
import com.cell.net.io.MessageHeader;
import com.cell.net.io.MutualMessage;
import com.net.Protocol;
import com.net.minaimpl.NetDataInputImpl;
import com.net.minaimpl.NetDataOutputImpl;
import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ChannelManager;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.server.Server;
import com.net.server.ServerListener;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.ISFSEventListener;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.Zone;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.SFSExtension;
import com.smartfoxserver.v2.util.ClientDisconnectionReason;

public abstract class SFSServerAdapter extends SFSExtension implements Server, ISFSEventListener
{
	public static int PACKAGE_DEFAULT_SIZE = 4096;

//	--------------------------------------------------------------------------------------
	
	private RoomChannelManager 	channel_manager;
	
	private ConcurrentHashMap<Integer, SFSSession> 
									sessions = new ConcurrentHashMap<Integer, SFSSession>();
	
	private Zone 					current_zone;
	
	private ServerListener			server_listener;
	
//	--------------------------------------------------------------------------------------

	@Override
	public void open(int port, ServerListener listener) throws IOException 
	{
		this.channel_manager 	= new RoomChannelManager();
		this.current_zone 		= getParentZone();
		
		this.addEventListener(SFSEventType.USER_DISCONNECT, 			this);
		this.addEventListener(SFSEventType.USER_JOIN_ROOM, 				this);
		this.addEventListener(SFSEventType.USER_JOIN_ZONE, 				this);
		this.addEventListener(SFSEventType.USER_LEAVE_ROOM, 			this);
		this.addEventListener(SFSEventType.USER_LOGIN, 					this);
		this.addEventListener(SFSEventType.USER_LOGOUT, 				this);
		this.addEventListener(SFSEventType.USER_RECONNECTION_SUCCESS, 	this);
		this.addEventListener(SFSEventType.USER_RECONNECTION_TRY, 		this);
		this.addEventListener(SFSEventType.USER_VARIABLES_UPDATE, 		this);

		this.server_listener = listener;
		this.server_listener.init(this);
		
		trace(getClass().getSimpleName() + " ready, " +
				"current zone is [" + current_zone.getName() + "]");
	}
	


	public void handleClientRequest(String requestId, User sender, ISFSObject params) 
	{
//		System.out.println("received " + params);
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
				e.printStackTrace();
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

	@Override
	public void handleServerEvent(ISFSEvent event) throws Exception
	{
		trace("handleServerEvent:" + event.toString());
		switch (event.getType()) {
		case USER_LOGIN:
		case USER_JOIN_ZONE:
		{
			User user = (User)event.getParameter(SFSEventParam.USER);
			if (user != null) {
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
			}
			break;
		}
		case USER_LOGOUT:
		{				
			User user = (User)event.getParameter(SFSEventParam.USER);
			if (user != null) {
				user.disconnect(ClientDisconnectionReason.IDLE);
			}
			break;
		}
		case USER_DISCONNECT: 
		{
			User user = (User)event.getParameter(SFSEventParam.USER);
			if (user != null) {
				synchronized (sessions) {
					SFSSession session = (SFSSession)user.getProperty(ClientSession.class);
					if (session != null) {
						session = sessions.remove(user.getId());
						user.removeProperty(ClientSession.class);
						session.getListener().disconnected(session);
					}
				}
			}
			break;
		}
		default:
			break;
		}
		
	}


//	----------------------------------------------------------------------------------------------------------

	abstract public ExternalizableFactory getMessageFactory();

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
			byte[] msg_data = in.getByteArray("message");
			ExternalizableFactory codec = getMessageFactory();
			p.Message = codec.createMessage(message_type);	// ext 4
			MutualMessage ext = (MutualMessage)p.Message;
			NetDataInputImpl obj_in = new NetDataInputImpl(
					IoBuffer.wrap(msg_data), codec);
			codec.getMutualCodec().readMutual(ext, obj_in);
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
			if (p.Message instanceof MutualMessage) {
				ExternalizableFactory codec = getMessageFactory();
				out.putInt("message_type", codec.getType(p.Message));	// ext 4
				NetDataOutputImpl net_out = new NetDataOutputImpl(
						IoBuffer.allocate(PACKAGE_DEFAULT_SIZE), codec);
				codec.getMutualCodec().writeMutual(
						((MutualMessage)p.Message), net_out);
				byte[] data = net_out.getWritedData();
				out.putByteArray("message", data);
			} else {
				out.putInt("message_type", 0);	// ext 4
			}
		}

		p.DynamicSendTime = System.currentTimeMillis();
		return out;
	}
	
	void disconnect(User user) {
		this.getApi().disconnectUser(user, 
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
//		System.out.println("send " + message);
		try {
			send("msg", encode(p), user);
		} catch (Throwable e) {
			e.printStackTrace();
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
			send("msg", encode(p), users);
		} catch (Throwable e) {
			e.printStackTrace();
			trace(e);
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
	public void dispose() throws IOException {
		server_listener.destory();
	}
	
	@Override
	public void destroy() {
		try {
			dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.destroy();
	}
}
