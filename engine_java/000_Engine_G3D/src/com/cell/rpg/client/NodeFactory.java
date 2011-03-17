package com.cell.rpg.client;

@Deprecated
public abstract class NodeFactory
{
	static private NodeFactory s_Instance;
	static public NodeFactory getInstance() {
		return s_Instance;
	}
	
	
	protected NodeFactory() {
		s_Instance = this;
	}
	
	

}
