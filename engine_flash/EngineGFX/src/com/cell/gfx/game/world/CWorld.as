
package com.cell.gfx.game.world
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CGraphicsBitmap;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.CWorldCamera;
	import com.cell.gfx.game.IGraphics;
	import com.cell.util.CMath;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.geom.Rectangle;
	
	/**
	 * @since 2006-11-30 
	 * @version 1.0
	 */
	public class CWorld extends Bitmap
	{	
		protected var 	cg				: IGraphics;
		protected var 	camera			: CWorldCamera;
		
		protected var	sprites			: Vector.<CWorldSprite> 		= new Vector.<CWorldSprite>();
		
		
		public function CWorld(viewWidth:int, viewHeight:int)
		{		
			super(new BitmapData(viewWidth, viewHeight, false));
			this.camera		= new CWorldCamera(viewWidth, viewHeight);
			this.cg			= new CGraphicsBitmap(bitmapData);
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
			if (CMath.intersectRect2(
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
		
		public function update() : void
		{
			for each (var spr : CWorldSprite in sprites) {
				spr.update();
			}
		}
		
		public function render() : void
		{
			renderSprites(cg, sprites);
		}
		
//		------------------------------------------------------------------------------------------------------
		
		
		public function addSprite(spr:CWorldSprite) : Boolean 
		{
			if (spr._parent == null) {
				if (this.sprites.indexOf(spr)<0) {
					this.sprites.push(spr);
					spr._parent = this;
					return true;
				}
			}
			return false;
		}
		
		public function removeSprite(spr:CWorldSprite) : Boolean 
		{
			if (spr._parent == this) {
				var index : int = sprites.indexOf(spr);
				if (index >= 0) {
					this.sprites.splice(index, 1);
					spr._parent = null;
					return true;
				}
			}
			return false;
		}
		
		public function getSprite(index:int) : CWorldSprite {
			return sprites[index];
		}
		
		public function getSpriteCount() : int {
			return sprites.length;
		}
		
		public function getSpriteIndex(spr:CWorldSprite) : int {
			return sprites.indexOf(spr);
		}
		
		
		protected function renderSprites(g:IGraphics, sprites:Vector.<CWorldSprite>) : void
		{
			var x1:int = getCameraX();
			var y1:int = getCameraY();
			var x2:int = getCameraX() + getCameraWidth();
			var y2:int = getCameraY() + getCameraHeight();
 			
			for each (var spr : CWorldSprite in sprites)
			{
				if(CMath.intersectRect(
					spr.x + spr.getAnimates().w_left, 
					spr.y + spr.getAnimates().w_top, 
					spr.x + spr.getAnimates().w_right, 
					spr.y + spr.getAnimates().w_bottom, 
					x1, y1, x2, y2))
				{
					spr.render(g, spr.x - x1, spr.y - y1, spr.getCurrentAnimate(), spr.getCurrentFrame());
				}
			}
		}
	}
}
