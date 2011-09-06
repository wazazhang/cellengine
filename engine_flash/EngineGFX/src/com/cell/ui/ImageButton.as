package com.cell.ui
{
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
			udl.load(new URLRequest(up_url));
			var ddl : Loader = new Loader();
			ddl.load(new URLRequest(down_url));
			return new ImageButton(udl, ddl);
		}


	}
}