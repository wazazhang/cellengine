package com.cell.game.ai.astar.manhattan
{
	public class MWayPoint
	{
		public var BX:int;
		public var BY:int;
		public var X:int; 
		public var Y:int;
		
		public var Next:MWayPoint;
		
		public var	Data:Object;
		
		public function MWayPoint(bx:int,by:int,cellw:int,cellh:int)
		{
			BX = bx;
			BY = by;
			X = BX*cellw + cellw/2;
			Y = BY*cellh + cellh/2;
		}
		
		public function setWayPointn(x:int, y:int) : void
		{
			X = x;
			Y = y;
		}
		
		public function toVector() : Vector.<MWayPoint> {
			var	v:Vector.<MWayPoint> = new Vector.<MWayPoint>;
			var wp :MWayPoint;
			wp = this;
			while (wp != null) {
				v.push(wp);
				wp = wp.Next;
			}
			return v;
		}		
	}
}