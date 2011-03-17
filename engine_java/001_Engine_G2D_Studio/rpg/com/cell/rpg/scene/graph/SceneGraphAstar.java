package com.cell.rpg.scene.graph;

import com.cell.game.ai.pathfind.AbstractAstar;


/**
 * 所有场景的链接图，并提供寻路算法
 * @author WAZA
 *
 */
public class SceneGraphAstar extends AbstractAstar
{
	
	public SceneGraphAstar(SceneGraph map) {
		super(map);
	}

	public SceneGraph getMap() {
		return (SceneGraph)super.getMap();
	}
	
//	public Collection<SceneGraphNode> findPath(SceneGraphNode srcNode, SceneGraphNode dstNode) {
//		try{
//			WayPoint wp = super.findPath(srcNode, dstNode);
//			ArrayList<SceneGraphNode> ret = new ArrayList<SceneGraphNode>();
//			for(WayPoint p : wp) {
//				ret.add((SceneGraphNode)p.map_node);
//			}
//			return ret;
//		}catch(Exception err){
//			err.printStackTrace();
//		}
//		return null;
//	}
}
