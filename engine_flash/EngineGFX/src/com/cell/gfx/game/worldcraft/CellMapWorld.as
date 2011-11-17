package com.cell.gfx.game.worldcraft
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.CMapView;
	import com.cell.gfx.game.ICamera;
	import com.cell.util.CMath;

	public class CellMapWorld extends CellWorld
	{
		internal var _map : CMap ;
		internal var _map_view : CMapView;
		
		public function CellMapWorld(
			viewWidth:int, 
			viewHeight:int,
			map:CMap)
		{								
			this._map = map;
			this._map_view = new CMapView(map, viewWidth, viewHeight);
			super(viewWidth, viewHeight);
			addChild(_map_view);
		}
		
		override protected function createCamera(w:int, h:int) : ICamera
		{
			return new CellMapCamera(_map_view, w, h);
		}
		
		override public function render() : void
		{					
			_map_view.render();
			_map_view.x = _map_view.getCamera().getX();
			_map_view.y = _map_view.getCamera().getY();
			super.render();			
		}

	}
}