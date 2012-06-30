package com.cell.game.ai.astar.manhattan
{
	import com.cell.game.ai.astar.AbstractAstar;
	import com.cell.game.ai.astar.AbstractAstarMapNode;
	import com.cell.game.ai.astar.TempMapNode;
	import com.cell.game.ai.astar.WayPoint;
	
	public class AstarManhattan extends AbstractAstar
	{
		public var mmap:MMap ;
		
		var wp:Vector.<WayPoint>;
		var  node_maps:Array;	
		
		public function AstarManhattan(map:AstarManhattanMap,is_static:Boolean,is_edge:Boolean)
		{
			super(new MMap(map, is_static, is_edge));
			
			this.mmap = getMap() as MMap;
			
			//			this.node_map = new TempMapNode[mmap.ycount][mmap.xcount]; 
			// 地图数组的初始化
			this.node_maps = new Array();
			for(var y:int = 0; y < mmap.ycount; y++) 
			{
				var arr_i:Array = new Array();
				for each(var tnode in all_node) {
					var mnode:MMapNode = tnode.data as MMapNode;
					//					node_map[mnode.Y][mnode.X] = tnode;
					arr_i.push(mnode);
				}
				node_maps.push(arr_i);
			}	
			
			//			for (var tnode in all_node) {
			//				var mnode:MMapNode = tnode.data as MMapNode;
			//				node_map[mnode.Y][mnode.X] = tnode;
			//			}
		}
		
		override protected function  getTempMapNode(node:AbstractAstarMapNode):TempMapNode {
			var mnode:MMapNode = node as MMapNode;
			return node_maps[mnode.Y][mnode.X];
		}
		
		public function toWP(start:Vector.<WayPoint>):MWayPoint
		{
			var ret:MWayPoint = null;
			var path:MWayPoint = null;
			for each(var wp in start) {
				var mnpde:MMapNode = wp.map_node as MMapNode;
				if (path != null) {
					path.Next = new MWayPoint(mnpde.X, mnpde.Y, mmap.map.getCellW(), mmap.map.getCellH());
					path = path.Next;
				} else {
					path = new MWayPoint(mnpde.X, mnpde.Y, mmap.map.getCellW(), mmap.map.getCellH());
					ret = path;
				}
			}
			return ret;
		}
		public function findPath_man(sx:int,sy:int,dx:int,dy:int):MWayPoint 
		{
			try{
				wp = super.findPath(node_maps[sy][sx], 
					node_maps[dy][dx]);
				return toWP(wp);
			}catch(err:Error) {
				err.printStackTrace();
			}
			return null;
		}
	}
}
