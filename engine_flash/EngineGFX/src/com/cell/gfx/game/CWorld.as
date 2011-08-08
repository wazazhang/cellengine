
package com.cell.gfx.game
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.util.CMath;
	
	import flash.display.BitmapData;
	import flash.geom.Rectangle;
	
	/**
	 * @since 2006-11-30 
	 * @version 1.0
	 */
	public class CWorld implements IImageObserver
	{	
		protected var 	buffer		: BitmapData;
		protected var 	cg			: IGraphics;
		
		protected var 	camera		: CWorldCamera;
		
		public function CWorld(viewWidth:int, viewHeight:int)
		{		
			this.camera		= new CWorldCamera(viewWidth, viewHeight);
			this.buffer		= new BitmapData(viewWidth, viewHeight, false);
			this.cg			= new CGraphicsBitmap(buffer);
		}
		
//		------------------------------------------------------------------------------------------------------
		
		public function getCamera() : CWorldCamera {
			return camera;
		}
		
		public function getCameraX() : Number
		{
			return this.camera.x;
		}
		
		public function getCameraY() : Number
		{
			return this.camera.y;
		}
		
		public function getCameraWidth()  : Number
		{
			return this.camera.w;
		}
		
		public function getCameraHeight() : Number
		{
			return this.camera.h;
		}
		
		public function locateCamera(x:Number, y:Number) : void 
		{
			camera.setPos(x, y);
		}
		
		public function moveCamera(dx:Number, dy:Number) : void 
		{
			camera.move(dx, dy);
		}
		
//		------------------------------------------------------------------------------------------------------
		
		final public function locateCameraCenter(x:int, y:int) : void 
		{
			locateCamera(x - getCameraWidth()/2, y - getCameraHeight()/2);
		}
		
		final public function intersectsCamera(x:int, y:int, width:int, height:int) : Boolean
		{
			if (CMath.intersectRect(
				getCameraX(), getCameraY(), getCameraWidth(), getCameraHeight(),
				x, y, width, height
			)) {
				return true;
			}
			return false;
		}
		
		
		final public function toWorldPosX(screenX:int) : int {
			return screenX + getCameraX();
		}
		
		final public function toWorldPosY(screenY:int) : int {
			return screenY + getCameraY();
		}
		
		final public function toScreenPosX(worldX:int) : int {
			return worldX - getCameraX();
		}
		
		final public function toScreenPosY(worldY:int) : int {
			return worldY - getCameraY();
		}
		
//		------------------------------------------------------------------------------------------------------
		
		public function render() : void
		{
			
		}
		
		public function imagesLoaded(e:ResourceEvent) : void
		{
			
		}
		
		
	}
}
