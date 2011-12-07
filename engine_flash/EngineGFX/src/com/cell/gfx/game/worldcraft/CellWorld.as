package com.cell.gfx.game.worldcraft
{
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.ICamera;
	import com.cell.gfx.game.IGraphics;
	import com.cell.util.Arrays;
	import com.cell.util.CMath;
	import com.cell.util.Util;
	
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.geom.Rectangle;

	public class CellWorld extends Sprite
	{
		internal var _timer : int = 0;
		
		internal var _camera : ICamera;
		
		private var _units : Vector.<CellUnit> = new Vector.<CellUnit>();
		
		private var _units_adding : Vector.<CellUnit> = new Vector.<CellUnit>();
		private var _units_removing : Vector.<CellUnit> = new Vector.<CellUnit>();
		
		public function CellWorld(
			viewWidth:int, 
			viewHeight:int)
		{					
			this.mouseEnabled = false;
			this.mouseChildren = false;

			this._camera	= createCamera(viewWidth, viewHeight);
			this.scrollRect = _camera.bounds;
		}
		
		protected function createCamera(w:int, h:int) : ICamera
		{
			return new CellWorldCamera(w, h);
		}
		
		protected function get units() : Vector.<CellUnit>
		{
			return _units;
		}
		
		public function get timer() : int
		{
			return _timer;
		}
		
		//		------------------------------------------------------------------------------------------------------
		
		public function get camera() : ICamera
		{
			return _camera;
		}
		
		public function getCamera() : ICamera {
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
		public function update() : void 
		{
			for each (var s:CellUnit in _units) {
				s.updateIn(this);
			}
			if (_units_adding.length > 0) {
				for each (var add:* in _units_adding) {
					_units.push(add);
				}
				_units_adding.splice(0, _units_adding.length);
			}
			if (_units_removing.length > 0) {
				for each (var remove:* in _units_removing) {
					_units.splice(_units.indexOf(remove), 1);
				}
				_units_removing.splice(0, _units_removing.length);
			}
			onUpdate();
		}
		
		public function render() : void
		{
			for each (var s:CellUnit in _units) {
				s.renderIn();
			}
			this.scrollRect = _camera.bounds;
			_timer++;
		}
	
		protected function onUpdate() : void {
			
		}
		
//		protected function onRender(cg:IGraphics) : void {
//			
//		}
		

		
//		------------------------------------------------------------------------------------------------------
		
		override public function addChild(child:DisplayObject):DisplayObject
		{
			var ret : DisplayObject = super.addChild(child);
			if (ret != null && child is CellUnit) {
				_units_adding.push(child);
				onAddedUnit(child as CellUnit);
			}
			return ret;
		}
		

		override public function addChildAt(child:DisplayObject, index:int):DisplayObject
		{
			var ret : DisplayObject = super.addChildAt(child, index);
			if (ret != null && child is CellUnit) {
				_units_adding.push(child);
				onAddedUnit(child as CellUnit);
			}
			return ret;
		}
		
		override public function removeChild(child:DisplayObject):DisplayObject
		{
			var ret : DisplayObject = super.removeChild(child);
			if (ret != null && child is CellUnit) {
				_units_removing.push(child);
				onRemovedUnit(child as CellUnit);
			}
			return ret;
		}

		override public function removeChildAt(index:int):DisplayObject
		{
			var ret : DisplayObject = super.removeChildAt(index);
			if (ret != null && ret is CellUnit) {
				_units_removing.push(ret);
				onRemovedUnit(ret as CellUnit);
			}
			return ret;
		}
		
		protected function onAddedUnit(u:CellUnit) : void
		{
			
		}
		protected function onRemovedUnit(u:CellUnit) : void
		{
			
		}
//		------------------------------------------------------------------------------------------------------
		
		public function addSprite(spr:Sprite) : Boolean 
		{
			this.addChild(spr);
			return false;
		}
		
		public function removeSprite(spr:Sprite) : Boolean 
		{
			this.removeChild(spr);
			return false;
		}
		
		public function removeAllSprite() : int
		{
			_units.splice(0, _units.length);
			var count : int = 0;
			while (super.numChildren > 0) {
				if (this.removeChildAt(0)) {
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
		
//		------------------------------------------------------------------------------------------------------
	
		public function sortY() : void
		{
			Util.sortOnChilds(this, "y", Array.NUMERIC);
		}
	}
}