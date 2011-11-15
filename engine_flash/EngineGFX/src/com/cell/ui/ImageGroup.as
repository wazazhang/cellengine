package com.cell.ui
{
	import com.cell.io.UrlManager;
	
	import flash.display.DisplayObject;
	import flash.display.Loader;
	import flash.display.Sprite;
	import flash.events.Event;

	public class ImageGroup extends Sprite
	{		
		
		private var _anchor : int = Anchor.ANCHOR_LEFT | Anchor.ANCHOR_TOP;

		public function ImageGroup()
		{
		}
		
		
		public function get anchor() : int
		{
			return this._anchor;
		}
		
		public function set anchor(value:int) : void 
		{
			if (value != _anchor) {
				_anchor = value;
				reset();
			}
		}

		public function reset() : void
		{
			for (var i:int = numChildren-1; i>=0; --i) {
				Anchor.setAnchorPos(getChildAt(i), _anchor);
			}
		}
		
		private function loaded(e:Event) : void
		{
			this.reset();
		}
		
		public static function createImageGroupClass(anchor:int, ... items) : ImageGroup
		{
			var ret : ImageGroup = new ImageGroup();
			for each(var o : Class in items) {
				ret.addChild(new o() as DisplayObject);
			}
			ret.anchor = anchor;
			return ret;
		}

		public static function createImageGroupUrl(anchor:int, ... urls) : ImageGroup
		{
			var ret : ImageGroup = new ImageGroup();
			for each(var o : String in urls) {
				var ld : Loader = UrlManager.createLoader();
				ld.load(UrlManager.getUrl(o));
				ld.contentLoaderInfo.addEventListener(Event.COMPLETE, ret.loaded);
				ret.addChild(ld);
//				ret.addChild(new o() as DisplayObject);
				
			}
			ret.anchor = anchor;
			return ret;
		}

	}
}