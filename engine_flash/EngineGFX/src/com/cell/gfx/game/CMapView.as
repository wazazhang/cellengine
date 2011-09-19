package com.cell.gfx.game
{	
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CCamera;
	import com.cell.gfx.game.CGraphicsBitmap;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.IGraphics;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Sprite;
	import flash.geom.Rectangle;

	public class CMapView extends Bitmap
	{
		protected var Map		: CMap;
		protected var Camera	: CCamera;
		protected var cg		: IGraphics;
		
		private var old_cx		: int;
		private var old_cy		: int;
		
		
		public function CMapView(map:CMap, viewWidth:int, viewHeight:int)
		{			
			super(new BitmapData(viewWidth, viewHeight, false, 0xff000000));
			this.cg		= new CGraphicsBitmap(bitmapData);
			this.Map 	= map;
			this.Camera = new CCamera(viewWidth, viewHeight, map);			
			this.Camera.resetBuffer();
			this.Camera.render(cg);

		}
		
		public function getMap() : CMap {
			return Map;
		}
		
		public function getCamera() : CCamera {
			return Camera;
		}
		
		//	------------------------------------------------------------------------------------------------------
		
		public function toWorldPosX(screenX:int) : int {
			return screenX + Camera.getX();
		}
		
		public function toWorldPosY(screenY:int) : int {
			return screenY + Camera.getY();
		}
		
		public function toScreenPosX(worldX:int) : int {
			return worldX - Camera.getX();
		}
		
		public function toScreenPosY(worldY:int) : int {
			return worldY - Camera.getY();
		}
		
		public function render() : void
		{
			if (old_cx != Camera.getX() || old_cy != Camera.getY()) 
			{
				this.bitmapData.lock();
				try {
					this.Camera.render(cg);
					this.old_cx = Camera.getX();
					this.old_cy = Camera.getY();
				} finally {
					this.bitmapData.unlock();
				}
			}
			
		}
		
	}
}