package com.net.server;

import com.net.MessageHeader;
import com.net.Protocol;
import com.net.client.ServerSession;
import com.net.client.service.BasicNetService;
import com.net.client.service.WaitingListener;

/**
 * 一般用于将从一个客户端连接接受到的数据发送到另外一台服务器。
 * 然后再将源信息反馈给客户端。
 * @author WAZA
 * @param <REQ>
 * @param <RSP>
 */
public class WaitingListenerProxy<
		REQ extends MessageHeader, 
		RSP extends MessageHeader> implements WaitingListener<REQ, RSP>
{
	private ClientSession	src_client;
	private Protocol 		src_protocol;
		
	/**
	 * 转发送到远程服务器。可以覆盖此方法做预处理。
	 * @param remote
	 * @param request
	 */
	public void sendToServer(
			BasicNetService remote, 
			ClientSession client, 
			Protocol protocol, 
			REQ request) 
	{
		this.src_client		= client;
		this.src_protocol	= protocol;
		remote.sendRequest(request, this);
	}
	
	/**
	 * 远程服务器反馈消息后被调用。可以覆盖此方法做预处理。
	 * 反馈给客户端。
	 * @param response
	 */
	public void sendToClient(ClientSession client, REQ request, RSP response) {
		client.sendResponse(src_protocol, response);
	}
	
	/**
	 * @return the src_client
	 */
	public ClientSession getSrcClient() {
		return src_client;
	}

	/**
	 * @param service 远程服务器
	 * @param request
	 * @param response
	 */
	final public void response(BasicNetService remote, REQ request, RSP response){
		sendToClient(src_client, request, response);
	}
	
	public void timeout(BasicNetService remote, REQ request, long send_time){}
	
	
}
