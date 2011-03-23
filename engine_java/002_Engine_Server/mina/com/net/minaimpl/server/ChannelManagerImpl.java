package com.net.minaimpl.server;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ChannelManager;
import com.net.server.Server;

public class ChannelManagerImpl implements ChannelManager
{
	final private ConcurrentHashMap<Integer, Channel> Channels = new ConcurrentHashMap<Integer, Channel>(10);
	
	final AbstractServer server;
	
	ChannelManagerImpl(AbstractServer server){
		this.server = server;
	}
	
	public Channel createChannel(int id, ChannelListener listener) {
		ChannelImpl channel = new ChannelImpl(listener, id, server, this);
		Channel ret = Channels.putIfAbsent(id, channel);
		if (ret == null) {
			return channel;
		}
		return null;
	}
	
	public Channel getChannel(int id) {
		return Channels.get(id);
	}
	
	public Iterator<Channel> getChannels() {
		return Channels.values().iterator();
	}
	
	@Override
	public Channel removeChannel(int id) {
		return Channels.remove(id);
	}
}
