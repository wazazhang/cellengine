package com.cell.gfx.game.worldcraft
{
	import com.cell.gfx.game.CWorldCamera;
	
	import flash.geom.Rectangle;

	public class CellWorldCamera extends CWorldCamera
	{
		private var _rect : Rectangle;
		
		public function CellWorldCamera(w:int, h:int)
		{
			super(w, h);
			_rect = new Rectangle(0,0,w,h);
		}
		
		internal function get rect() : Rectangle
		{
			_rect.x = x;
			_rect.y = y;
			return _rect;
		}
	}
}