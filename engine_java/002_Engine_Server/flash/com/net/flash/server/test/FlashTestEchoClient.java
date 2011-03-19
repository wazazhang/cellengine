package com.net.flash.server.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.cell.util.concurrent.ThreadPool;
import com.net.ExternalizableMessage;
import com.net.MessageHeader;
import com.net.NetDataInput;
import com.net.NetDataOutput;
import com.net.Protocol;
import com.net.client.service.BasicNetService;
import com.net.client.service.NetService;
import com.net.client.service.WaitingListener;
import com.net.flash.message.FlashMessageFactory;
import com.net.flash.server.test.Messages.*;
import com.net.minaimpl.client.ServerSessionImpl;
import com.net.minaimpl.server.ServerImpl;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.server.ServerListener;

public class FlashTestEchoClient extends NetService
{
	FlashTestEchoClient() {
		super(new ServerSessionImpl(
				CObject.getAppBridge().getClassLoader(),
				new FlashMessageFactory(new MutualMessageCodecJava(), Messages.class)));
	}
	
	void run()
	{
		try {
			BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
			textInput("") ;
			while (true) {
				try {
					String cmd = read.readLine();
					textInput(cmd);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	void textInput(String cmd) 
	{
		if (cmd.startsWith("send")) 
		{
			String[] _number = cmd.split("\\s", 2);
			if (_number.length == 2) {
				testSend(_number[1].trim());
				return;
			}
		}
		else if (cmd.startsWith("connect"))
		{
			String[] host_port = cmd.split("\\s");
			if (host_port.length == 3) {
				String host = host_port[1].trim();
				Integer port = Integer.parseInt(host_port[2].trim());
				connect(host, port, 10000L);
				return;
			}
			if (host_port.length == 1) {
				connect("127.0.0.1", 19820, 10000L);
				return;
			}
		}
		else if (cmd.startsWith("disconnect"))
		{
			close(true);
			return;
		}
		else if (cmd.startsWith("exit"))
		{
			System.exit(1);
			return;
		}
		System.out.println("usage:");
		System.out.println("connect <host> <port>");
		System.out.println("disconnect");
		System.out.println("send <package-count>");
		System.out.println("exit");
	}
	
	void testSend(String text)
	{
		try {
			sendRequest(new EchoRequest(text), 1, new WaitingListener<MessageHeader, MessageHeader>() {
				public void response(BasicNetService service, MessageHeader request, MessageHeader response) {
					System.out.println("response : " + response);
				}
				public void timeout(BasicNetService service, MessageHeader request, long time) {
					System.out.println("timeout : " + request);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		CAppBridge.init();
		new FlashTestEchoClient().run();
	}
	
}
