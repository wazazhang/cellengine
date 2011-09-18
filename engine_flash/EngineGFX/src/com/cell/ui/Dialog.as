package com.cell.ui
{
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.Sprite;
	import flash.display.Stage;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.geom.Rectangle;

	public class Dialog extends Sprite
	{
		var src : DisplayObject;
		var dst : DisplayObjectContainer;
		
		public function Dialog(src:DisplayObject, 
							   dst:DisplayObjectContainer,
									 bg_color:int=0xffffff, 
									 bg_alpha:Number = 0.01)
		{
			this.src = src;
			this.dst = dst;
			src.addEventListener(Event.REMOVED_FROM_STAGE, srcclose);
			
			var drect : Rectangle = dst.getBounds(dst);
			
			this.graphics.beginFill(bg_color&0xffffff, Math.max(bg_alpha, 0.001));
			this.graphics.drawRect(0, 0, drect.width, drect.height);
			this.graphics.endFill();
			this.addEventListener(MouseEvent.MOUSE_DOWN, close);
			
			this.x = drect.x;
			this.y = drect.y;
		}
		
		
		private function srcclose(e:Event) : void
		{
			src.removeEventListener(Event.REMOVED_FROM_STAGE, srcclose);
			dst.removeChild(this);
		}
		
		private function close(e:MouseEvent) : void
		{
			var stgp : Point = new Point(dst.mouseX, dst.mouseY);
			var srcrect : Rectangle = src.getBounds(dst);
			if (srcrect.containsPoint(stgp)) {
			} else if (dst.contains(src)) {
				dst.removeChild(src);
			}
		}
		
		public static function showAsDialog(src:DisplayObject, 
										  dst:DisplayObjectContainer, 
										  bg_color:int=0xffffff, 
										  bg_alpha:Number = 0.01) : void
		{
			dst.addChild(new Dialog(src, dst, bg_color, bg_alpha))
			dst.addChild(src);
		}
		
	}
}