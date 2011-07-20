package com.cell.gfx.game
{
	import com.cell.gfx.CGraphics;
	import com.cell.gfx.CGraphicsDisplay;
	
	import flash.display.Sprite;

	public class CMapView extends Sprite
	{
		protected var Map		: CMap;
		protected var Camera	: CCamera;
		
		private var cg : CGraphics;
		
		public function CMapView(map:CMap, viewWidth:int, viewHeight:int)
		{			
			this.Map 	= map;
			this.Camera = new CCamera(viewWidth, viewHeight, map);
			this.cg 	= new CGraphicsDisplay(graphics);		
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
			//Camera.resetBuffer();
			//Camera.render(cg);
		}
		
	}
}