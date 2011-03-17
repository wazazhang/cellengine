package com.net.minaimpl.server.proxy.r2b;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.util.ConcurrentHashSet;

import com.cell.exception.NotImplementedException;
import com.net.ExternalizableFactory;
import com.net.MessageHeader;
import com.net.minaimpl.SessionAttributeKey;
import com.net.minaimpl.SystemMessages.ServerStatusRequestC2S;
import com.net.minaimpl.SystemMessages.ServerStatusResponseS2C;
import com.net.minaimpl.server.AbstractServer;
import com.net.minaimpl.server.ChannelImpl;
import com.net.minaimpl.server.ClientSessionImpl;
import com.net.minaimpl.server.ServerImpl;
import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ChannelManager;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.server.Server;
import com.net.server.ServerListener;

/**
 * 将大量客户端链接，通过该服务器，转发到另外一个服务器上，只建立很少量的链接。<br>
 * BridgeServer <-> RemoteServer
 * @author WAZA
 */
public class BridgeServerR2B extends AbstractServer
{	
	/**
	 * @param cl ClassLoader
	 * @param ef ExternalizableFactory
	 * @param ioProcessCount IO处理线程数
	 * @param sessionWriteIdleTimeSeconds	多长时间内没有发送数据，断掉链接
	 * @param sessionReadIdleTimeSeconds	多长时间内没有接受数据，断掉链接
	 */
	public BridgeServerR2B(
			ClassLoader cl,
			ExternalizableFactory ef,
			int ioProcessCount, 
			int sessionWriteIdleTimeSeconds,
			int sessionReadIdleTimeSeconds) {
		super(cl, ef, ioProcessCount, sessionWriteIdleTimeSeconds, sessionReadIdleTimeSeconds);
	}
	
	@Override
	public ChannelManager getChannelManager() {
		return null;
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------
	
	public Iterator<ClientSession> getSessions() {}
	
	public ClientSession getSession(long sessionID) {}
	
	public ClientSession getSession(Object object){}
	
	public boolean hasSession(ClientSession session) {}
	
	public int getSessionCount() {}
	
	public void broadcast(MessageHeader message){}
	

//	-----------------------------------------------------------------------------------------------------------------------------
	
	public void sessionOpened(IoSession session) throws Exception {
		log.debug("sessionOpened : " + session);
		
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		log.debug("sessionClosed : " + session);
	}
	
	public void messageReceived(final IoSession session, final Object message) throws Exception 
	{
		//System.out.println(Thread.currentThread().getName() + " messageReceived");
		if (message instanceof MessageHeader) {

		} else {
			log.error("bad message type : " + session + " : " + message);
		}
	}
	
	
//	-----------------------------------------------------------------------------------------------------------------------------
	
	private class RealSession
	{
		
	}

	private class BridgeSession implements ClientSession
	{
		@Override
		public boolean containsAttribute(Object key) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean disconnect(boolean force) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object getAttribute(Object key) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Object> getAttributeKeys() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getID() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getIdleDuration() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getReceivedBytes() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getReceivedMessageCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getSentBytes() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getSentMessageCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isConnected() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object removeAttribute(Object key) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean send(MessageHeader message) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object setAttribute(Object key, Object value) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ClientSessionListener getListener() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Server getServer() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void send(MessageHeader request, MessageHeader response) {
			// TODO Auto-generated method stub
			
		}
		

		
	}


	private class BridgeChannelManager implements ChannelManager
	{

		/* (non-Javadoc)
		 * @see com.net.server.ChannelManager#createChannel(int, com.net.server.ChannelListener)
		 */
		@Override
		public Channel createChannel(int id, ChannelListener listener) {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see com.net.server.ChannelManager#getChannel(int)
		 */
		@Override
		public Channel getChannel(int id) {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see com.net.server.ChannelManager#getChannels()
		 */
		@Override
		public Iterator<Channel> getChannels() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see com.net.server.ChannelManager#removeChannel(int)
		 */
		@Override
		public Channel removeChannel(int id) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	
	private class BridgeChannel implements Channel
	{


		@Override
		public ChannelListener getChannelListener() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see com.net.server.Channel#getID()
		 */
		@Override
		public int getID() {
			// TODO Auto-generated method stub
			return 0;
		}

		/* (non-Javadoc)
		 * @see com.net.server.Channel#getServer()
		 */
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

		/* (non-Javadoc)
		 * @see com.net.server.Channel#getSessions()
		 */
		@Override
		public Iterator<ClientSession> getSessions() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see com.net.server.Channel#hasSession(com.net.server.ClientSession)
		 */
		@Override
		public boolean hasSession(ClientSession session) {
			// TODO Auto-generated method stub
			return false;
		}

		/* (non-Javadoc)
		 * @see com.net.server.Channel#hasSessions()
		 */
		@Override
		public boolean hasSessions() {
			// TODO Auto-generated method stub
			return false;
		}

		/* (non-Javadoc)
		 * @see com.net.server.Channel#join(com.net.server.ClientSession)
		 */
		@Override
		public boolean join(ClientSession session) {
			// TODO Auto-generated method stub
			return false;
		}

		/* (non-Javadoc)
		 * @see com.net.server.Channel#leave(com.net.server.ClientSession)
		 */
		@Override
		public boolean leave(ClientSession session) {
			// TODO Auto-generated method stub
			return false;
		}

		/* (non-Javadoc)
		 * @see com.net.server.Channel#leaveAll()
		 */
		@Override
		public int leaveAll() {
			// TODO Auto-generated method stub
			return 0;
		}

		/* (non-Javadoc)
		 * @see com.net.server.Channel#send(com.net.server.ClientSession, com.net.MessageHeader, com.net.MessageHeader)
		 */
		@Override
		public int send(ClientSession sender, MessageHeader request,
				MessageHeader response) {
			// TODO Auto-generated method stub
			return 0;
		}

		/* (non-Javadoc)
		 * @see com.net.server.Channel#send(com.net.server.ClientSession, com.net.MessageHeader)
		 */
		@Override
		public int send(ClientSession sender, MessageHeader message) {
			// TODO Auto-generated method stub
			return 0;
		}

		/* (non-Javadoc)
		 * @see com.net.server.Channel#send(com.net.MessageHeader)
		 */
		@Override
		public int send(MessageHeader message) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}

}
