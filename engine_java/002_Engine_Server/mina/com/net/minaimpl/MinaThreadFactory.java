package com.net.minaimpl;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MinaThreadFactory implements ThreadFactory 
{
	private static MinaThreadFactory instance = new MinaThreadFactory();
	public static MinaThreadFactory getInstance() {
		return instance;
	}
	
	
	private ThreadGroup group;
	private AtomicInteger threadNumber = new AtomicInteger(1);
	public MinaThreadFactory() {
		this.group = new ThreadGroup("Net");
	}
	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r);
		t.setName("Net-" + threadNumber.getAndIncrement());
		return t;
	}
	public ThreadGroup getGroup() {
		return group;
	}
	
}
