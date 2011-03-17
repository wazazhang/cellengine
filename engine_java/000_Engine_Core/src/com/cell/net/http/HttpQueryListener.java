package com.cell.net.http;

public interface HttpQueryListener
{
	public void response(String url, String result);	
	
	public void timeout(String url);
}
