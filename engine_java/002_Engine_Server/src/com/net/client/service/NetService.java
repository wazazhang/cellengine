package com.net.client.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CObject;
import com.cell.util.concurrent.ThreadPool;
import com.net.MessageHeader;
import com.net.client.ServerSession;


public class NetService extends BasicNetService
{
//	---------------------------------------------------------------------------------------------------------------------------------
	
	final private ServerSession 	Session;
	
	private String					ServerHost;
	private Integer 				ServerPort;
	
//	---------------------------------------------------------------------------------------------------------------------------------

	public NetService(ServerSession session, ThreadPool thread_pool) 
	{
		super(thread_pool);
		this.Session 			= session;
	}
	
	public NetService(ServerSession session) {
		this(session, null);
	}
	
	/**
	 * 得到当前的网络链接套接字
	 * @return
	 */
	public ServerSession getSession() {
		return Session;
	}
	
	/**
	 * 链接到主机并制定超时时间
	 * @param host
	 * @param port
	 * @param timeout
	 * @return
	 */
	public boolean connect(String host, Integer port, Long timeout) {
		if (!Session.isConnected()) {
			log.info("connecting... " + host + ":" + port);
    		try {
    			ServerHost = host;
    			ServerPort = port;
    			Session.connect(host, port, timeout, getSessionListener());
    			return true;
    		} catch (Exception e) {
    			e.printStackTrace();
    			return false;
    		}
    	}
    	return true;
	}

	/**
	 * 重新链接到主机
	 * @return
	 */
	public boolean reconnect(Long timeout) 
	{
		if (!Session.isConnected()) {
			try {
				Session.connect(ServerHost, ServerPort, timeout, getSessionListener());
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
    	return true;
	}
    
	/**
	 * 是否已链接
	 * @return
	 */
	public boolean isConnected() {
    	if (Session.isConnected()) {
    		return Session.isConnected();
		}else{
			printNotConnectError();
		}
    	return false;
    }
	
	/**
	 * 断开链接
	 * @param force 是否立即断开
	 */
	@Override
	public void close(boolean force) {
    	Session.disconnect(force);
	}
	
	@Override
	public void dispose() {
		Session.dispose();
		
		this.clearNotifyListeners();
	}
	
	final private void printNotConnectError() {
		log.error("session is not connect, please call connect(String host, String port) first !");
	}
	
	
	
	
}
