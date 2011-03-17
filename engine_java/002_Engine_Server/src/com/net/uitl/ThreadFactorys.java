package com.net.uitl;

import java.util.concurrent.ThreadFactory;

public class ThreadFactorys 
{
	public static ThreadFactory IoServiceFactory = new ThreadFactory(){
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setName("IOService");
			t.setDaemon(true);
			return t;
		}
	};
	public static ThreadFactory MessageProcessServiceFactory = new ThreadFactory(){
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setName("MessageProcessService");
			t.setDaemon(true);
			return t;
		}
	};
}
