package com.cell.gfx
{
	
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CCamera;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.IGraphics;
	import com.cell.gfx.game.IImageObserver;
	
	import flash.display.Sprite;
	import flash.geom.Rectangle;

	public class CellMapView extends Sprite implements IImageObserver
	{
		protected var Map		: CMap;
		protected var Camera	: CCamera;
		protected var cg		: IGraphics;
		
		public function CellMapView(map:CMap, viewWidth:int, viewHeight:int)
		{			
			this.cg		= new CGraphicsDisplay(graphics);
			this.Map 	= map;
			this.Camera = new CCamera(viewWidth, viewHeight, map);
			this.repaint();		
			this.Map.getAnimates().getImages().addImageObserver(this);
			this.scrollRect = new Rectangle(0, 0, viewWidth, viewHeight);
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
		
		public function repaint() : void
		{
			this.Camera.render(cg);
		}
		
		public function imagesLoaded(e:ResourceEvent) : void
		{
			//			trace(e.type + " : " + e.images_set.Name);
			this.getCamera().resetBuffer();
			repaint();
		}
	}
}