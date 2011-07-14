package com.cell.gfx
{
	import com.cell.gameedit.Output;
	import com.cell.gameedit.object.ImagesSet;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;

	public class CImages
	{
		public const ANGLE_90		: Number = Math.PI / 2;
		public const ANGLE_270		: Number = Math.PI * 3 / 2;
		
		/** None Flip Rotate */
		public const TRANS_NONE 	: int = 0;
		
		/** Flip Vertical */
		public const TRANS_V 		: int = 1;
		
		/** Flip Horizental */
		public const TRANS_H 		: int  = 2;
		
		/** Flip Horizental and Vertical */
		public const TRANS_HV		: int = 3;
		
		/** anticlockwise rotate 90 angle */
		public const TRANS_90 		: int = 6;
		
		/** anticlockwise rotate 270 angle */
		public const TRANS_270 		: int = 5;
		
		/** first anticlockwise rotate 90 angle , second flip Horizental */
		public const TRANS_H90 		: int = 4;
		
		/** first anticlockwise rotate 90 angle , second flip Vertical */
		public const TRANS_V90 		: int = 7;
		
		/** 180 Rotate */
		public const TRANS_180 		: int = 3; 
		
		
//		-------------------------------------------------------------------------------------------------
	
		protected var tiles : Array;
		
		public function CImages()
		{
		}

		public function getImage(index:int) : BitmapData
		{
			return (tiles[index] as BitmapData);
		}
		
		public function getWidth(index:int) : int
		{
			return (tiles[index] as BitmapData).width;
		}
		
		public function getHeight(index:int) : int
		{
			return (tiles[index] as BitmapData).height;
		}
		
		public function clone() : CImages
		{
			var ret : CImages = new CImages();
			for each (var tile:BitmapData in this.tiles) {
				ret.tiles.push(tile.clone());
			}
			return ret;
		}
		
	}
}