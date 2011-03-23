package com.net.server;

import java.util.Iterator;

public interface ChannelManager 
{
	public Channel createChannel(String id, ChannelListener listener);
	
	public Channel removeChannel(String id);
	
	public Channel getChannel(String id);
	
	public Iterator<Channel> getChannels();
}
