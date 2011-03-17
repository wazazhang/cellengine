package com.cell.util.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

public class EventListeners<T extends EventListener> implements Iterable<T>
{
	private static final long serialVersionUID = 1;

	ReentrantLock	lock = new ReentrantLock();
	
	final Class<T>	listener_class;
	
	ArrayList<T>	list = new ArrayList<T>();
	
	
	public EventListeners(Class<T> cls) {
		this.listener_class = cls;
	}
	
	public Class<T> getListenerClass() {
		return listener_class;
	}
	
	public void addListener(T listener) {
		lock.lock();
		try{
			if (listener!=null) {
				list.add(listener);
			}
		}finally{
			lock.unlock();
		}
	}
	
	public void removeEventListener(T listener) {
		lock.lock();
		try{
			list.remove(listener);
		}finally{
			lock.unlock();
		}
	}

	public Iterator<T> iterator() {
		return list.iterator();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
}
