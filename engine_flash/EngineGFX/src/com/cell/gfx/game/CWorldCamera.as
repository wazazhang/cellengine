package com.cell.gfx.game
{
	public class CWorldCamera
	{
		private var _x : Number;
		private var _y : Number;
		private var _w : Number;
		private var _h : Number;
		
		
		public function CWorldCamera(w:int, h:int)
		{
			this._w = w;
			this._h = h;
		}
		
		public function get x() : Number
		{
			return _x;
		}
		
		public function get y() : Number
		{
			return _y;
		}
		
		public function get w() : Number
		{
			return _w;
		}
		
		public function get h() : Number
		{
			return _h;
		}
		
		
		/**
		 * set position within map</br>
		 * @param x x 
		 * @param y y
		 */
		public function setPos(x:Number, y:Number) : void {
			this._x = x;
			this._y = y;
		}
		
		
		/**
		 * move camera within map</br>
		 * @param px offset x
		 * @param py offset y
		 */
		public function move(x:Number, y:Number) : void
		{
			this._x += x;
			this._y += y;
		}
	}
}