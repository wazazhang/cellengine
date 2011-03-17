package com.cell.util.event;

import java.util.concurrent.ConcurrentHashMap;

public class EventListenerMap {
	
	ConcurrentHashMap<Class<?>, EventListeners<?>> event_map = new ConcurrentHashMap<Class<?>, EventListeners<?>>();
	
	@SuppressWarnings("unchecked")
	public<T extends EventListener> void putListener(Class<T> type, T listener) {
		synchronized (event_map) {
			EventListeners<T> listeners = (EventListeners<T>)event_map.get(type);
			if (listeners == null) {
				listeners = new EventListeners<T>(type);
				event_map.put(type, listeners);
			}
			listeners.addListener(listener);
		}
	}
	
	@SuppressWarnings("unchecked")
	public<T extends EventListener> EventListeners<T> getListeners(Class<T> type) {
		EventListeners<T> listeners = (EventListeners<T>)event_map.get(type);
		return listeners;
	}
	
	
	
}
