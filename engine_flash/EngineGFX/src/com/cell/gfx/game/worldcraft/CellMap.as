package com.cell.gfx.game.worldcraft
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.IImageObserver;
	
	import flash.display.Sprite;

	public class CellMap extends Sprite implements IImageObserver
	{
		public function CellMap(
			viewWidth:int, 
			viewHeight:int,
			map:CMap, Cworldcamera)
		{
//			super(viewWidth, viewHeight);
		}

		public function imagesLoaded(e:ResourceEvent) : void
		{
			repaint();
		}

		public function repaint() : void
		{
		
		}

		public function cameraMove(dx:int, dy:int) : void {
			
		}
	}
}