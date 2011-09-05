package com.cell.ui
{
	import flash.display.DisplayObject;
	import flash.display.Loader;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.net.URLRequest;

	public class ImageBox extends Sprite
	{
		public static const ANCHOR_LEFT 	: int = 0x00;
		public static const ANCHOR_HCENTER 	: int = 0x01;
		public static const ANCHOR_RIGHT 	: int = 0x02;
		
		public static const ANCHOR_TOP	 	: int = 0x00;
		public static const ANCHOR_VCENTER 	: int = 0x10;
		public static const ANCHOR_BOTTOM	: int = 0x20;
		
		public var anchor : int = 0;
		
		private var o:DisplayObject;
		
		public function ImageBox(o:DisplayObject, anchor:int = 0x11)
		{
			this.anchor = anchor;
			this.o = o;
			this.cacheAsBitmap = true;
			addChild(o);
			addEventListener(Event.ENTER_FRAME, update);
		}
		
		private function update(e:Event) : void
		{
			if ((anchor & ANCHOR_HCENTER)!=0) {
				o.x = -o.width/2;
			}
			else if ((anchor & ANCHOR_RIGHT)!=0) {
				o.x = -o.width;
			}
			else {
				o.x = 0;
			}
			if ((anchor & ANCHOR_VCENTER)!=0) {
				o.y = -o.height/2;
			}
			else if ((anchor & ANCHOR_BOTTOM)!=0) {
				o.y = -o.height;
			}
			else {
				o.y = 0;
			}
		}
				
		public static function createImageBox(url:String, anchor:int = 0x11) : ImageBox
		{
			var ld : Loader = new Loader();
			ld.load(new URLRequest(url));
			return new ImageBox(ld, anchor);
		}
		
	}
}