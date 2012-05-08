package com.cell.rpg.scene.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.cell.game.ai.pathfind.AbstractAstarMap;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.scene.graph.SceneGraphNode.LinkInfo;


/**
 * 所有场景的链接图，并提供寻路算法
 * @author WAZA
 *
 */
public class SceneGraph implements AbstractAstarMap<SceneGraphNode>
{
	final LinkedHashMap<Integer, SceneGraphNode> scene_map;
	
	public SceneGraph(Collection<Scene> scenes) 
	{
		scene_map = new LinkedHashMap<Integer, SceneGraphNode>(scenes.size());
		
		for (Scene scene : scenes) {
			SceneGraphNode node = new SceneGraphNode(scene);
			scene_map.put(scene.getIntID(), node);
		}
		
		for (SceneGraphNode node : scene_map.values()) {
			for (LinkInfo info : node.next_links.values()) {
				SceneGraphNode next = scene_map.get(info.next_scene_id);
				node.nexts.add(next);
			}
		}
	}
	
	@Override
	public boolean containsNode(SceneGraphNode node) {
		return scene_map.containsKey(node.scene_id);
	}
	
	@Override
	public Collection<SceneGraphNode> getAllNodes() {
		return scene_map.values();
	}
	
	@Override
	public int getNodeCount() {
		return scene_map.size();
	}
	
	public SceneGraphNode getSceneGraphNode(Integer scene_id) {
		return scene_map.get(scene_id);
	}
}
