package com.net.server;

import com.net.MessageHeader;
import com.net.Protocol;

public interface ClientSessionListener
{
	/**
	 * 监听客户端的断开
	 */
	public void disconnected(ClientSession session);
	
	/**
	 * 监听客户端发送过来的数据包
	 * @param session
	 * @param protocol 
	 * @param message
	 */
	public void receivedMessage(ClientSession session, Protocol protocol, MessageHeader message);
	
	
	/**
	 * 服务器向客户端发送已完成
	 * @param session
	 * @param protocol
	 * @param message
	 */
	public void sentMessage(ClientSession session, Protocol protocol, MessageHeader message);

}