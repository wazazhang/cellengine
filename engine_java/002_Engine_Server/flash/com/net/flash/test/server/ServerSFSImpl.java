package com.net.flash.test.server;

import com.cell.j2se.CAppBridge;
import com.net.ExternalizableFactory;
import com.net.flash.message.FlashMessageFactory;
import com.net.flash.test.MessageCodecJava;
import com.net.flash.test.Messages;
import com.net.server.ServerListener;
import com.net.sfsimpl.server.SFSServerAdapter;
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

		adapter = new SFSServerAdapter(this, 
				new FlashMessageFactory(new MessageCodecJava(), Messages.class), 
				new FlashTestEchoServer());
		
		trace(new Object[] { "Test SFSExtension started" });
		
	}

	@Override
	public void handleClientRequest(String requestId, User sender, ISFSObject params) {
		adapter.handleClientRequest(requestId, sender, params);
	}
	
	@Override
	public void destroy() 
	{
		super.destroy();
	}

}
