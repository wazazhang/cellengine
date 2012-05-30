package com.net;


import java.util.Set;

/**
 * Session接口
 * @author zhangyifei
 */
public interface AbstractSession 
{
	/**
	 * 获取SessionID，每个Session都有一个唯一ID
	 * @return
	 */
	public long 			getID();
	
	/**
	 * 判断当前是否已连接
	 * @return
	 */
	public boolean			isConnected();
	
	/**
	 * 主动关闭连接
	 * @param force 是否立刻关闭连接（强制关闭），即不等待队列里的操作完成。
	 * @return
	 */
	public boolean			disconnect(boolean force);
	
	/**
	 * 获取已发送消息包数量
	 * @return
	 */
	public long 			getSentMessageCount() ;
	/**
	 * 获取已接受消息包数量
	 * @return
	 */
	public long 			getReceivedMessageCount () ;
	/**
	 * 获取已发送消息字节数
	 * @return
	 */
	public long 			getSentBytes();
	/**
	 * 获取已接收消息字节数
	 * @return
	 */
	public long 			getReceivedBytes();


	public Object 			getAttribute(Object key);
	public Object 			setAttribute(Object key, Object value);
	public Object 			removeAttribute(Object key);
	public boolean 			containsAttribute(Object key);
	public Set<Object>		getAttributeKeys();
	
	/**
	 * 获取Session的本端地址
	 */
	public String			getLocalAddress();
	
	/**
	 * 获取Session的对端地址
	 */
	public String			getRemoteAddress();

};



