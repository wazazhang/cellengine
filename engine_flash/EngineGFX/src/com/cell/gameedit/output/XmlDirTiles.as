package com.cell.gameedit.output
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gfx.game.CImage;
	import com.cell.gfx.game.IGraphics;
	import com.cell.gfx.game.IImageObserver;
	import com.cell.gfx.game.IImages;
	import com.cell.io.UrlManager;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Loader;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.net.URLRequest;
	
	public class XmlDirTiles extends XmlCTiles
	{
		private var loader 		: Loader;
		private var loader_wait	: Array;
		
		public function XmlDirTiles(output:XmlDirOutputLoader, img:ImagesSet)
		{
			super(output, img);
			var url:String = output.path_root + img.Name + "." + output.getImageExtentions();
			this.loader = new Loader();
			this.loader_wait = new Array();
			this.loader.contentLoaderInfo.addEventListener(Event.COMPLETE, complete);  
			this.loader.load(UrlManager.getUrl(url));
		}
		
		private function complete(e:Event) : void
		{
			var data : BitmapData = (loader.content as Bitmap).bitmapData;
			initAllImages(data);
			this.loader = null;
			var event : ResourceEvent = new ResourceEvent(ResourceEvent.IMAGES_LOADED);
			event.images = this;
			event.images_set = img;
			for each (var f:IImageObserver in loader_wait) {
				f.imagesLoaded(event);
			}
			this.loader_wait = null;
		}
		
		override public function addImageObserver(listener:IImageObserver):void
		{
			if (loader != null) {
				loader_wait.push(listener);
			}
		}
	}
}