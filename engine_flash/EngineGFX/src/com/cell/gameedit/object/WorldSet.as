package com.cell.gameedit.object
{
	import com.cell.gameedit.SetObject;

	public class WorldSet implements SetObject
	{
		public var Index 		: int;
		public var Name 		: String;
		
		public var GridXCount	: int;
		public var GridYCount	: int;
		public var GridW		: int;
		public var GridH		: int;
		public var Width		: int;
		public var Height		: int;
		
		/** SpriteObject[] */
		public var Sprs 		: Array;
		/** MapObject[] */
		public var Maps 		: Array;
		/** WaypointObject[] */
		public var WayPoints 	: Array;
		/** RegionObject[] */
		public var Regions		: Array;
	
		public var Data			: String;
		
		/** int[][] */
		public var Terrian		: Array;
	
		
		
		public function getIndex() : int {
			return Index;
		}
		
		public function getName() : String {
			return Name;
		}
	
		public function getTerrainCell( grid_x:int,  grid_y:int) : int {
			return Terrian[grid_x][grid_y];
		}
		
	}
}
