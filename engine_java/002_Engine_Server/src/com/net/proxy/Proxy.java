package com.net.proxy;

import java.io.IOException;

import com.net.MessageHeader;


public interface Proxy 
{
	public void open(int local_port, String remote_host, int remote_port) throws IOException;

	public void close() throws IOException;
	
}
