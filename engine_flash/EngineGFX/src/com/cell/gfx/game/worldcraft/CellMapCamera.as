package com.cell.gfx.game.worldcraft
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.CMapView;
	import com.cell.gfx.game.ICamera;
	
	import flash.display.Sprite;
	import flash.geom.Rectangle;

	public class CellMapCamera implements ICamera
	{		
		private var _map:CMapView;
		
		private var _rect : Rectangle;
		
		private var _world_bounds : Rectangle;
		
		public function CellMapCamera(map:CMapView, viewW:Number, viewH:Number, wb:Rectangle=null)
		{
			this._rect = new Rectangle(0, 0, viewW, viewH);
			this._world_bounds = wb;
			this._map = map;
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
			this.setPos(_map.getCamera().getX()+dx, _map.getCamera().getY());
		}
		public function addVectorY(dy:Number) : void {
			this.setPos(_map.getCamera().getX(), _map.getCamera().getY()+dy);
		}
		public function setVectorX(x:Number) : void {
			this.setPos(x, _map.getCamera().getY());
		}
		public function setVectorY(y:Number) : void {
			this.setPos(_map.getCamera().getX(), y);
		}
		public function getVectorX() : Number {
			return this._map.getCamera().getX();
		}
		public function getVectorY() : Number {
			return this._map.getCamera().getY();
		}
		public function move(dx:Number, dy:Number) : void {
			setPos(
				_map.getCamera().getX()+dx, 
				_map.getCamera().getY()+dy);
		}
		
		
		public function setPos(x:Number, y:Number) : void 
		{
			if (_world_bounds != null) 
			{
				if (x < _world_bounds.x) {
					x = _world_bounds.x;
				}
				if (x > _world_bounds.right - _rect.width) {
					x = _world_bounds.right - _rect.width;
				}
				if (y < _world_bounds.y) {
					y = _world_bounds.y;
				}
				if (y > _world_bounds.bottom - _rect.height) {
					y = _world_bounds.bottom - _rect.height;
				}
			}
			this._map.getCamera().setPos(x, y);
		}
		
	}
}