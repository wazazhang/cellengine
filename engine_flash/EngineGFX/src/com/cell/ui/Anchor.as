package com.cell.ui
{
	import flash.display.DisplayObject;

	public class Anchor
	{
		public static const ANCHOR_LEFT 	: int = 0x00;
		public static const ANCHOR_HCENTER 	: int = 0x01;
		public static const ANCHOR_RIGHT 	: int = 0x02;
		
		public static const ANCHOR_TOP	 	: int = 0x00;
		public static const ANCHOR_VCENTER 	: int = 0x10;
		public static const ANCHOR_BOTTOM	: int = 0x20;
		
		public static const ANCHOR_CENTER	: int = 0x11;
		
		public static function setAnchorPos(o : DisplayObject, anchor : int) : void
		{
			if ((anchor & Anchor.ANCHOR_HCENTER)!=0) {
				o.x = -o.width/2;
			}
			else if ((anchor & Anchor.ANCHOR_RIGHT)!=0) {
				o.x = -o.width;
			}
			else {
				o.x = 0;
			}
			if ((anchor & Anchor.ANCHOR_VCENTER)!=0) {
				o.y = -o.height/2;
			}
			else if ((anchor & Anchor.ANCHOR_BOTTOM)!=0) {
				o.y = -o.height;
			}
			else {
				o.y = 0;
			}
		}
		
		public static function setAnchorRect(o : DisplayObject, anchor : int, width:int, height:int) : void
		{
			if ((anchor & Anchor.ANCHOR_HCENTER)!=0) {
				o.x = width/2-o.width/2;
			}
			else if ((anchor & Anchor.ANCHOR_RIGHT)!=0) {
				o.x = width-o.width;
			}
			else {
				o.x = 0;
			}
			if ((anchor & Anchor.ANCHOR_VCENTER)!=0) {
				o.y = height/2-o.height/2;
			}
			else if ((anchor & Anchor.ANCHOR_BOTTOM)!=0) {
				o.y = height-o.height;
			}
			else {
				o.y = 0;
			}
		}
		
		
		

		
	}
}