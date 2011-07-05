package com.net.flash.test.server;

import com.cell.j2se.CAppBridge;
import com.net.ExternalizableFactory;
import com.net.flash.test.MessageCodecJava;
import com.net.flash.test.Messages;
import com.net.sfsimpl.server.SFSServerAdapter;

public class ServerSFSImpl extends SFSServerAdapter
{
	ExternalizableFactory codec = new ExternalizableFactory(
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
