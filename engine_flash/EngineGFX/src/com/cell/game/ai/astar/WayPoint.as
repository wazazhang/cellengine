package com.cell.game.ai.astar
{
	final public class WayPoint
	{
		public var map_node:AbstractAstarMapNode;
		
		public var next:WayPoint;
		
		
		public function WayPoint(map_node:AbstractAstarMapNode)
		{
			this.map_node = map_node;
			
		}
		
		public function toVector():Vector.<WayPoint>{
			
			var	v:Vector.<WayPoint> = new Vector.<WayPoint>;
			var wp :WayPoint;
			
			wp=this;
			
			while(true){
				if(wp!=null){
					v.push(wp);
					if(wp.getNext() ==null){
						break;		
						
					}else{
						wp=wp.getNext();
						
					}
					
				}
			}			
			return v;
		}		
		public function getNext():WayPoint
		{
			return next;
		}
	}
}