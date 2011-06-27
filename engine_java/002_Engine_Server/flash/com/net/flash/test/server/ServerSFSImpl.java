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

public class ServerSFSImpl extends SFSServerAdapter
{
	FlashMessageFactory codec = new FlashMessageFactory(
			new MessageCodecJava(), 
			Messages.class);
	
	@Override
	public void init() 
	{
		CAppBridge.initNullStorage();
		try {		
			this.open(0, new FlashTestEchoServer());
		} catch (Exception e) {
			e.printStackTrace();
		}
		trace(new Object[] { "Test SFSExtension started" });
	}
	
	@Override
	public ExternalizableFactory getMessageFactory() {
		return codec;
	}

	@Override
	public void destroy() 
	{
		super.destroy();
	}

}
