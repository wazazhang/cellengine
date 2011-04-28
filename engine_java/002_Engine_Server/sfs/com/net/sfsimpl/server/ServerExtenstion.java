package com.net.sfsimpl.server;

import java.io.IOException;
import java.util.Iterator;

import com.cell.exception.NotImplementedException;
import com.net.MessageHeader;
import com.net.server.ChannelManager;
import com.net.server.ClientSession;
import com.net.server.Server;
import com.net.server.ServerListener;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class ServerExtenstion extends SFSExtension implements Server
{
	@Override
	public void init()
	{
		
	}

	@Override
	public void destroy() 
	{
		
	}
	
	@Override
	public void handleClientRequest(String requestId, User sender, ISFSObject params) 
	{


	}
	
	@Override
	public Object handleInternalMessage(String cmdName, Object params)
	{
		return super.handleInternalMessage(cmdName, params);
	}
	
	@Override
	public void handleServerEvent(ISFSEvent event) throws Exception
	{
		super.handleServerEvent(event);
	}

//	----------------------------------------------------------------------------------------------------------

	@Override
	public ClientSession getSession(long sessionID) {
		return null;
	}
	
	@Override
	public int getSessionCount() {
		return 0;
	}
	
	@Override
	public Iterator<ClientSession> getSessions() {
		return null;
	}
	
	@Override
	public boolean hasSession(ClientSession session) {
		return false;
	}

//	----------------------------------------------------------------------------------------------------------

	@Override
	public void broadcast(MessageHeader message) 
	{
		
	}

//	----------------------------------------------------------------------------------------------------------

	@Override
	public ChannelManager getChannelManager() 
	{
		return null;
	}

//	----------------------------------------------------------------------------------------------------------

	@Override
	public long getHeartBeatReceived() {
		return 0;
	}
	
	@Override
	public long getHeartBeatSent() {
		return 0;
	}
	
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
	
//	----------------------------------------------------------------------------------------------------------

	@Deprecated
	@Override
	public void open(int port, ServerListener listener) throws IOException {
		throw new NotImplementedException("not support on sfs system !");
	}
	

	@Deprecated
	@Override
	public void dispose() throws IOException {
		throw new NotImplementedException("not support on sfs system !");
	}
	
	
}
