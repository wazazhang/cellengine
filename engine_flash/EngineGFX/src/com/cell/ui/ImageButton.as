package com.cell.ui
{
	import com.cell.io.UrlManager;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.display.Loader;
	import flash.display.SimpleButton;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.net.URLRequest;

	public class ImageButton extends SimpleButton
	{
		internal var img_up 		: DisplayObject;
		internal var img_down 	: DisplayObject;
				
		internal var _anchor : int = Anchor.ANCHOR_LEFT | Anchor.ANCHOR_TOP;
		
		public function ImageButton(unsel:DisplayObject, sel:DisplayObject)
		{
			super(unsel, unsel, sel, unsel);
			super.useHandCursor = false;
			this.img_up = unsel;
			this.img_down = sel;
			Anchor.setAnchorPos(img_up, _anchor);
			Anchor.setAnchorPos(img_down, _anchor);
		}
		
		public function get anchor() : int
		{
			return this._anchor;
		}
		
		public function set anchor(value:int) : void 
		{
			if (value != _anchor) {
				_anchor = value;
				Anchor.setAnchorPos(img_up, _anchor);
				Anchor.setAnchorPos(img_down, _anchor);
			}
		}

		
		public static function createImageButtonClass(up_c:Class, down_c:Class) : ImageButton
		{
			return new ImageButton(new up_c() as DisplayObject, new down_c() as DisplayObject);
		}
		
		public static function createImageButtonScale(img:Class, down_scale:Number = 1.2) : ImageButton
		{
			var c1 : DisplayObject = new img() as DisplayObject;
			var c2 : DisplayObject = new img() as DisplayObject;
			c2.scaleX = down_scale;
			c2.scaleY = down_scale;
			return new ImageButton(c1, c2);
		}
		
		public static function createImageButtonScaleBitmap(img:BitmapData, down_scale:Number = 1.2) : ImageButton
		{
			var c1 : DisplayObject = new Bitmap(img);
			var c2 : DisplayObject = new Bitmap(img);
			c2.scaleX = down_scale;
			c2.scaleY = down_scale;
			return new ImageButton(c1, c2);
		}
	}
}