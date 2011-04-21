package com.net.client.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.util.concurrent.ThreadPool;
//import com.net.Heartbeat;
import com.net.MessageHeader;
import com.net.Protocol;
import com.net.client.ClientChannel;
import com.net.client.ServerSession;
import com.net.client.ServerSessionListener;





/**
 * 客户端链接到服务器的服务，该类可以方便监听(请求/回馈/通知)
 * @author WAZA
 */
public abstract class BasicNetService
{
	/**request从1开始*/
	final private AtomicInteger 	SendedPacks 			= new AtomicInteger(1);
	
	final private SimpleClientListenerImpl
									session_listener 		= new SimpleClientListenerImpl();
	
	public long 					DropRequestTimeOut 		= 60000;
	
	final private ConcurrentHashMap<Integer, Request> 
									WaitingListeners 		= new ConcurrentHashMap<Integer, Request>();

//	---------------------------------------------------------------------------------------------------------------
//	notifiers
	final private ReentrantLock		notifies_lock	= new ReentrantLock();
	
	final private ConcurrentHashMap<Class<?>, HashSet<NotifyListener<?>>> 
									notifies_map	= new ConcurrentHashMap<Class<?>, HashSet<NotifyListener<?>>>();
	
	
	final private ConcurrentHashMap<Protocol, MessageHeader>
									UnhandledMessages 		= new ConcurrentHashMap<Protocol, MessageHeader>();
	

//	---------------------------------------------------------------------------------------------------------------
	
	
	private long					LastCleanRequestTime 	= System.currentTimeMillis();

	private AtomicInteger			request_response_ping	= new AtomicInteger();
	
//	private AtomicInteger			heartbeat_counter_		= new AtomicInteger();

	final private ThreadPool		thread_pool;
	
	final protected Logger 			log;
	
	
//	---------------------------------------------------------------------------------------------------------------------------------

	public BasicNetService(ThreadPool thread_pool) 
	{
		this.log				= LoggerFactory.getLogger(getClass().getName());
		this.thread_pool		= thread_pool;
	}

	final protected ServerSessionListener getSessionListener() 
	{
		return session_listener;
	}

//	----------------------------------------------------------------------------------------------------------------------------

	/**
	 * 断开链接
	 * @param force 是否立即断开
	 */
	abstract public void close(boolean force) ;

	abstract public void dispose();
	
//	----------------------------------------------------------------------------------------------------------------------------

	abstract protected ServerSession getSession();

//	----------------------------------------------------------------------------------------------------------------------------

	/**
	 * 直接发送，不监听回馈 
	 * @param msg
	 */
	public void send(MessageHeader msg) 
	{
		getSession().send(msg);
	}

//	----------------------------------------------------------------------------------------------------------------------------

	public long getHeartbeatCount()
	{
//		return this.heartbeat_counter_.get();
		
		return getSession().getHeartBeatReceived() + getSession().getHeartBeatSent();
	}
	
//	@Deprecated
//	final public AtomicReference<MessageHeader> sendHeartbeat()
//	{
//		心跳机制客户端单方面实现是不完整的，该功能已经迁移到minaimpl内部实现
//		BasicNetService子类可以以池的形式实现，此方法也不能保证所有的session都为有效链接。
//		详见 #com.net.minaimpl.KeepAlive
//		ServerSession session = getSession();
//		
//		if (session != null)
//		{
//			Heartbeat heartbeat = new Heartbeat();			
//			
//			if (session.send(heartbeat))
//			{
//				heartbeat_counter_.incrementAndGet();
//				return new AtomicReference<MessageHeader>(heartbeat);
//			}
//		}
//		
//		return null;
//	}
	
    /**
     * 发送并监听返回
     * @param <T>
     * @param message
     * @param timeout
     * @param listeners
     * @return
     */
	final public AtomicReference<MessageHeader> sendRequest(MessageHeader message, long timeout, WaitingListener<?, ?> ... listeners)
    {
    	if (System.currentTimeMillis() - LastCleanRequestTime > DropRequestTimeOut) {        	
    		LastCleanRequestTime = System.currentTimeMillis();
    		cleanRequestAndNotify();
    	}
    	
		Request request = new Request(message, timeout, listeners);
    	WaitingListeners.put(request.getPacketNumber(), request);

    	for (WaitingListener<?,?> l : listeners) {
    		onListeningRequest(message, l);
    	}
		request.run();
		
		return request;
	}
    
    /**
     * 发送并监听返回
     * @param message
     * @param listeners
     */
    final public AtomicReference<MessageHeader> sendRequest(MessageHeader message, WaitingListener<?,?> ... listeners) 
    {
    	return sendRequest(message, 0, listeners);
	}
    
    /**
     * 强制移除所有等待中的请求
     * @param type
     */
	final public void clearRequests() 
	{
		synchronized (WaitingListeners) {
			for (Integer pnum : new ArrayList<Integer>(WaitingListeners.keySet())) {
				WaitingListeners.remove(pnum);
			}
		}
	}

	/**
	 * 得到网络交互延迟时间
	 * @return
	 */
	final public int getPing() 
	{
		return request_response_ping.get();
	}
	
	/*
	 * 得到底层使用的线程池
	 */
	final public ThreadPool getThreadPool()
	{
		return this.thread_pool;
	}
	
//	-------------------------------------------------------------------------------------------------------------
    
	AtomicReference<ScheduledFuture<?>> schedule_clean_task				= new AtomicReference<ScheduledFuture<?>>();
	AtomicReference<ScheduledFuture<?>> schedule_clean_task_fix_rate	= new AtomicReference<ScheduledFuture<?>>();
	
	final public ScheduledFuture<?> scheduleCleanTask(ThreadPool tp, long time_ms) 
	{
		synchronized (schedule_clean_task) {
			ScheduledFuture<?> old = schedule_clean_task.get();
			if (old != null) {
				old.cancel(false);
			}
			schedule_clean_task.set(tp.schedule(new Runnable() {
				@Override
				public void run() {
					cleanRequestAndNotify();
				}
			}, time_ms));
			return schedule_clean_task.get();
		}
	}
	
	final public ScheduledFuture<?> scheduleCleanTaskFixRate(ThreadPool tp, int period_ms) 
	{
		synchronized (schedule_clean_task_fix_rate) {
			ScheduledFuture<?> old = schedule_clean_task_fix_rate.get();
			if (old != null) {
				old.cancel(false);
			}
			schedule_clean_task_fix_rate.set(tp.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					cleanRequestAndNotify();
				}
			}, period_ms, period_ms));
			return schedule_clean_task_fix_rate.get();
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------
//	notify
    
	/**
	 * 添加一个用于主动监听服务器端的消息的监听器
	 * @param message_type
	 * @param listener
	 */
	final public void registNotifyListener(Class<? extends MessageHeader> message_type, NotifyListener<?> listener) 
	{
		synchronized (notifies_lock) {
			HashSet<NotifyListener<?>> notifys = notifies_map.get(message_type);
			if (notifys == null) {
				notifys = new HashSet<NotifyListener<?>>();
				notifies_map.put(message_type, notifys);
			}
			notifys.add(listener);
		}
		if (!UnhandledMessages.isEmpty()) {
			ArrayList<Protocol> removed = null;
			for (Entry<Protocol, MessageHeader> unotify : UnhandledMessages.entrySet()) {
				if (tryReceivedNotify(unotify.getValue())) {
					if (removed == null) {
						removed = new ArrayList<Protocol>(UnhandledMessages.size());
					}
					removed.add(unotify.getKey());
				}
			}
			if (removed != null) {
				for (Protocol unotify : removed) {
					UnhandledMessages.remove(unotify);
				}
			}
		}
	}
	
	/**
	 * 删除一个用于主动监听服务器端的消息的监听器
	 * @param message_type
	 * @param listener
	 */
	final public void unregistNotifyListener(Class<? extends MessageHeader> message_type, NotifyListener<?> listener) 
	{
		synchronized (notifies_lock){
			HashSet<NotifyListener<?>> notifys = notifies_map.get(message_type);
			if (notifys != null) {
				notifys.remove(listener);
			}
		}
	}
	
	/**
	 * 清理所有用于主动监听服务器端的消息的监听器
	 * @param message_type
	 */
	final public void clearNotifyListener(Class<? extends MessageHeader> message_type) 
	{
		synchronized (notifies_lock)
		{
			notifies_map.remove(message_type);
		}
	}
	
	final public void clearNotifyListeners()
	{
		synchronized (notifies_lock)
		{
			notifies_map.clear();
		}
	}

//	----------------------------------------------------------------------------------------------------------------------------

//  -------------------------------------------------------------------------------------------------------------
  
    /**
     * 立刻清理所有未响应的请求和囤积的未知消息
     */
    final public void cleanRequestAndNotify() 
    {
//    	System.err.println("waiting listeners : " + WaitingListeners.size());
    	
    	try{
    		for (Integer pnum : new ArrayList<Integer>(WaitingListeners.keySet())) {
        		Request req = WaitingListeners.get(pnum);
    			if (req != null && req.isDroped()) {
    				WaitingListeners.remove(pnum);
    				req.timeout();
    				log.error("drop a timeout request : " + req);
    			}
    		}
    	}
    	catch (Exception err){
    		log.error(err.getMessage(), err);
    	}
    	
		try {
			if (!UnhandledMessages.isEmpty()) {
				ArrayList<Protocol> removed = null;
				for (Protocol unotify : UnhandledMessages.keySet()) {
					if (System.currentTimeMillis() - unotify.getReceivedTime() > DropRequestTimeOut) {
						if (removed == null) {
							removed = new ArrayList<Protocol>(UnhandledMessages.size());
						}
						removed.add(unotify);
					}
				}
				if (removed != null) {
					for (Protocol unotify : removed) {
						UnhandledMessages.remove(unotify);
						log.info("drop a unhandled notify : " + unotify);
					}
				}
			}
		} catch (Exception err) {
			log.error(err.getMessage(), err);
		}
    }
    
    public void clearUnhandledNotify(Class<?> notify_type)
    {
		try 
		{
			if (!UnhandledMessages.isEmpty()) 
			{
				ArrayList<Protocol> removed = null;
				
				for (Entry<Protocol, MessageHeader> unotify : UnhandledMessages.entrySet()) 
				{
					Protocol protocol = unotify.getKey();
					MessageHeader msg = unotify.getValue();
					
					if (notify_type.isInstance(msg)) 
					{
						if (removed == null)
							removed = new ArrayList<Protocol>(UnhandledMessages.size());
						removed.add(protocol);
					}
				}
				if (removed != null) 
				{
					for (Protocol unotify : removed) 
					{
						UnhandledMessages.remove(unotify);
						log.info("clear a unhandled notify : " + unotify);
					}
				}
			}
		} 
		catch (Exception err) 
		{
			log.error(err.getMessage(), err);
		}
    }
    
//    -------------------------------------------------------------------------------------------------------------------
    
	final private void processReceiveSessionMessage(ServerSession session, Protocol protocol, MessageHeader message) 
	{
		try {
			onReceivedMessage(session, message);
		} catch (Exception err) {
			log.error(err.getMessage(), err);
		}
		if (tryReceivedNotify(message)) {
			return;
		} else if (tryReceivedResponse(protocol, message)) {
			return;
		} else if (!tryPushUnhandledNotify(protocol, message)) {
			log.error("handle no listener message : " + message);
		}
	}
	
	final private void processReceiveChannelMessage(ServerSession session, Protocol protocol, MessageHeader message) 
	{
		try {
			onReceivedMessage(session, message);
		} catch (Exception err) {
			log.error(err.getMessage(), err);
		}
		if (tryReceivedNotify(message)) {
			return;
		} else if (!tryPushUnhandledNotify(protocol, message)) {
			log.error("handle no listener channel message : " + message);
		}
	}

//	----------------------------------------------------------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	final private boolean tryReceivedNotify(MessageHeader message) 
	{
		synchronized (notifies_lock)
		{
			HashSet<NotifyListener<?>> notifys = notifies_map.get(message.getClass());
			if (notifys != null && !notifys.isEmpty()) {
				for (NotifyListener notify : notifys) {
					notify.notify(this, message);
				}
				return true;
			}
		}
    	return false;
	}
	
	final private boolean tryReceivedResponse(Protocol protocol, MessageHeader message)
	{
		Request request = WaitingListeners.remove(protocol.getPacketNumber());
    	if (request != null) {
    		request.messageResponsed(protocol, message);    	
    		return true;
    	}
    	return false;
	}
	
	final private boolean tryPushUnhandledNotify(Protocol protocol, MessageHeader message)
	{
		if (protocol.getPacketNumber() == 0) {
			UnhandledMessages.put(protocol, message);
			return true;
		}
    	return false;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------
	
	protected void onListeningRequest(MessageHeader request, WaitingListener<?,?> listeners){}
	
	protected void onConnected(ServerSession session) {}
	
	protected void onDisconnected(ServerSession session, boolean graceful, String reason) {}

	protected void onJoinedChannel(ClientChannel channel) {}
    
	protected void onLeftChannel(ClientChannel channel) {}
  
	protected void onReceivedMessage(ServerSession session, MessageHeader message){}
	
	protected void onSentMessage(ServerSession session, MessageHeader message){}
//	----------------------------------------------------------------------------------------------------------------------------
	

	private class SimpleClientListenerImpl implements ServerSessionListener
	{
		public void connected(ServerSession session) 
		{
			log.info("reconnected : " + session);
			onConnected(session);
		}
		
	    public void disconnected(ServerSession session, boolean graceful, String reason) 
	    {
	    	log.info("disconnected : " + (graceful? "graceful" : "not graceful") + " : " + reason);
			onDisconnected(session, graceful, reason);
			ScheduledFuture<?> old_1 = schedule_clean_task_fix_rate.getAndSet(null);
			ScheduledFuture<?> old_2 = schedule_clean_task.getAndSet(null);
			if (old_1 != null) {
				old_1.cancel(false);
			}
			if (old_2 != null) {
				old_2.cancel(false);
			}
			if (thread_pool != null) {
				scheduleCleanTask(thread_pool, DropRequestTimeOut);
			}
	    }
	    
	    public void joinedChannel(ClientChannel channel) 
	    {
	    	log.info("joined channel : \"" + channel.getID() + "\"");
			onJoinedChannel(channel);
	    }

	    public void leftChannel(ClientChannel channel) 
	    {
	    	log.info("left channel : \""  + channel.getID() + "\"");
	        onLeftChannel(channel);	
	    }
	    
	    public void receivedMessage(ServerSession session, Protocol protocol, MessageHeader message)
	    {
			if (message != null) {
				if (thread_pool!=null) {
					thread_pool.executeTask(new ReceiveTask(session, protocol, message));
				} else {
					processReceiveSessionMessage(session, protocol, message);
				}
			} else {
				log.error("handle null message !");
			}
	    }
	    
	    public void receivedChannelMessage(ClientChannel channel, Protocol protocol, MessageHeader message)
	    {
			if (message != null) {
				if (thread_pool!=null) {
					thread_pool.executeTask(new ReceiveChannelTask(channel.getSession(), protocol, message));
				} else {
					processReceiveChannelMessage(channel.getSession(), protocol, message);
				}
			} else {
				log.error("handle null channel message !");
			}
		}
	    
	    @Override
	    public void sentMessage(ServerSession session, Protocol protocol, MessageHeader message) 
	    {
	    	onSentMessage(session, message);
	    }
	    
		private class ReceiveTask implements Runnable
		{
			final MessageHeader message;
			final ServerSession session;
			final Protocol protocol;
			
			public ReceiveTask(ServerSession session, Protocol protocol, MessageHeader message)
			{
				this.message = message;
				this.session = session;
				this.protocol = protocol;
			}
			
			@Override
			public void run() 
			{
				try {
					processReceiveSessionMessage(session, protocol, message);
				} catch (Throwable err) {
					err.printStackTrace();
				}
			}
		}
		
		private class ReceiveChannelTask implements Runnable
		{
			final MessageHeader message;
			final ServerSession session;
			final Protocol 		protocol;
			
			public ReceiveChannelTask(ServerSession session, Protocol protocol, MessageHeader message) 
			{
				this.message = message;
				this.session = session;
				this.protocol = protocol;
			}
			
			@Override
			public void run() 
			{
				try {
					processReceiveChannelMessage(session, protocol, message);
				} catch (Throwable err) {
					err.printStackTrace();
				}
			}
		}
	}


//	-------------------------------------------------------------------------------------------------

//	-------------------------------------------------------------------------------------------------
	
	
	
//	-------------------------------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	private class Request extends AtomicReference<MessageHeader> implements Runnable
	{
		private static final long 			serialVersionUID = 1L;
		
		final MessageHeader 				Message;
		final WaitingListener[]				Listeners;
		final long 							SendTimeOut;
		final int							PacketNumber;
		long								SendTime;
		
		private Request(MessageHeader msg, long timeout, WaitingListener ... listeners)
		{
			if (SendedPacks.get() == 0) {
				SendedPacks.incrementAndGet();
			}
			this.PacketNumber 	= SendedPacks.getAndIncrement();
			this.Message 		= msg;
			this.SendTimeOut 	= timeout > 0 ? timeout : 0;
			this.Listeners 		= listeners;
		}
		
		public int getPacketNumber() 
		{
			return PacketNumber;
		}
		
		public void run () 
		{
//			System.out.println("request " + this);
			if (SendTimeOut > 0) {
				synchronized (this) {
					SendTime = System.currentTimeMillis();
					getSession().sendRequest(PacketNumber, Message);
					try {
						wait(SendTimeOut);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				getSession().sendRequest(PacketNumber, Message);
			}
		}
		
		private void messageResponsed(Protocol protocol, MessageHeader response) 
		{
			set(response);
			request_response_ping.set((int)(System.currentTimeMillis() - SendTime));
			if (SendTimeOut > 0) {
				synchronized (this){
					notify();
				}
			}
			for (WaitingListener wait : Listeners) {
				if (wait != null) {
					wait.response(BasicNetService.this, Message, response);
				}
			}
		}
		
		private void timeout() 
		{
			for (WaitingListener wait : Listeners) {
				if (wait != null) {
					wait.timeout(BasicNetService.this, Message, SendTime);
				}
			}
		}
				
		protected boolean isDroped() 
		{
			return SendTime + SendTimeOut + DropRequestTimeOut < System.currentTimeMillis();
		}
		
		public String toString() 
		{
			return "Request [" + getPacketNumber() + "] " + Message;
		}
		
	}
	
}
