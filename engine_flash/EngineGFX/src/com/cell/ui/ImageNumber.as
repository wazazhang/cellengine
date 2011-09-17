package com.cell.ui
{
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Sprite;
	import flash.geom.Point;
	import flash.geom.Rectangle;

	public class ImageNumber extends Sprite
	{
		var tiles:Vector.<BitmapData> = new Vector.<BitmapData>(12);
		var size : Number = 12;
		var number : Number = 0;
		
		/**图片按自上而下或从左到右顺序排列，内容为[0123456789-.]*/
		public function ImageNumber(src:BitmapData, sx:int, sy:int, fw:int, fh:int)
		{
			if (src != null) {
				for (var y:int=sy; y<src.height; y+=fh) {
					for (var x:int=sx; x<src.width; x+=fw) {
						var t : BitmapData = new BitmapData(fw, fh, true, 0);
						t.copyPixels(src,
							new Rectangle(x, y, fw, fh),
							new Point(0, 0), 
							null, null, true);
						tiles.push(t);
					}
				}
				size = fh;
			}
			setNumber(0);
		}
		
		
		public function setNumber(v:Number)  : void
		{
			
		}
		
		public function setSize(s:Number) : void
		{
			
		}
		
		public function getNumber() : Number
		{
			return number;
		}
		
		public function getSize() : Number
		{
			return size;
		}
		
		public function copy(v:Number, size:Number) : ImageNumber
		{
			var ret : ImageNumber = new ImageNumber(null,0,0,0,0);
			ret.tiles = this.tiles;
			ret.setNumber(v);
			ret.setSize(size);
			return ret;
		}
	}
}