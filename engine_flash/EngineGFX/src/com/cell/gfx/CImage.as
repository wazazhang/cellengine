package com.cell.gfx
{
	import flash.display.BitmapData;

	public class CImage
	{
		internal var src : BitmapData;
		
		public function CImage()
		{
		}
		
		public function get width() : int {
			return 0;
		}
		
		public function get height() : int {
			return 0;
		}
		
		public function clone() : CImage {
			return this;
		}
	}
}