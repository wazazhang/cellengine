package com.cell.ui
{
	import com.cell.io.UrlManager;
	
	import flash.display.DisplayObject;
	import flash.display.Loader;
	import flash.display.LoaderInfo;
	import flash.events.Event;
	import flash.net.URLRequest;

	public class ImageGallery extends SingleViewPanel
	{
		public function ImageGallery(viewW:int, viewH:int, backcolor:int=0, backalpha:Number=0)
		{
			super(viewW, viewH, backcolor, backalpha);
		}
		
		private function loaded(e:Event) : void
		{
			setMode(mode);
		}
		
		public static function createImageGalleryUrl(viewW:int, viewH:int, images_url : Array) : ImageGallery
		{
			var ret : ImageGallery = new ImageGallery(viewW, viewH);
			
			for each (var str : String in images_url) {
				var loader : Loader = UrlManager.createLoader();
				loader.contentLoaderInfo.addEventListener(Event.COMPLETE, ret.loaded);
				loader.load(UrlManager.getUrl(str));
				ret.addChildW(loader);
			}
			
			return ret;
		}
		
		public static function createImageGalleryClass(viewW:int, viewH:int, classes : Array) : ImageGallery
		{
			var ret : ImageGallery = new ImageGallery(viewW, viewH);
			
			for each (var c : Class in classes) {
				ret.addChildW(new c() as DisplayObject);
			}
			ret.setMode(ret.mode);
			return ret;
		}
	}
}