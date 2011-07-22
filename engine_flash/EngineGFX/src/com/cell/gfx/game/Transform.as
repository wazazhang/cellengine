package com.cell.gfx.game
{
	import flash.geom.Matrix;

	public class Transform
	{
		static public const TRANS_NONE 		: int = 0;
		static public const TRANS_90 		: int = 1;
		static public const TRANS_180 		: int = 2; 
		static public const TRANS_270 		: int = 3;
		static public const TRANS_H 		: int = 4;
		static public const TRANS_H90 		: int = 5;
		static public const TRANS_H180 		: int = 6;
		static public const TRANS_H270 		: int = 7;
		
		
		
		
		
		static public var ANGLE_90			: Number = Math.PI / 2;
		static public var ANGLE_180			: Number = Math.PI;
		static public var ANGLE_270			: Number = Math.PI * 3 / 2;
		
		
		static public function getMatrix(x:int, y:int, width:int, height:int, transform:int) : Matrix
		{
			var ret : Matrix = new Matrix();
			switch (transform) {
				case Transform.TRANS_NONE:
					ret.translate(x, y);
					break;
				case Transform.TRANS_90:
					ret.rotate(ANGLE_90);
					ret.translate(width, 0);
					ret.translate(x, y);
					break;
				case Transform.TRANS_180:
					ret.rotate(ANGLE_180);
					ret.translate(width, height);
					ret.translate(x, y);
					break;
				case Transform.TRANS_270:
					ret.rotate(ANGLE_270);
					ret.translate(0, height);
					ret.translate(x, y);
					break;
				
				case Transform.TRANS_H:
					ret.scale(-1, 1);
					ret.translate(width, 0);
					ret.translate(x, y);
					break;
				case Transform.TRANS_H90:
					ret.scale(-1, 1);
					ret.translate(height, 0);
					ret.rotate(ANGLE_90);
					ret.translate(width, 0);
					ret.translate(x, y);
					break;
				case Transform.TRANS_H180:
					ret.scale(-1, 1);
					ret.translate(width, 0);
					ret.rotate(ANGLE_180);
					ret.translate(width, height);
					ret.translate(x, y);
					break;
				case Transform.TRANS_H270:
					ret.scale(-1, 1);
					ret.translate(height, 0);
					ret.rotate(ANGLE_270);
					ret.translate(0, height);
					ret.translate(x, y);
					break;
			}
			return ret;
		}
	}
}