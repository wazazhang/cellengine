package com.net.minaimpl.server.telnet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CUtil;
import com.net.minaimpl.SessionAttributeKey;

import com.net.server.telnet.*;


public class TelnetServerImpl extends IoHandlerAdapter implements TelnetServer
{
	final ReentrantReadWriteLock	session_rw_lock	= new ReentrantReadWriteLock();

	final protected Logger 			log = LoggerFactory.getLogger(getClass().getName());
	
	final protected IoAcceptor 		Acceptor;
	
	protected long 					StartTime;

	protected TelnetServerListener	SrvListener;
//	----------------------------------------------------------------------------------------------------------------------
	
	/**
	 * @param ioProcessCount IO处理线程数
	 * @param sessionWriteIdleTimeSeconds	多长时间内没有发送数据，断掉链接
	 * @param sessionReadIdleTimeSeconds	多长时间内没有接受数据，断掉链接
	 */
	public TelnetServerImpl(
			String 	charsetName,
			int 	ioProcessCount, 
			int 	sessionWriteIdleTimeSeconds,
			int 	sessionReadIdleTimeSeconds) 
	{
		Charset charset = Charset.forName(charsetName);
		this.Acceptor = new NioSocketAcceptor(ioProcessCount);
		this.Acceptor.getSessionConfig().setReaderIdleTime(sessionWriteIdleTimeSeconds);
		this.Acceptor.getSessionConfig().setWriterIdleTime(sessionReadIdleTimeSeconds);
		this.Acceptor.setHandler(this);
		this.Acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(charset)));
	}	
	
//	----------------------------------------------------------------------------------------------------------------------
	
	synchronized public void open(int port, TelnetServerListener listener) throws IOException 
	{
		if (!Acceptor.isActive()) {
			this.SrvListener	= listener;
			this.StartTime		= System.currentTimeMillis();
			log.info("starting server at port : " + port);
			Acceptor.bind(new InetSocketAddress(port));
			log.info("server started !");
		}
	}
	
	synchronized public void close() throws IOException
	{
		if (!Acceptor.isDisposed()) {
			log.info("server closing...");
			Acceptor.unbind();
			Acceptor.dispose();
			log.info("server closed !");
		}
	}
//	-----------------------------------------------------------------------------------------------------------------------

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error(cause.getMessage() + "\n" + session);
		session.close(false);
	}
	
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.debug("sessionIdle : " + session + " : " + status);
		session.close(false);
	}

//	----------------------------------------------------------------------------------------------------------------------

	private TelnetSessionImpl getBindSession(IoSession session)
	{
		session_rw_lock.readLock().lock();
		try {
			Object obj = session.getAttribute(SessionAttributeKey.CLIENT_SESSION);
			if (obj instanceof TelnetSessionImpl) {
				return (TelnetSessionImpl) obj;
			}
		} finally {
			session_rw_lock.readLock().unlock();
		}
		return null;
	}
	
	
	public Iterator<TelnetSession> getSessions() {
		session_rw_lock.readLock().lock();
		try{
			ArrayList<TelnetSession> sessions = new ArrayList<TelnetSession>(Acceptor.getManagedSessionCount());
			for (IoSession session : Acceptor.getManagedSessions().values()){
				TelnetSessionImpl client = getBindSession(session);
				if (client!=null){
					sessions.add(client);
				}
			}
			return sessions.iterator();
		} finally {
			session_rw_lock.readLock().unlock();
		}
	}
	
	public TelnetSession getSession(long sessionID) {
		session_rw_lock.readLock().lock();
		try{
			IoSession session = Acceptor.getManagedSessions().get(sessionID);
			TelnetSessionImpl client = getBindSession(session);
			if (client!=null){
				return client;
			}
			return null;
		} finally {
			session_rw_lock.readLock().unlock();
		}
	}
	
	public boolean hasSession(TelnetSession session) {
		session_rw_lock.readLock().lock();
		try{
			return Acceptor.getManagedSessions().containsKey(session.getID());
		} finally {
			session_rw_lock.readLock().unlock();
		}
	}
	
	public int getSessionCount() {
		session_rw_lock.readLock().lock();
		try{
			return Acceptor.getManagedSessionCount();
		} finally {
			session_rw_lock.readLock().unlock();
		}
	}
	
	public void broadcast(String message){
		Acceptor.broadcast(message);
	}
	
//	-----------------------------------------------------------------------------------------------------------------------

	public void sessionOpened(IoSession session) throws Exception {
		log.debug("sessionOpened : " + session);
		TelnetSessionImpl client = new TelnetSessionImpl(session, this);
		session_rw_lock.writeLock().lock();
		try{
			session.setAttribute(SessionAttributeKey.CLIENT_SESSION, client);
		} finally {
			session_rw_lock.writeLock().unlock();
		}
		client.Listener = SrvListener.connected(client);
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		log.debug("sessionClosed : " + session);
		TelnetSessionImpl client = getBindSession(session);
		session.removeAttribute(SessionAttributeKey.CLIENT_SESSION);
		if (client != null) {
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
		TelnetSessionImpl client = getBindSession(session);
		if (client == null) {
			log.error("client is expire : " + session);
			return;
		}
		if (message instanceof String) {
			if (client.Listener != null) {
				client.Listener.receivedMessage(client, message.toString());
			}
		} else {
			log.error("bad message type : " + session + " : " + message);
		}
	}
	
}
