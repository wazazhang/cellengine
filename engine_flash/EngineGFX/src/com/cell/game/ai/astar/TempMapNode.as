package com.cell.game.ai.astar
{
	public final class TempMapNode
	{
		public var node_index:int;
		
		/**对应的地图数据*/
		public var  data:AbstractAstarMapNode;
		
		/**对应的下一个节点*/
		public var  nexts:Array;
		
		/**每次寻路时，暂存的上一个节点*/
		public var s_father:TempMapNode;
		private var s_g:int= 0;
		private var s_h:int= 0;
		private var s_f:int = 0;
		
		public function TempMapNode(node_indexa:int,data:AbstractAstarMapNode) {
			this.node_index	= node_index;
			this.data		= data;
			this.nexts		= new Array(data.getNexts().length);
		}
		
		public function setFather(father:TempMapNode,target:TempMapNode):void
		{
			this.s_father	= father;
			this.s_g 		= father.s_g + data.getPriority(father.data);
			this.s_h 		= data.getDistance(target.data);
			this.s_f 		= (s_g + s_h) ;
		}
	
	}
}