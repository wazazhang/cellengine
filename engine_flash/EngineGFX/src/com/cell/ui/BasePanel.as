package com.cell.ui
{
	import com.cell.gfx.CellSprite;
	import com.cell.util.Util;
	
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.geom.Rectangle;

	public class BasePanel extends CellSprite
	{
		private var local_bounds : Rectangle = null;
		
		private var backcolor:int=0;
		private var backalpha:Number=0;
		
		public function BasePanel(viewW:int, viewH:int, backcolor:int=0, backalpha:Number=0)
		{
			this.backalpha = backalpha;
			this.backcolor = backcolor;
			
			resize(viewW, viewH);
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
				
		public function resize(viewW:int, viewH:int) : void
		{
			var cx : Number = 0;
			var cy : Number = 0;
			if (this.scrollRect!=null) {
				cx = this.scrollRect.x;
				cy = this.scrollRect.y;
			}
			this.scrollRect = new Rectangle(cx, cy, viewW, viewH);
			if (backalpha>0) {
				this.graphics.clear();
				this.graphics.beginFill(backcolor, backalpha);
				this.graphics.drawRect(0, 0, viewW, viewH);
				this.graphics.endFill();
			}
			Util.updateScrollRect(this);
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
			local_bounds = rect.clone();
			return rect;
		}
		
		public function getLocalBounds() : Rectangle 
		{
			if (local_bounds == null) {
				createLocalBounds();
			}
			return local_bounds;
		}
		
		public function getChildBounds() : Rectangle
		{
			var rect : Rectangle = null;
			for (var i:int=numChildren-1; i>=0; --i) {
				var o:DisplayObject = getChildAt(i);
				if (rect == null) {
					rect = new Rectangle(o.x, o.y, o.width, o.height);
				} else {
					rect = rect.union(new Rectangle(o.x, o.y, o.width, o.height));
				}
			}
			if (rect == null) {
				rect = new Rectangle(0, 0, width, height);
			}
			return rect;
		}
		
		public function setCamera(cx:int, cy:int) : void
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
		
		public function moveCamera(dx:int, dy:int) : void
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

		public function getCamera() : Rectangle
		{
			var sc : Rectangle = this.scrollRect;
			return new Rectangle(sc.x, sc.y, sc.width, sc.height);
		}
	}
}