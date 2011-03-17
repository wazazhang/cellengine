package com.net;


import java.util.Set;

public interface AbstractSession 
{
	public long 			getID();
	
	public boolean			isConnected();
	
	public boolean			disconnect(boolean force);
	
	public long 			getSentMessageCount() ;
	public long 			getReceivedMessageCount () ;
	public long 			getSentBytes();
	public long 			getReceivedBytes();

	public Object 			getAttribute(Object key);
	public Object 			setAttribute(Object key, Object value);
	public Object 			removeAttribute(Object key);
	public boolean 			containsAttribute(Object key);
	public Set<Object>		getAttributeKeys();
	
	/**
	 * 获取Session的本端地址
	 */
	public String			getLocalAddress();
	
	/**
	 * 获取Session的对端地址
	 */
	public String			getRemoteAddress();

};



