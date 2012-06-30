package com.cell.game.ai.astar
{
	import com.cell.game.ai.astar.manhattan.MMapNode;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ArrayList;

	public interface AbstractAstarMap
	{
		/**
		 * 测试是否包含该节点
		 * @param node
		 * @return
		 */
		function 	containsNode(node:AbstractAstarMapNode):Boolean;
		
		/**
		 * 得到所有的节点
		 * @return
		 */
		function getAllNodes():Object;
		
		/**
		 * 得到节点数量
		 * @return
		 */
		function getNodeCount():int;

	}
}