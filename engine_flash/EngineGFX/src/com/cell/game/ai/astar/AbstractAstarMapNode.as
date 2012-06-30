package com.cell.game.ai.astar
{
	import mx.collections.ArrayCollection;

	public interface AbstractAstarMapNode
	{
		/**
		 * 得到所有通向的下一个节点
		 * 
		 * @return
		 */
		function getNexts():Vector.<AbstractAstarMapNode>;
		
		/**
		 * 测试是否可以过
		 * 
		 * @param father
		 *            父节点
		 * @return
		 */
		function testCross(father:AbstractAstarMapNode):Boolean;
		
		/**
		 * 得到和目标节点的距离
		 * 
		 * @param target
		 * @return
		 */
		function getDistance(target:AbstractAstarMapNode):int;
		
		/**
		 * 得到和目标节点的优先链接值
		 * @param target
		 * @return
		 */
		function getPriority(target:AbstractAstarMapNode):int;
	}
}