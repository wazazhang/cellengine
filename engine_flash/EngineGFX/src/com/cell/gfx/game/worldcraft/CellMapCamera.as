package com.cell.gfx.game.worldcraft
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.ICamera;
	import com.cell.gfx.game.IImageObserver;
	
	import flash.display.Sprite;
	import flash.geom.Rectangle;

	public class CellMapCamera extends CellWorldCamera
	{
		internal var world:CellMapWorld;
		
		public function CellMapCamera(w:int, h:int, world:CellMapWorld)
		{
			super(w, h);
		}
	}
}