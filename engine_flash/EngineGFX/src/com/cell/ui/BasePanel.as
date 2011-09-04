package com.cell.ui
{
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.geom.Rectangle;

	public class BasePanel extends Sprite
	{
		public function BasePanel(viewW:int, viewH:int)
		{
			this.graphics.beginFill(0xffffffff, 1);
			this.graphics.drawRect(0, 0, viewW, viewH);
			this.graphics.endFill();

			this.scrollRect = new Rectangle(0, 0, viewW, viewH);

		}
		
		public function addChildW(child:DisplayObject) : void
		{
			if (numChildren > 0) {
				var last : DisplayObject = getChildAt(numChildren-1);
				child.x = last.x + last.width;
				child.y = 0;
			} else {
				child.x = 0;
				child.y = 0;
			}
			super.addChild(child);
		}
			
		public function addChildH(child:DisplayObject) : void
		{
			if (numChildren > 0) {
				var last : DisplayObject = getChildAt(numChildren-1);
				child.x = 0;
				child.y = last.y + last.height;
			} else {
				child.x = 0;
				child.y = 0;
			}
			super.addChild(child);
		}
		
		public function createLocalBounds() : Rectangle
		{
			var rect : Rectangle = new Rectangle(0, 0, width, height);
			for (var i:int=numChildren-1; i>=0; --i) {
				var o:DisplayObject = getChildAt(i);
				rect = rect.union(new Rectangle(o.x,o.y,o.width,o.height));
			}
			return rect;
		}
		

		protected function setCamera(cx:int, cy:int) : void
		{
			var cb : Rectangle = createLocalBounds();
			var sc : Rectangle = this.scrollRect;
			sc.x = cx;
			sc.y = cy;
			sc.x = Math.min(sc.x, cb.x+cb.width  - sc.width);
			sc.y = Math.min(sc.y, cb.y+cb.height - sc.height);		
			sc.x = Math.max(sc.x, cb.x);
			sc.y = Math.max(sc.y, cb.y);
			this.scrollRect = sc;
		}
		
		protected function moveCamera(dx:int, dy:int) : void
		{
			var cb : Rectangle = createLocalBounds();
			var sc : Rectangle = this.scrollRect;
			sc.x += dx;
			sc.y += dy;
			sc.x = Math.min(sc.x, cb.x+cb.width  - sc.width);
			sc.y = Math.min(sc.y, cb.y+cb.height - sc.height);		
			sc.x = Math.max(sc.x, cb.x);
			sc.y = Math.max(sc.y, cb.y);
			this.scrollRect = sc;
		}

	}
}