
package com.net.minaimpl.proxy;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoConnector;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.net.minaimpl.server.ServerImpl;
import com.net.proxy.Proxy;

public class ProxyImpl implements Proxy
{
	static final Logger 	log 		= LoggerFactory.getLogger(ProxyImpl.class.getName());
	
    NioSocketAcceptor 		acceptor;
    IoConnector 			connector;

    public void open(int local_port, String remote_host, int remote_port)
    {
    	if (acceptor == null)
    	{
    		 // Create TCP/IP acceptor.
            acceptor = new NioSocketAcceptor();
            // Create TCP/IP connector.
            connector = new NioSocketConnector();
            // Set connect timeout.
            connector.setConnectTimeoutMillis(30*1000L);
    	
            ProxyIoHandlerC2P handler = new ProxyIoHandlerC2P(
            		connector,
            		new InetSocketAddress(remote_host, remote_port));
            try{
                // Start proxy.
                acceptor.setHandler(handler);
                acceptor.bind(new InetSocketAddress(local_port));
            }catch (Exception e) {
            	log.error(e.getMessage(), e);
			}

            log.info("listening on port " + local_port + "->" + remote_host+":"+remote_port);
    	}
    }
    
    public void close()
    {
		if (acceptor != null) {
			try {
				acceptor.unbind();
				acceptor.dispose();
				acceptor = null;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			log.info("close client -> proxy");
		}

		if (connector != null) {
			try {
				connector.dispose();
				connector = null;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			log.info("close proxy -> server");
		}
		
		
	}
    
    

}
