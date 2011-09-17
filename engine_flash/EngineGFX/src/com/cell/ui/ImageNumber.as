package com.cell.ui
{
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Sprite;
	import flash.geom.Point;
	import flash.geom.Rectangle;

	public class ImageNumber extends Bitmap
	{		
		public static const C_ZE = '0'.charCodeAt(0);
		public static const C_NG = '-'.charCodeAt(0);
		public static const C_PT = '.'.charCodeAt(0);
		
		
		var fw : int ;
		var fh : int ;
		var tiles:Vector.<BitmapData>;
		
		var _size : Number = 12;
		var _number : Number = 0;
		var _anchor : int = Anchor.ANCHOR_LEFT | Anchor.ANCHOR_TOP;
		
		
		/**图片按自上而下或从左到右顺序排列，内容为[0123456789-.]*/
		public function ImageNumber(src:BitmapData, sx:int, sy:int, fw:int, fh:int)
		{
			if (src != null) {
				this.fw = fw;
				this.fh = fh;
				this.tiles = new Vector.<BitmapData>(12);
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
			number = (0);
		}
		
		/////////////////////////////////////////////////////////////////
		
		public function get anchor() : int {
			return this._anchor;
		}
		public function set anchor(value:int) : void 
		{
			if (value != _anchor) {
				_anchor = value;
				reset();
			}
		}
		
		/////////////////////////////////////////////////////////////////
		
		public function get number() : Number {
			return _number;
		}
		public function set number(v:Number)  : void
		{
			if (v != _number) {
				_number = v;
				reset();
			}
		}
		
		/////////////////////////////////////////////////////////////////
		
		public function get size() : Number {
			return _size;
		}
		public function set size(s:Number) : void
		{
			if (s != _size) {
				_size = s;
				reset();
			}
		}
		
		/////////////////////////////////////////////////////////////////
		
		private function reset() : void
		{
			
			var nstr:String = _number.toString();
			var sx : int = 0;
			var sy : int = 0;
			
			if (_anchor & Anchor.ANCHOR_HCENTER != 0) {
				sx = -nstr.length * fw / 2;
			}
			if (_anchor & Anchor.ANCHOR_RIGHT != 0) {
				sx = -nstr.length * fw;
			}
			if (_anchor & Anchor.ANCHOR_VCENTER != 0) {
				sx = -fh / 2;
			}
			if (_anchor & Anchor.ANCHOR_BOTTOM != 0) {
				sx = -fh;
			}
			var buff : BitmapData =new BitmapData(fw*nstr.length, fh, true);
			
			for (var i:int=0;i<nstr.length;i++)
			{
				var c : Number = nstr.charCodeAt(i);
				var m : BitmapData = null;
				if (c == C_NG) {
					m = tiles[10];
				} else if (c == C_PT) {
					m = tiles[11];
				} else {
					m = tiles[c - C_ZE];
				}
				buff.copyPixels(m, new Rectangle(0,0,fw,fh), new Point(sx, sy), null, null, true);
				//numberIImage.render(cg,int(nstr.charAt(i)),x+i*14 ,y,14,19,0);
				sx += fw;
			}
			this.bitmapData=buff;
		}
		
		
		public function copy(v:Number, size:Number) : ImageNumber
		{
			var ret : ImageNumber = new ImageNumber(null,0,0,0,0);
			ret.tiles = this.tiles;
			ret.fw = this.fw;
			ret.fh = this.fh;
			
			ret.number = v;
			ret.size = size;

			return ret;
		}
	}
}