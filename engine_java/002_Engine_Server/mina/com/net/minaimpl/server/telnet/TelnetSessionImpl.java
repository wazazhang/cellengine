package com.net.minaimpl.server.telnet;

import java.util.Set;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.net.server.telnet.TelnetSession;
import com.net.server.telnet.TelnetSessionListener;

public class TelnetSessionImpl implements TelnetSession 
{
	final protected IoSession 			Session;
	final protected TelnetServerImpl	Server;
	TelnetSessionListener				Listener;
	
	TelnetSessionImpl(IoSession session, TelnetServerImpl server){
		Session = session;
		Server = server;
	}
	
	public TelnetServerImpl getServer() {
		return Server;
	}
	
	public IoSession getIoSession() {
		return Session;
	}
	
	public String toString() {
		return Session.toString();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof TelnetSessionImpl) {
			return Session.equals(((TelnetSessionImpl) obj).Session);
		}
		return false;
	}
	
	public boolean disconnect(boolean force) {
		Session.close(force);
		return true;
	}
	
	public long getID() {
		return Session.getId();
	}

	public boolean isConnected() {
		return Session.isConnected();
	}

	@Override
	public TelnetSessionListener getListener() {
		return Listener;
	}
	
	@Override
	public void send(String message) {
		Session.write(message);
	}
	
	public void sendControl(byte[] data) {
		Session.write(IoBuffer.wrap(data));
	}

//	------------------------------------------------------------------------------
	

	@Override
	public long getReceivedBytes() {
		return Session.getReadBytes();
	}
	@Override
	public long getReceivedMessageCount() {
		return Session.getReadMessages();
	}
	@Override
	public long getSentBytes() {
		return Session.getWrittenBytes();
	}
	@Override
	public long getSentMessageCount() {
		return Session.getWrittenMessages();
	}
	
	
	
//	------------------------------------------------------------------------------
	
	public Object getAttribute(Object key) {
		return Session.getAttribute(key);
	}

	public Object setAttribute(Object key, Object value){
		return Session.setAttribute(key, value);
	}

	public Object removeAttribute(Object key){
		return Session.removeAttribute(key);
	}

	public boolean containsAttribute(Object key){
		return Session.containsAttribute(key);
	}

	public Set<Object> getAttributeKeys(){
		return Session.getAttributeKeys();
	}
	
	
	@Override
	public String getLocalAddress() 
	{
		return Session.getLocalAddress().toString();
	}

	@Override
	public String getRemoteAddress() 
	{
		return Session.getRemoteAddress().toString();
	}	
	
}
