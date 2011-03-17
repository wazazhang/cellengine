package com.cell.rpg.client;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;


public abstract class World 
{

	protected HashMap<Integer, String> KeyPressedMap = new HashMap<Integer, String>();
	protected HashMap<Integer, String> KeyReleasedMap = new HashMap<Integer, String>();
	protected HashMap<Integer, String> KeyHoldMap = new HashMap<Integer, String>();
	
	private Hashtable<String, Node> Elements = new Hashtable<String, Node>();
	
	
	public void add(Node node) {
		if (Elements.containsKey(node.Name)){
			try{
				throw new Exception("duplicate node object : " + node.Name);
			}catch(Exception err){
				err.printStackTrace();
			}
		}
		Elements.put(node.Name, node);
		node.Parent = this;
		node.added(this);
	}
	
	public void remove(Node node) {
		Elements.remove(node.Name);
		node.Parent = null;
		node.removed(this);
	}
	
	public void update() {
		Enumeration<Node> nodes = Elements.elements();
		while (nodes.hasMoreElements()) {
			Node node = nodes.nextElement();
			node.update();
		}
	}
	
	
	public boolean isKeyDown(int keyCode) {
		return KeyPressedMap.get(keyCode)!=null;
	}
	
	public boolean isKeyUp(int keyCode) {
		return KeyReleasedMap.get(keyCode)!=null;
	}
	
	public boolean isKeyHold(int keyCode) {
		return KeyHoldMap.get(keyCode)!=null;
	}
	

}
