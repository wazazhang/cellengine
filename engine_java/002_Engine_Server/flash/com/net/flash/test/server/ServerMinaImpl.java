package com.net.flash.test.server;

import java.io.IOException;

import com.cell.CIO;
import com.cell.j2se.CAppBridge;
import com.cell.net.io.ExternalizableFactory;
import com.net.flash.test.MessageCodecJava;
import com.net.flash.test.Messages;
import com.net.minaimpl.server.ServerImpl;

public class ServerMinaImpl extends ServerImpl
{
	public ServerMinaImpl() {
		super(CIO.getAppBridge().getClassLoader(), 
				new ExternalizableFactory(new MessageCodecJava(), Messages.class), 10, 600, 600, 0);
	}
	
	public static void main(String[] args) throws IOException
	{
		try {
			CAppBridge.init();
			int port = 19820;
			if (args.length > 0) {
				try {
					port = Integer.parseInt(args[0]);
				} catch (Exception err) {
					System.err.println("use default port " + port);
				}
			}
			new ServerMinaImpl().open(port, new FlashTestEchoServer());
		} catch (Exception err) {
			err.printStackTrace();
			System.exit(1);
		}
	}
}
