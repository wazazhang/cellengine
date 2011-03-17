
package com.net.chat.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CIO;
import com.net.MessageHeader;
import com.net.chat.ChatMessages.ChannelNotifyS2C;
import com.net.chat.ChatMessages.ChannelRequestC2S;
import com.net.chat.ChatMessages.ChannelResponseS2C;
import com.net.chat.ChatMessages.ChatNotifyS2C;
import com.net.chat.ChatMessages.ChatRequestC2S;
import com.net.chat.ChatMessages.ChatResponseS2C;
import com.net.chat.ChatMessages.LoginRequestC2S;
import com.net.chat.ChatMessages.LoginResponseS2C;
import com.net.minaimpl.server.ServerImpl;
import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.server.Server;
import com.net.server.ServerListener;

public class ChatServer extends ServerImpl
{
	private static final Logger _log = LoggerFactory.getLogger(ChatServer.class.getName());
	
    public static void main(String args[]) throws Exception
	{
    	System.out.println(
			"**********************************************************************************************\n" + 
			"* Chat Server                                                                                *\n" + 
			"**********************************************************************************************"
			);
		try {
			String Port = "16000";

			if (args != null && args.length >= 1) {
				Port = args[0];
			}

			ChatServer server = new ChatServer();

			server.open(Integer.parseInt(Port));
			
		} catch (Throwable err) {
			err.printStackTrace();
			System.exit(-1);
		}
	}
    
    public ChatServer() {
    	super(16, 1000000, 1000000);
	}
    
    public void open(int port) throws Exception
    {
    	super.open(port, new GTServerListener());
    }
    
    public class GTServerListener implements ServerListener
    {
    	public GTServerListener() {}
    	
        public ClientSessionListener connected(ClientSession session) {
    		return new GTClientListener(session, ChatServer.this);
        }
    }
    
    public class GTClientListener implements ClientSessionListener
    {
    	private final ClientSession sessionRef;

    	final Server serverRef;
    	
		public GTClientListener(ClientSession session, Server server) {
			sessionRef = session;
			serverRef = server;
		}

		public void disconnected(ClientSession session) 
		{
			System.out.println("disconnected : " + sessionRef.getName());
			
		}

		public void receivedMessage(ClientSession session, MessageHeader message)
		{
			if (message instanceof LoginRequestC2S)
			{
				processLoginMessage((LoginRequestC2S)message, session);
			}
			else if (message instanceof ChatRequestC2S) 
	    	{
	    		processChatMessage((ChatRequestC2S)message, session);
	    	}
	    	else if (message instanceof ChannelRequestC2S) 
	    	{
	    		processChannelMessage((ChannelRequestC2S)message, session);
	    	}
		}
    }
  
    public class GTChannelListener implements ChannelListener
    {
        public void receivedMessage(Channel channel, ClientSession session, MessageHeader message)
        {
        	if (message instanceof ChatRequestC2S) 
	    	{
	    		processChatMessage((ChatRequestC2S)message, session);
	    	}
        }
        
        public void sessionJoined(Channel channel, ClientSession session) 
        {
        	broadcastJoinChannel(channel, session);
        }
        
        public void sessionLeaved(Channel channel, ClientSession session)
        {
        	broadcastLeaveChannel(channel, session);
        }
    }
    
//	-----------------------------------------------------------------------------------------------------------------------    


//	-----------------------------------------------------------------------------------------------------------------------    

    String getUserName(ClientSession session)
    {
    	Object obj = session.getAttribute("UserName");
    	if (obj != null) {
    		return obj.toString();
    	}
    	return "";
    }
    
    void setUserName(ClientSession session, String name) 
    {
    	session.setAttribute("UserName", name);
    }
    
    ClientSession containsUser(String name)
    {
		for (Iterator<ClientSession> it = getSessions(); it.hasNext();) {
			ClientSession session = it.next();
			if (getUserName(session).equals(name)) {
				return session;
			}
		}
		return null;
	}
    
    
//	-----------------------------------------------------------------------------------------------------------------------    

	protected void processLoginMessage(LoginRequestC2S request, ClientSession session)
	{
		if (request.UserName.length() > 0 && containsUser(request.UserName) == null) {
			setUserName(session, request.UserName);
			session.send(request, LoginResponseS2C.create_SUCCEED());
		} else {
			session.send(request, LoginResponseS2C.create_FAILED("duplicate user name"));
		}
	}
    
    protected void processChatMessage(ChatRequestC2S request, ClientSession session)
    {
    	if (getUserName(session).length()<=0)
    	{
    		session.send(request, ChatResponseS2C.create_UNKNOW());
    	}
    	
		try
		{
			switch(request.Action)
			{
			case ALL:
				chatAll(request, session);
				break;
				
			case CHANNEL:
				chatChannel(request, session, getChannelManager().getChannel(request.Channel));
				break;
				
			case SINGLE:
				chatSingle(request, session, containsUser(request.Reciver));
				break;
			}
		
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
			session.send(request, ChatResponseS2C.create_UNKNOW());
		}
    }
    
    void chatAll(ChatRequestC2S request, ClientSession session)
    {
    	broadcast(ChatNotifyS2C.create_AllNotify(request.Sender, request.Message));
		session.send(request, ChatResponseS2C.create_SUCCEED());
    }
    
    void chatChannel(ChatRequestC2S request, ClientSession session, Channel ch)
    {
		if (ch != null)
		{
			ch.send(session, ChatNotifyS2C.create_ChannelNotify(request.Sender, request.Channel, request.Message));
			session.send(request, ChatResponseS2C.create_SUCCEED());
		}
		else
		{
			session.send(request, ChatResponseS2C.create_CHANNEL_NOT_EXIST());
		}
    }
    
    void chatSingle(ChatRequestC2S request, ClientSession session, ClientSession reciver)
    {
    	if (reciver != null)
		{
			reciver.send(ChatNotifyS2C.create_SingleNotify(request.Sender, request.Reciver, request.Message));
			session.send(request, ChatResponseS2C.create_SUCCEED());
		}
		else
		{
			session.send(request, ChatResponseS2C.create_RECIVER_NOT_EXIST());
		}
    }
    
    protected void processChannelMessage(ChannelRequestC2S request, ClientSession session)
    {
    	if (getUserName(session).length()<=0){
    		session.send(request, ChatResponseS2C.create_UNKNOW());
    	}
    	
    	try
    	{
			switch (request.Action)
			{
			case GET_JOINED_CHANNELS:
				getJoinedChannels(session, request);
				break;
			
			case GET_CHANNEL_MEMBERS:
				getChannelMembers(
						getChannelManager().getChannel(request.ChannelName),
						session,
						request);
				break;
			
			case CREATE_CHANNEL:
				createChannel(session, request);
				break;
				
			case JOIN_CHANNEL:
				joinChannel(
						getChannelManager().getChannel(request.ChannelName), 
						session, 
						request);
				break;
				
			case LEAVE_CHANNEL:
				leaveChannel(
						getChannelManager().getChannel(request.ChannelName), 
						session, 
						request);
				break;
			}
    	}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
			session.send(request, ChatResponseS2C.create_UNKNOW());
		}
	}
    
    void getJoinedChannels(ClientSession session, ChannelRequestC2S request)
    {
    	Iterator<Channel> channels = session.getServer().getChannelManager().getChannels();
		
		ArrayList<Integer> list = new ArrayList<Integer>();

		while (channels.hasNext()) {
			Channel ch = channels.next();
			if (ch.hasSession(session)) {
				list.add(ch.getID());
			}
		}

		session.send(request, ChannelResponseS2C.create_SUCCEED_GET_JOINED_CHANNELS(list));
    }
    
    void getChannelMembers(Channel dstChannel, ClientSession session, ChannelRequestC2S request)
    {
		if (dstChannel!=null) 
		{
			Iterator<ClientSession> iter = dstChannel.getSessions();
			ArrayList<String> list = new ArrayList<String>();
			while (iter.hasNext()) {
				ClientSession member = iter.next();
				list.add(getUserName(member));
			}
			
			session.send(request, ChannelResponseS2C.create_SUCCEED_GET_CHANNEL_MEMBERS(list));
		}
		else
		{
			session.send(request, ChannelResponseS2C.create_FAILED(request.Action));
		}
    }
    
    void createChannel(ClientSession session, ChannelRequestC2S request) 
    {
    	Channel dstChannel = session.getServer().getChannelManager().getChannel(request.ChannelName);
		
		if (dstChannel==null) 
		{
			dstChannel = getChannelManager().createChannel(request.ChannelName, new GTChannelListener());
			session.send(request, ChannelResponseS2C.create_SUCCEED_CREATE_CHANNEL(dstChannel.getID()));
		}
		else
		{
			session.send(request, ChannelResponseS2C.create_FAILED(request.Action));
		}
    }

    
    
    void joinChannel(Channel dstChannel, ClientSession session, ChannelRequestC2S request)
    {
    	if (dstChannel!=null && dstChannel.join(session)) 
		{
			session.send(request, ChannelResponseS2C.create_SUCCEED(request.Action));
		}
		else
		{
			session.send(request, ChannelResponseS2C.create_FAILED(request.Action));
		}
    }
    
    void leaveChannel(Channel dstChannel, ClientSession session, ChannelRequestC2S request)
    {
    	if (dstChannel!=null && dstChannel.leave(session)) 
		{
			
			
			session.send(request, ChannelResponseS2C.create_SUCCEED(request.Action));
		}
		else
		{
			session.send(request, ChannelResponseS2C.create_FAILED(request.Action));
		}
    }
    
    private void broadcastJoinChannel(Channel dstChannel, ClientSession session)
    {
    	dstChannel.send(session, ChannelNotifyS2C.create_MEMBER_JOINED(
    			dstChannel.getID(), 
    			getUserName(session)));	
    }
    
    private void broadcastLeaveChannel(Channel dstChannel, ClientSession session)
    {
    	dstChannel.send(session, ChannelNotifyS2C.create_MEMBER_LEAVED(
    			dstChannel.getID(), 
    			getUserName(session)));
    }
    
}



