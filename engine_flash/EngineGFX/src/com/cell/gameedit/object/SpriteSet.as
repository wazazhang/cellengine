package com.cell.gameedit.object
{
	import com.cell.gameedit.SetObject;
	
	public class SpriteSet implements SetObject
	{
		public var Index			: int;
		public var Name 			: String;
		
		public var ImagesName		: String;
		/** short[] */
		public var PartX			: Array;
		/** short[] */
		public var PartY			: Array;
		/** short[] */
		public var PartTileID		: Array;
		/** byte[] */
		public var PartTileTrans	: Array;	
		/** short[][] */
		public var Parts			: Array;
	
		/** int[] */
		public var BlocksMask		: Array;
		/** short[] */
		public var BlocksX1			: Array;
		/** short[] */
		public var BlocksY1			: Array;
		/** short[] */
		public var BlocksW			: Array;
		/** short[] */
		public var BlocksH			: Array;
		/** short[][] */
		public var Blocks			: Array;
	
		public var AnimateCount		: int;
		/** String[] */
		public var AnimateNames		: Array;
		/** short[][] */
		public var FrameAnimate		: Array;
		/** short[][] */
		public var FrameCDMap		: Array;
		/** short[][] */
		public var FrameCDAtk		: Array;
		/** short[][] */
		public var FrameCDDef		: Array;
		/** short[][] */
		public var FrameCDExt		: Array;
		
		public var AppendData	: String;
		
		public function getIndex() : int {
			return Index;
		}
		
		public function getName() : String {
			return Name;
		}
	
		// images[PartTileID[Parts[FrameAnimate[anim][frame]][subpart]]];
		public function getPartImageIndex( anim:int,  frame:int,  subpart:int) : int {
			return PartTileID[Parts[FrameAnimate[anim][frame]][subpart]];
		}
	
		public function getPartTrans( anim:int,  frame:int,  subpart:int) : int {
			return PartTileTrans[Parts[FrameAnimate[anim][frame]][subpart]];
		}
	
		public function getPartX( anim:int,  frame:int,  subpart:int) : int {
			return PartX[Parts[FrameAnimate[anim][frame]][subpart]];
		}
	
		public function getPartY( anim:int,  frame:int,  subpart:int) : int {
			return PartY[Parts[FrameAnimate[anim][frame]][subpart]];
		}
	
	}
}
