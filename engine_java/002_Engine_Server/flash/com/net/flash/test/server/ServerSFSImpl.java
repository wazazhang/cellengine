package com.net.flash.test.server;

import com.cell.j2se.CAppBridge;
import com.net.ExternalizableFactory;
import com.net.flash.message.FlashMessageFactory;
import com.net.flash.test.MessageCodecJava;
import com.net.flash.test.Messages;
import com.net.server.ServerListener;
import com.net.sfsimpl.server.ServerExtenstion;

public class ServerSFSImpl extends ServerExtenstion
{
	@Override
	public void init() 
	{
		CAppBridge.initNullStorage();

		super.init();
		
		trace(new Object[] { "Test SFSExtension started" });
		
	}

	@Override
	protected ServerListener createListener() throws Exception {
		return new FlashTestEchoServer();
	}
	
	@Override
	public ExternalizableFactory createFactory() {	
		return new FlashMessageFactory(new MessageCodecJava(), Messages.class);
	}
	
	@Override
	public void destroy() 
	{
		super.destroy();
	}

}
