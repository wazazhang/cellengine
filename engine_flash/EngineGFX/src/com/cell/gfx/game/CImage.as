package com.cell.gfx.game
{
	import com.cell.io.UrlManager;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Loader;
	import flash.events.Event;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.net.URLRequest;

	public class CImage
	{
		internal var src : BitmapData;
		
		private var loader 	: Loader = null;
		
		public function CImage(src : BitmapData)
		{
			this.src = src;
		}
		
		public function getSrc() : BitmapData
		{
			return this.src ;
		}
		
		public function get width() : int {
			return src.width;
		}
		
		public function get height() : int {
			return src.height;
		}
		
		public function clone() : CImage {
			return new CImage(src);
		}
		
		public function subImage(x:int, y:int, width:int, height:int) : CImage
		{
			var sub : BitmapData = new BitmapData(width, height, true, 0);
			sub.copyPixels(src, new Rectangle(x, y, width, height), new Point(0, 0));
			return new CImage(sub);
		}
		
		private function load(url:URLRequest) : void
		{
			this.loader = UrlManager.createLoader();
			this.loader.contentLoaderInfo.addEventListener(Event.COMPLETE, complete);  
			this.loader.load(url);
		}
		
		private function complete(e:Event) : void
		{
			var bitmap : Bitmap = (loader.content as Bitmap);
			this.src = bitmap.bitmapData;
			this.loader = null;
		}
		
		public function copyPixels(source:CImage, srcx:int, srcy:int, srcw:int, srch:int, dstx:int, dsty:int) : void
		{
			this.src.copyPixels(source.src, 
				new Rectangle(srcx, srcy, srcw, srch), 
				new Point(dstx, dsty), 
				null, null, false);
		}
		
		public function createGraphics() : IGraphics
		{
			return new CGraphicsBitmap(src);
		}
		
		public static function createImageBuff(width:int, height:int, color:uint=0) : CImage
		{
			return new CImage(new BitmapData(width, height, true, color));
		}
		
		public static function createImage(data:BitmapData) : CImage
		{
			return new CImage(data);
		}
		
		public static function loadImage(url:String, width:int=32, height:int=32) : CImage
		{ 
			var ret : CImage = new CImage(new BitmapData(width, height, true, 0));
			ret.load(new URLRequest(url));
			return ret;
		}

	}
}