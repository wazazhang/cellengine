package com.cell.gfx
{
	import flash.display.BitmapData;

	public class CImage
	{
		internal var src : BitmapData;
		
		public function CImage(src : BitmapData)
		{
			this.src = src;
		}
		
		public function get width() : int {
			return src.width;
		}
		
		public function get height() : int {
			return src.height;
		}
		
		public function clone() : CImage {
			return new CImage(src);
		}
	}
}