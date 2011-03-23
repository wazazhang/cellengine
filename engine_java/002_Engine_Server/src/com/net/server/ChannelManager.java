package com.net.server;

import java.util.Iterator;

public interface ChannelManager 
{
	public Channel createChannel(int id, ChannelListener listener);
	
	public Channel removeChannel(int id);
	
	public Channel getChannel(int id);
	
	public Iterator<Channel> getChannels();
}
