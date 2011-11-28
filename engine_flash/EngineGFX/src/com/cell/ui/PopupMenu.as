package com.cell.ui
{
	import com.cell.gfx.CellSprite;
	import com.cell.math.MathVector;
	import com.cell.util.Map;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;

	public class PopupMenu extends CellSprite
	{
		public static const MODE_UP : int = 0;
		public static const MODE_DOWN : int = 1;
		public static const MODE_LEFT : int = 2;
		public static const MODE_RIGHT : int = 3;
		
		public static const MODE_HCENTER : int = 4;
		public static const MODE_VCENTER : int = 5;
		
		public static const MODE_ROUND : int = 5;
		
		private var items 	: Vector.<DisplayObject> = new Vector.<DisplayObject>();
		private var itemsp 	: Vector.<Point> = new Vector.<Point>();
		private var basebtn : DisplayObject;
		private var opening : Boolean = false;
		private var closing : Boolean = false;
		private var opened	: Boolean = false;
		
		public var mode : int = MODE_UP;
		
		public var blank : int = 1;
		
		public function PopupMenu(basebtn:DisplayObject, ... items)
		{
			this.basebtn = basebtn;
			for each (var o : DisplayObject in items) {
				o.visible = false;
				this.items.push(o);
				this.itemsp.push(new Point(0, 0));
				this.addChild(o);
			}
			this.addChild(basebtn);
			
			basebtn.addEventListener(MouseEvent.MOUSE_DOWN, onMouseDown);
		}
		
		private function onMouseDown(e:MouseEvent) : void
		{
			if (opened) {
				close();
			} else {
				open();
			}
		}
		
		public function isOpened() : Boolean
		{
			return opened;
		}
		
		public function open() : void
		{
			opened = false;
			opening = true;
			closing = false;
			
			var totalW : int = 0;
			var totalH : int = 0;
			for (var i : int =0; i<items.length; i++) {				
				var o : DisplayObject = items[i];
				switch (mode) {
					case MODE_UP:
					case MODE_DOWN:
					case MODE_VCENTER:
						totalW += o.width + blank;
						totalH = Math.max(o.height, totalH);
						break;
					case MODE_LEFT:
					case MODE_RIGHT:
					case MODE_HCENTER:
						totalW = Math.max(o.width, totalW);
						totalH += o.height + blank;
						break;
				}
			}
			
			var sx : int = basebtn.x;
			var sy : int = basebtn.y;
			switch (mode) {
			case MODE_UP:
				sy -= basebtn.height;
				break;
			case MODE_DOWN:
				sy += basebtn.height;
				break;
			case MODE_LEFT:
				sx -= basebtn.width;
				break;
			case MODE_RIGHT:
				sx += basebtn.width;
				break;
			case MODE_HCENTER:
				sx -= totalW/2;
				break;
			case MODE_VCENTER:
				sy -= totalH/2;
				break;
			}
			
			for (var i : int =0; i<items.length; i++)
			{
				var o : DisplayObject = items[i];
				var p : Point = itemsp[i];
				o.visible = true;
				
				switch (mode) {
				case MODE_UP:
					p.x = sx;
					p.y = sy;
					sy -= o.height + blank;
					break;
				case MODE_DOWN:
					p.x = sx;
					p.y = sy;
					sy += o.height + blank;
					break;
				case MODE_LEFT:
					p.x = sx;
					p.y = sy;
					sx -= o.width + blank;
					break;
				case MODE_RIGHT:
					p.x = sx;
					p.y = sy;
					sx += o.width + blank;
					break;
				
				case MODE_HCENTER:
					p.x = sx;
					p.y = sy;
					sx += o.width + blank;
					break;
				
				case MODE_VCENTER:
					p.x = sx;
					p.y = sy;
					sy += o.height + blank;
					break;
				}
			}
			
		}
		
		public function close() : void
		{
			opened = false;
			opening = false;
			closing = true;
			
			for (var i : int =0; i<items.length; i++)
			{
				var o : DisplayObject = items[i];
				var p : Point = itemsp[i];

				p.x = basebtn.x;
				p.y = basebtn.y;
			}
		}
		
		private function closed() : void
		{
			
			for (var i : int =0; i<items.length; i++)
			{
				var o : DisplayObject = items[i];
				o.visible = false;
			}
		}
		
		override protected function update(e:Event):void
		{
			if (opening || closing) 
			{
				var alldonea:Boolean = true;
				
				for (var i : int =0; i<items.length; i++)
				{
					var o : DisplayObject = items[i];
					var p : Point = itemsp[i];

					var d : Number = MathVector.getDistance2Point(p.x, p.y, o.x, o.y);
					if (!MathVector.smoveTo(o, p.x, p.y, Math.max(d/2, 1))) {
						alldonea = false;
					}
				}
				if (alldonea) {
					if (opening) {
						opened = true;
					}
					if (closing) {
						closed();
					}
					opening = false;
					closing = false;
				}
			}
			
		}
		
		
		
	}
}