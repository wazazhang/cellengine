package com.cell.gameedit.object
{
	import com.cell.gameedit.SetObject;
	
	public class MapSet implements SetObject
	{
		public var Index			: int;
		public var Name 			: String;
		
		public var ImagesName		: String;
		
		public var LayerCount		: int;
		public var XCount			: int;
		public var YCount			: int;
		public var CellW			: int;
		public var CellH			: int;
		
		/** int[] */
		public var TileID			: Array;
		/** int[] */
		public var TileTrans		: Array;
		/** int[][] */
		public var Animates			: Array;
//		/** int[][] */
//		public var TerrainScene2D	: Array;
		/**int[][][]*/
		public var LayersScene2D	: Array;
		
		/** int[] */
		public var BlocksType		: Array;
		/** int[] */
		public var BlocksMask		: Array;
		/** int[] */
		public var BlocksX1			: Array;
		/** int[] */
		public var BlocksY1			: Array;
		/** int[] */
		public var BlocksX2			: Array;
		/** int[] */
		public var BlocksY2			: Array;
		/** int[] */
		public var BlocksW			: Array;
		/** int[] */
		public var BlocksH			: Array;
		
//		/** int[][] */
//		public var TerrainBlock2D : Array;
		/**int[][][]*/
		public var LayersBlock2D	: Array;
		
		/**String[]*/
		public var AppendData	: Array;

		
		
		public function getIndex() : int {
			return Index;
		}
		
		public function getName() : String {
			return Name;
		}
		
		public function getLayerImagesIndex(layer:int, x:int, y:int, part:int) : int
		{
			return TileID[Animates[LayersScene2D[layer][y][x]][part]];
		}
		
		public function getLayerTrans(layer:int, x:int, y:int, part:int) : int
		{
			return TileTrans[Animates[LayersScene2D[layer][y][x]][part]];
		}
		
	}

}
