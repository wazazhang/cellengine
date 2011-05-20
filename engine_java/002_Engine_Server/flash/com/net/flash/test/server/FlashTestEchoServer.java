package com.net.flash.test.server;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.CUtil;
import com.cell.io.CFile;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.cell.util.concurrent.ThreadPool;
import com.net.ExternalizableMessage;
import com.net.MessageHeader;
import com.net.NetDataInput;
import com.net.NetDataOutput;
import com.net.Protocol;
import com.net.flash.message.FlashMessageFactory;
import com.net.flash.test.MessageCodecJava;
import com.net.flash.test.Messages;
import com.net.flash.test.Messages.*;
import com.net.minaimpl.server.ServerImpl;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.server.ServerListener;

public class FlashTestEchoServer implements ServerListener
{
	private static Logger log = LoggerFactory.getLogger(FlashTestEchoServer.class);
	
	private ThreadPool services = new ThreadPool("Flash-Test");
	
	public FlashTestEchoServer() {
		
	}

	@Override
	public ClientSessionListener connected(ClientSession session) {
		log.info("connected " + session.getRemoteAddress());
		return new EchoClientSession(session);
	}
	
	class EchoClientSession implements ClientSessionListener, Runnable
	{
		ClientSession session;
		ScheduledFuture<?> task;
		public EchoClientSession(ClientSession session) {
			this.session = session;
			// 每10秒向客户端发送个消息
			this.task = services.scheduleAtFixedRate(this, 1000, 10000);
		}
		@Override
		public void disconnected(ClientSession session) {
			log.info("disconnected " + session.getRemoteAddress());
			this.task.cancel(false);
		}
		@Override
		public void sentMessage(ClientSession session, Protocol protocol, MessageHeader message) {}
		@Override
		public void receivedMessage(ClientSession session, Protocol protocol, MessageHeader message) {
			if (message instanceof EchoRequest) {
				session.sendResponse(protocol, new EchoResponse(message.toString() + " ok"));
			}
			else if (message instanceof Echo2Request) {
				session.sendResponse(protocol, new Echo2Response(message.toString() + " ok"));
			}
		}
		public void run() {
//			log.info("send notify");
			this.session.send(new EchoNotify("roll " + CUtil.getRandom(1, 100)));
		}
	}
	
}
