package com.cell.gfx.game
{	
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CCamera;
	import com.cell.gfx.game.CGraphicsBitmap;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.IGraphics;
	import com.cell.gfx.game.IImageObserver;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Sprite;
	import flash.geom.Rectangle;

	public class CMapView extends Bitmap implements IImageObserver
	{
		protected var Map		: CMap;
		protected var Camera	: CCamera;
		protected var cg		: IGraphics;
		
		public function CMapView(map:CMap, viewWidth:int, viewHeight:int)
		{			
			super(new BitmapData(viewWidth, viewHeight, false, 0xff000000));
			this.cg		= new CGraphicsBitmap(bitmapData);
			this.Map 	= map;
			this.Camera = new CCamera(viewWidth, viewHeight, map);
			this.Map.getAnimates().getImages().addImageObserver(this);
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
		
		public function update() : void
		{
			this.Camera.render(cg);
		}
		
		public function imagesLoaded(e:ResourceEvent) : void
		{
			//			trace(e.type + " : " + e.images_set.Name);
			this.Camera.resetBuffer();
			this.Camera.render(cg);
		}
	}
}