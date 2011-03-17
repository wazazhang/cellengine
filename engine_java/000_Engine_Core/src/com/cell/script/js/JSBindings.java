package com.cell.script.js;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.script.Bindings;

public class JSBindings extends HashMap<String, Object> implements Bindings
{
	private static final long serialVersionUID = 1L;
	
	public JSBindings() {}
	
	public JSBindings(Map<String, Object> map) {
		super(map);
	}
	
	public JSBindings(Set<Entry<String, Object>> map) {
		for (Entry<String, Object> e : map) {
			put(e.getKey(), e.getValue());
		}
	}
	
}
