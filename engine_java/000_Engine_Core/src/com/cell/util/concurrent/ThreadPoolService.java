package com.cell.util.concurrent;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;


public interface ThreadPoolService
{
	/**
	 * 异步执行一个任务
	 * @param r
	 */
	public Future<?> executeTask(Runnable r);
	
	/**
	 * 计划执行一个任务
	 * @param r
	 * @param delay 延后多少毫秒
	 * @return
	 */
	public ScheduledFuture<?> schedule(Runnable r, long delay);

	/**
	 * 计划执行一个任务，并周期执行
	 * @param r
	 * @param initial 延后多少毫秒开始
	 * @param period 开始后，每隔多长时间重新执行
	 * @return
	 */
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable r, long initial, long period);

	
}