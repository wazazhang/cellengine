package com.net.sfsimpl.server;

import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.net.MessageHeader;
import com.net.Protocol;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.server.Server;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.util.ClientDisconnectionReason;

public class SFSSession implements ClientSession 
{
	private static final Logger log = LoggerFactory.getLogger(SFSSession.class.getName());
	
	final private HashMap<Object, Object>	attributes = new HashMap<Object, Object>();
	
	final protected User	 				user;
	final protected SFSServerAdapter		server;
	private ClientSessionListener			listener;
	
	protected SFSSession(User session, SFSServerAdapter server)
	{
		this.user = session;
		this.server = server;
	}

	@Override
	public String toString() {
		return user.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SFSSession) {
			return user.equals(((SFSSession) obj).user);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return user.hashCode();
	}

//	------------------------------------------------------------------------------------
	
	public boolean disconnect(boolean force) 
	{
		server.disconnect(user);
		return true;
	}

	
	public boolean send(MessageHeader message)
	{
		server.send(user, message, 
				Protocol.PROTOCOL_SESSION_MESSAGE, 0, 0);
		return true;
	}

	public boolean sendResponse(Protocol request, MessageHeader response)
	{
		server.send(user, response, 
				Protocol.PROTOCOL_SESSION_MESSAGE, 0, request.getPacketNumber());
		return true;
	}
	
//	------------------------------------------------------------------------------------

	@Override
	public Server getServer() {
		return server;
	}
	
	public String getName() {
		return user.getName();
	}

	public long getID() {
		return user.getId();
	}

	public String getAddress() {
		return user.getIpAddress();
	}

	public boolean isConnected() {
		return user.isConnected();
	}

	@Override
	public ClientSessionListener getListener() {
		return listener;
	}

	void setListener(ClientSessionListener listener) {
		this.listener = listener;
	}

//	------------------------------------------------------------------------------------
	
	public Object getAttribute(Object key) {
		synchronized (attributes) {
			return attributes.get(key);
		}
	}

	public Object setAttribute(Object key, Object value) {
		synchronized (attributes) {
			return attributes.put(key, value);
		}
	}

	public Object removeAttribute(Object key) {
		synchronized (attributes) {
			return attributes.remove(key);
		}
	}

	public boolean containsAttribute(Object key) {
		synchronized (attributes) {
			return attributes.containsKey(key);
		}
	}

	public Set<Object> getAttributeKeys() {
		synchronized (attributes) {
			return attributes.keySet();
		}
	}

//	------------------------------------------------------------------------------------
	
//	synchronized public void stopHeartBeat() {
//		if (heartbeat_future!=null) {
//			heartbeat_future.cancel(false);
//		}
//	}
	@Override
	public long getReceivedBytes() {
		return 0;
	}
	@Override
	public long getReceivedMessageCount() {
		return 0;
	}
	@Override
	public long getSentBytes() {
		return 0;
	}
	@Override
	public long getSentMessageCount() {
		return 0;
	}

	@Override
	public String getLocalAddress() {
		return server.toString();
	}

	@Override
	public String getRemoteAddress() {
		return user.getIpAddress();
	}
	
	
//	synchronized public void startHeartBeat(ThreadPool pool, final long heartbeat_timeout)
//	{
//		if (heartbeat_future == null || 
//			heartbeat_future.isDone() || 
//			heartbeat_future.isCancelled())
//		{
//			// 在heartbeat_timeout ms秒后检查是否在这段时间内没有消息发送过来
//			heartbeat_future = pool.scheduleAtFixedRate(new Runnable(){
//				public void run() {
//					ClientSessionImpl iosession = ClientSessionImpl.this;
//					if (iosession.getIdleDuration() >= heartbeat_timeout) {
//						log.error("long time no message (" + iosession.getIdleDuration() + "), kick " + iosession);
//						iosession.disconnect(false);
//					}
//				}
//			}, 
//			heartbeat_timeout + 1, 
//			heartbeat_timeout);
//		}
//	}
	
	
}
