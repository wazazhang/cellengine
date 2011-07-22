package com.cell.gfx
{
	import flash.geom.Matrix;

	public class Transform
	{
		
		static public const ANGLE_90		: Number = Math.PI / 2;
		static public const ANGLE_270		: Number = Math.PI * 3 / 2;
		
		/** None Flip Rotate */
		static public const TRANS_NONE 		: int = 0;
		
		/** Flip Vertical */
		static public const TRANS_V 		: int = 1;
		
		/** Flip Horizental */
		static public const TRANS_H 		: int  = 2;
		
		/** Flip Horizental and Vertical */
		static public const TRANS_HV		: int = 3;
		
		/** anticlockwise rotate 90 angle */
		static public const TRANS_90 		: int = 6;
		
		/** anticlockwise rotate 270 angle */
		static public const TRANS_270 		: int = 5;
		
		/** first anticlockwise rotate 90 angle , second flip Horizental */
		static public const TRANS_H90 		: int = 4;
		
		/** first anticlockwise rotate 90 angle , second flip Vertical */
		static public const TRANS_V90 		: int = 7;
		
		/** 180 Rotate */
		static public const TRANS_180 		: int = 3; 
		
		
		public static function getMatrix(x:int, y:int, width:int, height:int, transform:int) : Matrix
		{
			var ret : Matrix = new Matrix();
			ret.translate(x, y);
			switch (transform) {
				case TRANS_NONE:
					break;
				case TRANS_90:
					ret.rotate(Math.PI/2);
					break;
				case TRANS_180:
					ret.rotate(Math.PI);
					break;
				case TRANS_270:
					ret.rotate(-Math.PI/2);
					break;
				case TRANS_H:
					ret.scale(-1, 1);
					break;
				case TRANS_V:
					ret.scale(1, -1);
					break;
				case TRANS_HV:
					ret.scale(-1, -1);
					break;
				case TRANS_H90:
					ret.rotate(Math.PI/2);
					ret.scale(-1, 1);
					break;
				case TRANS_V90:
					ret.rotate(Math.PI/2);
					ret.scale(1, -1);
					break;
			}
			return ret;
		}
	}
}