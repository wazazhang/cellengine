package com.net.server;

import com.net.MessageHeader;
import com.net.Protocol;
import com.net.client.service.BasicNetService;

/**
 * 将客户端的消息预处理，然后以确定是否转发到服务器，或直接反馈给客户端。
 * 服务器反馈的消息也可以预处理，然后确定是否在发送给客户端。
 * @author WAZA
 * @param <REQ>
 * @param <RSP>
 */
public class WaitingListenerPreprocessor<
		REQ extends MessageHeader, 
		RSP extends MessageHeader> extends WaitingListenerProxy<REQ, RSP>
{
	@Override
	final public void sendToServer(
			BasicNetService remote, 
			ClientSession client,
			Protocol protocol,
			REQ request) 
	{
		MessageHeader response = preprocessingMessage(request);
		if (response == null) {
			super.sendToServer(remote, client, protocol, request);
		} else {
			client.sendResponse(protocol, response);
		}
	}

	final public void sendToClient(ClientSession client, REQ request, RSP response) {
		if (endprocessingMessage(request, response)) {
			super.sendToClient(client, request, response);
		}
	}
	

	/**
	 * 如果该方法返回空，转发给服务器处理，否则直接发送反馈给客户端。
	 * @param request
	 * @return
	 */
	protected RSP preprocessingMessage(REQ request) {
		return null;
	}

	/**
	 * 对服务器传回的数据在本机的影响做处理
	 * @param request
	 * @param response
	 * @return 是否反馈给客户端
	 */
	protected boolean endprocessingMessage(REQ request, RSP response) {
		return true;
	}
	
	
}
