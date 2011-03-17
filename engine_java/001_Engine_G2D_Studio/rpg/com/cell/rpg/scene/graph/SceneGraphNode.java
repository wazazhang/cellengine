package com.cell.rpg.scene.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import com.cell.game.ai.pathfind.AbstractAstarMapNode;
import com.cell.game.ai.pathfind.AbstractAstar.WayPoint;
import com.cell.math.MathVector;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.scene.SceneUnit;
import com.cell.rpg.scene.ability.ActorTransport;

public class SceneGraphNode implements AbstractAstarMapNode
{

//	--------------------------------------------------------------------------------------------

	final public int		scene_id;
	final public String		scene_name;
	final public int		scene_group;
	final public float		x;
	final public float		y;
	final public float		width;
	final public float		height;
	
	/** 
	 * key is next scene id, <br>
	 * value is next link info*/
	final public LinkedHashMap<Integer, LinkInfo>
							next_links 		= new LinkedHashMap<Integer, LinkInfo>(1);
	
	final ArrayList<AbstractAstarMapNode>		
							nexts 			= new ArrayList<AbstractAstarMapNode>();

//	--------------------------------------------------------------------------------------------

	public SceneGraphNode(Scene scene) 
	{
		this.scene_id	= scene.getIntID();
		this.scene_name	= scene.name;
		this.scene_group= scene.group;
		this.x			= scene.scene_node.x;
		this.y			= scene.scene_node.y;
		this.width		= scene.scene_node.width;
		this.height		= scene.scene_node.height;
		for (SceneUnit unit : scene.scene_units) {
			ActorTransport tp = unit.getAbility(ActorTransport.class);
			if (tp != null) {
				try{
					LinkInfo next = new LinkInfo(
							this.scene_id,
							unit.id,
							Integer.parseInt(tp.next_scene_id), 
							tp.next_scene_object_id); 
					next_links.put(next.next_scene_id, next);
				}catch(Exception err) {
					System.err.println(
							"scene ["+scene_name+"("+scene_id+")] link error at ["+ unit.name+"("+ unit.id +")]");
					err.printStackTrace();
				}
			}
		}
		nexts.ensureCapacity(next_links.size());
	}
	
	@Override
	public Collection<AbstractAstarMapNode> getNexts() {
		return nexts;
	}

	@Override
	public boolean testCross(AbstractAstarMapNode father) {
		return true;
	}

	@Override
	public int getDistance(AbstractAstarMapNode target) {
		SceneGraphNode t = (SceneGraphNode)target;
		return (int)MathVector.getDistance(x, y, t.x, t.y);
	}
	
	@Override
	public int getPriority(AbstractAstarMapNode target) {
		return 10;
	}

//	--------------------------------------------------------------------------------------------

	public LinkInfo getLinkTransport(SceneGraphNode next) {
		if (next != null) {
			for (LinkInfo info : next_links.values()) {
				if (info.next_scene_id.equals(next.scene_id)) {
					return info;
				}
			}
		}
		return null;
	}
	public LinkInfo getLinkTransport(WayPoint next) {
		if (next != null) {
			return getLinkTransport((SceneGraphNode)next.map_node);
		}
		return null;
	}
	
	
//	--------------------------------------------------------------------------------------------
	
	public static class LinkInfo
	{
		final public Integer	this_scene_id;
		final public String		this_scene_obj_name;
		final public Integer	next_scene_id;
		final public String		next_scene_obj_name;
		
		public LinkInfo(
				int this_scene_id,
				String this_scene_obj, 
				int next_scene_id, 
				String next_scene_obj) {
			this.this_scene_id			= this_scene_id;
			this.this_scene_obj_name	= this_scene_obj;
			this.next_scene_id			= next_scene_id;
			this.next_scene_obj_name	= next_scene_obj;
		}
	}
}
