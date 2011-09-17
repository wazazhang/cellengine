package com.cell.ui
{
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.geom.Rectangle;
	
	public class SingleViewScrollBar extends Sprite
	{
		private var value : int = 0;
		private var max : int = 1;
		private var min : int = 0;
		
		private var text : ImageNumber;
		
		private var img_hd:BitmapData; 
		
		private var img_back:BitmapData;
		
		public function SingleViewScrollBar(hd:BitmapData, back:BitmapData, txt:ImageNumber)
		{			
			this.mouseChildren = false;
			this.mouseEnabled = false;
			text = txt.copy(0);
			text.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_BOTTOM;
			text.y = -2;
			addChild(text);
			img_hd = hd;
			img_back = back;
			rest();
		}
		
		public function getText() : ImageNumber
		{
			return text;
		}
		
		public function getValue() : int
		{
			return value;
		}
		
		public function setValue(v:int) : void
		{
			v = Math.max(min, v);
			v = Math.min(max, v);
			if (this.value != v) {
				this.value = v;
				rest();
			}
		}
		
		public function getMax() : int
		{
			return max;
		}
		
		public function getMin() : int
		{
			return min;
		}
		
		public function setRange(min:int, max:int) : void
		{			
			max = Math.max(min, max);
			min = Math.min(min, max);
			if (this.max != max || this.min!=min) {
				this.max = max;
				this.min = min;
				rest();
			}
		}
		
		public function rest() : void
		{
			while (numChildren > 0) {
				this.removeChildAt(0);
			}
			
			var count : int = max - min;
			var sw : int = img_hd.width * 2;
			var sx : int = -count * sw / 2 + sw/2;
			
			for (var i:int = min; i<=max; i++) {
				if (i == value) {
					var b : Bitmap = new Bitmap(img_hd);
					text.x = sx + b.width/2;
					b.x = sx;
					addChild(b);
				} else {
					var b : Bitmap = new Bitmap(img_back);
					b.x = sx;
					addChild(b);
				}
				sx += sw;
			}
			
			text.number = value;
			this.addChild(text);

		}
	}
}