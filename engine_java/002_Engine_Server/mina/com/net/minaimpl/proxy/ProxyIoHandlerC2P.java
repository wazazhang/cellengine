
package com.net.minaimpl.proxy;

import java.net.SocketAddress;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;

import com.net.minaimpl.SessionAttributeKey;


public class ProxyIoHandlerC2P extends ProxyIoHandler
{
    private final ProxyIoHandlerS2P	connectorHandler = new ProxyIoHandlerS2P();
    private final IoConnector 		connector;
    private final SocketAddress		remoteAddress;

    public ProxyIoHandlerC2P(IoConnector connector, SocketAddress remoteAddress) 
    {
        this.connector = connector;
        this.remoteAddress = remoteAddress;
        connector.setHandler(connectorHandler);
    }

    /**
     * 当客户端链接到代理时
     */
    @Override
    public void sessionOpened(final IoSession c2p) throws Exception 
    {
    	// 链接远程主机
        connector.connect(remoteAddress).addListener(new IoFutureListener<ConnectFuture>() {
            public void operationComplete(ConnectFuture future) {
                try {
                	IoSession p2s = future.getSession();
                	// 如果链接成功，使c2p和p2s互相持有链接
                	p2s.setAttribute(SessionAttributeKey.PROXY_OTHER_IO_SESSION, c2p);
                    c2p.setAttribute(SessionAttributeKey.PROXY_OTHER_IO_SESSION, p2s);
                    p2s.resumeRead();
                    p2s.resumeWrite();
                } catch (RuntimeIoException e) {
                	c2p.close(true);
                } finally {
                	c2p.resumeRead();
                	c2p.resumeWrite();
                }
            }
        });
    }
    
}

