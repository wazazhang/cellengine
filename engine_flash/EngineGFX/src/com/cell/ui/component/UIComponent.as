package com.cell.ui.component
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.ui.layout.UIRect;
	
	import flash.display.DisplayObjectContainer;

	public class UIComponent extends CellSprite
	{
		internal var bg : UIRect = null;
		
		public function UIComponent(rect:UIRect)
		{
			this.mouseChildren = false;
			this.mouseEnabled  = false;
			if (rect != null) {
				this.bg = rect;
				this.addChild(bg);
			}
		}
		
		public function setLocation(x:Number, y:Number) : void
		{
			this.x = x;
			this.y = y;
		}
		
		public function setCenter(parent:DisplayObjectContainer) : void
		{
			this.x = parent.width/2  - this.width/2;
			this.y = parent.height/2 - this.height/2;
		}
		
		public function setCenterRect(x:Number, y:Number, w:Number, h:Number) : void
		{
			this.x = x + w/2 - this.width/2;
			this.y = y + h/2 - this.height/2;
		}
		
		public function setSize(w:int, h:int) : void
		{
			this.resize(w, h, false);
		}
		
		/**推荐使用setSize改变尺寸*/
		override public function set width(w:Number) : void
		{
			if (super.width != w) {
				resize(w, height, true);
			}
		}
		
		/**推荐使用setSize改变尺寸*/
		override public function set height(h:Number) : void
		{
			if (super.height != h) {
				resize(width, h, true);
			}
		}
		
		protected function resize(w:int, h:int, flush:Boolean) : Boolean
		{
			if (flush || w != width || h != height) {
				if (bg != null) {
					bg.createBuffer(w, h);
				}
				return true;
			}
			return false;
		}
		
		public function setBG(rect:UIRect) : void
		{
			var w : int = width;
			var h : int = height;
			if (this.bg!=null) {
				this.removeChild(bg);
			}
			this.bg = rect;
			if (rect != null) {
				this.addChildAt(bg, 0);
				resize(w, h, true);
			}
		}
		
		public function getBG() : UIRect
		{
			return bg;
		}
	}
}