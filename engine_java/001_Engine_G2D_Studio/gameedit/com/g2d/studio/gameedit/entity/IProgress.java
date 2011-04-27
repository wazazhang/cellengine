package com.g2d.studio.gameedit.entity;

import java.util.concurrent.atomic.AtomicInteger;




public interface IProgress 
{
	/**
	 * 设置进度条的最大值
	 * @param prefix 进度描述用的前缀
	 * @param total 最大值
	 */
	public void setMaximum(String prefix, int total);
	
	/**
	 * 设置进度条的进度值
	 * @param prefix 进度描述用的前缀
	 * @param value 进度值
	 */
	public void setValue(String prefix, int value);
	
	
	public void increment();

	public void increment(int count) ;
	/**
	 * 得到进度条的最大值
	 * @return 最大值
	 */
	public int getMaximum();
	
	/**
	 * 得到进度条的当前值
	 * @return 当前值
	 */
	public int getValue();
	

}; // interface IProgress







