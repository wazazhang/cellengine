package com.net.client.service;

import com.net.MessageHeader;

public interface NotifyListener<N extends MessageHeader>
{
	public void notify(BasicNetService service, N notify);
}
