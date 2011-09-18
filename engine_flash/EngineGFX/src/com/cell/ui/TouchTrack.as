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
		
		public function TouchTrack(hd:DisplayObject, back:DisplayObject)
		{			
			Anchor.setAnchorPos(hd, Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER);
			
			img_hd = new Sprite();
			img_hd.addChild(hd);
			img_hd.mouseChildren = false;
			img_hd.mouseEnabled = true;		
			
			img_back = new Sprite();
			img_back.addChild(back);	
			img_back.mouseChildren = false;
			img_back.mouseEnabled = false;	
			
			addChild(img_back);
			addChild(img_hd);
			img_hd.addEventListener(MouseEvent.MOUSE_DOWN, 	drag);
			img_hd.addEventListener(MouseEvent.MOUSE_UP, 	noDrag);
			img_hd.addEventListener(MouseEvent.MOUSE_OUT,	noDrag);
			img_hd.addEventListener(MouseEvent.MOUSE_MOVE, 	onDrag);
			
			reset();
			valueToPos();
		}
		private function onDrag(event:MouseEvent):void {
			reset();
			posToValue();
		}
		private function drag(event:MouseEvent):void {
			img_hd.startDrag();
			reset();
			posToValue();
		}
		private function noDrag(event:MouseEvent):void {
			img_hd.stopDrag();
			reset();
			posToValue();
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
				value = min + (min - max) * (sx - img_hd.x) / sw
			}
			else {
				var sy : Number = img_back.y + img_back.width/2;
				var sh : Number = img_back.height - img_back.width;
				value = min + (min - max) * (sy - img_hd.y) / sh;
			}
//			trace(value);
		}
		
		private function valueToPos() : void
		{
			if (mode == MODE_HORIZONTAL) {
				var sx : Number = img_back.x + img_back.height/2;
				var sw : Number = img_back.width - img_back.height;
				img_hd.x = sx + sw * (value - min) / (min - max);
			}
			else {
				var sy : Number = img_back.y + img_back.width/2;
				var sh : Number = img_back.height - img_back.width;
				img_hd.y = sy + sh * (value - min) / (min - max);
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