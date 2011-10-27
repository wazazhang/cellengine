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
		private var _anchor : int = 0;
		
		private var o:DisplayObject;
		
		public function ImageBox(o:DisplayObject, anchor:int = 0x11)
		{
			this._anchor = anchor;
			this.o = o;
//			this.cacheAsBitmap = true;
			addChild(o);
			Anchor.setAnchorPos(o, _anchor);
		}
		
		override protected function update(e:Event) : void
		{
		}
				
		public function set anchor(a:int) : void
		{
			if (_anchor != a) {
				_anchor = a;
				Anchor.setAnchorPos(o, _anchor);
			}
		}
		
		public function get anchor() : int
		{
			return _anchor;
		}
		
		public static function createImageBox(url:String, anchor:int = 0x11) : ImageBox
		{
			var ld : Loader = new Loader();
			var ret : ImageBox = new ImageBox(ld, anchor);
			ld.contentLoaderInfo.addEventListener(
				Event.COMPLETE, 
				function loaded(e:Event):void{
					ret.anchor = anchor;
				}
			);
			ld.load(UrlManager.getUrl(url));
			return ret;
		}
		
		public static function createImageBoxClass(c:Class, anchor:int = 0x11) : ImageBox
		{
			return new ImageBox(new c() as DisplayObject, anchor);
		}
	}
}