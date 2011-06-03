package com.net.server;

public interface ServerListener
{
	public void init(Server server);
	
	public ClientSessionListener connected(ClientSession session) ;

	public void destory();
	
}
