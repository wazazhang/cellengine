package com.cell.gameedit.output
{
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gfx.CGraphics;
	import com.cell.gfx.CImage;
	import com.cell.gfx.game.CImages;
	
	import flash.display.Bitmap;
	import flash.display.Loader;
	import flash.events.Event;
	import flash.net.URLRequest;

	public class XmlDirTiles implements CImages
	{
		protected var tiles 	: Array;
		
		protected var output	: XmlOutput;
		protected var set		: ImagesSet;
		
		protected var loader 	: Loader;
		protected var urlreq	: URLRequest;
		
		public function XmlDirTiles(output:XmlOutput, set:ImagesSet, clone:Boolean=false)
		{
			this.output	= output;
			this.set	= set;
			this.tiles	= new Array(set.Count);
			
			if (!clone) {
				var url:String = output.path_root + set.Name + "." + output.getImageExtentions();
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
			var ret : XmlDirTiles = new XmlDirTiles(output, set, true);
			for (var i:int=0; i<tiles.length; i++) {
				if (this.tiles[i] != null) {
					ret.tiles[i] = this.tiles[i].clone();
				}
			}
			return ret;
		}
		
		public function getImage(index:int) : CImage
		{
			return (tiles[index] as CImage);
		}
		
		public function getWidth(index:int) : int
		{
			return (tiles[index] as CImage).width;
		}
		
		public function getHeight(index:int) : int
		{
			return (tiles[index] as CImage).height;
		}
		
		public function render(g:CGraphics, index:int, x:int, y:int, transform:int) : void {
			if (tiles[index]!=null){
				g.drawImage(tiles[index], x, y, transform);
			}
		}
	}
}