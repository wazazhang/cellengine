package com.cell.ui
{
	import com.cell.gfx.CellSprite;
	import com.cell.io.UrlManager;
	
	import flash.display.DisplayObject;
	import flash.display.Loader;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.net.URLRequest;

	public class ImageBox extends CellSprite
	{
		public var anchor : int = 0;
		
		private var o:DisplayObject;
		
		public function ImageBox(o:DisplayObject, anchor:int = 0x11)
		{
			this.anchor = anchor;
			this.o = o;
//			this.cacheAsBitmap = true;
			addChild(o);
		}
		
		override protected function update(e:Event) : void
		{
			Anchor.setAnchorPos(o, anchor);
		}
				
		public static function createImageBox(url:String, anchor:int = 0x11) : ImageBox
		{
			var ld : Loader = new Loader();
			ld.load(UrlManager.getUrl(url));
			return new ImageBox(ld, anchor);
		}
		
		public static function createImageBoxClass(c:Class, anchor:int = 0x11) : ImageBox
		{
			return new ImageBox(new c() as DisplayObject, anchor);
		}
	}
}