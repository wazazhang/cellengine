package com.net.client.service;

import com.net.MessageHeader;

public class WaitingListenerAdapter implements WaitingListener<MessageHeader, MessageHeader>
{
	public void response(BasicNetService service, MessageHeader request, MessageHeader response){}
	
	/**
	 * @param service
	 * @param request
	 * @param send_time 该消息的发送时间
	 */
	public void timeout(BasicNetService service, MessageHeader request, long send_time){}
}
