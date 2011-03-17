package com.net.minaimpl.server.proxy.b2c;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.net.ExternalizableFactory;
import com.net.MessageHeader;
import com.net.minaimpl.NetPackageCodec;
import com.net.minaimpl.server.proxy.b2c.ProxyEncoderB2C.ProxyMessage;

/**
 * 将大量客户端链接，通过该服务器，转发到另外一个服务器上，只建立很少量的链接。<br>
 * BridgeServer <-> Client
 * @author WAZA
 */
public abstract class ProxyServerB2C extends IoHandlerAdapter// implements Proxy
{
	final protected Logger 		log = LoggerFactory.getLogger(getClass().getName());
	
	final ProxyEncoderB2C		Codec;
	final IoAcceptor 			Acceptor;
	final ClassLoader 			class_loader;
	final ExternalizableFactory	ext_factory;
	
	private long 				StartTime;
//	----------------------------------------------------------------------------------------------------------------------

	/**
	 * @param cl ClassLoader
	 * @param ef ExternalizableFactory
	 * @param ioProcessCount IO处理线程数
	 * @param sessionWriteIdleTimeSeconds	多长时间内没有发送数据，断掉链接
	 * @param sessionReadIdleTimeSeconds	多长时间内没有接受数据，断掉链接
	 * @param server_connector
	 */
	public ProxyServerB2C(
			ClassLoader cl,
			ExternalizableFactory ef,
			int ioProcessCount) 
	{	
		this.class_loader	= cl;
		this.ext_factory	= ef;
		this.Codec			= new ProxyEncoderB2C(cl, ef);
		this.Acceptor		= new NioSocketAcceptor(ioProcessCount);
		this.Acceptor.setHandler(this);
		this.Acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(Codec));
	}	
	
//	----------------------------------------------------------------------------------------------------------------------

	final public long getStartTime() {
		return StartTime;
	}
	
	synchronized public void open(int localPort, String remoteHost, int remotePort) throws IOException 
	{
		log.info("starting server at port : " + localPort);
		StartTime = System.currentTimeMillis();
		Acceptor.bind(new InetSocketAddress(localPort));
		log.info("server started !");
	}
	
	synchronized public void close() throws IOException
	{
		log.info("server closing...");
		Acceptor.unbind();
		Acceptor.dispose();
		log.info("server closed !");
	}

//	-----------------------------------------------------------------------------------------------------------------------

//	-----------------------------------------------------------------------------------------------------------------------


	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error(cause.getMessage() + "\n" + session, cause);
		session.close(false);
	}
	
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.debug("sessionIdle : " + session + " : " + status);
		session.close(false);
	}

	public void sessionOpened(IoSession session) throws Exception {
		log.debug("sessionOpened : " + session);
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		log.debug("sessionClosed : " + session);
	}
	
	public void messageReceived(IoSession session, Object message) throws Exception 
	{
		if (message instanceof ProxyMessage) {
			ProxyMessage header = (ProxyMessage) message;
			header.SessionID			= session.getId();		
			header.ChannelSesseionID	= session.getId();
			ServerSessionB2S remote		= getServerSession();
			remote.send(header);
			// TODO send server
		} else {
			log.error("bad message type : " + session + " : " + message);
		}
	}
	
	protected ServerSessionB2S getServerSession()
	{
		return null;
	}

	protected class ServerSessionB2S extends IoHandlerAdapter
	{
		private String	c_host; 
		private int		c_port; 
		private long	c_timeout;
		
		IoSession			Session;
		IoConnector			Connector;
		NetPackageCodec		Codec;
		
		public ServerSessionB2S()
		{
			Codec		= new NetPackageCodec(class_loader, ext_factory);
			Connector 	= new NioSocketConnector();
			Connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(Codec));
			Connector.setHandler(this);
		}
		
		public void send(ProxyMessage message)
		{
			
		}
		
		synchronized public boolean connect(String host, int port, long timeout) throws IOException 
		{
			
			this.c_host		= host; 
			this.c_port		= port; 
			this.c_timeout	= timeout;
			
			SocketAddress address = new InetSocketAddress(host, port);
			
			ConnectFuture future1 = Connector.connect(address);
			future1.awaitUninterruptibly(timeout);
			if (!future1.isConnected()) {
				return false;
			}
			Session = future1.getSession();

			if (Session != null && Session.isConnected()) {
				return true;
			} else {
				log.error("not connect : " + address.toString());
			}
			
			return false;
		}
		
		public void sessionClosed(IoSession session) throws Exception {
			try {
				log.error("server is broken, reconnec to server !");
//				connect(c_host, c_port, c_timeout);
			} catch (Throwable t) {
				log.error(t.getMessage(), t);
			}
		}
		
		public void messageReceived(IoSession iosession, Object message) throws Exception 
		{
			if (message instanceof MessageHeader) {
				MessageHeader header = (MessageHeader) message;
			} else {
				log.error("messageReceived : bad message type : " + message);
			}
		}
	}
}


