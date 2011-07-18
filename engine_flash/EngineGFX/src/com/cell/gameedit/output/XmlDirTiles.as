package com.cell.gameedit.output
{
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gfx.CGraphics;
	import com.cell.gfx.CImage;
	import com.cell.gfx.game.CImages;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Loader;
	import flash.events.Event;
	import flash.net.URLRequest;

	public class XmlDirTiles implements CImages
	{
		protected var output	: XmlOutput;
		protected var img		: ImagesSet;
		protected var tiles 	: Array;
		
		private var loader 	: Loader;
		private var urlreq	: URLRequest;
		
		public function XmlDirTiles(output:XmlOutput, img:ImagesSet, clone:Boolean=false)
		{
			this.output	= output;
			this.img	= img;
			this.tiles	= new Array(img.Count);
			
			if (!clone) {
				for (var i:int=0; i<img.Count; i++){
					if (img.ClipsW[i] > 0 && img.ClipsH[i] > 0) {
						var tile : CImage = new CImage(new BitmapData(
							img.ClipsW[i], 
							img.ClipsH[i],
							true));
					}
				}
				var url:String = output.path_root + img.Name + "." + output.getImageExtentions();
				trace(url);
				this.loader = new Loader();
				this.urlreq = new URLRequest(url);
				this.loader.contentLoaderInfo.addEventListener(Event.COMPLETE, complete);  
				this.loader.load(urlreq);
			}
			
		}
		
		private function complete(e:Event) : void
		{
			(loader.content as Bitmap);
		}
		
		
		public function clone() : CImages
		{
			var ret : XmlDirTiles = new XmlDirTiles(output, img, true);
			for (var i:int=0; i<tiles.length; i++) {
				if (this.tiles[i] != null) {
					ret.tiles[i] = this.tiles[i].clone();
				}
			}
			return ret;
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
		
		public function render(g:CGraphics, index:int, x:int, y:int, transform:int) : void {
			if (tiles[index]!=null){
				g.drawImage(tiles[index], x, y, transform);
			}
		}
	}
}