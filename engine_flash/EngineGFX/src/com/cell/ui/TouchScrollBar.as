package com.cell.ui
{
	import flash.display.Bitmap;
	import flash.display.DisplayObject;
	import flash.display.Loader;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.geom.Rectangle;

	public class TouchScrollBar extends Sprite
	{
		public static const MODE_HORIZONTAL	: int = 0;
		public static const MODE_VERTICAL	: int = 1;
		

		public var mode : int = MODE_HORIZONTAL;
		
		private var value : Number = 0;
		private var max : Number = 1;
		
		protected var ld_img_head 	: DisplayObject;
		protected var ld_img_strip 	: DisplayObject;
		
		public var scroll_bounds : Rectangle = null;
		
		public function TouchScrollBar(hd:DisplayObject, back:DisplayObject)
		{
			ld_img_head = hd;
			ld_img_strip = back;
			addChild(ld_img_strip);
			addChild(ld_img_head);
			rest();
		}

		public function setValue(v:Number) : void
		{
			if (this.value != v) {
				this.value = v;
				rest();
			}
		}
		
		public function setMax(max:Number) : void
		{
			if (this.max != max) {
				this.max = max;
				rest();
			}
		}
		
		public function rest() : void
		{
			if (mode == MODE_HORIZONTAL) {
				if (scroll_bounds == null) {
					var sx : Number = 0;
					var sw : Number = ld_img_strip.width - ld_img_head.width;
					ld_img_head.x = sx + sw * value / max;
					ld_img_head.y = (ld_img_strip.height-ld_img_head.height)/2;
				} else {
					var sx : Number = scroll_bounds.x;
					var sw : Number = scroll_bounds.width - ld_img_head.width;
					ld_img_head.x = sx + sw * value / max;
					ld_img_head.y = scroll_bounds.y + (scroll_bounds.height-ld_img_head.height)/2;
				}
			}
			else if (mode == MODE_VERTICAL) {
				if (scroll_bounds == null) {
					var sy : Number = 0;
					var sh : Number = ld_img_strip.height - ld_img_head.height;
					ld_img_head.y = sy + sh * value / max;
					ld_img_head.x = (ld_img_strip.width-ld_img_head.width)/2;
				} else {
					var sy : Number = scroll_bounds.y;
					var sh : Number = scroll_bounds.height - scroll_bounds.height;
					ld_img_head.y = sy + sh * value / max;
					ld_img_head.x = scroll_bounds.x + (scroll_bounds.width-ld_img_head.width)/2;
				}
			}
		}
	}
}