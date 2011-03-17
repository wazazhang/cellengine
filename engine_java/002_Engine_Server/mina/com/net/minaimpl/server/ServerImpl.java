package com.net.minaimpl.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.mina.core.session.IoSession;

import com.cell.CUtil;
import com.cell.util.concurrent.ThreadPool;
import com.net.ExternalizableFactory;
import com.net.MessageHeader;
import com.net.Protocol;
import com.net.minaimpl.ProtocolImpl;
import com.net.minaimpl.ProtocolPool;
import com.net.minaimpl.SessionAttributeKey;
import com.net.server.Channel;
import com.net.server.ChannelManager;
import com.net.server.ClientSession;


public class ServerImpl extends AbstractServer
{
	final private ChannelManager		channel_manager;

	protected Map<Class<?>, AtomicLong>	message_received_count = 
		new HashMap<Class<?>, AtomicLong>();
	protected Map<Class<?>, AtomicLong>	message_sent_count = 
		new HashMap<Class<?>, AtomicLong>();
	
//	----------------------------------------------------------------------------------------------------------------------
	
	public ServerImpl() 
	{
		this(Thread.currentThread().getContextClassLoader(), null,
				Runtime.getRuntime().availableProcessors() + 1, 
				Integer.MAX_VALUE, 
				Integer.MAX_VALUE, 
				0);
	}

	/**
	 * @param ioProcessCount
	 * @param sessionWriteIdleTimeSeconds	多长时间内没有发送数据，断掉链接(秒)
	 * @param sessionReadIdleTimeSeconds	多长时间内没有接受数据，断掉链接(秒)
	 * @param keepalive_interval_sec		心跳间隔，0表示不使用心跳机制
	 */
	public ServerImpl(
			int ioProcessCount, 
			int sessionWriteIdleTimeSeconds,
			int sessionReadIdleTimeSeconds,
			int keepalive_interval_sec) 
	{
		this(Thread.currentThread().getContextClassLoader(), null,
				ioProcessCount, 
				sessionWriteIdleTimeSeconds, 
				sessionReadIdleTimeSeconds,
				keepalive_interval_sec);
	}
	
	/**
	 * @param class_loader
	 * @param externalizable_factory
	 * @param ioProcessCount
	 * @param sessionWriteIdleTimeSeconds	多长时间内没有发送数据，断掉链接(秒)
	 * @param sessionReadIdleTimeSeconds	多长时间内没有接受数据，断掉链接(秒)
	 * @param keepalive_interval_sec		心跳间隔，0表示不使用心跳机制
	 */
	public ServerImpl(
			ClassLoader 			class_loader,
			ExternalizableFactory 	externalizable_factory,
			int 					ioProcessCount, 
			int 					sessionWriteIdleTimeSeconds,
			int 					sessionReadIdleTimeSeconds,
			int 					keepalive_interval_sec) 
	{
		this(class_loader, externalizable_factory, null, null,
				ioProcessCount, 
				sessionWriteIdleTimeSeconds, 
				sessionReadIdleTimeSeconds, 
				keepalive_interval_sec, true);
	}
	
	/**
	 * @param class_loader
	 * @param externalizable_factory
	 * @param ioProcessCount
	 * @param sessionWriteIdleTimeSeconds	多长时间内没有发送数据，断掉链接(秒)
	 * @param sessionReadIdleTimeSeconds	多长时间内没有接受数据，断掉链接(秒)
	 * @param keepalive_interval_sec		心跳间隔，0表示不使用心跳机制
	 * @param close_on_error
	 */
	public ServerImpl(
			ClassLoader 			class_loader,
			ExternalizableFactory 	externalizable_factory,
			int 					ioProcessCount, 
			int 					sessionWriteIdleTimeSeconds,
			int 					sessionReadIdleTimeSeconds,
			int 					keepalive_interval_sec,
			boolean					close_on_error) 
	{
		this(class_loader, externalizable_factory, null, null,
				ioProcessCount, 
				sessionWriteIdleTimeSeconds, 
				sessionReadIdleTimeSeconds, 
				keepalive_interval_sec, 
				close_on_error);
	}
	
	/**
	 * @param class_loader
	 * @param externalizable_factory
	 * @param acceptor_pool
	 * @param io_processor_pool
	 * @param io_processor_count
	 * @param sessionWriteIdleTimeSeconds	多长时间内没有发送数据，断掉链接(秒)
	 * @param sessionReadIdleTimeSeconds	多长时间内没有接受数据，断掉链接(秒)
	 * @param keepalive_interval_sec		心跳间隔，0表示不使用心跳机制
	 * @param close_on_error
	 */
	public ServerImpl(
			ClassLoader 			class_loader,
			ExternalizableFactory 	externalizable_factory,
			Executor 				acceptor_pool,
			Executor 				io_processor_pool,
			int						io_processor_count,
			int 					sessionWriteIdleTimeSeconds,
			int 					sessionReadIdleTimeSeconds, 
			int 					keepalive_interval_sec,
			boolean					close_on_error) 
	{
		super(class_loader, externalizable_factory, 
				acceptor_pool, 
				io_processor_pool,
				io_processor_count, 
				sessionWriteIdleTimeSeconds, 
				sessionReadIdleTimeSeconds, 
				keepalive_interval_sec,
				close_on_error);
		this.channel_manager = new ChannelManagerImpl(this);
	}	
//	----------------------------------------------------------------------------------------------------------------------

//	-----------------------------------------------------------------------------------------------------------------------

	public ChannelManager getChannelManager() {
		return channel_manager;
	}
	
	private ClientSessionImpl getBindSession(IoSession session)
	{
		return (ClientSessionImpl)session.getAttribute(SessionAttributeKey.CLIENT_SESSION);
	}
	
//	class SessionIterator implements Iterator<ClientSession>
//	{
//		public SessionIterator() {
//			Acceptor.getManagedSessions().containsKey(SessionAttributeKey.CLIENT_SESSION);
//		}
//		
//		@Override
//		public boolean hasNext() {
//			// TODO Auto-generated method stub
//			return false;
//		}
//		@Override
//		public ClientSession next() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		@Override
//		public void remove() {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}
	
	
	public Iterator<ClientSession> getSessions() {
		ArrayList<ClientSession> sessions = new ArrayList<ClientSession>(Acceptor.getManagedSessionCount());
		for (IoSession session : Acceptor.getManagedSessions().values()) {
			ClientSessionImpl client = getBindSession(session);
			if (client != null) {
				sessions.add(client);
			}
		}
		return sessions.iterator();
	}
	
	public ClientSession getSession(long sessionID) {
		IoSession session = Acceptor.getManagedSessions().get(sessionID);
		ClientSessionImpl client = getBindSession(session);
		return client;
	}
	
	public ClientSession getSession(Object object){
		for (IoSession session : Acceptor.getManagedSessions().values()){
			ClientSessionImpl client = getBindSession(session);
			if (object.equals(client)) {
				return client;
			}
		}
		return null;
	}
	
	public boolean hasSession(ClientSession session) {
		return Acceptor.getManagedSessions().containsKey(session.getID());
	}
	
	public int getSessionCount() {
		return Acceptor.getManagedSessionCount();
	}
	
	public void broadcast(MessageHeader message){
		ProtocolImpl p = ProtocolPool.getInstance().createProtocol();
		p.Protocol	= Protocol.PROTOCOL_SESSION_MESSAGE;
		p.message	= message;
		Acceptor.broadcast(p);
	}
	
//	-----------------------------------------------------------------------------------------------------------------------

	public void sessionOpened(IoSession session) throws Exception {
		log.debug("sessionOpened : " + session);
		ClientSessionImpl client = new ClientSessionImpl(session, this);
		session.setAttribute(SessionAttributeKey.CLIENT_SESSION, client);
		client.setListener(SrvListener.connected(client));
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		log.debug("sessionClosed : " + session);
		ClientSessionImpl client = (ClientSessionImpl)session.removeAttribute(SessionAttributeKey.CLIENT_SESSION);
		if (client != null) {
			try {
				Iterator<Channel> channels = channel_manager.getChannels();
				while (channels.hasNext()) {
					channels.next().leave(client);
				}
			} catch (Throwable e) {
				log.error(e.getMessage(), e);
			}
			try {
				if (client.Listener != null) {
					client.Listener.disconnected(client);
				}
			} catch (Throwable e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	public void messageReceived(final IoSession session, final Object message) throws Exception 
	{
		//System.out.println(Thread.currentThread().getName() + " messageReceived");
		if (message instanceof Protocol)
		{
			Protocol header = (Protocol)message;
			
			recordMessageCount(message_received_count, header);
			
			ClientSessionImpl client = getBindSession(session);
			if (client != null && client.Listener != null)
			{
				switch (header.getProtocol())
				{
				case Protocol.PROTOCOL_CHANNEL_MESSAGE:
					ChannelImpl channel = (ChannelImpl)channel_manager.getChannel(header.getChannelID());
					if (channel != null) {
						channel.getChannelListener().receivedMessage(channel, client, header.getMessage());
					}
					break;
					
				case Protocol.PROTOCOL_SESSION_MESSAGE:
					client.Listener.receivedMessage(client, header, header.getMessage());
					break;
					
//				case Protocol.PROTOCOL_CHANNEL_JOIN_S2C:
//					break;
//				case Protocol.PROTOCOL_CHANNEL_LEAVE_S2C:
//					break;
					
				default:
					log.error("unknow message : " + session + " : " + message);
				}
			}
		}
		else
		{
			log.error("bad message type : " + session + " : " + message);
		}
		
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception 
	{
		if (message instanceof Protocol) {
			Protocol header = (Protocol) message;
			recordMessageCount(message_sent_count, header);
			ClientSessionImpl client = getBindSession(session);
			if (client != null && client.Listener != null) {
				client.Listener.sentMessage(client, header, header.getMessage());
			}
		} else {
			log.error("bad message type : " + session + " : " + message);
		}
	}

    protected long recordMessageCount(Map<Class<?>, AtomicLong> map, Protocol msg) {
		if (map != null && msg.getMessage() != null) {
			synchronized (map) {
				AtomicLong idx = map.get(msg.getMessage().getClass());
				if (idx == null) {
					idx = new AtomicLong(0);
					map.put(msg.getMessage().getClass(), idx);
				}
				return idx.incrementAndGet();
			}
		}
		return 0;
    }
	

	public String getMessageIOStats()
	{
		StringBuilder lines = new StringBuilder();
		lines.append("[I/O message count]\n");
		if (message_received_count!=null) {
			lines.append("[Received]\n");
			synchronized (message_received_count) {
				CUtil.toStatusLines(80, message_received_count, lines);
			}
			CUtil.toStatusSeparator(lines);
		}
		if (message_sent_count!=null) {
			lines.append("[Sent]\n");
			synchronized (message_sent_count) {
				CUtil.toStatusLines(80, message_sent_count, lines);
			}
			CUtil.toStatusSeparator(lines);
		}
		return lines.toString();
	}
	
}
