package com.cell.ui
{
	import com.cell.gfx.CellSprite;
	
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Rectangle;

	public class TouchTrack extends CellSprite
	{
		public static const MODE_HORIZONTAL	: int = 0;
		public static const MODE_VERTICAL	: int = 1;
		
		private var mode : int = MODE_HORIZONTAL;
		
		private var value : Number = 0;
		private var max : Number = 1;
		private var min : Number = 0;
				
		private var img_hd		:Sprite; 
		
		private var img_back	:Sprite;
		
		private var hd_down : Boolean = false;
		
		public function TouchTrack(hd:DisplayObject, back:DisplayObject)
		{			
			Anchor.setAnchorPos(hd, Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER);
			
			img_hd = new Sprite();
			img_hd.addChild(hd);
			img_hd.mouseChildren = false;
			img_hd.mouseEnabled = false;		
			
			img_back = new Sprite();
			img_back.addChild(back);	
			img_back.mouseChildren = false;
			img_back.mouseEnabled = false;	
			
			addChild(img_back);
			addChild(img_hd);
			
			addEventListener(MouseEvent.MOUSE_DOWN, 	mouseDown);
			addEventListener(MouseEvent.MOUSE_UP, 		mouseUp);
			addEventListener(MouseEvent.MOUSE_MOVE, 	mouseMove);
			addEventListener(MouseEvent.MOUSE_OUT,		mouseOut);
						
			reset();
			valueToPos();
		}
		
		private function mouseDown(event:MouseEvent):void 
		{
			img_hd.x = mouseX;
			img_hd.y = mouseY;
			reset();
			posToValue();
			
			hd_down = true;
			
		}
		
		private function mouseMove(event:MouseEvent):void 
		{
			if (hd_down) {
				img_hd.x = mouseX;
				img_hd.y = mouseY;
				reset();
				posToValue();
			}
		}
		
		private function mouseUp(event:MouseEvent):void 
		{
			if (hd_down) {
				img_hd.x = mouseX;
				img_hd.y = mouseY;
				reset();
				posToValue();
			}
			hd_down = false;
			
		}
		
		private function mouseOut(event:MouseEvent):void 
		{
			if (hd_down) {
				img_hd.x = mouseX;
				img_hd.y = mouseY;
				reset();
				posToValue();
			}
			hd_down = false;
		}
		
		override protected function update(e:Event):void
		{
			reset();
		}
		
		private function reset() : void
		{
			if (mode == MODE_HORIZONTAL) {
				var sx : Number = img_back.x + img_back.height/2;
				var sw : Number = img_back.width - img_back.height;
				img_hd.x = Math.max(img_hd.x, sx);
				img_hd.x = Math.min(img_hd.x, sx + sw)
				img_hd.y = img_back.y + img_back.height/2;
			}
			else {
				var sy : Number = img_back.y + img_back.width/2;
				var sh : Number = img_back.height - img_back.width;
				img_hd.y = Math.max(img_hd.y, sy);
				img_hd.y = Math.min(img_hd.y, sy + sh)
				img_hd.x = img_back.x + img_back.width/2;
			}
		}
		
		private function posToValue() : void
		{
			if (mode == MODE_HORIZONTAL) {
				var sx : Number = img_back.x + img_back.height/2;
				var sw : Number = img_back.width - img_back.height;
				value = min + (max - min) * (img_hd.x-sx) / sw
			}
			else {
				var sy : Number = img_back.y + img_back.width/2;
				var sh : Number = img_back.height - img_back.width;
				value = min + (max - min) * (img_hd.y-sy) / sh;
			}
//			trace(value);
		}
		
		private function valueToPos() : void
		{
			if (mode == MODE_HORIZONTAL) {
				var sx : Number = img_back.x + img_back.height/2;
				var sw : Number = img_back.width - img_back.height;
				img_hd.x = sx + sw * (value - min) / (max - min);
				img_hd.y = img_back.y + img_back.height/2;
			}
			else {
				var sy : Number = img_back.y + img_back.width/2;
				var sh : Number = img_back.height - img_back.width;
				img_hd.y = sy + sh * (value - min) / (max - min);
				img_hd.x = img_back.x + img_back.width/2;
			}
		}
		
		public function getValue() : Number
		{
			return value;
		}
		
		public function setValue(v:Number) : void
		{
			v = Math.max(min, v);
			v = Math.min(max, v);
			if (this.value != v) {
				this.value = v;
				valueToPos();
			}
		}
		
		public function getMax() : Number
		{
			return max;
		}
		
		public function getMin() : Number
		{
			return min;
		}
		
		public function setRange(min:Number, max:Number) : void
		{			
			max = Math.max(min, max);
			min = Math.min(min, max);
			if (this.max != max || this.min!=min) {
				this.max = max;
				this.min = min;
				valueToPos();
			}
		}
		
		
		
	}
}