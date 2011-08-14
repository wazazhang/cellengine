package com.cell.gfx.game
{
	import com.cell.math.IVector2D;

	public class CWorldCamera implements IVector2D
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
		
		public function addVectorX(dx:Number) : void {
			this._x += dx;
		}
		public function addVectorY(dy:Number) : void {
			this._y += dy;
		}
		public function setVectorX(x:Number) : void {
			this._x = x;
		}
		public function setVectorY(y:Number) : void {
			this._y = y;
		}
		public function getVectorX() : Number {
			return this._x;
		}
		public function getVectorY() : Number {
			return this._y;
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