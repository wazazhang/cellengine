package com.cell.gameedit.output
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gfx.game.CImage;
	import com.cell.gfx.game.IGraphics;
	import com.cell.gfx.game.IImages;
	import com.cell.io.UrlManager;
	import com.cell.util.Map;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Loader;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IOErrorEvent;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.net.URLRequest;
	
	public class XmlUrlTiles extends XmlCTiles
	{
		private var isTile		: Boolean = false;
		
		private var loader 		: Loader;
		
		private var img_complete:Function;
		private var img_error	:Function;
		
		private var tile_count : int = 0;
		private var tile_urls : Vector.<int>;
		private var tile_cur : int;
		
		public function XmlUrlTiles(output:XmlUrlOutputLoader, img:ImagesSet, img_complete:Function, img_error:Function)
		{
			super(output, img);
			
			this.img_complete 	= img_complete;
			this.img_error 		= img_error;
			this.isTile 		= img.CustomOut.indexOf("tile")>=0;
			
			if (isTile)
			{
				trace("init tile images!");
				initAllImagesTile(output);
				startLoadTile();
			}
			else 
			{				
				var url:String = output.path_root + img.Name + "." + output.getImageExtentions();
				
				this.loader = new Loader();
				this.loader.contentLoaderInfo.addEventListener(Event.COMPLETE, 			img_complete);
				this.loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, 	img_error);
				this.loader.contentLoaderInfo.addEventListener(Event.COMPLETE, 			complete);  
				this.loader.load(UrlManager.getUrl(url));
			}
		}
		
		public function getPercent() : Number
		{
			if (isTile) {
				return (tile_count - tile_urls.length) / tile_count;
			} else if (loader != null) {
				return (loader.contentLoaderInfo.bytesLoaded / loader.contentLoaderInfo.bytesTotal);
			} else {
				return 0;
			}
		}
		
		private function complete(e:Event) : void
		{
			var data : BitmapData = (loader.content as Bitmap).bitmapData;
			initAllImagesGroup(data);
		}
		
		protected function initAllImagesTile(output:XmlUrlOutputLoader) : void
		{
			tile_urls = new Vector.<int>();

			for (var i:int=0; i<img.Count; i++){
				if (img.ClipsW[i] > 0 && img.ClipsH[i] > 0) {
					tile_count ++;
					tile_urls.push(i);
				}
			}
		}
		
		private function startLoadTile() : void
		{								
			

			tile_cur = tile_urls[0];
			tile_urls.splice(0, 1);			
			
			var url:String = (output as XmlUrlOutputLoader).path_root + img.Name + "/" + tile_cur + "." + output.getImageExtentions();

			this.loader = new Loader();
			this.loader.contentLoaderInfo.addEventListener(Event.COMPLETE, 			tileLoaded);
			this.loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, 	tileError);
			this.loader.load(UrlManager.getUrl(url));
		}
		
		private function tileLoaded(e:Event) : void 
		{
			var dtile : BitmapData = (tiles[tile_cur] as CImage).getSrc();
			var stile : BitmapData = (loader.content as Bitmap).bitmapData;
			
			dtile.copyPixels(
				stile, 
				new Rectangle(0, 0, img.ClipsW[tile_cur], img.ClipsH[tile_cur]), 
				new Point(0, 0), 
				null, null, false)
			
			if (tile_urls.length == 0) 
			{
				img_complete.call(output, e);
			}
			else 
			{
				startLoadTile();
			}
		}
		
		private function tileError(e:Event) : void 
		{
			img_error.call(output, e);
		}
	}
}