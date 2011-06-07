package com.net.flash.test.server;

import java.io.IOException;

import com.cell.j2se.CAppBridge;
import com.net.ExternalizableFactory;
import com.net.flash.message.FlashMessageFactory;
import com.net.flash.test.MessageCodecJava;
import com.net.flash.test.Messages;
import com.net.server.ServerListener;
import com.net.sfsimpl.server.SFSServerAdapter;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class ServerSFSImpl extends SFSExtension
{
	SFSServerAdapter adapter;
	
	@Override
	public void init() 
	{
		CAppBridge.initNullStorage();

		try {		
			adapter = new SFSServerAdapter(this, 
				new FlashMessageFactory(new MessageCodecJava(), Messages.class));
			adapter.open(0, new FlashTestEchoServer());
		} catch (Exception e) {
			e.printStackTrace();
		}
		trace(new Object[] { "Test SFSExtension started" });
		
	}

	@Override
	public void handleClientRequest(String requestId, User sender, ISFSObject params) {
		adapter.handleClientRequest(requestId, sender, params);
	}
	
	@Override
	public void handleServerEvent(ISFSEvent event) throws Exception {
		System.out.println("handleServerEvent ex: " + event.toString());
		super.handleServerEvent(event);
	}
	
	@Override
	public Object handleInternalMessage(String cmdName, Object params) {
		System.out.println("handleInternalMessage ex: " + cmdName + " : " + params);
		return super.handleInternalMessage(cmdName, params);
	}
	
	@Override
	public void destroy() 
	{
		super.destroy();
	}

}
