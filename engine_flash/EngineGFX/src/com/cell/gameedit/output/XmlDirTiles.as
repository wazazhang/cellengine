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
		protected var output	: XmlOutputLoader;
		protected var img		: ImagesSet;
		protected var tiles 	: Array;
		
		private var loader 	: Loader;
		
		public function XmlDirTiles(output:XmlOutputLoader, img:ImagesSet, clone:Boolean=false)
		{
			this.output	= output;
			this.img	= img;
			this.tiles	= new Array(img.Count);
			
			if (!clone) {
				for (var i:int=0; i<img.Count; i++){
					if (img.ClipsW[i] > 0 && img.ClipsH[i] > 0) {
						tiles[i] = new CImage(new BitmapData(
							img.ClipsW[i], 
							img.ClipsH[i],
							true));
					}
				}
				var url:String = output.path_root + img.Name + "." + output.getImageExtentions();
				this.loader = new Loader();
				this.loader.contentLoaderInfo.addEventListener(Event.COMPLETE, complete);  
				this.loader.load(new URLRequest(url));
			}
			
		}
		
		private function complete(e:Event) : void
		{
			var cimage : CImage = new CImage((loader.content as Bitmap).bitmapData);
			for (var i:int=0; i<img.Count; i++){
				if (img.ClipsW[i] > 0 && img.ClipsH[i] > 0) {
					tiles[i] = cimage.subImage(
						img.ClipsX[i],
						img.ClipsY[i],
						img.ClipsW[i],
						img.ClipsH[i]
						);
				}
			}
			this.loader = null;
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
		
		public function render(g:CGraphics, index:int, x:int, y:int, transform:int) : void 
		{
			if (tiles[index]!=null){
				g.drawImage(tiles[index], x, y, transform);
			}
		}
		
		
		public function copyTo(dst:CImage, index:int, x:int, y:int, transform:int) : void 
		{
			if (tiles[index]!=null){
				

			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
	}
}