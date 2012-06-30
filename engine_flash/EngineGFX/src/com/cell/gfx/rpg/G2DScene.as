package com.cell.gfx.rpg
{
	import com.cell.gameedit.OutputLoader;
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.ResourceLoader;
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gameedit.object.SpriteSet;
	import com.cell.gameedit.object.WorldSet;
	import com.cell.gameedit.object.worldset.SpriteObject;
	import com.cell.gfx.game.CCamera;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.CSpriteBuffer;
	import com.cell.gfx.game.IGraphics;
	
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.Scene;
	import flash.display.Sprite;
	import flash.geom.Rectangle;

	public class G2DScene extends Sprite
	{
		private var resource 	: ResourceLoader;
		private var world_data	: WorldSet;
		private var cur_time	: int = 0;
		
		public function G2DScene(
			res:ResourceLoader,
			world:WorldSet,
			viewWidth:int, 
			viewHeight:int)
		{
			this.scrollRect = new Rectangle(0, 0, viewWidth, viewHeight);
			this.resource = res;
			this.world_data = world;
			for each (var obj:SpriteObject in world.Sprs) {
				var unit : G2DUnit = createMapObject(obj);
				if (unit != null) {
					addChild(unit);
				}
			}
		}
		
		public function getResource() : ResourceLoader
		{
			return resource;
		}
		
		public function getWorldSet() : WorldSet
		{
			return world_data;
		}
		
		protected function createMapObject(obj:SpriteObject) : G2DUnit 
		{
			var cspr : CSpriteBuffer = resource.getSpriteBuffer(obj.SprID);
			var unit : G2DMapObject = new G2DMapObject(cspr, obj);
			return unit;
		}
		
		public function setCameraSize(w:int, h:int) : void
		{
			var rect : Rectangle = scrollRect;
			
			if (rect.width != w || rect.height != h)	
			{
				if (world_data != null) 
				{
					if (w > world_data.Width) {
						w = world_data.Width;
					}
					if (h > world_data.Height) {
						h = world_data.Height;
					}
				}
				rect.width = w;
				rect.height = h;
				
				this.scrollRect = rect;
			}
		}
		
		public function locateCamera(x:int, y:int) : void 
		{
			var rect : Rectangle = scrollRect;
			
			if (rect.x != x || rect.y != y)		
			{
				if (x < 0) {
					x = 0;
				}
				if (x > world_data.Width - rect.width) {
					x = world_data.Width - rect.width;
				}
				if (y < 0) {
					y = 0;
				}
				if (y > world_data.Height - rect.height) {
					y = world_data.Height - rect.height;
				}
				rect.x = x ;
				rect.y = y ;
				
				this.scrollRect = rect;
			}
		}
		
		/**
		 * 每帧调用一次
		 */
		public function update() : void {
			for (var i:int = 0; i<numChildren; i++) {
				var s : DisplayObject = getChildAt(i);
				if (s is G2DUnit) {
					(s as G2DUnit).update();
				}
			}
		}
		
		public function getCameraX() : int
		{
			return this.scrollRect.x;
		}
		
		public function getCameraY() : int
		{
			return this.scrollRect.y;
		}
		
		public function getCameraWidth()  : int
		{
			return this.scrollRect.width;
		}
		
		public function getCameraHeight() : int
		{
			return this.scrollRect.height;
		}
		
		
		public function locateCameraCenter(x:int, y:int) : void 
		{
			locateCamera(x - scrollRect.width/2, y - scrollRect.height/2);
		}
		
		public function moveCamera(dx:int, dy:int) : void 
		{
			locateCamera(getCameraX() + dx, getCameraY() + dy);
		}
		
		public function containsInCamera(spr:DisplayObject) : Boolean
		{
			if (scrollRect.intersects(spr.getBounds(this))) {
				return true;
			}
			return false;
		}
		
	}
	
	
}