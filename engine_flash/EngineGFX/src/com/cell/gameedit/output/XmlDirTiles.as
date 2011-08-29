package com.cell.gameedit.output
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gfx.game.CImage;
	import com.cell.gfx.game.IGraphics;
	import com.cell.gfx.game.IImageObserver;
	import com.cell.gfx.game.IImages;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Loader;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.net.URLRequest;
	
	public class XmlDirTiles implements IImages
	{
		protected var output	: XmlOutputLoader;
		protected var img		: ImagesSet;
		protected var tiles 	: Array;
		
		private var loader 		: Loader;
		private var loader_wait	: Array;
		
		public function XmlDirTiles(output:XmlOutputLoader, img:ImagesSet, clone:Boolean=false)
		{
			this.output	= output;
			this.img	= img;
			this.tiles	= new Array(img.Count);
			
			if (!clone) {
				for (var i:int=0; i<img.Count; i++){
					if (img.ClipsW[i] > 0 && img.ClipsH[i] > 0) {
						tiles[i] = createTileImage(i, img.ClipsW[i], img.ClipsH[i], img.ClipsKey[i]);
//						new CImage(new BitmapData(
//							img.ClipsW[i], 
//							img.ClipsH[i],
//							true));
					}
				}
				var url:String = output.path_root + img.Name + "." + output.getImageExtentions();
				if (output.url_wrapper!=null) {
					url = output.url_wrapper.getResourceUrl(url);
				}
				this.loader = new Loader();
				this.loader_wait = new Array();
				this.loader.contentLoaderInfo.addEventListener(Event.COMPLETE, complete);  
				this.loader.load(new URLRequest(url));
			}
			
		}
		
		protected function createTileImage(tileid:int, width:int, height:int, key:String) : CImage
		{
			return new CImage(new BitmapData(
				width, 
				height,
				true));
		}
		
		private function complete(e:Event) : void
		{
			var data : BitmapData = (loader.content as Bitmap).bitmapData;
			for (var i:int=0; i<img.Count; i++){
				if (img.ClipsW[i] > 0 && img.ClipsH[i] > 0) {
					(tiles[i] as CImage).getSrc().copyPixels(
						data, 
						new Rectangle(img.ClipsX[i], img.ClipsY[i], img.ClipsW[i], img.ClipsH[i]), 
						new Point(0, 0), 
						null, null, false);
				}
			}
			this.loader = null;
			var event : ResourceEvent = new ResourceEvent(ResourceEvent.IMAGES_LOADED);
			event.images = this;
			event.images_set = img;
			for each (var f:IImageObserver in loader_wait) {
				f.imagesLoaded(event);
			}
			this.loader_wait = null;
		}
		
		public function clone() : IImages
		{
			var ret : XmlDirTiles = new XmlDirTiles(output, img, true);
			for (var i:int=0; i<tiles.length; i++) {
				if (this.tiles[i] != null) {
					ret.tiles[i] = this.tiles[i].clone();
				}
			}
			return ret;
		}
		public function addImageObserver(listener:IImageObserver):void
		{
			if (loader != null) {
				loader_wait.push(listener);
			}
		}
		
		
		public function getImage(index:int) : CImage
		{
			if (tiles[index] != null) {
				return (tiles[index] as CImage);
			}
			return null;
		}
		
		public function getWidth(index:int) : int
		{
			if (tiles[index] != null) {
				return (tiles[index] as CImage).width;
			}
			return 0;
		}
		
		public function getHeight(index:int) : int
		{
			if (tiles[index] != null) {
				return (tiles[index] as CImage).height;
			}
			return 0;
		}
		
		public function render(g:IGraphics, index:int, x:int, y:int, w:int, h:int, transform:int) : void 
		{
			if (tiles[index]!=null){
				g.drawImage(tiles[index], x, y, w, h, transform);
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
	}
}