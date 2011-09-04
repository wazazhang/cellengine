package com.cell.ui
{
	import flash.display.Loader;
	import flash.display.LoaderInfo;
	import flash.events.Event;
	import flash.net.URLRequest;

	public class ImageGallery extends SingleViewPanel
	{
		private var images_url : Array;
		
		public function ImageGallery(viewW:int, viewH:int, images_url : Array)
		{
			super(viewW, viewH);
			
			this.images_url = images_url;
			
			for each (var str : String in images_url) {
				var loader : Loader = new Loader();
				loader.contentLoaderInfo.addEventListener(Event.COMPLETE, loaded);
				loader.load(new URLRequest(str));
				this.addChildW(loader);
			}
		}
		
		private function loaded(e:Event) : void
		{
//			this.addChildW((e.target as LoaderInfo).content);
			setMode(mode);
		}

	}
}