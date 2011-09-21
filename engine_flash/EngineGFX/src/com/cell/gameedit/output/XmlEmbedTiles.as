package com.cell.gameedit.output
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gfx.game.CImage;
	import com.cell.gfx.game.IGraphics;
	import com.cell.gfx.game.IImages;
	import com.cell.io.UrlManager;
	import com.cell.util.StringUtil;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Loader;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.net.URLRequest;
	
	public class XmlEmbedTiles extends XmlCTiles
	{
		public function XmlEmbedTiles(output:XmlEmbedOutputLoader, img:ImagesSet)
		{
			super(output, img);
			
			if (img.CustomOut.indexOf("tile")>=0) {
				trace("init tile images!");
				initAllImagesTile(output);
			} else {
				var src : Bitmap = output.getImageClass(img);
				initAllImagesGroup(src.bitmapData);
			}
		}
		
		protected function initAllImagesTile(output:XmlEmbedOutputLoader) : void
		{
			for (var i:int=0; i<img.Count; i++){
				if (img.ClipsW[i] > 0 && img.ClipsH[i] > 0) {
					var dtile : BitmapData = (tiles[i] as CImage).getSrc();
					var stile : BitmapData = output.getTileClass(img, i).bitmapData;
					dtile.copyPixels(
						stile, 
						new Rectangle(0, 0, img.ClipsW[i], img.ClipsH[i]), 
						new Point(0, 0), 
						null, null, false)
				}
			}
		}
	}
}