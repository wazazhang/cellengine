package com.cell.gfx.game.worldcraft
{
	import com.cell.gfx.game.CWorldCamera;
	import com.cell.util.CMath;
	
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.geom.Rectangle;

	public class CellWorld extends Sprite
	{
		private var cur_time	: int = 0;
		private var _camera		: CellWorldCamera;
		
		public function CellWorld(
			viewWidth:int, 
			viewHeight:int)
		{		
			this.mouseEnabled = false;
			this.mouseChildren = false;
			this._camera	= new CellWorldCamera(viewWidth, viewHeight);
			this.scrollRect = _camera.rect;
		}
		
		
		//		------------------------------------------------------------------------------------------------------
		
		public function get camera() : CWorldCamera
		{
			return _camera;
		}
		
		public function getCamera() : CWorldCamera {
			return _camera;
		}
		
		public function getCameraX() : Number
		{
			return this._camera.x;
		}
		
		public function getCameraY() : Number
		{
			return this._camera.y;
		}
		
		public function getCameraWidth()  : Number
		{
			return this._camera.w;
		}
		
		public function getCameraHeight() : Number
		{
			return this._camera.h;
		}
		
		public function locateCamera(x:Number, y:Number) : void 
		{
			_camera.setPos(x, y);
		}
		
		public function moveCamera(dx:Number, dy:Number) : void 
		{
			_camera.move(dx, dy);
		}
		
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
		
		/**
		 * 每帧调用一次
		 */
		final public function update() : void 
		{
			for (var i:int = 0; i<numChildren; i++) {
				var s : DisplayObject = getChildAt(i);
				if (s is CellUnit) {
					(s as CellUnit).update(this);
				}
			}
			
			this.scrollRect = _camera.rect;
			
			onUpdate();
		}
				
		protected function onUpdate() : void {
			
		}
		
		//		------------------------------------------------------------------------------------------------------
		
		
		public function addSprite(spr:Sprite) : Boolean 
		{
			super.addChild(spr);
			return false;
		}
		
		public function removeSprite(spr:Sprite) : Boolean 
		{
			super.removeChild(spr);
			return false;
		}
		
		public function removeAllSprite() : int
		{
			var count : int = 0;
			while (super.numChildren > 0) {
				if (super.removeChildAt(0)) {
					count ++;
				}
			}
			return count;
		}
		
		public function getSprite(index:int) : Sprite {
			return super.getChildAt(index) as Sprite;
		}
		
		public function getSpriteCount() : int {
			return super.numChildren;
		}
		
		public function getSpriteIndex(spr:Sprite) : int {
			return super.getChildIndex(spr);
		}
		
	}
}