package com.net.minaimpl.proxy;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.net.minaimpl.SessionAttributeKey;
import com.net.minaimpl.server.ServerImpl;


public abstract class ProxyIoHandler extends IoHandlerAdapter 
{
	static final Logger 	log 		= LoggerFactory.getLogger(ProxyIoHandler.class.getName());
	
    @Override
    public void sessionCreated(IoSession session) throws Exception
    {
        session.suspendRead();
        session.suspendWrite();
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception 
    {
		IoSession sess = (IoSession) session.getAttribute(SessionAttributeKey.PROXY_OTHER_IO_SESSION);
		if (sess != null) {
			sess.setAttribute(SessionAttributeKey.PROXY_OTHER_IO_SESSION, null);
			sess.close(false);
			session.setAttribute(SessionAttributeKey.PROXY_OTHER_IO_SESSION, null);
		}
	}

    @Override
    public void messageReceived(IoSession src, Object message) throws Exception
    {
    	IoSession dst = ((IoSession)src.getAttribute(SessionAttributeKey.PROXY_OTHER_IO_SESSION));
    	
    	IoBuffer read_buffer 	= (IoBuffer) message;
    	int length 				= read_buffer.remaining();
    	
        IoBuffer write_buffer	= IoBuffer.allocate(length);
        read_buffer.mark();
        
        write_buffer.put(read_buffer);
        write_buffer.flip();
        
        dst.write(write_buffer);
        read_buffer.reset();
        
        System.out.println(src + " -> " + dst + " " + length + "(bytes)");
    }
    
    @Override
    public void messageSent(IoSession session, Object message) throws Exception 
    {}
    
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception 
    {
    	log.error(cause.getMessage(), cause);
    }
}
