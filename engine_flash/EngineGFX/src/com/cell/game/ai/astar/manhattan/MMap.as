package com.cell.game.ai.astar.manhattan
{
	import com.cell.game.ai.astar.AbstractAstarMap;
	import com.cell.game.ai.astar.AbstractAstarMapNode;
	import com.cell.util.Map;
	
	import mx.states.OverrideBase;

	internal class MMap implements AbstractAstarMap
	{
		
		public var	map:AstarManhattanMap;
		public var	xcount:int;
		public var ycount:int;
		
		public var is_static:Boolean;
		public var	is_edge:Boolean;
		
		
		public var all_nodes:Vector.<MMapNode>;
		
		public var all_nodes_map:Array;
		
		public var near_table :Array=[
			[-1,-1], [0,-1], [1,-1],
			[-1, 0], /*{0,0}*/[ 1, 0],
			[-1, 1], [ 0, 1], [ 1, 1]
		];
		
		public function MMap(map:AstarManhattanMap, is_static:Boolean,is_edge:Boolean){
		
			this.map		= map;
			this.xcount		= map.getXCount();
			this.ycount		= map.getYCount();
			this.is_static	= is_static;
			this.is_edge	= is_edge;
			
			this.all_nodes 		= new Vector.<MMapNode>(xcount*ycount);
			this.all_nodes_map 	= new Array();

			for (var y:int = 0; y < map.getYCount(); y++) 
			{
				var arr_i:Array = new Array();
				for (var x:int = 0; x< map.getXCount(); x++) 
				{
					var node:MMapNode = new MMapNode(this, x, y);
					arr_i.push(node);
					all_nodes.push(node);
				}
				all_nodes_map.push(arr_i);
			}	

			for(var i:int=0;i<map.getYCount();i++)
			{
				for(var j:int=0;j<map.getXCount();j++)
				{
					var node2:MMapNode = all_nodes_map[i][j];
					for each(var np :Array in near_table)
					{
						try
						{
							var ndx : int= np[0];
							var ndy : int= np[1];
							var near :MMapNode= all_nodes_map[y+ndy][x+ndx];
							if (is_static) {
								if (near.static_flag != false) {
									continue;
								}
								if (is_edge && ndx!=0 && ndy!=0) {
									var ta:MMapNode= all_nodes_map[y][x+ndx];
									var tb:MMapNode = all_nodes_map[y+ndy][x];
									if (ta.static_flag!=false || tb.static_flag!=false ) {
										continue;
									}
								}
							}
							node2.nexts.push(near);
						} 
						catch(error:Error) 
						{
							trace(error.message);
						}
					}
				}
			}
	
		}

		public function containsNode(node:AbstractAstarMapNode):Boolean
		{
			// TODO Auto Generated method stub
			var nodes = node as MMapNode;
			if (nodes.Y < ycount && nodes.Y >= 0) {
				if (nodes.X < xcount && nodes.X >= 0) {
					return true;
				}
			}
			return false;
		}
		
		public function getAllNodes():Object
		{
			// TODO Auto Generated method stub
			return all_nodes;
		}
		
		
		public function getNodeCount():int
		{
			// TODO Auto Generated method stub
			return all_nodes.length;
		}
		
	}
}