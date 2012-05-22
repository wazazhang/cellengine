package com.net.minaimpl.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.net.io.ExternalizableFactory;
import com.cell.net.io.MessageHeader;
import com.net.Protocol;
import com.net.client.ServerSession;
import com.net.client.ServerSessionListener;
import com.net.minaimpl.KeepAlive;
import com.net.minaimpl.NetPackageCodec;
import com.net.minaimpl.ProtocolImpl;
import com.net.minaimpl.ProtocolPool;
import com.net.minaimpl.SystemMessages;


public class ServerSessionImpl extends IoHandlerAdapter implements ServerSession 
{
	final Logger log;

	final AtomicReference<IoSession> session_ref = new AtomicReference<IoSession>(null);

	ConcurrentHashMap<Integer, ClientChannelImpl> channels = new ConcurrentHashMap<Integer, ClientChannelImpl>();

	IoConnector 			Connector;
	NetPackageCodec 		Codec;
	KeepAlive				keep_alive;
	ServerSessionListener 	Listener;
	
	final CleanTask			clean_task;
	
	
	
	public ServerSessionImpl() {
		this(Thread.currentThread().getContextClassLoader(), null);
	}
	
	public ServerSessionImpl(boolean smooth_close) {
		this(Thread.currentThread().getContextClassLoader(), null, smooth_close, 
				Integer.MAX_VALUE, 
				Integer.MAX_VALUE,
				0);
	}
	
	public ServerSessionImpl(ClassLoader cl, ExternalizableFactory ef) {
		this(Thread.currentThread().getContextClassLoader(), ef, true,
				Integer.MAX_VALUE, 
				Integer.MAX_VALUE,
				0);
	}

	/**
	 * @param cl
	 * @param ef
	 * @param smooth_close
	 * @param write_idle_time_sec		多长时间内没有发送数据，断掉链接(秒)
	 * @param read_idle_time_sec		多长时间内没有接受数据，断掉链接(秒)
	 * @param keepalive_interval_sec	心跳间隔，0表示不使用心跳机制
	 */
	public ServerSessionImpl(ClassLoader cl,
			ExternalizableFactory ef, 
			boolean smooth_close, 
			int write_idle_time_sec,
			int read_idle_time_sec, 
			int keepalive_interval_sec)
	{
		log			= LoggerFactory.getLogger(getClass().getName());
		Codec		= new NetPackageCodec(cl, ef);
		Connector 	= new NioSocketConnector();
		Connector.getSessionConfig().setReaderIdleTime(read_idle_time_sec);
		Connector.getSessionConfig().setWriterIdleTime(write_idle_time_sec);
		if (keepalive_interval_sec > 0) {
			keep_alive = new KeepAlive(
					Codec,
					keepalive_interval_sec,
					Math.min(write_idle_time_sec, read_idle_time_sec));
			Connector.getFilterChain().addLast("keep-alive", keep_alive); //心跳  
		}
		Connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(Codec));
		Connector.setHandler(this);

		this.clean_task = new CleanTask();
		if (smooth_close) {
			Runtime.getRuntime().addShutdownHook(clean_task);
		}
	}
	
	@Override
	public void dispose() {
		Runtime.getRuntime().removeShutdownHook(clean_task);
		try {
			if (isConnected()) {
				disconnect(true);
			}
			Connector.dispose();
		} catch (Exception err) {
			log.error(err.getMessage(), err);
		}
	}

	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		dispose();
	}
	
	public boolean connect(String host, int port, ServerSessionListener listener) throws IOException {
		return this.connect(host, port, 10000, listener);
	}
	
	public boolean connect(String host, int port, long timeout, ServerSessionListener listener) throws IOException 
	{
		if (!isConnected()) {
			synchronized (session_ref) {
				SocketAddress address = new InetSocketAddress(host, port);
				Listener = listener;
				Connector.setConnectTimeoutMillis(timeout);
				ConnectFuture future1 = Connector.connect(address);
				future1.awaitUninterruptibly(timeout);
				if (!future1.isConnected()) {
					return false;
				}
				IoSession io_session = future1.getSession();
				if (io_session != null && io_session.isConnected()) {
					this.session_ref.set(io_session);
					this.clean_task.set(io_session);
					return true;
				} else {
					log.error("not connect : " + address.toString());
				}
			}
		} else {
			log.error("Already connected !");
		}
		return false;
	}
	
	public boolean disconnect(boolean force) {
		IoSession io_session = null;
		synchronized (session_ref) {
			io_session = session_ref.getAndSet(null);
			clean_task.set(null);
		}
		if (io_session != null) {
			io_session.close(force);
		} else {
			log.error("session is null !");
		}
		return false;
	}
	
	public boolean isConnected() {
		synchronized (session_ref) {
			IoSession io_session = session_ref.get();
			if (io_session != null) {
				if (io_session.isConnected()) {
					return true;
				} else {
					session_ref.set(null);
					return false;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean send(MessageHeader message) {
		return sendRequest(0, message);
	}
	
	@Override
	public boolean sendRequest(int pnum, MessageHeader message) {
		if (isConnected()) {
			IoSession io_session = session_ref.get();
			if (io_session != null) {
				ProtocolImpl p 	= ProtocolPool.getInstance().createProtocol();
				p.Protocol 		= ProtocolImpl.PROTOCOL_SESSION_MESSAGE;
				p.message		= message;
				p.PacketNumber	= pnum;
//				io_session.resumeWrite();
				WriteFuture future = io_session.write(p);
				// Wait until the message is completely written out to the O/S
				// buffer.
//				future.awaitUninterruptibly();
//				if (future.isWritten()) {
//					// The message has been written successfully.
//					return true;
//				} else {
//					// The messsage couldn't be written out completely for some
//					// reason.
//					// (e.g. Connection is closed)
//					return false;
//				}
				return false;
			}
			return true;
		} else {
			log.error("server not connected !");
		}
		return false;
	}
	
	protected void sendChannel(MessageHeader message, ClientChannelImpl channel) {
		if (isConnected()) {
			IoSession io_session = session_ref.get();
			if (io_session != null) {
				ProtocolImpl p = ProtocolPool.getInstance().createProtocol();
				p.Protocol			= ProtocolImpl.PROTOCOL_CHANNEL_MESSAGE;
				p.ChannelID			= channel.getID();
//				p.ChannelSessionID	= getID();
				p.message			= message;
				io_session.write(p);
			}
		}
	}

//	-----------------------------------------------------------------------------------------------------------------------
	public long getSentMessageCount() {
		return Codec.getSentMessageCount();
	}
	
	public long getReceivedMessageCount () {
		return Codec.getReceivedMessageCount();
	}
	
	public long getSentBytes(){
		return Codec.getTotalSentBytes();
	}
	
	public long getReceivedBytes(){
		return Codec.getTotalReceivedBytes();
	}
	
	public long getHeartBeatReceived() {
		if (keep_alive != null) {
			return keep_alive.getReceivedMessageCount();
		}
		return 0;
	}
	
	public long getHeartBeatSent() {
		if (keep_alive != null) {
			return keep_alive.getSentMessageCount();
		}
		return 0;
	}
//	-----------------------------------------------------------------------------------------------------------------------

//	public void sessionCreated(IoSession session) throws Exception {}
//	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {}
//	public void messageSent(IoSession session, Object message) throws Exception {}
	
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		//cause.printStackTrace();
		Listener.onError(this, cause);
	}
	
	public void sessionOpened(IoSession session) throws Exception {
		Listener.connected(this);
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		Listener.disconnected(this, true, "sessionClosed");
		clean_task.set(null);
	}
	
	public void messageReceived(final IoSession iosession, final Object message) throws Exception 
	{
		if (message instanceof Protocol) 
		{
			Protocol header = (Protocol)message;
			try
			{
				switch (header.getProtocol())
				{
				case Protocol.PROTOCOL_SESSION_MESSAGE:{
					Listener.receivedMessage(this, header, header.getMessage());
					break;
				}
				case Protocol.PROTOCOL_CHANNEL_MESSAGE: {
					ClientChannelImpl channel = null;
					synchronized (channels) {
						channel = channels.get(header.getChannelID());
						if (channel == null) {
							channel = new ClientChannelImpl(this, header.getChannelID());
							channels.putIfAbsent(header.getChannelID(), channel);
						}
					}
					Listener.receivedChannelMessage(channel, header, header.getMessage());
					break;
				}
				case Protocol.PROTOCOL_CHANNEL_JOIN_S2C:{
					synchronized (channels) {
						channels.put(header.getChannelID(), new ClientChannelImpl(this, header.getChannelID()));
					}
					break;
				}
				case Protocol.PROTOCOL_CHANNEL_LEAVE_S2C:{
					synchronized (channels) {
						ClientChannelImpl channel = channels.get(header.getChannelID());
						if (channel != null) {
							Listener.leftChannel(channel);
							channels.remove(header.getChannelID());
						}
					}
					break;
				}
				case ProtocolImpl.PROTOCOL_SYSTEM_NOTIFY:{
					Listener.receivedMessage(this, header, header.getMessage());
					break;
				}
				default:
					log.error("messageReceived : " +
							"unknow protocol(" + Integer.toString(header.getProtocol(), 16) + ")" + " : " +
							"data : " + header);
				}
			}
			catch (Exception e) 
			{
				log.error("messageReceived : message process error : " + header);
				e.printStackTrace();
			}
		}
		else
		{
			log.error("messageReceived : bad message type : " + message);
		}
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception 
	{
		if (message instanceof Protocol) {
			Protocol header = (Protocol) message;
			try {
				Listener.sentMessage(this, header, header.getMessage());
			} catch (Exception e) {
				log.error("messageSent : bad protocol : " + header);
				e.printStackTrace();
			}
		} else {
			log.error("messageSent : bad message type : " + message);
		}
	}
	
	public IoSession getIoSession() {
		return session_ref.get();
	}

	@Override
	public boolean containsAttribute(Object key) {
		return session_ref.get().containsAttribute(key);
	}

	@Override
	public Object getAttribute(Object key) {
		return session_ref.get().getAttribute(key);
	}
	@Override
	public Set<Object> getAttributeKeys() {
		return session_ref.get().getAttributeKeys();
	}
	
	@Override
	public long getID() {
		return session_ref.get().getId();
	}
	
	@Override
	public Object removeAttribute(Object key) {
		return session_ref.get().removeAttribute(key);
	}
	
	@Override
	public Object setAttribute(Object key, Object value) {
		return session_ref.get().setAttribute(key, value);
	}
	
	public String toString() {
		try {
			synchronized (session_ref) {
				return "" + session_ref.get();
			}
		} catch (Exception err) {
			return err.getMessage();
		}
	}
	
	@Override
	public String getLocalAddress() 
	{
		return session_ref.get().getLocalAddress().toString();
	}

	@Override
	public String getRemoteAddress() 
	{
		return session_ref.get().getRemoteAddress().toString();
	}	
	
	
	private static class CleanTask extends Thread
	{
		private IoSession session;
		
		private synchronized void set(IoSession session) {
			this.session = session;
		}
		
		public void run() {
			if (session != null) {
				String info; 
				try {
					info = session.toString();
				} catch (Throwable err){
					info = err.getMessage();
				}
				System.out.println("Clear session : " + info);
				try {
					session.close(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
