package com.cell.gfx.game
{
	import com.cell.util.CMath;
	
	public class CCD
	{
		static public const CD_TYPE_RECT 	: int = 1;
		static public const CD_TYPE_LINE 	: int = 2;
		static public const CD_TYPE_POINT 	: int = 3;
		
		public var Type : int;
	
		public var Mask : int;
	
		/**Left */
		public var X1 : int;
		/**Top */
		public var Y1 : int;
		/**Right*/
		public var X2 : int;
		/**Bottom*/
		public var Y2 : int;
	
		public var Data : *;
		
		public var SetData : Array;
		
		public function clone() : CCD 
		{
			var ret : CCD = new CCD();
			ret.Type 	= this.Type;
			ret.Mask	= this.Mask;
			ret.X1		= this.X1;
			ret.Y1		= this.Y1;
			ret.X2		= this.X2;
			ret.Y2		= this.Y2;
			return ret;
		}
	
		public function getWidth() : int {
			return X2 - X1 + 1;
		}
		
		public function getHeight() : int {
			return Y2 - Y1 + 1;
		}
		
		/**
		 * 得到外包矩形中心点和渲染中心点的偏移
		 * @return
		 */
		public function getMedianY() : int {
			return -(getHeight() / 2 - (Y1 + getHeight()));
		}
		
		/**
		 * 得到外包矩形中心点和渲染中心点的偏移
		 * @return
		 */
		public function getMedianX() : int {
			return -(getWidth() / 2 - (X1 + getWidth()));
		}
	
		public function render(g:IGraphics, px:int, py:int, color:int) : void
		{
//			if ( Mask == 0) return;
//			g.setColor(color);
//			switch (Type) {
//			case CD_TYPE_LINE:
//				g.drawLine(px + X1, py + Y1, px + X2, py + Y2 );
//				break;
//			case CD_TYPE_RECT:
//				g.drawRect(px + X1, py + Y1, X2 - X1 , Y2 - Y1 );
//				break;
//			}
		}
		
		
		static public function createCDRect(mask:int, x:int, y:int, w:int, h:int) : CCD 
		{
			var ret : CCD = new CCD();
			ret.Type = CCD.CD_TYPE_RECT;
			ret.Mask = mask;
			ret.X1 = x;
			ret.Y1 = y;
			ret.X2 = (x + w);
			ret.Y2 = (y + h);
			return ret;
		}
		
		static public function createCDRect2Point(mask:int, sx:int, sy:int, dx:int, dy:int) : CCD  
		{
			var ret : CCD = new CCD();
			ret.Type = CD_TYPE_RECT;
			ret.Mask = mask;
			ret.X1 =  Math.min(sx, dx);
			ret.Y1 =  Math.min(sy, dy);
			ret.X2 =  Math.max(sx, dx);
			ret.Y2 =  Math.max(sy, dy);
			return ret;
		}
		
		static public function createCDLine(mask:int, px:int, py:int, qx:int, qy:int) : CCD 
		{
			var ret : CCD = new CCD();
			ret.Type = CD_TYPE_LINE;
			ret.Mask =  mask;
			ret.X1 = px;
			ret.Y1 = py;
			ret.X2 = qx;
			ret.Y2 = qy;
			return ret;
		}
	
		static public function touch(
				b1:CCD,  x1:int,  y1:int, 
				b2:CCD,  x2:int,  y2:int, 
				processStatus:Boolean=false) : Boolean
		{
			if (processStatus) {
				if ((b1.Mask & b2.Mask) == 0) {
					return false;
				}
			}
			if (b1.Type == CD_TYPE_RECT && b2.Type == CD_TYPE_RECT) {
				return touchRect(b1, x1, y1, b2, x2, y2);
			} else if (b1.Type == CD_TYPE_LINE && b2.Type == CD_TYPE_LINE) {
				return touchLine(b1, x1, y1, b2, x2, y2);
			} else if (b1.Type == CD_TYPE_RECT) {
				return touchRectLine(b1, x1, y1, b2, x2, y2);
			} else if (b2.Type == CD_TYPE_RECT) {
				return touchRectLine(b2, x2, y2, b1, x1, y1);
			}
			return false;
		}
	
		static public function touchRect(
				src:CCD, sx:int, sy:int, 
				dst:CCD, dx:int, dy:int) : Boolean
		{
			return CMath.intersectRect(
					src.X1 + sx, src.Y1 + sy, 
					src.X2 + sx, src.Y2 + sy, 
					dst.X1 + dx, dst.Y1 + dy, 
					dst.X2 + dx, dst.Y2 + dy);
		}
	
		static public function touchLine(
				src:CCD, sx:int, sy:int, 
				dst:CCD, dx:int, dy:int) : Boolean
		{
			if (touchRect(src, sx, sy, dst, dx, dy)) {
				return CMath.intersectLine(
						src.X1 + sx, src.Y1 + sy, 
						src.X2 + sx, src.Y2 + sy, 
						dst.X1 + dx, dst.Y1 + dy, 
						dst.X2 + dx, dst.Y2 + dy);
			}
			return false;
		}
	
		static public function touchRectLine(
				rect:CCD,  rx:int,  ry:int, 
				line:CCD,  lx:int,  ly:int) : Boolean
		{
			if (CMath.intersectRect(
					rx + rect.X1, ry + rect.Y1,//
					rx + rect.X2, ry + rect.Y2,//
					lx + Math.min(line.X1,line.X2), ly + Math.min(line.Y1,line.Y2),//
					lx + Math.max(line.X1,line.X2), ly + Math.max(line.Y1,line.Y2))//
			) {
				if (CMath.intersectLine(//TOP
						(rx + rect.X1) , (ry + rect.Y1) ,//
						(rx + rect.X2) , (ry + rect.Y1) ,//
						(lx + line.X1) , (ly + line.Y1) ,//
						(lx + line.X2) , (ly + line.Y2)))//
					return true;
				if (CMath.intersectLine(//LEFT
						(rx + rect.X1) , (ry + rect.Y1) ,//
						(rx + rect.X1) , (ry + rect.Y2) ,//
						(lx + line.X1) , (ly + line.Y1) ,//
						(lx + line.X2) , (ly + line.Y2)))//
					return true;
				if (CMath.intersectLine(//RIGHT
						(rx + rect.X2) , (ry + rect.Y1) ,//
						(rx + rect.X2) , (ry + rect.Y2) ,//
						(lx + line.X1) , (ly + line.Y1) ,//
						(lx + line.X2) , (ly + line.Y2)))//
					return true;
				if (CMath.intersectLine(//BUTTON
						(rx + rect.X1) , (ry + rect.Y2) ,//
						(rx + rect.X2) , (ry + rect.Y2) ,//
						(lx + line.X1) , (ly + line.Y1) ,//
						(lx + line.X2) , (ly + line.Y2)))//
					return true;
			}
			return false;
		}
	
	}
}