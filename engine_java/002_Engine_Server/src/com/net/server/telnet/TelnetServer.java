package com.net.server.telnet;

import java.io.IOException;

/**
 * <a href="package.html">Telnet规范</a>
 * @author WAZA
 */
public interface TelnetServer 
{
	public void	open(int port, TelnetServerListener listener) throws IOException ;
	
	public void	close() throws IOException;
		
}
