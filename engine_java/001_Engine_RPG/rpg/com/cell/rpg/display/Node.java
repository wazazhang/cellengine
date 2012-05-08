package com.cell.rpg.display;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 为组织SceneGraph结构的Node
 * @author WAZA
 *
 */
public abstract class Node implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static enum Type
	{
		SPRITE,
		MAP,
		WORLD,
	}
	
	/**对应CPJ工程*/
	final public 	String		cpj_project_name;	
	/**在CPJ工程中的对象名*/
	final public	String		cpj_object_id;
	/**在CPJ工程内的对象类型*/
	final public	Type		set_object_type;
	
	public	float		x;
	public	float		y;
	public	float		z;
	
	final public ArrayList<Node> sub_nodes = new ArrayList<Node>();
	
	protected Node(String cpj_name, String object_id, Type type)
	{
		cpj_project_name	= cpj_name;	
		cpj_object_id		= object_id;
		set_object_type		= type;
	}
	
	@Override
	public String toString() {
		return super.toString()+"{" +
		cpj_project_name + "," +  cpj_object_id + "," + set_object_type +
		"}";
	}
}
