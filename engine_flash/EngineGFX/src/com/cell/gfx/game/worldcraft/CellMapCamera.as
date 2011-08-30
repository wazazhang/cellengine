package com.cell.gfx.game.worldcraft
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.CMapView;
	import com.cell.gfx.game.ICamera;
	import com.cell.gfx.game.IImageObserver;
	
	import flash.display.Sprite;
	import flash.geom.Rectangle;

	public class CellMapCamera implements ICamera
	{		
		private var _map:CMapView;
		
		private var _rect : Rectangle;
		
		public function CellMapCamera(map:CMapView)
		{
			_rect = new Rectangle(0, 0, map.width, map.height);
			_map = map;
		}
		
		public function get bounds() : Rectangle
		{
			_rect.x = _map.getCamera().getX();
			_rect.y = _map.getCamera().getY();
			return _rect;
		}
		
		public function get x() : Number
		{
			return _map.getCamera().getX();
		}
		
		public function get y() : Number
		{
			return _map.getCamera().getY();
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
			this._map.getCamera().move(dx, 0);
		}
		public function addVectorY(dy:Number) : void {
			this._map.getCamera().move(0, dy);
		}
		public function setVectorX(x:Number) : void {
			this._map.getCamera().setPos(x, _map.getCamera().getY());
		}
		public function setVectorY(y:Number) : void {
			this._map.getCamera().setPos(_map.getCamera().getX(), y);
		}
		public function getVectorX() : Number {
			return this._map.getCamera().getX();
		}
		public function getVectorY() : Number {
			return this._map.getCamera().getY();
		}
		
		public function setPos(x:Number, y:Number) : void {
			this._map.getCamera().setPos(x, y);
		}
		
		public function move(x:Number, y:Number) : void
		{
			this._map.getCamera().move(x, y);
		}
	}
}