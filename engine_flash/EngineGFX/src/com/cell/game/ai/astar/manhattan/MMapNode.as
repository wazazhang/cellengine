package com.cell.game.ai.astar.manhattan
{
	import com.cell.game.ai.astar.AbstractAstarMapNode;

	internal class MMapNode implements AbstractAstarMapNode
	{
		
		var 		mmap:MMap;
		var 		X:int;
		var 		Y:int;
		var		static_flag:Boolean;
		
		var nexts:Vector.<AbstractAstarMapNode> = new Vector.<AbstractAstarMapNode>(8);
		
		
		public function MMapNode(map:MMap,x:int,y:int)
		{
			this.mmap			= map;
			this.X 				= x;
			this.Y 				= y;
			this.static_flag	= mmap.map.getFlag(X, Y);
		}
		public function getDistance(target:AbstractAstarMapNode):int
		{
			// TODO Auto Generated method stub
			var t:MMapNode = target as MMapNode;
			var ndx:int = X - t.X;
			var ndy:int = Y - t.Y;
			return (Math.abs(ndx) + Math.abs(ndy));
		}
		
		public function getNexts():Vector.<AbstractAstarMapNode>
		{
			// TODO Auto Generated method stub
			return nexts;
		}
		
		public function getPriority(target:AbstractAstarMapNode):int
		{
			// TODO Auto Generated method stub
			var t:MMapNode =  target as MMapNode;
			if (X != t.X && Y != t.Y) {
				return 14;
			}
			return 10;
		}
		
		public function testCross(father:AbstractAstarMapNode):Boolean
		{
			// TODO Auto Generated method stub
			if (mmap.is_static) {
				return static_flag == false;
			} else {
				if (mmap.map.getFlag(X, Y) != false) {
					return false;
				}
				if (mmap.is_edge) {
					var t:MMapNode = father as MMapNode;
					var ndx:int = X - t.X;
					var ndy:int = Y - t.Y;
					if (ndx!=0 && ndy!=0) {
						var ta:MMapNode	= mmap.all_nodes_map[Y][t.X];
						var tb:MMapNode = mmap.all_nodes_map[t.Y][X];
						if (ta.static_flag!=false || tb.static_flag!=false ) {
							return false;
						}
					}
				}
				return true;
			}
		}
		
	}
}