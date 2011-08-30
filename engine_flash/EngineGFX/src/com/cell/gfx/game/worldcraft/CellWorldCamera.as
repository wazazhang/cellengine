package com.cell.gfx.game.worldcraft
{
	import com.cell.gfx.game.ICamera;
	
	import flash.geom.Rectangle;

	public class CellWorldCamera implements ICamera
	{
		private var _rect : Rectangle;
		
		public function CellWorldCamera(w:int, h:int)
		{
			_rect = new Rectangle(0,0,w,h);
		}
		
		public function get bounds() : Rectangle
		{
			return _rect;
		}
		
		public function get x() : Number
		{
			return _rect.x;
		}
		
		public function get y() : Number
		{
			return _rect.y;
		}
		
		public function get w() : Number
		{
			return _rect.width;
		}
		
		public function get h() : Number
		{
			return _rect.height;
		}
		
		public function addVectorX(dx:Number) : void {
			this._rect.x += dx;
		}
		public function addVectorY(dy:Number) : void {
			this._rect.y += dy;
		}
		public function setVectorX(x:Number) : void {
			this._rect.x = x;
		}
		public function setVectorY(y:Number) : void {
			this._rect.y = y;
		}
		public function getVectorX() : Number {
			return this._rect.x;
		}
		public function getVectorY() : Number {
			return this._rect.y;
		}
		
		
		/**
		 * set position within map</br>
		 * @param x x 
		 * @param y y
		 */
		public function setPos(x:Number, y:Number) : void {
			this._rect.x = x;
			this._rect.y = y;
		}
		
		
		/**
		 * move camera within map</br>
		 * @param px offset x
		 * @param py offset y
		 */
		public function move(x:Number, y:Number) : void
		{
			this._rect.x += x;
			this._rect.y += y;
		}
		
	}
}