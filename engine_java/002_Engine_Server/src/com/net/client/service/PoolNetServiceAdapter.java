package com.net.client.service;

import com.net.client.ServerSession;
import com.net.client.ServerSessionListener;


public abstract interface PoolNetServiceAdapter
{
	abstract public ServerSession createServerSession(PoolNetService service, ServerSessionListener listener);
	
	abstract public ServerSession reconnect(PoolNetService service, ServerSession session, ServerSessionListener listener);
}
