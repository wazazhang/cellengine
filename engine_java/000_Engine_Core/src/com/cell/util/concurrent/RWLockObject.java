package com.cell.util.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 加了读锁，不可以加写锁，但是可以再加读锁<br>
 * 加了写锁，不可以加读锁，也不可以再加写锁<br>
 * @author WAZA
 */
public class RWLockObject
{
	protected ReentrantReadWriteLock 	lock 	= new ReentrantReadWriteLock();
	protected Lock 					readLock 	= null;
	protected Lock 					writeLock 	= null;
	
	public RWLockObject() 
	{
		readLock 	= lock.readLock();//获得读的锁
		writeLock 	= lock.writeLock();//获得对应的写的锁
	}
	
	@Override
	protected void finalize() throws Throwable {
		readLock.unlock();
		writeLock.unlock();
		super.finalize();
	}
	
	public static void main(String[] args) throws Exception
	{
		RWLockObject lo = new RWLockObject();

		{
			lo.writeLock.lock();
			lo.readLock.lock();
			
			System.out.println("doing");
	
			lo.readLock.unlock();
			lo.writeLock.unlock();
		}
		{
			lo.readLock.lock();
			lo.writeLock.lock();
			
			System.out.println("doing");

			lo.writeLock.unlock();
			lo.readLock.unlock();
		}
	}
}
