package com.cell.gfx
{
	
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CCamera;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.IGraphics;
	import com.cell.gfx.game.IImageObserver;
	
	import flash.display.Sprite;

	public class G2DMapView extends Sprite implements IImageObserver
	{
		protected var Map		: CMap;
		protected var Camera	: CCamera;
		protected var cg		: IGraphics;
		
		public function G2DMapView(map:CMap, viewWidth:int, viewHeight:int)
		{			
			this.Map 	= map;
			this.Camera = new CCamera(viewWidth, viewHeight, map);
			this.repaint();		
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