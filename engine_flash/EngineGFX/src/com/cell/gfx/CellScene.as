package com.cell.gfx
{
	import flash.display.Sprite;
	import flash.geom.Rectangle;

	public class CellScene extends Sprite
	{
		public function CellScene(
			viewWidth:int, 
			viewHeight:int)
		{
			this.scrollRect = new Rectangle(0, 0, viewWidth, viewHeight);
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
		
		public function setCameraSize(w:int, h:int) : void
		{
			var rect : Rectangle = scrollRect;
			
			if (rect.width != w || rect.height != h)	
			{
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
				rect.x = x ;
				rect.y = y ;
				this.scrollRect = rect;
			}
		}
		
		public function locateCameraCenter(x:int, y:int) : void 
		{
			locateCamera(x - scrollRect.width/2, y - scrollRect.height/2);
		}
		
		public function moveCamera(dx:int, dy:int) : void 
		{
			locateCamera(getCameraX() + dx, getCameraY() + dy);
		}
		
	}
}