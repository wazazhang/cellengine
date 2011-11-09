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
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.net.URLRequest;
	
	public class XmlCTiles implements IImages
	{
		protected var output	: XmlCOutputLoader;
		protected var img		: ImagesSet;
		protected var tiles 	: Array;
		
		public function XmlCTiles(output:XmlCOutputLoader, img:ImagesSet)
		{
			this.output	= output;
			this.img	= img;
			this.tiles	= new Array(img.Count);
			for (var i:int=0; i<img.Count; i++){
				if (img.ClipsW[i] > 0 && img.ClipsH[i] > 0) {
					tiles[i] = output.createTileImage(i, img.ClipsW[i], img.ClipsH[i], img.ClipsKey[i]);
				}
			}
		}
		
		protected function initAllImagesGroup(data : BitmapData) : void
		{
			for (var i:int=0; i<img.Count; i++){
				if (img.ClipsW[i] > 0 && img.ClipsH[i] > 0) {
					(tiles[i] as CImage).getSrc().copyPixels(
						data, 
						new Rectangle(img.ClipsX[i], img.ClipsY[i], img.ClipsW[i], img.ClipsH[i]), 
						new Point(0, 0), 
						null, null, false);
				}
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