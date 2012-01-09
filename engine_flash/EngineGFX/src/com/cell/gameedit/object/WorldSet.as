package com.cell.gameedit.object
{
	import com.cell.gameedit.SetObject;
	import com.cell.util.Map;

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
		
		/** [Index, SpriteObject] */
		public var Sprs 		: Map	= new Map();
		/** [Index, MapObject] */
		public var Maps 		: Map	= new Map();
		/** [Index, WaypointObject] */
		public var WayPoints 	: Map	= new Map();
		/** [Index, RegionObject] */
		public var Regions		: Map	= new Map();
	
		public var Data			: String;
		
		/** int[x][y] */
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
