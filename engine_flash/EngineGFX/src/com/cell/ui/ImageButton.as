package com.cell.ui
{
	import com.cell.io.UrlManager;
	
	import flash.display.DisplayObject;
	import flash.display.Loader;
	import flash.display.SimpleButton;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.net.URLRequest;

	public class ImageButton extends SimpleButton
	{
		private var img_up 		: DisplayObject;
		private var img_down 	: DisplayObject;
		
		private var value : Boolean = false;
		
		public function ImageButton(unsel:DisplayObject, sel:DisplayObject)
		{
			super(unsel, unsel, sel, unsel);
			super.useHandCursor = false;
		}
		
		public static function createImageButton(up_url:String, down_url:String) : ImageButton
		{
			var udl : Loader = new Loader();
			udl.load(UrlManager.getUrl(up_url));
			var ddl : Loader = new Loader();
			ddl.load(UrlManager.getUrl(down_url));
			return new ImageButton(udl, ddl);
		}

		public static function createImageButtonClass(up_c:Class, down_c:Class) : ImageButton
		{
			return new ImageButton(new up_c() as DisplayObject, new down_c() as DisplayObject);
		}
	}
}