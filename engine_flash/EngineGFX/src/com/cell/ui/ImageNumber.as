package com.cell.ui
{
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.BitmapDataChannel;
	import flash.display.Sprite;
	import flash.geom.Point;
	import flash.geom.Rectangle;

	public class ImageNumber extends Sprite
	{		
		public static const C_ZE = '0'.charCodeAt(0);
		public static const C_NG = '-'.charCodeAt(0);
		public static const C_PT = '.'.charCodeAt(0);
		
		
		var fw : int ;
		var fh : int ;
		var tiles:Vector.<BitmapData>;
		
		var _buff : Bitmap = new Bitmap();
		var _size : Number = 0;
		var _number : Number = 0;
		var _anchor : int = Anchor.ANCHOR_LEFT | Anchor.ANCHOR_TOP;
		
		var color : int = 0;
		
		/**图片按自上而下或从左到右顺序排列，内容为[0123456789-.]*/
		public function ImageNumber(src:BitmapData, sx:int, sy:int, fw:int, fh:int)
		{
			super.mouseChildren = false;
			super.mouseEnabled = false;
			addChild(_buff);
			if (src != null) {
				this.fw = fw;
				this.fh = fh;
				this.tiles = new Vector.<BitmapData>();
				for (var y:int=sy; y<src.height; y+=fh) {
					for (var x:int=sx; x<src.width; x+=fw) {
						var t : BitmapData = new BitmapData(fw, fh, true, 0x00000000);
						t.copyPixels(src,
							new Rectangle(x, y, fw, fh),
							new Point(0, 0), 
							null, null, true);
						tiles.push(t);
					}
				}
				size = (fh);
				number = (0);
			}
		}
		
		
		public function setColor(c:int) : void
		{
			if (color != c) {
				this.color = c;
				var btiles:Vector.<BitmapData> = new Vector.<BitmapData>(tiles.length);
				for (var i:int=tiles.length-1; i>=0; --i) {
					var t: BitmapData = tiles[i];
					var dt : BitmapData = new BitmapData(fw, fh, true, color);
//					dt.copyPixels(
//						dt,
//						new Rectangle(0, 0, fw, fh),
//						new Point(0, 0), 
//						t, 
//						new Point(0, 0),
//						true);
					dt.copyChannel(
						t,
						new Rectangle(0, 0, fw, fh),
						new Point(0, 0), 
						BitmapDataChannel.ALPHA, 
						BitmapDataChannel.ALPHA);
					btiles[i] = dt;
				}
				this.tiles = btiles;
				reset();
			}
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
				this.scaleY = this.scaleX = s / fh;
			}
		}
		
		/////////////////////////////////////////////////////////////////
		
		private function reset() : void
		{
			var nstr:String = _number.toString();
			var ix : int = 0;
			var buff : BitmapData =new BitmapData(fw*nstr.length, fh, true, 0x00000000);
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
				buff.copyPixels(m, new Rectangle(0,0,fw,fh), new Point(ix, 0), null, null, true);
				//numberIImage.render(cg,int(nstr.charAt(i)),x+i*14 ,y,14,19,0);
				ix += fw;
			}

			
			
			var sx : int = 0;
			var sy : int = 0;
			if ((_anchor & Anchor.ANCHOR_HCENTER) != 0) {
				sx = -nstr.length * fw / 2;
			}
			else if ((_anchor & Anchor.ANCHOR_RIGHT) != 0) {
				sx = -nstr.length * fw;
			}
			if ((_anchor & Anchor.ANCHOR_VCENTER) != 0) {
				sy = -fh / 2;
			}
			else if ((_anchor & Anchor.ANCHOR_BOTTOM) != 0) {
				sy = -fh;
			}
			
			this._buff.bitmapData=buff;
			this._buff.x = sx;
			this._buff.y = sy;
			
//			this.graphics.clear();
//			this.graphics.beginFill(0xff0000, alpha);
//			this.graphics.drawRect(_buff.x, _buff.y, _buff.width, _buff.height);
//			this.graphics.endFill();
//			this.mask = _buff;
		}
		
		public function copy(v:Number, s:Number=0) : ImageNumber
		{
			var ret : ImageNumber = new ImageNumber(null,0,0,0,0);
			ret.tiles = this.tiles;
			ret.fw = this.fw;
			ret.fh = this.fh;
			
			ret.number = v;
			if (size > 0) {
				ret.size = s;
			} else {
				ret.size = this.size;
			}
			ret.reset();
			return ret;
		}
	}
}