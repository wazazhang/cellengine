package com.cell.gameedit.object
{
	import com.cell.gameedit.SetObject;

	public class ImagesSet implements SetObject
	{
		public var Index 		: int;
		public var Name 		: String;
		
		public var Count 		: int;
		/**int[]*/
		public var ClipsX 		: Array;
		/**int[]*/
		public var ClipsY 		: Array;
		/**int[]*/
		public var ClipsW 		: Array;
		/**int[]*/
		public var ClipsH 		: Array;
		/**String[]*/
		public var ClipsKey 	: Array;
		
		public var CustomOut	: String;
		
		public var CustomExt	: String;
		
		public var AppendData	: String;
		
		public function getIndex() : int {
			return Index;
		}
		
		public function getName() : String {
			return Name;
		}
		
	}
}
