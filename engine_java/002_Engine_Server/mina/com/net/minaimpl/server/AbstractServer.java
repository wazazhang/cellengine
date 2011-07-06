package com.net.minaimpl.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.mina.core.IoUtil;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.filterchain.IoFilterChainBuilder;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.service.IoProcessor;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.service.IoServiceStatistics;
import org.apache.mina.core.service.SimpleIoProcessorPool;
import org.apache.mina.core.service.TransportMetadata;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.core.session.IoSessionDataStructureFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioProcessor;
import org.apache.mina.transport.socket.nio.NioSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CUtil;
import com.cell.util.concurrent.ThreadPool;
import com.net.ExternalizableFactory;
import com.net.MessageHeader;
import com.net.minaimpl.KeepAlive;
import com.net.minaimpl.MinaThreadFactory;
import com.net.minaimpl.NetPackageCodec;
import com.net.minaimpl.ProtocolImpl;
import com.net.minaimpl.ProtocolPool;
import com.net.minaimpl.SessionAttributeKey;
import com.net.minaimpl.SystemMessages;
import com.net.minaimpl.SystemMessages.*;
import com.net.server.Channel;
import com.net.server.ChannelManager;
import com.net.server.ClientSession;
import com.net.server.Server;
import com.net.server.ServerListener;


public abstract class AbstractServer extends IoHandlerAdapter implements Server
{
	final protected Logger 			log = LoggerFactory.getLogger(getClass().getName());
	
	final NetPackageCodec 			Codec;
	final IoAcceptor 				Acceptor;
	final ExecutorService 			AcceptorPool;
	final ExecutorService 			IoProcessorPool;
	
	final ExternalizableFactory 	externalizable_factory;
	
	ServerListener					SrvListener;
	long							StartTime;
	boolean							CloseOnError = true;
	KeepAlive						keep_alive;
	
//	----------------------------------------------------------------------------------------------------------------------
	/**
	 * @param class_loader
	 * @param externalizable_factory
	 * @param acceptor_pool
	 * @param io_processor_pool
	 * @param io_processor_count
	 * @param sessionWriteIdleTimeSeconds	多长时间内没有发送数据，断掉链接(秒)
	 * @param sessionReadIdleTimeSeconds	多长时间内没有接受数据，断掉链接(秒)
	 * @param keepalive_interval_sec		心跳间隔，0表示不使用心跳机制
	 * @param close_on_error
	 */
	public AbstractServer(
			ClassLoader 			class_loader,
			ExternalizableFactory 	externalizable_factory,
			Executor 				acceptor_pool,
			Executor 				io_processor_pool,
			int						io_processor_count,
			int 					sessionWriteIdleTimeSeconds,
			int 					sessionReadIdleTimeSeconds, 
			int 					keepalive_interval_sec,
			boolean					close_on_error) 
	{
		if (acceptor_pool == null) {
			this.AcceptorPool = Executors.newCachedThreadPool(MinaThreadFactory.getInstance());
			acceptor_pool = this.AcceptorPool;
		} else {
			this.AcceptorPool = null;
		}

		if (io_processor_pool == null) {
			this.IoProcessorPool = Executors.newCachedThreadPool(MinaThreadFactory.getInstance());
			io_processor_pool = this.IoProcessorPool;
		} else {
			this.IoProcessorPool = null;
		}

		this.externalizable_factory = externalizable_factory;
		this.Codec = new NetPackageCodec(class_loader, externalizable_factory);
		this.Acceptor = new NioSocketAcceptor(acceptor_pool, 
				new SimpleIoProcessorPool<NioSession>(
						NioProcessor.class, 
						io_processor_pool, 
						io_processor_count));
		this.Acceptor.getSessionConfig().setReaderIdleTime(sessionReadIdleTimeSeconds);
		this.Acceptor.getSessionConfig().setWriterIdleTime(sessionWriteIdleTimeSeconds);
		this.Acceptor.setHandler(this);
		if (keepalive_interval_sec > 0) {
			this.keep_alive = new KeepAlive(
					Codec, 
					keepalive_interval_sec,
					Math.min(sessionReadIdleTimeSeconds, sessionWriteIdleTimeSeconds));
			this.Acceptor.getFilterChain().addLast("keep-alive", keep_alive); //心跳  
		}
		this.Acceptor.getFilterChain().addLast("codec", 
				new ProtocolCodecFilter(Codec));
		this.CloseOnError = close_on_error;
	}	
	
//	----------------------------------------------------------------------------------------------------------------------

	@Override
	public ExternalizableFactory getMessageFactory() {
		return externalizable_factory;
	}
	
	final public long getStartTime() {
		return StartTime;
	}
	
	synchronized public void open(int port, ServerListener listener) throws IOException 
	{
		if (!Acceptor.isActive()) {
			this.SrvListener = listener;
			this.SrvListener.init(this);
			this.StartTime = System.currentTimeMillis();
			log.info("starting server at port : " + port);
			Acceptor.bind(new InetSocketAddress(port));
			log.info("server started !");
		}
	}

	synchronized public void dispose() throws IOException
	{
		if (!Acceptor.isDisposed()) 
		{
			log.info("server closing...");
			try {
				Acceptor.unbind();
				Acceptor.dispose();
			} catch (Throwable err) {
				log.error(err.getMessage(), err);
			}
			try {
				if (this.AcceptorPool != null) {
					this.AcceptorPool.awaitTermination(1, TimeUnit.SECONDS);
					this.AcceptorPool.shutdownNow();
				}
			} catch (Throwable err) {
				log.error(err.getMessage(), err);
			}
			try {
				if (this.IoProcessorPool != null) {
					this.IoProcessorPool.awaitTermination(1, TimeUnit.SECONDS);
					this.IoProcessorPool.shutdownNow();
				}
			} catch (Throwable err) {
				log.error(err.getMessage(), err);
			}
			log.info("server closed !");
		}
	}

//	-----------------------------------------------------------------------------------------------------------------------

	final public long getSentMessageCount() {
		return Codec.getSentMessageCount();
	}
	
	final public long getReceivedMessageCount () {
		return Codec.getReceivedMessageCount();
	}
	
	final public long getSentBytes(){
		return Codec.getTotalSentBytes();
	}
	
	final public long getReceivedBytes(){
		return Codec.getTotalReceivedBytes();
	}
	final public long getHeartBeatReceived() {
		if (keep_alive != null) {
			return keep_alive.getReceivedMessageCount();
		}
		return 0;
	}
	final public long getHeartBeatSent() {
		if (keep_alive != null) {
			return keep_alive.getSentMessageCount();
		}
		return 0;
	}

//	-----------------------------------------------------------------------------------------------------------------------


	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error(cause.getMessage() + " : " + session, cause);
		if (CloseOnError) {
			if (session.isConnected() && !session.isClosing()) {
				session.close(false);
			}
		}
	}
	
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.debug("sessionIdle : " + session + " : " + status);
		if (CloseOnError) {
			if (session.isConnected() && !session.isClosing()) {
				session.close(false);
			}
		}
	}

//	-----------------------------------------------------------------------------------------------------------------------
	
	protected boolean write(
			IoSession 		session, 
			MessageHeader 	message,
			byte			protocol, 
			int				channel_id, 
			int				packnumber)
	{
		try {
			if (session.isConnected() && !session.isClosing()) {
				ProtocolImpl p = ProtocolPool.getInstance().createProtocol();
//				p.SessionID			= session.getId();
				p.Protocol			= protocol;
				p.PacketNumber		= packnumber;
				p.message			= message;			
				p.ChannelID			= channel_id;
//				p.ChannelSessionID	= channel_sender_id;
//				session.resumeWrite();
				WriteFuture future = session.write(p);
				
//				// Wait until the message is completely written out to the O/S
//				// buffer.
//				future.awaitUninterruptibly();
//				if (future.isWritten()) {
//					// The message has been written successfully.
//					return true;
//				} else {
//					// The messsage couldn't be written out completely for some
//					// reason.
//					// (e.g. Connection is closed)
//					return false;
//				}
				return false;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

//	----------------------------------------------------------------------------------------------------------------------


	
//	----------------------------------------------------------------------------------------------------------------------
	
	public String getStats()
	{
		StringBuilder lines = new StringBuilder();
		lines.append("[Mina Server Implements]\n");
		CUtil.toStatusLine("Active",					Acceptor.isActive(), lines);
		CUtil.toStatusLine("ActivationTime",			CUtil.timesliceToStringHour(Acceptor.getActivationTime()), lines);
		CUtil.toStatusLine("Address",					Acceptor.getLocalAddresses(), lines);
		CUtil.toStatusLine("SessionCount",				getSessionCount(), lines);
		CUtil.toStatusLine("ScheduledWriteBytes",		CUtil.toBytesSizeString(Acceptor.getScheduledWriteBytes()), lines);
		CUtil.toStatusLine("ScheduledWriteMessages",	Acceptor.getScheduledWriteMessages(), lines);
		CUtil.toStatusLine("ReceivedBytes",				CUtil.toBytesSizeString(getReceivedBytes()), lines);
		CUtil.toStatusLine("ReceivedMessageCount",		getReceivedMessageCount(), lines);
		CUtil.toStatusLine("SentBytes",					CUtil.toBytesSizeString(getSentBytes()), lines);
		CUtil.toStatusLine("SentMessageCount",			getSentMessageCount(), lines);
		CUtil.toStatusLine("HeartBeatSent",				getHeartBeatSent(), lines);
		CUtil.toStatusLine("HeartBeatReceived",			getHeartBeatReceived(), lines);
		CUtil.toStatusLine("StartTime",					CUtil.timeToString(getStartTime()), lines);
		if (AcceptorPool instanceof ThreadPoolExecutor) {
			lines.append("[AcceptorPool]\n");
			lines.append(ThreadPool.getStatus((ThreadPoolExecutor)AcceptorPool));
		}
		if (IoProcessorPool instanceof ThreadPoolExecutor) {
			lines.append("[IoProcessorPool]\n");
			lines.append(ThreadPool.getStatus((ThreadPoolExecutor)IoProcessorPool));
		}
		CUtil.toStatusSeparator(lines);
		return lines.toString();
	}
	



}
