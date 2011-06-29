package com.cell.util
{
	public class CMath
	{
		/**
		 * comput cyc number: (value+d) within 0~max scope
		 * @param value
		 * @param d
		 * @param max
		 * @return 
		 */
		static public function cycNum(value:int, d:int, max:int) : int 
		{
			value += d;
			return (value>=0)?(value % max):((max + value % max) % max) ;
		}
		
		/**
		 * comput cyc mod: -1 mod 10 = -1
		 * @param value
		 * @param div
		 * @return 
		 */
		static public function cycMod(value:int, div:int) : int
		{
			return (value/div) + (value<0?-1:0);
		}
		
		/**
		 * @param value 
		 * @return 1 or 0 or -1
		 */
		static public function getDirect(value:int) : int
		{
			return value==0?0:(value>0?1:-1);
		}
		
		/**
		 * comput round mod roundMode(33,8) = 5 => 33/8 + (33%8==0?:0:1)
		 * @param value
		 * @param div
		 * @return 
		 */
		static public function roundMod(value:int, div:int) : int 
		{
			return (value/div) + (value%div==0?0:(1*getDirect(value)));
		}
		
		
		/**
		 * 根据速度和时间段得到距离
		 * @param speed 速度 (距离/秒)
		 * @param interval_ms 毫秒
		 * @return
		 */
		static public function getDistance(speed:Number, interval_ms:Number) : Number
		{
			return speed * (interval_ms / 1000.0);
		}
		
		//	-------------------------------------------------------------------------------------------------------------------
		
		/**
		 * 
		 * @param px
		 * @param py
		 * @param qx
		 * @param qy
		 * @return v[]
		 */
		public static function vectorAdd(out:Array, px:Number, py:Number, qx:Number, qy:Number) : void
		{
			out[0] = px + qx;
			out[1] = py + qy;
		}
		
		/**
		 * 
		 * @param px
		 * @param py
		 * @param qx
		 * @param qy
		 * @return v[]
		 */
		public static function vectorSub(out:Array, px:Number, py:Number, qx:Number, qy:Number) : void 
		{
			out[0] = px - qx;
			out[1] = py - qy;
		}
		
		/**
		 * Vector Cross Product in Descartes reference frame 
		 * if P x Q > 0 , P above Q clockwise
		 * if P x Q < 0 , P above Q anticlockwise
		 * if P x Q = 0 , P equal Q at same line
		 * 
		 * @param p
		 * @param q
		 * @return +-
		 */
		public static function vectorCrossProduct(p:Array, q:Array) : Number 
		{
			return (p[0] * q[1] - p[1] * q[0]);
		}
		
		/**
		 * 判断2条线段是否相交
		 * ((Q2-Q1)x(P1-Q1))*((P2-Q1)x(Q2-Q1)) >= 0 
		 * ((P2-P1)x(Q1-P1))*((Q2-P1)x(P2-P1)) >= 0
		 * 
		 * @param p1x line 1
		 * @param p1y
		 * @param p2x
		 * @param p2y
		 * @param q1x line 2
		 * @param q1y
		 * @param q2x
		 * @param q2y
		 * @return false:true
		 */
		static public function intersectLine(
			p1x:Number, p1y:Number, p2x:Number, p2y:Number,
			q1x:Number, q1y:Number, q2x:Number, q2y:Number) : Boolean
		{
			/* croe */
			var v1:Array = new Array(0, 0);
			var v2:Array = new Array(0, 0);
			var v3:Array = new Array(0, 0);
			//		float v4[] = new float[2];
			
			var v5:Array = new Array(0, 0);
			var v6:Array = new Array(0, 0);
			var v7:Array = new Array(0, 0);
			//		float v8[] = new float[2];
			
			{
				CMath.vectorSub(v1, q2x, q2y, q1x, q1y);//1
				CMath.vectorSub(v2, p1x, p1y, q1x, q1y);//2
				CMath.vectorSub(v3, p2x, p2y, q1x, q1y);//3
				//			CMath.vectorSub(v4, q2x, q2y, q1x, q1y);//4=1
				
				CMath.vectorSub(v5, p2x, p2y, p1x, p1y);//5
				CMath.vectorSub(v6, q1x, q1y, p1x, p1y);//6
				CMath.vectorSub(v7, q2x, q2y, p1x, p1y);//7
				//			CMath.vectorSub(v8, p2x, p2y, p1x, p1y);//8=5
				
				if (CMath.vectorCrossProduct(v1, v2) * CMath.vectorCrossProduct(v3, v1) >= 0 && 
					CMath.vectorCrossProduct(v5, v6) * CMath.vectorCrossProduct(v7, v5) >= 0) {
					return true;
				}
				return false;
			}
		}
		
		//	--------------------------------------------------------------------------------------------------
		/**
		 * 判断2圆是否碰撞
		 */
		static public function intersectRound(
			sx:Number, sy:Number, sr:Number, 
			dx:Number, dy:Number, dr:Number) : Boolean
		{
			var w:Number = sx - dx;
			var h:Number = sy - dy;
			var r:Number = sr + dr;
			
			if (w*w + h*h <= r*r) {
				return true;
			}
			
			return false;
		}
		
		//	--------------------------------------------------------------------------------------------------
		
		static public function includeRoundPoint(
			sx:Number, sy:Number, sr:Number, 
			px:Number, py:Number) : Boolean
		{
			var w:Number = sx - px;
			var h:Number = sy - py;
			
			if (w*w + h*h <= sr*sr) {
				return true;
			}
			
			return false;
		}
		
		//	--------------------------------------------------------------------------------------------------
		
		static public function intersectRect(
			sx1:Number, sy1:Number, sx2:Number, sy2:Number, 
			dx1:Number, dy1:Number, dx2:Number, dy2:Number) : Boolean
		{
			if (sx2 < dx1)		return false;
			if (sx1 > dx2)		return false;
			if (sy2 < dy1)		return false;
			if (sy1 > dy2)		return false;
			return true;
		}
		
		static public function intersectRect2(
			sx1:Number, sy1:Number, sw:Number, sh:Number, 
			dx1:Number, dy1:Number, dw:Number, dh:Number) : Boolean
		{
				var sx2:Number = sx1 + sw - 1 ;
				var sy2:Number = sy1 + sh - 1 ;
				var dx2:Number = dx1 + dw - 1 ;
				var dy2:Number = dy1 + dh - 1 ;
				if (sx2 < dx1)		return false;
				if (sx1 > dx2)		return false;
				if (sy2 < dy1)		return false;
				if (sy1 > dy2)		return false;
				return true;	
		}
		
		//	--------------------------------------------------------------------------------------------------
		
		static public function includeRectPoint(
			sx1:Number, sy1:Number, 
			sx2:Number, sy2:Number, 
			dx:Number, dy:Number) : Boolean
		{
				if (sx2 < dx)		return false;
				if (sx1 > dx)		return false;
				if (sy2 < dy)		return false;
				if (sy1 > dy)		return false;
				return true;
		}
		
		static public function includeRectPoint2(
			sx1:Number, sy1:Number, 
			sw:Number, sh:Number, 
			dx:Number, dy:Number) : Boolean
		{
				var sx2:Number = sx1 + sw - 1 ;
				var sy2:Number = sy1 + sh - 1 ;
				if (sx2 < dx)		return false;
				if (sx1 > dx)		return false;
				if (sy2 < dy)		return false;
				if (sy1 > dy)		return false;
				return true;
		}
		
		//	--------------------------------------------------------------------------------------------------
		
		/**
		 * if the value is in a~b or equal
		 * @param value
		 * @param a
		 * @param b
		 * @return
		 */
		public static function isIncludeEqual(value:Number, min:Number, max:Number) : Boolean
		{
			return max >= value && min <= value ;
		}
		
		/**
		 * if the value is in a~b not equal
		 * @param value
		 * @param a
		 * @param b
		 * @return
		 */
		public static function isInclude(value:Number, min:Number, max:Number) : Boolean
		{
			return max > value && min < value ;
		}
		
		
		//	--------------------------------------------------------------------------------------------------
		
		

	}
}