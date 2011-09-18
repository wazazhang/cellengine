package com.cell.ui
{
	import flash.display.DisplayObject;
	import flash.display.Sprite;

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
				for (var i:int = numChildren-1; i>=0; --i) {
					Anchor.setAnchorPos(getChildAt(i), _anchor);
				}
			}
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

		
	}
}