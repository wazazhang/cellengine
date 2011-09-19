package com.cell.gameedit.output
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gfx.game.CImage;
	import com.cell.gfx.game.IGraphics;
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
	
	public class XmlUrlTiles extends XmlCTiles
	{
		private var loader 		: Loader;
		
		public function XmlUrlTiles(output:XmlUrlOutputLoader, img:ImagesSet, ld:Loader)
		{
			super(output, img);
			var url:String = output.path_root + img.Name + "." + output.getImageExtentions();
			this.loader = ld;
			this.loader.contentLoaderInfo.addEventListener(Event.COMPLETE, complete);  
			this.loader.load(UrlManager.getUrl(url));
		}
		
		private function complete(e:Event) : void
		{
			var data : BitmapData = (loader.content as Bitmap).bitmapData;
			initAllImages(data);
			this.loader = null;
		}
	}
}